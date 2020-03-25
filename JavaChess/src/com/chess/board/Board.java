package com.chess.board;

import com.chess.player.Alliance;
import com.chess.pieces.*;
import com.chess.player.BlackPlayer;
import com.chess.player.Player;
import com.chess.player.WhitePlayer;


import java.util.*;

public class Board {


  private final List<Tile> board;
  private final Collection<Piece> whiteP;
  private final Collection<Piece> blackP;
private final WhitePlayer whitePlayer;
private final BlackPlayer blackPlayer;
private final Player currentPlayer;

  private Board(Builder builder) {
    this.board = createBoard(builder);

    this.whiteP = calculateActivePieces(this.board, Alliance.WHITE);

    this.blackP = calculateActivePieces(this.board, Alliance.BLACK);


    final Collection<Moves> whiteStandardLegalMoves = calculateLegalMoves(this.whiteP);

    final Collection<Moves> blackStandardLegalMoves = calculateLegalMoves(this.blackP);
    this.whitePlayer=new WhitePlayer(this,whiteStandardLegalMoves,blackStandardLegalMoves);
    this.blackPlayer=new BlackPlayer(this,whiteStandardLegalMoves,blackStandardLegalMoves);
  this.currentPlayer=builder.nextMoveMaker.choosePlayer(this.whitePlayer,this.blackPlayer);

  }

  public Player currentPlayer(){
    return this.currentPlayer;
  }


  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
      final String tiletext = this.board.get(i).toString();
      builder.append(String.format("%3s", tiletext));
      if ((i + 1) % BoardUtils.NUM_TILES_PER_ROW == 0) {
        builder.append("\n");
      }

    }
    return builder.toString();
  }

  public Collection<Piece>getBlackPieces(){
    return this.blackP;
  }
  public Collection<Piece>getWhitePieces(){
    return this.whiteP;
  }
  private Collection<Moves> calculateLegalMoves(Collection<Piece> pieces) {

    final List<Moves> legalMoves = new ArrayList<>();
    for (Piece piece : pieces) {
      legalMoves.addAll(piece.calculateMoves(this));
    }
    return legalMoves;
  }

  private Collection<Piece> calculateActivePieces(List<Tile> board, Alliance alliance) {

    final List<Piece> activePieces = new ArrayList<>();

    for (Tile tile : board) {
      if (tile.isOcupied()) {
        Piece piece = tile.getPiece();
        if (piece.getPieceColour() == alliance) {
          activePieces.add(piece);
        }
      }
    }


    return activePieces;
  }

  private static List<Tile> createBoard(final Builder builder) {
    final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
    List<Tile> tileList=new ArrayList<>();
    for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
      tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
      tileList.add(Tile.createTile(i, builder.boardConfig.get(i)));

    }
    return tileList;
  }

  public static Board createStandardBoard() {
    final Builder builder = new Builder();
    // Black Layout

    builder.setPiece(new Rook(Alliance.BLACK, 0));
    builder.setPiece(new Knight(Alliance.BLACK, 1));
    builder.setPiece(new Bishop(Alliance.BLACK, 2));
    builder.setPiece(new Queen(Alliance.BLACK, 3));
    builder.setPiece(new King(Alliance.BLACK, 4));
    builder.setPiece(new Bishop(Alliance.BLACK, 5));
    builder.setPiece(new Knight(Alliance.BLACK, 6));
    builder.setPiece(new Rook(Alliance.BLACK, 7));
    builder.setPiece(new Pawn(Alliance.BLACK, 8));
    builder.setPiece(new Pawn(Alliance.BLACK, 9));
    builder.setPiece(new Pawn(Alliance.BLACK, 10));
    builder.setPiece(new Pawn(Alliance.BLACK, 11));
    builder.setPiece(new Pawn(Alliance.BLACK, 12));
    builder.setPiece(new Pawn(Alliance.BLACK, 13));
    builder.setPiece(new Pawn(Alliance.BLACK, 14));
    builder.setPiece(new Pawn(Alliance.BLACK, 15));
    // White Layout
    builder.setPiece(new Pawn(Alliance.WHITE, 48));
    builder.setPiece(new Pawn(Alliance.WHITE, 49));
    builder.setPiece(new Pawn(Alliance.WHITE, 50));
    builder.setPiece(new Pawn(Alliance.WHITE, 51));
    builder.setPiece(new Pawn(Alliance.WHITE, 52));
    builder.setPiece(new Pawn(Alliance.WHITE, 53));
    builder.setPiece(new Pawn(Alliance.WHITE, 54));
    builder.setPiece(new Pawn(Alliance.WHITE, 55));
    builder.setPiece(new Rook(Alliance.WHITE, 56));
    builder.setPiece(new Knight(Alliance.WHITE, 57));
    builder.setPiece(new Bishop(Alliance.WHITE, 58));
    builder.setPiece(new Queen(Alliance.WHITE, 59));
    builder.setPiece(new King(Alliance.WHITE, 60));
    builder.setPiece(new Bishop(Alliance.WHITE, 61));
    builder.setPiece(new Knight(Alliance.WHITE, 62));
    builder.setPiece(new Rook(Alliance.WHITE, 63));
    //white to move
    builder.setMoveMaker(Alliance.WHITE);

    return builder.build();
  }

  public Iterable<Moves>getAllLegalMoves(){
    List<Moves> allLegalMoves = new ArrayList<>();
    allLegalMoves.addAll(this.whitePlayer.getLegalMoves());
    allLegalMoves.addAll(this.blackPlayer.getLegalMoves());
    return Collections.unmodifiableList(allLegalMoves);
  }

  public WhitePlayer whitePlayer() {
    return this.whitePlayer;
  }

  public BlackPlayer blackPlayer() {
    return this.blackPlayer;
  }


  public static class Builder {
    Map<Integer, Piece> boardConfig;
    Alliance nextMoveMaker;
     Pawn enPassantPawn;

    public Builder() {
      this.boardConfig = new HashMap<>();
    }

    public Builder setPiece(final Piece piece) {
      this.boardConfig.put(piece.getPiecePos(), piece);
      return this;
    }

    public Builder setMoveMaker(final Alliance nextMoveMaker) {
      this.nextMoveMaker = nextMoveMaker;
      return this;
    }

    public Board build() {
      return new Board(this);
    }

    public void setEnPassantPawn(Pawn enPassantPawn) {
      this.enPassantPawn=enPassantPawn;
    }
  }

  public Tile getTile(final int candidateDestinationCoord) {
    return board.get(candidateDestinationCoord);
  }
}
