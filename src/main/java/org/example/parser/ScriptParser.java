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
                final boolean hasEnoughArguments = i + 1 < words.length;
                if (hasEnoughArguments) {
                    String next = words[i + 1];
                    // @TODO: When Opcodes are defined, check if the next word is an OP
                    // if (next.startsWith("OP")) {
                    //     throw new RuntimeException("PUSHDATA expected data, and an OPERATION was found instead.");
                    // }
                    tokens.add(new Token(TokenType.DATA, next));
                    i++; // Skip the next word since it's already processed
                    continue;
                } else {
                    throw new RuntimeException("PUSHDATA without data");
                }
            }

            // @TODO: When Opcodes are defined, replace this prefix check with an actual set check
            if (word.startsWith("OP")) {
                tokens.add(new Token(TokenType.OPERATOR, word));
            } else {
                tokens.add(new Token(TokenType.DATA, word));
            }
        }

        return tokens;
    }
}
