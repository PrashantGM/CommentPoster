package com.example.commentposter.ui.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.commentposter.CommentPosterDB;
import com.example.commentposter.R;
import com.example.commentposter.adapter.MyPostsAdapter;
import com.example.commentposter.dao.CommentDAO;
import com.example.commentposter.entity.Comment;
import com.example.commentposter.ui.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MyTimelineActivityFragment extends Fragment {


    private FloatingActionButton fabAddPost;
    private RecyclerView myPostsView;
    private ImageView ivRefresh;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_my_timeline, container, false);
        fabAddPost=view.findViewById(R.id.fabAddPost);
        myPostsView=view.findViewById(R.id.myPostsView);
        myPostsView.setLayoutManager(new LinearLayoutManager(getContext()));
        ivRefresh=view.findViewById(R.id.ivRefresh);

        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMyPosts();
                Toast.makeText(getContext(), "Refreshed!", Toast.LENGTH_SHORT).show();
            }
        });

        loadMyPosts();
        fabAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Opening dialog");
                Bundle args = new Bundle();
                args.putString("msg", "newpost");
                NewPostFragment dialogFragment=new NewPostFragment();
                dialogFragment.setArguments(args);
                dialogFragment.show(getChildFragmentManager(),"post_fragment");
            }
        });

        return view;
    }
    public void loadMyPosts(){
        CommentPosterDB commentPosterDB = CommentPosterDB.getCommentPosterDB(getActivity());
        CommentDAO commentDAO = commentPosterDB.commentDAO();
        MainActivity mainActivity=(MainActivity) getActivity();
        Integer userid= mainActivity.getMyUserId();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Comment> comments= commentDAO.getCommentsByUser(userid);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyPostsAdapter myPostsAdapter = new MyPostsAdapter(comments);
                        myPostsView.setAdapter(myPostsAdapter);
                    }
                });
            }
        }).start();
    }

}