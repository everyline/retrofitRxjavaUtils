package com.rzx.commonlibrary.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * 文件描述：
 * 作者：G
 * 创建时间：2019/7/31
 */
public class JActivityManager {
    private Stack<Activity> activityStack;
    private static JActivityManager instance;
    private final static String TAG = JActivityManager.class.getSimpleName();

    private JActivityManager() {
    }

    public synchronized static JActivityManager getInstance() {
        if (instance == null) {
            instance = new JActivityManager();
        }
        return instance;
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }


    /**
     * 将当前Activity推入栈中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.addElement(activity);
        int size = activityStack.size();
    }


    /**
     * 主动退出Activity
     *
     * @param activity
     */
    public void closeActivity(Activity activity) {
        if (activity != null) {
            if (!activity.isFinishing()) {
                activity.finish();
            } else {
            }

            activityStack.removeElement(activity);
            activity = null;
        }

        int size = activityStack.size();

    }

    /**
     * 结束所有的activity
     */
    public void closeAllActivity() {
        try {
            for (int i = 0; i < activityStack.size(); i++) {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束指定类名的Activity
     *
     * @param cls 指定的Activity
     */
    public void finishActivity(Class<?> cls) {
        int size = activityStack.size();
        for (int i = 0; i < size; i++) {
            Activity activity = activityStack.get(i);
            if (activity != null && activity.getClass().equals(cls)) {
                closeActivity(activity);
                size--;
                i--;
            }
        }
    }

    /**
     * 移除全部元素，除了指定类型的以外
     *
     * @param cls
     */
    public void popAllActivityExceptOne(Class<?> cls) {
        int size = activityStack.size();
        for (int i = 0; i < size; i++) {
            Activity activity = activityStack.get(i);
            if (activity != null && activity.getClass().equals(cls)) {
            } else {
                closeActivity(activity);
                size--;
                i--;
            }
        }
    }


    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            closeAllActivity();
            //退出JVM(java虚拟机),释放所占内存资源,0表示正常退出(非0的都为异常退出)
            System.exit(0);
            //从操作系统中结束掉当前程序的进程
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
