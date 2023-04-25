package com.university.brailleflip.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class GestureUI extends View {
    public GestureUI(Context context) {
        super(context);
    }

    public GestureUI(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GestureUI(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int point = 0;
    float firstDownX;
    float firstUpX;
    float secondDownX;
    float secondUpX;
    float secondDownY;//Press down Y-axis
    float secondUpY;//Lift up Y-axis

    int firstType;//The first touch point
    int secondType;//The second touch point

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount();

        int actionIndex = event.getActionIndex();
        int pointerId = event.getPointerId(actionIndex);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN://The first touch point type
                firstType=3;
                point = 1;
                firstDownX = event.getX();
                Log.e("Touch!", "One finger press");

                break;
            case MotionEvent.ACTION_UP:
                firstUpX = event.getX();
                if (point == 1) {//only one touch
                    if (firstDownX - firstUpX > 50) {//Slide left with one finger
                        if (operate != null) {
                            operate.cursorLeft();//Move the cursor to the left.
                        }
                        Log.e("Touch!", "Swipe left");
                    } else if (firstDownX - firstUpX < -50) {//Swipe right with one finger
                        Log.e("Touch!", "Swipe right");
                        if (operate != null) {
                            operate.cursorRight();//Move the cursor to the left.
                        }
                    } else {//touch event

                    }
                }

                if (point == 2) {//The second touch point type

                    if (firstDownX - firstUpX > 50) {//first Touch Swipe left
                        firstType = 1;
                        Log.e("Touch!", "Swipe left");
                    } else if (firstDownX - firstUpX < -50) {//first Touch Swipe right
                        firstType = 2;
                    } else {
                        firstType = 3;
                    }

                    if (Math.abs(secondDownX - secondUpX) > Math.abs(secondDownY - secondUpY)) {//If the X movement distance is greater than Y, the main direction is X-axis movement.
                        if (secondDownX - secondUpX > 50) {//one finger Swipe left
                            secondType = 1;
                            Log.e("Touch!", "Swipe left");
                        } else if (secondDownX - secondUpX < -50) {//one finger Swipe right
                            Log.e("Touch!", "Swipe right");
                            secondType = 2;
                        }
                    } else {//Y-axis movement
                        if (secondDownY - secondUpY > 50) {//swipe up
                            secondType = 3;
                            Log.e("Touch!", "up");
                        } else if (secondDownY - secondUpY < -50) {//swipe down
                            Log.e("Touch!", "down");
                            secondType = 4;
                        }
                    }


                    Log.e("type", "first point" + firstType);
                    Log.e("type", "second point" + secondType);


                    //action detection
                    if (firstType == 1 && secondType == 1) {//two finger   Swipe left
                        if (operate != null) {
                            operate.beginning();//cursor moves to start
                        }
                    }
                    if (firstType == 2 && secondType == 2) {//two finger   Swipe right
                        if (operate != null) {
                            operate.end();//cursor moves to end
                        }
                    }

                    if (firstType == 3 && secondType == 3) {//copy
                        if (operate != null) {
                            operate.copy();//copy
                        }
                    }
                    if (firstType == 3 && secondType == 4) {//paste
                        if (operate != null) {
                            operate.paste();//paste
                        }
                    }


                }


                Log.e("Touch!", "Trigger recognition action========================" + point);

                break;
            case MotionEvent.ACTION_POINTER_DOWN://greater than one touch point
                point = actionIndex + 1;//number ? of touch point
                secondDownX = event.getX(actionIndex);
                secondDownY = event.getY(actionIndex);
                Log.e("Touch!", "number" + (actionIndex + 1) + "finger pressed" + secondDownY);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                secondUpX = event.getX(actionIndex);
                secondUpY = event.getY(actionIndex);
                float x = event.getX(0);
                if (firstDownX-x>50){//swipe left

                }else if (firstDownX-x<=-50){//swipe right

                }else {
                    firstType=4;
                }
                Log.e("Touch!", "number" + (actionIndex + 1) + "finger lifted" + x);

                if (Math.abs(secondDownX - secondUpX) > Math.abs(secondDownY - secondUpY)) {//If the X movement distance is greater than Y, the main direction is X-axis movement.

                    if (secondDownX - secondUpX > 50) {//one touch Swipe left
                        secondType = 1;
                        Log.e("Touch!", "Swipe left");
                    } else if (secondDownX - secondUpX < -50) {//one touch Swipe right
                        Log.e("Touch!", "Swipe right");
                        secondType = 2;
                    }

                    if (firstType==4&&secondType == 1) {//move cursor one character left
                        if (operate != null) {
                            operate.selectLeft();//move cursor one character left
                        }
                    }
                    if (firstType==4&&secondType == 2) {//move cursor one character right
                        if (operate != null) {
                            operate.selectRight();//move cursor one character right
                        }
                    }
                }


                break;
        }

        return true;

    }

    private Operate operate;

    public void setOperate(Operate operate) {
        this.operate = operate;
    }

    public interface Operate {
        void cursorLeft();

        void cursorRight();

        void selectLeft();

        void selectRight();

        void copy();

        void paste();

        void beginning();

        void end();

    }
}
