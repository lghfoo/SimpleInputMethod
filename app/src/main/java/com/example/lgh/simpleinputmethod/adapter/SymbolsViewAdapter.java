package com.example.lgh.simpleinputmethod.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lgh.simpleinputmethod.R;
import com.example.lgh.simpleinputmethod.model.Symbol;

import java.util.List;

public class SymbolsViewAdapter extends RecyclerView.Adapter< SymbolsViewAdapter.Holder>{
    List<Symbol> symbols;
    public SymbolsViewAdapter(List<Symbol>symbols){
        this.symbols = symbols;
    }

    @NonNull
    @Override
    public SymbolsViewAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_symbol_item, viewGroup, false);
        return new SymbolsViewAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SymbolsViewAdapter.Holder holder, int i) {
        holder.symbolBtn.setText(symbols.get(i).getValue());
    }

    @Override
    public int getItemCount() {
        return symbols.size();
    }

    static class Holder extends RecyclerView.ViewHolder{
        TextView symbolBtn;
        public Holder(@NonNull View itemView) {
            super(itemView);
            symbolBtn = itemView.findViewById(R.id.symbol_btn);
        }
    }
}
