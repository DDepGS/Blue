package com.example.blue.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.blue.R;
import com.example.blue.models.publicacion;

import java.util.List;

public class AdapterF extends ArrayAdapter<publicacion> {
    Context context;
    List<publicacion>arreglof;

    public AdapterF(@NonNull Context context, List<publicacion>arreglof)
    {
        super(context, R.layout.lista_publicacion,arreglof);
        this.context=context;
        this.arreglof=arreglof;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_publicacion,null,true);

        TextView titulo = view.findViewById(R.id.Titulo);

        titulo.setText(arreglof.get(position).getTitulo());


        return view;
    }
}
