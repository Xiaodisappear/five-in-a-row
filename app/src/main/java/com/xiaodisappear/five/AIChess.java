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

        if (!isLegalLocation(location) || chessStatus.size() < 8 || person == 0) {
            return false;
        }

        if ((downOrUpContinuationCount(location, person, false)
                + downOrUpContinuationCount(location, person, true))
                >= 4) {
            return true;
        }

        if ((leftOrRightContinuationCount(location, person, false)
                + leftOrRightContinuationCount(location, person, true)) >= 4) {
            return true;
        }

        if ((upleftOrRightDownContinuationCount(location, person, false)
                + upleftOrRightDownContinuationCount(location, person, true)) >= 4) {
            return true;
        }

        if ((downLeftOrUpRightContinuationCount(location, person, false)
                + downLeftOrUpRightContinuationCount(location, person, true)) >= 4) {
            return true;
        }

        return false;
    }

    /**
     * How many pieces on the up/down side of the continuous.
     */
    private int downOrUpContinuationCount(ChessLocation location, int person, boolean isUp) {

        if (person == PERSON_NO) {
            return 0;
        }

        int x = location.x;
        int y = location.y;
        int count = 0;

        for (int i = 1; i <= 5; i++) {

            int yKey;
            if (isUp) {
                yKey = y - i;
            } else {
                yKey = y + i;
            }

            if ((yKey > 0 && isUp) || (yKey < horNumber && !isUp)) {
                String key = new StringBuffer().append(String.valueOf(x)).append(yKey).toString();
                 Integer whoPerson = chessStatus.get(key);
                if (whoPerson != null && person == whoPerson) {
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
     * How many pieces on the left/right side of the continuous.
     */
    private int leftOrRightContinuationCount(ChessLocation location, int person, boolean isLeft) {

        if (person == PERSON_NO) {
            return 0;
        }

        int x = location.x;
        int y = location.y;
        int count = 0;

        for (int i = 1; i <= 5; i++) {

            int xKey;
            if (isLeft) {
                xKey = x - i;
            } else {
                xKey = x + i;
            }

            if ((xKey > 0 && isLeft) || (xKey < horNumber && !isLeft)) {
                String key = new StringBuffer().append(String.valueOf(xKey)).append(y).toString();
                Integer whoPerson = chessStatus.get(key);
                if (whoPerson != null && person == whoPerson) {
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
     * How many pieces on the upleft/downright side of the continuous.
     */
    private int upleftOrRightDownContinuationCount(ChessLocation location, int person,
            boolean isUpleft) {
        if (person == PERSON_NO) {
            return 0;
        }

        int x = location.x;
        int y = location.y;
        int count = 0;

        for (int i = 1; i <= 5; i++) {

            int xKey;
            int yKey;
            if (isUpleft) {
                xKey = x - i;
                yKey = y - i;
            } else {
                xKey = x + i;
                yKey = y + i;
            }

            if (((xKey > 0 && yKey > 0 && isUpleft) || (xKey < horNumber && yKey < verNumber
                    && !isUpleft))) {
                String key = new StringBuffer().append(String.valueOf(xKey)).append(
                        yKey).toString();
                Integer whoPerson = chessStatus.get(key);
                if (whoPerson !=null && person == whoPerson) {
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
     * How many pieces on the upright/downleft side of the continuous.
     */
    private int downLeftOrUpRightContinuationCount(ChessLocation location, int person,
            boolean isDownLeft) {
        if (person == PERSON_NO) {
            return 0;
        }

        int x = location.x;
        int y = location.y;
        int count = 0;

        for (int i = 1; i <= 5; i++) {

            int xKey;
            int yKey;
            if (isDownLeft) {
                xKey = x - i;
                yKey = y + i;
            } else {
                xKey = x + i;
                yKey = y - i;
            }

            if (((xKey > 0 && yKey < verNumber && isDownLeft) || (xKey < horNumber && yKey > 0
                    && !isDownLeft))) {
                String key = new StringBuffer().append(String.valueOf(xKey)).append(
                        yKey).toString();
                Integer whoPerson = chessStatus.get(key);
                if (whoPerson != null && person == whoPerson) {
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


    public void clear() {

        if(redLocations!=null && !redLocations.isEmpty()){
            redLocations.clear();
        }

        if(blackLocations!=null && !blackLocations.isEmpty()){
            blackLocations.clear();
        }

        if(chessStatus!=null && !chessStatus.isEmpty()){
            chessStatus.clear();
        }

    }
}
