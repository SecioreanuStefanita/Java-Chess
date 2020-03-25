package com.chess.player;

import com.chess.board.Board;
import com.chess.board.Moves;
import com.chess.board.Tile;
import com.chess.pieces.Piece;
import com.chess.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BlackPlayer extends Player {


  public BlackPlayer(Board board, Collection<Moves> whiteStandardLegalMoves, Collection<Moves> blackStandardLegalMoves) {
super(board,blackStandardLegalMoves,whiteStandardLegalMoves);
  }

  @Override
  public Collection<Piece> getActivePieces() {
    return this.board.getBlackPieces();
  }

  @Override
  public Alliance getAlliance() {
    return Alliance.BLACK;
  }

  @Override
  public WhitePlayer getOpponent() {
    return this.board.whitePlayer();
  }

  @Override
  protected Collection<Moves> calculateKingCastles(Collection<Moves> playerLegals, Collection<Moves> opponentsLegals) {
    final List<Moves> kingCastles=new ArrayList<>();

    if(this.playerKing.isFirstMove()&&!this.isInCheck()){
      if(!this.board.getTile(5).isOcupied()&&!this.board.getTile(6).isOcupied()){
        final Tile rookTile=this.board.getTile(7);
        if(rookTile.isOcupied()&&rookTile.getPiece().isFirstMove()){
          if(Player.calculateAttacksOnTile(5,opponentsLegals).isEmpty()&&
                  Player.calculateAttacksOnTile(6,opponentsLegals).isEmpty()&&
                  rookTile.getPiece().getPieceType().isRook()){
            kingCastles.add(new Moves.KingSideCastleMove(this.board,this.playerKing,6,(Rook)rookTile.getPiece(),rookTile.getTileCoordinate(),5));
          }

        }

      }
      if(!this.board.getTile(1).isOcupied()&&!this.board.getTile(2).isOcupied()&&
              !this.board.getTile(3).isOcupied()){
        final Tile rookTile=this.board.getTile(0);
        if(rookTile.isOcupied()&&rookTile.getPiece().isFirstMove()&&
        Player.calculateAttacksOnTile(2,opponentsLegals).isEmpty()&&
                Player.calculateAttacksOnTile(3,opponentsLegals).isEmpty()&&
                        rookTile.getPiece().getPieceType().isRook()){
          kingCastles.add(new Moves.QueenSideCastleMove(this.board,this.playerKing,2,(Rook)rookTile.getPiece(),rookTile.getTileCoordinate(),3));
        }
      }



    }



    return kingCastles;
  }
}
