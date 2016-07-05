package com.infinite.imageloadingviewsample;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.infinite.imageloadingview.ImageLoadingView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ImageLoadingView loadingView,l2;

    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingView = (ImageLoadingView) findViewById(R.id.loadingView);
        l2= (ImageLoadingView) findViewById(R.id.loadingView2);

        loadingView.setLoadingView(R.mipmap.infinite);
        loadingView.setIndeterminateColor(Color.GRAY);
        timer=new Timer("");
        timer.schedule(new MyTask(),2000,100);

    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loadingView.setProgress(msg.arg1);
            l2.setProgress(msg.arg1);
        }
    };

    private int p;
    private class MyTask extends TimerTask{

        @Override
        public void run() {
            p++;
            Message message=handler.obtainMessage();
            message.arg1=p;
            handler.sendMessage(message);
            if (p==100)
                timer.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
