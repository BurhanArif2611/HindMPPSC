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
import com.hindmppsc.exam.activity.Complete_Current_AffairListActivity;
import com.hindmppsc.exam.activity.Current_Affairs_MonthActivity;
import com.hindmppsc.exam.activity.Prelims_Video_Course.MaterialListActivity;
import com.hindmppsc.exam.activity.Prelims_Video_Course.MockTestPrelimsActivity;
import com.hindmppsc.exam.activity.Prelims_Video_Course.PrelimsPreviousPaperActivity;
import com.hindmppsc.exam.models.NewPurchase_Models.OtherCourse;
import com.hindmppsc.exam.models.NewPurchase_Models.Result;
import com.hindmppsc.exam.utility.ErrorMessage;

import java.util.List;

public class NewPurchase_OtherCourse_Adapter extends RecyclerView.Adapter<NewPurchase_OtherCourse_Adapter.MyViewHolder> {
    View view;
    Context activity;
    List<OtherCourse> arrayList;


    public NewPurchase_OtherCourse_Adapter(Context policyActivity, List<OtherCourse> arrayList) {
        this.activity = policyActivity;
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public NewPurchase_OtherCourse_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_class_subjects_list, parent, false);
        return new NewPurchase_OtherCourse_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewPurchase_OtherCourse_Adapter.MyViewHolder holder, int position) {
        final OtherCourse listCoupon = arrayList.get(position);
        int p = position + 1;
        if ((p % 2) == 0) {
            holder.first_layout.setVisibility(View.VISIBLE);
            holder.second_layout.setVisibility(View.GONE);
        } else {
            holder.first_layout.setVisibility(View.GONE);
            holder.second_layout.setVisibility(View.VISIBLE);
        }
        holder.subject_name_first_tv.setText(listCoupon.getExam() + "\n" + listCoupon.getMaterialTypeId());
        holder.subject_name_second_tv.setText(listCoupon.getExam() + "\n" + listCoupon.getMaterialTypeId());


        holder.join_btn.setVisibility(View.GONE);
        holder.join_second_btn.setVisibility(View.GONE);
        holder.time_tv.setVisibility(View.VISIBLE);
        holder.second_time_tv.setVisibility(View.VISIBLE);
        holder.second_time_tv.setText(listCoupon.getDuration());
        holder.time_tv.setText(listCoupon.getDuration());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listCoupon.getMaterialTypeId().equals("Syllabus")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(listCoupon.getMaterialType()));
                    bundle.putString("exam_type", String.valueOf(listCoupon.getExamId()));
                    bundle.putString("material", listCoupon.getMaterialTypeId());
                    bundle.putString("Exam_type", listCoupon.getMaterialTypeId());
                    ErrorMessage.I(activity, PrelimsPreviousPaperActivity.class, bundle);
                }else if (listCoupon.getMaterialTypeId().equals("Mock Test")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(listCoupon.getMaterialType()));
                    bundle.putString("exam_type", String.valueOf(listCoupon.getExamId()));
                    bundle.putString("material", listCoupon.getMaterialTypeId());
                    bundle.putString("Exam_type", listCoupon.getMaterialTypeId());
                    ErrorMessage.I(activity, MockTestPrelimsActivity.class, bundle);
                }else if (listCoupon.getMaterialTypeId().equals("Current Affairs Complete course")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(listCoupon.getMaterialType()));
                    bundle.putString("exam_type", String.valueOf(listCoupon.getExamId()));
                    bundle.putString("material", listCoupon.getMaterialTypeId());
                    bundle.putString("Exam_type", listCoupon.getMaterialTypeId());
                    ErrorMessage.I(activity, Complete_Current_AffairListActivity.class, bundle);


                }else if (listCoupon.getMaterialTypeId().equals("Preivious Paper")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(listCoupon.getMaterialType()));
                    bundle.putString("exam_type", String.valueOf(listCoupon.getExamId()));
                    bundle.putString("material", listCoupon.getMaterialTypeId());
                    bundle.putString("Exam_type", listCoupon.getMaterialTypeId());
                    ErrorMessage.I(activity, PrelimsPreviousPaperActivity.class, bundle);

                }else if (listCoupon.getMaterialTypeId().equals("Current Affairs Monthly ")|| listCoupon.getMaterialTypeId().equals("Current Affairs Monthly")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(listCoupon.getMaterialType()));
                    bundle.putString("exam_type", String.valueOf(listCoupon.getExamId()));
                    bundle.putString("material", listCoupon.getMaterialTypeId());
                    bundle.putString("Exam_type", listCoupon.getMaterialTypeId());
                    ErrorMessage.I(activity, Current_Affairs_MonthActivity.class, bundle);

                }
            }
        });
        if (listCoupon.getExam().toLowerCase().contains("live class")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_live_class));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_live_class));
        }else if (listCoupon.getExam().toLowerCase().contains("prelims")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_prelims));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_prelims));
        }else if (listCoupon.getExam().toLowerCase().contains("mains")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_mains));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_mains));
        }else if (listCoupon.getExam().toLowerCase().contains("interview")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_interview));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_interview));
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView subject_name_second_tv, subject_name_first_tv, time_tv, second_time_tv;
        Button join_btn, join_second_btn;
        CardView first_layout, second_layout;
        ImageView subject_img,right_subject_img;
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
            subject_img = itemView.findViewById(R.id.subject_img);
            right_subject_img = itemView.findViewById(R.id.right_subject_img);
        }
    }
}
