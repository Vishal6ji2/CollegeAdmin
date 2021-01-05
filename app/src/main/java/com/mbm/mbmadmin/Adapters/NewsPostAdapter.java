package com.mbm.mbmadmin.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.DialogCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mbm.mbmadmin.Activities.NewsfeedActivity;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.NewsPostSuitcase;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class NewsPostAdapter extends RecyclerView.Adapter<NewsPostAdapter.ViewHolder> {

    Context context;
    ArrayList<NewsPostSuitcase> arrnewspostlist = new ArrayList<>();


    public NewsPostAdapter(Context context, ArrayList<NewsPostSuitcase> arrpostlist) {
        this.arrnewspostlist = arrpostlist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.custompostlayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.profileimg.setImageResource(arrnewspostlist.get(position).profileimg);

        holder.postimg.setImageResource(arrnewspostlist.get(position).postimg);

        holder.profilename.setText(arrnewspostlist.get(position).profilename);
        holder.txtheading.setText(arrnewspostlist.get(position).txtheading);
        holder.txtdetails.setText(arrnewspostlist.get(position).txtdetails);
        holder.datetime.setText(arrnewspostlist.get(position).datetime);
        holder.timeago.setText(arrnewspostlist.get(position).timeago);

        holder.postimg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder builder = new  AlertDialog.Builder(context);
                builder.setMessage("Do you want to delete this post from news feed")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arrnewspostlist.remove(position);
                                dialog.dismiss();
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        });
                builder.show();
                return true;
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final AlertDialog.Builder builder = new  AlertDialog.Builder(context);
                builder.setMessage("Do you want to delete this post from news feed")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arrnewspostlist.remove(position);
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
        return arrnewspostlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView profilename,txtheading,txtdetails,datetime,timeago;
        CircularImageView profileimg;
        ImageView postimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profilename = itemView.findViewById(R.id.custompost_profilename);
            txtheading = itemView.findViewById(R.id.custompost_txtheading);
            txtdetails = itemView.findViewById(R.id.custompost_txtdetails);
            datetime = itemView.findViewById(R.id.custompost_posttime);
            timeago = itemView.findViewById(R.id.custompost_timeago);

            profileimg = itemView.findViewById(R.id.custompost_profileimg);

            postimg = itemView.findViewById(R.id.custompost_postimg); }
    }
}
