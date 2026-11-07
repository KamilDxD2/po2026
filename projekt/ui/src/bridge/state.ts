export type TileLetter = 'F' | 'H' | 'S' | 'C';

export interface GameStateView {
  width: number;
  height: number;
  cellSize: number;
  tiles: string;
}

export function parseState(json: string): GameStateView {
  return JSON.parse(json) as GameStateView;
}

export function tileAt(state: GameStateView, col: number, row: number): TileLetter {
  return state.tiles.charAt(row * state.width + col) as TileLetter;
}
