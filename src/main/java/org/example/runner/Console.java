package org.example.runner;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

/**
 * Thin wrapper around Jansi for colored terminal output.
 * Call Console.install() once at startup to enable ANSI on all platforms.
 */
public class Console {

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

    public static void skip(String value) {
        System.out.println(Ansi.ansi().fgDefault().a("[SKIP]  ").reset().fgBrightBlack().a(value).reset());
    }

    public static void error(String message) {
        System.out.println(Ansi.ansi().fgRed().a("[ERROR] ").reset().a(message));
    }

    public static void stackSize(int size) {
        System.out.println(Ansi.ansi().fgYellow().a("  stack: " + size).reset());
    }

    public static void scriptHeader(int number, String script) {
        System.out.println(Ansi.ansi().bold().a("--- Script #" + number + " ---").reset());
        System.out.println(Ansi.ansi().fgBrightBlack().a("  " + script).reset());
    }

    public static void result(boolean valid) {
        if (valid) {
            System.out.println(Ansi.ansi().fgGreen().bold().a("  Result: VALID").reset());
        } else {
            System.out.println(Ansi.ansi().fgRed().bold().a("  Result: INVALID").reset());
        }
    }
}
