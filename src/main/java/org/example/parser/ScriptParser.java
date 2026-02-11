package org.example.parser;

import java.util.ArrayList;
import java.util.List;

public class ScriptParser {

    public List<Token> parse(String input) {
        final String[] words = input.split("\\s+");
        List<Token> tokens = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            if (word.equals("PUSHDATA")) {
                if (i + 1 < words.length) {
                    tokens.add(new Token(TokenType.DATA, words[i + 1]));
                    i++; // Skip the next word since it's already processed
                    continue;
                } else {
                    throw new RuntimeException("PUSHDATA without data");
                }
            }

            if (word.startsWith("OP")) {
                tokens.add(new Token(TokenType.OPERATOR, word));
            } else {
                tokens.add(new Token(TokenType.DATA, word));
            }
        }

        return tokens;
    }
}
