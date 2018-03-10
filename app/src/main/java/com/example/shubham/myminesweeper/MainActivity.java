package com.example.shubham.myminesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    int size = 12;
    int minenumber=15;
    minebutton board[][];
    minebutton button;
    ImageButton reset;
    LinearLayout rootlayout;
    TextView textView,textView1;
    int flag;
    int i,j=0;
    int indexI;
    int indexJ;
    int score=0;
    int total_score=0;
    int flagCount=15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        intent.getStringExtra("intentname");

        rootlayout = findViewById(R.id.rootlayout);
        textView=findViewById(R.id.urscore);
        textView1=findViewById(R.id.flagCount);
        reset=findViewById(R.id.reset);
        setUpBoard();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.easy) {
            size = 12;
            minenumber=15;
            flagCount=15;
            textView1.setText(flagCount+"");
            setUpBoard();
        }
        else if (item.getItemId() == R.id.medium) {
            size = 14;
            minenumber=25;
            flagCount=25;
            textView1.setText(flagCount+"");
            setUpBoard();
        }
        else if (item.getItemId()==R.id.hard){
            size = 16;
            minenumber=30;
            flagCount=30;
            textView1.setText(flagCount+"");
            setUpBoard();
        }
        return super.onOptionsItemSelected(item);

    }
    private void setUpBoard() {
        board =new minebutton[size][size];
        flag=0;
        score=0;
        rootlayout.removeAllViews();
        for(i = 0;i<size;i++){

            LinearLayout rowLayout = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);
            rowLayout.setLayoutParams(params);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            for(j = 0;j<size-6;j++){
                minebutton button = new minebutton(this);
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
                button.setLayoutParams(buttonParams);
                reset.setOnClickListener(this);
                button.setOnClickListener(this);
                button.setOnLongClickListener(this);
                button.setIndex(i,j);
                board[i][j] = button;
                rowLayout.addView(button);
            }
            rootlayout.addView(rowLayout);
        }
        Toast.makeText(this,"Best of  Luck!!",Toast.LENGTH_SHORT).show();
    }
    public void generate(int minenumber,final int size,int indexI,int indexJ){
        Random r=new Random();
        while(minenumber!=0){
            i=r.nextInt(size);
            j=r.nextInt(size-6);
            if (board[i][j].getButtonValue()==-1)
                continue;
            else if (i==indexI && j==indexJ)
                continue;
            else {
                board[i][j].setButtonValue(-1);
                minenumber--;
            }
        }

    }

    @Override
    public void onClick(View view) {
        textView1.setText(flagCount+"");
        if(view.getId() == R.id.reset)
            resetGame();
        else {
            button = (minebutton) view;
            if (button.flag == false && button.isEnabled()) {

                indexI = button.getIndexI();
                indexJ = button.getIndexJ();
                if (flag == 0) {
                    textView1.setText(flagCount+"");
                    generate(minenumber, size, indexI, indexJ);
                    calculateNeighbours();
                    flag++;
                }
                button.setText(button.buttonValue + "");
                button.setBackgroundResource(R.drawable.revealed_button);
                button.setEnabled(false);
                if (button.buttonValue>=0)
                    score=score+button.buttonValue;
                textView.setText(score+"");
                checkIfGameWon();
                revealButton(button.buttonValue, indexI, indexJ);
            }
        }
    }

    private void checkIfGameWon() {
        if (total_score==score && flagCount==0)
            Toast.makeText(this,"You Won",Toast.LENGTH_LONG).show();
    }

    private void resetGame() {
        flagCount=minenumber;
        flag=0;
        score=0;
        total_score=0;
        textView.setText(score+"");
        textView1.setText(flagCount+"");
        setUpBoard();
    }

    public void calculateNeighbours() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size - 6; y++) {
                board[x][y].buttonValue = (getneighbour(board, x, y));
                if (board[x][y].buttonValue>=0)
                total_score=total_score+board[x][y].buttonValue;
            }
        }
    }


    private int getneighbour(minebutton board[][],int i,int j){
        int count=0;
        if (board[i][j].getButtonValue()==-1) {
            return -1;
        }
        else {

            if (isMineInBound(i - 1, j - 1)) {
                if (board[i - 1][j - 1].getButtonValue() == -1) {
                    count++;
                }
            }

            if (isMineInBound(i, j - 1)) {
                if (board[i][j - 1].getButtonValue() == -1) {
                    count++;
                }
            }
            if (isMineInBound(i + 1, j - 1)) {
                if (board[i + 1][j - 1].getButtonValue() == -1) {
                    count++;
                }
            }
            if (isMineInBound(i - 1, j)) {
                if (board[i - 1][j].getButtonValue() == -1) {
                    count++;
                }
            }
            if (isMineInBound( i + 1, j)) {
                if (board[i + 1][j].getButtonValue() == -1) {
                    count++;
                }
            }
            if (isMineInBound(i - 1, j + 1)) {
                if (board[i - 1][j + 1].getButtonValue() == -1) {
                    count++;
                }
            }
            if (isMineInBound(i, j + 1)) {
                if (board[i][j + 1].getButtonValue() == -1) {
                    count++;
                }
            }
            if (isMineInBound(i + 1, j + 1)) {
                if (board[i + 1][j + 1].getButtonValue() == -1) {
                    count++;
                }
            }
        }
        return count;

    }
    private boolean isMineInBound(int i,int j) {
        if (i >= 0 && j >= 0 && i < size && j < size - 6) {

            return true;
        }
        else
            return false;
    }
    public void revealButton(int button,int indexI,int indexJ){
        if (button==-1)
            gameover();
        else if (button==0) {
            int a=indexI;
            int b=indexJ;
            revealNeighbours(a,b);
        }
    }

    private void gameover() {
        for (i=0;i<size;i++)
            for (j=0;j<size-6;j++)
            {
                if (board[i][j].getButtonValue()==-1) {
                    board[i][j].setText(-1 + "");
                    board[i][j].setBackgroundResource(R.drawable.mine);
                }
                board[i][j].setEnabled(false);
            }
        Toast.makeText(this,"Game Over     Your Score="+score,Toast.LENGTH_SHORT).show();
    }
    private void revealNeighbours(int i,int j){
        if (isneighbourPresent(i-1,j-1)){
            if (board[i-1][j-1].getButtonValue()==0 && board[i-1][j-1].isEnabled()){
                board[i-1][j-1].setText(board[i-1][j-1].buttonValue+"");
                board[i-1][j-1].setBackgroundResource(R.drawable.revealed_button);
                board[i-1][j-1].setEnabled(false);
                revealNeighbours(i-1,j-1);
            }
            else if (board[i-1][j-1].getButtonValue()>0 && board[i-1][j-1].isEnabled()){
                score=score+board[i-1][j-1].getButtonValue();
                board[i-1][j-1].setText(board[i-1][j-1].buttonValue+"");
                board[i-1][j-1].setBackgroundResource(R.drawable.revealed_button);
                board[i-1][j-1].setEnabled(false);
            }
        }
        if (isneighbourPresent(i,j-1)){
            if (board[i][j-1].getButtonValue()==0 && board[i][j-1].isEnabled()) {
                board[i][j - 1].setText(board[i][j - 1].buttonValue + "");
                board[i][j-1].setBackgroundResource(R.drawable.revealed_button);
                board[i][j-1].setEnabled(false);
                revealNeighbours(i, j - 1);
            }
            else if (board[i][j-1].getButtonValue()>0 && board[i][j-1].isEnabled()){
                score=score+board[i][j-1].getButtonValue();
                board[i][j-1].setText(board[i][j-1].buttonValue+"");
                board[i][j-1].setBackgroundResource(R.drawable.revealed_button);
                board[i][j-1].setEnabled(false);
            }
        }
        if (isneighbourPresent(i+1,j-1)){
            if (board[i+1][j-1].getButtonValue()==0 && board[i+1][j-1].isEnabled()) {
                board[i + 1][j - 1].setText(board[i + 1][j - 1].buttonValue + "");
                board[i+1][j-1].setBackgroundResource(R.drawable.revealed_button);
                board[i+1][j-1].setEnabled(false);
                revealNeighbours(i + 1, j - 1);
            }
            else if (board[i+1][j-1].getButtonValue()>0 && board[i+1][j-1].isEnabled()){
                score=score+board[i+1][j-1].getButtonValue();
                board[i+1][j-1].setText(board[i+1][j-1].buttonValue+"");
                board[i+1][j-1].setBackgroundResource(R.drawable.revealed_button);
                board[i+1][j-1].setEnabled(false);
            }
        }
        if (isneighbourPresent(i-1,j)){
            if (board[i-1][j].getButtonValue()==0 && board[i-1][j].isEnabled()) {
                board[i - 1][j].setText(board[i - 1][j].buttonValue + "");
                board[i-1][j].setBackgroundResource(R.drawable.revealed_button);
                board[i-1][j].setEnabled(false);
                revealNeighbours(i - 1, j);
            }
            else if (board[i-1][j].getButtonValue()>0 && board[i-1][j].isEnabled()){
                score=score+board[i-1][j].getButtonValue();
                board[i-1][j].setText(board[i-1][j].buttonValue+"");
                board[i-1][j].setBackgroundResource(R.drawable.revealed_button);
                board[i-1][j].setEnabled(false);
            }
        }
        if (isneighbourPresent(i+1,j)){
            if (board[i+1][j].getButtonValue()==0 && board[i+1][j].isEnabled()) {
                board[i + 1][j].setText(board[i + 1][j].buttonValue + "");
                board[i+1][j].setBackgroundResource(R.drawable.revealed_button);
                board[i+1][j].setEnabled(false);
                revealNeighbours(i + 1, j);
            }
            else if (board[i+1][j].getButtonValue()>0 && board[i+1][j].isEnabled()){
                score=score+board[i+1][j].getButtonValue();
                board[i+1][j].setText(board[i+1][j].buttonValue+"");
                board[i+1][j].setBackgroundResource(R.drawable.revealed_button);
                board[i+1][j].setEnabled(false);
            }
        }
        if (isneighbourPresent(i-1,j+1)){
            if (board[i-1][j+1].getButtonValue()==0 && board[i-1][j+1].isEnabled()) {
                board[i - 1][j + 1].setText(board[i - 1][j + 1].buttonValue + "");
                board[i-1][j+1].setBackgroundResource(R.drawable.revealed_button);
                board[i-1][j+1].setEnabled(false);
                revealNeighbours(i - 1, j + 1);
            }
            else if (board[i-1][j+1].getButtonValue()>0 && board[i-1][j+1].isEnabled()){
                score=score+board[i-1][j+1].getButtonValue();
                board[i-1][j+1].setText(board[i-1][j+1].buttonValue+"");
                board[i-1][j+1].setBackgroundResource(R.drawable.revealed_button);
                board[i-1][j+1].setEnabled(false);
            }
        }
        if (isneighbourPresent(i,j+1)){
            if (board[i][j+1].getButtonValue()==0 && board[i][j+1].isEnabled()) {
                board[i][j + 1].setText(board[i][j + 1].buttonValue + "");
                board[i][j+1].setBackgroundResource(R.drawable.revealed_button);
                board[i][j+1].setEnabled(false);
                revealNeighbours(i, j + 1);
            }
            else if (board[i][j+1].getButtonValue()>0 && board[i][j+1].isEnabled()){
                score=score+board[i][j+1].getButtonValue();
                board[i][j+1].setText(board[i][j+1].buttonValue+"");
                board[i][j+1].setBackgroundResource(R.drawable.revealed_button);
                board[i][j+1].setEnabled(false);
            }
        }
        if (isneighbourPresent(i+1,j+1)){
            if (board[i+1][j+1].getButtonValue()==0 && board[i+1][j+1].isEnabled()) {
                board[i + 1][j + 1].setText(board[i + 1][j + 1].buttonValue + "");
                board[i+1][j+1].setBackgroundResource(R.drawable.revealed_button);
                board[i+1][j+1].setEnabled(false);
                revealNeighbours(i + 1, j + 1);
            }
            else if (board[i+1][j+1].getButtonValue()>0 && board[i+1][j+1].isEnabled()){
                score=score+board[i+1][j+1].getButtonValue();
                board[i+1][j+1].setText(board[i+1][j+1].buttonValue+"");
                board[i+1][j+1].setBackgroundResource(R.drawable.revealed_button);
                board[i+1][j+1].setEnabled(false);
            }
        }

    }
    private boolean isneighbourPresent(int i,int j){
        if (i >= 0 && j >= 0 && i < size && j < size - 6) {

            return true;
        }
        else
            return false;
    }

    @Override
    public boolean onLongClick(View view) {
        button=(minebutton) view;
        if (button.getText().toString().isEmpty()) {
            button.setText("F");
            flagCount--;
            button.setFlagValue(true);
            button.setBackgroundResource(R.drawable.flag);
            checkIfGameWon();
        }
        else if (button.getText().toString().equals("F")) {
            button.setText("");
            button.setFlagValue(false);
            button.setBackgroundResource(R.drawable.button_bg);
            flagCount++;
            checkIfGameWon();
        }
        textView1.setText(flagCount+"");
        return true;
    }
}