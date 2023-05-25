package com.example.aondefica.ui.mapa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.lifecycle.ViewModelProvider;

import com.example.aondefica.R;
import com.example.aondefica.databinding.FragmentMapaBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapaBinding binding;
    private MapView mapView;
    private GoogleMap googleMap;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);
        mapView = view.findViewById(R.id.mapView);
        mapView.setClickable(true);

        posicionar();

        Bundle mapViewBundle = null;
        if(savedInstanceState!=null){
            mapViewBundle=savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        return view;
    }

    private void posicionar() {
        //PONTO CENTRAL DA CIDADE
        LatLng latLong = new LatLng(-22.1244244, -51.3860479);

        if (googleMap != null) {
            // MARCADOR NO MAPA
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLong);
            googleMap.addMarker(markerOptions);

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLong));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {//evento que ocorre quando uma activity dceve ser destruida e recriada, exemplo: dispositov é rotacionado
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle (MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle (MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle); // informa ao mapView que ocorreu um evento onSaveInstace
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMinZoomPreference(12);
        this.googleMap.setIndoorEnabled(true);

        // Define a posição inicial do mapa
        LatLng initialPosition = new LatLng(-22.1244244, -51.3860479);
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPosition, 15f));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(initialPosition);
        googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(initialPosition));

        UiSettings settings = this.googleMap.getUiSettings();
        settings.setIndoorLevelPickerEnabled(true);

        settings.setMyLocationButtonEnabled(true);
        settings.setMapToolbarEnabled(true);
        settings.setCompassEnabled(true);
        settings.setZoomControlsEnabled(true);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}