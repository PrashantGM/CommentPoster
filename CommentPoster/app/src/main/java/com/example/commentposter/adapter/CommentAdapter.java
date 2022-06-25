package com.example.commentposter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.commentposter.R;

import com.example.commentposter.entity.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    List<Comment> lstComments;

    public CommentAdapter(List<Comment> lstComments) {
        this.lstComments = lstComments;

    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_gtimeline,parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment commentdata = lstComments.get(position);
        holder.tvTComment.setText(commentdata.getComment());
        holder.tvTPostDate.setText(commentdata.getDate_posted());
        holder.tvTName.setText("By " + commentdata.getUsername());
    }


    @Override
    public int getItemCount() {
        return lstComments.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvTComment,tvTName,tvTPostDate;
        ImageView ivDelete;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTComment=itemView.findViewById(R.id.tvTComment);
            tvTName=itemView.findViewById(R.id.tvTName);
            tvTPostDate=itemView.findViewById(R.id.tvTPostDate);

        }
    }

}
