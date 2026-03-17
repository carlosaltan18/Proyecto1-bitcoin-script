package org.example.integration;

import org.example.interpreter.ScriptInterpreter;
import org.example.parser.ScriptParser;
import org.example.parser.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * End-to-end integration tests for the Bitcoin Script interpreter.
 *
 * Each test parses a full script string and runs it through the interpreter,
 * asserting the overall pass/fail result.  These tests cover the three
 * mandatory demonstrations from the project spec plus common edge cases.
 */
class ScriptInterpreterIntegrationTest {

    private ScriptParser parser;
    private ScriptInterpreter interpreter;

    @BeforeEach
    void setUp() {
        parser = new ScriptParser();
        interpreter = new ScriptInterpreter();
    }

    // ---------------------------------------------------------------
    // Helper
    // ---------------------------------------------------------------
    private boolean run(String script) {
        List<Token> tokens = parser.parse(script);
        return interpreter.execute(tokens);
    }

    // ===============================================================
    // DEMO A — P2PKH (Pay-to-Public-Key-Hash)
    // scriptSig:    <sig> <pubKey>
    // scriptPubKey: OP_DUP OP_HASH160 <pubKeyHash> OP_EQUALVERIFY OP_CHECKSIG
    // ===============================================================

    @Test
    void p2pkh_shouldPassWithCorrectSignatureAndKey() {
        // MockScriptMode: hash160 returns first 4 bytes → "pubK"
        String script = "PUSHDATA signature PUSHDATA pubKey "
                + "OP_DUP OP_HASH160 PUSHDATA pubK OP_EQUALVERIFY OP_CHECKSIG";
        assertTrue(run(script));
    }

    @Test
    void p2pkh_shouldFailWithWrongHash() {
        // hash160("pubKey") = "pubK", but we compare against "XXXX"
        String script = "PUSHDATA signature PUSHDATA pubKey "
                + "OP_DUP OP_HASH160 PUSHDATA XXXX OP_EQUALVERIFY OP_CHECKSIG";
        assertFalse(run(script));
    }

    @Test
    void p2pkh_shouldFailWithWrongPubKey() {
        // Use a pubKey whose first 4 bytes ("diff") don't match the claimed hash "pubK"
        String script = "PUSHDATA signature PUSHDATA differentKey "
                + "OP_DUP OP_HASH160 PUSHDATA pubK OP_EQUALVERIFY OP_CHECKSIG";
        assertFalse(run(script));
    }

    // ===============================================================
    // DEMO B — OP_IF / OP_ELSE / OP_ENDIF
    // ===============================================================

    @Test
    void opIf_shouldExecuteTrueBranch() {
        // condition is 1 → enter IF, push OP_1, skip ELSE
        String script = "OP_1 OP_IF OP_1 OP_ELSE OP_0 OP_ENDIF";
        assertTrue(run(script));
    }

    @Test
    void opIf_shouldExecuteFalseBranch() {
        // condition is 0 → skip IF body, execute ELSE (pushes OP_0)
        String script = "OP_0 OP_IF OP_1 OP_ELSE OP_0 OP_ENDIF";
        assertFalse(run(script));
    }

    @Test
    void opIf_withoutElse_trueBranchLeavesValue() {
        String script = "OP_1 OP_IF OP_1 OP_ENDIF";
        assertTrue(run(script));
    }

    @Test
    void opIf_withoutElse_falseBranchLeavesEmptyStack() {
        // condition false, no ELSE → stack empty after ENDIF → false
        String script = "OP_0 OP_IF OP_1 OP_ENDIF";
        assertFalse(run(script));
    }

    @Test
    void opNotIf_shouldSkipBodyWhenConditionIsTrue() {
        // OP_NOTIF: body executes when condition is false
        String script = "OP_1 OP_NOTIF OP_1 OP_ELSE OP_0 OP_ENDIF";
        assertFalse(run(script));
    }

    @Test
    void nestedOpIf_shouldHandleTwoLevels() {
        // outer true, inner true → result is OP_1
        String script = "OP_1 OP_IF OP_1 OP_IF OP_1 OP_ENDIF OP_ENDIF";
        assertTrue(run(script));
    }

    @Test
    void nestedOpIf_outerFalseSkipsEverythingInside() {
        String script = "OP_0 OP_IF OP_1 OP_IF OP_1 OP_ENDIF OP_ENDIF";
        assertFalse(run(script));
    }

    @Test
    void unclosedOpIf_shouldReturnFalse() {
        // Missing OP_ENDIF → interpreter treats as malformed script
        String script = "OP_1 OP_IF OP_1";
        assertFalse(run(script));
    }

    // ===============================================================
    // DEMO C (optional/advanced) — Multisig 2-of-3
    // OP_0 <sig1> <sig2> OP_2 <pub1> <pub2> <pub3> OP_3 OP_CHECKMULTISIG
    // ===============================================================

