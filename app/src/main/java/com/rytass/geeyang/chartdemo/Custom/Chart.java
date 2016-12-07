package com.rytass.geeyang.chartdemo.Custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Scroller;

import com.rytass.geeyang.chartdemo.Global.L;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yangjiru on 2016/12/7.
 */

public class Chart extends View {
    private int width = 0;
    private int height = 0;

    private final static float DEFAULT_SCALE = 0.9f;
    private float scale = DEFAULT_SCALE;
    private float maxScale = 2f;
    private float minScale = 0.8f;

    private final static PointF DEFAULT_DELTA = new PointF(0f, 0f);
    private PointF delta = new PointF(DEFAULT_DELTA.x, DEFAULT_DELTA.y);

    private final static float DEFAULT_BOARD_STROKE_WIDTH = 3;
    private float boardStrokeWidth = DEFAULT_BOARD_STROKE_WIDTH;

    private final static float DEFAULT_BOARD_LINE_WIDTH = 1;
    private float boardLineWidth = DEFAULT_BOARD_LINE_WIDTH;

    private final static float DEFAULT_POINT_RADIUS = 6;
    private float pointRadius = DEFAULT_POINT_RADIUS;

    private final static float DEFAULT_POINT_VERTICAL_MARGIN = 30;
    private float pointVerticalMargin = DEFAULT_POINT_VERTICAL_MARGIN;

    private final static int DEFAULT_BOARD_COLOR = Color.parseColor("#3F51B5");
    private int boardColor = DEFAULT_BOARD_COLOR;

    private final static int DEFAULT_POINT_COLOR = Color.parseColor("#FF4081");
    private int pointColor = DEFAULT_POINT_COLOR;

    private List<Integer> values = new ArrayList<>();

    /* mocks */ {
        values.add(60);
        values.add(-30);
        values.add(80);
        values.add(20);
        values.add(-100);
        values.add(70);
        values.add(40);
        values.add(0);
    }

    private State state;

    private static enum State {NONE, DRAG, ZOOM, FLING}

    private GestureDetector gestureDetector = null;
    private ScaleGestureDetector scaleGestureDetector = null;

    private Scroller scroller = null;

    public Chart(Context context) {
        this(context, null);
    }

    public Chart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Chart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        gestureDetector = new GestureDetector(context, new GestureListener());
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
        super.setOnTouchListener(new ChartOnTouchListener());
        setState(State.NONE);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        Paint boardStrokePaint = new Paint();
        boardStrokePaint.setStrokeWidth(dpToPixel(boardStrokeWidth));
        boardStrokePaint.setColor(boardColor);
        boardStrokePaint.setStyle(Paint.Style.STROKE);
        boardStrokePaint.setAntiAlias(true);

        final Path boardBoder = new Path();
        boardBoder.moveTo(0, 0);
        boardBoder.lineTo(width, 0);
        boardBoder.lineTo(width, height);
        boardBoder.lineTo(0, height);
        boardBoder.close();

        int boardVerticalLineCount = values.size();

        Paint boardLinePaint = new Paint();
        boardLinePaint.setColor(boardColor);
        boardLinePaint.setStyle(Paint.Style.FILL);
        boardLinePaint.setAntiAlias(true);

        Path boardVerticalLines = new Path();
        for (int index = 0; index < boardVerticalLineCount; index++) {
            boardVerticalLines.moveTo((float) width / (float) (boardVerticalLineCount + 1) * (float) (index + 1) - dpToPixel(boardLineWidth) / 2f, 0);
            boardVerticalLines.lineTo((float) width / (float) (boardVerticalLineCount + 1) * (float) (index + 1) + dpToPixel(boardLineWidth) / 2f, 0);
            boardVerticalLines.lineTo((float) width / (float) (boardVerticalLineCount + 1) * (float) (index + 1) + dpToPixel(boardLineWidth) / 2f, height);
            boardVerticalLines.lineTo((float) width / (float) (boardVerticalLineCount + 1) * (float) (index + 1) - dpToPixel(boardLineWidth) / 2f, height);
            boardVerticalLines.close();
        }

        Paint pointPaint = new Paint();
        pointPaint.setColor(pointColor);
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setAntiAlias(true);

        int minValue = Collections.min(values);
        int maxValue = Collections.max(values);

