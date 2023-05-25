package com.example.aondefica.ui.resultados;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.aondefica.R;
import com.example.aondefica.api.RetrofitConfig;
import com.example.aondefica.api.geo_localizacao.GeoLocalizacao;
import com.example.aondefica.api.resultados.Resultados;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultadosAdapter extends ArrayAdapter<Resultados> {

    private LayoutInflater inflater;
    private TextView tvRua;
    private TextView tvCep;
    private TextView tvBairro;
    private Button btInfo;
    private FragmentManager fragmentManager;
    private String lat;
    private String lng;

    private Resultados resultado;
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

        resultado = getItem(position);

        tvRua = itemView.findViewById(R.id.tvRua);
        tvBairro = itemView.findViewById(R.id.tvBairro);
        tvCep= itemView.findViewById(R.id.tvCep);



        tvBairro.setText(resultado.getBairro());
        tvCep.setText(resultado.getCep());

        btInfo = itemView.findViewById(R.id.btnInfo);
        btInfo.setOnClickListener(e -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Detalhes");

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View dialogView = inflater.inflate(R.layout.dialog_resultados, null);
            builder.setView(dialogView);

            TextView tvCep = dialogView.findViewById(R.id.tvCep);
            TextView tvLogradouro = dialogView.findViewById(R.id.tvLogradouro);
            TextView tvComplemento = dialogView.findViewById(R.id.tvComplemento);
            TextView tvBairro = dialogView.findViewById(R.id.tvBairro);
            TextView tvLocalidade = dialogView.findViewById(R.id.tvLocalidade);
            TextView tvUF = dialogView.findViewById(R.id.tvUF);
            TextView tvIBGE = dialogView.findViewById(R.id.tvIBGE);
            TextView tvGIA = dialogView.findViewById(R.id.tvGIA);
            TextView tvDDD = dialogView.findViewById(R.id.tvDDD);
            TextView tvSIAFI = dialogView.findViewById(R.id.tvSIAFI);

            tvCep.setText("CEP: " + resultado.getCep());
            tvLogradouro.setText("Logradouro: " + resultado.getLogradouro());
            tvComplemento.setText("Complemento: " + resultado.getComplemento());
            tvBairro.setText("Bairro: " + resultado.getBairro());
            tvLocalidade.setText("Localidade: " + resultado.getLocalidade());
            tvUF.setText("UF: " + resultado.getUf());
            tvIBGE.setText("IBGE: " + resultado.getIbge());
            tvGIA.setText("GIA: " + resultado.getGia());
            tvDDD.setText("DDD: " + resultado.getDdd());
            tvSIAFI.setText("SIAFI: " + resultado.getSiafi());


            builder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("Ver no mapa", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    /*MapaFragment fragmentMapa = new MapaFragment();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_mapa, fragmentMapa);
                    fragmentTransaction.commit();*/
                    loadWSGeolocalizacao();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        return itemView;
    }

    private void loadWSGeolocalizacao() {
        String address = resultado.getLogradouro()+", "+resultado.getBairro()+", "+resultado.getLocalidade()+", "+resultado.getUf();
        Call<GeoLocalizacao> call = new RetrofitConfig().getLocalizacao().buscarLocalizacao(address);

        call.enqueue(new Callback<GeoLocalizacao>() {
            @Override
            public void onResponse(Call<GeoLocalizacao> call, Response<GeoLocalizacao> response) {
                Log.i("", ""+response.body());
                GeoLocalizacao buscaLoc = response.body();
                lat = buscaLoc.results.get(0).geometry.location.lat;
                lng = buscaLoc.results.get(0).geometry.location.lng;
            }

            @Override
            public void onFailure(Call<GeoLocalizacao> call, Throwable t) {
                Log.e("Erro", "Falha na chamada: " + t.getMessage());
            }
        });

    }

}

