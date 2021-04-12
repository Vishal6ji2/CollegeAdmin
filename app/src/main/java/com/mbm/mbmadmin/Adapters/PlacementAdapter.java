package com.mbm.mbmadmin.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.mbm.mbmadmin.Activities.FullPlacementNewsActivity;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.PlacementSuitcase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlacementAdapter extends RecyclerView.Adapter<PlacementAdapter.ViewHolder> {

    Context context;
    ArrayList<PlacementSuitcase> arrplacementlist;

    List<String> colors = new ArrayList<>();

    public PlacementAdapter(Context context, ArrayList<PlacementSuitcase> arrplacementlist) {
        this.context = context;
        this.arrplacementlist = arrplacementlist;
    }

    @NonNull
    @Override
    public PlacementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        colors.add("#5E97F6");
        colors.add("#9CCC65");
        colors.add("#FF8A65");
        colors.add("#9E9E9E");
        colors.add("#9FA8DA");
        colors.add("#90A4AE");
        colors.add("#AED581");
        colors.add("#F6BF26");
        colors.add("#FFA726");
        colors.add("#4DD0E1");
        colors.add("#BA68C8");
        colors.add("#A1887F");
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.customplacementlayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PlacementAdapter.ViewHolder holder, final int position) {


        Random r = new Random();
        int i1 = r.nextInt(11);

        holder.cmpname.setBackgroundColor(Color.parseColor(colors.get(i1)));

        holder.cmpname.setText(arrplacementlist.get(position).companyname);
        holder.cmpnews.setText(arrplacementlist.get(position).placementnews);
        holder.cmptime.setText(arrplacementlist.get(position).cmptime);
        holder.cmptitle.setText(arrplacementlist.get(position).placementtitle);



        holder.itemView.startAnimation(AnimationUtils.loadAnimation(context,R.anim.tabsanim));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "clicked "+position, Toast.LENGTH_SHORT).show();
                holder.cmptitle.setTextColor(context.getResources().getColor(R.color.colordarkgrey));
                holder.cmpnews.setTextColor(context.getResources().getColor(R.color.colordarkgrey));
                holder.cmptime.setTextColor(context.getResources().getColor(R.color.colordarkgrey));
                holder.cmpname.setBackgroundColor(context.getResources().getColor(R.color.colordarkgrey));
                context.startActivity(new Intent(context, FullPlacementNewsActivity.class));
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
                                arrplacementlist.remove(position);
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
        return arrplacementlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView cmpname,cmptitle,cmpnews,cmptime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cmpname = itemView.findViewById(R.id.customplacement_cmpname);
            cmptitle = itemView.findViewById(R.id.customplacement_cmptitle);
            cmptime = itemView.findViewById(R.id.customplacement_cmptime);
            cmpnews = itemView.findViewById(R.id.customplacement_cmpnews);

        }
    }
}
