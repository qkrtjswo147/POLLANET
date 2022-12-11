package com.example.finalproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.type.LatLng;

import net.daum.android.map.MapViewEventListener;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment {
    Context ct;
    MapView mapView;
    Button search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_map, container, false);
        ct = container.getContext();
        FloatingActionButton button= (FloatingActionButton) v.findViewById(R.id.tracking_button);

        mapView= new MapView(getActivity());

        ViewGroup mapViewContainer = (ViewGroup) v.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTracking();
            }
        });
        // 줌 레벨 변경
        mapView.setZoomLevel(7, true);

        search = v.findViewById(R.id.btn_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return v;
    }
    //gps확인

    @SuppressWarnings ({"MissingPermission"})
    public void startTracking(){
        // 현재위치 트래킹
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
        LocationManager locationManager= (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        Location userLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        double uLatitude = userLocation.getLatitude();
        double uLongitude = userLocation.getLongitude();
        MapPoint userNowLocation=MapPoint.mapPointWithGeoCoord(uLatitude, uLongitude);

        //위치찍기
        MapPoint MARKER_POINT = userNowLocation;
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("현재위치");
        marker.setMapPoint(MARKER_POINT);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker);
    }

    // 위치추적 중지
    public void stopTracking(){
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
    }

    public static String getAddress(Context context, double latitude, double longitude) {
        String strAddress="현재 위치를 확인 할 수 없습니다."; Geocoder geocoder=new Geocoder(context, Locale.KOREA); List<Address> address; try {
            address = geocoder.getFromLocation(latitude, longitude, 1); strAddress = address.get(0).getAddressLine(0).toString(); }catch (Exception e) {
        }
        return strAddress; }


}