package com.by.wind.demo;

/**
 * Created by Wind on 2017/12/12.
 */

public abstract class Computer {

    protected String mBoard;
    protected String mDisplay;
    protected String mOS;

    protected Computer() {

    }
    public String getBoard() {
        return mBoard;
    }

    public void setBoard(String board) {
        this.mBoard = board;
    }

    public String getDisplay() {
        return mDisplay;
    }

    public void setDisplay(String display) {
        this.mDisplay = display;
    }

    public abstract void setOS();

    @Override
    public String toString() {
        return "Computer{" +
                "mBoard='" + mBoard + '\'' +
                ", mDisplay='" + mDisplay + '\'' +
                ", mOS='" + mOS + '\'' +
                '}';
    }
}
