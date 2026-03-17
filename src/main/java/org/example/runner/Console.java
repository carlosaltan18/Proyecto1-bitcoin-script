package org.example.runner;

import org.example.stack.StackScript;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Thin wrapper around Jansi for colored terminal output.
 * Call Console.install() once at startup to enable ANSI on all platforms.
 */
public class Console {

    /** Maximum number of bytes shown per stack element in trace output. */
    private static final int MAX_BYTES_SHOWN = 8;

    public static void install() {
        AnsiConsole.systemInstall();
    }

    public static void uninstall() {
        AnsiConsole.systemUninstall();
    }

    public static void push(String value) {
        System.out.println(Ansi.ansi().fgCyan().a("[PUSH]  ").reset().a(value));
    }

    public static void op(String value) {
        System.out.println(Ansi.ansi().fgBlue().bold().a("[OP]    ").reset().a(value));
    }

    /** Prints an op with an explanatory note, used for OP_ELSE / OP_ENDIF. */
    public static void op(String value, String note) {
        System.out.println(Ansi.ansi()
                .fgBlue().bold().a("[OP]    ").reset()
                .a(value + "  ")
                .fgBrightBlack().a("← " + note)
                .reset());
    }

    public static void skip(String value) {
        System.out.println(Ansi.ansi().fgBrightBlack().a("[SKIP]  " + value).reset());
    }

    public static void error(String message) {
        System.out.println(Ansi.ansi().fgRed().a("[ERROR] ").reset().a(message));
    }

    /**
     * Prints the current stack contents (bottom-to-top, left-to-right) after each step.
     * Each byte[] is rendered as a short human-readable string.
     */
    public static void stackState(StackScript<byte[]> stack) {
        // Iterator yields top-first; reverse so bottom is leftmost, top is rightmost.
        List<String> items = new ArrayList<>();
        for (byte[] item : stack) items.add(renderBytes(item));
        Collections.reverse(items);

        StringBuilder sb = new StringBuilder();
        sb.append("  stack (").append(stack.size()).append("): [");
        sb.append(String.join(", ", items));
        sb.append("]");
        if (!stack.isEmpty()) sb.append(" ←top");

        System.out.println(Ansi.ansi().fgYellow().a(sb.toString()).reset());
    }

    public static void scriptHeader(int number, String script) {
        System.out.println(Ansi.ansi().bold().a("--- Script #" + number + " ---").reset());
        System.out.println(Ansi.ansi().fgBrightBlack().a("  " + script).reset());
    }

    /** Printed mid-script to mark the boundary between scriptSig and scriptPubKey. */
    public static void p2pkhDivider() {
        System.out.println(Ansi.ansi()
                .fgBrightBlack().a("  ··· scriptPubKey begins ···")
                .reset());
    }

    public static void result(boolean valid) {
        if (valid) {
            System.out.println(Ansi.ansi().fgGreen().bold().a("  Result: VALID").reset());
        } else {
            System.out.println(Ansi.ansi().fgRed().bold().a("  Result: INVALID").reset());
        }
    }

    // ---------------------------------------------------------------

    /**
     * Renders a byte array as a readable string for trace output.
     * Tries UTF-8 printable text first; falls back to hex for binary data.
     */
    private static String renderBytes(byte[] data) {
        if (data == null || data.length == 0) return "0x(empty)";

        // Check if all bytes are printable ASCII
        boolean printable = true;
        for (byte b : data) {
            if (b < 32 || b > 126) { printable = false; break; }
        }

        String raw;
        if (printable) {
            raw = new String(data);
        } else {
            StringBuilder hex = new StringBuilder("0x");
            for (int i = 0; i < Math.min(data.length, MAX_BYTES_SHOWN); i++) {
                hex.append(String.format("%02x", data[i]));
            }
            if (data.length > MAX_BYTES_SHOWN) hex.append("…");
            raw = hex.toString();
        }

        // Truncate long strings
        if (raw.length() > MAX_BYTES_SHOWN + 2) {
            raw = raw.substring(0, MAX_BYTES_SHOWN) + "…";
        }
        return raw;
    }
}
