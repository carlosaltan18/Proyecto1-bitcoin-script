package org.example.opcode;

import org.example.opcode.functions.*;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *Class that serves to relate the script with de function of the script
 */
public class OpcodeImplements {
    private static final Map<String, Opcode> OPCODES = new HashMap<>();
    static {
        OPCODES.put("OP_0", new OpPushZero());
        for (int i = 1; i <= 16; i++) {
            OPCODES.put("OP_" + i, new OpPushNumber(i));
        }

        OPCODES.put("OP_DUP", new OpDup());
        OPCODES.put("OP_DROP", new OpDrop());
        OPCODES.put("OP_EQUAL", new OpEqual());
        OPCODES.put("OP_EQUALVERIFY", new OpEqualVerify());
        OPCODES.put("OP_HASH160", new OpHash160());
        OPCODES.put("OP_CHECKSIG", new OpCheckSig());
        OPCODES.put("OP_ADD", new OpAdd());
        OPCODES.put("OP_SUB", new OpSub());
        OPCODES.put("OP_BOOL_AND", new OpBoolAnd());
        OPCODES.put("OP_BOOL_OR", new OpBoolOr());
        OPCODES.put("OP_NOT", new OpNot());
        OPCODES.put("OP_NUM_EQUALVERIFY", new OpNumEqualVerify());
        OPCODES.put("OP_CHECKMULTISIG", new OpCheckMultiSig());
        OPCODES.put("OP_IF", new OpIf());
        OPCODES.put("OP_ELSE", new OpElse());
        OPCODES.put("OP_ENDIF", new OpEndIf());
    }
    public static Opcode get(String name) {
        return OPCODES.get(name);
    }
}