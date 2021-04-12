package com.mbm.mbmadmin.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.PaperSuitcase;

import java.util.ArrayList;

public class PaperAdapter extends RecyclerView.Adapter<PaperAdapter.ViewHolder> {

    Context context;
    ArrayList<PaperSuitcase> arrpaperlist = new ArrayList<>();

    public PaperAdapter(Context context, ArrayList<PaperSuitcase> arrpaperlist) {
        this.context = context;
        this.arrpaperlist = arrpaperlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.custompaperlayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.pdfimg.setImageResource(R.drawable.fileicon);
        holder.txtpdftime.setText(arrpaperlist.get(position).pdftime);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.pdf995.com/samples/pdf.pdf";

                Uri uri = Uri.parse(url);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri,"application/pdf");

                context.startActivity(intent);

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to delete this post from news feed")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arrpaperlist.remove(position);
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
        return arrpaperlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtpdftime;
        ImageView pdfimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtpdftime = itemView.findViewById(R.id.custompaper_txtyear);
            pdfimg = itemView.findViewById(R.id.custompaper_img);
        }
    }
}
