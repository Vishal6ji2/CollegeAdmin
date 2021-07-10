package com.mbm.mbmadmin.Adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.LinksSuitcase;

import java.util.ArrayList;

import static com.mbm.mbmadmin.ViewUtils.toast;

public class LinksAdapter extends RecyclerView.Adapter<LinksAdapter.ViewHolder> {
    Context context;
    ArrayList<LinksSuitcase> arrlinkslist;

    public LinksAdapter(@NonNull Context context, @NonNull ArrayList<LinksSuitcase> arrlinkslist) {
        this.context = context;
        this.arrlinkslist = arrlinkslist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.customlinkslayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.txtlinkname.setText(arrlinkslist.get(position).linkname);
        holder.itemView.setOnClickListener(v -> {
            openWebPage(context,arrlinkslist.get(position).weblink);
        });
    }

    public static void openWebPage(@NonNull Context context, @NonNull String url) {
        try {
            if (!URLUtil.isValidUrl(url)) {
                toast(context, " This is not a valid link");
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            }
        } catch (ActivityNotFoundException e) {
            toast(context, " You don't have any browser to open web page");
        }
    }


    @Override
    public int getItemCount() {
        return arrlinkslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtlinkname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtlinkname = itemView.findViewById(R.id.customlink_txtname);
        }
    }
}
