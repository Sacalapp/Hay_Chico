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

public class Notificaciones extends Service {
    @Nullable

    public static final int segundo = 60;
    public static final int miliosegundo = segundo * 1000;

    ArrayList codigo_notificacion =new ArrayList();
    ArrayList logo_equipo =new ArrayList();
    ArrayList codigo_jugador =new ArrayList();
    ArrayList id_equipo =new ArrayList();
    ArrayList id_equipo2 =new ArrayList();
    ArrayList id_notificacion =new ArrayList();
    ArrayList nombre_equipo =new ArrayList();

    ArrayList equipo_uno =new ArrayList();
    ArrayList equipo_dos =new ArrayList();

    private String id_usuario,nombre_equipo_2,id_notificacion_2,logo_equipo_,nombre_equipo_,id_equipo_,id_equipo_2,equipo_1,equipo_2,NOMBRE_EQUIPO,LOGO_EQUIPO;
    int notificationID1 = 0, notificationID2 = 0, notificationID4 = 0, notificationID5 = 0, notificationID6 = 0,notificationID7 = 0,notificationID8 = 0;
    private int aa=0,NOTY=0,bb=0;

    @Override
    public void onCreate() {

    }

    private void bd(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "user", null, 1);

        SQLiteDatabase bd = admin.getWritableDatabase();

        //Cursor fila = bd.rawQuery("select nombre from usuario where codigo=" + dni, null);
        Cursor fila = bd.rawQuery("select  usuario_id from usuario where codigo=" + bb, null);

