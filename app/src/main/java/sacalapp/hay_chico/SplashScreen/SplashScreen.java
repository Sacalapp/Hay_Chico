package sacalapp.hay_chico.SplashScreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import sacalapp.hay_chico.BuildConfig;
import sacalapp.hay_chico.R;
import sacalapp.hay_chico.SplashScreen.login.Login;

public class SplashScreen extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY = 1000;
    public static final int segundo = 4;
    public static final int miliosegundo = segundo * 1000;
    public static final int dalay = 2;
    private ProgressBar progressBar;
    private String fecha_ver,app_version,msj_ver,app_ver,msj,Ruta;

    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        info = (TextView) findViewById(R.id.lbl_info_internet);
        app_ver = BuildConfig.VERSION_NAME;
       // GifImageView gifImageView = (GifImageView) findViewById(R.id.gifImageView);
        progressBar = (ProgressBar) findViewById(R.id.progreso_inicio);
        progressBar.setMax(maximo_progreso());
        //Set GIFImageView resource
       /* try {
            InputStream inputStream = getAssets().open("balon.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
        } catch (IOException ex) {
            Exception e;
        }*/



        if (isWorkingInternetPersent()) {
            new SplashScreen.actualizr_version().execute("http://www.sacalapp.com/version.php");
        } else {
            showAlertDialog(SplashScreen.this, "Error",
                    "Revisa tu conexión a internet", false);
        }
    }

    public void empezaranimacion() {

        new CountDownTimer(miliosegundo, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                progressBar.setProgress(establecer_progreso(millisUntilFinished));
            }

            @Override
            public void onFinish() {

                Intent nuevofrom = new Intent(SplashScreen.this, Login.class);

                startActivity(nuevofrom);
                finish();
            }
        }.start();
    }

    public int establecer_progreso(long mili) {
        return (int) ((miliosegundo - mili) / 1000);
    }

    public int maximo_progreso() {
        return segundo - dalay;
    }

    public boolean isWorkingInternetPersent() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getBaseContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {

                        return true;
                    }

        }


        return false;
    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        // alertDialog.setIcon((status) ? R.mipmap.ic_launcher : R.mipmap.ic_launcher);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                finish();
                System.exit(0);
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }

    private class actualizr_version extends AsyncTask<String, Void, String> {
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

            JSONArray ja = null;
            try {
                ja = new JSONArray(result);

                app_version=ja.get(0).toString();
                fecha_ver=ja.get(1).toString();
                msj=ja.get(2).toString();
                Ruta=ja.get(3).toString();
                if (app_ver.equals(app_version)){
                    empezaranimacion();
                }else {
                    createSimpleDialog();
                }



            } catch (JSONException e) {
                e.printStackTrace();

            }

        }

    }

    public android.support.v7.app.AlertDialog createSimpleDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);

        builder.setTitle("Nueva Versión")
                .setMessage("Actualiza ¿Hay Chico?")
                .setPositiveButton("Actualizar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(Ruta));
                                startActivity(intent);
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (msj.equals("Obligatorio")) {
                                finish();
                                }else {
                                    empezaranimacion();
                                }


                            }
                        });

        builder.show();
        return builder.create();
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
