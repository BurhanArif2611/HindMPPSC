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
import com.hindmppsc.exam.activity.Live_Classes.PackegeListActivity;

import com.hindmppsc.exam.activity.Prelims_Video_Course.MaterialListActivity;
import com.hindmppsc.exam.activity.Prelims_Video_Course.MockTestPrelimsActivity;
import com.hindmppsc.exam.activity.Prelims_Video_Course.PaperListActivity;
import com.hindmppsc.exam.activity.Prelims_Video_Course.PrelimsPreviousPaperActivity;
import com.hindmppsc.exam.models.MaterialType_models.Result;
import com.hindmppsc.exam.utility.ErrorMessage;

import java.util.List;

public class Material_Type_Adapter extends RecyclerView.Adapter<Material_Type_Adapter.MyViewHolder> {
    View view;
    Context activity;
    List<Result> arrayList;
    String Exam_type;

    public Material_Type_Adapter(Context policyActivity, List<Result> arrayList, String exam_type) {
        this.activity = policyActivity;
        this.arrayList = arrayList;
        this.Exam_type = exam_type;

    }

    @NonNull
    @Override
    public Material_Type_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_class_subjects_list, parent, false);
        return new Material_Type_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Material_Type_Adapter.MyViewHolder holder, int position) {
        final Result listCoupon = arrayList.get(position);
        int p = position + 1;
        if ((p % 2) == 0) {
            holder.first_layout.setVisibility(View.VISIBLE);
            holder.second_layout.setVisibility(View.GONE);
        } else {
            holder.first_layout.setVisibility(View.GONE);
            holder.second_layout.setVisibility(View.VISIBLE);
        }
        holder.subject_name_first_tv.setText(listCoupon.getMaterialType());
        holder.subject_name_second_tv.setText(listCoupon.getMaterialType());

        if (listCoupon.getSubscribe().equals("subscribe")) {
            holder.join_btn.setText("Attend");
            holder.join_second_btn.setText("Attend");
        }
        holder.join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   if (Exam_type.toLowerCase().trim().equals("interview")) {
                    if (listCoupon.getMaterialType().toLowerCase().equals("preivious paper")) {
                        ((MaterialListActivity) activity).getpreivious_paper(listCoupon);
                    } else if (listCoupon.getMaterialType().toLowerCase().equals("mock test")) {
                        ((MaterialListActivity) activity).getMockTest(listCoupon);
                    }else if (listCoupon.getMaterialType().toLowerCase().equals("video course")) {
                        ((MaterialListActivity) activity).getInterviewPreviousVideo(listCoupon);
                    }else if (listCoupon.getMaterialType().toLowerCase().equals("ebook")) {
                        ((MaterialListActivity) activity).getInterviewEBook(listCoupon);
                    } else {
                      // ((MaterialListActivity) activity).getInterviewPreviousVideo(listCoupon);
                      *//*  Bundle bundle = new Bundle();
                        bundle.putString("id", String.valueOf(listCoupon.getId()));
                        bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
                        bundle.putString("material", listCoupon.getMaterialType());
                        ErrorMessage.I(activity, PaperListActivity.class, bundle);*//*
                    }
                } else {*/
                    if (listCoupon.getMaterialType().toLowerCase().equals("preivious paper")) {
                        ((MaterialListActivity) activity).getpreivious_paper(listCoupon);
                    }else   if (listCoupon.getMaterialType().toLowerCase().equals("syllabus")) {
                        ((MaterialListActivity) activity).getsyllabus(listCoupon);
                    } else if (listCoupon.getMaterialType().toLowerCase().equals("mock test")) {
                        ((MaterialListActivity) activity).getMockTest(listCoupon);
                    }else if (listCoupon.getMaterialType().toLowerCase().equals("current affairs complete course")) {
                        ((MaterialListActivity) activity).getCurrent_Affairs_Complete_course(listCoupon);
                    }else if (listCoupon.getMaterialType().toLowerCase().equals("current affairs month ") || listCoupon.getMaterialType().toLowerCase().equals("current affairs month")) {
                        ((MaterialListActivity) activity).getCurrent_Affairs_Month(listCoupon);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", String.valueOf(listCoupon.getId()));
                        bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
                        bundle.putString("material", listCoupon.getMaterialType());
                        ErrorMessage.I(activity, PaperListActivity.class, bundle);
                    }
              //  }
            }
        });
        holder.join_second_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (Exam_type.toLowerCase().trim().equals("interview")) {
                    if (listCoupon.getMaterialType().toLowerCase().equals("preivious paper")) {
                        ((MaterialListActivity) activity).getpreivious_paper(listCoupon);
                    } else if (listCoupon.getMaterialType().toLowerCase().equals("mock test")) {
                        ((MaterialListActivity) activity).getMockTest(listCoupon);
                    }else if (listCoupon.getMaterialType().toLowerCase().equals("video course")) {
                        ((MaterialListActivity) activity).getInterviewPreviousVideo(listCoupon);
                    }else if (listCoupon.getMaterialType().toLowerCase().equals("ebook")) {
                        ((MaterialListActivity) activity).getInterviewEBook(listCoupon);
                    } else {
                        // ((MaterialListActivity) activity).getInterviewPreviousVideo(listCoupon);
                      *//*  Bundle bundle = new Bundle();
                        bundle.putString("id", String.valueOf(listCoupon.getId()));
                        bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
                        bundle.putString("material", listCoupon.getMaterialType());
                        ErrorMessage.I(activity, PaperListActivity.class, bundle);*//*
                    }
                } else {*/
                    if (listCoupon.getMaterialType().toLowerCase().equals("preivious paper")) {
                        ((MaterialListActivity) activity).getpreivious_paper(listCoupon);
                    } else   if (listCoupon.getMaterialType().toLowerCase().equals("syllabus")) {
                        ((MaterialListActivity) activity).getsyllabus(listCoupon);
                    }
                    else if (listCoupon.getMaterialType().toLowerCase().equals("mock test")) {
                        ((MaterialListActivity) activity).getMockTest(listCoupon);
                    }else if (listCoupon.getMaterialType().toLowerCase().equals("current affairs complete course")) {
                        ((MaterialListActivity) activity).getCurrent_Affairs_Complete_course(listCoupon);
                    }else if (listCoupon.getMaterialType().toLowerCase().equals("current affairs month ") || listCoupon.getMaterialType().toLowerCase().equals("current affairs month")) {
                        ((MaterialListActivity) activity).getCurrent_Affairs_Month(listCoupon);
                    }  else {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", String.valueOf(listCoupon.getId()));
                        bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
                        bundle.putString("material", listCoupon.getMaterialType());
                        ErrorMessage.I(activity, PaperListActivity.class, bundle);
                    }
               // }

            }
        });
        if (listCoupon.getMaterialType().toLowerCase().contains("syllabus")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_health_education));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_health_education));
        }else if (listCoupon.getMaterialType().toLowerCase().contains("video course")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_live_class));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_live_class));
        }else if (listCoupon.getMaterialType().toLowerCase().contains("ebook")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_ebook));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_ebook));
        }else if (listCoupon.getMaterialType().toLowerCase().contains("preivious paper")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_history));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_history));
        }else if (listCoupon.getMaterialType().toLowerCase().contains("mock test")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_presentation));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_presentation));
        }
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listCoupon.getSubscribe().equals("subscribe")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", listCoupon.getId());
                    bundle.putString("exam_type", listCoupon.getExamType());
                    ErrorMessage.I(activity, LiveClassActivity.class, bundle);
                }
            }
        });*/


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
