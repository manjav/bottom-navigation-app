package com.grantech.cinnagen.solife.controls;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.Fragments;

/**
 * Created by ManJav on 1/23/2019.
 */

public class InjectionBoard extends ConstraintLayout implements ConstraintLayout.OnClickListener
{
    public static Rect REGION_ABDOMEN;
    public static Rect REGION_LIMIT;
    public static Point POINT_RIGHT;
    public static Rect REGION_RIGHT;
    public static Point POINT_LEFT;
    public static Rect REGION_LEFT;

    private OnClickListener clickListener;

    private boolean touchable;
    private float radiusMask = 8;

    private String prevTime;
    private boolean prevVisibility = true;
    private boolean prevPointVisibility = true;
    private String prevPointStr = "0,0";
    private final Point prevPoint = new Point();

    private String nextTime;
    private boolean nextVisibility = true;
    private boolean nextPointVisibility = true;
    private String nextPointStr = "0,0";
    private final Point nextPoint = new Point();

    public Rect selectedRegion;
    public boolean autoRegion = true;
    private View prevView;
    private TextView prevText;
    private View nextView;
    private TextView nextText;
    private ConstraintLayout layout;
    private Bitmap bitmap;
    private Rect bodyRect;

    public InjectionBoard(Context context)
    {
        super(context);
        this.init(context, null, 0);
    }

    public InjectionBoard(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.init(context, attrs, 0);
    }

