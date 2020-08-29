package com.hindmppsc.exam.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.models.Live_class_comment_models.Result;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Live_Class_Comment_Adapter extends RecyclerView.Adapter<Live_Class_Comment_Adapter.MyViewHolder> {
    View view;
    Context activity;
    List<Result> arrayList;



    public Live_Class_Comment_Adapter(Context policyActivity, List<Result> arrayList) {
        this.activity = policyActivity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Live_Class_Comment_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_comment_adapter, parent, false);
        return new Live_Class_Comment_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Live_Class_Comment_Adapter.MyViewHolder holder, int position) {
        final Result listCoupon = arrayList.get(position);
        holder.user_name_tv.setText(listCoupon.getUserName());
        holder.user_comment_tv.setText(listCoupon.getComment());
       // Glide.with(activity).load(listCoupon.getImage()).placeholder(R.drawable.ic_defult_user).into(holder.user_img);

        Glide.with(activity).load(listCoupon.getImage()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                try {
                    int width = holder.user_img.getMeasuredWidth();
                    int diw = resource.getWidth();
                    if (diw > 0) {
                        int height = 0;
                        height = width * resource.getHeight() / diw;
                        resource = Bitmap.createScaledBitmap(resource, width, height, false);
                    }
                    holder.user_img.setImageBitmap(resource);
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView user_name_tv, user_comment_tv;
        CircleImageView user_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            user_name_tv = (TextView) itemView.findViewById(R.id.user_name_tv);
            user_comment_tv = (TextView) itemView.findViewById(R.id.user_comment_tv);
            user_img = (CircleImageView) itemView.findViewById(R.id.user_img);
        }
    }
}
