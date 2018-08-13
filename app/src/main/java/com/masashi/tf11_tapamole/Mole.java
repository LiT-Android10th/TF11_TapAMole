package com.masashi.tf11_tapamole;

import android.widget.ImageView;

/**
 * Created by Masashi Hamaguchi on 2018/08/12.
 */

public class Mole {

    private int state;

    // UI
    private ImageView moleImage;

    private android.os.Handler h;
    private Runnable hide;

    public Mole(ImageView imageView) {
        state = 0;
        moleImage = imageView;
        moleImage.setImageResource(R.drawable.mole1);

        h = new android.os.Handler();
        hide = new Runnable() {
            @Override
            public void run() {
                state = 0;
                moleImage.setImageResource(R.drawable.mole1);
            }
        };
    }

    public void start() {
        if (state == 0) {
            state = 1;
            moleImage.setImageResource(R.drawable.mole2);
            h.postDelayed(hide, 1000);
        }
    }

    public int tapMole() {
        if (state == 1) {
            state = 2;
            moleImage.setImageResource(R.drawable.mole3);

            h.removeCallbacks(hide); // start時のpostを消去
            h.postDelayed(hide, 1000);
            return 1; // スコア（1点）を返す
        }
        return  0; // スコア（0点）を返す
    }

}
