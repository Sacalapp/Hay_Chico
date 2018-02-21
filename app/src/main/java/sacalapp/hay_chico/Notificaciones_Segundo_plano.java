package sacalapp.hay_chico;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jarvicfelipe on 11/04/2017.
 */

public class Notificaciones_Segundo_plano extends Service {
    @Nullable

    public static final int segundo = 10;
    public static final int miliosegundo = segundo * 1000;

    ArrayList id_equipo =new ArrayList();
    ArrayList logo_equipo =new ArrayList();
    ArrayList nombre_equipo =new ArrayList();
    ArrayList calificacion_equipo =new ArrayList();
    ArrayList id_notificacion =new ArrayList();
    ArrayList estado_np =new ArrayList();
private String id_usuario,nombre_equipo_2,id_notificacion_2;
    int notificationID = 0;
    private int aa=0;
    ArrayList equipo_1 =new ArrayList();
    @Override
    public void onCreate() {

    }

    private void bd(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "user", null, 1);

        SQLiteDatabase bd = admin.getWritableDatabase();

        //Cursor fila = bd.rawQuery("select nombre from usuario where codigo=" + dni, null);
        Cursor fila = bd.rawQuery("select  usuario_id from usuario where codigo=" + 0, null);

        if (fila.moveToFirst()) {

            id_usuario=(fila.getString(0));


        } else Toast.makeText(this, "No existe ningún usuario con ese dni", Toast.LENGTH_SHORT).show();
        bd.close();
    }

    private void traer_notificaciones() {

        id_equipo.clear();
        nombre_equipo.clear();
        logo_equipo.clear();
        calificacion_equipo.clear();
        id_notificacion.clear();
        estado_np.clear();
        equipo_1.clear();





        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/notificacion_para usuario_noti.php?id_usuario="+id_usuario, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        for (int i=0;i<jsonArray.length();i++){

                            id_equipo.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                            nombre_equipo.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                            logo_equipo.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                            calificacion_equipo.add(jsonArray.getJSONObject(i).getString("calificacion_equipo"));
                            id_notificacion.add(jsonArray.getJSONObject(i).getString("Notificacion_id"));
                            estado_np.add(jsonArray.getJSONObject(i).getString("Notificacion_id"));
                            equipo_1.add(jsonArray.getJSONObject(i).getString("Notificacion_id"));

                               nombre_equipo_2=nombre_equipo.get(i).toString();
                               id_notificacion_2=id_notificacion.get(i).toString();
                            notificationID=notificationID+1;
                               displayNotification();

                        }

                        if(aa==0){
                            empezaranimacion();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error al traer datos", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    @Override
    public int onStartCommand(Intent intenc, int flags, int idArranque) {
        bd();
      empezaranimacion();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {


        Toast.makeText(getApplicationContext(), "Stop", Toast.LENGTH_SHORT).show();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    protected void displayNotification(){

        new Notificaciones_Segundo_plano.CargarDato().execute("http://www.sacalapp.com/actualizar_noti.php?id_notificacion="+id_notificacion_2);


        Intent i = new Intent(this, Notificacion_usuario.class);
        i.putExtra("id_jugador", id_usuario);

        i.putExtra("notificationID", notificationID);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        CharSequence ticker ="Un equipo Quiere Ficharte";
        CharSequence ticker2 ="Ir a Notificaciones";
        CharSequence contentTitle = nombre_equipo_2;
        CharSequence contentText = "Quiere contar con tus servicios!";
        Notification noti = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setTicker(ticker)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.ic_escudo)
                .addAction(R.drawable.ic_balon, ticker2, pendingIntent)
                .setVibrate(new long[] {100, 250, 100, 500})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build();
        nm.notify(notificationID, noti);

        empezaranimacion();
    }


    private class CargarDato extends AsyncTask<String, Void, String> {
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

        }


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

    public void empezaranimacion() {

        new CountDownTimer(miliosegundo, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {


            }

            @Override
            public void onFinish() {

                traer_notificaciones();
                Toast.makeText(getApplicationContext(), "Start", Toast.LENGTH_LONG).show();
            }
        }.start();
    }


}
