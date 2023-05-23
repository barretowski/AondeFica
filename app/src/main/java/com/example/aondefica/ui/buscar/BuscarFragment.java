package com.example.aondefica.ui.buscar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.aondefica.R;
import com.example.aondefica.api.RetrofitConfig;
import com.example.aondefica.api.estados_cidades.Cidades;
import com.example.aondefica.api.estados_cidades.Estados;
import com.example.aondefica.databinding.FragmentBuscarBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscarFragment extends Fragment {

    private FragmentBuscarBinding binding;
    private Spinner spEstados;
    private Spinner spCidades;
    private EditText etReferencia;
    private Button btBuscar;
    private Estados itemEstado;
    private Cidades itemCidade;
    private List<Estados> listEstados;
    private List<Cidades> listCidades;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);
        spEstados = (Spinner) view.findViewById(R.id.spEstado);
        spCidades = (Spinner) view.findViewById(R.id.spCidade);
        etReferencia = view.findViewById(R.id.etReferencia);
        btBuscar = view.findViewById(R.id.btBuscar);
        loadWsEstado();
        spEstados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                itemEstado = (Estados) adapterView.getItemAtPosition(i);
                loadWsCidade();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    private void loadWsCidade() {
        Call<List<Cidades>> call = new RetrofitConfig().getEstadoService().buscarCidades(itemEstado.getSigla());
        call.enqueue(new Callback<List<Cidades>>() {
            @Override
            public void onResponse(Call<List<Cidades>> call, Response<List<Cidades>> response) {
                listCidades = new ArrayList<Cidades>();
                listCidades.addAll(response.body());

                ArrayAdapter<String> cidadeAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listCidades);
                spCidades.setAdapter(cidadeAdapter);
            }

            @Override
            public void onFailure(Call<List<Cidades>> call, Throwable t) {

            }
        });
    }

    private void loadWsEstado() {
        Call<List<Estados>> call = new RetrofitConfig().getEstadoService().buscarEstados();
        call.enqueue(new Callback<List<Estados>>() {
            @Override
            public void onResponse(Call<List<Estados>> call, Response<List<Estados>> response) {
                listEstados = new ArrayList<Estados>();
                listEstados.addAll(response.body());

                ArrayAdapter<Estados> estadoAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listEstados);
                spEstados.setAdapter(estadoAdapter);
                itemEstado=listEstados.get(0);

                loadWsCidade();
            }
            @Override
            public void onFailure(Call<List<Estados>> call, Throwable t) {

            }
        });
    }

    public void buscarEstadosCidades(){
        Call<List<Estados>> call = new RetrofitConfig().getEstadoService().buscarEstados();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}