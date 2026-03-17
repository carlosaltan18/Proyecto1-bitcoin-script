package org.example.interpreter;

import org.example.opcode.Opcode;
import org.example.opcode.OpcodeImplements;
import org.example.opcode.functions.OpPushData;
import org.example.opcode.helpers.ScriptUtils;
import org.example.parser.Token;
import org.example.parser.TokenType;
import org.example.runner.Console;

import java.util.List;
import java.util.Set;

/**
 * Class that executes a list of tokens based on stack rules.
 *
 * Flow-control opcodes (OP_IF, OP_ELSE, OP_ENDIF) always execute regardless
 * of the current exec-stack state so they can maintain proper nesting.
 * Every other opcode or data push is skipped when isExecuting() is false.
 */
public class ScriptInterpreter {

    /**
     * Opcodes that must always run to keep the exec stack balanced,
     * even when we are inside a skipped branch.
     */
    private static final Set<String> FLOW_CONTROL_OPS = Set.of(
            "OP_IF", "OP_ELSE", "OP_ENDIF"
    );

    /**
     * Opcodes that mark the start of scriptPubKey in a P2PKH script.
     * Used to print a visual divider in trace mode.
     */
    private static final Set<String> P2PKH_BOUNDARY_OPS = Set.of("OP_DUP");

    public boolean execute(List<Token> tokens) {
        return execute(tokens, false);
    }

    /**
     * Executes the token list.
     * @param tokens parsed script tokens
     * @param trace  when true, prints each step to stdout
     * @return true if the script succeeds (top of stack is non-zero)
     */
    public boolean execute(List<Token> tokens, boolean trace) {

        ExecutionContext context = new ExecutionContext();
        context.setTrace(trace);

        boolean isP2PKH = isP2PKHScript(tokens);
        boolean dividerPrinted = false;

        try {
            for (Token token : tokens) {

                boolean isFlowControl = token.type() == TokenType.OPERATOR
                        && FLOW_CONTROL_OPS.contains(token.value());

                // Skip everything except flow-control ops when in a skipped branch.
                if (!context.isExecuting() && !isFlowControl) {
                    if (trace) Console.skip(token.value());
                    continue;
                }

                // Print P2PKH boundary divider just before OP_DUP
                if (trace && isP2PKH && !dividerPrinted
                        && token.type() == TokenType.OPERATOR
                        && P2PKH_BOUNDARY_OPS.contains(token.value())) {
                    Console.p2pkhDivider();
                    dividerPrinted = true;
                }

                if (token.type() == TokenType.DATA) {
                    if (trace) Console.push(token.value());
                    new OpPushData(token.value().getBytes()).execute(context);
                } else {
                    Opcode opcode = OpcodeImplements.get(token.value());
                    if (opcode == null) {
                        throw new RuntimeException("Unknown opcode: " + token.value());
                    }

                    if (trace) {
                        String note = branchNote(token.value(), context);
                        if (note != null) Console.op(token.value(), note);
                        else Console.op(token.value());
                    }

                    opcode.execute(context);
                }

                if (trace) Console.stackState(context.getStack());
            }

            // Unclosed OP_IF = malformed script.
            if (!context.getExecStack().isEmpty()) {
                throw new RuntimeException("Unclosed OP_IF: exec stack is not empty");
            }

            if (context.getStack().isEmpty()) {
                return false;
            }
            return ScriptUtils.isTruthy(context.getStack().pop());

        } catch (Exception e) {
            if (trace) Console.error(e.getMessage());
            return false;
        }
    }

    /**
     * Returns a human-readable note for OP_ELSE and OP_ENDIF to show
     * which branch is active after the opcode runs, or null for all others.
     */
    private String branchNote(String opName, ExecutionContext context) {
        return switch (opName) {
            case "OP_ELSE" -> context.isExecuting() ? "entering else branch" : "leaving else branch";
            case "OP_ENDIF" -> "closing if block";
            default -> null;
        };
    }

    /**
     * Returns true if the token list looks like a P2PKH script
     * (contains both OP_DUP and OP_CHECKSIG).
     */
    private boolean isP2PKHScript(List<Token> tokens) {
        boolean hasDup = false;
        boolean hasCheckSig = false;
        for (Token t : tokens) {
            if ("OP_DUP".equals(t.value())) hasDup = true;
            if ("OP_CHECKSIG".equals(t.value())) hasCheckSig = true;
        }
        return hasDup && hasCheckSig;
    }
}
