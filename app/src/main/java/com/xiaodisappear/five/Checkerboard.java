package com.xiaodisappear.five;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoxinggen on 10/18/16.
 *
 * Board the coordinate class
 */

public class Checkerboard {

    /**
     * LeftTop this coordinate.
     */
    private float[] mLeftTop = new float[2];

    /**
     * Board the coordinate limit.
     */
    private SquareRec mBoardLimit = new SquareRec();

    /**
     * The width of ths board.
     */
    private int boardWidth;

    /**
     * The height of ths board.
     */
    private int boardHeight;

    /**
     * The horizontal number of ths board.
     */
    private int horizontalNumber;

    /**
     * The vertical number of ths board.
     */
    private int verticallNumber;

    /**
     * The chess of ths width.
     */
    private float chessWidth;

    /**
     * The chess of ths height.
     */
    private float chessHeight;

    private Map<Integer, List<SquareRec>> boardSquare = new HashMap<>();

    /**
     * The horizontal coordinate point collection boar.
     */
    private List<ChessLine> chessHors;

    /**
     * The vertical coordinate point collection boar.
     */
    private List<ChessLine> chessVer;

    private List<SquareRec> serchVerSquares;

    private List<SquareRec> redPerson;

    private List<SquareRec> blackPerson;

    private List<String> existPoint;

    private AIChess mAIChess;


    public Checkerboard(@NonNull float[] leftTop, int horizontalNumber, int verticallNumber,
            int boardWidth, int boardHeight) {

        this.mLeftTop = leftTop;
        this.horizontalNumber = horizontalNumber;
        this.verticallNumber = verticallNumber;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        calBoardLimit();
        calChessLimit();

        if (redPerson == null) {
            redPerson = new ArrayList<>();
        }

        if (blackPerson == null) {
            blackPerson = new ArrayList<>();
        }

        if (existPoint == null) {
            existPoint = new ArrayList<>();
        }

        if (mAIChess == null) {
            mAIChess = new AIChess(verticallNumber, horizontalNumber);
        }
    }

    /**
     * Calculate the border around the chess.
     */
    private void calChessLimit() {

        chessWidth = boardWidth / (horizontalNumber + 1);
        chessHeight = boardHeight / (verticallNumber + 1);

        spliteBoard(1, 1);
    }

    /**
     * Get width of ths chess.
     */
    public float getChessWidth() {
        return chessWidth;
    }

    /**
     * Get height of ths chess.
     */
    public float getChessHeight() {
        return chessHeight;
    }

    /**
     * Get red walking.
     */
    public List<SquareRec> getRedPerson() {
        return redPerson;
    }

    /**
     * Get black walking.
     */
    public List<SquareRec> getBlackPerson() {
        return blackPerson;
    }


    /**
     * Cover board coordinates.
     */
    private void spliteBoard(int verNumber, int horNum) {

        List<SquareRec> rowSquares = boardSquare.get(verNumber);
        if (rowSquares == null) {
            rowSquares = new ArrayList<>();
            boardSquare.put(verNumber, rowSquares);
        }

        SquareRec square = new SquareRec();
        if (verNumber == 1) {   //Calculate the upper left corner coordinates.
            if (horNum == 1) {
                square.leftTop = mLeftTop;
            } else {
                square.leftTop = rowSquares.get(rowSquares.size() - 1).rightTop;
            }
        } else {
            square.leftTop = boardSquare.get(verNumber - 1).get(horNum - 1).leftBottom;
        }

        square.rightTop[0] = square.leftTop[0] + chessWidth;
        square.rightTop[1] = square.leftTop[1];

        square.leftBottom[0] = square.leftTop[0];
        square.leftBottom[1] = square.leftTop[1] + chessHeight;

        square.rightBottom[0] = square.leftBottom[0] + chessWidth;
        square.rightBottom[1] = square.leftBottom[1];

        horNum++;
        if (verNumber <= verticallNumber && horNum <= horizontalNumber) {
            rowSquares.add(square);
            spliteBoard(verNumber, horNum);
        } else if (verNumber <= verticallNumber && horNum > horizontalNumber) {
            rowSquares.add(square);
            verNumber++;
            horNum = 1;
            spliteBoard(verNumber, horNum);
        }

    }

    /**
     * Calculate the border around the board.
     */
    private void calBoardLimit() {

        mBoardLimit.leftTop = mLeftTop;

        mBoardLimit.rightTop[0] = mLeftTop[0] + boardWidth;
        mBoardLimit.rightTop[1] = mLeftTop[1];

        mBoardLimit.leftBottom[0] = mLeftTop[0];
        mBoardLimit.leftBottom[1] = mLeftTop[1] + boardHeight;

        mBoardLimit.rightBottom[0] = mLeftTop[0] + boardWidth;
        mBoardLimit.rightBottom[1] = mLeftTop[1] + boardHeight;

    }

