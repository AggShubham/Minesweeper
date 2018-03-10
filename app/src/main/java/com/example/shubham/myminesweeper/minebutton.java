package com.example.shubham.myminesweeper;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;

/**
 * Created by Shubham on 30-01-2018.
 */

public class minebutton extends AppCompatButton{
    public int buttonValue=0;
    public boolean flag;
    private int indexI;
    private int indexJ;
    public int getButtonValue() {
        return buttonValue;
    }

    public void setButtonValue(int buttonValue) {
        this.buttonValue = buttonValue;
    }

    public minebutton(Context context) {
        super(context);
        setBackgroundResource(R.drawable.button_bg);

    }

    public void setIndex(int i, int j){
        indexI = i;
        indexJ = j;
    }

    public int getIndexI() {
        return indexI;
    }

    public int getIndexJ() {
        return indexJ;
    }

    public void setFlagValue(boolean FlagValue){
        flag=FlagValue;
    }

    public boolean getFlagValue(){
        return flag;
    }
}