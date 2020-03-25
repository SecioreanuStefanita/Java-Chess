package com.chess.pieces;

import com.chess.player.Alliance;
import com.chess.board.Board;
import com.chess.board.BoardUtils;
import com.chess.board.Moves;
import com.chess.board.Moves.AttackMoves;
import com.chess.board.Moves.MajorMoves;
import com.chess.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight extends Piece {

  private final static int[] CANDIDATE_MOVE_COORD = {-17, -15, -10, -6, 6, 10, 15, 17};

  public Knight(final Alliance pieceColour,final int piecePos) {
    super(PieceType.KNIGHT,piecePos, pieceColour);
  }

  @Override
  public Collection<Moves> calculateMoves(Board board) {

    final List<Moves> legalMoves = new ArrayList<>();
    int candidateDestinationCoord;
    for (final int currentCandiate : CANDIDATE_MOVE_COORD) {
      candidateDestinationCoord = this.piecePos + currentCandiate;
      if (BoardUtils.IsValideCoord(candidateDestinationCoord)) {

        if (isFirstColumnExclusion(this.piecePos, currentCandiate)
                || isSecondColumnExclusion(this.piecePos, currentCandiate) ||
                isSeventhColumnExclusion(this.piecePos, currentCandiate) ||
                isEightColumnExclusion(this.piecePos, currentCandiate)) {

          continue;
        }
        final Tile candidateDestinationTile = board.getTile(candidateDestinationCoord);
        if (!candidateDestinationTile.isOcupied()) {
          legalMoves.add(new MajorMoves(board, this, candidateDestinationCoord));

        } else {
          final Piece pieceAtDestination = candidateDestinationTile.getPiece();
          final Alliance pieceColour = pieceAtDestination.pieceColour;
          if (this.pieceColour != pieceAtDestination.pieceColour) {
            legalMoves.add(new AttackMoves(board, this, candidateDestinationCoord, pieceAtDestination));

          }
        }

      }

    }
    return legalMoves;
  }

  @Override
  public Knight movedPiece(Moves move) {
    return new Knight(move.getMovedPiece().getPieceColour(),move.getDestinationCoordinate());
  }



  public static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
    return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 ||
            candidateOffset == 6 || candidateOffset == 15);
  }

  public static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
    return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);
  }

  public static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
    return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == 10 || candidateOffset == -6);
  }

  public static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {

    return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == -15 || candidateOffset == -6 ||
            candidateOffset == 10 || candidateOffset == 17);
  }


  @Override
  public String toString() {
    return PieceType.KNIGHT.toString();
  }

}
