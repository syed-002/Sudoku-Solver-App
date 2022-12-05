package com.example.sudoku;

import java.util.ArrayList;

public class sudokuSolver {

   static int[][] board;
   ArrayList<ArrayList<Object>> emptyBox;

   int selected_row;
   int selected_col;

    sudokuSolver(){
        selected_row = -1;
        selected_col = -1;

        board = new int[9][9];
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                board[i][j]=0;
            }
        }
        emptyBox = new ArrayList<>();
    }


    public void getEmptyBoxIndexes(){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
               if(this.board[i][j]==0){
                   this.emptyBox.add(new ArrayList<>());
                   this.emptyBox.get(this.emptyBox.size()-1).add(i); //add row
                   this.emptyBox.get(this.emptyBox.size()-1).add(j); //add col
                    //for correct color on the number
               }
            }
        }
    }

    private  boolean check(int row, int col){
        if(this.board[row][col]>0){
            for(int i=0; i<9; i++){
                if(this.board[i][col] == this.board[row][col] && row!=i){
                    return false;
                }

                if(this.board[row][i]==this.board[row][col] && col!=i){
                    return false;
                }
            }
            int sub_x, sub_y;
            sub_x = (row/3)*3;
            sub_y = (col/3)*3;

            for(int i=sub_x; i<sub_x+3; i++){
                for(int j= sub_y; j< sub_y+3; j++){
                    if(this.board[i][j] == this.board[row][col] && row!=i && col!=j){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean rec_solve(sudokuBoard display){
        int row=-1, col=-1; //def val

        for(int i=0; i<9; i++){ //findin empty cell
            for(int j=0; j<9; j++){
                if(this.board[i][j] ==0){
                    row=i;
                    col=j;
                    break;
                }
            }
        }
        if(row==-1 || col==-1){
            return true; //cell is filled
        }
        for(int i=1; i<=9; i++){  //if cell aint filled
            this.board[row][col] = i;
            display.invalidate();

            if(check(row, col)){
                if(rec_solve(display)){
                    return true;
                }
            }

            this.board[row][col]=0; //backtracking
        }
        return false;
    }

    public void resetBoard(){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                board[i][j]=0;
            }
        }
        this.emptyBox = new ArrayList<>();
    }

    public void setNumberPos(int num){
        //updates the number the user wanmts to add to board
        if(this.selected_row!=-1 && this.selected_col!=-1) {
            //click same button twice to remove the number
            if (this.board[this.selected_row-1][selected_col-1] == num) {
                this.board[this.selected_row-1][selected_col-1] = 0;
            } else {
                this.board[this.selected_row-1][selected_col-1] = num;
            }
        }
    }

    public int[][] getBoard(){
         return this.board;
    }

    public ArrayList<ArrayList<Object>> getEmptyBoxIndex(){
        return this.emptyBox;
    }
    public int getSelectedRow(){
        return  selected_row;
    }
    public int getSelectedCol(){
        return  selected_col;
    }

    public  void setSelectedRow(int r){
        selected_row =r;
    }
    public  void setSelectedCol(int r){
        selected_col =r;
    }

}