    /**
     * Calculate the horizontal coordinate of the board.
     */
    public List<ChessLine> getHorizontalCoordinates() {

        if (chessHors != null && chessHors.size() > 0) {
            return chessHors;
        }

        if (boardSquare != null && boardSquare.size() > 0) {

            chessHors = new ArrayList<>();
            ChessLine chessLine;

            for (int i = 1; i <= horizontalNumber; i++) {

                List<SquareRec> square = boardSquare.get(i);

                chessLine = new ChessLine();

                chessLine.startX = square.get(0).leftTop[0];
                chessLine.startY = square.get(0).leftTop[1];

                chessLine.endX = square.get(square.size() - 1).rightTop[0];
                chessLine.endY = square.get(square.size() - 1).rightTop[1];

                chessHors.add(chessLine);
                if (i == horizontalNumber) {
                    chessLine = new ChessLine();

                    chessLine.startX = square.get(0).leftBottom[0];
                    chessLine.startY = square.get(0).leftBottom[1];

                    chessLine.endX = square.get(square.size() - 1).rightBottom[0];
                    chessLine.endY = square.get(square.size() - 1).rightBottom[1];

                    chessHors.add(chessLine);
                }
            }
        }

        return chessHors;
    }

    /**
     * Calculate the vertical coordinate of the board.
     */
    public List<ChessLine> getVerticalCoordinates() {

        if (chessVer != null && chessVer.size() > 0) {
            return chessVer;
        }

        if (boardSquare != null && boardSquare.size() > 0) {

            chessVer = new ArrayList<>();
            ChessLine chessLine;

            for (int i = 1; i <= verticallNumber; i++) {

                chessLine = new ChessLine();

                chessLine.startX = boardSquare.get(1).get(i - 1).leftTop[0];
                chessLine.startY = boardSquare.get(1).get(i - 1).leftTop[1];

                chessLine.endX = boardSquare.get(verticallNumber).get(i - 1).leftBottom[0];
                chessLine.endY = boardSquare.get(verticallNumber).get(i - 1).leftBottom[1];

                chessVer.add(chessLine);
                if (i == verticallNumber) {
                    chessLine = new ChessLine();

                    chessLine.startX = boardSquare.get(1).get(horizontalNumber - 1).rightTop[0];
                    chessLine.startY = boardSquare.get(1).get(horizontalNumber - 1).rightTop[1];

                    chessLine.endX = boardSquare.get(verticallNumber).get(
                            horizontalNumber - 1).rightBottom[0];
                    chessLine.endY = boardSquare.get(verticallNumber).get(
                            horizontalNumber - 1).rightBottom[1];

                    chessVer.add(chessLine);
                }
            }
        }

        return chessHors;
    }

    /**
     * add step.
     */
    public void addChess(float[] coodinate) {

        try {
            if (!isContainBoard(coodinate)) {
                return;
            }

            int herIndex = searchHerIndex(coodinate[0]);

            int verIndex = searchVerIndex(coodinate[1]);

            if (herIndex == -1 || verIndex == -1) {
                return;
            }

            if (existPoint == null) {
                existPoint = new ArrayList<>();
            }

            String value = new StringBuffer().append(String.valueOf(herIndex)).append(
                    String.valueOf(verIndex)).toString();
            if (existPoint.contains(value)) {
                return;
            }

            existPoint.add(value);

            SquareRec tagSquare = boardSquare.get(verIndex + 1).get(herIndex);

            if (redPerson.size() <= blackPerson.size()) {
                redPerson.add(tagSquare);
                mAIChess.addRedLocation(new ChessLocation(herIndex, verIndex));
            } else {
                blackPerson.add(tagSquare);
                mAIChess.addBlackLocation(new ChessLocation(herIndex, verIndex));
            }
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
            e.printStackTrace();
        }
    }


    private boolean isContainBoard(float[] coodinate) {

        if (coodinate == null || coodinate.length != 2) {
            return false;
        }

        if (mBoardLimit.leftTop[0] < coodinate[0]
                && mBoardLimit.rightTop[0] > coodinate[0]
                && mBoardLimit.leftTop[1] < coodinate[1]
                && mBoardLimit.leftBottom[1] > coodinate[1]) {
            return true;
        }

        return false;
    }


    private int searchHerIndex(float x) {

        List<SquareRec> herSquarces = boardSquare.get(1);

        int start = 0;
        int end = herSquarces.size() - 1;
        while (start <= end) {
            int middle = (start + end) / 2;
            SquareRec square = herSquarces.get(middle);

            if (square.leftTop[0] < x && square.rightTop[0] > x) {
                return middle;
            } else if (x < square.leftTop[0]) {
                end = middle - 1;
            } else if (x > square.rightTop[0]) {
                start = middle + 1;
            }
        }
        return -1;
    }


    private int searchVerIndex(float y) {

        if (serchVerSquares == null) {
            serchVerSquares = new ArrayList<>();
            for (int i = 1; i <= verticallNumber; i++) {
                serchVerSquares.add(boardSquare.get(i).get(0));
            }
        }

        int start = 0;
        int end = serchVerSquares.size() - 1;
        while (start <= end) {
            int middle = (start + end) / 2;
            SquareRec square = serchVerSquares.get(middle);

            if (square.leftTop[1] < y && square.leftBottom[1] > y) {
                return middle;
            } else if (y < square.leftTop[1]) {
                end = middle - 1;
            } else if (y > square.leftBottom[1]) {
                start = middle + 1;
            }
        }
        return -1;
    }
}
