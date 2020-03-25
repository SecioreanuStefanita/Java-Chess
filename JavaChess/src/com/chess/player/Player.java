package com.chess.player;

import com.chess.board.Board;
import com.chess.board.Moves;
import com.chess.pieces.King;
import com.chess.pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {


  protected final Board board;
  protected final King playerKing;
  protected final Collection<Moves> legalMoves;
  private final boolean isInCheck;

  public Player(Board board, Collection<Moves> legalMoves, Collection<Moves> oponentMoves) {
    this.board = board;
    this.playerKing = establishKing();
    legalMoves.addAll(calculateKingCastles(legalMoves, oponentMoves));
    this.legalMoves = legalMoves;
    this.isInCheck = !calculateAttacksOnTile(this.playerKing.getPiecePos(), oponentMoves).isEmpty();


  }

  public King getPlayerKing(){
    return this.playerKing;
  }

  public Collection<Moves>getLegalMoves(){
    return this.legalMoves;
  }
  protected static Collection<Moves> calculateAttacksOnTile(int piecePos, Collection<Moves> oponentMoves) {
    final List<Moves> attackMoves = new ArrayList<>();
    for (final Moves moves : oponentMoves) {
      if (piecePos == moves.getDestinationCoordinate()) {
        attackMoves.add(moves);
      }
    }
    return attackMoves;
  }


  private King establishKing() {
    for (final Piece piece : getActivePieces()) {
      if (piece.getPieceType().isKing()) {
        return (King) piece;
      }
    }
    throw new RuntimeException("Establish King EXECPTION");
  }


  public boolean isMoveLegal(final Moves move) {
    return this.legalMoves.contains(move);
  }

  public boolean isInCheck() {
    return this.isInCheck;
  }

  public boolean isInCheckMate() {
    return this.isInCheck && !hasEscapeMoves();
  }

  protected boolean hasEscapeMoves() {
    for (final Moves move : this.legalMoves) {
      final MoveTransition transition = makeMove(move);
      if (transition.getMoveStatus().isDone()) {
        return true;
      }
    }
    return false;
  }

  public boolean isInStaleMate() {

    return this.isInCheck && !hasEscapeMoves();
  }

  public boolean isCastled() {
    return false;
  }

  public MoveTransition makeMove(final Moves move) {
    if (!this.legalMoves.contains(move)) {
      return new MoveTransition(this.board, this.board, move, MoveStatus.ILLEGAL_MOVE);
    }
    final Board transitionedBoard = move.execute();
    if(transitionedBoard.currentPlayer().getOpponent().isInCheck()) {
      return new MoveTransition(this.board, this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
    }
    return transitionedBoard.currentPlayer().getOpponent().isInCheck() ?
            new MoveTransition(this.board, this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK) :
            new MoveTransition(this.board, transitionedBoard, move, MoveStatus.DONE);
  }

  public abstract Collection<Piece> getActivePieces();

  public abstract Alliance getAlliance();

  public abstract Player getOpponent();

  protected abstract Collection<Moves>calculateKingCastles(Collection<Moves> playerLegals,
                                                           Collection<Moves>opponentsLegals);


}
