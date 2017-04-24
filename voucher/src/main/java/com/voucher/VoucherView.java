package com.voucher;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;



/**
 * @TODO<自定义优惠券控件>
 * @author 小嵩
 * @date 2017-4-14
 */

public class VoucherView extends LinearLayout {
    private Paint mPaint;

    //item间距 默认是5dp
    private float mGap;

    //绘制的图层
    private Bitmap mBitmap;
    private Canvas mCanvas;

    //item半径 默认是10dp
    private float mRadius ;

    //item数量
    private int mCircleNum_H;
    private int mCircleNum_V;

    //除过item和间隙外多余出来的部分
    private float mRemain_H;//水平
    private float mRemain_V;//垂直


    //画笔颜色
    private int mPaintColor;

    //指定绘制的方向
    private int mOrientation;
    public final static int DRAW_HORIZONTAL = 0;//水平
    public final static int DRAW_VERTICAL = 1;//垂直
    public final static int DRAW_ＡROUND = 2;//全部

    //锯齿形状 （圆形，椭圆，三角形，正方形）
    private int drawType;
    private static final int CIRCLE = 0;
    private static final int ELLIPSE = 1;
    private static final int TRIANGLE = 2;
    private static final int SQUARE = 3;



    @Override
    public void setOrientation(int orientation) {
    }

    public VoucherView(Context context) {
        this(context,null);
    }

