package com.example.dfashjk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.View;

public class BallView extends View {

    private float x = 100;
    private float y = 100;
    private float vx = 0;
    private float vy = 0;
    private final float radius = 50;
    private final float mass = 0.1f;
    private float gravity = 0.98f;
    private final float bounceFactor = 0.7f;
    private Bitmap bitmap;

    public BallView(Context context) {
        super(context);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.earth_img);
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (2 * radius), (int) (2 * radius), false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, x - radius, y - radius, null);
    }
    public void setImageResource(int resId) {
        bitmap = BitmapFactory.decodeResource(getResources(), resId);
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (2 * radius), (int) (2 * radius), false);
        invalidate();
    }

    public void move(float ax, float ay) {

        vx += ax / mass;
        vy += ay / mass + gravity;

        float newX = x + vx;
        float newY = y + vy;

        if (newX - radius < 0) {
            newX = radius;
            vx = -vx * bounceFactor;
        } else if (newX + radius > getWidth()) {
            newX = getWidth() - radius;
            vx = -vx * bounceFactor;
        }

        if (newY - radius < 0) {
            newY = radius;
            vy = -vy * bounceFactor;
        } else if (newY + radius > getHeight()) {
            newY = getHeight() - radius;
            vy = -vy * bounceFactor;
        }

        x = newX;
        y = newY;

        invalidate();
    }

    public void setGravity(float newGravity) {
        this.gravity = newGravity;
    }

    public float getMass() {
        return mass;
    }
}