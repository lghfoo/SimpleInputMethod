package com.example.lgh.simpleinputmethod.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.lgh.simpleinputmethod.R;

public class SimpleInputMethodKeyboardView extends FrameLayout{
    final static String TAG = "SimpleInputMethod";

    public SimpleInputMethodKeyboardView(Context context) {
        super(context);
        init();
    }

    public SimpleInputMethodKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleInputMethodKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LinearLayout numberKeyboard, primaryKeyboard;
    private SymbolKeyboardViewWrapper symbolKeyboardViewWrapper;
    private void init(){
        numberKeyboard = (LinearLayout)inflate(getContext(), R.layout.keyboard_number, null);
        primaryKeyboard = (LinearLayout)inflate(getContext(), R.layout.keyboard_primary, null);
        symbolKeyboardViewWrapper = new SymbolKeyboardViewWrapper(getContext());
        setKeyboard(Keybutton.LETTER);
    }

//    public void setKeyboard(View keyboard){
//        this.removeAllViews();
//        this.addView(keyboard);
//    }

    public void setKeyboard(int keyboard){
        this.removeAllViews();
        switch (keyboard){
            case Keybutton.DIGIT:
                this.addView(numberKeyboard);
                break;
            case Keybutton.LETTER:
                this.addView(primaryKeyboard);
                break;
            case Keybutton.SYMBOL:
                this.addView(symbolKeyboardViewWrapper.getView());
                break;
            default:
        }
    }

//    void setKeybuttonListener(View v){
//        if(v instanceof Keybutton){
//            v.setOnClickListener(this);
//            return;
//        }
//
//        ViewGroup group = null;
//        if(v instanceof ViewGroup){
//            group = (ViewGroup)v;
//        }
//        else{
//            return;
//        }
//
//        int cnt = group.getChildCount();
//        for(int i = 0; i < cnt; i++){
//            setKeybuttonListener(group.getChildAt(i));
//        }
//    }
//

}
