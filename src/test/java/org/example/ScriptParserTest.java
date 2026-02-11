package org.example;

import org.example.parser.ScriptParser;
import org.example.parser.Token;
import org.example.parser.TokenType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScriptParserTest {
    static ScriptParser parser;

    @BeforeAll
    static void setUp() {
        parser = new ScriptParser();
    }


    @Test
    void shouldReturnAllTokens() {
        List<Token> list = parser.parse("OP_WHATEVER DATA XD");
        assertEquals(3, list.size());

        assertEquals(new Token(TokenType.OPERATOR, "OP_WHATEVER"), list.get(0));
        assertEquals(new Token(TokenType.DATA, "DATA"), list.get(1));
        assertEquals(new Token(TokenType.DATA, "XD"), list.get(2));
    }

        @Test
    void shouldAllowExplicitPushDataAllTokens() {
        List<Token> list = parser.parse("OP_WHATEVER PUSHDATA DATA PUSHDATA XD");
        assertEquals(3, list.size());

        assertEquals(new Token(TokenType.OPERATOR, "OP_WHATEVER"), list.get(0));
        assertEquals(new Token(TokenType.DATA, "DATA"), list.get(1));
        assertEquals(new Token(TokenType.DATA, "XD"), list.get(2));
    }

}
