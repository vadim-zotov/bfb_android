package com.sphereinc.chairlift.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sphereinc.chairlift.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by veinhorn on 1.3.15.
 */

public class TagView extends LinearLayout {

    private static final int LEFT_PADDING_DEFAULT = 15;
    private static final int RIGHT_PADDING_DEFAULT = 15;
    private static final int TOP_PADDING_DEFAULT = 10;
    private static final int BOTTOM_PADDING_DEFAULT = 10;
    private static final int TEXT_COLOR_DEFAULT = Color.WHITE;
    private static final int CIRCLE_COLOR_DEFAULT = Color.WHITE;
    private static final int BORDER_RADIUS_DEFAULT = 5;
    private static final int CIRCLE_RADIUS_DEFAULT = 7;

    private static final int MODERN_TAG_MULTIPLIER = 2; // used to draw crop inside modern tag
    private static final int TRAPEZIUM_TAG_MULTIPLIER = 3; // used to draw triangle for trapezium tag


    private Paint backgroundPaint;
    private Paint circlePaint;
    private Paint trianglePaint;

    // TagView properties
    private int tagType;
    private int tagColor;
    private boolean tagUpperCase;
    private float tagBorderRadius;
    private float tagCircleRadius;
    private int tagCircleColor;
    private int tagTextColor;
    //////////////////

    private int tagLeftPadding;
    private int tagRightPadding;
    private int tagTopPadding;
    private int tagBottomPadding;


//    @Bind(R.id.tv_test)
    TextView _tvTest;

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View v = View.inflate(context, R.layout.selector_list_layout,
                null);
        addView(v);
        ButterKnife.bind(this);


        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TagView, 0, 0);
        try {
            tagColor = typedArray.getColor(R.styleable.TagView_tagColor, Color.BLACK);
            tagUpperCase = typedArray.getBoolean(R.styleable.TagView_tagUpperCase, false);
            tagBorderRadius = typedArray.getInteger(R.styleable.TagView_tagBorderRadius, BORDER_RADIUS_DEFAULT);
            tagCircleRadius = typedArray.getInteger(R.styleable.TagView_tagCircleRadius, CIRCLE_RADIUS_DEFAULT);
            tagCircleColor = typedArray.getColor(R.styleable.TagView_tagCircleColor, CIRCLE_COLOR_DEFAULT);
            tagTextColor = typedArray.getColor(R.styleable.TagView_tagTextColor, TEXT_COLOR_DEFAULT);
        } finally {
            typedArray.recycle();
        }

        initPadding();
        init();

    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(new TagDrawable());
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(new TagDrawable());
        }
    }

    private void drawModernTag(Rect bounds, Canvas canvas) {
        setPadding(tagLeftPadding * MODERN_TAG_MULTIPLIER, tagTopPadding, tagRightPadding, tagBottomPadding);
        RectF formattedBounds = getBounds(bounds);
        canvas.drawRoundRect(formattedBounds, tagBorderRadius, tagBorderRadius, backgroundPaint);
        float xPosition = formattedBounds.left + tagLeftPadding;
        float yPosition = (formattedBounds.bottom - formattedBounds.top) / 2;
        canvas.drawCircle(xPosition, yPosition, tagCircleRadius, circlePaint);
//        setTextColor(tagTextColor);
    }

    private RectF getBounds(Rect bounds) {
        return new RectF(bounds.left, bounds.top, bounds.right, bounds.bottom);
    }

    /**
     * Initializes Paint objects that will be used to draw tags, speed up draw method
     */

    private class TagDrawable extends Drawable {
        @Override
        public void setAlpha(int alpha) {
        }

        @Override
        public void draw(Canvas canvas) {
            drawModernTag(getBounds(), canvas);
        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {
        }

        @Override
        public int getOpacity() {
            return 0;
        }
    }


    private void init() {
//        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        backgroundPaint.setColor(tagColor);
//
//        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        circlePaint.setColor(tagCircleColor);
//
//        trianglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        trianglePaint.setColor(tagColor);
//        trianglePaint.setStyle(Paint.Style.FILL);
    }

    private void initPadding() {
        int left = getPaddingLeft();
        int right = getPaddingRight();
        int top = getPaddingTop();
        int bottom = getPaddingBottom();
        if (left == 0) tagLeftPadding = LEFT_PADDING_DEFAULT;
        else tagLeftPadding = left;
        if (right == 0) tagRightPadding = RIGHT_PADDING_DEFAULT;
        else tagRightPadding = right;
        if (top == 0) tagTopPadding = TOP_PADDING_DEFAULT;
        else tagTopPadding = top;
        if (bottom == 0) tagBottomPadding = BOTTOM_PADDING_DEFAULT;
        else tagBottomPadding = bottom;
    }


}