        if (fila.moveToFirst()) {

            id_usuario=(fila.getString(0));

            empezaranimacion();
        } else //Toast.makeText(this, "No existe ningún usuario con ese dni", Toast.LENGTH_SHORT).show();
        bd.close();
    }

    private void traer_notificaciones() {

        id_notificacion.clear();
        codigo_notificacion.clear();
        codigo_jugador.clear();
        id_equipo.clear();
        nombre_equipo.clear();
        logo_equipo.clear();
        id_equipo2.clear();



        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/notificacion_para_usuario_noti.php?id_usuario="+id_usuario, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        aa=jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++){

                            id_notificacion.add(jsonArray.getJSONObject(i).getString("id_notificacion"));
                            codigo_notificacion.add(jsonArray.getJSONObject(i).getString("codigo_notificacion"));
                            codigo_jugador.add(jsonArray.getJSONObject(i).getString("codigo_jugador"));
                            id_equipo.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                            nombre_equipo.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                            logo_equipo.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                            id_equipo2.add(jsonArray.getJSONObject(i).getString("id_equipo_2"));

                            String dato=codigo_notificacion.get(i).toString();
                            logo_equipo_=logo_equipo.get(i).toString();
                            id_equipo_=id_equipo.get(i).toString();
                            id_equipo_2=id_equipo2.get(i).toString();
                            id_notificacion_2=id_notificacion.get(i).toString();
                            nombre_equipo_=nombre_equipo.get(i).toString();

                                if (dato.equals("1")){
                                    displayNotification_1();
                                }else if(dato.equals("2")){
                                    displayNotification_2();
                                }else  if (dato.equals("4")){
                                    disi();
                                }else if(dato.equals("5")){
                                    displayNotification_5();
                                }else if(dato.equals("6")){
                                    descargarreferencia();
                                }else if(dato.equals("7")){
                                    NOTY=1;
                                    new Notificaciones.ConsultarDatos().execute("http://www.sacalapp.com/equipo_datos_noti.php?dato=" + id_equipo_2);
                                }else if(dato.equals("8")){
                                    NOTY=2;
                                    new Notificaciones.ConsultarDatos().execute("http://www.sacalapp.com/equipo_datos_noti.php?dato=" + id_equipo_2);
                                }
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
        //Toast.makeText(getApplicationContext(), "Start" ,Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {


        //Toast.makeText(getApplicationContext(), "Stop", Toast.LENGTH_SHORT).show();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    protected void displayNotification_1(){

        new Notificaciones.CargarDato().execute("http://www.sacalapp.com/actualizar_noti.php?id_notificacion="+id_notificacion_2);



        Intent i = new Intent(this, Notificacion_usuario.class);
       // i.putExtra("id_jugador", id_usuario);
        i.putExtra("notificationID", notificationID1);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        String nombre_="escudo_"+logo_equipo_;
        String recurso="drawable";
        int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
        CharSequence ticker ="Un equipo Quiere Ficharte";
        CharSequence ticker2 ="Ir a Notificaciones";
        CharSequence contentTitle = nombre_equipo_;
        CharSequence contentText = "Quiere contar con tus servicios!";
        Notification noti = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setTicker(ticker)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(res_imagen)
                .addAction(R.drawable.ic_balon, ticker2, pendingIntent)
                .setVibrate(new long[] {100, 250, 100, 500})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build();
        nm.notify(notificationID1, noti);

        //bd();
        empezaranimacion();

    }

    protected void displayNotification_2(){

        new Notificaciones.CargarDato().execute("http://www.sacalapp.com/actualizar_noti.php?id_notificacion="+id_notificacion_2);


        Intent i = new Intent(this, Partidos_usuarios.class);

        i.putExtra("notificationID2", notificationID2);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        String nombre_="escudo_"+logo_equipo_;
        String recurso="drawable";
        int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
        CharSequence ticker ="Te han invitado a un partido";
        CharSequence ticker2 ="Ir a Notificaciones";
        CharSequence contentTitle = nombre_equipo_;
        CharSequence contentText = "Ha realizado un partido. confirma tu asistencia!!";
        Notification noti = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setTicker(ticker)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(res_imagen)
                .addAction(R.drawable.ic_balon, ticker2, pendingIntent)
                .setVibrate(new long[] {100, 250, 100, 500})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build();
        nm.notify(notificationID2, noti);

        //bd();
        empezaranimacion();
    }

    protected  void disi(){
        new Notificaciones.CargarDato2().execute("http://www.sacalapp.com/actualizar_noti_2.php?id_notificacion="+id_notificacion_2+"&id_equipo="+id_equipo_2);
    }

    protected void displayNotification_4(){

        Intent i = new Intent(this, Equipo.class);


        i.putExtra("id_usuario", id_usuario);
        i.putExtra("parametro", id_equipo_);
        i.putExtra("nombre_equipo", nombre_equipo_2);
        i.putExtra("notificationID4", notificationID4);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        String nombre_="escudo_"+logo_equipo_;
        String recurso="drawable";
        int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());

        CharSequence ticker ="Han retado a tu equipo";
        CharSequence ticker2 ="Ir a tu equipo";
        CharSequence contentTitle = nombre_equipo_;
        CharSequence contentText = "Quieres jugar contra"+" "+nombre_equipo_2+" "+".Aceptaras?";
        Notification noti = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setTicker(ticker)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(res_imagen)
                .addAction(R.drawable.ic_escudo, ticker2, pendingIntent)
                .setVibrate(new long[] {100, 250, 100, 500})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build();
        nm.notify(notificationID4, noti);

        empezaranimacion();
        //bd();
    }

    protected void displayNotification_5(){

        new Notificaciones.CargarDato().execute("http://www.sacalapp.com/actualizar_noti.php?id_notificacion="+id_notificacion_2);


        Intent i = new Intent(this, Partidos_usuarios.class);
        //i.putExtra("id_jugador", id_usuario);
        i.putExtra("notificationID2", notificationID2);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        String nombre_="escudo_"+logo_equipo_;
        String recurso="drawable";
        int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
        CharSequence ticker ="Te han invitado a un partido";
        CharSequence ticker2 ="Ir a Notificaciones";
        CharSequence contentTitle = nombre_equipo_;
        CharSequence contentText = "Le hacen falta jugadores. ¿Te Unirás?";
        Notification noti = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setTicker(ticker)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(res_imagen)
                .addAction(R.drawable.ic_balon, ticker2, pendingIntent)
                .setVibrate(new long[] {100, 250, 100, 500})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build();
        nm.notify(notificationID2, noti);

        empezaranimacion();
        // bd();
    }

    protected void displayNotification_6(){

        new Notificaciones.CargarDato().execute("http://www.sacalapp.com/actualizar_noti.php?id_notificacion="+id_notificacion_2);


        Intent i = new Intent(this, Perfil.class);
        //i.putExtra("id_jugador", id_usuario);
       // i.putExtra("notificationID6", notificationID6);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        CharSequence ticker ="Quieren Completar tu equipo";
        CharSequence ticker2 ="Ir a perfil";
        CharSequence contentTitle = equipo_uno.get(0).toString()+" "+"vs"+" "+equipo_dos.get(0).toString();
        CharSequence contentText = "Hay chico? para este jugador en tu equipo";
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
        nm.notify(notificationID6, noti);

        empezaranimacion();
        //bd();
    }

    protected void displayNotification_7(){

        new Notificaciones.CargarDato().execute("http://www.sacalapp.com/actualizar_noti.php?id_notificacion="+id_notificacion_2);

        Intent i = new Intent(this, Perfil.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        String nombre_="escudo_"+LOGO_EQUIPO;
        String recurso="drawable";
        int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
        CharSequence ticker ="Te han rechazado la invitación";
        CharSequence ticker2 ="Ir a Perfil";
        CharSequence contentTitle = NOMBRE_EQUIPO +" A rechazado la invitación";
        CharSequence contentText = nombre_equipo_+" Reta a otro equipo";
        Notification noti = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setTicker(ticker)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(res_imagen)
                .addAction(R.drawable.ic_balon, ticker2, pendingIntent)
                .setVibrate(new long[] {100, 250, 100, 500})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build();
        nm.notify(notificationID6, noti);

        empezaranimacion();
        //bd();
    }

    protected void displayNotification_8(){

        new Notificaciones.CargarDato().execute("http://www.sacalapp.com/actualizar_noti.php?id_notificacion="+id_notificacion_2);

        Intent i = new Intent(this, Perfil.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        String nombre_="escudo_"+LOGO_EQUIPO;
        String recurso="drawable";
        int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
        CharSequence ticker ="Han aceptado la invitación";
        CharSequence ticker2 ="Ir a Perfil";
        CharSequence contentTitle = NOMBRE_EQUIPO +" Aceptado la invitación a jugar";
        CharSequence contentText = "Tienes un partido";
        Notification noti = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setTicker(ticker)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(res_imagen)
                .addAction(R.drawable.ic_balon, ticker2, pendingIntent)
                .setVibrate(new long[] {100, 250, 100, 500})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build();
        nm.notify(notificationID6, noti);

        empezaranimacion();
        //bd();
    }

    private void descargarreferencia() {

        equipo_uno.clear();
        equipo_dos.clear();


        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://www.sacalapp.com/actualizar_noti_3.php?id_notificacion="+id_notificacion_2+"&id_partido="+id_equipo_2, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i = 0; i < jsonArray.length(); i++) {

                            if (i==0){
                                equipo_uno.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                            }else if(i==1){
                                equipo_dos.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                            }
                        }
                        displayNotification_6();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error 2", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "Revisa tu conexión a internet", Toast.LENGTH_LONG).show();

            }
        });
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

    private class CargarDato2 extends AsyncTask<String, Void, String> {
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

                nombre_equipo_2=ja.get(0).toString();

                displayNotification_4();

            } catch (JSONException e) {
                e.printStackTrace();

            }

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
                //Toast.makeText(getApplicationContext(), "Start", Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    private class ConsultarDatos extends AsyncTask<String, Void, String> {
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

                NOMBRE_EQUIPO=(ja.getString(0).toString());
                LOGO_EQUIPO=(ja.getString(1).toString());

                if (NOTY==1) {
                    displayNotification_7();
                }else   if (NOTY==2) {
                    displayNotification_8();
                }



            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Notificaciones.this, "Error", Toast.LENGTH_LONG).show();

            }

        }
    }


}
