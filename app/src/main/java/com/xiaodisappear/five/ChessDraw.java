package com.xiaodisappear.five;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.view.MotionEvent;

import java.util.List;

/**
 * Created by guoxinggen on 10/18/16.
 */

public class ChessDraw {

    private Context mContext;

    private static final int RES_CHESS_WHITE = R.drawable.ic_chess_white;
    private static final int RES_CHESS_BLACK = R.drawable.ic_chess_black;

    private static final int CHESSBOARD_NUMBER = 10;

    private float[] leftTop = new float[2];
    private Bitmap chessWhite;
    private Bitmap chessBlack;

    private Checkerboard mCheckerboard;

    private Paint mChessLinePaint;

    public ChessDraw(Context context) {

        this.mContext = context;
        initRes();
        initLinePaint();

    }

    private void initLinePaint() {

        mChessLinePaint = new Paint();
        mChessLinePaint.setColor(Color.BLACK);
        mChessLinePaint.setAntiAlias(true);
    }

    private void initRes() {

        int topY = (GameSufaceView.SCREEN_HEIGHT - GameSufaceView.SCREEN_WIDTH) / 2;
        leftTop[0] = 50f;
        leftTop[1] = topY;

        mCheckerboard = new Checkerboard(leftTop, CHESSBOARD_NUMBER, CHESSBOARD_NUMBER,
                GameSufaceView.SCREEN_WIDTH - 100,
                GameSufaceView.SCREEN_WIDTH - 100);

        chessWhite = Utils.ratio(((BitmapDrawable) mContext.getDrawable(
                RES_CHESS_WHITE)).getBitmap(), mCheckerboard.getChessWidth(),
                mCheckerboard.getChessHeight());

        chessBlack = Utils.ratio(((BitmapDrawable) mContext.getDrawable(
                RES_CHESS_BLACK)).getBitmap(), mCheckerboard.getChessWidth(),
                mCheckerboard.getChessHeight());

    }

    public void onTouchEvent(MotionEvent event) {
        mCheckerboard.addChess(new float[]{event.getX(), event.getY()});
    }


    public void onDraw(@NonNull Canvas canvas) {

        try {

            if (canvas == null) {
                return;
            }

            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            List<ChessLine> hors = mCheckerboard.getHorizontalCoordinates();

            for (ChessLine chessLine : hors) {
                if (chessLine == null) {
                    continue;
                }
                canvas.drawLine(chessLine.startX, chessLine.startY, chessLine.endX, chessLine.endY,
                        mChessLinePaint);
            }

            List<ChessLine> vers = mCheckerboard.getVerticalCoordinates();

            for (ChessLine chessLine : vers) {
                if (chessLine == null) {
                    continue;
                }
                canvas.drawLine(chessLine.startX, chessLine.startY, chessLine.endX, chessLine.endY,
                        mChessLinePaint);
            }

            List<SquareRec> redPerson = mCheckerboard.getRedPerson();

            if (redPerson != null && redPerson.size() > 0) {

                for (SquareRec squareRec : redPerson) {

                    if (squareRec == null) {
                        continue;
                    }
                    canvas.drawBitmap(chessWhite, squareRec.leftTop[0], squareRec.leftTop[1], null);
                }

            }

            List<SquareRec> blackPerson = mCheckerboard.getBlackPerson();

            if (blackPerson != null && blackPerson.size() > 0) {

                for (SquareRec squareRec : blackPerson) {

                    if (squareRec == null) {
                        continue;
                    }
                    canvas.drawBitmap(chessBlack, squareRec.leftTop[0], squareRec.leftTop[1], null);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
