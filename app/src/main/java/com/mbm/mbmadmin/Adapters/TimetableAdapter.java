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

import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.TimetableSuitcase;

import java.sql.Time;
import java.util.ArrayList;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.ViewHolder> {
    Context context;
    ArrayList<TimetableSuitcase> arrtimelist = new ArrayList<>();

    public TimetableAdapter(Context context, ArrayList<TimetableSuitcase> arrtimelist) {
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

        holder.semimg.setImageResource(arrtimelist.get(position).semimg);
        holder.txtsem.setText(arrtimelist.get(position).txtsem);

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
