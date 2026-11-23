export const InputBits = {
  UP: 1,
  DOWN: 2,
  LEFT: 4,
  RIGHT: 8,
  BOMB: 16,
  DETONATE: 32,
} as const;

const KEY_BITS = new Map<string, number>([
  ['ArrowUp', InputBits.UP],
  ['KeyW', InputBits.UP],
  ['ArrowDown', InputBits.DOWN],
  ['KeyS', InputBits.DOWN],
  ['ArrowLeft', InputBits.LEFT],
  ['KeyA', InputBits.LEFT],
  ['ArrowRight', InputBits.RIGHT],
  ['KeyD', InputBits.RIGHT],
  ['KeyX', InputBits.BOMB],
  ['KeyZ', InputBits.DETONATE],
]);

export interface Keyboard {
  mask(): number;
  dispose(): void;
}

export function createKeyboard(target: Window = window): Keyboard {
  let mask = 0;

  const onKeyDown = (event: KeyboardEvent) => {
    const bit = KEY_BITS.get(event.code);
    if (bit === undefined) {
      return;
    }
    event.preventDefault();
    mask |= bit;
  };

  const onKeyUp = (event: KeyboardEvent) => {
    const bit = KEY_BITS.get(event.code);
    if (bit === undefined) {
      return;
    }
    event.preventDefault();
    mask &= ~bit;
  };

  const onBlur = () => {
    mask = 0;
  };

  target.addEventListener('keydown', onKeyDown);
  target.addEventListener('keyup', onKeyUp);
  target.addEventListener('blur', onBlur);

  return {
    mask: () => mask,
    dispose: () => {
      target.removeEventListener('keydown', onKeyDown);
      target.removeEventListener('keyup', onKeyUp);
      target.removeEventListener('blur', onBlur);
    },
  };
}
