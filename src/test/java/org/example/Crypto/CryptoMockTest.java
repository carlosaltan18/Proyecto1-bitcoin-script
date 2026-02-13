package org.example.Crypto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CryptoMockTest {

    @Test
    void hash160ShouldReturnFirstFourBytes() {
        byte[] data = "abcdef".getBytes();

        byte[] hash = CryptoMock.hash160(data);

        assertEquals(4, hash.length);
        assertEquals('a', hash[0]);
        assertEquals('b', hash[1]);
    }

    @Test
    void checkSigShouldReturnTrueWhenBothNotEmpty() {
        boolean result = CryptoMock.checkSig(
                "sig".getBytes(),
                "pubKey".getBytes()
        );

        assertTrue(result);
    }

    @Test
    void checkSigShouldReturnFalseWhenEmpty() {
        boolean result = CryptoMock.checkSig(
                new byte[]{},
                "pubKey".getBytes()
        );

        assertFalse(result);
    }

}