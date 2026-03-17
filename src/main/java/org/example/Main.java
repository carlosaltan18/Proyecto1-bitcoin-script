package org.example;

import org.example.interpreter.ScriptInterpreter;
import org.example.parser.ScriptParser;
import org.example.parser.Token;
import org.example.runner.Console;
import org.example.runner.ScriptFileRunner;

import java.io.IOException;
import java.util.List;

/**
 * Entry point for the Bitcoin Script interpreter.
 *
 * <p>Usage:
 * <pre>
 *   mvn exec:java -Dexec.mainClass=org.example.Main -Dexec.args="[file.txt] [--trace]"
 * </pre>
 *
 * <ul>
 *   <li>If a file path is provided, each non-blank, non-comment line is
 *       executed as an independent script via {@link org.example.runner.ScriptFileRunner}.</li>
 *   <li>If no file is provided, a hardcoded demo script is executed.</li>
 *   <li>{@code --trace} enables step-by-step stack output for every script.</li>
 * </ul>
 */
public class Main {

    /**
     * Parses command-line arguments and runs the interpreter.
     *
     * @param args optional: {@code <file.txt>} and/or {@code --trace}
     * @throws IOException if the provided script file cannot be read
     */
    public static void main(String[] args) throws IOException {

        Console.install();

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
            Console.uninstall();
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
            Console.result(valid);

        } catch (Exception e) {
            Console.error(e.getMessage());
        }

        Console.uninstall();
    }
}
