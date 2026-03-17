package org.example.interpreter;

import org.example.opcode.Opcode;
import org.example.opcode.OpcodeImplements;
import org.example.opcode.functions.OpPushData;
import org.example.parser.Token;
import org.example.parser.TokenType;

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

        try {
            for (Token token : tokens) {

                boolean isFlowControl = token.type() == TokenType.OPERATOR
                        && FLOW_CONTROL_OPS.contains(token.value());

                // Skip everything except flow-control ops when in a skipped branch.
                if (!context.isExecuting() && !isFlowControl) {
                    if (trace) {
                        System.out.println("[SKIP]  " + token.value());
                    }
                    continue;
                }

                if (token.type() == TokenType.DATA) {
                    if (trace) {
                        System.out.println("[PUSH]  " + token.value());
                    }
                    new OpPushData(token.value().getBytes()).execute(context);
                } else {
                    Opcode opcode = OpcodeImplements.get(token.value());
                    if (opcode == null) {
                        throw new RuntimeException("Unknown opcode: " + token.value());
                    }
                    if (trace) {
                        System.out.println("[OP]    " + token.value());
                    }
                    opcode.execute(context);
                }

                context.printStack();
            }

            // Unclosed OP_IF = malformed script.
            if (!context.getExecStack().isEmpty()) {
                throw new RuntimeException("Unclosed OP_IF: exec stack is not empty");
            }

            if (context.getStack().isEmpty()) {
                return false;
            }
            return context.getStack().pop()[0] != 0;

        } catch (Exception e) {
            if (trace) {
                System.out.println("[ERROR] " + e.getMessage());
            }
            return false;
        }
    }
}
