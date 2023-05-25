package com.example.aondefica.api.geo_localizacao;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GeoLocalizacaoService {
    @GET("geocode/json?key=AIzaSyAl3DIOS1BhSlDGBouwSGrv8TI7eaE1CpE")
    Call<GeoLocalizacao> buscarLocalizacao(@Query("address") String address);
}
