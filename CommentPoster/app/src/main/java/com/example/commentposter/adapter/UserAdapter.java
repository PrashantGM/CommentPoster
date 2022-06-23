package com.example.commentposter.adapter;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commentposter.R;
import com.example.commentposter.UserDAO;
import com.example.commentposter.UserDatabase;
import com.example.commentposter.UserEntity;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    List<UserEntity> lstUsers;

    public UserAdapter(List<UserEntity> lstUsers) {
        this.lstUsers = lstUsers;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user,parent,false);
       return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
//        String users=lstUsers[position];
        UserEntity userdata=lstUsers.get(position);
        holder.tvName.setText(userdata.getName());
        holder.tvEmail.setText(userdata.getEmail());
        holder.tvDate.setText(userdata.getDate());
        String email=userdata.getEmail();
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                UserDatabase userDatabase=UserDatabase.getUserDatabase(holder.tvEmail.getContext());
                UserDAO userDAO=userDatabase.userDAO();
                userDAO.deleteUser(email);
                lstUsers.remove(userdata);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return lstUsers.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvName,tvEmail,tvDate;
        ImageView ivDelete;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.tvName);
            tvEmail=itemView.findViewById(R.id.tvEmail);
            tvDate=itemView.findViewById(R.id.tvDate);
            ivDelete=itemView.findViewById(R.id.ivDelete);

        }
    }

}
