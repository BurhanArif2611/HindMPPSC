package com.hindmppsc.exam.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.Prelims_Video_Course.MaterialListActivity;
import com.hindmppsc.exam.activity.Prelims_Video_Course.PaperListActivity;

import com.hindmppsc.exam.activity.SetPaperListActivity;
import com.hindmppsc.exam.models.SetList_Models.Result;
import com.hindmppsc.exam.utility.ErrorMessage;

import java.util.List;

public class SetList_Adapter extends RecyclerView.Adapter<SetList_Adapter.MyViewHolder> {
    View view;
    Context activity;
    List<Result> arrayList;
    String Exam_type;

    public SetList_Adapter(Context policyActivity, List<Result> arrayList, String exam_type) {
        this.activity = policyActivity;
        this.arrayList = arrayList;
        this.Exam_type = exam_type;

    }

    @NonNull
    @Override
    public SetList_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_class_subjects_list, parent, false);
        return new SetList_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SetList_Adapter.MyViewHolder holder, int position) {
        final Result listCoupon = arrayList.get(position);
        int p = position + 1;
        if ((p % 2) == 0) {
            holder.first_layout.setVisibility(View.VISIBLE);
            holder.second_layout.setVisibility(View.GONE);
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_ebook));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_ebook));
        } else {
            holder.first_layout.setVisibility(View.GONE);
            holder.second_layout.setVisibility(View.VISIBLE);
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_presentation));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_presentation));
        }
        holder.subject_name_first_tv.setText(listCoupon.getSetPaper());
        holder.subject_name_second_tv.setText(listCoupon.getSetPaper());


        holder.join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(listCoupon.getSetPaper()));
                bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
                bundle.putString("material", String.valueOf(listCoupon.getMaterialTypeId()));
                bundle.putString("Exam_type", Exam_type);
                ErrorMessage.I(activity, SetPaperListActivity.class, bundle);

            }
        });
        holder.join_second_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(listCoupon.getSetPaper()));
                bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
                bundle.putString("material", String.valueOf(listCoupon.getMaterialTypeId()));
                bundle.putString("Exam_type", Exam_type);
                ErrorMessage.I(activity, SetPaperListActivity.class, bundle);
            }
        });
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView subject_name_second_tv, subject_name_first_tv;
        Button join_btn, join_second_btn;
        CardView first_layout, second_layout;
        ImageView right_subject_img, subject_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            subject_name_second_tv = (TextView) itemView.findViewById(R.id.subject_name_second_tv);
            subject_name_first_tv = (TextView) itemView.findViewById(R.id.subject_name_first_tv);
            join_btn = itemView.findViewById(R.id.join_btn);
            join_second_btn = itemView.findViewById(R.id.join_second_btn);
            first_layout = itemView.findViewById(R.id.first_layout);
            second_layout = itemView.findViewById(R.id.second_layout);
            subject_img = (ImageView) itemView.findViewById(R.id.subject_img);
            right_subject_img = (ImageView) itemView.findViewById(R.id.right_subject_img);
        }
    }

}
