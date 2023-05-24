package com.example.aondefica.ui.resultados;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.aondefica.R;
import com.example.aondefica.api.resultados.Resultados;

import java.util.ArrayList;

public class ResultadosAdapter extends ArrayAdapter<Resultados> {

    private LayoutInflater inflater;
    private TextView tvRua;
    private TextView tvCep;
    private TextView tvBairro;
    public ResultadosAdapter(Context context, ArrayList<Resultados> resultadosList) {
        super(context, 0, resultadosList);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = inflater.inflate(R.layout.list_resultados, parent, false);
        }

        Resultados resultado = getItem(position);

        tvRua = itemView.findViewById(R.id.tvRua);
        tvBairro = itemView.findViewById(R.id.tvBairro);
        tvCep= itemView.findViewById(R.id.tvCep);

        tvRua.setText(resultado.getLogradouro());
        tvBairro.setText(resultado.getBairro());
        tvCep.setText(resultado.getCep());
        return itemView;
    }
}

