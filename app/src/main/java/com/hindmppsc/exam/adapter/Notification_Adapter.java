package com.hindmppsc.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hindmppsc.exam.R;
import com.hindmppsc.exam.models.Notifications.Result;


import java.util.List;

public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.MyViewHolder> {
    View view;
    Context activity;
    List<Result> arrayList;


    public Notification_Adapter(Context policyActivity, List<Result> arrayList) {
        this.activity = policyActivity;
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public Notification_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_adapter, parent, false);
        return new Notification_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Notification_Adapter.MyViewHolder holder, int position) {
        final Result listCoupon = arrayList.get(position);
        holder.date_tv.setText(listCoupon.getDate());
        holder.message_tv.setText(listCoupon.getMessage());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date_tv, message_tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            date_tv = (TextView) itemView.findViewById(R.id.date_tv);
            message_tv = (TextView) itemView.findViewById(R.id.message_tv);

        }
    }

}