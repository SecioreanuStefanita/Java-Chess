package com.chess;

import com.chess.GUI.Table;
import com.chess.board.Board;
public class JChess {

  public static void main(String[] args) {

    Board board= Board.createStandardBoard();

    System.out.println(board);
    Table table=new Table();

  }

}
