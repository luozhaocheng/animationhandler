package com.hirosama.animations;

import android.annotation.SuppressLint;
import android.os.Handler;

public abstract class AnimationHandler {
    
    protected OnInterpolatorListener interListener = null;
    protected OnAnimationFinishedListener finishedListener = null;
    
    protected int current = 0;
    protected int result = 0;
    protected boolean running = false;
    
    public AnimationHandler(OnInterpolatorListener listener) {
        interListener = listener;
    }
    
    public OnAnimationFinishedListener getFinishedListener() {
        return finishedListener;
    }
    
    public void setFinishedListener(OnAnimationFinishedListener finishedListener) {
        this.finishedListener = finishedListener;
    }
    
    public boolean isRunning() {
        return running;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
    
    public void run(int val) {
        result = val;
        if (current != result) {
            start();
        }
    }
    
    public void start() {
        if (!running) {
            handler.sendEmptyMessageDelayed(1, 50);
        }
        else {
            if (finishedListener != null) {
                finishedListener.onAnimationFinished();
            }
        }
    }
    
    public void destroy() {
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }
    
    protected abstract void loop();
    
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                if (current == result) {
                    running = false;
                    if (finishedListener != null) {
                        finishedListener.onAnimationFinished();
                    }
                    
                }
                else {
                    running = true;
                    loop();
                    interListener.onInterpolate(current);
                    handler.sendEmptyMessageDelayed(1, 50);
                }
            }
        };
    };
    
    public interface OnInterpolatorListener {
        void onInterpolate(int val);
    }
    
    public interface OnAnimationFinishedListener {
        void onAnimationFinished();
    }

}
