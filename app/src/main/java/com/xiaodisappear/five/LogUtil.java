package com.xiaodisappear.five;

import android.text.TextUtils;
import android.util.Log;

/**
 * 对日志进行管理 在DeBug模式开启，其它模式关闭
 *
 * @author disppear 下午2:52:59
 */
public class LogUtil {

    /**
     * 是否开启debug
     */
    public static boolean isDebug = true;
    public static boolean isOutFile = true;
    private static String tag = "disppear##";

    private static String DEFAULT_TAG = "ksyche";

    static String className;
    static String methodName;
    static int lineNumber;

    private static String createLog(String log) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(methodName);
        buffer.append(":");
        buffer.append(lineNumber);
        buffer.append("]");
        buffer.append(log);

        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    /**
     * 错误 Write By disppear 2014-5-8
     */
    public static void e(String msg) {
        if (isDebug) {

            getMethodNames(new Throwable().getStackTrace());
            Log.e(tag + className, createLog(msg));
        }
    }

    /**
     * 信息 Write By disppear 2014-5-8
     */
    public static void i(String msg) {
        if (isDebug) {
            getMethodNames(new Throwable().getStackTrace());
            Log.i(tag + className, createLog(msg));
        }
    }

    /**
     * 警告 Write By disppear 2014-5-8
     */
    public static void w(String msg) {
        if (isDebug) {

            getMethodNames(new Throwable().getStackTrace());
            Log.w(tag + className, createLog(msg));
        }
    }

    public static void outDetail(String tag, String info) {
        if (info != null) {
            if (TextUtils.isEmpty(tag)) {
                tag = DEFAULT_TAG;
            }
            StackTraceElement stack[] = (new Throwable()).getStackTrace();
            if (stack.length > 3) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(info);
                buffer.append("<--");
                String[] names = stack[1].getClassName().split("\\.");
                buffer.append(names[names.length - 1] + "第" + stack[1].getLineNumber() + "行<--");
                names = stack[2].getClassName().split("\\.");
                buffer.append(names[names.length - 1] + "第" + stack[2].getLineNumber() + "行<--");
                names = stack[3].getClassName().split("\\.");
                buffer.append(names[names.length - 1] + "第" + stack[3].getLineNumber() + "行");
                Log.d(tag, buffer.toString());
            } else {
                Log.d(tag, info);
            }
        }
    }
}