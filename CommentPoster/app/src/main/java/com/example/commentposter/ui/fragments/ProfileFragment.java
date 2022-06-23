package com.example.commentposter.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.commentposter.CommentPosterDB;
import com.example.commentposter.R;
import com.example.commentposter.dao.UserDAO;
import com.example.commentposter.entity.User;
import com.example.commentposter.ui.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class ProfileFragment extends Fragment {


    private EditText etFName,etFEmail,etFPassword,etFDateRegistered,etFDateUpdated;
    private Button btnFUpdate;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        btnFUpdate= view.findViewById(R.id.btnFUpdate);
        etFName= view.findViewById(R.id.etFName);
        etFEmail= view.findViewById(R.id.etFEmail);
        etFPassword= view.findViewById(R.id.etFPassword);
        etFDateUpdated= view.findViewById(R.id.etFDateUpdated);
        etFDateRegistered= view.findViewById(R.id.etFDateRegistered);

        CommentPosterDB commentPosterDB = CommentPosterDB.getUserDatabase(getActivity());
        final UserDAO userDAO= commentPosterDB.userDAO();
        MainActivity mainActivity=(MainActivity) getActivity();
        String email=mainActivity.getMyEmail();

       mHandler.post(new Runnable() {
            @Override
            public void run() {
                User user = userDAO.getUserData(email);
                etFName.setText(user.name);
                etFEmail.setText(user.email);
                etFPassword.setText(user.password);
                etFDateRegistered.setText(user.dateRegistered);
                etFDateUpdated.setText(user.dateUpdated);
                Toast.makeText(getActivity(), "Successfully Loaded!", Toast.LENGTH_SHORT).show();
            }


        });

        btnFUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=etFName.getText().toString();
                String password=etFPassword.getText().toString();
                String dateUpdated=etFDateUpdated.getText().toString();

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            userDAO.updateUser(name,password,dateUpdated,email);
                            Toast.makeText(getActivity(), "Successfully Updated!", Toast.LENGTH_SHORT).show();
                            MainActivity mainActivity1= (MainActivity) getActivity();
                            mainActivity1.updateNavHeader();

                                    }


                    });

                }

        });

        
        return view;

    }
}