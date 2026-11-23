import type { GameStateView } from '../bridge/state.ts';
import { SCALE } from './drawBoard.ts';

const PLAYER_COLOR = '#f2f2f2';

export function drawPlayer(ctx: CanvasRenderingContext2D, state: GameStateView): void {
  const size = state.cellSize * SCALE;
  ctx.fillStyle = PLAYER_COLOR;
  ctx.fillRect(state.player.x * SCALE, state.player.y * SCALE, size, size);
}
