package com.chess.board;

import com.chess.pieces.Pawn;
import com.chess.pieces.Piece;
import com.chess.pieces.Rook;

public abstract class Moves {


  final Board board;
  final Piece movedPiece;
  final int destinationCoord;
  public static final Moves NULL_MOVE = new NullMove();

  public Moves(Board board, Piece movedPiece, int destinationCoord) {
    this.board = board;
    this.movedPiece = movedPiece;
    this.destinationCoord = destinationCoord;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.destinationCoord;
    result = prime * result + this.movedPiece.hashCode();
    return result;
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Moves)) {
      return false;
    }
    final Moves otherMove = (Moves) other;
    return getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
            getMovedPiece().equals(otherMove.getMovedPiece());
  }

  public int getCurrentCoordinate() {
    return this.movedPiece.getPiecePos();
  }

  public int getDestinationCoordinate() {
    return this.destinationCoord;
  }

  public Board execute() {
    final Board.Builder builder = new Board.Builder();
    for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
      if (this.movedPiece.equals(piece)) {
        builder.setPiece(piece);
      }
    }
    for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
      builder.setPiece(piece);
    }
    builder.setPiece(this.movedPiece.movedPiece(this));
    builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
    return builder.build();
  }


  public static final class MajorMoves extends Moves {
    public MajorMoves(Board board, Piece movedPiece, int destinationCoord) {
      super(board, movedPiece, destinationCoord);
    }

  }

  public Piece getMovedPiece() {
    return this.movedPiece;
  }

  public boolean isAttack() {
    return false;
  }

  public boolean isCastlingMove() {
    return false;
  }

  public Piece getAttackedPiece() {
    return null;
  }

  public static class AttackMoves extends Moves {

    final Piece attackedPiece;

    @Override
    public Board execute() {
      return null;
    }


    public AttackMoves(Board board, Piece movedPiece, int destinationCoord, Piece attackedPiece) {
      super(board, movedPiece, destinationCoord);
      this.attackedPiece = attackedPiece;
    }

    @Override
    public boolean isAttack() {
      return true;
    }

    @Override
    public Piece getAttackedPiece() {
      return this.attackedPiece;
    }

    @Override
    public int hashCode() {
      return this.attackedPiece.hashCode() + super.hashCode();
    }

    @Override
    public boolean equals(Object other) {
      if (this == other) {
        return true;
      }
      if (!(other instanceof AttackMoves)) {
        return false;
      }
      final AttackMoves otherAttackMove = (AttackMoves) other;
      return super.equals(otherAttackMove) && getAttackedPiece() == otherAttackMove.getAttackedPiece();
    }
  }

  public static final class PawnMove extends Moves {
    public PawnMove(Board board, Piece movedPiece, int destinationCoord) {
      super(board, movedPiece, destinationCoord);
    }

  }

  public static class PawnAttackMove extends AttackMoves {
    public PawnAttackMove(Board board, Piece movedPiece, int destinationCoord, Piece attackedPiece) {
      super(board, movedPiece, destinationCoord, attackedPiece);
    }

  }

  public static class PawnEnPassantAttackMove extends PawnAttackMove {
    public PawnEnPassantAttackMove(Board board, Piece movedPiece, int destinationCoord, Piece attackedPiece) {
      super(board, movedPiece, destinationCoord, attackedPiece);
    }

  }

  public static final class PawnJump extends Moves {
    public PawnJump(Board board, Piece movedPiece, int destinationCoord) {
      super(board, movedPiece, destinationCoord);
    }

    @Override
    public Board execute() {
      final Board.Builder builder = new Board.Builder();
      for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
        if (!this.movedPiece.equals(piece)) {
          builder.setPiece(piece);
        }
      }
      for (Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
        builder.setPiece(piece);
      }
      final Pawn movedPawn = (Pawn) this.movedPiece.movedPiece(this);
      builder.setPiece(movedPiece);
      builder.setEnPassantPawn(movedPawn);
      builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
      return builder.build();
    }
  }

  static abstract class CastleMove extends Moves {

    protected final Rook castleRook;
    protected final int castleRookStart;
    protected final int castleRookDestination;


    public CastleMove(Board board, Piece movedPiece, int destinationCoord
            , Rook castleRook, int castleRookStart, int castleRookDestination) {
      super(board, movedPiece, destinationCoord);
      this.castleRook = castleRook;
      this.castleRookStart = castleRookStart;
      this.castleRookDestination = castleRookDestination;
    }

    public Rook getCastleRook() {
      return this.castleRook;
    }

    @Override
    public boolean isCastlingMove() {
      return true;
    }

    @Override
    public Board execute() {

      final Board.Builder builder = new Board.Builder();
      for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
        if (!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)) {
          builder.setPiece(piece);
        }
      }
      for (Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
        builder.setPiece(piece);
      }
      builder.setPiece(this.movedPiece.movedPiece(this));
      builder.setPiece(new Rook(this.castleRook.getPieceColour(), this.castleRookDestination));
      builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
      return builder.build();
    }


  }

  public static final class KingSideCastleMove extends CastleMove {
    public KingSideCastleMove(Board board, Piece movedPiece, int destinationCoord, Rook castleRook, int castleRookStart, int castleRookDestination) {
      super(board, movedPiece, destinationCoord, castleRook, castleRookStart, castleRookDestination);
    }

    @Override
    public String toString() {
      return "0-0";
    }
  }

  public static final class QueenSideCastleMove extends CastleMove {
    public QueenSideCastleMove(Board board, Piece movedPiece, int destinationCoord, Rook castleRook, int castleRookStart, int castleRookDestination) {
      super(board, movedPiece, destinationCoord, castleRook, castleRookStart, castleRookDestination);
    }

    @Override
    public String toString() {
      return "0-0-0";
    }
  }

  public static final class NullMove extends Moves {
    public NullMove() {
      super(null, null, -1);
    }

    @Override
    public Board execute() {
      throw new RuntimeException("NULL MOVE");
    }

  }

  public static class MoveFactory {
    private MoveFactory() {
      throw new RuntimeException("Not Instanciable");
    }

    public static Moves createMove(Board board, int currentCoordinate, int destinationCoordinate) {
      for (Moves move : board.getAllLegalMoves()) {
        if (move.getCurrentCoordinate() == currentCoordinate &&
                move.getDestinationCoordinate() == destinationCoordinate) {
          return move;
        }
      }
      return NULL_MOVE;
    }
  }

}
