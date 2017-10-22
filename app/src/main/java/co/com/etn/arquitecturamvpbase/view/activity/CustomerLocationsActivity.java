package co.com.etn.arquitecturamvpbase.view.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.model.Customer;
import co.com.etn.arquitecturamvpbase.model.PhoneList;

public class CustomerLocationsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "CustomerLocations";
    private GoogleMap mMap;
    int[] COLORS = {
            R.color.colorPrimary,
            R.color.colorAccent,
            R.color.colorPrimaryDark,
            R.color.colorPrimaryDisabled
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkPlayServices()){
            setContentView(R.layout.activity_customer_locations);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } else {
            //TODO
        }
    }


    private boolean checkPlayServices() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(result)) {
                googleApiAvailability.getErrorDialog(this, result, 9000).show();
            }
            return false;
        }
        return true;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getCustomerLocations();

//        createMarkers(coordinatesInit);

        changeStateControls();

    }

    private void createMarkers(Double[] coordinatesInit) {
        LatLng initPoint = new LatLng(coordinatesInit[0], coordinatesInit[1]);

        mMap.addMarker(new MarkerOptions().position(initPoint).title("Marker in Home").
                icon(bitmapDescriptorFromVector(this, R.drawable.ic_local_bar_black_24dp)));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(initPoint, 18));

    }

    private void changeStateControls() {
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
    }





    RoutingListener routingListener = new RoutingListener() {
        @Override
        public void onRoutingFailure(RouteException e) {

            Log.e(TAG, e.getMessage());
        }

        @Override
        public void onRoutingStart() {
            Log.i(TAG, "Iniciando Ruta");
        }

        @Override
        public void onRoutingSuccess(ArrayList<Route> routes, int shortestRouteIndex) {

            ArrayList<Polyline> polyLines = new ArrayList<>();

            int indexColor = COLORS.length;
            for (Route route : routes) {
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.color(ContextCompat.getColor(getApplicationContext(), COLORS[indexColor++%COLORS.length]));
                polylineOptions.width(10);
                polylineOptions.addAll(route.getPoints());

                Polyline polylineTemp = mMap.addPolyline(polylineOptions);
                polyLines.add(polylineTemp);

                int distance = route.getDistanceValue();
                int duration = route.getDurationValue();

                Toast.makeText(CustomerLocationsActivity.this, "distance: " + distance + " - Duration: " + duration, Toast.LENGTH_LONG).show();

            }

        }

        @Override
        public void onRoutingCancelled() {
            Log.w(TAG, "Ruta Cancelada");
        }
    };

    private void calculateRoute(LatLng myHome, LatLng myOffice) {
        ArrayList<LatLng> points = new ArrayList<>();
        points.add(myHome);
        points.add(myOffice);

        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .waypoints(points)
                .key(getString(R.string.google_maps_key))
                .optimize(true).alternativeRoutes(true)
                .withListener(routingListener)
                .build()

                ;
        routing.execute();

    }



    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int idRes) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, idRes);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public void getCustomerLocations() {
        if (getIntent().getExtras() != null) {
            Customer customer = (Customer) getIntent().getExtras().getSerializable(Customer.class.getName());
            if (customer != null) {
                ArrayList<LatLng> points = createMarkers(customer);
                calculateRoutes(points);
                centerJustPoints(points);
            }
        }
    }

    private ArrayList<LatLng> createMarkers(Customer customer) {
        ArrayList<PhoneList> phoneList = customer.getPhoneList();
        ArrayList<LatLng> points = new ArrayList<>();
        for (PhoneList phone : phoneList) {
            Double[] coordinates = phone.getLocation().getCoordinates();
            LatLng latLng = new LatLng(coordinates[0], coordinates[1]);
            mMap.addMarker(new MarkerOptions()
                    .position(latLng).title("Marker in Home")
                    .icon(bitmapDescriptorFromVector(this, R.drawable.ic_local_bar_black_24dp)));
            points.add(latLng);
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(points.get(0), 18));
        return points;

    }

    private void calculateRoutes(Customer customer) {
        ArrayList<PhoneList> phoneList = customer.getPhoneList();
        ArrayList<LatLng> points = new ArrayList<>();
        for (PhoneList phone : phoneList) {
            Double[] coordinates = phone.getLocation().getCoordinates();
            points.add(new LatLng(coordinates[0], coordinates[1]));
        }
        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .waypoints(points)
                .key(getString(R.string.google_maps_key))
                .optimize(true)
                .withListener(routingListener)
                .build()

                ;
        routing.execute();

        centerJustPoints(points);

    }

    private void calculateRoutes(ArrayList<LatLng> points) {
        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .waypoints(points)
                .key(getString(R.string.google_maps_key))
                .optimize(true).alternativeRoutes(true)
                .withListener(routingListener)
                .build()
                ;
        routing.execute();



    }

    private void centerJustPoints(ArrayList<LatLng> points) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng point : points) {
            builder.include(point);
        }

        LatLngBounds bounds = builder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 50); //50 padding
        mMap.animateCamera(cameraUpdate);
    }

}
