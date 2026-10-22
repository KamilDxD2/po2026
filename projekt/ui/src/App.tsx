import { useEffect, useState } from 'react';

function App() {
  const [javaResponse, setJavaResponse] = useState<number | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const script = document.createElement('script');
    script.src = '/wasm/classes.wasm-runtime.js';
    script.async = true;

    script.onload = async () => {
      try {
        // @ts-ignore
        const teavm = await window.TeaVM.wasmGC.load('/wasm/classes.wasm');

        console.log("Załadowany obiekt TeaVM:", teavm);
        console.log("Dostępne eksporty Wasm:", teavm.instance.exports);

        const ping = teavm.exports.pingJavaEngine;

        if (typeof ping === 'function') {
          setJavaResponse(ping());
        } else {
          console.error("Nie znaleziono pingJavaEngine! Upewnij się, że w Javie nad metodą jest @JSExport.");
          setError("Brak funkcji w wyeksportowanym module.");
        }
      } catch (err) {
        console.error("WASM Load Error:", err);
        setError(String(err));
      }
    };

    document.body.appendChild(script);

    return () => {
      document.body.removeChild(script);
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