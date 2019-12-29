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
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.grantech.cinnagen.solife.R;

/**
 * Created by ManJav on 1/23/2019.
 */

public class InjectionBoard extends ConstraintLayout implements ConstraintLayout.OnClickListener
{
    public static Rect REGION_ABDOMEN;
    public static Point POINT_RIGHT;
    public static Rect REGION_RIGHT;
    public static Point POINT_LEFT;
    public static Rect REGION_LEFT;

    private OnClickListener clickListener;

    private float radiusMask = 8;
    private boolean pointVisibility;
    private String pointStr = "0,0";
    private final Point point = new Point();
    private boolean prevVisibility = true;
    private String prevTime;
    private boolean nextVisibility = true;
    private String nextTime;
    private boolean touchable;

    private Rect selectedRegion;
    private boolean autoRegion = true;
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
            REGION_ABDOMEN = new Rect( (int) (100 * d), (int) (25 * d),     (int) (160 * d), (int) (95 * d));
            POINT_RIGHT   = new Point( (int) (75 * d),  (int) (-230 * d));
            REGION_RIGHT   = new Rect( (int) (70 * d),  (int) (250 * d),	(int) (70 * d),	 (int) (95 * d));
            POINT_LEFT    = new Point( (int) (-75 * d), (int) (-230 * d) );
            REGION_LEFT    = new Rect( (int) (220 * d), (int) (250 * d),	(int) (70 * d),  (int) (95 * d));
        }

        layout = (ConstraintLayout) inflate(context, R.layout.injection_board, this);

        this.prevView =     this.findViewById(R.id.prev_view);
        this.prevText =     this.findViewById(R.id.body_prev_time);
        this.nextView =     this.findViewById(R.id.next_view);
        this.nextText =     this.findViewById(R.id.body_next_time);
        this.bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.body);

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.InjectionBoard, defStyle, 0);
        this.setPointVisibility(a.getBoolean(R.styleable.InjectionBoard_pointVisibility, this.pointVisibility));
        this.setRadiusMask(a.getFloat(R.styleable.InjectionBoard_raduisMask, this.radiusMask));
        this.setPrevVisibility(a.getBoolean(R.styleable.InjectionBoard_prevVisibility, this.prevVisibility));
        this.setPoint(a.getString(R.styleable.InjectionBoard_point));
        this.setNextVisibility(a.getBoolean(R.styleable.InjectionBoard_nextVisibility, this.nextVisibility));
        this.setTouchable(a.getBoolean(R.styleable.InjectionBoard_touchable, this.touchable));
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

    public boolean isPointVisibility() {
        return this.pointVisibility;
    }
    public void setPointVisibility(boolean pointVisibility) {
        this.pointVisibility = pointVisibility;
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


    public String getPointString() {
        return pointStr;
    }
    public void setPoint(String pointStr) {
        if( this.pointStr.equals(pointStr) )
            return;
        this.pointStr = pointStr;
        String[] points = pointStr.split(",");
        if( points.length < 2 )
            return;
        this.setPoint(Integer.parseInt(points[0]), Integer.parseInt(points[1]));
    }
    public void setPoint(int x, int y) {
        float density = getResources().getDisplayMetrics().density;
        this.point.x = (int) (x * density);
        this.point.y = (int) (y * density);
    }
    public Point getPoint() {
        return this.point;
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
        if( autoRegion || bodyRect == null )
        {
            bodyRect = new Rect();
            if( point.x == 0 && point.y == 0 )
            {
                bodyRect.bottom = layout.getHeight();
                int w = bitmap.getWidth() * layout.getHeight() / bitmap.getHeight();
                bodyRect.left = (int) ((layout.getWidth() - w) * 0.5);
                bodyRect.right = bodyRect.left + w;
                selectedRegion = null;
            }
            else
            {
                if( this.point.y >= REGION_RIGHT.top )
                {
                    if( this.point.x >= REGION_LEFT.left )
                    {
                        selectedRegion = REGION_LEFT;
                        bodyRect.left = (int) ((layout.getWidth() - bitmap.getWidth()) * 0.5) + POINT_LEFT.x;
                    }
                    else
                    {
                        selectedRegion = REGION_RIGHT;
                        bodyRect.left = (int) ((layout.getWidth() - bitmap.getWidth()) * 0.5) + POINT_RIGHT.x;
                    }
                    bodyRect.top = POINT_RIGHT.y;
                }
                else
                {
                    selectedRegion = REGION_ABDOMEN;
                    bodyRect.left = (int) ((layout.getWidth() - bitmap.getWidth()) * 0.5);
                    bodyRect.top = 0;
                }

                bodyRect.bottom = bitmap.getHeight() + bodyRect.top;
                bodyRect.right = bitmap.getWidth() + bodyRect.left;

                int threshold = bitmap.getHeight() - point.y - layout.getHeight();
                if( threshold < 0 )
                {
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

        // draw point
        if( this.pointVisibility && (point.x != 0 || point.y != 0) )
        {
            paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
            canvas.drawCircle(point.x + bodyRect.left, point.y + bodyRect.top, 6 * getResources().getDisplayMetrics().density, paint);
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
        if( x < selectedRegion.left || y < selectedRegion.top || x > selectedRegion.left + selectedRegion.right || y > selectedRegion.top + selectedRegion.bottom )
            return false;

        this.autoRegion = false;
        this.setPointVisibility(true);
        this.setPoint((int) (x / getResources().getDisplayMetrics().density), (int) (y / getResources().getDisplayMetrics().density));
        this.invalidate();
//        Log.i(Fragments.TAG, selectedRegion + " == " + point);
        return super.onTouchEvent(event);
    }
}
