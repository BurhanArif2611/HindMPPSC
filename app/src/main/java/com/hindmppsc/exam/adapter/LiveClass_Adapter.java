package com.hindmppsc.exam.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hindmppsc.exam.BuildConfig;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.Live_Classes.LiveClassActivity;
import com.hindmppsc.exam.activity.Live_Classes.PackegeListActivity;

import com.hindmppsc.exam.activity.Live_Classes.PlayURLActivity;
import com.hindmppsc.exam.activity.PDFViewerActivity;
import com.hindmppsc.exam.activity.PlayVideoActivity;
import com.hindmppsc.exam.activity.WebviewActivity;
import com.hindmppsc.exam.models.LiveClass_models.Result;
import com.hindmppsc.exam.utility.ErrorMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LiveClass_Adapter extends RecyclerView.Adapter<LiveClass_Adapter.MyViewHolder> {
    View view;
    Context activity;
    List<Result> arrayList;
    String Check;


    public LiveClass_Adapter(Context policyActivity, List<Result> arrayList, String check) {
        this.activity = policyActivity;
        this.arrayList = arrayList;
        this.Check = check;

    }

    @NonNull
    @Override
    public LiveClass_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_class_subjects_list, parent, false);
        return new LiveClass_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LiveClass_Adapter.MyViewHolder holder, int position) {
        if (Check.equals("Previous")) {
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_e_learning));
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_e_learning));
        } else {
            holder.subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_video_conference));
            holder.right_subject_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_video_conference));
        }
        final Result listCoupon = arrayList.get(position);
        int p = position + 1;
        if ((p % 2) == 0) {
            holder.first_layout.setVisibility(View.VISIBLE);
            holder.second_layout.setVisibility(View.GONE);
        } else {
            holder.first_layout.setVisibility(View.GONE);
            holder.second_layout.setVisibility(View.VISIBLE);
        }
        holder.subject_name_first_tv.setText(listCoupon.getTitle());
        holder.subject_name_second_tv.setText(listCoupon.getTitle());

        holder.join_btn.setVisibility(View.GONE);
        holder.join_second_btn.setVisibility(View.GONE);
        holder.time_tv.setVisibility(View.VISIBLE);
        holder.second_time_tv.setVisibility(View.VISIBLE);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfs1 = new SimpleDateFormat("dd MMM yyyy");
            Date dt, dt1;
            try {
                dt = sdf.parse(listCoupon.getTime());
                dt1 = sdf1.parse(listCoupon.getDate());
                System.out.println("Time Display: " + sdfs.format(dt)); // <-- I got result here
                holder.time_tv.setText("" + sdfs1.format(dt1) + "\n" + sdfs.format(dt));
                holder.second_time_tv.setText("" + sdfs1.format(dt1) + "\n" + sdfs.format(dt));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Check.equals("Live")) {
                  /*  Bundle bundle = new Bundle();
                    bundle.putString("URL", listCoupon.getUrl());
                    bundle.putString("Live_id", listCoupon.getId());
                    ErrorMessage.I(activity, PlayURLActivity.class, bundle);*/
                    //ErrorMessage.I(activity, WebviewActivity.class, bundle);
                    try {
                        if (listCoupon.getUrl().contains("pdf") || listCoupon.getUrl().contains("doc") || listCoupon.getUrl().contains("hindmppscclass.online")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("image", listCoupon.getUrl());
                            bundle.putString("title", listCoupon.getTitle());
                            ErrorMessage.I(activity, PDFViewerActivity.class, bundle);
                        } else {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(listCoupon.getUrl()));
                            activity.startActivity(browserIntent);
                        }
                    } catch (Exception e) {
                    }
                } else {
                  /*  Bundle bundle = new Bundle();
                    bundle.putString("URL", listCoupon.getUrl());
                   // ErrorMessage.I(activity, PlayVideoActivity.class, bundle);
                    ErrorMessage.I(activity, WebviewActivity.class, bundle);*/
                    try {
                        if (listCoupon.getUrl().contains("pdf") || listCoupon.getUrl().contains("doc") || listCoupon.getUrl().contains("hindmppscclass.online")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("image", listCoupon.getUrl());
                            bundle.putString("title", listCoupon.getTitle());
                            ErrorMessage.I(activity, PDFViewerActivity.class, bundle);
                        } else {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(listCoupon.getUrl()));
                            activity.startActivity(browserIntent);
                        }
                    } catch (Exception e) {
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
        TextView subject_name_second_tv, subject_name_first_tv, time_tv, second_time_tv;
        Button join_btn, join_second_btn;
        CardView first_layout, second_layout;
        ImageView subject_img, right_subject_img;

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
            right_subject_img = itemView.findViewById(R.id.right_subject_img);
            subject_img = itemView.findViewById(R.id.subject_img);
        }
    }
}
