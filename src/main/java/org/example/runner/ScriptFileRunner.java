package org.example.runner;

import org.example.interpreter.ScriptInterpreter;
import org.example.parser.ScriptParser;
import org.example.parser.Token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Reads a script file and executes each script line through the interpreter.
 * Blank lines and lines starting with '#' are ignored.
 */
public class ScriptFileRunner {

    private final ScriptParser parser = new ScriptParser();
    private final ScriptInterpreter interpreter = new ScriptInterpreter();

    public void run(String filePath, boolean trace) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        int scriptNumber = 0;

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                continue;
            }

            scriptNumber++;
            System.out.println("--- Script #" + scriptNumber + " ---");
            System.out.println("  " + trimmed);

            try {
                List<Token> tokens = parser.parse(trimmed);
                boolean valid = interpreter.execute(tokens, trace);
                System.out.println("  Result: " + (valid ? "VALID" : "INVALID"));
            } catch (Exception e) {
                System.out.println("  Error: " + e.getMessage());
            }
        }
    }
}
