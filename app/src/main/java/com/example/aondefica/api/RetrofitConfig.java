package com.example.aondefica.api;
import com.example.aondefica.api.estados_cidades.EstadoCidadeService;
import com.example.aondefica.api.resultados.ResultadosService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitConfig {

    private final Retrofit retrofitEstadoCidade;
    private final Retrofit retrofitResultados;

    public RetrofitConfig() {
        Gson gson = new GsonBuilder().setLenient().create();
        retrofitEstadoCidade = new Retrofit.Builder().baseUrl("https://servicodados.ibge.gov.br/api/v1/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        retrofitResultados = new Retrofit.Builder().baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
    public EstadoCidadeService getEstadoService() {
        return this.retrofitEstadoCidade.create(EstadoCidadeService.class);
    }

    public ResultadosService getResultados(){
        return this.retrofitResultados.create(ResultadosService.class);
    }
}
