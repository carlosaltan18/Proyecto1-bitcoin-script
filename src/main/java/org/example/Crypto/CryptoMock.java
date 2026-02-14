package org.example.Crypto;

import java.util.Arrays;

/**
 * A class that serves to simulate a Hash160
 */
public class CryptoMock {
    /**
     * Metod that takes de 4 first characters for a hash
     * @param data Token
     * @return hask token
     */
    public static byte[] hash160(byte[] data){
        return Arrays.copyOfRange(data, 0, Math.min(4, data.length));
    }

    /**
     * Metod that simulate if the two values are equals
     * @param sig the next token
     * @param pubKey token hash
     * @return true if both are equals
     */
    public static boolean checkSig(byte[] sig, byte[] pubKey){
        return sig.length > 0 && pubKey.length > 0;
    }
}
