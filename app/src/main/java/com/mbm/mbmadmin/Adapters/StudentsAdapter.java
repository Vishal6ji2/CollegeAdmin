package com.mbm.mbmadmin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.imageview.ShapeableImageView;
import com.mbm.mbmadmin.Activities.StudentsListActivity;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.GetAdminStudentsResponse;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.Collection;

import static com.mbm.mbmadmin.Activities.StudentsListActivity.arrstudentlist;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.ViewHolder> implements Filterable {

    ShapeableImageView profileimg;

    ImageView cancelimg;

    TextView txtname, txtemail, txtmob, txtbranch, txtregno;

    Context context;

    @NonNull
    public static ArrayList<GetAdminStudentsResponse.Student> arrstudentslist = new ArrayList<>();

    ArrayList<GetAdminStudentsResponse.Student> studentArrayList;

    public static int flag = 0;

    public StudentsAdapter(@NonNull Context context, @NonNull ArrayList<GetAdminStudentsResponse.Student> arrstudentslist) {
        this.context = context;
        StudentsAdapter.arrstudentslist = arrstudentslist;
        studentArrayList = new ArrayList<>(arrstudentslist);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.customstudentlayout, parent, false));
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return examplefilter;
    }

    public void filterlist(@NonNull ArrayList<GetAdminStudentsResponse.Student> filterlist) {
        arrstudentslist = filterlist;
        notifyDataSetChanged();
    }

    @NonNull
    public Filter examplefilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<GetAdminStudentsResponse.Student> filterlist = new ArrayList<>();

            if (constraint.toString().isEmpty()) {
                filterlist.addAll(studentArrayList);
            } else {

                for (GetAdminStudentsResponse.Student student : studentArrayList) {
                    if (student.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filterlist.add(student);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filterlist;


            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            arrstudentslist.clear();
            arrstudentslist.addAll((Collection<? extends GetAdminStudentsResponse.Student>) results.values);
            notifyDataSetChanged();
        }
    };


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.studentname.setText(arrstudentslist.get(position).getName());

        if (arrstudentslist.get(position).getStatus().equals("0")) {
            holder.studentstatus.setImageResource(R.drawable.blockusericongray);
        } else {
            holder.studentstatus.setVisibility(View.GONE);
        }

        holder.studentregno.setText(arrstudentslist.get(position).getRegNo());

        if (arrstudentslist.get(position).getProfile() == null) {
            holder.studentimg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mbmlogo));
        } else {
            Glide.with(context).load(arrstudentslist.get(position).getProfile()).into(holder.studentimg);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag != 0) {
                    if (StudentsListActivity.isinActionmode) {
                        ((StudentsListActivity) context).prepareSelection(position);
                        notifyItemChanged(position);
                    }
                }else {
                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
                    View bottomsheetview = LayoutInflater.from(context).inflate(R.layout.fullstudentlayout, null);
                    studentbottomsheetviews (bottomsheetview);

                    txtname.setText(arrstudentslist.get(position).getName());
                    txtregno.setText(arrstudentlist.get(position).getRegNo());
                    txtmob.setText(arrstudentlist.get(position).getMobile());
                    txtemail.setText(arrstudentlist.get(position).getEmail());
                    txtbranch.setText(arrstudentlist.get(position).getDepartmentName());

                    if (arrstudentlist.get(position).getProfile() == null) {
                        profileimg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mbmlogo));
                    } else {
                        Glide.with(context).load(arrstudentlist.get(position).getProfile()).into(profileimg);
                    }
                    cancelimg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bottomSheetDialog.dismiss();
                        }
                    });
                    bottomSheetDialog.setContentView(bottomsheetview);
                    bottomSheetDialog.show();

                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                ((StudentsListActivity)context).prepareSelection(position);
                flag = 1;

                return true;
            }
        });

        if (StudentsListActivity.isinActionmode) {

            if (StudentsListActivity.selectedStudentsArrayList.contains(arrstudentslist.get(position))) {
                holder.itemView.setBackgroundResource(R.color.colorlightgray);

            } else {
                holder.itemView.setBackgroundResource(android.R.color.white);

            }
        } else {
            holder.itemView.setBackgroundResource(android.R.color.white);
        }

    }

    public void removedata(@NonNull ArrayList<GetAdminStudentsResponse.Student> selectedStudentArraylist) {

        for (GetAdminStudentsResponse.Student student:selectedStudentArraylist) {
            arrstudentslist.remove(student);
        }
        notifyDataSetChanged();
        flag = 0;
    }

    @Override
    public int getItemCount() {
        return arrstudentslist.size();
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

        TextView studentname,studentregno;
        ShapeableImageView studentimg;
        ImageView studentstatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            studentname = itemView.findViewById(R.id.customstudent_txtname);
            studentregno = itemView.findViewById(R.id.customstudent_rollno);

            studentimg = itemView.findViewById(R.id.customstudent_profileimg);

            studentstatus = itemView.findViewById(R.id.customstudent_checkbox);

        }
    }

    public void studentbottomsheetviews(@NonNull View view) {

        profileimg = view.findViewById(R.id.studentprofile_profileimg);

        txtname = view.findViewById(R.id.studentprofile_txtname);
        txtmob = view.findViewById(R.id.studentprofile_txtmob);
        txtemail = view.findViewById(R.id.studentprofile_txtemail);
        txtbranch = view.findViewById(R.id.studentprofile_txtbranch);
        txtregno = view.findViewById(R.id.studentprofile_txtregno);

        cancelimg = view.findViewById(R.id.studentprofile_cancelimg);

    }

}
