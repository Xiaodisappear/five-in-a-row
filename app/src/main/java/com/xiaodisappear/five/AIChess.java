package com.xiaodisappear.five;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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


    public void addRedLocation(@NonNull ChessLocation location) {

        if (!isLegalLocation(location)) {
            return;
        }

        this.chessStatus.put(location.getKey(), PERSON_RED);
        redLocations.add(location);

    }

    public void addBlackLocation(@NonNull ChessLocation location) {

        if (!isLegalLocation(location)) {
            return;
        }

        this.chessStatus.put(location.getKey(), PERSON_BLACK);
        blackLocations.add(location);

    }

    /**
     * Coordinates should be illegal？
     */
    private boolean isLegalLocation(@NonNull ChessLocation location) {

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
    public boolean isWin(@NonNull ChessLocation location, int person) {

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
    private int downOrUpContinuationCount(@NonNull ChessLocation location, int person,
            boolean isUp) {

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
    private int leftOrRightContinuationCount(@NonNull ChessLocation location, int person,
            boolean isLeft) {

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
    private int upleftOrRightDownContinuationCount(@NonNull ChessLocation location, int person,
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
     * How many pieces on the upright/downleft side of the continuous.
     */
    private int downLeftOrUpRightContinuationCount(@NonNull ChessLocation location, int person,
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

        if (redLocations != null && !redLocations.isEmpty()) {
            redLocations.clear();
        }

        if (blackLocations != null && !blackLocations.isEmpty()) {
            blackLocations.clear();
        }

        if (chessStatus != null && !chessStatus.isEmpty()) {
            chessStatus.clear();
        }
    }

    /**
     * Anlaye this next step.
     */
    private ChessLocation isAnalyzeNextLocation(ChessLocation location) {

        if (chessStatus.size() >= 6) {

            ChessLocation nextLocation = analyzeDowOrUpLocation(location);
            if (nextLocation != null) return nextLocation;

            nextLocation = analyzeLeftOrRightLocation(location);
            if (nextLocation != null) return nextLocation;

            nextLocation = analyzeUpLeftOrDownRightLocation(location);
            if (nextLocation != null) return nextLocation;

            nextLocation = analyzeDownLeftOrUpRightLocation(location);
            if (nextLocation != null) return nextLocation;

        }

        return null;
    }

    @Nullable
    private ChessLocation analyzeUpLeftOrDownRightLocation(ChessLocation location) {
        int upLeft = upleftOrRightDownContinuationCount(location, PERSON_RED, false);
        int downRight = upleftOrRightDownContinuationCount(location, PERSON_RED, true);
        if (upLeft + downRight > 3) {
            Integer value = null;
            if (upLeft >= 3) {
                if ((location.x - upLeft - 1 > 0) && (location.y - upLeft - 1 > 0)) {
                    value = chessStatus.get(new StringBuffer().append(
                            String.valueOf(location.x - upLeft - 1)).append(
                            String.valueOf(location.y - upLeft - 1)).toString());
                }

                if (value != null && value == PERSON_NO) {
                    return new ChessLocation(location.x - upLeft - 1, location.y - upLeft - 1);
                } else {
                    if (location.x + 1 <= horNumber && location.y + 1 <= verNumber) {
                        value = chessStatus.get(new StringBuffer().append(
                                String.valueOf(location.x + 1)).append(
                                location.y + 1).toString());

                        if (value != null && value == PERSON_NO) {
                            return new ChessLocation(location.x + 1, location.y + 1);
                        }
                    }
                }
            }

            if (downRight >= 3) {
                if ((location.x + downRight + 1 < horNumber) && (location.y + downRight + 1
                        < verNumber)) {
                    value = chessStatus.get(new StringBuffer().append(
                            String.valueOf(location.x + downRight + 1)).append(
                            String.valueOf(location.y + downRight + 1)).toString());
                }

                if (value != null && value == PERSON_NO) {
                    return new ChessLocation(location.x + downRight + 1,
                            location.y + upLeft + 1);
                } else {
                    if (location.x - 1 > 0 && location.y - 1 > 0) {
                        value = chessStatus.get(new StringBuffer().append(
                                String.valueOf(location.x + 1)).append(
                                location.y + 1).toString());

                        if (value != null && value == PERSON_NO) {
                            return new ChessLocation(location.x - 1, location.y - 1);
                        }
                    }
                }
            }

            if ((location.x + downRight + 1 < horNumber) && (location.y + downRight + 1
                    < verNumber)) {
                value = chessStatus.get(new StringBuffer().append(
                        String.valueOf(location.x + downRight + 1)).append(
                        String.valueOf(location.y + downRight + 1)).toString());
            }

            if (value != null && value == PERSON_NO) {
                return new ChessLocation(location.x + downRight + 1,
                        location.y + upLeft + 1);
            } else {
                if (location.x - upLeft - 1 > 0 && location.y - upLeft - 1 > 0) {
                    value = chessStatus.get(new StringBuffer().append(
                            String.valueOf(location.x - upLeft - 1)).append(
                            location.y - upLeft - 1).toString());

                    if (value != null && value == PERSON_NO) {
                        return new ChessLocation(location.x - upLeft - 1,
                                location.y - upLeft - 1);
                    }
                }
            }


        }
        return null;
    }

    @Nullable
    private ChessLocation analyzeDownLeftOrUpRightLocation(ChessLocation location) {
        int downLeft = downLeftOrUpRightContinuationCount(location, PERSON_RED, false);
        int upRight = downLeftOrUpRightContinuationCount(location, PERSON_RED, true);
        if (downLeft + upRight > 3) {
            Integer value = null;
            if (downLeft >= 3) {
                if ((location.x - downLeft - 1 > 0) && (location.y + downLeft + 1 <= verNumber)) {
                    value = chessStatus.get(new StringBuffer().append(
                            String.valueOf(location.x - downLeft - 1)).append(
                            String.valueOf(location.y + downLeft + 1)).toString());
                }

                if (value != null && value == PERSON_NO) {
                    return new ChessLocation(location.x - downLeft - 1, location.y + downLeft - 1);
                } else {
                    if (location.x + 1 <= horNumber && location.y - 1 > 0) {
                        value = chessStatus.get(new StringBuffer().append(
                                String.valueOf(location.x + 1)).append(
                                location.y - 1).toString());

                        if (value != null && value == PERSON_NO) {
                            return new ChessLocation(location.x + 1, location.y - 1);
                        }
                    }
                }
            }

            if (upRight >= 3) {
                if ((location.x + upRight + 1 <= horNumber) && (location.y - upRight - 1 > 0
                )) {
                    value = chessStatus.get(new StringBuffer().append(
                            String.valueOf(location.x + upRight + 1)).append(
                            String.valueOf(location.y - upRight - 1)).toString());
                }

                if (value != null && value == PERSON_NO) {
                    return new ChessLocation(location.x + upRight + 1,
                            location.y - upRight - 1);
                } else {
                    if (location.x - 1 > 0 && location.y + 1 <= verNumber) {
                        value = chessStatus.get(new StringBuffer().append(
                                String.valueOf(location.x - 1)).append(
                                location.y + 1).toString());

                        if (value != null && value == PERSON_NO) {
                            return new ChessLocation(location.x - 1, location.y - 1);
                        }
                    }
                }
            }

            if ((location.x + upRight + 1 <= horNumber) && (location.y - upRight - 1
                    > 0)) {
                value = chessStatus.get(new StringBuffer().append(
                        String.valueOf(location.x + upRight + 1)).append(
                        String.valueOf(location.y - upRight - 1)).toString());
            }

            if (value != null && value == PERSON_NO) {
                return new ChessLocation(location.x + upRight + 1,
                        location.y - upRight - 1);
            } else {
                if (location.x - downLeft - 1 > 0 && location.y + downLeft + 1 <= verNumber) {
                    value = chessStatus.get(new StringBuffer().append(
                            String.valueOf(location.x - downLeft - 1)).append(
                            location.y + downLeft + 1).toString());

                    if (value != null && value == PERSON_NO) {
                        return new ChessLocation(location.x - downLeft - 1,
                                location.y + downLeft + 1);
                    }
                }
            }

        }
        return null;
    }


    @Nullable
    private ChessLocation analyzeLeftOrRightLocation(@NonNull ChessLocation location) {

        int left = leftOrRightContinuationCount(location, PERSON_RED, false);
        int right = leftOrRightContinuationCount(location, PERSON_RED, true);

        if (left + right < 3) {
            return null;
        }

        Integer value = null;
        if (left >= 3) {
            if (location.x - 4 > 0) {
                value = chessStatus.get(new StringBuffer().append(
                        String.valueOf(location.x - 4)).append(
                        String.valueOf(location.y)).toString());
            }

            if (value != null && value == PERSON_NO) {
                return new ChessLocation(location.x - 4, location.y);
            } else {
                if (location.x + 1 <= horNumber) {
                    value = chessStatus.get(new StringBuffer().append(
                            String.valueOf(location.x + 1)).append(
                            String.valueOf(location.y)).toString());
                }

                if (value != null && value == PERSON_NO) {
                    return new ChessLocation(location.x + 1, location.y);
                }
            }
        }

        if (right >= 3) {
            if (location.x + 4 <= horNumber) {
                value = chessStatus.get(new StringBuffer().append(
                        String.valueOf(location.x + 4)).append(
                        String.valueOf(location.y)).toString());
            }

            if (value != null && value == PERSON_NO) {
                return new ChessLocation(location.x + 4, location.y);
            } else {
                if (location.x - 1 > 0) {
                    value = chessStatus.get(new StringBuffer().append(
                            String.valueOf(location.x - 1)).append(
                            String.valueOf(location.y)).toString());
                }

                if (value != null && value == PERSON_NO) {
                    return new ChessLocation(location.x - 1, location.y);
                }
            }
        }

        if ((location.x - left - 1) > 0) {
            value = chessStatus.get(new StringBuffer().append(
                    String.valueOf(location.x - left - 1)).append(
                    String.valueOf(location.y)).toString());
            if (value != null && value == PERSON_NO) {
                return new ChessLocation(location.x - left - 1, location.y);
            } else {
                if (location.x + right + 1 <= horNumber) {
                    value = chessStatus.get(new StringBuffer().append(
                            String.valueOf(location.x + right + 1)).append(
                            String.valueOf(location.y)).toString());

                    if (value != null && value == PERSON_NO) {
                        return new ChessLocation(location.x + right + 1,
                                location.y);
                    }
                }
            }
        }

        return null;
    }

    @Nullable
    private ChessLocation analyzeDowOrUpLocation(@NonNull ChessLocation location) {

        int dow = downOrUpContinuationCount(location, PERSON_RED, false);
        int up = downOrUpContinuationCount(location, PERSON_RED, true);

        if (dow + up < 3) {
            return null;
        }

        Integer value = null;
        if (dow > 3) {
            if (location.y + 4 <= verNumber) {
                value = chessStatus.get(new StringBuffer().append(
                        String.valueOf(location.x)).append(
                        String.valueOf(location.y + 4)).toString());
            }

            if (value != null && value == PERSON_NO) {
                return new ChessLocation(location.x, location.y + 4);
            } else {
                if (location.y - 1 > 0) {
                    value = chessStatus.get(new StringBuffer().append(
                            String.valueOf(location.x)).append(
                            String.valueOf(location.y - 1)).toString());
                }

                if (value != null && value == PERSON_NO) {
                    return new ChessLocation(location.x, location.y - 1);
                }
            }
        }

        if (up > 3) {
            if (location.y - 4 > 0) {
                value = chessStatus.get(new StringBuffer().append(
                        String.valueOf(location.x)).append(
                        String.valueOf(location.y - 4)).toString());
            }
            if (value != null && value == PERSON_NO) {
                return new ChessLocation(location.x, location.y - 4);
            } else {
                if (location.y + 1 <= verNumber) {
                    value = chessStatus.get(new StringBuffer().append(
                            String.valueOf(location.x)).append(
                            String.valueOf(location.y + 1)).toString());
                }
                if (value != null && value == PERSON_NO) {
                    return new ChessLocation(location.x, location.y + 1);
                }
            }
        }

        if ((location.y + dow + 1) < verNumber) {
            value = chessStatus.get(new StringBuffer().append(
                    String.valueOf(location.x)).append(
                    String.valueOf(location.y + dow + 1)).toString());
            if (value != null && value == PERSON_NO) {
                return new ChessLocation(location.x, location.y + dow + 1);
            } else {
                if (location.y - up - 1 > 0) {
                    value = chessStatus.get(new StringBuffer().append(
                            String.valueOf(location.x)).append(
                            String.valueOf(location.y - up - 1)).toString());

                    if (value != null && value == PERSON_NO) {
                        return new ChessLocation(location.x, location.y - up - 1);
                    }
                }
            }
        }
        return null;
    }
}
