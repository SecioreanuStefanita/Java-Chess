package com.chess.pieces;

import com.chess.player.Alliance;
import com.chess.board.Board;
import com.chess.board.Moves;

import java.util.Collection;

public abstract class Piece {
  protected final PieceType pieceType;
protected final int piecePos;
protected final Alliance pieceColour;
protected final boolean isFirstMove;
private final int cachedHashCode;
Piece(final PieceType pieceType,final int piecePos,final Alliance pieceColour)
{
  this.pieceColour=pieceColour;
  this.piecePos=piecePos;
  this.isFirstMove=false;
  this.pieceType=pieceType;
  this.cachedHashCode=computeHashCode();
}
public int computeHashCode(){
  int result=pieceType.hashCode();
  result=31*result+pieceColour.hashCode();
  result=31*result+piecePos;
  result=31*result+(isFirstMove?1:0);
  return result;
}

public boolean isFirstMove(){
  return this.isFirstMove;
}

public Alliance getPieceColour(){
  return this.pieceColour;
}

public abstract Collection<Moves> calculateMoves(final Board board);

  public int getPiecePos() {
    return this.piecePos;
  }
  public PieceType getPieceType(){
    return this.pieceType;
  }


  @Override
  public boolean equals(Object other) {
    if(this==other){
      return true;
    }
    if(!(other instanceof Piece)){
      return false;
    }
    final Piece otherPiece=(Piece)other;
    return piecePos==otherPiece.getPiecePos() && pieceType==otherPiece.getPieceType() &&
            pieceColour==otherPiece.pieceColour && isFirstMove==otherPiece.isFirstMove();
  }

  @Override
  public int hashCode() {
    return this.cachedHashCode;
  }

  public abstract Piece movedPiece(Moves move);


  public enum PieceType{
    PAWN("P") {
      @Override
      public boolean isKing() {
        return false;
      }
      public boolean isRook() {
        return false;
      }
    },
    KNIGHT("N") {
      @Override
      public boolean isKing() {
        return false;
      }
      public boolean isRook() {
        return false;
      }
    },
    QUEEN("Q") {
      @Override
      public boolean isKing() {
        return false;
      }
      public boolean isRook() {
        return false;
      }
    },
    KING("K") {
      @Override
      public boolean isKing() {
        return true;
      }
      public boolean isRook() {
        return false;
      }
    },
    ROOK("R") {
      @Override
      public boolean isKing() {
        return false;
      }
      public boolean isRook() {
        return true;
      }
    },
    BISHOP("B") {
      @Override
      public boolean isKing() {
        return false;
      }
      public boolean isRook() {
        return false;
      }
    };
    private String pieceName;
    PieceType(final String pieceName){
      this.pieceName=pieceName;
    }


    @Override
    public String toString() {
      return this.pieceName;
    }

    public abstract boolean isKing();

    public abstract boolean isRook();
  }
}
