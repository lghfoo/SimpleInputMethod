package com.example.lgh.simpleinputmethod.custom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.example.lgh.simpleinputmethod.R;
import com.example.lgh.simpleinputmethod.adapter.SymbolsViewAdapter;
import com.example.lgh.simpleinputmethod.databases.SymbolDatabase;
import com.example.lgh.simpleinputmethod.model.Symbol;

import java.util.ArrayList;
import java.util.List;

public class SymbolKeyboardViewWrapper implements View.OnClickListener {
    private Context context;
    private boolean isLock;
    private Keybutton lockBtn;
    private LinearLayout symbolKeyboardView, tabLayout;
    private RecyclerView symbolsContainer;
    private List<Symbol>symbols = new ArrayList<>();
    private SymbolsViewAdapter adapter = new SymbolsViewAdapter(symbols);
    private SymbolDatabase symbolDatabase;
    private static final int SYMBOL_ROW_COUNT = 3;

    //无法使用TabLayout......
    private List<Keybutton> tabs = new ArrayList<>();
    private Keybutton selection;
    public SymbolKeyboardViewWrapper(Context context){
        this.context = context;
        symbolDatabase = SymbolDatabase.getInstance(context);
        symbolKeyboardView = (LinearLayout)View.inflate(context, R.layout.keyboard_symbol, null);
        tabLayout = symbolKeyboardView.findViewById(R.id.symbol_type_tabs_ll);
        lockBtn = symbolKeyboardView.findViewById(R.id.lock_btn);
        symbolsContainer = symbolKeyboardView.findViewById(R.id.symbols_rv);

        lockBtn.setOnClickListener(this);
        int cnt = tabLayout.getChildCount();
        for(int i = 0; i < cnt; i++){
            Keybutton btn = (Keybutton)tabLayout.getChildAt(i);
            btn.setOnClickListener(this);
            tabs.add(btn);
        }

        symbolsContainer.setLayoutManager(new StaggeredGridLayoutManager(SYMBOL_ROW_COUNT, StaggeredGridLayoutManager.HORIZONTAL));
        symbolsContainer.setAdapter(adapter);

        select(Symbol.SYMBOL_OFTEN);
    }

    public LinearLayout getView(){
        return symbolKeyboardView;
    }

    public void select(String tag){
        for(Keybutton k : tabs){
            if(k.getText().toString().equals(tag)){
                select(k);
                break;
            }
        }
    }

    public void select(Keybutton btn){
        if(selection != null)selection.deselect(false);
        btn.select(false);
        selection = btn;

        String tag = btn.getText().toString();
        symbols.clear();
        symbols.addAll(symbolDatabase.getValues(tag));
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        Keybutton btn;
        if(v instanceof Keybutton){
            btn = (Keybutton)v;
        }
        else{
            return;
        }

        switch (btn.keyType){
            case Keybutton.SYMBOL_TYPE:
                select(btn);
                break;
            case Keybutton.LOCK:
                isLock = !isLock;
                if(isLock)btn.select(true);
                else btn.deselect(true);
                break;
        }
    }
}
