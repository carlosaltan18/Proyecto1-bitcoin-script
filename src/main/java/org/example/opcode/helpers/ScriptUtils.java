package org.example.opcode.helpers;

/**
 * Utility methods shared across opcode implementations.
 */
public final class ScriptUtils {

    private ScriptUtils() {}

    /**
     * Returns true if the byte array represents a truthy value.
     * An empty array (canonical OP_0 / OP_FALSE) is false.
     * Any non-empty array with at least one non-zero byte is true.
     */
    public static boolean isTruthy(byte[] value) {
        if (value == null || value.length == 0) return false;
        for (byte b : value) {
            if (b != 0) return true;
        }
        return false;
    }
}
