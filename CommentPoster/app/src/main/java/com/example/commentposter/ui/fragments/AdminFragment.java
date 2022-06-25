package com.example.commentposter.ui.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.commentposter.CommentPosterDB;
import com.example.commentposter.R;
import com.example.commentposter.adapter.UserAdapter;
import com.example.commentposter.dao.UserDAO;
import com.example.commentposter.entity.User;

import java.util.List;


public class AdminFragment extends Fragment {

    RecyclerView recyclerView;
    Button btnExport;
    Intent myFileIntent;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_admin, container, false);
        btnExport = view.findViewById(R.id.btnExport);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        loadUsers();

        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myFileIntent=new Intent(Intent.ACTION_GET_CONTENT);
                Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                startActivityForResult(Intent.createChooser(i, "Choose folder to Save"), 9999);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 9999:
                if(resultCode==RESULT_OK){
                    String Fpath = data.getData().getPath();
                        exportUserDataAsFile(Fpath);
                }
        }
    }

    private void exportUserDataAsFile(String fpath) {
        //export code here
    }

    public void loadUsers(){
        CommentPosterDB commentPosterDB = CommentPosterDB.getCommentPosterDB(getActivity());
        UserDAO userDAO = commentPosterDB.userDAO();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<User> users = userDAO.getAllUsers();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        UserAdapter userAdapter = new UserAdapter(users);
                        recyclerView.setAdapter(userAdapter);
                    }
                });
            }
        }).start();
    }
}