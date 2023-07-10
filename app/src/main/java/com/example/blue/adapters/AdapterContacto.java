package com.example.blue.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blue.models.Contacto;
import com.example.blue.databinding.ItemContainerUserBinding;
import com.example.blue.listeners.UserListener;

import java.util.List;

public class AdapterContacto extends RecyclerView.Adapter<AdapterContacto.UserViewHolder>{

    private final List<Contacto> users;
    private final UserListener userListener;

    public AdapterContacto(List<Contacto> users, UserListener userListener) {
        this.users = users;
        this.userListener = userListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerUserBinding itemContainerUserBinding = ItemContainerUserBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new UserViewHolder(itemContainerUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        ItemContainerUserBinding binding;

        UserViewHolder(ItemContainerUserBinding itemContainerUserBinding){
            super(itemContainerUserBinding.getRoot());
            binding = itemContainerUserBinding;
        }

        void setUserData(Contacto user){
            binding.textName.setText(user.name);
            binding.textUserName.setText(user.username);
            binding.getRoot().setOnClickListener(v -> userListener.onUserClicked(user));
        }
    }
}
