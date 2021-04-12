package com.mbm.mbmadmin.Adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.SyllabusSuitcase;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class SyllabusAdapter extends RecyclerView.Adapter<SyllabusAdapter.ViewHolder> implements Filterable {

    Context context;
    ArrayList<SyllabusSuitcase> arrsyllabuslist = new ArrayList<>();


    public SyllabusAdapter(Context context, ArrayList<SyllabusSuitcase> arrsyllabuslist) {
        this.context = context;
        this.arrsyllabuslist = arrsyllabuslist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.customsyllabuslayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.pdfname.setText(arrsyllabuslist.get(position).pdfname);
        holder.pdfimg.setImageResource(R.drawable.pdficon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://mbmvishal.000webhostapp.com/files/1615697689Continuity_Form%20(1).pdf";
//                String davUrl = "ms-excel:ofv|u|" + url;
                Uri targetUri = Uri.parse(url);
//                https://mbmvishal.000webhostapp.com/files/1615699459assignment3.cpp
//                https://mbmvishal.000webhostapp.com/files/1615697689Continuity_Form%20(1).pdf
                 Intent intent = new Intent(android.content.Intent.ACTION_VIEW,targetUri);
//                intent.setDataAndType(targetUri,"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                context.startActivity(intent);
                //                String mime = "*/*";
//                MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//                if (mimeTypeMap.hasExtension(
//                        MimeTypeMap.getFileExtensionFromUrl(targetUri.toString()))) {
//                    mime = mimeTypeMap.getMimeTypeFromExtension(
//                            MimeTypeMap.getFileExtensionFromUrl(targetUri.toString()));
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrsyllabuslist.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public void filterlist(ArrayList<SyllabusSuitcase> filterlist) {
        arrsyllabuslist = filterlist;
        notifyDataSetChanged();
    }

    public static String getMimeType(Context context, Uri uri) {
        String extension;

        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(Objects.requireNonNull(uri.getPath()))).toString());

        }

        return extension;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView pdfimg;
        TextView pdfname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pdfimg = itemView.findViewById(R.id.customsyllabus_pdficon);
            pdfname = itemView.findViewById(R.id.customsyllabus_pdfname);
        }
    }
}
