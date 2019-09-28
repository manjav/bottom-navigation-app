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
import android.view.View;
import android.widget.ImageView;
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

    private float raduisMask = 8;
    private boolean prevVisibility = true;
    private String prevTime;
    private String pointStr = "0,0";
    private final Point point = new Point();

    private boolean nextVisibility = true;
    private String nextTime;

    private View prevView;
    private TextView prevText;
    private View nextView;
    private TextView nextText;
    private ConstraintLayout layout;
    private ImageView imageView;
    private Bitmap bitmap;

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
        this.imageView =    this.findViewById(R.id.imageView);
        this.bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.body);

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.InjectionBoard, defStyle, 0);
        this.setRaduisMask(a.getFloat(R.styleable.InjectionBoard_raduisMask, this.raduisMask));
        this.setPrevVisibility(a.getBoolean(R.styleable.InjectionBoard_prevVisibility, this.prevVisibility));
        this.setPoint(a.getString(R.styleable.InjectionBoard_point));
        this.setNextVisibility(a.getBoolean(R.styleable.InjectionBoard_nextVisibility, this.nextVisibility));
        a.recycle();
    }


    @Override
    public void setOnClickListener(@androidx.annotation.Nullable OnClickListener listener)
    {
        this.clickListener = listener;
        super.setOnClickListener(listener);
    }
    @Override
    public void onClick(View v)
    {
        if( this.clickListener != null )
            this.clickListener.onClick(this);
    }


    public boolean isPrevVisibility() {
        return prevVisibility;
    }
    public void setPrevVisibility(boolean prevVisibility) {
        this.prevVisibility = prevVisibility;
        this.prevView.setVisibility(prevVisibility ? View.VISIBLE : View.GONE);
    }

    public boolean isNextVisibility() {
        return nextVisibility;
    }
    public void setNextVisibility(boolean nextVisibility) {
        this.nextVisibility = nextVisibility;
        this.nextView.setVisibility(nextVisibility ? View.VISIBLE : View.GONE);
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


    public String getPoint() {
        return pointStr;
    }
    public void setPoint(String pointStr) {
        if( this.pointStr.equals(pointStr) )
            return;
        this.pointStr = pointStr;
        String[] points = pointStr.split(",");
        if( points.length < 2 )
            return;
        float density = getResources().getDisplayMetrics().density;
        this.point.x = (int) (Integer.parseInt(points[0]) * density);
        this.point.y = (int) (Integer.parseInt(points[1]) * density);
//        layout.postInvalidate();
    }

    public float getRaduisMask() {
        return raduisMask;
    }
    public void setRaduisMask(float raduisMask) {
        this.raduisMask = raduisMask;
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
        float roundPx = raduisMask * getResources().getDisplayMetrics().density;

        final Paint paint = new Paint();
        final Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final Rect dstRect = new Rect();
        if( point.x == 0 && point.y == 0 )
        {
            dstRect.bottom = layout.getHeight();
            int w = bitmap.getWidth() * layout.getHeight() / bitmap.getHeight();
            dstRect.left = (int) ((layout.getWidth() - w) * 0.5);
            dstRect.right = dstRect.left + w;
        }
        else
        {
            if( this.point.y >= REGION_RIGHT.top )
            {
                if( this.point.x >= REGION_LEFT.left )
                    dstRect.left = (int) ((layout.getWidth() - bitmap.getWidth()) * 0.5) + POINT_LEFT.x;
                else
                    dstRect.left = (int) ((layout.getWidth() - bitmap.getWidth()) * 0.5) + POINT_RIGHT.x;
                dstRect.top = POINT_RIGHT.y;
            }
            else
            {
                dstRect.left = (int) ((layout.getWidth() - bitmap.getWidth()) * 0.5);
                dstRect.top = 0;
            }

            dstRect.bottom = bitmap.getHeight() + dstRect.top;
            dstRect.right = bitmap.getWidth() + dstRect.left;
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
        canvas.drawBitmap(bitmap, srcRect, dstRect, paint);

        // draw point
        if( point.x != 0 || point.y != 0 )
        {
            paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
            canvas.drawCircle(point.x + dstRect.left, point.y + dstRect.top, 6 * getResources().getDisplayMetrics().density, paint);
        }
    }

}
