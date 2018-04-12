package dondesoi.don_de_soi.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import dondesoi.don_de_soi.R;
import dondesoi.don_de_soi.ressources.Center;
import dondesoi.don_de_soi.ressources.ConstantValues;
import dondesoi.don_de_soi.ressources.SaveData;

/**
 * Created by Vitaly on 2/12/2018.
 */
public class MapFragment extends Fragment {

    private MapView mapView;
    private GoogleMap map;
    private onClickListener mListener;
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude", IS_LOC_ENABLED = "enabled";
    private LatLng startingPosition; //default position
    private boolean isLocationEnabled;
    private CameraUpdate cameraUpdate;

    public boolean isMapReady(){
        return (map != null);
    }

    public void placeMarkers(List<Center> centerList){
        if(map != null){
            map.clear();
            for(Center center : centerList){
                map.addMarker(new MarkerOptions().position(center.getLatLng()).
                        title(String.valueOf(center.getId())).snippet(center.getData()).draggable(false));
            }
        }
    }

    public void updateMapIcon(boolean isEnabled){
        if(map != null){
            map.setMyLocationEnabled(isEnabled);
        }
    }

    public void zoom(LatLng latLng){
        cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        map.moveCamera(cameraUpdate);
    }

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);
        if(isLocationEnabled){
            map.setMyLocationEnabled(true);
        }
        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        cameraUpdate = CameraUpdateFactory.newLatLngZoom(startingPosition, ConstantValues.BIG_ZOOM);

        // Updates the location and zoom of the MapView
        map.animateCamera(cameraUpdate);
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(mListener != null){
                    mListener.onMapInteraction(map, marker);
                    return true;
                }
                return false;
            }
        });
        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(getContext());
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(getContext());
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(getContext());
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
        mListener.mapReady();
        return v;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String lat = getArguments().getString(LATITUDE), lon = getArguments().getString(LONGITUDE);
            if(lat.isEmpty() || lon.isEmpty()){
                startingPosition = new LatLng(ConstantValues.DEFAULT_LATITUDE, ConstantValues.DEFAULT_LONGITUDE);
            }else{
                startingPosition = new LatLng(Double.valueOf(lat), Double.valueOf(lon));
            }
            isLocationEnabled = getArguments().getBoolean(IS_LOC_ENABLED);
        }
    }

    public static MapFragment newInstance(String latitude, String longitude, boolean isLocationEnabled){
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(LATITUDE, latitude);
        args.putString(LONGITUDE, longitude);
        args.putBoolean(IS_LOC_ENABLED, isLocationEnabled);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        if(isLocationEnabled){
            map.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        map.setMyLocationEnabled(false);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    public interface onClickListener{
        public void onMapInteraction(GoogleMap map, Marker marker);
        public void mapReady();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onClickListener) {
            mListener = (onClickListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}