package org.example;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public List<Token> parse(String input) {
        final String[] words = input.split("\\s+");
        List<Token> tokens = new ArrayList<>();

        for (String word : words) {
            if (word.startsWith("OP")) {
                tokens.add(new Token(TokenType.OPERATOR, word));
            }
            else {
                tokens.add(new Token(TokenType.DATA, word));
            }
        }

        return tokens;
    }
}
