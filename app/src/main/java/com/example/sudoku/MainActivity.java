package com.example.sudoku;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar; //for actionbar

public class MainActivity extends AppCompatActivity {

    private sudokuBoard gameBoard;
    private sudokuSolver gameBoardSolver;
    private Button solveBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // Define ActionBar object
//        ActionBar actionBar;
//        actionBar = getSupportActionBar();
//
//        // Define ColorDrawable object and parse color
//        // using parseColor method
//        // with color hash code as its parameter
//        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(""));
//
//        // Set BackgroundDrawable
//        assert actionBar != null;
//        actionBar.setBackgroundDrawable(colorDrawable);

        //code written from here.. linking them with oncreate.. the above variables
        gameBoard = findViewById(R.id.sudokuBoard);
        gameBoardSolver = gameBoard.getSolver();
        solveBTN = findViewById(R.id.solveButton);






    }

    public void BTNOnePress(View view){
        gameBoardSolver.setNumberPos(1);
        gameBoard.invalidate(); //refreshes or redraws the board
    }
    public void BTNTwoPress(View view){
        gameBoardSolver.setNumberPos(2);
        gameBoard.invalidate(); //refreshes or redraws the board
    }
    public void BTNThreePress(View view){
        gameBoardSolver.setNumberPos(3);
        gameBoard.invalidate(); //refreshes or redraws the board
    }
    public void BTNFourPress(View view){
        gameBoardSolver.setNumberPos(4);
        gameBoard.invalidate(); //refreshes or redraws the board
    }
    public void BTNFivePress(View view){
        gameBoardSolver.setNumberPos(5);
        gameBoard.invalidate(); //refreshes or redraws the board
    }
    public void BTNSixPress(View view){
        gameBoardSolver.setNumberPos(6);
        gameBoard.invalidate(); //refreshes or redraws the board
    }
    public void BTNSevenPress(View view){
        gameBoardSolver.setNumberPos(7);
        gameBoard.invalidate(); //refreshes or redraws the board
    }
    public void BTNEightPress(View view){
        gameBoardSolver.setNumberPos(8);
        gameBoard.invalidate(); //refreshes or redraws the board
    }
    public void BTNNinePress(View view){
        gameBoardSolver.setNumberPos(9);
        gameBoard.invalidate(); //refreshes or redraws the board
    }

    public void solve(View view){
        if(solveBTN.getText().toString().equals(getString(R.string.solve))){
            solveBTN.setText(getString(R.string.clear));

            gameBoardSolver.getEmptyBoxIndexes(); // gonna populate the empty cells

            solveBoardThread sbt = new solveBoardThread();

            new Thread(sbt).start(); //so we can still intereact with app
            gameBoard.invalidate();

        }
        else{
            solveBTN.setText(getString(R.string.solve));

            gameBoardSolver.resetBoard();
            gameBoard.invalidate();
        }
    }

    class solveBoardThread implements  Runnable{
        @Override
        public void run(){
            gameBoardSolver.rec_solve(gameBoard);
        }
    }
}