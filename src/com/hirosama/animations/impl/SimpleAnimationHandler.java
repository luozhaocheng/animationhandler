
package com.hirosama.animations.impl;

import com.hirosama.animations.AnimationHandler;

public class SimpleAnimationHandler extends AnimationHandler {

    public SimpleAnimationHandler(OnInterpolatorListener listener) {
        super(listener);
    }

    @Override
    protected void loop() {
        int d, step;
        if (current < result) {
            d = (result - current) / 15;
            step = d > 7 ? d : 7;
            if (current + step > result) {
                current = result;
            }
            else {
                current += step;
            }
        }
        else {
            d = (current - result) / 15;
            step = d > 7 ? d : 7;
            if (current - step < result) {
                current = result;
            }
            else {
                current -= step;
            }
        }
    }

}
