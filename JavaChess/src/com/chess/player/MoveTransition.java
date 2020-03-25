package com.chess.player;

import com.chess.board.Board;
import com.chess.board.Moves;

public class MoveTransition {

  private final Board fromBoard;
  private final Board toBoard;
  private final Moves transitionMove;
  private final MoveStatus moveStatus;

  public MoveTransition(Board fromBoard,Board toBoard, Moves transitionMove, MoveStatus moveStatus) {
   this.fromBoard=fromBoard;
   this.toBoard=toBoard;
   this.transitionMove=transitionMove;
    this.moveStatus = moveStatus;

  }

  public MoveStatus getMoveStatus(){
    return this.moveStatus;

  }

  public Board getToBoard() {
    return this.toBoard;
  }

}
