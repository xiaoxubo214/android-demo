package com.by.wind.demo;

/**
 * Created by Wind on 2017/12/12.
 */

public class WindowsBuilder extends Builder {
    Computer mComputer = new WindowsComputer();
    @Override
    public void buildBoard(String board) {
        mComputer.setBoard(board);
    }

    @Override
    public void buildDisplay(String display) {
        mComputer.setDisplay(display);
    }

    @Override
    public void buildOS() {
        mComputer.setOS();
    }

    @Override
    public Computer create() {
        return mComputer;
    }
}
