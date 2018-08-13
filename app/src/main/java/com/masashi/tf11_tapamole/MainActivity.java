package com.masashi.tf11_tapamole;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView scoreText, timeText;

    int[] imageResource = {R.id.imageView, R.id.imageView2, R.id.imageView3,
                            R.id.imageView4, R.id.imageView5, R.id.imageView6,
                            R.id.imageView7, R.id.imageView8, R.id.imageView9,
                            R.id.imageView10, R.id.imageView11, R.id.imageView12};


    Mole moles[];               // モグラの配列

    int time, score;            // 時間、スコアの関数

    Timer timer;                // タイマー
    TimerTask timerTask;        // タイマーで処理する内容
    Handler h;                  // ハンドラー（タイマーからUIスレッドへの通信用）
    Random random = new Random();   // ランダムな数字を発生させる

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ひもづけ
        scoreText = findViewById(R.id.scoreText);
        timeText = findViewById(R.id.timeText);

        // モグラの数だけモグラの配列を作る
        moles = new Mole[12];
        for (int i = 0; i < 12; i++) {
            // レイアウトのImageViewを1つずつ取り出し
            ImageView imageView = findViewById(imageResource[i]);
            // imageViewをつかってi番目のもぐらのインスタンスを生成
            moles[i] = new Mole(imageView);

        }

        // Handlerのインスタンスを生成
        h = new Handler();

    }

    public void start(View v) {
        time = 60;  // タイマーの初期化
        score = 0;  // スコアの初期化
        timeText.setText(String.valueOf(time));
        scoreText.setText(String.valueOf(score));

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                // TODO 一定時間ごとに行う処理を書く
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        // 0 <= r < 12のランダムな数字を生成
                        int r = random.nextInt(12);
                        // r番目のもぐらが飛び出す
                        moles[r].start();

                        time = time - 1;    // 時間を1減らす
                        timeText.setText(String.valueOf(time));
                        // 時間が0以下になったら
                        if (time <= 0) {
                            timer.cancel(); // タイマーを終了する
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    public void tapMole(View v) {
        // たたいたImageViewのタグを取得
        String tag_str = (String) v.getTag();
        // tag_strをint型（整数）に変換
        int tag_int = Integer.valueOf(tag_str);

        // 対応したもぐらを叩く
        score += moles[tag_int].tapMole();

        scoreText.setText(String.valueOf(score));
    }
}
