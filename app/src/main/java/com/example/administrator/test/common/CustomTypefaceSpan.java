package com.example.administrator.test.common;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;


/**
 * Created by Administrator on 8/18/2017.
 */

public class CustomTypefaceSpan extends TypefaceSpan
{
    private Typeface newType;

    public static final Creator CREATOR = new Creator() {
        @Override
        public CustomTypefaceSpan createFromParcel(Parcel parcel) {
            return new CustomTypefaceSpan(parcel);
        }

        @Override
        public CustomTypefaceSpan[] newArray(int i) {
            return new CustomTypefaceSpan[i];
        }
    };

    public CustomTypefaceSpan(Parcel parcelable)
    {
        super(parcelable);
    }

    public CustomTypefaceSpan(String family, Typeface typeface)
    {
        super(family);
        newType = typeface;
    }

    @Override
    public void updateDrawState(TextPaint drawState) {
        applyCustomTypeFace(drawState,newType);
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        applyCustomTypeFace(paint,newType);
    }

    private static void applyCustomTypeFace(Paint paint, Typeface tf)
    {
        int oldStyle;
        Typeface old = paint.getTypeface();
        if (old == null) {
            oldStyle = 0;
        } else {
            oldStyle = old.getStyle();
        }

        int fake = oldStyle & ~tf.getStyle();
        if ((fake & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }

        if ((fake & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }

        paint.setTypeface(tf);
    }
}
