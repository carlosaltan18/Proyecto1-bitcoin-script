package org.example.interpreter;

import org.example.opcode.Opcode;
import org.example.opcode.OpcodeImplements;
import org.example.opcode.functions.OpPushData;
import org.example.parser.Token;
import org.example.parser.TokenType;

import java.util.List;

/**
 * Class that executes a list of tokens based on stack rules.
 */
public class ScriptInterpreter {

    /**
     * Method that serves to push dato or execute a operation depends of the token
     * @param tokens list of tokens
     * @return a boolean is script is valid
     */
    public boolean execute(List<Token> tokens) {

        ExecutionContext context = new ExecutionContext();

        try {

            for (Token token : tokens) {

                if (token.type() == TokenType.DATA) {

                    new OpPushData(token.value().getBytes())
                            .execute(context);

                } else {

                    Opcode opcode = OpcodeImplements.get(token.value());

                    if (opcode == null) {
                        throw new RuntimeException("Unknown opcode " + token.value());
                    }

                    opcode.execute(context);
                }
            }

            if (context.getStack().isEmpty()) {
                return false;
            }

            return context.getStack().pop()[0] != 0;

        } catch (Exception e) {
            return false;
        }
    }
}
