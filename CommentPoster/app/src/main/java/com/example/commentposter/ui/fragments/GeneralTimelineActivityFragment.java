package com.example.commentposter.ui.fragments;

import static android.widget.LinearLayout.VERTICAL;
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
import com.example.commentposter.adapter.CommentAdapter;
import com.example.commentposter.dao.CommentDAO;
import com.example.commentposter.entity.Comment;
import com.example.commentposter.ui.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class GeneralTimelineActivityFragment extends Fragment {

    private FloatingActionButton fabAddPost;
    private RecyclerView timelineView;
    private ImageView ivRefresh;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_timeline, container, false);
        fabAddPost=view.findViewById(R.id.fabAddPost);
        ivRefresh=view.findViewById(R.id.ivRefresh);

        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               loadTimeline();
                Toast.makeText(getContext(), "Refreshed!", Toast.LENGTH_SHORT).show();
            }
        });
        timelineView=view.findViewById(R.id.timelineView);
       LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
       timelineView.setLayoutManager(linearLayoutManager);
        CommentPosterDB commentPosterDB = CommentPosterDB.getCommentPosterDB(getActivity());
        CommentDAO commentDAO = commentPosterDB.commentDAO();
        loadTimeline();
        MainActivity mainActivity=(MainActivity) getActivity();
        String userType= mainActivity.getUserType();
        if(userType.equals("adminUser")){
            fabAddPost.setVisibility(View.GONE);
        }
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

    public void loadTimeline(){
        CommentPosterDB commentPosterDB = CommentPosterDB.getCommentPosterDB(getActivity());
        CommentDAO commentDAO = commentPosterDB.commentDAO();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Comment> comments= commentDAO.getAllComments();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        CommentAdapter commentAdapter = new CommentAdapter(comments);
                        commentAdapter.notifyDataSetChanged();
                        timelineView.setAdapter(commentAdapter);

                    }
                });
            }
        }).start();
    }
}