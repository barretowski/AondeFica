package com.example.aondefica.api.estados_cidades;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EstadoCidadeService {
    @GET("localidades/estados/")
    Call<List<Estados>> buscarEstados();

    @GET("localidades/estados/{uf}/municipios")
    Call<List<Cidades>> buscarCidades(@Path("uf") String uf);
}
