package com.mbm.mbmadmin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.imageview.ShapeableImageView;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.StudentsSuitcase;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.ViewHolder> implements View.OnClickListener, Filterable {

    Context context;
    ArrayList<StudentsSuitcase> arrstudentslist = new ArrayList<>();
    View.OnClickListener onClickListener;

    public StudentsAdapter(Context context, ArrayList<StudentsSuitcase> arrstudentslist) {
        this.context = context;
        this.arrstudentslist = arrstudentslist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customstudentlayout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public void filterlist(ArrayList<StudentsSuitcase> filterlist) {
        arrstudentslist = filterlist;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.studentname.setText(arrstudentslist.get(position).strname);
        if (arrstudentslist.get(position).status.equals("1")){

            holder.studentstatus.setChecked(true);
        }else {
            holder.studentstatus.setChecked(false);
        }
        holder.studentimg.setImageResource(arrstudentslist.get(position).profileimg);

    }

    @Override
    public int getItemCount() {
        return arrstudentslist.size();
    }

    public boolean onClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
        return true;
    }


    @Override
    public void onClick(View v) {

        onClickListener.onClick(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView studentname;
        ShapeableImageView studentimg;
        MaterialCheckBox studentstatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            studentname = itemView.findViewById(R.id.customstudent_txtname);
            studentimg = itemView.findViewById(R.id.customstudent_profileimg);
            studentstatus = itemView.findViewById(R.id.customstudent_checkbox);
        }
    }
}
