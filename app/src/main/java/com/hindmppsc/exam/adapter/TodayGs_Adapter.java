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
import com.hindmppsc.exam.models.Live_class_subjects.Result;
import com.hindmppsc.exam.utility.ErrorMessage;

import java.util.List;

public class TodayGs_Adapter extends RecyclerView.Adapter<TodayGs_Adapter.MyViewHolder> {
    View view;
    Context activity;
    List<Result> arrayList;


    public TodayGs_Adapter(Context policyActivity, List<Result> arrayList) {
        this.activity = policyActivity;
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public TodayGs_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.live_class_subjects_list, parent, false);
        return new TodayGs_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TodayGs_Adapter.MyViewHolder holder, int position) {
        final Result listCoupon=arrayList.get(position);
        int p = position + 1;
        if ((p % 2) == 0) {
            holder.first_layout.setVisibility(View.VISIBLE);
            holder.second_layout.setVisibility(View.GONE);
        }
        else {
            holder.first_layout.setVisibility(View.GONE);
            holder.second_layout.setVisibility(View.VISIBLE);
        }
        holder.subject_name_first_tv.setText(listCoupon.getDay());
        holder.subject_name_second_tv.setText(listCoupon.getDay());
        holder.join_btn.setText("Watch");
        holder.join_second_btn.setText("Watch");
        holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_book));
        holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_book));
      /*  if (listCoupon.getSubscribe().equals("subscribe")) {
            holder.join_btn.setVisibility(View.GONE);
            holder.join_second_btn.setVisibility(View.GONE);
        }*/
        holder.join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "Today GS");
                bundle.putString("image", listCoupon.getImage());
                ErrorMessage.I(activity, PDFViewerActivity.class, bundle);
            }
        });
        holder.join_second_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "Today GS");
                bundle.putString("image", listCoupon.getImage());
                ErrorMessage.I(activity, PDFViewerActivity.class, bundle);
            }
        });
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

        /*if (listCoupon.getSubject().toLowerCase().contains("current affairs")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_student));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_student));
        }else if (listCoupon.getSubject().toLowerCase().contains("computer")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_computer));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_computer));
        }else if (listCoupon.getSubject().toLowerCase().contains("cast")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_e_learning));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_e_learning));
        }else if (listCoupon.getSubject().toLowerCase().contains("environment")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_globe));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_globe));
        }else if (listCoupon.getSubject().toLowerCase().contains("ethics")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_calculation));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_calculation));
        }else if (listCoupon.getSubject().toLowerCase().contains("essay")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_read));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_read));
        }else if (listCoupon.getSubject().toLowerCase().contains("hindi")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_pencil));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_pencil));
        }else if (listCoupon.getSubject().toLowerCase().contains("health education")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_health_education));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_health_education));
        }else if (listCoupon.getSubject().toLowerCase().contains("interview")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_interview));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_interview));
        }else if (listCoupon.getSubject().toLowerCase().contains("indian history")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_history));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_history));
        }else if (listCoupon.getSubject().toLowerCase().contains("indian geography")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_history));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_history));
        }else if (listCoupon.getSubject().toLowerCase().contains("indian polity")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_presentation));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_presentation));
        }else if (listCoupon.getSubject().toLowerCase().contains("indian economics")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_economics));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_economics));
        }else if (listCoupon.getSubject().toLowerCase().contains("mains")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_prelims));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_prelims));
        }else if (listCoupon.getSubject().toLowerCase().contains("mp economics")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_economics));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_economics));
        }else if (listCoupon.getSubject().toLowerCase().contains("mp geography")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_history));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_history));
        }else if (listCoupon.getSubject().toLowerCase().contains("mp polity")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_presentation));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_presentation));
        }else if (listCoupon.getSubject().toLowerCase().contains("national institute")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_institute));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_institute));
        }else if (listCoupon.getSubject().toLowerCase().contains("prelims")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_prelims));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_prelims));
        }else if (listCoupon.getSubject().toLowerCase().contains("state institute")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_institute));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_institute));
        }else if (listCoupon.getSubject().toLowerCase().contains("world geography")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_history));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_history));
        }*/

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView subject_name_second_tv,subject_name_first_tv;
        Button join_btn,join_second_btn;
        CardView first_layout,second_layout;
        ImageView right_subject_img,subject_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            subject_name_second_tv = (TextView) itemView.findViewById(R.id.subject_name_second_tv);
            subject_name_first_tv = (TextView) itemView.findViewById(R.id.subject_name_first_tv);
            join_btn = itemView.findViewById(R.id.join_btn);
            join_second_btn = itemView.findViewById(R.id.join_second_btn);
            first_layout =  itemView.findViewById(R.id.first_layout);
            second_layout =  itemView.findViewById(R.id.second_layout);
            subject_img = (ImageView) itemView.findViewById(R.id.subject_img);
            right_subject_img = (ImageView)  itemView.findViewById(R.id.right_subject_img);
        }
    }
}
