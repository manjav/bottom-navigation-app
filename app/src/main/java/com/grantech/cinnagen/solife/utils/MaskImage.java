package com.grantech.cinnagen.solife.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

public class MaskImage extends View
{
    private final Bitmap bitmap;
    private final int width;
    private final int height;
    private final float roundPx;
    public MaskImage(Context mContext, float roundPx, Bitmap bitmap, int width, int height)
    {
        super(mContext);
        this.roundPx = roundPx;
        this.bitmap = bitmap;
        this.width = width;
        this.height = height;
    }

    @SuppressLint("DrawAllocation")
    public void onDraw(Canvas canvas)
    {
//        Bitmap output = Bitmap.createBitmap(bitmap, Bitmap.Config.ARGB_8888);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final Rect dstRect = new Rect(0, 0, getWidth(), bitmap.getHeight() * bitmap.getWidth() / getWidth());
        final RectF rectF = new RectF(dstRect);

//        canvas.drawColor(Color.WHITE);
//        paint.setAntiAlias(true);
//        canvas.drawARGB(0, 0, 0, 0);
//        paint.setColor(color);
//        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, srcRect, srcRect, paint);

//        Path clipPath = new Path();
//        clipPath.addRoundRect(new RectF(0, 0, 66, 60), roundPx, roundPx, Path.Direction.CCW);
        canvas.clipRect(new Rect(0,0,22,22));
//        super.onDraw(canvas);

//        paint.setStyle(Paint.Style.FILL);
//        canvas.drawColor(Color.WHITE);
//        paint.setColor(Color.BLUE);
//        canvas.drawCircle(20, 20, 15, paint);
    }

}
