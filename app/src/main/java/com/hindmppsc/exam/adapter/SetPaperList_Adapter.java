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
import com.hindmppsc.exam.activity.PDFViewerActivity;
import com.hindmppsc.exam.activity.Prelims_Video_Course.MaterialListActivity;
import com.hindmppsc.exam.activity.Prelims_Video_Course.MockTestPrelimsActivity;
import com.hindmppsc.exam.activity.SetPaperListActivity;
import com.hindmppsc.exam.activity.Set_ListActivity;
import com.hindmppsc.exam.models.SetList_Models.Result;
import com.hindmppsc.exam.utility.ErrorMessage;

import java.util.List;

public class SetPaperList_Adapter extends RecyclerView.Adapter<SetPaperList_Adapter.MyViewHolder> {
    View view;
    Context activity;
    List<Result> arrayList;
    String Exam_type;

    public SetPaperList_Adapter(Context policyActivity, List<Result> arrayList, String exam_type) {
        this.activity = policyActivity;
        this.arrayList = arrayList;
        this.Exam_type = exam_type;

    }

    @NonNull
    @Override
    public SetPaperList_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_class_subjects_list, parent, false);
        return new SetPaperList_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SetPaperList_Adapter.MyViewHolder holder, int position) {
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
        holder.subject_name_first_tv.setText(""+listCoupon.getPaper());
        holder.subject_name_second_tv.setText(""+listCoupon.getPaper());


        holder.join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Exam_type.toLowerCase().trim().contains("prelims")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(listCoupon.getMaterialTypeId()));
                    bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
                    bundle.putString("material", listCoupon.getSetPaper());
                    bundle.putString("paper", String.valueOf(listCoupon.getPaper()));
                    bundle.putString("Exam_type", Exam_type);
                    ErrorMessage.I(activity, MockTestPrelimsActivity.class, bundle);
                } else if (Exam_type.contains("Mains")) {
                    try{
                        Bundle bundle = new Bundle();
                        bundle.putString("image", listCoupon.getUrl());
                        bundle.putString("title", String.valueOf(listCoupon.getPaper()));
                        ErrorMessage.I(activity, PDFViewerActivity.class, bundle);
                    }catch (Exception e){}
                }else  {
                    try{
                        Bundle bundle = new Bundle();
                        bundle.putString("image", listCoupon.getUrl());
                        bundle.putString("title", String.valueOf(listCoupon.getPaper()));
                        ErrorMessage.I(activity, PDFViewerActivity.class, bundle);
                    }catch (Exception e){}
                }




            }
        });
        holder.join_second_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Exam_type.toLowerCase().trim().contains("prelims")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(listCoupon.getMaterialTypeId()));
                    bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
                    bundle.putString("material", listCoupon.getSetPaper());
                    bundle.putString("paper", String.valueOf(listCoupon.getPaper()));
                    bundle.putString("Exam_type", Exam_type);
                    ErrorMessage.I(activity, MockTestPrelimsActivity.class, bundle);
                } else if (Exam_type.contains("Mains")) {
                    try{
                        Bundle bundle = new Bundle();
                        bundle.putString("image", listCoupon.getUrl());
                        bundle.putString("title", String.valueOf(listCoupon.getPaper()));
                        ErrorMessage.I(activity, PDFViewerActivity.class, bundle);
                    }catch (Exception e){}
                }else {
                    try{
                        Bundle bundle = new Bundle();
                        bundle.putString("image", listCoupon.getUrl());
                        bundle.putString("title", String.valueOf(listCoupon.getPaper()));
                        ErrorMessage.I(activity, PDFViewerActivity.class, bundle);
                    }catch (Exception e){}
                }
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
