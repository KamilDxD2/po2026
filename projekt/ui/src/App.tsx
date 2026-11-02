import { useEffect, useState } from 'react';

// Exports of GameEngine (Java) compiled by TeaVM, used by the boundary-type spike
interface SpikeResult {
  getCheck(): string;
  getName(): string;
  getBytes(): Int8Array;
}

interface JavaExports {
  pingJavaEngine(): number;
  echo(text: string): string;
  sumBytes(bytes: ArrayLike<number>): number;
  makeBytes(): Int8Array;
  countNames(names: string[]): string;
  makeResult(): SpikeResult;
  base64(bytes: ArrayLike<number>): string;
  base64DecodedLength(text: string): number;
}

interface TeaVMGlobal {
  wasmGC: { load(path: string): Promise<{ exports: JavaExports }> };
}

type Status = 'PASS' | 'FAIL' | 'NOT SUPPORTED';

interface TestRow {
  javaType: string;
  call: string;
  expected: string;
  actual: string;
  status: Status;
}

interface TestCase {
  javaType: string;
  call: string;
  expected: string;
  run: (java: JavaExports) => unknown;
}

const TEST_CASES: TestCase[] = [
  { javaType: 'int (out)', call: 'pingJavaEngine()', expected: '2026', run: (java) => java.pingJavaEngine() },
  { javaType: 'String (in / out)', call: 'echo("zażółć")', expected: '"zażółć!"', run: (java) => java.echo('zażółć') },
  { javaType: 'byte[] (in, JS array)', call: 'sumBytes([1, 2, 3])', expected: '6', run: (java) => java.sumBytes([1, 2, 3]) },
  { javaType: 'byte[] (in, Int8Array)', call: 'sumBytes(new Int8Array([1, 2, -1]))', expected: '2', run: (java) => java.sumBytes(new Int8Array([1, 2, -1])) },
  { javaType: 'byte[] (out)', call: 'makeBytes()', expected: 'Int8Array [1, 2, -1]', run: (java) => java.makeBytes() },
  { javaType: 'String[] (in)', call: 'countNames(["a", "b"])', expected: '"2:a,b"', run: (java) => java.countNames(['a', 'b']) },
  { javaType: 'Java object (out)', call: 'makeResult().getName()', expected: '"my save"', run: (java) => java.makeResult().getName() },
  { javaType: 'byte[] from object getter', call: 'makeResult().getBytes()', expected: 'Int8Array [7, -7]', run: (java) => java.makeResult().getBytes() },
  { javaType: 'java.util.Base64 (encode)', call: 'base64([1, 2, -1])', expected: '"AQL/"', run: (java) => java.base64([1, 2, -1]) },
  { javaType: 'java.util.Base64 (decode)', call: 'base64DecodedLength("AQL/")', expected: '3', run: (java) => java.base64DecodedLength('AQL/') },
];

// Not in the wasm: a byte[][] parameter crashes the TeaVM 0.16 Wasm GC compiler
const UNSUPPORTED_ROW: TestRow = {
  javaType: 'byte[][] (in)',
  call: 'totalLength([[1, 2], [3]])',
  expected: '3',
  actual: 'TeaVM 0.16 compiler crash (ArrayIndexOutOfBoundsException)',
  status: 'NOT SUPPORTED',
};

function format(value: unknown): string {
  if (value instanceof Int8Array) {
    return `Int8Array [${Array.from(value).join(', ')}]`;
  }
  if (typeof value === 'string') {
    return `"${value}"`;
  }
  return String(value);
}

function runTests(java: JavaExports): TestRow[] {
  const rows = TEST_CASES.map((testCase): TestRow => {
    try {
      const actual = format(testCase.run(java));
      const status = actual === testCase.expected ? 'PASS' : 'FAIL';
      return { javaType: testCase.javaType, call: testCase.call, expected: testCase.expected, actual, status };
    } catch (err) {
      return { javaType: testCase.javaType, call: testCase.call, expected: testCase.expected, actual: `error: ${String(err)}`, status: 'FAIL' };
    }
  });
  return [...rows, UNSUPPORTED_ROW];
}

const STATUS_COLORS: Record<Status, string> = {
  'PASS': 'green',
  'FAIL': 'red',
  'NOT SUPPORTED': 'darkorange',
};

const cellStyle = { border: '1px solid #ccc', padding: '6px 10px', textAlign: 'left' as const };

function App() {
  const [rows, setRows] = useState<TestRow[] | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const script = document.createElement('script');
    script.src = '/wasm/classes.wasm-runtime.js';
    script.async = true;

    script.onload = async () => {
      try {
        const teaVM = (window as unknown as { TeaVM: TeaVMGlobal }).TeaVM;
        const teavm = await teaVM.wasmGC.load('/wasm/classes.wasm');

        // Spike (temporary): lets you call teavm.exports.* from the DevTools console
        Object.assign(window, { teavm });

        setRows(runTests(teavm.exports));
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

  const passed = rows?.filter((row) => row.status === 'PASS').length ?? 0;

  return (
    <div style={{ margin: '40px auto', maxWidth: '1000px', padding: '0 16px', fontFamily: 'sans-serif' }}>
      <h1>Bomberman Engine: Java ↔ JavaScript boundary types</h1>
      <p>
        Each row calls a <code>@JSExport</code> method of the Java engine (TeaVM 0.16, Wasm GC)
        and checks the value that comes back.
      </p>
      {error ? (
        <h2 style={{ color: 'red' }}>Błąd: {error}</h2>
      ) : rows === null ? (
        <h2>Ładowanie...</h2>
      ) : (
        <>
          <h2>{passed} / {TEST_CASES.length} passed</h2>
          <div style={{ overflowX: 'auto' }}>
            <table style={{ borderCollapse: 'collapse', width: '100%' }}>
              <thead>
                <tr>
                  <th style={cellStyle}>Java type</th>
                  <th style={cellStyle}>Call</th>
                  <th style={cellStyle}>Expected</th>
                  <th style={cellStyle}>Actual</th>
                  <th style={cellStyle}>Result</th>
                </tr>
              </thead>
              <tbody>
                {rows.map((row) => (
                  <tr key={row.call}>
                    <td style={cellStyle}>{row.javaType}</td>
                    <td style={cellStyle}><code>{row.call}</code></td>
                    <td style={cellStyle}><code>{row.expected}</code></td>
                    <td style={cellStyle}><code>{row.actual}</code></td>
                    <td style={{ ...cellStyle, color: STATUS_COLORS[row.status], fontWeight: 'bold' }}>{row.status}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          <p>
            Decision: saves and the top score cross the boundary as Base64 <code>String</code>s,
            because <code>byte[][]</code> cannot be exported (see <code>uml/03-diagram-klas.puml</code>).
          </p>
        </>
      )}
    </div>
  );
}

export default App;
