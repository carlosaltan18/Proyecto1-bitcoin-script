# Bitcoin Script Interpreter

A didactic interpreter for a subset of Bitcoin Script, built in Java as part of **Proyecto #1 — Fase 2** for the Data Structures course.

Scripts are executed left-to-right on a byte-array stack. A script is **valid** if it finishes without error and the top of the stack is a non-zero (truthy) value.

---

## Requirements

| Tool | Version |
|------|---------|
| Java (JDK) | 21 or newer |
| Maven | Not required — Maven Wrapper is included |

> **No Maven installation needed.** The project ships with `mvnw` (Unix/macOS) and `mvnw.cmd` (Windows) which download Maven automatically on first run.

---

## Quick start

```bash
# 1. Clone the repo
git clone <repo-url>
cd Proyecto1-bitcoin-script

# 2. Run all demo scripts (Unix/macOS)
./mvnw exec:java -Dexec.mainClass=org.example.Main -Dexec.args="scripts.txt --trace"

# Windows
mvnw.cmd exec:java -Dexec.mainClass=org.example.Main -Dexec.args="scripts.txt --trace"
```

---

## Running scripts

### Run a script file

```bash
./mvnw exec:java -Dexec.mainClass=org.example.Main -Dexec.args="scripts.txt"
```

Any `.txt` file works. Each non-blank line that does not start with `#` is treated as one independent script.

### Enable step-by-step trace output

Add `--trace` to see the stack state after every instruction:

```bash
./mvnw exec:java -Dexec.mainClass=org.example.Main -Dexec.args="scripts.txt --trace"
```

Trace output example:
```
--- Script #1 ---
  PUSHDATA signature PUSHDATA pubKey OP_DUP OP_HASH160 PUSHDATA pubK OP_EQUALVERIFY OP_CHECKSIG
[PUSH]  signature
  stack (1): [signature] ←top
[PUSH]  pubKey
  stack (2): [signature, pubKey] ←top
  ··· scriptPubKey begins ···
[OP]    OP_DUP
  stack (3): [signature, pubKey, pubKey] ←top
...
  Result: VALID
```

### Run your own script file

Create any `.txt` file with one script per line:

```text
# my_scripts.txt
# Lines starting with # are comments and are ignored

# Simple equality check
OP_1 OP_1 OP_EQUAL

# P2PKH demo (mock mode: hash160 = first 4 bytes)
PUSHDATA sig PUSHDATA pubKey OP_DUP OP_HASH160 PUSHDATA pubK OP_EQUALVERIFY OP_CHECKSIG
```

Then run:

```bash
./mvnw exec:java -Dexec.mainClass=org.example.Main -Dexec.args="my_scripts.txt --trace"
```

---

## Script syntax

### Pushing data

```
PUSHDATA <value>
```

Pushes the string `<value>` as a byte array. Example: `PUSHDATA hello`

### Opcodes

Write opcode names directly, separated by spaces:

```
OP_1 OP_2 OP_ADD OP_3 OP_EQUAL
```

### Comments and blank lines

```text
# This is a comment — ignored by the runner
OP_1 OP_NOT   # inline comments are NOT supported — put comments on their own line
```

---

## Supported opcodes

### Literals
| Opcode | Description |
|--------|-------------|
| `OP_0` / `OP_FALSE` | Push empty byte array (false) |
| `OP_1` … `OP_16` | Push the number 1–16 |

### Stack
| Opcode | Description |
|--------|-------------|
| `OP_DUP` | Duplicate top element |
| `OP_DROP` | Remove top element |
| `OP_SWAP` | Swap top two elements |
| `OP_OVER` | Copy second-to-top onto top |

### Logic & comparison
| Opcode | Description |
|--------|-------------|
| `OP_EQUAL` | Push 1 if top two are equal, else 0 |
| `OP_EQUALVERIFY` | Like `OP_EQUAL` but fails the script if false |
| `OP_NOT` | Logical NOT of top element |
| `OP_BOOLAND` / `OP_BOOL_AND` | Logical AND |
| `OP_BOOLOR` / `OP_BOOL_OR` | Logical OR |

### Arithmetic
| Opcode | Description |
|--------|-------------|
| `OP_ADD` | Add top two numbers |
| `OP_SUB` | Subtract top from second |
| `OP_NUMEQUALVERIFY` / `OP_NUM_EQUALVERIFY` | Fail if top two numbers are not equal |
| `OP_LESSTHAN` | 1 if second < top |
| `OP_GREATERTHAN` | 1 if second > top |
| `OP_LESSTHANOREQUAL` | 1 if second ≤ top |
| `OP_GREATERTHANOREQUAL` | 1 if second ≥ top |

