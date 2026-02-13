package org.example.Crypto;

import java.util.Arrays;

public class CryptoMock {

    public static byte[] hash160(byte[] data){

        //esta es una simulacion simple, tomamos los primeros 4 bytes como "hash"
        return Arrays.copyOfRange(data, 0, Math.min(4, data.length));
    }

    public static boolean chechSig(byte[] sig, byte[] pubKey){

        // tambien simulacion, si ambos tiene contenido, lo tomamos como valido
        return sig.length > 0 && pubKey.length > 0;
    }
}