        Path boardHorizontalLines = new Path();
        boardHorizontalLines.moveTo(0, height - ((float) height - 2f * dpToPixel(pointVerticalMargin)) / (float) (maxValue - minValue) * (float) (0 - minValue) - dpToPixel(pointVerticalMargin) - dpToPixel(boardLineWidth) / 2f);
        boardHorizontalLines.lineTo(width, height - ((float) height - 2f * dpToPixel(pointVerticalMargin)) / (float) (maxValue - minValue) * (float) (0 - minValue) - dpToPixel(pointVerticalMargin) - dpToPixel(boardLineWidth) / 2f);
        boardHorizontalLines.lineTo(width, height - ((float) height - 2f * dpToPixel(pointVerticalMargin)) / (float) (maxValue - minValue) * (float) (0 - minValue) - dpToPixel(pointVerticalMargin) + dpToPixel(boardLineWidth) / 2f);
        boardHorizontalLines.lineTo(0, height - ((float) height - 2f * dpToPixel(pointVerticalMargin)) / (float) (maxValue - minValue) * (float) (0 - minValue) - dpToPixel(pointVerticalMargin) + dpToPixel(boardLineWidth) / 2f);
        boardHorizontalLines.close();

        Path valuePoints = new Path();
        for (int index = 0; index < boardVerticalLineCount; index++) {
            valuePoints.addCircle((float) width / (float) (boardVerticalLineCount + 1) * (float) (index + 1),
                    height - ((float) height - 2f * dpToPixel(pointVerticalMargin)) / (float) (maxValue - minValue) * (float) (values.get(index) - minValue) - dpToPixel(pointVerticalMargin),
                    dpToPixel(pointRadius),
                    Path.Direction.CW);
        }

        /* draw something */
        Path visibleArea = new Path();
        visibleArea.addRect(0, 0, width, height, Path.Direction.CW);

        scalePath(boardBoder);
        translatePath(boardBoder);
        boardBoder.op(visibleArea, Path.Op.INTERSECT);
        canvas.drawPath(boardBoder, boardStrokePaint);

        scalePath(boardVerticalLines);
        translatePath(boardVerticalLines);
        boardVerticalLines.op(visibleArea, Path.Op.INTERSECT);
        canvas.drawPath(boardVerticalLines, boardLinePaint);

        scalePath(boardHorizontalLines);
        translatePath(boardHorizontalLines);
        boardHorizontalLines.op(visibleArea, Path.Op.INTERSECT);
        canvas.drawPath(boardHorizontalLines, boardLinePaint);

        scalePath(valuePoints);
        translatePath(valuePoints);
        valuePoints.op(visibleArea, Path.Op.INTERSECT);
        canvas.drawPath(valuePoints, pointPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);

        L.d(String.format("available size: width=%d height=%d", width, height));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private float dpToPixel(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private void scalePath(Path path) {
        RectF viewBound = new RectF(0, 0, width, height);
        scalePath(path, viewBound.centerX(), viewBound.centerY());
    }

    private void scalePath(Path path, float centerX, float centerY) {
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scale, scale, centerX, centerY);
        path.transform(scaleMatrix);
    }

    private void translatePath(Path path) {
        translatePath(path, delta.x, delta.y);
    }

    private void translatePath(Path path, float deltaX, float deltaY) {
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setTranslate(deltaX, deltaY);
        path.transform(scaleMatrix);
    }

    private void setState(State state) {
        this.state = state;
    }

    private PointF downPosition = null;
    private PointF previousDelta = null;

    private class ChartOnTouchListener implements OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            gestureDetector.onTouchEvent(motionEvent);
            scaleGestureDetector.onTouchEvent(motionEvent);

            if (state == State.NONE || state == State.DRAG || state == State.FLING) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setState(State.DRAG);
                        downPosition = new PointF(motionEvent.getX(), motionEvent.getY());
                        previousDelta = new PointF(delta.x, delta.y);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (state == State.DRAG) {
                            delta.x = previousDelta.x + motionEvent.getX() - downPosition.x;
                            delta.y = previousDelta.y + motionEvent.getY() - downPosition.y;
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        setState(State.NONE);
                        break;
                }
            }

            invalidate();
            return true;
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            L.d();
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        public ScaleListener() {
            super();
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            L.d();
            setState(State.ZOOM);
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            scale *= scaleFactor;

            scale = Math.max(minScale, scale);
            scale = Math.min(scale, maxScale);
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            L.d();
            setState(State.NONE);
            super.onScaleEnd(detector);
        }
    }
}
