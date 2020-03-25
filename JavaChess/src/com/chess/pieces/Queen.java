package com.chess.pieces;

import com.chess.player.Alliance;
import com.chess.board.Board;
import com.chess.board.BoardUtils;
import com.chess.board.Moves;
import com.chess.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Queen extends Piece {
  public static final int[]CANDIDATE_MOVES_COORD={7,9,-7,-9,-1,1,8,-8};

  public Queen(Alliance pieceColour,int piecePos) {
    super(PieceType.QUEEN,piecePos, pieceColour);
  }


  @Override
  public String toString() {
    return PieceType.QUEEN.toString();
  }

  @Override
  public Collection<Moves> calculateMoves(Board board) {

    final List<Moves> legalMoves=new ArrayList<>();
    for(int candidateCoordinate:CANDIDATE_MOVES_COORD)
    {
      int candidateDestinationCoord=this.piecePos;
      while(BoardUtils.IsValideCoord(candidateDestinationCoord)){

        if(isFirstColumnExcept(candidateDestinationCoord,candidateCoordinate)||
                isEightColumnExcept(candidateDestinationCoord,candidateCoordinate)) {
          break;
        }


        candidateCoordinate+=candidateDestinationCoord;
        if(BoardUtils.IsValideCoord(candidateDestinationCoord))
        {
          final Tile candidateDestinationTile = board.getTile(candidateDestinationCoord);
          if (!candidateDestinationTile.isOcupied()) {
            legalMoves.add(new Moves.MajorMoves(board,this,candidateDestinationCoord));
          } else {
            final Piece pieceAtDestination = candidateDestinationTile.getPiece();
            final Alliance pieceColour = pieceAtDestination.pieceColour;
            if (this.pieceColour != pieceAtDestination.pieceColour) {
              legalMoves.add(new Moves.AttackMoves(board,this,candidateDestinationCoord,pieceAtDestination));
            } break;
          }

        }
      }
    }


    return legalMoves;
  }


  @Override
  public Queen movedPiece(Moves move) {
    return new Queen(move.getMovedPiece().getPieceColour(),move.getDestinationCoordinate());
  }

  private static boolean isFirstColumnExcept(final int currentPos,final int candidatePos)
  {
    return BoardUtils.FIRST_COLUMN[currentPos]&&(candidatePos==-7||candidatePos==9||candidatePos==-1);
  }

  private static boolean isEightColumnExcept(final int currentPos,final int candidatePos)
  {
    return BoardUtils.EIGHT_COLUMN[currentPos]&&(candidatePos==7||candidatePos==-9||candidatePos==1);

  }
}
