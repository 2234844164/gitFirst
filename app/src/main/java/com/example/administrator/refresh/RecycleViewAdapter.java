package com.example.administrator.refresh;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>{
    List<String> list;
    static Activity context;
    private static DisplayMetrics metric;
    public RecycleViewAdapter(List<String> list, Activity context){
        this.list = list;
        this.context = context;
        metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Uri uri = Uri.parse(list.get(position));
        holder.mImageView.setImageURI(uri);
        holder.tv.setText("" + position);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        SimpleDraweeView mImageView;
        TextView tv;
        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (SimpleDraweeView)itemView.findViewById(R.id.mImageView);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mImageView.getLayoutParams();
            tv = (TextView) itemView.findViewById(R.id.tv);
            params.height =metric.widthPixels*2/3;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int selectNum = (int)v.getTag();
            Toast.makeText(context, ""+selectNum, Toast.LENGTH_SHORT).show();
        }
    }
}
