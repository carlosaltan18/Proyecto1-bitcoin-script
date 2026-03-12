package org.example;

import org.example.parser.ScriptParser;
import org.example.parser.Token;
import org.example.parser.TokenType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScriptParserTest {
    static ScriptParser parser;

    @BeforeAll
    static void setUp() {
        parser = new ScriptParser();
    }

    // P2PKH locking script: OP_DUP OP_HASH160 <pubKeyHash> OP_EQUALVERIFY OP_CHECKSIG
    @Test
    void shouldParseP2PKHLockingScript() {
        List<Token> tokens = parser.parse("OP_DUP OP_HASH160 89abcdefabbaabbaabbaabbaabbaabbaabbaabba OP_EQUALVERIFY OP_CHECKSIG");
        assertEquals(5, tokens.size());

        assertEquals(new Token(TokenType.OPERATOR, "OP_DUP"), tokens.get(0));
        assertEquals(new Token(TokenType.OPERATOR, "OP_HASH160"), tokens.get(1));
        assertEquals(new Token(TokenType.DATA, "89abcdefabbaabbaabbaabbaabbaabbaabbaabba"), tokens.get(2));
        assertEquals(new Token(TokenType.OPERATOR, "OP_EQUALVERIFY"), tokens.get(3));
        assertEquals(new Token(TokenType.OPERATOR, "OP_CHECKSIG"), tokens.get(4));
    }

    // P2PKH unlocking script: <sig> <pubKey>
    @Test
    void shouldParseP2PKHUnlockingScript() {
        List<Token> tokens = parser.parse("3045022100abc 04deadbeefcafe");
        assertEquals(2, tokens.size());

        assertEquals(new Token(TokenType.DATA, "3045022100abc"), tokens.get(0));
        assertEquals(new Token(TokenType.DATA, "04deadbeefcafe"), tokens.get(1));
    }

    // Arithmetic script: OP_2 OP_3 OP_ADD
    @Test
    void shouldParseArithmeticScript() {
        List<Token> tokens = parser.parse("OP_2 OP_3 OP_ADD");
        assertEquals(3, tokens.size());

        assertEquals(new Token(TokenType.OPERATOR, "OP_2"), tokens.get(0));
        assertEquals(new Token(TokenType.OPERATOR, "OP_3"), tokens.get(1));
        assertEquals(new Token(TokenType.OPERATOR, "OP_ADD"), tokens.get(2));
    }

    // PUSHDATA explicit form: PUSHDATA <data> is consumed and produces a DATA token
    @Test
    void shouldAllowExplicitPushData() {
        List<Token> tokens = parser.parse("OP_DUP PUSHDATA 89abcdefabbaabbaabbaabbaabbaabbaabbaabba OP_EQUAL");
        assertEquals(3, tokens.size());

        assertEquals(new Token(TokenType.OPERATOR, "OP_DUP"), tokens.get(0));
        assertEquals(new Token(TokenType.DATA, "89abcdefabbaabbaabbaabbaabbaabbaabbaabba"), tokens.get(1));
        assertEquals(new Token(TokenType.OPERATOR, "OP_EQUAL"), tokens.get(2));
    }

    // PUSHDATA at the very end with nothing after it must throw
    @Test
    void shouldThrowWhenPushDataHasNoFollowingData() {
        assertThrows(RuntimeException.class, () -> {
            parser.parse("OP_DUP PUSHDATA");
        }, "PUSHDATA without data");
    }
}
