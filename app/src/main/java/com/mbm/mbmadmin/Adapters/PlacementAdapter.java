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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mbm.mbmadmin.Activities.FullPlacementNewsActivity;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.PlacementNewsFetchResponse;
import com.mbm.mbmadmin.Suitcases.PlacementSuitcase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlacementAdapter extends RecyclerView.Adapter<PlacementAdapter.ViewHolder> {

    Context context;
    ArrayList<PlacementNewsFetchResponse.Placementnews> arrplacementlist;

    List<String> colors = new ArrayList<>();

    public PlacementAdapter(@NonNull Context context, @NonNull ArrayList<PlacementNewsFetchResponse.Placementnews> arrplacementlist) {
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

        holder.cmpname.setText(arrplacementlist.get(position).getCmpName());
        holder.cmpnews.setText(arrplacementlist.get(position).getCmpNews());
        holder.cmptime.setText(arrplacementlist.get(position).getCmpTime());
        holder.cmptitle.setText(arrplacementlist.get(position).getCmpTitle());

        holder.itemView.startAnimation(AnimationUtils.loadAnimation(context,R.anim.tabsanim));

        holder.itemView.setOnClickListener(v -> {
            holder.cmptitle.setTextColor(ContextCompat.getColor(context,R.color.colordarkgrey));
            holder.cmpnews.setTextColor(ContextCompat.getColor(context,R.color.colordarkgrey));
            holder.cmptime.setTextColor(ContextCompat.getColor(context,R.color.colordarkgrey));

            sendCmpData(position);

        });

        holder.itemView.setOnLongClickListener(v -> {

            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Do you want to delete this post from news feed")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        arrplacementlist.remove(position);
                        notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {

                    });
            builder.show();
            return true;
        });

    }

    protected void sendCmpData(int position) {
        Intent intent = new Intent(context,FullPlacementNewsActivity.class);
        intent.putExtra("cmpname",arrplacementlist.get(position).getCmpName());
        intent.putExtra("cmptitle",arrplacementlist.get(position).getCmpTitle());
        intent.putExtra("cmpnews",arrplacementlist.get(position).getCmpNews());
        intent.putExtra("cmpfilename",arrplacementlist.get(position).getCmpFilename());
        intent.putExtra("cmpfilepath",arrplacementlist.get(position).getCmpFilepath());
        intent.putExtra("cmptime",arrplacementlist.get(position).getCmpTime());
        intent.putExtra("cmpuploadedby",arrplacementlist.get(position).getCmpUploadeby());

        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return arrplacementlist.size();
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
