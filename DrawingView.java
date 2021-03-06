package com.example.reverendcode.pictocast;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ReverendCode on 4/10/17.
 */

public class DrawingView extends View {

    private Path drawPath;
    private Paint drawPaint, canvasPaint;
    private int paintColor = 0xff000000;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private HashMap<Path,Paint> pathList;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context,attrs);
        setupDrawing();
    }
    private void setupDrawing() {
        //get drawing area setup for interaction
        drawPath = new Path();
        pathList = new HashMap<>();
        drawPaint = setupPaint(paintColor);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //do something
        super.onSizeChanged(w,h,oldw,oldh);
        canvasBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //draw stuff
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
//        for each path in my stack, call canvas.drawPath()

        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //detect user touch
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath = new Path();
//                This appears to move the start of the line to the location the finger starts
                drawPath.moveTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_MOVE:
//                This seems to create a line from the start point, to the current point
//                (on a per frame basis?)
                drawPath.lineTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_UP:
//                This sends the drawn path to the actual canvas??
//                push to the stack, and then draw?
                pathList.put(drawPath, drawPaint);
                drawCanvas.drawPath(drawPath,drawPaint);

                break;
            default:
                return false;
        }
        invalidate(); //this causes onDraw() to execute
        return true;
    }

    public Paint setupPaint(int paintColor) {
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(20);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        return drawPaint;
    }
    public void setColor(String newColor) {
        invalidate();
        drawPath = new Path();
        paintColor = Color.parseColor(newColor);
        drawPaint = setupPaint(paintColor);
    }
}
