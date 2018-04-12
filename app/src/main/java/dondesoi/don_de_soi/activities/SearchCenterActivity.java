package dondesoi.don_de_soi.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import dondesoi.don_de_soi.R;
import dondesoi.don_de_soi.fragments.MapFragment;
import dondesoi.don_de_soi.ressources.Center;
import dondesoi.don_de_soi.ressources.ConstantValues;
import dondesoi.don_de_soi.ressources.HttpDataHandler;
import dondesoi.don_de_soi.ressources.MethodsUtils;
import dondesoi.don_de_soi.ressources.PermissionRequest;
import dondesoi.don_de_soi.ressources.SaveData;

public class SearchCenterActivity extends BaseActivity implements LocationListener, MapFragment.onClickListener {

    private LocationManager locationManager;
    private MapFragment mapFragment;
    private String provider;
    private LineChart mChart;
    private final int REQUEST_PERMISSION_FINE_LOCATION = 1;
    private final int REQUEST_PERMISSION_COARSE_LOCATION = 1;
    private PermissionRequest permissionRequester;
    private Criteria criteria;
    private List<String> permissions = new ArrayList<String>();
    private List<Integer> permissionFlags = new ArrayList<Integer>();
    private int minSchedule = 8, maxSchedule = 16;
    private List<Center> centerList = new ArrayList<>();
    private SQLiteDatabase coord_database;
    // To collect intel on centers
    // To find places on map
    private boolean isLocationEnabled;
    // Checking variable (if geocoder gave enough results)
    boolean check_result = true, isMapReady = false;

