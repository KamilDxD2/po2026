// The only file that talks to TeaVM. The rest of the view imports loadBridge()
// and calls the Java GameBridge through the typed GameBridge interface.

// @JSExport methods of agh.po.bomberman.controller.GameBridge.
// Keep in sync with the Java class (and uml/03-diagram-klas.puml).
export interface GameBridge {
  pingJavaEngine(): number;
}

interface TeaVMRuntime {
  wasmGC: {
    load(path: string): Promise<{ exports: GameBridge }>;
  };
}

declare global {
  interface Window {
    // Set by /wasm/classes.wasm-runtime.js, loaded in index.html
    TeaVM?: TeaVMRuntime;
  }
}

const WASM_PATH = '/wasm/classes.wasm';

let bridge: Promise<GameBridge> | null = null;

// Loads the wasm once; later calls (also React StrictMode's second effect run)
// get the same promise.
export function loadBridge(): Promise<GameBridge> {
  if (bridge === null) {
    const runtime = window.TeaVM;
    if (runtime === undefined) {
      return Promise.reject(new Error('TeaVM runtime not loaded: check the script tag in index.html'));
    }
    bridge = runtime.wasmGC.load(WASM_PATH).then((teavm) => teavm.exports);
  }
  return bridge;
}
