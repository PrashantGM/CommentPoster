package com.example.commentposter.ui.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commentposter.CommentPosterDB;
import com.example.commentposter.R;
import com.example.commentposter.adapter.CommentAdapter;
import com.example.commentposter.adapter.MyPostsAdapter;
import com.example.commentposter.dao.CommentDAO;
import com.example.commentposter.entity.Comment;
import com.example.commentposter.entity.User;
import com.example.commentposter.ui.MainActivity;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;


public class NewPostFragment extends DialogFragment {

    private EditText dfComment;
    private TextView dfTitle;
    private Button btnPost;
    private ImageView ivCancel;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_new_post, container, false);
        dfComment=view.findViewById(R.id.dfComment);
        btnPost=view.findViewById(R.id.btnPost);
        ivCancel=view.findViewById(R.id.ivCancel);
        dfTitle=view.findViewById(R.id.dfTitle);

        Bundle mArgs = getArguments();
         Integer idForUpdate  =mArgs.getInt("id");
        String commentForUpdate = mArgs.getString("comment");
//        String msg=mArgs.getString("msg");
        if(idForUpdate>0){
            dfComment.setText(commentForUpdate);
            btnPost.setText("Update");
            dfTitle.setText("Update Post");
        }


        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
            }
        });
        CommentPosterDB commentPosterDB = CommentPosterDB.getCommentPosterDB(getActivity());
        final CommentDAO commentDAO= commentPosterDB.commentDAO();
        btnPost.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String comment=dfComment.getText().toString();
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                String date = year + "/" + (month + 1) + "/" + day;
                MainActivity mainActivity=(MainActivity) getActivity();
                Integer userid= mainActivity.getMyUserId();
                String name=mainActivity.getMyName();
                Comment commententity=new Comment();
                commententity.setComment(comment);
                commententity.setDate_posted(date);
                commententity.setUsername(name);
                commententity.setUserid(userid);
                if(!comment.equals("")){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            if(idForUpdate>0){
                                commentDAO.updateComment(comment,idForUpdate,userid);

                            }
                            else{
                                commentDAO.postComment(commententity);

                                Toast.makeText(getActivity(), "Successfully Posted!", Toast.LENGTH_SHORT).show();
                            }

                            getDialog().dismiss();

                        }
                    });
                }
                else{
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Empty comments are not allowed!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return view;
    }
}