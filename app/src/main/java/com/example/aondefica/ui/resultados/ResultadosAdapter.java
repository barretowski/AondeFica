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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.example.aondefica.R;
import com.example.aondefica.api.RetrofitConfig;
import com.example.aondefica.api.geo_localizacao.GeoLocalizacao;
import com.example.aondefica.api.resultados.Resultados;
import com.example.aondefica.listener.MapListener;
import com.example.aondefica.ui.mapa.MapaFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultadosAdapter extends ArrayAdapter<Resultados> {
    private MapListener listener;
    private LayoutInflater inflater;
    private TextView tvRua;
    private TextView tvCep;
    private TextView tvBairro;
    private Button btInfo;
    private FragmentManager fragmentManager;
    private String lat;
    private String lng;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Resultados resAtual;
    private Resultados resultado;
    public ResultadosAdapter(Context context, ArrayList<Resultados> resultadosList, MapListener listener) {
        super(context, 0, resultadosList);
        inflater = LayoutInflater.from(context);
        this.listener = listener;
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



        tvRua.setText(resultado.getLogradouro());
        tvBairro.setText(resultado.getBairro());
        tvCep.setText(resultado.getCep());

        btInfo = itemView.findViewById(R.id.btnInfo);
        btInfo.setTag(position);
        btInfo.setOnClickListener(e -> {
            int pos = (int) e.getTag();
            resAtual = getItem(pos);
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

            tvCep.setText("CEP: " + resAtual.getCep());
            tvLogradouro.setText("Logradouro: " + resAtual.getLogradouro());
            tvComplemento.setText("Complemento: " + resAtual.getComplemento());
            tvBairro.setText("Bairro: " + resAtual.getBairro());
            tvLocalidade.setText("Localidade: " + resAtual.getLocalidade());
            tvUF.setText("UF: " + resAtual.getUf());
            tvIBGE.setText("IBGE: " + resAtual.getIbge());
            tvGIA.setText("GIA: " + resAtual.getGia());
            tvDDD.setText("DDD: " + resAtual.getDdd());
            tvSIAFI.setText("SIAFI: " + resAtual.getSiafi());


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
                    loadWSGeolocalizacao(resAtual);
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        return itemView;
    }

    private void loadWSGeolocalizacao(Resultados res) {
        String address = res.getLogradouro() + ", " + res.getBairro() + ", " + res.getLocalidade() + ", " + res.getUf();
        Call<GeoLocalizacao> call = new RetrofitConfig().getLocalizacao().buscarLocalizacao(address);
        call.enqueue(new Callback<GeoLocalizacao>() {
            @Override
            public void onResponse(Call<GeoLocalizacao> call, Response<GeoLocalizacao> response) {

                GeoLocalizacao buscaLoc = response.body();
                lat = buscaLoc.results.get(0).geometry.location.lat;
                lng = buscaLoc.results.get(0).geometry.location.lng;
                Log.i("", "" + lat + "-" + lng);
                listener.loadMap(lat, lng);
            }

            @Override
            public void onFailure(Call<GeoLocalizacao> call, Throwable t) {
                Log.e("Erro", "Falha na chamada: " + t.getMessage());
            }
        });
    }

}

