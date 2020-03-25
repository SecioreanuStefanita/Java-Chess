package com.chess.pieces;

import com.chess.player.Alliance;
import com.chess.board.Board;
import com.chess.board.BoardUtils;
import com.chess.board.Moves;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece{
  public static final int[]CANDIDATE_MOVES_COORD={8,16,7,9};

  @Override
  public String toString() {
    return PieceType.PAWN.toString();
  }

  public Pawn( Alliance pieceColour,int piecePos) {
    super(PieceType.PAWN,piecePos, pieceColour);
  }

  @Override
  public Collection<Moves> calculateMoves(Board board) {

    final List<Moves>legalMoves=new ArrayList<>();

    for(final int currentCandidate:CANDIDATE_MOVES_COORD)
    {
      int candidateDestCoord=this.piecePos+(currentCandidate*this.pieceColour.getDirection());
    if(!BoardUtils.IsValideCoord(candidateDestCoord)){
      continue;
    }
    if(currentCandidate==8&&!board.getTile(candidateDestCoord).isOcupied()){
      legalMoves.add(new Moves.MajorMoves(board,this,candidateDestCoord));
    }else if(currentCandidate==16&&this.isFirstMove()&&(BoardUtils.SEVENTH_RANK[this.piecePos]&&this.pieceColour==Alliance.BLACK)||
            (BoardUtils.SECOND_RANK[this.piecePos]&&this.pieceColour==Alliance.WHITE)){

      final int isFirstOpened=this.piecePos+(this.pieceColour.getDirection()*8);
      if (!board.getTile(isFirstOpened).isOcupied()&&!board.getTile(candidateDestCoord).isOcupied()){
      }


    }else if (currentCandidate==7&&!((BoardUtils.EIGHT_COLUMN[this.piecePos]&&this.pieceColour==Alliance.WHITE)||
            (BoardUtils.FIRST_COLUMN[this.piecePos]&&this.pieceColour==Alliance.BLACK))){
      if(board.getTile(candidateDestCoord).isOcupied()){
        Piece pieceAttacked= board.getTile(candidateDestCoord).getPiece();
        if(this.pieceColour!=pieceAttacked.pieceColour)
        {
          legalMoves.add(new Moves.MajorMoves(board,this,candidateDestCoord));

        }
      }

    }else if(currentCandidate==9&&!((BoardUtils.FIRST_COLUMN[this.piecePos]&&this.pieceColour==Alliance.WHITE)||
            (BoardUtils.EIGHT_COLUMN[this.piecePos]&&this.pieceColour==Alliance.BLACK))){
      if(board.getTile(candidateDestCoord).isOcupied()){
        Piece pieceAttacked= board.getTile(candidateDestCoord).getPiece();
        if(this.pieceColour!=pieceAttacked.pieceColour)
        {
          legalMoves.add(new Moves.MajorMoves(board,this,candidateDestCoord));

        }
      }

    }

    }
    return legalMoves;
  }
  @Override
  public Pawn movedPiece(Moves move) {
    return new Pawn(move.getMovedPiece().getPieceColour(),move.getDestinationCoordinate());
  }

}