    public void initializeLocationPermissions(){
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissionFlags.add(REQUEST_PERMISSION_COARSE_LOCATION);
        permissionFlags.add(REQUEST_PERMISSION_FINE_LOCATION);
    }
    public void mapReady(){
        isMapReady = mapFragment.isMapReady();
        mapFragment.placeMarkers(ConstantValues.CENTER_LISY);
    }
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_search_center);
        getLayoutInflater().inflate(R.layout.activity_search_center, view_stub);
        setTitle(this.getTitle());

        Button searchButton = findViewById(R.id.buttonSearch);
        final EditText searchedCenter = findViewById(R.id.centerName);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("click search");
                for(Center center : centerList){
                    if(MethodsUtils.containsIgnoreCase(center.getData(), String.valueOf(searchedCenter.getText()))){
                        System.out.println("center found" + center);
                        mapFragment.zoom(center.getLatLng());
                    }
                }
            }
        });
        permissionRequester = new PermissionRequest(this, this);

        // CREATION OF DATABASE : NEED TO PUT IT SOMEHWERE ELSE SORRY !!!
        coord_database = SaveData.createDataBase("coord_db",getApplicationContext());
        //SaveData.createTable(coord_database,"COORDINATES (id INT NOT NULL, lat DOUBLE, long DOUBLE, Text ADDRESS, Text INFO, Text TRANSPORT);");

        initializeLocationPermissions();
        isLocationEnabled = SaveData.getPreferences(ConstantValues.LOCALISATION, this, "true").equals("true");
        //check the location is enabled in the shared preferences
        if(isLocationEnabled){
            // Get the location manager
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            permissionRequester.promptPermissionIfNeeded(permissions, permissionFlags);
            criteria = new Criteria();
            provider = locationManager.getBestProvider(criteria, false);
        }

        //provider null
        Location location = null;
        if(provider != null){
            location = locationManager.getLastKnownLocation(provider);
        }
        // Initialize the location fields
        String latitude = "";
        String longitude = "";
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            latitude = String.valueOf(location.getLatitude());
            longitude = String.valueOf(location.getLongitude());
            this.onLocationChanged(location);
        }
        mapFragment = MapFragment.newInstance(latitude, longitude, isLocationEnabled);
        getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();

        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setViewPortOffsets(0, 0, 0, 0);
        mChart.setBackgroundColor(Color.rgb(104, 241, 175));

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.setMaxHighlightDistance(300);

        XAxis x = mChart.getXAxis();
        x.setEnabled(false);
        x.setTextColor(Color.BLACK);
        x.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);

        YAxis y = mChart.getAxisLeft();
        y.setEnabled(false);
        y.setLabelCount(0, false);
        y.setTextColor(Color.WHITE);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.WHITE);
        mChart.getAxisRight().setEnabled(false);

        // add data
        setData((maxSchedule - minSchedule), 100, 10);

        mChart.getLegend().setEnabled(false);

        mChart.animateXY(2000, 2000);

        // dont forget to refresh the drawing
        mChart.invalidate();

        /*
        try {
            AsyncTask.execute(new MyRunnable());
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }


    private void setData(int count, float range, int multiplier) {
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        int max = count * multiplier;
        int interval = maxSchedule - minSchedule;
        double start = 0;
        double step = 0.1; // Step for graphical purposes
        double busyness = Math.sin(start);
        int beginGap = 12, endGap = 14;
        int beginMidGap = (int) ((beginGap + minSchedule) * 0.5);
        int endMidGap = (int) ((endGap + maxSchedule) * 0.5);
        beginMidGap = (int) (max * ((beginMidGap - minSchedule) / (float) interval));
        endMidGap = (int) (max * ((endMidGap - minSchedule) / (float) interval));
        beginGap = (int) (max * ((beginGap - minSchedule) / (float) interval));
        endGap = (int) (max * ((endGap - minSchedule) / (float) interval));

        double i=0;
        //for (int i = 0; i < max; i++) {
        while (i<interval/step){
            System.out.println(busyness);

            if(i < beginMidGap){
                //System.out.println("i < beginMidGap");
                //busyness -= 1 / (float) (minSchedule * multiplier - beginMidGap);
                busyness += 7*Math.sin(0.1*(start+step));
            }else if(i > beginMidGap && i < beginGap){
                //System.out.println("i > beginMidGap && i < beginGap");
                //busyness += 1/ (float) (beginGap - beginMidGap);
                busyness += 7*Math.sin(0.1*(start+step));
                //start=start+step;
            }else if(i < endMidGap && i > beginGap){
                busyness += 5*Math.sin(0.1*(start+step));
                //start=start+step;
            }else if (i > endMidGap){
                busyness += 5*Math.sin(0.1*(start+step));
                //start=start+step;
            }

            float mult = (range + 1);
            float val = (float) (busyness * mult);
            yVals.add(new Entry((float)i, val));
            System.out.println("value : "+ val);
            i=i+step;
            start=start+step;
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            //set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.WHITE);
            set1.setFillColor(Color.WHITE);
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return -10;
                }
            });

            // create a data object with the datasets
            LineData data = new LineData(set1);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            mChart.setData(data);

        }
    }

    // END  : graphics drawing

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapFragment.updateMapIcon(false);
        try{
            if(locationManager != null){
                locationManager.removeUpdates(this);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        permissionRequester.promptPermissionIfNeeded(permissions, permissionFlags);
        mapFragment.updateMapIcon(isLocationEnabled);
        if(!permissionRequester.permissionNeeded(Manifest.permission.ACCESS_FINE_LOCATION)
                && !permissionRequester.permissionNeeded(Manifest.permission.ACCESS_COARSE_LOCATION));
        try{
            if(locationManager != null){
                provider = locationManager.getBestProvider(criteria, false);
                locationManager.requestLocationUpdates(provider, 400, 1, this);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onMapInteraction(GoogleMap map, Marker marker){
        map.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        marker.showInfoWindow();
        System.out.println(marker.getTitle());
    }

    public class MyRunnable implements Runnable {

        @Override
        public void run() {

            try {

                // To get to the right url
                HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

                DefaultHttpClient client = new DefaultHttpClient();

                SchemeRegistry registry = new SchemeRegistry();
                SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
                socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
                registry.register(new Scheme("https", socketFactory, 443));
                SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
                DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());

                // Set verifier
                HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

                // Opens the website with url parameter
                HttpGet httpPost = new HttpGet("https://dondesang.efs.sante.fr/trouver-une-collecte");
                // Executes the post and gets the response object
                HttpResponse response = httpClient.execute(httpPost);
                // Get the message from the response object
                HttpEntity entity = response.getEntity();
                // Get the content of the message
                InputStream webs = entity.getContent();
                // Read content and display
                StringBuffer sb = new StringBuffer();
                String newline;
                BufferedReader BR = new BufferedReader(new InputStreamReader(webs, "UTF-8"), 8);


                boolean isAddress = false;
                boolean isInfo = false;
                boolean isTransport = false;
                String address = "";
                String info = "";
                String transport = "";
                Double latitude = null, longitude = null;
                int id = 0;

                //while there is data
                while ((newline = BR.readLine()) != null) {

                    String current = new String(newline);

                    // GETTING ADDRESSES HERE
                    if (isAddress) {
                        if (current.contains("</div>")) {
                            //address_cent.add(new String(address));
                            System.out.println(address);
                            //address = "";
                            isAddress = false;
                        } else {
                            address += current.replace("&#039;", "'");
                        }
                    }
                    if ((current.contains("address")) && (current.contains("div"))) {
                        isAddress = true;
                    }

                    // GETTING INTEL HERE
                    if (isInfo) {
                        if (current.contains("</div>")) {
                            //info_cent.add(new String(info));
                            //System.out.println(info);
                            //info = "";
                            isInfo = false;
                        } else {
                            // Here we remove all the irrelevant parts of code in the information we want to get
                            String[] tab = current.split(">");
                            String noHTMLTags = "";
                            if(tab.length > 1){
                                noHTMLTags = tab[1];
                            }
                            info += noHTMLTags.replace("&agrave", " - ");
                        }
                    }

                    if ((current.contains("infos-text")) && (current.contains("div"))) {
                        isInfo = true;
                    }


                    // GETTING WAYS TO ACCESS CENTERS HERE

                    if (isTransport){
                        if (current.contains("</span>")) {
                            //transport_cent.add(new String(transport));
                            //System.out.println(transport);
                            //transport = "";
                            isTransport = false;
                        } else {
                            if (current.contains("parking")){
                                transport = "Parking : ";
                                transport +=  current.replace("&#039;", "'");
                            }
                            else {
                                transport += current.replace("&#039;", "'");
                            }
                        }
                    }
                    if ((current.contains("tram")) || (current.contains("bus")) || (current.contains("parking"))){
                        isTransport = true;
                    }
                    if(!address.isEmpty()){
                        Center currentCenter;
                        LatLng latLng = getLatLng(address);
                        if(latLng == null){
                            /*
                            currentCenter = new Center(id, null, null,"", address, info, transport);
                            SaveData.insertIntoDatabase(coord_database,"COORDINATES VALUES ("+ id +","+ null +","+ null
                                    +","+ address +","+ info +","+ transport +")");
                                    */
                        }else{
                            currentCenter = new Center(id, latLng.latitude, latLng.longitude,"", address, info, transport);
                            SaveData.insertIntoDatabase(coord_database,"COORDINATES VALUES ("+ id +","+ latLng.latitude +","+ latLng.longitude
                                    +","+ address +","+ info +","+ transport +")");
                            centerList.add(currentCenter);
                            System.out.println(currentCenter.toString());
                        }
                        // TRYING TO INSERT DATA INTO DATABASE

                        address = "";
                        info = "";
                        transport = "";
                        id++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                try{
                    //use data from database if this doesn't work
                    // We print the data to check it
                    Cursor cursor = SaveData.fetchDatabase(coord_database,"SELECT * FROM COORDINATES");

                    if(cursor!= null){
                        if(cursor.getCount() > 0){
                            do{
                                //read
                                centerList.add(new Center(cursor.getInt(0), cursor.getDouble(0), cursor.getDouble(1),
                                        "",cursor.getString(0), cursor.getString(1), cursor.getString(2)));
                            }while(cursor.moveToNext());
                        }
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }finally {
                // Displaying addresses
                updateUI();
            }
        }
    }
    public LatLng getLatLng(String address){
        LatLng latLng = null;
        try{
            HttpDataHandler http = new HttpDataHandler();
            String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s",address);
            String data = "";
            int counter = 0;

            do{
                data = http.getHTTPData(url);
                counter ++;
            }while(data.isEmpty() || counter < 20);

            JSONObject jsonObject = new JSONObject();
            //System.out.println(jsonObject);

            String lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                    .getJSONObject("location").get("lat").toString();
            String lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                    .getJSONObject("location").get("lng").toString();

            latLng = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
            System.out.println("LAT AND SO ON LALALAL : " + latLng);

            }catch(Exception e){
            e.printStackTrace();
        }finally{
            return latLng;
        }
    }
    public void updateUI() {
        this.runOnUiThread(new Runnable() {
            public void run() {
                if(MethodsUtils.countValidAddresses(centerList) < ConstantValues.CENTER_LISY.size()){
                    centerList = ConstantValues.CENTER_LISY;
                }
                if(isMapReady){
                    mapFragment.placeMarkers(centerList);
                }
            }
        });
    }
}