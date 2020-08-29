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
import com.hindmppsc.exam.activity.Prelims_Video_Course.Prelims_Video_Course_PackegeActivity;
import com.hindmppsc.exam.activity.Prelims_Video_Course.VideoListActivity;
import com.hindmppsc.exam.models.MaterialType_models.Result;
import com.hindmppsc.exam.utility.ErrorMessage;

import java.util.List;

public class PaperUnitList_Adapter extends RecyclerView.Adapter<PaperUnitList_Adapter.MyViewHolder> {
    View view;
    Context activity;
    List<Result> arrayList;


    public PaperUnitList_Adapter(Context policyActivity, List<Result> arrayList) {
        this.activity = policyActivity;
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public PaperUnitList_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_class_subjects_list, parent, false);
        return new PaperUnitList_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PaperUnitList_Adapter.MyViewHolder holder, int position) {
        final Result listCoupon = arrayList.get(position);
        int p = position + 1;
        if ((p % 2) == 0) {
            holder.first_layout.setVisibility(View.VISIBLE);
            holder.second_layout.setVisibility(View.GONE);
        } else {
            holder.first_layout.setVisibility(View.GONE);
            holder.second_layout.setVisibility(View.VISIBLE);
        }
        holder.subject_name_first_tv.setText(listCoupon.getUnit());
        holder.subject_name_second_tv.setText(listCoupon.getUnit());

      /*  if (listCoupon.getSubscribe().equals("subscribe")) {
            holder.join_btn.setVisibility(View.GONE);
            holder.join_second_btn.setVisibility(View.GONE);
        }*/
        holder.join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Unit_id", String.valueOf(listCoupon.getId()));
                bundle.putString("paper_id", String.valueOf(listCoupon.getPaper_id()));
                bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
                bundle.putString("material_type_id", String.valueOf(listCoupon.getMaterial_type_id()));
                bundle.putString("material", listCoupon.getMaterial());
                ErrorMessage.I(activity, VideoListActivity.class, bundle);


            }
        });
        holder.join_second_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("Unit_id", String.valueOf(listCoupon.getId()));
                bundle.putString("paper_id", String.valueOf(listCoupon.getPaper_id()));
                bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
                bundle.putString("material_type_id", String.valueOf(listCoupon.getMaterial_type_id()));
                bundle.putString("material", listCoupon.getMaterial());
                ErrorMessage.I(activity, VideoListActivity.class, bundle);

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
