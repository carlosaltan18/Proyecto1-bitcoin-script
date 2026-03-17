package org.example;

import org.example.interpreter.ScriptInterpreter;
import org.example.parser.ScriptParser;
import org.example.parser.Token;
import org.example.runner.ScriptFileRunner;

import java.io.IOException;
import java.util.List;

/**
 * Main class that show de result and set de script to parse and interpreter
 */
public class Main {

    public static void main(String[] args) throws IOException {

        boolean trace = false;
        String filePath = null;

        for (String arg : args) {
            if (arg.equals("--trace")) {
                trace = true;
            } else if (!arg.startsWith("--")) {
                filePath = arg;
            }
        }

        if (filePath != null) {
            new ScriptFileRunner().run(filePath, trace);
            return;
        }

        ScriptParser parser = new ScriptParser();
        ScriptInterpreter interpreter = new ScriptInterpreter();
        //String scriptSig = "PUSHDATA firma12333 PUSHDATA pubcKKKeyABCC";
        String scriptSig = "PUSHDATA firma_valida PUSHDATA clave_publicA PUSHDATA 1";

        //String scriptPubKey = "OP_DUP OP_HASH160 PUSHDATA pubc OP_EQUALVERIFY OP_CHECKSIG";
        String scriptPubKey =
                "OP_IF " +
                        "OP_DUP OP_HASH160 PUSHDATA hash_de_clave_publicA OP_EQUALVERIFY OP_CHECKSIG " +
                        "OP_ELSE " +
                        "PUSHDATA 9 " +
                        "OP_ENDIF";

        String fullScript = scriptSig + " " + scriptPubKey;

        try {
            List<Token> tokens = parser.parse(fullScript);
            boolean valid = interpreter.execute(tokens, trace);
            System.out.println("Script válido: " + valid);

        } catch (Exception e) {
            System.out.println("Error al ejecutar el script: " + e.getMessage());
        }
    }
}