    public InjectionBoard(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle)
    {
        if( REGION_ABDOMEN == null )
        {
            float d = getResources().getDisplayMetrics().density;
            REGION_ABDOMEN = new Rect( (int) (110 * d), (int) (30 * d),     (int) (140 * d), (int) (90 * d));
            REGION_LIMIT   = new Rect( (int) (140 * d), (int) (50 * d),     (int) (220 * d), (int) (100 * d));
            POINT_RIGHT   = new Point( (int) (75 * d),  (int) (-250 * d));
            REGION_RIGHT   = new Rect( (int) (80 * d),  (int) (250 * d),	(int) (50 * d),	 (int) (90 * d));
            POINT_LEFT    = new Point( (int) (-75 * d), (int) (-250 * d) );
            REGION_LEFT    = new Rect( (int) (230 * d), (int) (250 * d),	(int) (50 * d),  (int) (90 * d));
        }

        layout = (ConstraintLayout) inflate(context, R.layout.injection_board, this);

        this.prevView =     this.findViewById(R.id.prev_view);
        this.prevText =     this.findViewById(R.id.body_prev_time);
        this.nextView =     this.findViewById(R.id.next_view);
        this.nextText =     this.findViewById(R.id.body_next_time);
        this.bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.body);

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.InjectionBoard, defStyle, 0);
        this.setTouchable(a.getBoolean(R.styleable.InjectionBoard_touchable, this.touchable));
        this.setRadiusMask(a.getFloat(R.styleable.InjectionBoard_raduisMask, this.radiusMask));

        this.setPrevPointVisibility(a.getBoolean(R.styleable.InjectionBoard_prevPointVisibility, this.prevPointVisibility));
        this.setPrevVisibility(a.getBoolean(R.styleable.InjectionBoard_prevVisibility, this.prevVisibility));
        this.setPrevPoint(a.getString(R.styleable.InjectionBoard_prevPoint));

        this.setNextPointVisibility(a.getBoolean(R.styleable.InjectionBoard_nextPointVisibility, this.nextPointVisibility));
        this.setNextVisibility(a.getBoolean(R.styleable.InjectionBoard_nextVisibility, this.nextVisibility));
        this.setNextPoint(a.getString(R.styleable.InjectionBoard_nextPoint));

        a.recycle();
    }

    public boolean isPrevVisibility() {
        return this.prevVisibility;
    }
    public void setPrevVisibility(boolean prevVisibility) {
        this.prevVisibility = prevVisibility;
        this.prevView.setVisibility(prevVisibility ? View.VISIBLE : View.GONE);
    }

    public boolean isNextVisibility() {
        return this.nextVisibility;
    }
    public void setNextVisibility(boolean nextVisibility) {
        this.nextVisibility = nextVisibility;
        this.nextView.setVisibility(nextVisibility ? View.VISIBLE : View.GONE);
    }

    public boolean isNextPointVisibility() {
        return this.nextPointVisibility;
    }
    public void setNextPointVisibility(boolean pointVisibility) {
        this.nextPointVisibility = pointVisibility;
    }

    public boolean isPrevPointVisibility() {
        return this.prevPointVisibility;
    }
    public void setPrevPointVisibility(boolean pointVisibility) {
        this.prevPointVisibility = pointVisibility;
    }

    public String getPrevTime() {
        return prevTime;
    }
    public void setPrevTime(String prevTime) {
        this.prevTime = prevTime;
        this.prevText.setText(prevTime);
    }

    public String getNextTime() {
        return nextTime;
    }
    public void setNextTime(String nextTime) {
        this.nextTime = nextTime;
        this.nextText.setText(nextTime);
    }


    public String getPrevPointString() {
        return prevPointStr;
    }
    public void setPrevPoint(String pointStr) {
        if( this.prevPointStr.equals(pointStr) )
            return;
        this.prevPointStr = pointStr;
        String[] points = pointStr.split(",");
        if( points.length < 2 )
            return;
        this.setPrevPoint(Integer.parseInt(points[0]), Integer.parseInt(points[1]));
    }
    public void setPrevPoint(int x, int y) {
        float density = getResources().getDisplayMetrics().density;
        this.prevPoint.x = (int) (x * density);
        this.prevPoint.y = (int) (y * density);
        if( prevPoint.x == 0 && prevPoint.y == 0 )
            selectedRegion = null;
        else
            selectedRegion = getRegion(prevPoint.x, prevPoint.y);
    }

    public String getNextPointString() {
        return nextPointStr;
    }
    public void setNextPoint(String pointStr) {
        if( this.nextPointStr.equals(pointStr) )
            return;
        this.nextPointStr = pointStr;
        String[] points = pointStr.split(",");
        if( points.length < 2 )
            return;
        this.setNextPoint(Integer.parseInt(points[0]), Integer.parseInt(points[1]));
    }
    public void setNextPoint(int x, int y) {
        float density = getResources().getDisplayMetrics().density;
        this.nextPoint.x = (int) (x * density);
        this.nextPoint.y = (int) (y * density);
    }

    private Rect getRegion(int x, int y) {
        if( y >= REGION_RIGHT.top ){
            if( x >= REGION_LEFT.left )
                return REGION_LEFT;
            return REGION_RIGHT;
        }
        return REGION_ABDOMEN;
    }

    public Point getNextPoint() {
        return this.nextPoint;
    }
    public Point getPrevPoint() {
        return this.prevPoint;
    }

    public float getRadiusMask() {
        return radiusMask;
    }
    public void setRadiusMask(float raduisMask) {
        this.radiusMask = raduisMask;
    }

    public boolean isTouchable() {
        return this.touchable;
    }
    public void setTouchable(boolean touchable) {
        this.touchable = touchable;
    }


    @Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        this.drawElements(bitmap, canvas);
    }

    public void drawElements(Bitmap bitmap, Canvas canvas)
    {
        float roundPx = radiusMask * getResources().getDisplayMetrics().density;

        final Paint paint = new Paint();
        final Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        if( autoRegion || bodyRect == null ){
            bodyRect = new Rect();
            if( selectedRegion == null ) {
                bodyRect.bottom = layout.getHeight();
                int w = bitmap.getWidth() * layout.getHeight() / bitmap.getHeight();
                bodyRect.left = (int) ((layout.getWidth() - w) * 0.5);
                bodyRect.right = bodyRect.left + w;
            }
            else {
                if (selectedRegion.equals(REGION_ABDOMEN)) {
                    bodyRect.left = (int) ((layout.getWidth() - bitmap.getWidth()) * 0.5);
                    bodyRect.top = 0;
                } else {
                    if (selectedRegion.equals(REGION_LEFT))
                        bodyRect.left = (int) ((layout.getWidth() - bitmap.getWidth()) * 0.5) + POINT_LEFT.x;
                    else
                        bodyRect.left = (int) ((layout.getWidth() - bitmap.getWidth()) * 0.5) + POINT_RIGHT.x;
                    bodyRect.top = POINT_RIGHT.y;
                }

                bodyRect.bottom = bitmap.getHeight() + bodyRect.top;
                bodyRect.right = bitmap.getWidth() + bodyRect.left;
                int threshold = bitmap.getHeight() - prevPoint.y - layout.getHeight();
                if( threshold < 0 ) {
                    bodyRect.top -= threshold;
                    bodyRect.bottom -= threshold;
                }
            }
        }

        // mask all elements
        paint.setAntiAlias(true);
        Path path = new Path();
        path.moveTo(0, roundPx);
        path.cubicTo(0, roundPx, 0, 0, roundPx, 0);
        path.lineTo( layout.getWidth() - roundPx,0);
        path.cubicTo(layout.getWidth() - roundPx, 0, layout.getWidth(), 0, layout.getWidth(), roundPx);
        path.lineTo( layout.getWidth(), layout.getHeight() - roundPx);
        path.cubicTo(layout.getWidth(), layout.getHeight() - roundPx, layout.getWidth(), layout.getHeight(), layout.getWidth() - roundPx, layout.getHeight());
        path.lineTo(roundPx, layout.getHeight());
        path.cubicTo(roundPx, layout.getHeight(), 0, layout.getHeight(), 0, layout.getHeight() - roundPx);
        path.close();
        canvas.clipPath(path);

        // draw body image based on position
        canvas.drawBitmap(bitmap, srcRect, bodyRect, paint);

        // draw prev prevPoint
        if( this.isPrevPointVisibility() )
        {
            paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
            canvas.drawCircle(prevPoint.x + bodyRect.left, prevPoint.y + bodyRect.top, 6 * getResources().getDisplayMetrics().density, paint);
        }

        // draw next prevPoint
        if( this.isNextPointVisibility() )
        {
            paint.setColor(getResources().getColor(R.color.colorAccentLight));
            canvas.drawCircle(nextPoint.x + bodyRect.left, nextPoint.y + bodyRect.top, 6 * getResources().getDisplayMetrics().density, paint);
        }
    }

    @Override
    public void setOnClickListener(@androidx.annotation.Nullable OnClickListener listener)
    {
        this.clickListener = listener;
        super.setOnClickListener(listener);
    }
    @Override
    public void onClick(View v) {
        if( this.clickListener != null ) this.clickListener.onClick(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if( !touchable || event.getAction() != MotionEvent.ACTION_DOWN )
            return false;
        int x = (int)(event.getX() - bodyRect.left);
        int y = (int)(event.getY() - bodyRect.top);
        Log.i(Fragments.TAG, x + " " + event.getX() + "   " + y + " " + event.getY());

        Rect region = getRegion(x, y);
        if( x < region.left )
            x = region.left;
        if( y < region.top )
            y = region.top;
        if( x > region.left + region.right)
            x = region.left + region.right;
        if( y > region.top + region.bottom )
            y = region.top + region.bottom;
        if (region.equals(REGION_ABDOMEN)) {
            if( x > REGION_LIMIT.left && x < REGION_LIMIT.right && y > REGION_LIMIT.top && y < REGION_LIMIT.bottom)
                y = REGION_LIMIT.top;
        }

        this.autoRegion = false;
        this.setNextPointVisibility(true);
        this.setNextPoint((int) (x / getResources().getDisplayMetrics().density), (int) (y / getResources().getDisplayMetrics().density));
        this.invalidate();
        return super.onTouchEvent(event);
    }
}
