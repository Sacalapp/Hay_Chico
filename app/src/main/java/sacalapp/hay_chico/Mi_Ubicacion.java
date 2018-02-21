package sacalapp.hay_chico;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Mi_Ubicacion extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private static final String LOGTAG = "android-localizacion";

    private static final int PETICION_PERMISO_LOCALIZACION = 101;

    private GoogleApiClient apiClient;
    String dato_corde;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi__ubicacion);
         dato_corde=getIntent().getExtras().getString("id_usuario");





        apiClient = new GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
}



    @Override
    public void onConnectionFailed(ConnectionResult result) {
        //Se ha producido un error que no se puede resolver automáticamente
        //y la conexión con los Google Play Services no se ha establecido.

        //Log.e(LOGTAG, "Error grave al conectar con Google Play Services");
        Toast.makeText(getApplicationContext(), "Error grave al conectar con Google Play Services", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Conectado correctamente a Google Play Services

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {

            Location lastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(apiClient);

            updateUI(lastLocation);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Se ha interrumpido la conexión con Google Play Services

        Toast.makeText(getApplicationContext(), "Se ha interrumpido la conexión con Google Play Services", Toast.LENGTH_LONG).show();
        //Log.e(LOGTAG, "Se ha interrumpido la conexión con Google Play Services");
    }

    private void updateUI(Location loc) {
        if (loc != null) {



            String lactitud =  String.valueOf(loc.getLatitude());
            String longitud =  String.valueOf(loc.getLongitude());
            new Mi_Ubicacion.ActualizarDatos().execute("http://www.sacalapp.com/CDJ.php?lactitud=" + lactitud + "&longitud=" + longitud + "&usuario=" +dato_corde  );


        } else {

        }
    }

        @Override
        public void onRequestPermissionsResult ( int requestCode, String[] permissions,int[] grantResults){
            if (requestCode == PETICION_PERMISO_LOCALIZACION) {
                if (grantResults.length == 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //Permiso concedido

                    @SuppressWarnings("MissingPermission")
                    Location lastLocation =
                            LocationServices.FusedLocationApi.getLastLocation(apiClient);

                    updateUI(lastLocation);

                } else {
                    //Permiso denegado:
                    //Deberíamos deshabilitar toda la funcionalidad relativa a la localización.

                    //Log.e(LOGTAG, "Permiso denegado");
                    Toast.makeText(getApplicationContext(), "Permiso denegado", Toast.LENGTH_LONG).show();

                }
            }
        }


    private class ActualizarDatos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            //Toast.makeText(getApplicationContext(), "Ubicacion Actualizada", Toast.LENGTH_LONG).show();
            volver();


        }
    }

    private void volver() {
        Intent intent = new Intent(Mi_Ubicacion.this, Perfil.class);
        startActivity(intent);
        finish();
    }

    private String downloadUrl(String myurl) throws IOException {
        Log.i("URL",""+myurl);
        myurl = myurl.replace(" ","%20");
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("respuesta", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}

