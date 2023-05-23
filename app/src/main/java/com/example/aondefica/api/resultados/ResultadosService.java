package com.example.aondefica.api.resultados;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ResultadosService {
    @GET("{uf}/{nome}/{referencia}/json")
    Call<List<Resultados>> buscarResultados(@Path("uf") String uf, @Path("nome") String nome, @Path("referencia") String referencia);
}
