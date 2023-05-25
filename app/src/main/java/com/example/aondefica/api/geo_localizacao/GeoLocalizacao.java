package com.example.aondefica.api.geo_localizacao;
import java.util.ArrayList;
import java.util.List;

public class GeoLocalizacao {
    public List<Results> results;

    public GeoLocalizacao(List<Results> results) {
        this.results = results;
    }

    public List<Results> getResults() {
        return results;
    }
}
