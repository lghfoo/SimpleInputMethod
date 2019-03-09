package com.example.lgh.simpleinputmethod.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import com.example.lgh.simpleinputmethod.R;
import com.example.lgh.simpleinputmethod.util.MyUtil;

public class Keybutton extends android.support.v7.widget.AppCompatTextView {
    public final static int DELETE = 0;
    public final static int NEWLINE = 1;
    public final static int TO_NUMBER = 2;
    public final static int TO_SYMBOL = 3;
    public final static int RETURN_BACK = 4;
    public final static int SHIFT = 5;
    public final static int TOGGLE_EC = 6;
    public final static int LETTER = 7;
    public final static int DIGIT = 8;
    public final static int SYMBOL = 9;
    public final static int SPACE = 10;
    public final static int TOOLS = 11;
    public final static int LOCK = 12;
    public final static int SYMBOL_TYPE = 13;

    public int keyType;
    private boolean useXmlSize;
    public Keybutton(Context context) {
        super(context);
        init();
    }

    public Keybutton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Keybutton,
                0, 0);
        try {
            keyType = a.getInteger(R.styleable.Keybutton_keyType, -1);
            useXmlSize = a.getBoolean(R.styleable.Keybutton_useXmlSize, false);
            if(!useXmlSize){
                this.setWidth((int)MyUtil.convertDpToPixel(30.0f, getContext()));
                this.setHeight((int)MyUtil.convertDpToPixel(40.0f, getContext()));
            }

            TypedValue tmp = new TypedValue();
            boolean hasTextSize = a.getValue(R.styleable.Keybutton_android_textSize, tmp);
            boolean hasBackground = a.getValue(R.styleable.Keybutton_android_background, tmp);
            boolean hasTextColor = a.getValue(R.styleable.Keybutton_android_textColor, tmp);
            if(!hasTextSize)this.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            if(!hasBackground)this.setBackground(getContext().getDrawable(R.drawable.keybutton_background));
            if(!hasTextColor)this.setTextColor(getResources().getColor(android.R.color.black));
        } finally {
            a.recycle();
        }
        init();
    }

    public Keybutton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init(){
        this.setClickable(true);
        this.setOnClickListener((View.OnClickListener)getContext());
        this.setGravity(Gravity.CENTER);
    }

    void select(boolean hasCorner){
        Context context = getContext();
        this.setTextColor(context.getResources().getColor(R.color.white));
        if(!hasCorner) {
            this.setBackgroundResource(R.drawable.tool_keybutton_background_pressed);
        }
        else{
            this.setBackgroundResource(R.drawable.keybutton_background_pressed);
        }
    }

    void deselect(boolean hasCorner){
        Context context = getContext();
        this.setTextColor(context.getResources().getColor(android.R.color.black));
        if(!hasCorner) {
            this.setBackgroundResource(R.drawable.tool_keybutton_background);
        }
        else{
            this.setBackgroundResource(R.drawable.keybutton_background);
        }
    }
}
