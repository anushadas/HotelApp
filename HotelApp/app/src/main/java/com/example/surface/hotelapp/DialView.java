package com.example.surface.hotelapp;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
//class that draws Custom View from scratch for Sile Face in "Rank" page
public class DialView extends View {

    private float mWidth;                    // Custom view width
    private float mHeight;                   // Custom view height
    private Paint mTextPaint;                // For text in the view
    private Paint mDialPaint;                // For dial circle in the view
    //ovals for sad and happy smiles
    private RectF oval1,oval2, oval3, oval4;
    int viewWidth, viewHeight,
            whatToDraw = 2, defaultRating;

    int currEyeLX, currEyeRX, currEyeY;
    //to animate the change in the face
    ValueAnimator rightEyeAnimatorX, leftEyeAnimatorX, eyesAnimatorY;
    final long animationDuration = 300;

    //three required constructors
    public DialView(Context context) {
        super(context);
        init(context, null);
    }
    public DialView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DialView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    //initializes the variables
    private void init(Context context, AttributeSet attrs) {
        oval1 = new RectF();
        oval2 = new RectF();
        oval3 = new RectF();
        oval4 = new RectF();
        leftEyeAnimatorX = new ValueAnimator();
        rightEyeAnimatorX = new ValueAnimator();
        eyesAnimatorY = new ValueAnimator();
        //variables to set the design of eyes and mouth
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        //variables to set the design of the face
        mDialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDialPaint.setColor(Color.rgb(255, 204, 153));
        mDialPaint.setStyle(Paint.Style.FILL);
        //customize the rating attribute of the View
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DialView);
        try {
            defaultRating = typedArray.getInteger(
                    R.styleable.DialView_rating, 4);
        } finally {
            typedArray.recycle();
        }
        //defines which face to draw
        whatToDraw = defaultRating;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // Calculate the radius from the width and height.
        mWidth = w;
        mHeight = h;

        oval1.set((mWidth / 2) - (mWidth / 100 * 25), mHeight - (mHeight / 100 * 45),
                (mWidth / 2) + (mWidth / 100 * 25), mHeight - (mHeight / 100 * 10));

        oval2.set((mWidth / 2) - (mWidth / 100 * 30), mHeight - (mHeight / 100 * 60),
                (mWidth / 2) + (mWidth / 100 * 30), mHeight - (mHeight / 100 * 20));

        oval3.set((mWidth / 2) - (mWidth / 100 * 35), mHeight - (mHeight / 100 * 90),
                (mWidth / 2) + (mWidth / 100 * 35), mHeight - (mHeight / 100 * 20));

        oval4.set((mWidth / 2) - (mWidth / 100 * 40), mHeight - (mHeight / 100 * 90),
                (mWidth / 2) + (mWidth / 100 * 40), mHeight - (mHeight / 100 * 15));
    }

    //draws a Canvas object
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //draws the face
        canvas.drawCircle(250, 250, 250, mDialPaint);

        switch (whatToDraw) {
            //very sad face
            case 0:
                //eyes
                canvas.drawCircle(170, 180, 20, mTextPaint);
                canvas.drawCircle(320, 180, 20, mTextPaint);

                //mouth
                canvas.drawArc(oval1,0, -180, false, mTextPaint );
                break;
            //sad (neutral) face
            case 1:
                canvas.drawCircle(170, 180, 20, mTextPaint);
                canvas.drawCircle(320, 180, 20, mTextPaint);

                canvas.drawLine(100, 350, 400, 350, mTextPaint);
                break;
            //surprised face
            case 2:
                canvas.drawCircle(170, 180, 20, mTextPaint);
                canvas.drawCircle(320, 180, 20, mTextPaint);

                canvas.drawCircle(250, 350, 60, mTextPaint);
                break;
            //happy face
            case 3:
                canvas.drawCircle(170, 180, 20, mTextPaint);
                canvas.drawCircle(320, 180, 20, mTextPaint);

                canvas.drawArc(oval3,0, 180, false, mTextPaint );
                break;
            //super happy face
            case 4:
                canvas.drawCircle(170, 180, 20, mTextPaint);
                canvas.drawCircle(320, 180, 20, mTextPaint);

                canvas.drawArc(oval4,0, 180, false, mTextPaint );
                break;
        }
    }

    public void setRating(float rating) {
        viewWidth = Math.round(mWidth);
        viewHeight = Math.round(mHeight);
        switch ((int) rating) {
            case 0:
                whatToDraw = 0;
                startEyesAnimation((viewWidth / 2) - (viewWidth / 100 * 25),
                        (viewWidth / 2) + (viewWidth / 100 * 25),
                        (viewHeight / 100 * 20));
                break;
            case 1:
                whatToDraw = 1;
                startEyesAnimation((viewWidth / 2) - (viewWidth / 100 * 20),
                        (viewWidth / 2) + (viewWidth / 100 * 20),
                        (viewHeight / 100 * 20));
                break;
            case 2:
                whatToDraw = 2;
                startEyesAnimation((viewWidth / 2) - (viewWidth / 100 * 17),
                        (viewWidth / 2) + (viewWidth / 100 * 17),
                        (viewHeight / 100 * 25));
                break;
            case 3:
                whatToDraw = 3;
                startEyesAnimation((viewWidth / 2) - (viewWidth / 100 * 19),
                        (viewWidth / 2) + (viewWidth / 100 * 19),
                        (viewHeight / 100 * 22));
                break;
            case 4:
                whatToDraw = 4;
                startEyesAnimation((viewWidth / 2) - (viewWidth / 100 * 23),
                        (viewWidth / 2) + (viewWidth / 100 * 23),
                        (viewHeight / 100 * 23));
                break;
        }
    }

    //methods that animates the change of the face's state
    private void startEyesAnimation(int... newPositions) {

        leftEyeAnimatorX.setIntValues(currEyeLX, newPositions[0]);
        leftEyeAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currEyeLX = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        rightEyeAnimatorX.setIntValues(currEyeRX, newPositions[1]);
        rightEyeAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currEyeRX = (int) animation.getAnimatedValue();
            }
        });
        eyesAnimatorY.setIntValues(currEyeY, newPositions[2]);
        eyesAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currEyeY = (int) animation.getAnimatedValue();
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(animationDuration);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(rightEyeAnimatorX, leftEyeAnimatorX, eyesAnimatorY);
        animatorSet.start();
    }

}
