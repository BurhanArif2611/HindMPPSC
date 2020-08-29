package com.hindmppsc.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hindmppsc.exam.R;
import com.hindmppsc.exam.models.Faq_Model.Result;


import java.util.Date;
import java.util.List;

public class Faq_Adapter extends RecyclerView.Adapter<Faq_Adapter.ViewHolder> {
    Context activity;
    Date date;
    List<Result> results;

    public Faq_Adapter(Context orderHistoryActivity, List<Result> result) {
        this.results = result;
        this.activity = orderHistoryActivity;
    }

    @NonNull
    @Override
    public Faq_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_adapter, parent, false);
        Faq_Adapter.ViewHolder viewHolder = new Faq_Adapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Faq_Adapter.ViewHolder holder, final int position) {
        holder.ouestion_tv.setText(results.get(position).getQuestion());
        holder.answer_tv.setText(results.get(position).getAnswer());
        final int[] n1 = {1};
        holder.ouestion_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n1[0]++;
                if ((n1[0] % 2) == 0) {
                    // number is even
                    holder.answer_tv.setVisibility(View.VISIBLE);
                    holder.ouestion_tv.setCompoundDrawablesWithIntrinsicBounds(null,null,activity.getResources().getDrawable(R.drawable.ic_up_arrow),null);
                }

                else {
                    // number is odd
                    holder.answer_tv.setVisibility(View.GONE);
                    holder.ouestion_tv.setCompoundDrawablesWithIntrinsicBounds(null,null,activity.getResources().getDrawable(R.drawable.ic_down_arrow),null);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ouestion_tv, answer_tv;


        public ViewHolder(View itemView) {
            super(itemView);
            ouestion_tv = (TextView) itemView.findViewById(R.id.ouestion_tv);
            answer_tv = (TextView) itemView.findViewById(R.id.answer_tv);
          /*  imageView = (ImageView) itemView.findViewById(R.id.imageView);
            suscribie_layout = (RelativeLayout) itemView.findViewById(R.id.suscribie_layout);*/
        }
    }
}
