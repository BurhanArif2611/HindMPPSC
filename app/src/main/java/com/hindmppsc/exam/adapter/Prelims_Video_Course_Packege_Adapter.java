package com.hindmppsc.exam.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.Live_Classes.LiveClassActivity;
import com.hindmppsc.exam.activity.Live_Classes.PackegeListActivity;

import com.hindmppsc.exam.activity.Prelims_Video_Course.PaperUnitActivity;
import com.hindmppsc.exam.activity.Prelims_Video_Course.Prelims_Video_Course_PackegeActivity;
import com.hindmppsc.exam.models.MaterialType_models.Result;
import com.hindmppsc.exam.utility.ErrorMessage;

import java.util.List;

public class Prelims_Video_Course_Packege_Adapter extends RecyclerView.Adapter<Prelims_Video_Course_Packege_Adapter.MyViewHolder> {
    View view;
    Context activity;
    List<Result> arrayList;


    public Prelims_Video_Course_Packege_Adapter(Context policyActivity, List<Result> arrayList) {
        this.activity = policyActivity;
        this.arrayList = arrayList;


    }

    @NonNull
    @Override
    public Prelims_Video_Course_Packege_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.packege_list_adapter, parent, false);
        return new Prelims_Video_Course_Packege_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Prelims_Video_Course_Packege_Adapter.MyViewHolder holder, int position) {
        final Result listCoupon = arrayList.get(position);
        int p = position + 1;
        if ((p % 2) == 0) {
            holder.first_layout.setVisibility(View.VISIBLE);
            holder.second_layout.setVisibility(View.GONE);
        } else {
            holder.first_layout.setVisibility(View.GONE);
            holder.second_layout.setVisibility(View.VISIBLE);
        }
        if (listCoupon.getSubscribe().equals("subscribe")) {
            holder.packege_price_tv.setText("Attend Class" );
            holder.second_packege_price_tv.setText("Attend Class" );
        } else {
            holder.packege_price_tv.setText("₹ " + listCoupon.getPrice());
            holder.second_packege_price_tv.setText("₹ " + listCoupon.getPrice());
        }
        holder.subject_name_first_tv.setText(listCoupon.getPackage());
        holder.subject_name_second_tv.setText(listCoupon.getPackage());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listCoupon.getSubscribe().equals("subscribe")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
                    bundle.putString("material_type_id", String.valueOf(listCoupon.getMaterial_type_id()));
                    bundle.putString("paper_id", String.valueOf(listCoupon.getPaper_id()));
                    ErrorMessage.I(activity, PaperUnitActivity.class, bundle);
                } else {
                    ((Prelims_Video_Course_PackegeActivity) activity).GO_TO_NextPage(String.valueOf(listCoupon.getId()), String.valueOf(listCoupon.getExamType()), String.valueOf(listCoupon.getPrice()), String.valueOf(listCoupon.getMaterial_type_id()), String.valueOf(listCoupon.getPaper_id()));
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView subject_name_second_tv, subject_name_first_tv, packege_price_tv, second_packege_price_tv;
        CardView first_layout, second_layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            subject_name_second_tv = (TextView) itemView.findViewById(R.id.subject_name_second_tv);
            subject_name_first_tv = (TextView) itemView.findViewById(R.id.subject_name_first_tv);
            second_packege_price_tv = (TextView) itemView.findViewById(R.id.second_packege_price_tv);
            packege_price_tv = (TextView) itemView.findViewById(R.id.packege_price_tv);

            first_layout = itemView.findViewById(R.id.first_layout);
            second_layout = itemView.findViewById(R.id.second_layout);
        }
    }
}