    @Test
    void multisig_2of3_shouldPass() {
        String script = "OP_0 PUSHDATA sig1 PUSHDATA sig2 OP_2 "
                + "PUSHDATA pub1 PUSHDATA pub2 PUSHDATA pub3 OP_3 "
                + "OP_CHECKMULTISIG";
        assertTrue(run(script));
    }

    @Test
    void multisig_1of1_shouldPass() {
        String script = "OP_0 PUSHDATA sig1 OP_1 PUSHDATA pub1 OP_1 OP_CHECKMULTISIG";
        assertTrue(run(script));
    }

    // ===============================================================
    // Stack operations
    // ===============================================================

    @Test
    void opDup_shouldDuplicateTopElement() {
        String script = "OP_1 OP_DUP OP_EQUAL";
        assertTrue(run(script));
    }

    @Test
    void opSwap_shouldSwapTopTwo() {
        // push 1 then 0 → stack [1, 0(top)]; swap → [0, 1(top)]; drop → [0(top)]
        // To get true on top: push 0 then 1, swap → 0 on top of 1, drop 0 → 1 remains
        String script = "OP_0 OP_1 OP_SWAP OP_DROP";
        assertTrue(run(script));
    }

    @Test
    void opOver_shouldCopySecondToTop() {
        // stack: 1 0 → over → 1 0 1, drop twice → 1
        String script = "OP_1 OP_0 OP_OVER OP_DROP OP_DROP";
        assertTrue(run(script));
    }

    @Test
    void opDrop_shouldRemoveTopElement() {
        String script = "OP_0 OP_1 OP_DROP";
        assertFalse(run(script)); // OP_0 remains after dropping OP_1
    }

    // ===============================================================
    // Arithmetic
    // ===============================================================

    @Test
    void opAdd_shouldAddTwoNumbers() {
        // 2 + 3 = 5, compare with OP_5
        String script = "OP_2 OP_3 OP_ADD OP_5 OP_EQUAL";
        assertTrue(run(script));
    }

    @Test
    void opSub_shouldSubtractTwoNumbers() {
        // 5 - 3 = 2, compare with OP_2
        String script = "OP_5 OP_3 OP_SUB OP_2 OP_EQUAL";
        assertTrue(run(script));
    }

    @Test
    void opLessThan_shouldBeTrueWhenSmaller() {
        String script = "OP_3 OP_5 OP_LESSTHAN";
        assertTrue(run(script));
    }

    @Test
    void opGreaterThan_shouldBeTrueWhenLarger() {
        String script = "OP_5 OP_3 OP_GREATERTHAN";
        assertTrue(run(script));
    }

    // ===============================================================
    // Logic / comparison
    // ===============================================================

    @Test
    void opEqual_shouldBeTrueForSameValues() {
        String script = "OP_3 OP_3 OP_EQUAL";
        assertTrue(run(script));
    }

    @Test
    void opEqual_shouldBeFalseForDifferentValues() {
        String script = "OP_3 OP_5 OP_EQUAL";
        assertFalse(run(script));
    }

    @Test
    void opNot_shouldInvertZeroToOne() {
        String script = "OP_0 OP_NOT";
        assertTrue(run(script));
    }

    @Test
    void opNot_shouldInvertOneToZero() {
        String script = "OP_1 OP_NOT";
        assertFalse(run(script));
    }

    @Test
    void opBoolAnd_shouldBeTrueWhenBothNonZero() {
        String script = "OP_1 OP_1 OP_BOOLAND";
        assertTrue(run(script));
    }

    @Test
    void opBoolOr_shouldBeTrueWhenOneIsNonZero() {
        String script = "OP_0 OP_1 OP_BOOLOR";
        assertTrue(run(script));
    }

    // ===============================================================
    // Control flow — OP_VERIFY / OP_RETURN
    // ===============================================================

    @Test
    void opVerify_shouldPassWhenTopIsTrue() {
        String script = "OP_1 OP_VERIFY OP_1";
        assertTrue(run(script));
    }

    @Test
    void opVerify_shouldFailWhenTopIsZero() {
        String script = "OP_0 OP_VERIFY OP_1";
        assertFalse(run(script));
    }

    @Test
    void opReturn_shouldAlwaysFail() {
        String script = "OP_1 OP_RETURN";
        assertFalse(run(script));
    }

    // ===============================================================
    // Edge cases
    // ===============================================================

    @Test
    void emptyScript_shouldReturnFalse() {
        assertFalse(run(""));
    }

    @Test
    void singleTrueValue_shouldPass() {
        assertTrue(run("OP_1"));
    }

    @Test
    void singleFalseValue_shouldFail() {
        assertFalse(run("OP_0"));
    }

    @Test
    void unknownToken_isTreatedAsDataPush() {
        // The parser treats unknown tokens as raw data pushes (not opcodes).
        // OP_1 pushes 1, then OP_UNKNOWN_XYZ pushes its bytes as data.
        // Top of stack is the byte array of "OP_UNKNOWN_XYZ" — non-empty → truthy.
        assertTrue(run("OP_1 OP_UNKNOWN_XYZ"));
    }
}