    public VoucherView(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public VoucherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.scratchView, 0, 0);
            drawType = a.getInt(R.styleable.scratchView_drawType, CIRCLE);
            mOrientation = a.getInt(R.styleable.scratchView_orientation,DRAW_HORIZONTAL);//默认水平方向
            mGap = a.getDimensionPixelOffset(R.styleable.scratchView_mGap, 5);
            mRadius = a.getDimensionPixelOffset(R.styleable.scratchView_mRadius, 10);
            mPaintColor = a.getColor(R.styleable.scratchView_BgColor, 0xFFc0c0c0);
            a.recycle();//回收内存
        }

        initPaint();
    }

    private void initPaint() {//边缘锯齿画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mPaint.setStyle(Paint.Style.FILL);
    }

    /**
     *  item数量的 计算公式 ：
     *  circleNum = (int) ((w-gap)/(2*radius+gap));
     */

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        initDrawCanvas(w, h);

        switch (mOrientation){
            case DRAW_HORIZONTAL:
                measureHorNum(w);
                break;
            case DRAW_VERTICAL:
                measureVelNum(h);
                break;
            case DRAW_ＡROUND:
                measureHorNum(w);
                measureVelNum(h);
                break;
        }
    }


    /**
     * 初始化绘制图层
     * @param w
     * @param h
     */
    private void initDrawCanvas(int w, int h) {

        if (getBackground()==null){//背景未设置情况下，设置为透明背景
            setBackgroundColor(Color.TRANSPARENT);
        }

        // 初始化锯齿遮盖图层
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        // 绘制图层颜色
        mCanvas.drawColor(mPaintColor);
    }

    /**
     * 测量水平的item数目
     * @param w
     */
    private void measureHorNum(int w) {
        if(mRemain_H==0){
            mRemain_H=(w-mGap)%(mRadius*2+mGap);
        }
        mCircleNum_H=(int)((w-mGap)/(mRadius*2+mGap));
    }
    /**
     * 测量垂直item数目
     * @param h
     */
    private void measureVelNum(int h) {
        if(mRemain_V==0){
            mRemain_V=(h-mGap)%(mRadius*2+mGap);
        }
        mCircleNum_V=(int)((h-mGap)/(mRadius*2+mGap));
    }


    /**
     * 绘制锯齿
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, null);//绘制图层

       switch (mOrientation){

           /**
            * 水平方向
            */
           case DRAW_HORIZONTAL:
               if (drawType==CIRCLE){//圆形
                   drawHorCircle();
               }else if (drawType==ELLIPSE){//椭圆
                   drawHorEllipse();
               }else if(drawType==TRIANGLE){//三角形
                   drawHorTriangle();
               }else if(drawType==SQUARE){//正方形
                   drawHorSquare();
               }
               break;

           /**
            * 垂直方向
            */
           case DRAW_VERTICAL:
               if (drawType==CIRCLE){//圆形
                   drawVelCircle();
               }else if (drawType==ELLIPSE){//椭圆
                   drawVelEllipse();
               }else if(drawType==TRIANGLE){//三角形
                   drawVelTriangle();
               }else if(drawType==SQUARE){//正方形
                   drawVelSquare();
               }
               break;

           /**
            * 四周方向
            */
           case DRAW_ＡROUND:
               if (drawType==CIRCLE){//圆形
                   drawHorCircle();
                   drawVelCircle();
               }else if (drawType==ELLIPSE){//椭圆
                   drawHorEllipse();
                   drawVelEllipse();
               }else if (drawType==TRIANGLE){//三角形
                   drawHorTriangle();
                   drawVelTriangle();
               }else if(drawType==SQUARE){//正方形
                   drawHorSquare();
                   drawVelSquare();
               }
               break;
       }
    }


    ////***********************************************************////

    /**
     * 绘制水平的圆
     */
    private void drawHorCircle() {
        for (int i=0;i<mCircleNum_H;i++){
            float x = mGap+mRadius+mRemain_H/2+((mGap+mRadius*2)*i);
            mCanvas.drawCircle(x,0,mRadius,mPaint);
            mCanvas.drawCircle(x,getHeight(),mRadius,mPaint);
        }
    }

    /**
     * 绘制水平的椭圆
     */
    private void drawHorEllipse() {
        for (int i=0;i<mCircleNum_H;i++){
            float x = mGap+mRadius+mRemain_H/2+((mGap+mRadius*2)*i);
            // 定义椭圆对象
            RectF rectf = new RectF();
            // 设置椭圆大小
            rectf.left = x-mRadius;
            rectf.right = x+mRadius;
            rectf.top = 0;
            rectf.bottom = mRadius;
            // 绘制上面的椭圆
            mCanvas.drawOval(rectf, mPaint);
            rectf.top = getHeight()-mRadius;
            rectf.bottom = getHeight();
            // 绘制下面的椭圆
            mCanvas.drawOval(rectf, mPaint);
        }
    }

    /**
     * 绘制水平的三角形
     */
    private void drawHorTriangle() {
        for (int i=0;i<mCircleNum_H;i++){
            float x = mGap+mRadius+mRemain_H/2+((mGap+mRadius*2)*i);
            // 绘制三角形
            Path path = new Path();
            // 设置多边形的点
            path.moveTo(x-mRadius,0);
            path.lineTo(x+mRadius,0);
            path.lineTo(x, mRadius);
            path.lineTo(x-mRadius,0);
            // 使这些点构成封闭的多边形
            path.close();
            mCanvas.drawPath(path,mPaint);

            //绘制下边缘
            path.moveTo(x-mRadius,getHeight());
            path.lineTo(x+mRadius,getHeight());
            path.lineTo(x,getHeight()-mRadius);
            path.lineTo(x-mRadius,getHeight());
            // 使这些点构成封闭的多边形
            path.close();
            mCanvas.drawPath(path,mPaint);
        }
    }
    /**
     * 绘制水平的正方形
     */
    private void drawHorSquare() {
        for (int i=0;i<mCircleNum_H;i++){
            float x = mGap+mRadius+mRemain_H/2+((mGap+mRadius*2)*i);

            mCanvas.drawRect(0,x,0,mRadius,mPaint);
            // 定义正方形对象
            RectF rectf = new RectF();
            // 设置正方形大小
            rectf.left = x-mRadius/2;
            rectf.right = x+mRadius/2;
            rectf.top = 0;
            rectf.bottom = mRadius;
            // 绘制上面的正方形
            mCanvas.drawRect(rectf, mPaint);
            rectf.top = getHeight()-mRadius;
            rectf.bottom = getHeight();
            // 绘制下面的正方形
            mCanvas.drawRect(rectf, mPaint);
        }
    }

    ////***********************************************************////

    /**
     * 绘制垂直的圆
     */
    private void drawVelCircle() {
        for (int i=0;i<mCircleNum_V;i++){
            float y = mGap+mRadius+mRemain_V/2+((mGap+mRadius*2)*i);
            mCanvas.drawCircle(0,y,mRadius,mPaint);
            mCanvas.drawCircle(getWidth(),y,mRadius,mPaint);
        }
    }


    /**
     * 绘制垂直的椭圆
     */
    private void drawVelEllipse() {
        for (int i=0;i<mCircleNum_V;i++){
            float y = mGap+mRadius+mRemain_V/2+((mGap+mRadius*2)*i);
            // 定义椭圆对象
            RectF rectf = new RectF();
            // 设置椭圆大小
            rectf.left = 0;
            rectf.right = mRadius;
            rectf.top = y-mRadius;
            rectf.bottom = y+mRadius;
            // 绘制椭圆
            mCanvas.drawOval(rectf, mPaint);
            rectf.left = getWidth()-mRadius;
            rectf.right = getWidth();
            // 绘制椭圆
            mCanvas.drawOval(rectf, mPaint);
        }
    }

    /**
     * 绘制垂直的三角形
     */
    private void drawVelTriangle() {
        for (int i=0;i<mCircleNum_V;i++){
            float y = mGap+mRadius+mRemain_V/2+((mGap+mRadius*2)*i);
            // 绘制三角形
            Path path = new Path();
            // 设置多边形的点
            path.moveTo(0,y-mRadius);
            path.lineTo(0,y+mRadius);
            path.lineTo(mRadius,y);
            path.lineTo(0,y-mRadius);
            // 使这些点构成封闭的多边形
            path.close();
            mCanvas.drawPath(path,mPaint);

            //绘制下边缘
            path.moveTo(getWidth(),y-mRadius);
            path.lineTo(getWidth(),y+mRadius);
            path.lineTo(getWidth()-mRadius,y);
            path.lineTo(getWidth(),y-mRadius);
            // 使这些点构成封闭的多边形
            path.close();
            mCanvas.drawPath(path,mPaint);
        }
    }


    /**
     * 绘制垂直的椭圆
     */
    private void drawVelSquare() {
        for (int i=0;i<mCircleNum_V;i++){
            float y = mGap+mRadius+mRemain_V/2+((mGap+mRadius*2)*i);
            // 定义椭圆对象
            RectF rectf = new RectF();
            // 设置椭圆大小
            rectf.left = 0;
            rectf.right = mRadius/2;
            rectf.top = y-mRadius/2;
            rectf.bottom = y+mRadius;
            // 绘制椭圆
            mCanvas.drawRect(rectf, mPaint);
            rectf.left = getWidth()-mRadius;
            rectf.right = getWidth();
            // 绘制椭圆
            mCanvas.drawRect(rectf, mPaint);
        }
    }




    /**
     * ------------- 设置属性的方法 -------------
     **/

  /*  public void setRadius(float radius) {
        mRadius = radius;
    }

    public void setGap(float gap) {
        mGap = gap;
    }

    public void setColor(int color) {
        mPaintColor = color;
    }

    public void setDrawType( int Type) {
        this.drawType = Type;
    }*/

}
