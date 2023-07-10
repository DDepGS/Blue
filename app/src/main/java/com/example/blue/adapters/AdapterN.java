package com.example.blue.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.blue.R;
import com.example.blue.models.notificacion;

import java.util.List;

public class AdapterN extends ArrayAdapter<notificacion> {
    Context context;
    List<notificacion> arreglon;

    public AdapterN(@NonNull Context context, List<notificacion>arreglon)
    {
        super(context, R.layout.lista_notificacion, arreglon);
        this.context=context;
        this.arreglon=arreglon;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_notificacion, null, true);

        LinearLayout Forma = view.findViewById(R.id.layoutNotif);
        TextView Nombre = view.findViewById(R.id.Nombre);
        TextView Descricpion = view.findViewById(R.id.Descripcion);

        if(arreglon.get(position).getEstado().equals("Pendiente"))
        {
            Forma.setBackground(ContextCompat.getDrawable(context, R.drawable.cuadroverdeazul));
        }
        Nombre.setText(arreglon.get(position).getNombre());
        Descricpion.setText(arreglon.get(position).getDescripcion());
        return view;
    }
}
