package com.example.aondefica.ui.resultados;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.aondefica.R;
import com.example.aondefica.api.resultados.Resultados;
import com.example.aondefica.api.resultados.ResultadosDAO;
import com.example.aondefica.databinding.FragmentResultadosBinding;

import java.util.ArrayList;
import java.util.List;

public class ResultadosFragment extends Fragment {

    private FragmentResultadosBinding binding;
    private ListView listView;
    private Button btnApagar;
    private ResultadosDAO dalResult;
    private ArrayList<Resultados> arrayListResultados;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resultados, container, false);
        listView = view.findViewById(R.id.lvResultados);
        btnApagar = view.findViewById(R.id.btApagar);

        dalResult = new ResultadosDAO(requireContext());
        arrayListResultados = dalResult.getAll();
        //List <Resultados> listaResultados = new ArrayList<Resultados>();
        //listaResultados.addAll(arrayListResultados);

        ResultadosAdapter adapterResultados = new ResultadosAdapter(requireContext(), arrayListResultados);
        listView.setAdapter(adapterResultados);


        btnApagar.setOnClickListener(e->{
            dalResult.apagarTodos();
            arrayListResultados.clear();//limpa a lista de resultados
            adapterResultados.notifyDataSetChanged();// Notifica o adaptador sobre as mudanças nos dados
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}