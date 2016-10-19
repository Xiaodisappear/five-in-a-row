package com.xiaodisappear.five;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by guoxinggen on 10/18/16.
 */

public class GameSufaceView extends SurfaceView implements SurfaceHolder.Callback {


    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    private Context mContext;
    private SurfaceHolder mHolder;

    private DrawThread mDrawThread;

    private ChessDraw mChessDraw;

    //最大帧数 (1000 / 30)
    private static final int DRAW_INTERVAL = 30;


    public GameSufaceView(@NonNull Context context) {
        super(context);

        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);

        this.mContext = context;

        mHolder = this.getHolder();
        mHolder.addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        if (mDrawThread == null) {
            mDrawThread = new DrawThread();
        }

        if (mChessDraw == null) {
            mChessDraw = new ChessDraw(mContext);
        }

        mDrawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        if (null != mDrawThread) {
            mDrawThread.stopThread();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

       if(mChessDraw != null){
           mChessDraw.onTouchEvent(event);
       }

        return super.onTouchEvent(event);
    }

    private class DrawThread extends Thread {

        public boolean isRunning = false;

        public DrawThread() {
            isRunning = true;
        }

        public void stopThread() {
            isRunning = false;
            boolean workIsNotFinish = true;
            while (workIsNotFinish) {
                try {
                    this.join();
                    // 保证run方法执行完毕
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                workIsNotFinish = false;
            }
        }

        public void run() {
            long deltaTime = 0;
            long tickTime = 0;
            tickTime = System.currentTimeMillis();
            while (isRunning) {
                Canvas canvas = null;
                try {
                    synchronized (mHolder) {
                        canvas = mHolder.lockCanvas();
                        mChessDraw.onDraw(canvas);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != mHolder) {
                        mHolder.unlockCanvasAndPost(canvas);
                    }
                }

                deltaTime = System.currentTimeMillis() - tickTime;
                if (deltaTime < DRAW_INTERVAL) {
                    try {
                        Thread.sleep(DRAW_INTERVAL - deltaTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                tickTime = System.currentTimeMillis();
            }
        }
    }
}
