package com.hirosama.animations;

import android.annotation.SuppressLint;
import android.os.Handler;

public abstract class AdvanceAnimationHandler<T> {
    
    protected OnInterpolatorListener<T> interListener = null;
    protected OnAnimationFinishedListener finishedListener = null;
    
    protected T current = null;
    protected T result = null;
    protected boolean running = false;
    
    public AdvanceAnimationHandler(OnInterpolatorListener<T> listener) {
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

    public T getCurrent() {
        return current;
    }

    public void setCurrent(T current) {
        this.current = current;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
    
    public void run(T val) {
        result = val;
        if (!current.equals(result)) {
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
                if (current.equals(result)) {
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
    
    public interface OnInterpolatorListener<T> {
        void onInterpolate(T val);
    }
    
    public interface OnAnimationFinishedListener {
        void onAnimationFinished();
    }

}
