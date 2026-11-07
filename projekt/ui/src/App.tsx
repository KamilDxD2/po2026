import { useEffect, useRef, useState } from 'react';
import { loadBridge } from './bridge/gameBridge.ts';
import { parseState } from './bridge/state.ts';
import { drawBoard, SCALE } from './render/drawBoard.ts';

function App() {
  const canvasRef = useRef<HTMLCanvasElement>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    let active = true;

    loadBridge()
      .then((bridge) => {
        const canvas = canvasRef.current;
        if (!active || canvas === null) {
          return;
        }
        const state = parseState(bridge.getState());
        canvas.width = state.width * state.cellSize * SCALE;
        canvas.height = state.height * state.cellSize * SCALE;
        const ctx = canvas.getContext('2d');
        if (ctx === null) {
          setError('Canvas 2D is not available');
          return;
        }
        drawBoard(ctx, state);
      })
      .catch((err: unknown) => {
        console.error('WASM Load Error:', err);
        if (active) {
          setError(String(err));
        }
      });

    return () => {
      active = false;
    };
  }, []);

  return (
    <div style={{ marginTop: '24px', fontFamily: 'sans-serif' }}>
      <h1>Bomberman</h1>
      {error !== null && <h2 style={{ color: 'red' }}>Błąd: {error}</h2>}
      <canvas ref={canvasRef} style={{ maxWidth: '100%', imageRendering: 'pixelated' }} />
    </div>
  );
}

export default App;
