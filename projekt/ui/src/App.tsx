import { useEffect, useState } from 'react';
import { loadBridge } from './bridge/gameBridge.ts';

function App() {
  const [javaResponse, setJavaResponse] = useState<number | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    let active = true;

    loadBridge()
      .then((bridge) => {
        if (active) {
          setJavaResponse(bridge.pingJavaEngine());
        }
      })
      .catch((err: unknown) => {
        console.error("WASM Load Error:", err);
        if (active) {
          setError(String(err));
        }
      });

    return () => {
      active = false;
    };
  }, []);

  return (
    <div style={{ textAlign: 'center', marginTop: '50px', fontFamily: 'sans-serif' }}>
      <h1>Bomberman Engine (Official Wasm GC Bridge)</h1>
      {error ? (
        <h2 style={{ color: 'red' }}>Błąd: {error}</h2>
      ) : (
        <h2>
          Java Backend mówi: <span style={{ color: 'green' }}>{javaResponse !== null ? javaResponse : 'Ładowanie...'}</span>
        </h2>
      )}
    </div>
  );
}

export default App;
