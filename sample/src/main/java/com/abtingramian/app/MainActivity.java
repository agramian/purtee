package com.abtingramian.app;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;

import com.abtingramian.purtee.arrow.Arrow;
import com.abtingramian.purtee.shapedrawablewithborder.ShapeDrawableWithBorder;
import com.abtingramian.purtee.util.DimensionUtil;
import com.abtingramian.purtee.util.PathEffectUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // circle with border
        ShapeDrawableWithBorder circleShapeDrawable = new ShapeDrawableWithBorder(new OvalShape());
        circleShapeDrawable.getPaint().setDither(true);
        circleShapeDrawable.getPaint().setAntiAlias(true);
        circleShapeDrawable.setStrokeColor(Color.parseColor("red"));
        circleShapeDrawable.setStrokeWidth(20);
        // triangle with border
        int sizeDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        PathShape trianglePath = new PathShape(getPathTriangle(sizeDp, sizeDp), sizeDp, sizeDp);
        ShapeDrawableWithBorder triangleShapeDrawable = new ShapeDrawableWithBorder(trianglePath);
        triangleShapeDrawable.getPaint().setDither(true);
        triangleShapeDrawable.getPaint().setAntiAlias(true);
        triangleShapeDrawable.setStrokeColor(Color.parseColor("red"));
        triangleShapeDrawable.setStrokeWidth(20);
        // set background
        final View circle = findViewById(R.id.circle);
        final View triangle = findViewById(R.id.triangle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            circle.setBackground(circleShapeDrawable);
            triangle.setBackground(triangleShapeDrawable);
        } else {
            ViewCompat.setBackground(circle, circleShapeDrawable);
            ViewCompat.setBackground(triangle, triangleShapeDrawable);
        }
        // draw arrow
        circle.post(new Runnable() {
            @Override
            public void run() {
                Rect circleRect = new Rect();
                circle.getGlobalVisibleRect(circleRect);
                Rect triangleRect = new Rect();
                triangle.getGlobalVisibleRect(triangleRect);
                new Arrow.Builder(MainActivity.this)
                        .startPoint(new PointF(circleRect.left, circleRect.exactCenterY()))
                        .endPoint(new PointF(triangleRect.exactCenterX(), triangleRect.top))
                        .arrowColor(Color.GREEN)
                        .lineColorRes(R.color.colorPrimary)
                        .arrowRotationDegrees(180f)
                        .linePathEffect(new PathEffectUtil.DashPathEffectBuilder(MainActivity.this)
                                .addInterval(DimensionUtil.dpToPx(MainActivity.this, 4))
                                .addInterval(DimensionUtil.dpToPx(MainActivity.this, 8))
                                .build())
                        .build()
                        .show(MainActivity.this);
            }
        });
    }

    private Path getPathTriangle(int width, int height) {
        Path path = new Path();
        // bottom left
        path.moveTo(0, height);
        // top center tip
        path.lineTo(width / 2, 0);
        // bottom right
        path.lineTo(width, height);
        // close
        path.close();
        return path;
    }

}
