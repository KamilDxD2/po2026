export type TileLetter = 'F' | 'H' | 'S' | 'C';

export type GamePhase = 'STAGE_INTRO' | 'PLAYING' | 'PAUSED' | 'DYING' | 'GAME_OVER' | 'WON';

export interface PlayerView {
  x: number;
  y: number;
}

export interface GameStateView {
  state: GamePhase;
  width: number;
  height: number;
  cellSize: number;
  tiles: string;
  player: PlayerView;
}

export function parseState(json: string): GameStateView | null {
  return JSON.parse(json) as GameStateView | null;
}

export function tileAt(state: GameStateView, col: number, row: number): TileLetter {
  return state.tiles.charAt(row * state.width + col) as TileLetter;
}
