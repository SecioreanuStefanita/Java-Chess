package com.chess.player;

import com.chess.player.BlackPlayer;
import com.chess.player.Player;
import com.chess.player.WhitePlayer;

public enum Alliance {
  WHITE {
    @Override
    public int getDirection() {
      return +1;
    }
    @Override
    public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
      return whitePlayer;
    }
  },
  BLACK {
    @Override
    public int getDirection() {
      return -1;
    }

    @Override
    public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
      return blackPlayer;
    }
  };

  abstract public int getDirection();

  public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
}
