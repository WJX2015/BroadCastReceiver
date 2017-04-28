package com.example.lenovo_g50_70.broadcastreceiver;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * 仿QQ音乐的旋转碟子
 * Created by lenovo-G50-70 on 2017/4/27.
 */

public class MyCycleView extends View {

    private int radius=0;//圆形图片的半径
    private int degrees=0;//图片旋转的角度
    private int mWidth=0;
    private int mHeight=0;
    private Bitmap mBitmap;//圆形图片
    private Matrix mMatrix;//旋转动画的矩形
    private Handler mHandler;//异步消息处理机制
    private Runnable mRunnable;


    public MyCycleView(Context context) {
        super(context);
        initView();
    }

    public MyCycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyCycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.image10);
        mHandler =new Handler();
        mMatrix=new Matrix();
        mRunnable =new Runnable() {//旋转动画
            @Override
            public void run() {
                mMatrix.postRotate(degrees++,radius/2,radius/2);
                //刷新重绘
                invalidate();
                mHandler.postDelayed(mRunnable,50);
            }
        };
        mHandler.post(mRunnable);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量整个View的宽和高
        mWidth=measueWidth(widthMeasureSpec);
        mHeight=measureHeight(heightMeasureSpec);
        //设置控件的大小
        setMeasuredDimension(mWidth,mHeight);
    }

    /**
     * @param widthMeasureSpec
     * @return View的宽度
     * MeasureSpec封装了父布局传递给子布局的布局要求,Mode的三种模式
     * UNSPECIFIED,父不没有对子施加任何约束，子可以是任意大小
     * EXACTLY,父决定子的确切大小，子被限定在给定的边界里，忽略本身想要的大小
     * AT_MOST,子最大可以达到的指定大小
     */
    private int measueWidth(int widthMeasureSpec){
        int Mode =MeasureSpec.getMode(widthMeasureSpec);
        int Size =MeasureSpec.getSize(widthMeasureSpec);
        if(Mode==MeasureSpec.EXACTLY){
            mWidth=Size;
        }else {
            //由图片和Padding决定高度，但是不能超过View的宽
            int value =getPaddingLeft()+getPaddingRight()+mBitmap.getWidth();
            mWidth=Math.min(value,Size);
        }
        return mWidth;
    }

    /**
     * @param heightMeasureSpec
     * @return View的高度
     */
    private int measureHeight(int heightMeasureSpec){
        int Mode =MeasureSpec.getMode(heightMeasureSpec);
        int Size =MeasureSpec.getSize(heightMeasureSpec);
        if(Mode==MeasureSpec.EXACTLY){
            mHeight=Size;
        }else {
            int value =getPaddingTop()+getPaddingTop()+mBitmap.getHeight();
            mHeight=Math.min(value,Size);
        }
        return mHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.concat(mMatrix);
        //选取宽高小的值作为圆的半径
        radius=Math.min(mWidth,mHeight);
        //根据屏幕大小合适创建图片,Scale，可缩放的意思
        mBitmap =Bitmap.createScaledBitmap(mBitmap,radius,radius,false);
        canvas.drawBitmap(createCircleImage(mBitmap,radius),0,0,null);
        mMatrix.reset();
    }

    /**
     *
     * @param bitmap
     * @param radius
     * @return  圆形图片
     */
    private Bitmap createCircleImage(Bitmap bitmap,int radius){
        Paint paint =new Paint();
        paint.setAntiAlias(true);//抗锯齿
        //Bitmap.Config,位图信息,位数越高代表其可以存储的颜色信息越多,图像也就越逼真
        Bitmap target =Bitmap.createBitmap(radius,radius, Bitmap.Config.ARGB_8888);
        Canvas canvas =new Canvas(target);
        //4参数,圆心的x坐标,圆心的y坐标,圆的半径,绘制时所使用的画笔
        canvas.drawCircle(radius/2,radius/2,radius/2,paint);
        //图形混合模式,即图片重叠一起时,选择显示哪一部分,详情看drawable/img
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap,0,0,paint);
        return target;
    }
}
