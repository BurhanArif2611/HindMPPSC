package com.hindmppsc.exam.adapter;

import android.content.Context;
import android.graphics.Bitmap;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.DashboardActivity;
import com.hindmppsc.exam.activity.Live_Classes.PackegeListActivity;

import com.hindmppsc.exam.activity.Live_Classes.SubjectActivity;
import com.hindmppsc.exam.activity.Live_Classes.TaskForYou_InterviewsActivity;
import com.hindmppsc.exam.activity.Prelims_Video_Course.MaterialListActivity;
import com.hindmppsc.exam.activity.TaskForYouTypesActivity;
import com.hindmppsc.exam.activity.TodayGSActivity;
import com.hindmppsc.exam.activity.Update_Your_SelfActivity;
import com.hindmppsc.exam.models.Dashboard.Result;
import com.hindmppsc.exam.utility.ErrorMessage;

import java.util.List;

public class Dashboard_Adapter extends RecyclerView.Adapter<Dashboard_Adapter.MyViewHolder> {
    View view;
    Context activity;
    List<Result> arrayList;

    public Dashboard_Adapter(Context policyActivity, List<Result> arrayList) {
        this.activity = policyActivity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Dashboard_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_class_subjects_list, parent, false);
        return new Dashboard_Adapter.MyViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull final Dashboard_Adapter.MyViewHolder holder, int position) {
        final Result listCoupon = arrayList.get(position);
        int p = position + 1;
        if ((p % 2) == 0) {
            holder.first_layout.setVisibility(View.VISIBLE);
            holder.second_layout.setVisibility(View.GONE);
        } else {
            holder.first_layout.setVisibility(View.GONE);
            holder.second_layout.setVisibility(View.VISIBLE);
        }
        holder.subject_name_first_tv.setText(listCoupon.getExam());
        holder.subject_name_second_tv.setText(listCoupon.getExam());
        holder.join_btn.setVisibility(View.GONE);
        holder.join_second_btn.setVisibility(View.GONE);
        if (listCoupon.getExam().equals("Live Class")) {
            //
            //
            //  holder.subject_name_first_tv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, activity.getResources().getDrawable(R.drawable.ic_ic_video_play));
             holder.subject_name_second_tv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, activity.getResources().getDrawable(R.drawable.ic_ic_video_play));
        }

    /*    Glide.with(activity).load(listCoupon.getImage()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                try {
                    int width = holder.right_subject_img.getMeasuredWidth();
                    int diw = resource.getWidth();
                    if (diw > 0) {
                        int height = 0;
                        height = width * resource.getHeight() / diw;
                        resource = Bitmap.createScaledBitmap(resource, width, height, false);
                    }
                    holder.right_subject_img.setImageBitmap(resource);
                } catch (Exception e) {
                    try {
                        int width = (int) activity.getResources().getDimension(R.dimen._35sdp);
                        int diw = (int) activity.getResources().getDimension(R.dimen._35sdp);;
                        if (diw > 0) {
                            int height = 0;
                            height = width * resource.getHeight() / diw;
                            resource = Bitmap.createScaledBitmap(resource, width, height, false);
                        }
                        holder.right_subject_img.setImageBitmap(resource);
                    } catch (Exception e1) {}
                }
            }
        });
        Glide.with(activity).load(listCoupon.getImage()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                try {
                    int width = holder.subject_img.getMeasuredWidth();
                    int diw = resource.getWidth();
                    if (diw > 0) {
                        int height = 0;
                        height = width * resource.getHeight() / diw;
                        resource = Bitmap.createScaledBitmap(resource, width, height, false);
                    }
                    holder.subject_img.setImageBitmap(resource);
                } catch (Exception e) {
                    try {
                        int width = (int) activity.getResources().getDimension(R.dimen._35sdp);
                        int diw = (int) activity.getResources().getDimension(R.dimen._35sdp);;
                        if (diw > 0) {
                            int height = 0;
                            height = width * resource.getHeight() / diw;
                            resource = Bitmap.createScaledBitmap(resource, width, height, false);
                        }
                        holder.subject_img.setImageBitmap(resource);
                    } catch (Exception e1) {}
                }
            }
        });*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listCoupon.getExam().toLowerCase().trim().equals("prelims")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", listCoupon.getId());
                    bundle.putString("exam_type", listCoupon.getExam());
                    ErrorMessage.I(activity, MaterialListActivity.class, bundle);
                } else if (listCoupon.getExam().equals("Crash Course Prelims")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", listCoupon.getId());
                    bundle.putString("exam_type", listCoupon.getExam());
                    ErrorMessage.I(activity, MaterialListActivity.class, bundle);
                } else if (listCoupon.getExam().toLowerCase().trim().equals("mains")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", listCoupon.getId());
                    bundle.putString("exam_type", listCoupon.getExam());
                    ErrorMessage.I(activity, MaterialListActivity.class, bundle);
                } else if (listCoupon.getExam().equals("Crash Course Mains")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", listCoupon.getId());
                    bundle.putString("exam_type", listCoupon.getExam());
                    ErrorMessage.I(activity, MaterialListActivity.class, bundle);
                } else if (listCoupon.getExam().toLowerCase().trim().equals("interview")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", listCoupon.getId());
                    bundle.putString("exam_type", listCoupon.getExam());
                    ErrorMessage.I(activity, MaterialListActivity.class, bundle);
                } else if (listCoupon.getExam().toLowerCase().equals("update your self") || listCoupon.getExam().toLowerCase().contains("update your self")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", listCoupon.getId());
                    bundle.putString("exam_type", listCoupon.getExam());
                    ErrorMessage.I(activity, Update_Your_SelfActivity.class, bundle);
                } else if (listCoupon.getExam().toLowerCase().equals("today gs") || listCoupon.getExam().contains("Today GS")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", listCoupon.getId());
                    bundle.putString("exam_type", listCoupon.getExam());
                    ErrorMessage.I(activity, TodayGSActivity.class, bundle);
                } else if (listCoupon.getExam().toLowerCase().equals("task for you") || listCoupon.getExam().contains("Task For You")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", listCoupon.getId());
                    bundle.putString("exam_type", listCoupon.getExam());
                    ErrorMessage.I(activity, TaskForYouTypesActivity.class, bundle);
                } else if (listCoupon.getExam().toLowerCase().equals("live class") || listCoupon.getExam().contains("Live Class")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", listCoupon.getId());
                    bundle.putString("exam_type", listCoupon.getExam());
                    ErrorMessage.I(activity, SubjectActivity.class, bundle);
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
        }else if (listCoupon.getExam().toLowerCase().contains("update your self")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_update_your_self));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_update_your_self));
        }else if (listCoupon.getExam().toLowerCase().contains("today gs")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_today_gs));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_today_gs));
        }else if (listCoupon.getExam().toLowerCase().contains("task for you")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_pencil));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_pencil));
        }else if (listCoupon.getExam().toLowerCase().contains("crash course prelims")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_health_education));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_health_education));
        }else if (listCoupon.getExam().toLowerCase().contains("crash course mains")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_presentation));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_presentation));
        }else if (listCoupon.getExam().toLowerCase().contains("indian history")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_history));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_history));
        }else if (listCoupon.getExam().toLowerCase().contains("indian geography")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_history));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_history));
        }else if (listCoupon.getExam().toLowerCase().contains("indian polity")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_presentation));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_presentation));
        }else if (listCoupon.getExam().toLowerCase().contains("indian economics")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_economics));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_economics));
        }else if (listCoupon.getExam().toLowerCase().contains("mains")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_prelims));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_prelims));
        }else if (listCoupon.getExam().toLowerCase().contains("mp economics")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_economics));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_economics));
        }else if (listCoupon.getExam().toLowerCase().contains("mp geography")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_history));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_history));
        }else if (listCoupon.getExam().toLowerCase().contains("mp polity")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_presentation));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_presentation));
        }else if (listCoupon.getExam().toLowerCase().contains("national institute")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_institute));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_institute));
        }else if (listCoupon.getExam().toLowerCase().contains("prelims")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_prelims));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_prelims));
        }else if (listCoupon.getExam().toLowerCase().contains("state institute")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_institute));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_institute));
        }else if (listCoupon.getExam().toLowerCase().contains("world geography")){
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_history));
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_history));
        }

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
