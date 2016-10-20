package com.xiaodisappear.five;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoxinggen on 10/20/16.
 */

public class AIChess {

    public static final int PERSON_NO = 0;
    public static final int PERSON_RED = 1;
    public static final int PERSON_BLACK = 2;

    private List<ChessLocation> redLocations;
    private List<ChessLocation> blackLocations;

    private Map<String, Integer> chessStatus;

    private int verNumber;

    private int horNumber;

    public AIChess(int verNumber, int horNumber) {

        this.verNumber = verNumber;
        this.horNumber = horNumber;

        this.redLocations = new ArrayList<>();
        this.blackLocations = new ArrayList<>();
        this.chessStatus = new HashMap<>();

    }


    public void addRedLocation(ChessLocation location) {

        if (!isLegalLocation(location)) {
            return;
        }

        this.chessStatus.put(location.getKey(), PERSON_RED);
        redLocations.add(location);

    }

    public void addBlackLocation(ChessLocation location) {

        if (!isLegalLocation(location)) {
            return;
        }

        this.chessStatus.put(location.getKey(), PERSON_BLACK);
        blackLocations.add(location);

    }

    /**
     * Coordinates should be illegal？
     */
    private boolean isLegalLocation(ChessLocation location) {

        if (location == null) {
            return false;
        }

        if (location.x == 0 || location.y == 0) {
            return false;
        }

        return true;
    }

    /**
     * who wins.
     */
    public boolean isWin(ChessLocation location, int person) {

        if (!isLegalLocation(location) || person == 0) {
            return false;
        }

        if ((leftContinuationCount(location, person) + rightContinuationCount(location, person))
                > 4) {
            return true;
        }

        return false;
    }

    /**
     * How many pieces on the left side of the continuous.
     */
    private int leftContinuationCount(ChessLocation location, int person) {

        if (person == PERSON_NO) {
            return 0;
        }

        int x = location.x;
        int y = location.y;
        int count = 0;

        for (int i = 1; i <= 5; i++) {

            int xKey = x - 1;

            if (xKey > 0) {
                String key = new StringBuffer().append(String.valueOf(xKey)).append(y).toString();
                int whoPerson = chessStatus.get(key);
                if (person == whoPerson) {
                    count++;
                } else {
                    return count;
                }
            } else {
                return count;
            }

        }

        return count;
    }

    /**
     * How many pieces on the right side of the continuous.
     */
    private int rightContinuationCount(ChessLocation location, int person) {

        if (person == PERSON_NO) {
            return 0;
        }

        int x = location.x;
        int y = location.y;
        int count = 0;

        for (int i = 1; i <= 5; i++) {

            int xKey = x + 1;

            if (xKey > verNumber) {
                String key = new StringBuffer().append(String.valueOf(xKey)).append(y).toString();
                int whoPerson = chessStatus.get(key);
                if (person == whoPerson) {
                    count++;
                } else {
                    return count;
                }
            } else {
                return count;
            }

        }

        return count;
    }


}
