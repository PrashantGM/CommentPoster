package com.example.commentposter.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.commentposter.CommentPosterDB;
import com.example.commentposter.R;
import com.example.commentposter.dao.CommentDAO;
import com.example.commentposter.entity.Comment;
import com.example.commentposter.ui.fragments.NewPostFragment;

import java.util.List;

public class MyPostsAdapter extends RecyclerView.Adapter<MyPostsAdapter.MyPostsViewHolder>{

    List<Comment> lstComments;
    private Context context;

    public MyPostsAdapter(List<Comment> lstComments) {
        this.lstComments = lstComments;

    }

    @NonNull
    @Override
    public MyPostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_mytimeline,parent,false);
        return new MyPostsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostsViewHolder holder, int position) {
        context=holder.tvPComment.getContext();
        Comment commentdata=lstComments.get(position);
        holder.tvPComment.setText(commentdata.getComment());
        holder.tvPPostDate.setText(commentdata.getDate_posted());
        String comment=commentdata.getComment();
        Integer id=commentdata.getId();
        holder.ivPEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putInt("id",id);
                args.putString("comment", comment);
                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                NewPostFragment dialogFragment=new NewPostFragment();
                dialogFragment.setArguments(args);
                dialogFragment.show(manager, "post_fragment");

            }
        });
        holder.ivPDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CommentPosterDB commentPosterDB = CommentPosterDB.getCommentPosterDB(holder.tvPComment.getContext());
                CommentDAO commentDAO= commentPosterDB.commentDAO();
                commentDAO.deleteComment(commentdata.id,commentdata.userid);
                lstComments.remove(commentdata);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return lstComments.size();
    }

    class MyPostsViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvPComment,tvPPostDate;
        ImageView ivPEdit,ivPDelete;
        public MyPostsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPComment=itemView.findViewById(R.id.tvPComment);
            ivPEdit=itemView.findViewById(R.id.ivPEdit);
            ivPDelete=itemView.findViewById(R.id.ivPDelete);
            tvPPostDate=itemView.findViewById(R.id.tvPPostDate);

        }
    }

}
