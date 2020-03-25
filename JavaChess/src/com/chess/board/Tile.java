package com.chess.board;

import com.chess.player.Alliance;
import com.chess.pieces.Piece;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

 protected final int coordinate;

 public static final Map<Integer,EmptyTile>EMPTY_TILES=crateAllEmtyTiles();

  private static Map<Integer, EmptyTile> crateAllEmtyTiles() {
    final Map<Integer,EmptyTile> emptyTileMap=new HashMap<>();
    for (int i=0;i<BoardUtils.NUM_TILES;i++)
    {
      emptyTileMap.put(i,new EmptyTile(i));
    }
    return Collections.unmodifiableMap(emptyTileMap);
  }
  public static Tile createTile(final int tileCoord,final Piece piece)
  {
    return piece!=null?new OccupiedTile(tileCoord,piece):EMPTY_TILES.get(tileCoord);
  }

  private Tile(int coordinate) {
    this.coordinate = coordinate;
  }


  public abstract boolean isOcupied();

  public abstract Piece getPiece();
  public int getTileCoordinate()
  {
    return this.coordinate;
  }

  public static final class EmptyTile extends Tile {



    @Override
    public String toString() {
      return "-";
    }

    private EmptyTile(final int coordinate) {
      super(coordinate);
    }

    @Override
    public boolean isOcupied() {
      return false;
    }

    @Override
    public Piece getPiece() {
      return null;
    }
  }
  public static final class OccupiedTile extends Tile{
   private final Piece piece;


    @Override
    public String toString() {
        if(getPiece().getPieceColour()== Alliance.BLACK){
          return getPiece().toString().toLowerCase();
        }
        else return getPiece().toString();
    }


    private OccupiedTile(int coordinate, Piece piece) {
      super(coordinate);
      this.piece = piece;
    }

    @Override
    public boolean isOcupied() {
      return true;
    }

    @Override
    public Piece getPiece() {
      return this.piece;
    }
  }


}
