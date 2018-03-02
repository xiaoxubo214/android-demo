package com.by.wind.demo;

/**
 * Created by Wind on 2017/12/12.
 */

public abstract class Builder {
    public abstract void buildBoard(String board);
    public abstract void buildDisplay(String display);
    public abstract void buildOS();
    public abstract Computer create();
}
