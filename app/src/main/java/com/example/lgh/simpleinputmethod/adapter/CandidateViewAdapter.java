package com.example.lgh.simpleinputmethod.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lgh.simpleinputmethod.R;

import java.util.List;

public class CandidateViewAdapter extends RecyclerView.Adapter< CandidateViewAdapter.Holder> implements View.OnClickListener{
    List<String> data;
    OnItemClickListener clickListener;
    public CandidateViewAdapter(List<String>data){
        this.data = data;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_candidate_item, viewGroup, false);
       view.setOnClickListener(this);
       return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.content.setText(data.get(i));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {
        if(clickListener != null){
            clickListener.onClick(v);
        }
    }

    static class Holder extends RecyclerView.ViewHolder{
        TextView content;
        public Holder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
        }
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    public static interface OnItemClickListener{
        void onClick(View v);
    }
}
