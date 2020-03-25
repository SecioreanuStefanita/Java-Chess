package com.chess.pieces;

import com.chess.player.Alliance;
import com.chess.board.Board;
import com.chess.board.BoardUtils;
import com.chess.board.Moves;
import com.chess.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends Piece {
  public static final int[] CANDIDATE_MOVES_COORD = {8, 9, 7, -9, -7, -8, 1, -1};


  public King(Alliance pieceColour,int piecePos) {
    super(PieceType.KING,piecePos, pieceColour);
  }


  @Override
  public King movedPiece(Moves move) {
    return new King(move.getMovedPiece().getPieceColour(),move.getDestinationCoordinate());
  }



  @Override
  public Collection<Moves> calculateMoves(Board board) {

    List<Moves> legalMoves = new ArrayList<>();

    int candidateDestinationCoord;

    for (int currentCandidate : CANDIDATE_MOVES_COORD) {

      candidateDestinationCoord = this.piecePos + currentCandidate;
      if (isFirstColumnExclusion(this.piecePos,currentCandidate)||isEightColumnExclusion(this.piecePos,currentCandidate)) {
        continue;
      }

      if (BoardUtils.IsValideCoord(candidateDestinationCoord)) {
        final Tile candidateDestinationTile = board.getTile(candidateDestinationCoord);
        if (!candidateDestinationTile.isOcupied()) {
          legalMoves.add(new Moves.MajorMoves(board, this, candidateDestinationCoord));
        } else {
          final Piece pieceAtDestination = candidateDestinationTile.getPiece();
          final Alliance pieceColour = pieceAtDestination.pieceColour;
          if (this.pieceColour != pieceColour) {
            legalMoves.add(new Moves.AttackMoves(board, this, candidateDestinationCoord, pieceAtDestination));
          }
        }

      }

    }
    return legalMoves;
  }


  public static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
    return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 ||
            candidateOffset == 7);
  }

  public static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {
    return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1||candidateOffset==9);
  }


  @Override
  public String toString() {
    return PieceType.KING.toString();
  }

}
