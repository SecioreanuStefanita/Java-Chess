package com.chess.player;

import com.chess.board.Board;
import com.chess.board.Moves;
import com.chess.board.Tile;
import com.chess.pieces.Piece;
import com.chess.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WhitePlayer  extends Player{
  public WhitePlayer(Board board, Collection<Moves> whiteStandardLegalMoves, Collection<Moves> blackStandardLegalMoves) {
    super(board,whiteStandardLegalMoves,blackStandardLegalMoves);
  }

  @Override
  public Collection<Piece> getActivePieces() {
    return board.getWhitePieces();
  }

  @Override
  public Alliance getAlliance() {
    return Alliance.WHITE;
  }

  @Override
  public BlackPlayer getOpponent() {
    return this.board.blackPlayer();
  }

  @Override
  protected Collection<Moves> calculateKingCastles(Collection<Moves> playerLegals, Collection<Moves> opponentsLegals) {
    final List<Moves> kingCastles=new ArrayList<>();

    if(this.playerKing.isFirstMove()&&!this.isInCheck()){
      if(!this.board.getTile(61).isOcupied()&&!this.board.getTile(62).isOcupied()){
        final Tile rookTile=this.board.getTile(63);
        if(rookTile.isOcupied()&&rookTile.getPiece().isFirstMove()){
          if(Player.calculateAttacksOnTile(61,opponentsLegals).isEmpty()&&
          Player.calculateAttacksOnTile(62,opponentsLegals).isEmpty()&&
          rookTile.getPiece().getPieceType().isRook()){
            kingCastles.add(new Moves.KingSideCastleMove(this.board,this.playerKing,62,(Rook)rookTile.getPiece(),rookTile.getTileCoordinate(),61));
          }

        }

      }
      if(!this.board.getTile(59).isOcupied()&&!this.board.getTile(58).isOcupied()&&
      !this.board.getTile(57).isOcupied()){
        final Tile rookTile=this.board.getTile(56);
        if(rookTile.isOcupied()&&rookTile.getPiece().isFirstMove()&&
        Player.calculateAttacksOnTile(58,opponentsLegals).isEmpty()&&
        Player.calculateAttacksOnTile(59,opponentsLegals).isEmpty()&&
        rookTile.getPiece().getPieceType().isRook()){
          kingCastles.add(new Moves.QueenSideCastleMove(this.board,this.playerKing,58,(Rook)rookTile.getPiece(),rookTile.getTileCoordinate(),59));
        }
      }



    }



    return kingCastles;
  }
}
