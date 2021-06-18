package com.mbm.mbmadmin.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.TimetableFetchResponse;
import com.mbm.mbmadmin.Suitcases.TimetableSuitcase;

import java.sql.Time;
import java.util.ArrayList;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.ViewHolder> {
    Context context;
    ArrayList<TimetableFetchResponse.Timetable> arrtimelist;

    public TimetableAdapter(@NonNull Context context, @NonNull ArrayList<TimetableFetchResponse.Timetable> arrtimelist) {
        this.context = context;
        this.arrtimelist = arrtimelist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.customtimelayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if (arrtimelist.get(position).getTimetableimagePath() == null){
            holder.semimg.setVisibility(View.GONE);
        }else {
            holder.semimg.setVisibility(View.VISIBLE);
            Glide.with(context).load(arrtimelist.get(position).getTimetableimagePath()).into(holder.semimg);
        }
//        holder.txtsem.setText(arrtimelist.get(position).txtsem);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to delete this post from news feed")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arrtimelist.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrtimelist.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtsem;
        ImageView semimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtsem = itemView.findViewById(R.id.customtime_txtsem);
            semimg = itemView.findViewById(R.id.customtime_semimg);
        }
    }
}
