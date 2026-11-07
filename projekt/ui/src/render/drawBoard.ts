import { tileAt, type GameStateView, type TileLetter } from '../bridge/state.ts';

export const SCALE = 3;

const TILE_COLORS: Record<TileLetter, string> = {
  F: '#2e8b3a',
  H: '#7d7d7d',
  S: '#b5651d',
  C: '#e0a040',
};

export function drawBoard(ctx: CanvasRenderingContext2D, state: GameStateView): void {
  const size = state.cellSize * SCALE;
  ctx.imageSmoothingEnabled = false;
  for (let row = 0; row < state.height; row++) {
    for (let col = 0; col < state.width; col++) {
      ctx.fillStyle = TILE_COLORS[tileAt(state, col, row)];
      ctx.fillRect(col * size, row * size, size, size);
    }
  }
}
