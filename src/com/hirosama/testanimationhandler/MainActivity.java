
package com.hirosama.testanimationhandler;

import com.hirosama.animations.AnimationHandler;
import com.hirosama.animations.AnimationHandler.OnInterpolatorListener;
import com.hirosama.animations.impl.SimpleAnimationHandler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    
    TextView textView1;
    Button button1;
    
    AnimationHandler animation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        animation = new SimpleAnimationHandler(new OnInterpolatorListener() {
            @Override
            public void onInterpolate(int val) {
                textView1.setText(String.valueOf(val));
            }
        });
        
        textView1 = (TextView) findViewById(R.id.textView1);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.run(animation.getResult() + 100);
            }
        });
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        animation.destroy();
        animation = null;
    }
    
}
