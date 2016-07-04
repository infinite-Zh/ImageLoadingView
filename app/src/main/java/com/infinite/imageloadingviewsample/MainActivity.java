package com.infinite.imageloadingviewsample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.infinite.imageloadingview.ImageLoadingView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ImageLoadingView loadingView;

    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingView = (ImageLoadingView) findViewById(R.id.loadingView);
//        for(int i=0;i<20;i++){
//            final int p=i;
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Message message=handler.obtainMessage();
//                    message.arg1=p;
//                    handler.sendMessage(message);
//                }
//            },1000);
//        }
        timer=new Timer("");
        timer.schedule(new MyTask(),0,100);

    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loadingView.setProgress(msg.arg1*5);
            Log.e("ar1",msg.arg1+"");
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
