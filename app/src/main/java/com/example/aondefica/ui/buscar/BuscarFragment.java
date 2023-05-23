package com.example.aondefica.ui.buscar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.aondefica.MainActivity;
import com.example.aondefica.R;
import com.example.aondefica.api.RetrofitConfig;
import com.example.aondefica.api.estados_cidades.Cidades;
import com.example.aondefica.api.estados_cidades.Estados;
import com.example.aondefica.api.resultados.Resultados;
import com.example.aondefica.api.resultados.ResultadosDAO;
import com.example.aondefica.databinding.FragmentBuscarBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
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
    private int qtdSalva;

    private AlertDialog alerta;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);
        spEstados = (Spinner) view.findViewById(R.id.spEstado);
        spCidades = (Spinner) view.findViewById(R.id.spCidade);
        etReferencia = view.findViewById(R.id.etReferencia);
        btBuscar = view.findViewById(R.id.btBuscar);
        btBuscar.setEnabled(false);
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
        spCidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                itemCidade = (Cidades) adapterView.getItemAtPosition(i);
                btBuscar.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btBuscar.setOnClickListener(e->{
            sendWsResultados();
        });

        return view;
    }

    private void sendWsResultados() {
        Log.i("res ", ""+etReferencia.getText().toString());
        String ref = etReferencia.getText().toString();
        Call<List<Resultados>> call = new RetrofitConfig().getResultados().buscarResultados(itemEstado.sigla, itemCidade.nome, ref);

        call.enqueue(new Callback<List<Resultados>>() {
            @Override
            public void onResponse(Call<List<Resultados>> call, Response<List<Resultados>> response) {
                List<Resultados> res = response.body();
                if(res!=null && res.size()>0){
                    Gson gson = new Gson();
                    List<Resultados> listResultados = new ArrayList<>();
                    listResultados.addAll(res);

                    ResultadosDAO dalResult = new ResultadosDAO(requireContext());
                    //dalResult.apagarTodos();//apaga o conteudo
                    for (Resultados r : listResultados) {
                        //Resultados resultado = new Resultados(r.id, r.cep, r.logradouro, r.bairro);
                        dalResult.salvar(r);
                    }
                    qtdSalva = listResultados.size();
                    alertSuccess();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Erro"); //define o titulo
                    builder.setMessage("Nenhum cep foi encontrado com essas informações"); //define a mensagem
                    //define um botão como positivo
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            // código a ser executado
                        }
                    });
                    alerta = builder.create(); //cria o AlertDialog
                    alerta.show(); //Exibe

                }
            }

            @Override
            public void onFailure(Call<List<Resultados>> call, Throwable t) {

            }
        });
    }

    private void alertSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sucesso"); //define o titulo
        builder.setMessage(qtdSalva+" CEPs foram encontrados e salvos com essas informações, vá para a sessão resultados!"); //define a mensagem

        //define um botão como positivo
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                resetValues();
            }
        });
        alerta = builder.create(); //cria o AlertDialog
        alerta.show(); //Exibe
    }

    private void resetValues() {
        itemCidade=null;
        etReferencia.setText("");

        spCidades.setAdapter(null);
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
            }
            @Override
            public void onFailure(Call<List<Estados>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}