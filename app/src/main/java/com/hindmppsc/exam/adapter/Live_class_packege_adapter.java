package com.hindmppsc.exam.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.Live_Classes.LiveClassActivity;
import com.hindmppsc.exam.activity.Live_Classes.PackegeListActivity;

import com.hindmppsc.exam.activity.Live_Classes.PaymentActivity;
import com.hindmppsc.exam.models.Packege_models.Result;
import com.hindmppsc.exam.utility.ErrorMessage;

import java.util.List;

public class Live_class_packege_adapter extends RecyclerView.Adapter<Live_class_packege_adapter.MyViewHolder> {
    View view;
    Context activity;
    List<Result> arrayList;
    String Subject_id;

    public Live_class_packege_adapter(Context policyActivity, List<Result> arrayList, String subject_id) {
        this.activity = policyActivity;
        this.arrayList = arrayList;
        this.Subject_id = subject_id;

    }

    @NonNull
    @Override
    public Live_class_packege_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.packege_list_adapter, parent, false);
        return new Live_class_packege_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Live_class_packege_adapter.MyViewHolder holder, int position) {
        final Result listCoupon = arrayList.get(position);
        int p = position + 1;
        if ((p % 2) == 0) {
            holder.first_layout.setVisibility(View.VISIBLE);
            holder.second_layout.setVisibility(View.GONE);
        } else {
            holder.first_layout.setVisibility(View.GONE);
            holder.second_layout.setVisibility(View.VISIBLE);
        }
        if (listCoupon.getSubscribe().equals("subscribe")) {
            holder.packege_price_tv.setText("Attend Class" );
            holder.second_packege_price_tv.setText("Attend Class" );
        } else {
            holder.packege_price_tv.setText("₹ " + listCoupon.getPrice());
            holder.second_packege_price_tv.setText("₹ " + listCoupon.getPrice());
        }
        holder.subject_name_first_tv.setText(listCoupon.getPackage());
        holder.subject_name_second_tv.setText(listCoupon.getPackage());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ((PackegeListActivity)activity).GO_TO_NextPage(listCoupon.getId(),listCoupon.getExamType(),listCoupon.getPrice());
                if (listCoupon.getSubscribe().equals("subscribe")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", Subject_id);
                    bundle.putString("exam_type", listCoupon.getExamType());
                    ErrorMessage.I(activity, LiveClassActivity.class, bundle);
                } else {
                    if (!listCoupon.getOnOffStatus().equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", listCoupon.getId());
                        bundle.putString("exam_type", listCoupon.getExamType());
                        bundle.putString("price", listCoupon.getPrice());
                        // ErrorMessage.I(activity, PaymentActivity.class, bundle);
                        ((PackegeListActivity) activity).GO_TO_NextPage(listCoupon.getId(), listCoupon.getExamType(), listCoupon.getPrice());
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", Subject_id);
                        bundle.putString("exam_type", listCoupon.getExamType());
                        ErrorMessage.I(activity, LiveClassActivity.class, bundle);
                    }
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView subject_name_second_tv, subject_name_first_tv, packege_price_tv, second_packege_price_tv;
        CardView first_layout, second_layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            subject_name_second_tv = (TextView) itemView.findViewById(R.id.subject_name_second_tv);
            subject_name_first_tv = (TextView) itemView.findViewById(R.id.subject_name_first_tv);
            second_packege_price_tv = (TextView) itemView.findViewById(R.id.second_packege_price_tv);
            packege_price_tv = (TextView) itemView.findViewById(R.id.packege_price_tv);

            first_layout = itemView.findViewById(R.id.first_layout);
            second_layout = itemView.findViewById(R.id.second_layout);
        }
    }
}
