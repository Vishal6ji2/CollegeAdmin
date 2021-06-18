package com.mbm.mbmadmin.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.bumptech.glide.Glide;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.NoticeFetchResponse;
import com.mbm.mbmadmin.Suitcases.NoticeSuitcase;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    Context context;

    ArrayList<NoticeFetchResponse.Noticetable> arrnoticelist;

    ArrayList<String> arrimglist;

    ImageView[] dots;

    int dotscount;


    public NoticeAdapter(@NonNull Context context, @NonNull ArrayList<NoticeFetchResponse.Noticetable> arrnoticelist) {
        this.context = context;
        this.arrnoticelist = arrnoticelist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.customnoticelayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if (arrnoticelist!=null) {

            arrimglist = arrnoticelist.get(position).getImages();

            if (arrimglist != null && arrimglist.size() != 0) {

                holder.cardParent.setVisibility(View.VISIBLE);
                holder.recyclerView.setVisibility(View.VISIBLE);

                if (arrimglist.size() == 1) {
                    holder.lldots.setVisibility(View.GONE);
                } else if (arrimglist.size() > 1) {

                    preparedots(holder);
                    holder.lldots.setVisibility(View.VISIBLE);
                }
                SnapHelper mSnapHelper = new LinearSnapHelper();
                holder.recyclerView.setOnFlingListener(null);
                mSnapHelper.attachToRecyclerView(holder.recyclerView);
                holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
                holder.recyclerView.setAdapter(new SlideRecyclerAdapter(context, arrimglist));


            } else if ((arrimglist == null) || (arrimglist.size() == 0)) {

                holder.cardParent.setVisibility(View.GONE);
                holder.recyclerView.setVisibility(View.GONE);
                holder.lldots.setVisibility(View.GONE);

            }


            holder.recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to delete this post from Notice feed")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    arrnoticelist.remove(position);
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
    }


    void preparedots(final NoticeAdapter.ViewHolder holder) {

        holder.lldots.removeAllViews();

//        dotscount = sliderAdapter.getCount();

        dotscount = arrimglist.size();

        dots = new ImageView[dotscount];

        for (int i = 0; i<dotscount;i++){

            dots[i] = new ImageView(context);

            dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.unselectdots));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(4, 0, 4, 0);

            holder.lldots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.selecteddots));


        holder.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) holder.recyclerView.getLayoutManager();
                if (linearLayoutManager != null) {
                    int firstelementposition = linearLayoutManager.findFirstVisibleItemPosition();
                    setDots(firstelementposition);
                }
            }
        });
    }

    public void setDots(int position){
        for (int i = 0; i<dotscount; i++){
            dots[i].setImageDrawable(ContextCompat.getDrawable(context,R.drawable.unselectdots));
        }
        dots[position].setImageDrawable(ContextCompat.getDrawable(context,R.drawable.selecteddots));
    }

    @Override
    public int getItemCount() {

        return arrnoticelist.size();
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

        RecyclerView recyclerView;

        RelativeLayout cardParent;

        LinearLayout lldots;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardParent = itemView.findViewById(R.id.customnotice_parentlayout);

            recyclerView = itemView.findViewById(R.id.customnotice_recyclerview);

            lldots = itemView.findViewById(R.id.customnotice_lldots);

        }
    }
}