### Control flow
| Opcode | Description |
|--------|-------------|
| `OP_IF` | Execute next block if top is truthy |
| `OP_NOTIF` | Execute next block if top is falsy |
| `OP_ELSE` | Alternative branch |
| `OP_ENDIF` | Close conditional block |
| `OP_VERIFY` | Fail script if top is false |
| `OP_RETURN` | Immediately fail the script |

### Cryptographic (mock)
| Opcode | Description |
|--------|-------------|
| `OP_SHA256` | Mock SHA-256 (returns first 4 bytes) |
| `OP_HASH160` | Mock HASH160 (returns first 4 bytes) |
| `OP_HASH256` | Mock double-SHA256 (returns first 4 bytes) |
| `OP_CHECKSIG` | Mock signature check (passes if sig is non-empty) |
| `OP_CHECKSIGVERIFY` | `OP_CHECKSIG` + `OP_VERIFY` |
| `OP_CHECKMULTISIG` | Mock m-of-n multisig (see below) |

> Cryptographic operations are simulated. Real signature verification requires a transaction hash that is outside the scope of this interpreter.

### Multisig (`OP_CHECKMULTISIG`)

Stack layout expected (bottom → top):
```
OP_0  sig₁ … sigₘ  M  pub₁ … pubₙ  N  OP_CHECKMULTISIG
```

`OP_0` is a mandatory dummy element due to a historical bug in Bitcoin. Example 2-of-3:

```
OP_0 PUSHDATA sig1 PUSHDATA sig2 OP_2 PUSHDATA pub1 PUSHDATA pub2 PUSHDATA pub3 OP_3 OP_CHECKMULTISIG
```

---

## Running tests

```bash
./mvnw test
```

Expected output:
```
Tests run: 198, Failures: 0, Errors: 0, Skipped: 0
```

---

## Project structure

```
src/
├── main/java/org/example/
│   ├── Main.java                        # Entry point
│   ├── interpreter/
│   │   ├── ScriptInterpreter.java       # Executes token lists
│   │   └── ExecutionContext.java        # Main stack + exec stack state
│   ├── parser/
│   │   ├── ScriptParser.java            # Tokenizes script strings
│   │   ├── Token.java                   # Token (type + value)
│   │   └── TokenType.java               # OPERATOR or DATA
│   ├── opcode/
│   │   ├── Opcode.java                  # Interface for all opcodes
│   │   ├── OpcodeImplements.java        # Opcode name → implementation registry
│   │   ├── functions/                   # One class per opcode
│   │   └── helpers/
│   │       ├── ExecState.java           # EXECUTING / NOT_EXECUTING / PARENT_NOT_EXECUTING
│   │       └── ScriptUtils.java         # isTruthy() shared utility
│   ├── stack/
│   │   └── StackScript.java             # Generic stack (ArrayDeque-backed)
│   ├── runner/
│   │   ├── ScriptFileRunner.java        # Reads and runs .txt script files
│   │   └── Console.java                 # Colored ANSI terminal output (Jansi)
│   └── Crypto/
│       └── CryptoMock.java              # Mock hash160 + checkSig
└── test/java/org/example/
    ├── opcode/functions/                # Unit tests per opcode (28 classes)
    └── integration/
        └── ScriptInterpreterIntegrationTest.java  # End-to-end tests
scripts.txt                              # Demo scripts for all 3 required presentations
```

---

## Demo scripts (`scripts.txt`)

The included `scripts.txt` covers all three required Fase 2 demonstrations:

**Demo A — P2PKH (Pay-to-Public-Key-Hash)**
```
PUSHDATA signature PUSHDATA pubKey OP_DUP OP_HASH160 PUSHDATA pubK OP_EQUALVERIFY OP_CHECKSIG
```

**Demo B — Conditional with OP_IF / OP_ELSE / OP_ENDIF**
```
OP_1 OP_IF OP_1 OP_ELSE OP_0 OP_ENDIF
```

**Demo C — Multisig 2-of-3**
```
OP_0 PUSHDATA sig1 PUSHDATA sig2 OP_2 PUSHDATA pub1 PUSHDATA pub2 PUSHDATA pub3 OP_3 OP_CHECKMULTISIG
```
