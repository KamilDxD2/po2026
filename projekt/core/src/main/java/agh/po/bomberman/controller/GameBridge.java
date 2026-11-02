
package agh.po.bomberman.controller;

import java.util.Base64;

import org.teavm.jso.JSExport;

public class GameEngine {

    @JSExport
    public static int pingJavaEngine() {
        return 2026;
    }

    // ===== Boundary-type spike (temporary, delete after the test) =====

    @JSExport
    public static String echo(String text) {
        return text + "!";
    }

    @JSExport
    public static int sumBytes(byte[] bytes) {
        int sum = 0;
        for (byte value : bytes) {
            sum += value;
        }
        return sum;
    }

    @JSExport
    public static byte[] makeBytes() {
        return new byte[] {1, 2, -1};
    }

    @JSExport
    public static String countNames(String[] names) {
        return names.length + ":" + String.join(",", names);
    }

    // byte[][] parameter removed: it crashes the TeaVM 0.16 Wasm GC compiler
    // (ArrayIndexOutOfBoundsException in WasmGCMethodGenerator).

    @JSExport
    public static SpikeResult makeResult() {
        return new SpikeResult("OK", "my save", new byte[] {7, -7});
    }

    @JSExport
    public static String base64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    @JSExport
    public static int base64DecodedLength(String text) {
        return Base64.getDecoder().decode(text).length;
    }

    public static class SpikeResult {
        private final String check;
        private final String name;
        private final byte[] bytes;

        public SpikeResult(String check, String name, byte[] bytes) {
            this.check = check;
            this.name = name;
            this.bytes = bytes;
        }

        @JSExport
        public String getCheck() {
            return check;
        }

        @JSExport
        public String getName() {
            return name;
        }

        @JSExport
        public byte[] getBytes() {
            return bytes;
        }
    }

    // ===== End of spike =====

    public static void main(String[] args) {

    }
}
