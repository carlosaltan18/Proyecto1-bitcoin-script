package org.example.opcode;

import org.example.interpreter.ExecutionContext;

public interface Opcode {
    void execute(ExecutionContext context);
}
