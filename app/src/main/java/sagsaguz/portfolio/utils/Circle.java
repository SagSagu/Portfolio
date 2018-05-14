package sagsaguz.portfolio.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import sagsaguz.portfolio.R;

public class Circle extends View{

    private static final int START_ANGLE_POINT = 270;


    private final Paint paint;
    private final RectF rect;

    private float angle;

    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);

        final int strokeWidth = 25;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        //Circle color
        paint.setColor(getResources().getColor(R.color.statistics_bg));

        //size 200x200 example
        rect = new RectF(100, strokeWidth, 200 + strokeWidth, 200 + strokeWidth);

        //Initial Angle (optional, it can be zero)
        angle = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int defaultValue = 100;
        int height = canvas.getHeight() / 2;
        int width = canvas.getWidth() / 2;
        rect.set(width - defaultValue, height - defaultValue, width + defaultValue, height + defaultValue);

        canvas.drawArc(rect, START_ANGLE_POINT, angle, false, paint);
    }

    public void setColor(int color){
        paint.setColor(getResources().getColor(color));
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

}
