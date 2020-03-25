package com.chess.pieces;

import com.chess.player.Alliance;
import com.chess.board.Board;
import com.chess.board.BoardUtils;
import com.chess.board.Moves;
import com.chess.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Rook extends Piece {
  private final static int[] CANDIDATE_MOVE_COORD = {-8, 8, -1, 1};

  public Rook(Alliance pieceColour, int piecePos) {
    super(PieceType.ROOK, piecePos, pieceColour);
  }

  @Override
  public String toString() {
    return PieceType.ROOK.toString();
  }

  @Override
  public Collection<Moves> calculateMoves(Board board) {

    final List<Moves> legalMoves = new ArrayList<>();


    for (final int currentCandiate : CANDIDATE_MOVE_COORD) {
      int candidateDestinationCoord = this.piecePos;
      candidateDestinationCoord = this.piecePos + currentCandiate;
      while (BoardUtils.IsValideCoord(candidateDestinationCoord)) {
        if (BoardUtils.IsValideCoord(candidateDestinationCoord)) {
          if (isFirstColumnExclusion(candidateDestinationCoord, currentCandiate) ||
                  isEightColumnExclusion(candidateDestinationCoord, currentCandiate)) {

            break;
          }
          final Tile candidateDestinationTile = board.getTile(candidateDestinationCoord);
          if (!candidateDestinationTile.isOcupied()) {
            legalMoves.add(new Moves.MajorMoves(board, this, candidateDestinationCoord));

          } else {
            final Piece pieceAtDestination = candidateDestinationTile.getPiece();
            final Alliance pieceColour = pieceAtDestination.pieceColour;
            if (this.pieceColour != pieceAtDestination.pieceColour) {

              legalMoves.add(new Moves.AttackMoves(board, this, candidateDestinationCoord, pieceAtDestination));
            }
            break;
          }

        }
      }
    }


    return legalMoves;
  }

  @Override
  public Rook movedPiece(Moves move) {
    return new Rook(move.getMovedPiece().getPieceColour(), move.getDestinationCoordinate());
  }


  public static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
    return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1);
  }

  public static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {

    return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == 1);
  }

}
