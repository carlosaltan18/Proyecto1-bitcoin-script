package org.example;

import org.example.interpreter.ScriptInterpreter;
import org.example.parser.ScriptParser;
import org.example.parser.Token;

import java.util.List;

/**
 * Main class that show de result and set de script to parse and interpreter
 */
public class Main {

    public static void main(String[] args) {

        ScriptParser parser = new ScriptParser();
        ScriptInterpreter interpreter = new ScriptInterpreter();
        String scriptSig =
                "PUSHDATA firma123 PUSHDATA pubKeyABC";

        String scriptPubKey =
                "OP_DUP OP_HASH160 PUSHDATA pubK OP_EQUALVERIFY OP_CHECKSIG";
        String fullScript = scriptSig + " " + scriptPubKey;

        try {
            List<Token> tokens = parser.parseWord(fullScript);
            boolean valid = interpreter.execute(tokens);
            System.out.println("Script válido: " + valid);

        } catch (Exception e) {
            System.out.println("Error al ejecutar el script: " + e.getMessage());
        }
    }
}
