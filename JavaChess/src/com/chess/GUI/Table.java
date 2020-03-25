package com.chess.GUI;

import com.chess.board.Board;
import com.chess.board.BoardUtils;
import com.chess.board.Moves;
import com.chess.board.Tile;
import com.chess.pieces.Piece;
import com.chess.player.MoveTransition;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {

  private Board chessBoard;
  private final JFrame gameFrame;
  private final BoardPannel boardPannel;
  private static final Dimension BOARD_PANNEL_DIM =new Dimension(400,350) ;
  private final static Dimension OUTER_FRAME_DIM=new Dimension(600,600);
  private final static Dimension TILE_PANNEL_DIM=new Dimension(10,10);
private static String defaultPieceImagesPath="art/pieces/plain/";
private Tile sourceTile;
private Tile destinationTile;
private Piece humanMovedPiece;

  private final Color lightTileColor=Color.decode("#F2AA4C");
  private final Color darkTileColor=Color.decode("#101820");

  public Table(){
    this.gameFrame=new JFrame("My Chess Game");
    this.gameFrame.setSize(OUTER_FRAME_DIM);
    this.chessBoard=Board.createStandardBoard();
    this.gameFrame.setLayout(new BorderLayout());
    this.boardPannel=new BoardPannel();
    this.gameFrame.add(this.boardPannel,BorderLayout.CENTER);
    this.gameFrame.setVisible(true);
  }


  private class BoardPannel extends JPanel{
    final List<TilePannel> boardTiles;

    BoardPannel(){
      super(new GridLayout(8,8));
      this.boardTiles=new ArrayList<>();
      for(int i=0;i< BoardUtils.NUM_TILES;i++){
        final TilePannel tilePannel=new TilePannel(this,i);
        this.boardTiles.add(tilePannel);
        add(tilePannel);
      }
      setPreferredSize(BOARD_PANNEL_DIM);
      validate();


    }

    public void drawBoard(Board board) {
      removeAll();
      for(TilePannel tilePannel:boardTiles){
        tilePannel.drawTile(board);
        add(tilePannel);
      }
      validate();
      repaint();
    }
  }
  private class TilePannel extends JPanel{
  private final int tileId;
  TilePannel(BoardPannel boardPannel,final int tileId){
    super(new GridBagLayout());
    this.tileId=tileId;
    setPreferredSize(TILE_PANNEL_DIM);
    assignTileColour();
    assignTilePieceIcon(chessBoard);
    addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if(isRightMouseButton(e)) {

          sourceTile = null;
          humanMovedPiece = null;

        }else if(isLeftMouseButton(e)){


          if(sourceTile==null) {
            sourceTile=chessBoard.getTile(tileId);
            humanMovedPiece=sourceTile.getPiece();
            if(humanMovedPiece==null) {
              sourceTile=null;
            }
          }
          else {
            destinationTile=chessBoard.getTile(tileId);
            final Moves move = Moves.MoveFactory.createMove(chessBoard, sourceTile.getPiece().getPiecePos(),
                    tileId);
            final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
            if (transition.getMoveStatus().isDone()) {
              System.out.println("miscare facuta" );
              chessBoard = transition.getToBoard();

              //add move to move log
            }
            sourceTile=null;
            humanMovedPiece=null;

          }

          SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

              System.out.println("desenez");
              boardPannel.drawBoard(chessBoard);
            }
          });
        }

      }

      @Override
      public void mousePressed(MouseEvent e) {

      }

      @Override
      public void mouseReleased(MouseEvent e) {

      }

      @Override
      public void mouseEntered(MouseEvent e) {

      }

      @Override
      public void mouseExited(MouseEvent e) {

      }
    });
    validate();



  }

  private void assignTilePieceIcon(final Board board){
    this.removeAll();
    if(board.getTile(this.tileId).isOcupied()){
      try {
        final BufferedImage image= ImageIO.read(new File(defaultPieceImagesPath+board.getTile(this.tileId).getPiece().getPieceColour().toString().substring(0,1)+board.getTile(this.tileId).getPiece().toString()+".gif"));
        add(new JLabel(new ImageIcon(image)));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

    private void assignTileColour() {
      boolean isLight = ((tileId + tileId / 8) % 2 == 0);
      this.setBackground(isLight ? lightTileColor : darkTileColor);
    }

  public  void drawTile(final Board board) {
      assignTileColour();
      assignTilePieceIcon(board);
      validate();
      repaint();
    }
  }

}
