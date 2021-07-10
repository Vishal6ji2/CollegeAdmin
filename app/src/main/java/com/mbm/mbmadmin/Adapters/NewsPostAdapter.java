package com.mbm.mbmadmin.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.app.DialogCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;

import com.mbm.mbmadmin.Activities.FullImageShowActivity;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.NewsFetchResponse;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.Objects;


public class NewsPostAdapter extends RecyclerView.Adapter<NewsPostAdapter.ViewHolder> {

    Context context;

    ArrayList<NewsFetchResponse.Newsfeed> arrnewspostlist;

    ArrayList<String> imagelist ;

    ImageView[] dots;

    int dotscount;

    int linecount = 0;


    public NewsPostAdapter(@NonNull Context context, @NonNull ArrayList<NewsFetchResponse.Newsfeed> arrpostlist) {
        this.arrnewspostlist = arrpostlist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.custompostlayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        if (arrnewspostlist!=null) {
            linecount = 0;

            imagelist = arrnewspostlist.get(position).getImages();

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    showAlertDialog(position);

                    return true;
                }
            });


            holder.txtmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    holder.txtdetails.setMaxLines(linecount);
                    holder.txtmore.setVisibility(View.GONE);
                }
            });


            if (imagelist != null && imagelist.size() != 0) {

                holder.cardParent.setVisibility(View.VISIBLE);
                holder.viewPager.setVisibility(View.VISIBLE);

                holder.txtmore.setVisibility(View.GONE);

                if (!arrnewspostlist.get(position).getNewsParagraph().equals("")) {

                    holder.txtdetails.setVisibility(View.VISIBLE);
                    holder.txtheading.setVisibility(View.VISIBLE);

                    holder.txtheading.setText(arrnewspostlist.get(position).getNewsTitle());
                    holder.txtdetails.setText(arrnewspostlist.get(position).getNewsParagraph());

                    linecount = holder.txtdetails.getLineCount();

                    if (linecount > 3) {
                        holder.txtmore.setVisibility(View.VISIBLE);
                    } else {
                        holder.txtmore.setVisibility(View.GONE);
                    }

                } else {
                    holder.txtheading.setVisibility(View.GONE);
                    holder.txtdetails.setVisibility(View.GONE);
                }

                if (imagelist.size() == 1) {
                    holder.lldots.setVisibility(View.GONE);
                } else if (imagelist.size() > 1) {

                    preparedots(holder);
                    holder.lldots.setVisibility(View.VISIBLE);
                }
                SnapHelper mSnapHelper = new LinearSnapHelper();
                holder.viewPager.setOnFlingListener(null);
                mSnapHelper.attachToRecyclerView(holder.viewPager);
                holder.viewPager.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
                holder.viewPager.setAdapter(new SlideRecyclerAdapter(context, imagelist));

                setNormalViews(holder, position);


            }
            else if ((imagelist == null) || (imagelist.size() == 0)) {

                holder.viewPager.setVisibility(View.GONE);
                holder.lldots.setVisibility(View.GONE);

                if (!arrnewspostlist.get(position).getNewsParagraph().equals("")) {

                    holder.cardParent.setVisibility(View.VISIBLE);

                    holder.txtheading.setVisibility(View.VISIBLE);
                    holder.txtdetails.setVisibility(View.VISIBLE);

                    holder.txtheading.setText(arrnewspostlist.get(position).getNewsTitle());
                    holder.txtdetails.setText(arrnewspostlist.get(position).getNewsParagraph());

                    linecount = holder.txtdetails.getLineCount();

                    if (linecount > 3) {
                        holder.txtmore.setVisibility(View.VISIBLE);
                    } else {
                        holder.txtmore.setVisibility(View.GONE);
                    }

                    setNormalViews(holder, position);

                } else if (arrnewspostlist.get(position).getNewsParagraph().equals("")) {
                    holder.cardParent.setVisibility(View.GONE);
                }
            }
        }
    }

    private void setNormalViews(ViewHolder holder, int position) {

        holder.profilename.setText(arrnewspostlist.get(position).getAdminName());
        holder.txttimeago.setText(arrnewspostlist.get(position).getNewsTime());
        holder.txtdate.setText(arrnewspostlist.get(position).getNewsDate());

    }

    void showAlertDialog(final int position) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you want to delete this post from Notice feed")
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

    }


    void preparedots(final ViewHolder holder) {

        holder.lldots.removeAllViews();

//        dotscount = sliderAdapter.getCount();

        dotscount = imagelist.size();
        
        dots = new ImageView[dotscount];

        for (int i = 0; i<dotscount;i++){

            dots[i] = new ImageView(context);

                dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.unselectdots));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(4, 0, 4, 0);

            holder.lldots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.selecteddots));


        holder.viewPager.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) holder.viewPager.getLayoutManager();
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return arrnewspostlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView profilename,txtheading,txtdetails,txtdate,txttimeago,txtmore;

        CircularImageView profileimg;

        RelativeLayout cardParent;

        RecyclerView viewPager;

        LinearLayout lldots;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            lldots = itemView.findViewById(R.id.custompost_lldots);

            cardParent = itemView.findViewById(R.id.custompost_parentlayout);

            txtmore = itemView.findViewById(R.id.custompost_txtmore);
            profilename = itemView.findViewById(R.id.custompost_profilename);
            txtheading = itemView.findViewById(R.id.custompost_txtheading);
            txtdetails = itemView.findViewById(R.id.custompost_txtdetails);
            txtdate = itemView.findViewById(R.id.custompost_posttime);
            txttimeago = itemView.findViewById(R.id.custompost_timeago);

            profileimg = itemView.findViewById(R.id.custompost_profileimg);

            viewPager = itemView.findViewById(R.id.custompost_viewpager);

        }
    }
}
