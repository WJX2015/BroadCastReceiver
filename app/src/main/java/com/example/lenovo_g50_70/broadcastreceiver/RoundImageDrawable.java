package com.example.lenovo_g50_70.broadcastreceiver;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by lenovo-G50-70 on 2017/4/3.
 */

public class RoundImageDrawable extends Drawable {

    private Paint paint;
    private Bitmap bitmap;
    private RectF rectF;

    public RoundImageDrawable(Bitmap bitmap){
        this.bitmap=bitmap;
        BitmapShader bitmapShader =new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint =new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);
    }

    @Override
    public int getIntrinsicWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return bitmap.getHeight();
    }

    @Override
    public void setBounds(int left,int top,int right,int bottom) {
        super.setBounds(left,top,right,bottom);
        rectF=new RectF(left,top,right,bottom);
    }

    @Override
    public void draw(Canvas canvas) {
        //数字是圆角大小
        canvas.drawRoundRect(rectF,400,400,paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
