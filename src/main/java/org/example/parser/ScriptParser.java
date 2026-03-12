package org.example.parser;

import org.example.opcode.OpcodeImplements;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that serves to parse the script
 */
public class ScriptParser {

    /**
     * A public method that serves to parse a String to List of tokens
     * @param input String with script
     * @return List of token
     */
    public List<Token> parse(String input) {

        final String[] words = input.trim().split("\\s+");
        List<Token> tokens = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {

            String word = words[i];

            if (word.equals("PUSHDATA")) {

                if (i + 1 < words.length) {
                    tokens.add(new Token(TokenType.DATA, words[i + 1]));
                    i++;
                } else {
                    throw new RuntimeException("PUSHDATA without data");
                }

                continue;
            }

            if (OpcodeImplements.get(word) != null) {
                tokens.add(new Token(TokenType.OPERATOR, word));
            } else {
                tokens.add(new Token(TokenType.DATA, word));
            }
        }

        return tokens;
    }
}
