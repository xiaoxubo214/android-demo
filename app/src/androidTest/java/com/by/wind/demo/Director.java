package com.by.wind.demo;

/**
 * Created by Wind on 2017/12/12.
 */

public class Director {

    Builder mBuilder = null;
    public Director(Builder builder){
        this.mBuilder = builder;
    }

    public void construct(String board, String display) {
        mBuilder.buildBoard(board);
        mBuilder.buildDisplay(display);
        mBuilder.buildOS();
    }
}
