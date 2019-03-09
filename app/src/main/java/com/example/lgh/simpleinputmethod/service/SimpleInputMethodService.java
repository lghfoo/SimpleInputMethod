package com.example.lgh.simpleinputmethod.service;

import android.inputmethodservice.InputMethodService;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;

import com.example.lgh.simpleinputmethod.R;
import com.example.lgh.simpleinputmethod.activity.MainActivity;
import com.example.lgh.simpleinputmethod.adapter.CandidateViewAdapter;
import com.example.lgh.simpleinputmethod.custom.Keybutton;
import com.example.lgh.simpleinputmethod.custom.SimpleInputMethodKeyboardView;
import com.example.lgh.simpleinputmethod.databases.WubiDatabase;
import com.example.lgh.simpleinputmethod.test.Test;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class SimpleInputMethodService extends InputMethodService implements View.OnClickListener{
    boolean shiftPressed = false;
    boolean ECState = true;
    String pressedKeys = "";
    List<String> candidates = new ArrayList<>();
    WubiDatabase db;

    View view;
    InputConnection inputConnection;
    SimpleInputMethodKeyboardView keyboardView;
    View toolView, candidateView, activeView;
    TextView pressedView;
    RecyclerView candidatesRV;
    @Override
    public View onCreateInputView(){
        view = getLayoutInflater().inflate(R.layout.keyboard, null);
        init();
        return view;
    }

    void init(){
        if(view == null) {
            Log.d(MainActivity.TAG, "error: view is null");
            return;
        }
        inputConnection = this.getCurrentInputConnection();
        db = WubiDatabase.getInstance(this);
        keyboardView = view.findViewById(R.id.keyboard_kv);
        pressedView = view.findViewById(R.id.pressed_key_tv);
        toolView = view.findViewById(R.id.tool_ll);
        activeView = toolView;
        candidateView = view.findViewById(R.id.candidate_ll);
        candidatesRV = view.findViewById(R.id.candidate_rv);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(OrientationHelper.HORIZONTAL);
        candidatesRV.setLayoutManager(layoutManager);
        candidatesRV.setAdapter(new CandidateViewAdapter(candidates));
        ((CandidateViewAdapter)candidatesRV.getAdapter()).setOnItemClickListener(new CandidateViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = v.findViewById(R.id.content);
                if(tv != null){
                    String content = tv.getText().toString();
                    putContent(content);
                }
                else{
                    Log.d(MainActivity.TAG, "tv is null");
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        if(!(v instanceof Keybutton))return;
        Keybutton btn = (Keybutton)v;
        switch (btn.keyType){
            case Keybutton.DELETE:
                if(!pressedKeys.isEmpty()){
                    setPressedKeys(pressedKeys.substring(0, pressedKeys.length() - 1));
                    if(pressedKeys.isEmpty()){
                        cancelCandidate();
                    }
                    else {
                        updateCandidates();
                    }
                }
                else {
                    inputConnection.deleteSurroundingText(1, 0);
                }
                break;
            case Keybutton.SHIFT:
                shiftPressed = !shiftPressed;
                toggleBtn(btn, shiftPressed);
                break;
            case Keybutton.NEWLINE:
                inputConnection.commitText("\n", 1);
                break;
            case Keybutton.TO_NUMBER:
                if(!pressedKeys.isEmpty()) {
                    cancelCandidate();
                }
                keyboardView.setKeyboard(Keybutton.DIGIT);
                break;
            case Keybutton.TO_SYMBOL:
                if(!pressedKeys.isEmpty()){
                    cancelCandidate();
                }
                keyboardView.setKeyboard(Keybutton.SYMBOL);
                break;
            case Keybutton.RETURN_BACK:
                keyboardView.setKeyboard(Keybutton.LETTER);
                break;
            case Keybutton.TOGGLE_EC:
                ECState = !ECState;
                cancelCandidate();
                toggleBtn(btn, ECState);
                break;
            case Keybutton.SPACE:
                if(ECState){
                    if(!candidates.isEmpty()) {
                        putContent(candidates.get(0));
                    }
                    else{
                        if(!pressedKeys.isEmpty()){
                            setPressedKeys("");
                        }
                        inputConnection.commitText(" ", 1);
                    }
                }
                else{
                    inputConnection.commitText(" ", 1);
                }
                break;
            case Keybutton.TOOLS:
                switch (btn.getId()){
                    case R.id.tool_fold:
                        requestHideSelf(0);
                        break;

                    case R.id.cancel_candidate_tv:
                        cancelCandidate();
                        break;
                    default:

                }
                break;
            default:
                String str = btn.getText().toString();
                if(str.length() == 1) {
                    Character ch = str.charAt(0);
                    //中文
                    if(!shiftPressed && ECState){
                        if(Character.isLetter(ch)) {
                            setPressedKeys(pressedKeys + ch);
                            updateCandidates();
                        }
                        else{
                            inputConnection.commitText(ch.toString(), 1);
                        }
                    }
                    else {
                        //英文
                        if (shiftPressed) {
                            ch = Character.toUpperCase(ch);
                        }
                        inputConnection.commitText(ch.toString(), 1);
                    }
                }
                else{
                    inputConnection.commitText(str, 1);
                }
        }
    }

    void updateCandidates(){
        if(activeView == toolView){
            activeView.setVisibility(View.GONE);
            activeView = candidateView;
            activeView.setVisibility(View.VISIBLE);
        }
        candidates.clear();
        candidates.addAll(db.getValues(pressedKeys));
        candidatesRV.getAdapter().notifyDataSetChanged();
        candidatesRV.scrollToPosition(0);
    }

    void cancelCandidate(){
        if(activeView == candidateView){
            activeView.setVisibility(View.GONE);
            activeView = toolView;
            activeView.setVisibility(View.VISIBLE);
        }

        candidates.clear();
        candidatesRV.getAdapter().notifyDataSetChanged();
        setPressedKeys("");
    }

    void setPressedKeys(String pressedKeys){
        this.pressedKeys = pressedKeys;
        pressedView.setText(pressedKeys);
    }

    void putContent(String content){
        inputConnection.commitText(content, 1);
        candidates.clear();
        cancelCandidate();
    }

    @Override
    public void onStartInput(EditorInfo info, boolean restarting){
        super.onStartInput(info, restarting);
        inputConnection = this.getCurrentInputConnection();
    }

    void toggleBtn(View btn, boolean flag){
        if(flag){
            ((TextView)btn).setTextColor(getResources().getColor(R.color.white));
            btn.setBackgroundResource(R.drawable.keybutton_background_pressed);
        }
        else{
            ((TextView)btn).setTextColor(getResources().getColor(android.R.color.black));
            btn.setBackgroundResource(R.drawable.keybutton_background);
        }
    }
}
