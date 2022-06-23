package com.example.commentposter.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.commentposter.CommentPosterDB;
import com.example.commentposter.R;
import com.example.commentposter.adapter.UserAdapter;
import com.example.commentposter.dao.UserDAO;
import com.example.commentposter.entity.User;

import java.util.List;


public class AdminFragment extends Fragment {

    RecyclerView recyclerView;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_admin, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadUsers();
        return view;
    }
    public void loadUsers(){
        CommentPosterDB commentPosterDB = CommentPosterDB.getUserDatabase(getActivity());
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