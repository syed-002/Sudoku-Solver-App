package com.example.sudoku;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class sudokuBoard extends View {
    private final int boardColor;
    private final int cellFillColor;
    private final int cellHighlightColor;

    private final int letterColor;
    private final int letterColorSolve;

    private int cellSize;
    private final Paint boardColorPaint = new Paint();
    private final Paint cellFillColorPaint = new Paint();
    private final Paint cellHighlightColorPaint = new Paint();
    private  final Paint letterPaint = new Paint();
    private final Rect letterPaintBounds = new Rect();


    private final sudokuSolver solver = new sudokuSolver();

    public sudokuBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray x = context.getTheme().obtainStyledAttributes(attrs, R.styleable.sudokuBoard, 0, 0);

        try{
///extracting boardcolor from typed array andc store it in board color

            boardColor = x.getInteger(R.styleable.sudokuBoard_boardColor, 0);
            cellFillColor = x.getInteger(R.styleable.sudokuBoard_cellFillColor, 0);
            cellHighlightColor = x.getInteger(R.styleable.sudokuBoard_cellHighlightColor, 0);
            //extracted the variables from xml and assigned them toi the variables now in java code
            letterColor = x.getInteger(R.styleable.sudokuBoard_letterColor, 0);
            letterColorSolve = x.getInteger(R.styleable.sudokuBoard_letterColorSolve, 0);
        }finally {
            x.recycle(); //free up memory
        }
    }

    @Override
    protected  void onMeasure(int width, int height){
        super.onMeasure(width, height);

        int dimension = Math.min(this.getMeasuredWidth(), this.getMeasuredHeight()); //gets from phone
        cellSize = dimension/9;
        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected  void onDraw(Canvas canvas){
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(16);
        boardColorPaint.setColor(boardColor);
        boardColorPaint.setAntiAlias(true); //lines are crisp

        cellFillColorPaint.setStyle(Paint.Style.FILL);
        cellFillColorPaint.setColor(cellFillColor);
        cellFillColorPaint.setAntiAlias(true);

        cellHighlightColorPaint.setStyle(Paint.Style.FILL);
        cellHighlightColorPaint.setColor(cellHighlightColor);
        cellHighlightColorPaint.setAntiAlias(true);

        letterPaint.setStyle(Paint.Style.FILL);
        letterPaint.setAntiAlias(true);
        letterPaint.setColor(letterColor);

        colorCell(canvas, solver.getSelectedRow(), solver.getSelectedCol());
        canvas.drawRect(0, 0,getWidth(), getHeight(), boardColorPaint);
        drawBoard(canvas);
        drawNumbers(canvas);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public  boolean onTouchEvent(MotionEvent event){
        boolean isValid;

        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            solver.setSelectedRow((int)Math.ceil((y/cellSize)));
            solver.setSelectedCol((int)Math.ceil((x/cellSize)));

            isValid = true;
        }
        else{
            isValid = false;
        }

        return isValid;
    }

    private void drawNumbers(Canvas canvas){

        letterPaint.setTextSize(cellSize);
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(solver.getBoard()[i][j]!=0){
                    String text;
                    text = Integer.toString(solver.getBoard()[i][j]);
                    float width, height;
                    letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                    width = letterPaint.measureText(text);
                    height = letterPaintBounds.height();

                    canvas.drawText(text, (cellSize * j) + ((cellSize-width)/2),
                            (i*cellSize+cellSize)-((cellSize-height)/2), letterPaint);
                    //to place the numbers by user
                }
            }
        }

        //change color of text
        letterPaint.setColor(letterColorSolve);

        //solving
        for (ArrayList<Object> letter: solver.getEmptyBoxIndex()){
            //getting the 2d array from the solver
            int i = (int)letter.get(0);
            int j = (int)letter.get(1);

            String text;
            text = Integer.toString(solver.getBoard()[i][j]);
            float width, height;
            letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
            width = letterPaint.measureText(text);
            height = letterPaintBounds.height();

            canvas.drawText(text, (cellSize * j) + ((cellSize-width)/2),
                    (i*cellSize+cellSize)-((cellSize-height)/2), letterPaint);
            //to place the numbers by user
        }

    }

    private  void colorCell(Canvas canvas, int r, int c){
        if(solver.getSelectedCol()!= -1 && solver.getSelectedRow()!= -1){
            canvas.drawRect((c-1)*cellSize, 0, c*cellSize, cellSize*9, cellHighlightColorPaint );
             //defining boundary for selected col

            canvas.drawRect(0, (r-1)*cellSize, c*cellSize*9, r*cellSize, cellHighlightColorPaint );
            //defining boundary for selected row

            canvas.drawRect((c-1)*cellSize, (r-1)*cellSize, c*cellSize, r*cellSize, cellFillColorPaint );
            //defining boundary for selected cell
        }

        //refresing the sudoku board
        invalidate();
    }

    private  void drawThickLine(){
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(10);
        boardColorPaint.setColor(boardColor);
    }
    private  void drawThinLine(){
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(4);
        boardColorPaint.setColor(boardColor);
    }

    private void drawBoard(Canvas canvas){

        //for col
        for (int i =0; i<10; i++){
            if(i%3==0){
                drawThickLine();
            }
            else{
                drawThinLine();
            }
            canvas.drawLine(cellSize*i, 0, cellSize*i, getWidth(), boardColorPaint);
        }

        //for col
        for (int i =0; i<10; i++){
            if(i%3==0){
                drawThickLine();
            }
            else{
                drawThinLine();
            }
            canvas.drawLine(0, cellSize*i,  getWidth(),cellSize*i,  boardColorPaint);
        }

    }

    public sudokuSolver getSolver(){
        return this.solver;
    }
}
