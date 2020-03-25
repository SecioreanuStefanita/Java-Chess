package com.chess.player;

public enum MoveStatus {
  DONE {
    @Override
    public boolean isDone() {
      return true;
    }
  },
  ILLEGAL_MOVE {
    @Override
    public boolean isDone() {
      System.out.println("MISCARE ILEGALA");
      return false;
    }
  },
  LEAVES_PLAYER_IN_CHECK {
    @Override
   public boolean isDone() {
      System.out.println("e In sah");
      return false;
    }
  };
 public abstract boolean isDone();
}
