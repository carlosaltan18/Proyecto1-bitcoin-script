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
        OPCODES.put("OP_FALSE", new OpPushZero());
        for (int i = 1; i <= 16; i++) {
            OPCODES.put("OP_" + i, new OpPushNumber(i));
        }

        // Stack
        OPCODES.put("OP_DUP", new OpDup());
        OPCODES.put("OP_DROP", new OpDrop());
        OPCODES.put("OP_SWAP", new OpSwap());
        OPCODES.put("OP_OVER", new OpOver());

        // Comparison
        OPCODES.put("OP_EQUAL", new OpEqual());
        OPCODES.put("OP_EQUALVERIFY", new OpEqualVerify());
        OPCODES.put("OP_NOT", new OpNot());
        OPCODES.put("OP_BOOLAND", new OpBoolAnd());
        OPCODES.put("OP_BOOL_AND", new OpBoolAnd());
        OPCODES.put("OP_BOOLOR", new OpBoolOr());
        OPCODES.put("OP_BOOL_OR", new OpBoolOr());

        // Arithmetic
        OPCODES.put("OP_ADD", new OpAdd());
        OPCODES.put("OP_SUB", new OpSub());
        OPCODES.put("OP_NUMEQUALVERIFY", new OpNumEqualVerify());
        OPCODES.put("OP_NUM_EQUALVERIFY", new OpNumEqualVerify());
        OPCODES.put("OP_LESSTHAN", new OpLessThan());
        OPCODES.put("OP_GREATERTHAN", new OpGreaterThan());
        OPCODES.put("OP_LESSTHANOREQUAL", new OpLessThanOrEqual());
        OPCODES.put("OP_GREATERTHANOREQUAL", new OpGreaterThanOrEqual());

        // Control flow
        OPCODES.put("OP_IF", new OpIf());
        OPCODES.put("OP_NOTIF", new OpNotIf());
        OPCODES.put("OP_ELSE", new OpElse());
        OPCODES.put("OP_ENDIF", new OpEndIf());
        OPCODES.put("OP_VERIFY", new OpVerify());
        OPCODES.put("OP_RETURN", new OpReturn());

        // Crypto (mock — real implementation to be provided by professor)
        OPCODES.put("OP_SHA256", new OpSha256());
        OPCODES.put("OP_HASH160", new OpHash160());
        OPCODES.put("OP_HASH256", new OpHash256());
        OPCODES.put("OP_CHECKSIG", new OpCheckSig());
        OPCODES.put("OP_CHECKSIGVERIFY", new OpCheckSigVerify());
        OPCODES.put("OP_CHECKMULTISIG", new OpCheckMultiSig());
    }
    public static Opcode get(String name) {
        return OPCODES.get(name);
    }
}