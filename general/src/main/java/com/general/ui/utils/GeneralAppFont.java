package com.general.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GeneralAppFont {





    public static Typeface getFont(Context context, String name) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), name);
        return typeface;
    }

    public static Typeface getFont(Context context, String name, boolean isBold) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), name);
        if (isBold) {
            typeface = Typeface.create(typeface, Typeface.BOLD);
        }
        return typeface;
    }


    public static void setViewsFont(View[] views, String name, Context context) {
        Typeface typeface = getFont(context, name);
        for (View t : views) {
            if (t != null) {
                if (t instanceof Button) {
                    ((Button) t).setTypeface(typeface);
                } else if (t instanceof TextView) {
                    ((TextView) t).setTypeface(typeface);
                } else if (t instanceof EditText) {
                    ((EditText) t).setTypeface(typeface);
                } else if (t instanceof RadioButton) {
                    ((RadioButton) t).setTypeface(typeface);
                }

            }
        }
    }

    public static void setViewsFont(View[] views, String name, Context context, boolean isBold) {
        Typeface typeface = getFont(context, name, isBold);
        for (View t : views) {
            if (t != null) {
                if (t instanceof Button) {
                    ((Button) t).setTypeface(typeface);
                } else if (t instanceof TextView) {
                    ((TextView) t).setTypeface(typeface);
                } else if (t instanceof EditText) {
                    ((EditText) t).setTypeface(typeface);
                }

            }
        }
    }

    public static Typeface getFontStatic(Context context, String name) {
        return Typeface.createFromAsset(context.getAssets(), name);
    }


    public static void showAppFont(Activity appCompatActivity, View[] views, boolean isBold , String fontAppDefault ) {

        setViewsFont(views, fontAppDefault,
                appCompatActivity, isBold);
    }

    public static void showAppFont(AppCompatActivity appCompatActivity, View view , String fontAppDefault ) {
//        setViewsFont(new View[]{view}, fontAppDefault,
//                appCompatActivity);
    }



}