package com.xiaodisappear.five;

/**
 * Created by guoxinggen on 10/20/16.
 */

public class ChessLocation {

    public int x;

    public int y;

    public ChessLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getKey() {

        return new StringBuffer().append(String.valueOf(x)).append(String.valueOf(y)).toString();
    }

}
