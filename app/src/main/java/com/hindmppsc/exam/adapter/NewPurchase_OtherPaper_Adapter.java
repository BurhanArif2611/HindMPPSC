package com.hindmppsc.exam.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.Prelims_Video_Course.PaperListActivity;
import com.hindmppsc.exam.models.NewPurchase_Models.OrderPaper;
import com.hindmppsc.exam.models.NewPurchase_Models.Result;
import com.hindmppsc.exam.utility.ErrorMessage;

import java.util.List;

public class NewPurchase_OtherPaper_Adapter extends RecyclerView.Adapter<NewPurchase_OtherPaper_Adapter.MyViewHolder> {
    View view;
    Context activity;
    List<OrderPaper> arrayList;


    public NewPurchase_OtherPaper_Adapter(Context policyActivity, List<OrderPaper> arrayList) {
        this.activity = policyActivity;
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public NewPurchase_OtherPaper_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_class_subjects_list, parent, false);
        return new NewPurchase_OtherPaper_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewPurchase_OtherPaper_Adapter.MyViewHolder holder, int position) {
        final OrderPaper listCoupon = arrayList.get(position);
        int p = position + 1;
        if ((p % 2) == 0) {
            holder.first_layout.setVisibility(View.VISIBLE);
            holder.second_layout.setVisibility(View.GONE);
        } else {
            holder.first_layout.setVisibility(View.GONE);
            holder.second_layout.setVisibility(View.VISIBLE);
        }
        holder.subject_name_first_tv.setText(listCoupon.getExam()+"\n"+listCoupon.getPaper());
        holder.subject_name_second_tv.setText(listCoupon.getExam()+"\n"+listCoupon.getPaper());


        holder.join_btn.setVisibility(View.GONE);
        holder.join_second_btn.setVisibility(View.GONE);
        holder.time_tv.setVisibility(View.VISIBLE);
        holder.second_time_tv.setVisibility(View.VISIBLE);
        holder.second_time_tv.setText(listCoupon.getDuration());
        holder.time_tv.setText(listCoupon.getDuration());


holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       /* Bundle bundle = new Bundle();
        bundle.putString("id", String.valueOf(listCoupon.get()));
        bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
        bundle.putString("material", listCoupon.getMaterialType());
        ErrorMessage.I(activity, PaperListActivity.class, bundle);*/
    }
});

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView subject_name_second_tv, subject_name_first_tv,time_tv,second_time_tv;
        Button join_btn, join_second_btn;
        CardView first_layout, second_layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            subject_name_second_tv = (TextView) itemView.findViewById(R.id.subject_name_second_tv);
            subject_name_first_tv = (TextView) itemView.findViewById(R.id.subject_name_first_tv);
            second_time_tv = (TextView) itemView.findViewById(R.id.second_time_tv);
            time_tv = (TextView) itemView.findViewById(R.id.time_tv);
            join_btn = itemView.findViewById(R.id.join_btn);
            join_second_btn = itemView.findViewById(R.id.join_second_btn);
            first_layout = itemView.findViewById(R.id.first_layout);
            second_layout = itemView.findViewById(R.id.second_layout);
        }
    }
}
