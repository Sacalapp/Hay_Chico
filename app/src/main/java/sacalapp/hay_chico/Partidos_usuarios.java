package sacalapp.hay_chico;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
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
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class Partidos_usuarios extends AppCompatActivity {

    ArrayList hora_par =new ArrayList();
    ArrayList fecha_parti =new ArrayList();
    ArrayList lugar_par=new ArrayList();

    ArrayList id_equipo_par=new ArrayList();
    ArrayList logo_equipo_par=new ArrayList();
    ArrayList nombre_equipo_par=new ArrayList();
    ArrayList id_partido_par=new ArrayList();
    ArrayList id_notificacion_par =new ArrayList();
    ArrayList estado_np_par =new ArrayList();
    ArrayList equipo_1_par =new ArrayList();

    ArrayList id_equipo_eq=new ArrayList();
    ArrayList logo_equipo_eq=new ArrayList();
    ArrayList nombre_equipo_eq=new ArrayList();
    TextView lbl_negativo;
    private String id_partido,id_equipo_ju,id_noti_par,horaaa;
    private String equipo_uno_,equipo_dos_,logo_equipo_uno,logo_equipo_dos,id_equipo_uno,id_equipo_dos,dato_final,id_equi_1_par,fecha_,lugar_,id_usuario;
    private String jugador_2,jugador_3,jugador_4,jugador_5,jugador_6,jugador_7,jugador_8,jugador_9,campo;
    int tamaño_2,partido_cont=0,POSITION;

    ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_usu_par);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_usu_partido);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bd();
        lbl_negativo=(TextView)findViewById(R.id.lbl_user_par);

        listView=(ListView)findViewById(R.id.list_partidos_user);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    id_partido = (id_partido_par.get(position).toString());
                    id_equipo_ju = (id_equipo_par.get(position).toString());
                    id_noti_par = (id_notificacion_par.get(position).toString());
                    dato_final=(estado_np_par.get(position).toString());
                    horaaa=(hora_par.get(position).toString());
                    fecha_=(lugar_par.get(position).toString());
                    lugar_=(fecha_parti.get(position).toString());
                    POSITION=position;


                    new Partidos_usuarios.equipo1().execute("http://www.sacalapp.com/par_equipo_1.php?id_partido="+id_partido);


            }
        });

        getSupportActionBar().setTitle("Notificaciones");
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(getIntent().getExtras().getInt("notificationID2"));

    }

    private void bd()   {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "user", null, 1);

        SQLiteDatabase bd = admin.getWritableDatabase();

        //Cursor fila = bd.rawQuery("select nombre from usuario where codigo=" + dni, null);
        Cursor fila = bd.rawQuery("select  usuario_id from usuario where codigo=" + 0, null);

        if (fila.moveToFirst()) {

            id_usuario=(fila.getString(0));

            traer_notificaciones_partido_off();
        } else Toast.makeText(this, "No existe ningún usuario con ese dni", Toast.LENGTH_SHORT).show();
        bd.close();
    }

    private void traer_notificaciones_partido_off() {

        id_equipo_par.clear();
        nombre_equipo_par.clear();
        logo_equipo_par.clear();
        id_partido_par.clear();
        id_notificacion_par.clear();
        estado_np_par.clear();
        equipo_1_par.clear();

        hora_par.clear();
        lugar_par.clear();
        fecha_parti.clear();
        tamaño_2=0;




        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/notificacion_para_usuario_partido_off.php?id_usuario="+id_usuario, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        tamaño_2=jsonArray.length();

                            for (int i=0;i<jsonArray.length();i++){

                                id_equipo_par.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                                nombre_equipo_par.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                                logo_equipo_par.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                                id_partido_par.add(jsonArray.getJSONObject(i).getString("id_partido"));
                                id_notificacion_par.add(jsonArray.getJSONObject(i).getString("id_notificacion"));
                                estado_np_par.add(jsonArray.getJSONObject(i).getString("estado_NP"));
                                equipo_1_par.add(jsonArray.getJSONObject(i).getString("equipo_1"));
                                hora_par.add(jsonArray.getJSONObject(i).getString("hora_inicio"));
                                fecha_parti.add(jsonArray.getJSONObject(i).getString("fecha_partido_off"));
                                lugar_par.add(jsonArray.getJSONObject(i).getString("cancha_nombre"));

                            }

                            traer_notificaciones_partido();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void traer_notificaciones_partido() {

        final ProgressDialog progressDialog = new ProgressDialog(Partidos_usuarios.this);
        progressDialog.setMessage("Cargardo...");
        progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/notificacion_para_usuario_partido.php?id_usuario="+id_usuario, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        tamaño_2=tamaño_2+jsonArray.length();
                        if (tamaño_2==0){
                            lbl_negativo.setVisibility(View.VISIBLE);
                        } else {
                            listView.setVisibility(View.VISIBLE);
                            for (int i=0;i<jsonArray.length();i++){
                                id_equipo_par.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                                nombre_equipo_par.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                                logo_equipo_par.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                                id_partido_par.add(jsonArray.getJSONObject(i).getString("id_partido"));
                                id_notificacion_par.add(jsonArray.getJSONObject(i).getString("id_notificacion"));
                                estado_np_par.add(jsonArray.getJSONObject(i).getString("estado_NP"));
                                equipo_1_par.add(jsonArray.getJSONObject(i).getString("equipo_1"));
                                hora_par.add(jsonArray.getJSONObject(i).getString("hora_inicio"));
                                fecha_parti.add(jsonArray.getJSONObject(i).getString("fecha_partido"));
                                lugar_par.add(jsonArray.getJSONObject(i).getString("cancha_nombre"));
                            }
                        }
                        Llenar();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
    private void Llenar() {

        if (tamaño_2==0){
            lbl_negativo.setVisibility(View.VISIBLE);
        }else{
            for (int i=0;i<tamaño_2;i++){
                listView.setAdapter(new Partidos_usuarios.ImageAdater(getApplicationContext()));
            }
    }
    }

    private class equipo1 extends AsyncTask<String, Void, String> {
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

                id_equi_1_par=(ja.getString(0));

                Equipos();

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Partidos_usuarios.this, "Error", Toast.LENGTH_LONG).show();

            }

        }
    }

    private void Equipos() {

        id_equipo_eq.clear();
        nombre_equipo_eq.clear();
        logo_equipo_eq.clear();



        final ProgressDialog progressDialog = new ProgressDialog(Partidos_usuarios.this);
        progressDialog.setMessage("Cargardo...");
        progressDialog.show();

        final AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/traer_equipo_invitacion.php?id_partido="+id_partido, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        tamaño_2=jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++) {

                            id_equipo_eq.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                            nombre_equipo_eq.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                            logo_equipo_eq.add(jsonArray.getJSONObject(i).getString("logo_equipo"));

                            String aa = (id_equipo_eq.get(i).toString());
                            if (i == 0) {

                                if (aa.equals(id_equi_1_par)){

                                    id_equipo_uno = (id_equipo_eq.get(i).toString());
                                    equipo_uno_ = (nombre_equipo_eq.get(i).toString());
                                    logo_equipo_uno = (logo_equipo_eq.get(i).toString());
                                }else {
                                    id_equipo_dos = (id_equipo_eq.get(i).toString());
                                    equipo_dos_ = (nombre_equipo_eq.get(i).toString());
                                    logo_equipo_dos = (logo_equipo_eq.get(i).toString());
                                }


                            }else if (i == 1) {

                                if (aa.equals(id_equi_1_par)){

                                    id_equipo_uno = (id_equipo_eq.get(i).toString());
                                    equipo_uno_ = (nombre_equipo_eq.get(i).toString());
                                    logo_equipo_uno = (logo_equipo_eq.get(i).toString());
                                }else {
                                    id_equipo_dos = (id_equipo_eq.get(i).toString());
                                    equipo_dos_ = (nombre_equipo_eq.get(i).toString());
                                    logo_equipo_dos = (logo_equipo_eq.get(i).toString());
                                }

                            }
                        }



                        detalles_partido();


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void detalles_partido(){

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Partidos_usuarios.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.invitacion_partido,null);

        builder.setView(dialogView);


        TextView equipo_uno =(TextView)dialogView.findViewById(R.id.lbl_equipo_1);
        TextView equipo_dos =(TextView)dialogView.findViewById(R.id.lbl_equipo_2);
        TextView unfo =(TextView)dialogView.findViewById(R.id.lbl_infoo);
        TextView lugar =(TextView)dialogView.findViewById(R.id.lbl_lugar_d);
        TextView fecha =(TextView)dialogView.findViewById(R.id.lbl_fecha_d);
        TextView hora =(TextView)dialogView.findViewById(R.id.lbl_hora_d);

        ImageView imagen_equipo1 =(ImageView)dialogView.findViewById(R.id.equipo_uno);
        ImageView imagen_equipo2 =(ImageView)dialogView.findViewById(R.id.equipo_dos);

        AppCompatButton rechazar = (AppCompatButton)dialogView.findViewById(R.id.btn_rechazar);
        AppCompatButton volver = (AppCompatButton)dialogView.findViewById(R.id.btn_volver);
        AppCompatButton Firmar = (AppCompatButton)dialogView.findViewById(R.id.btn_confirmar);


        fecha.setText(fecha_);

        if (horaaa.equals("8:00")){
            hora.setText("800AM");
        }else  if (horaaa.equals("9:00")){
            hora.setText("9:00AM");
        }else if (horaaa.equals("10:00")){
            hora.setText("10:00AM");
        }else  if (horaaa.equals("11:00")){
            hora.setText("11:00AM");
        }else  if (horaaa.equals("12:00")){
            hora.setText("12:00PM");
        }else if (horaaa.equals("13:00")){
            hora.setText("1:00PM");
        }else  if (horaaa.equals("14:00")){
            hora.setText("2:00PM");
        }else  if (horaaa.equals("15:00")){
            hora.setText("3:00PM");
        }else  if (horaaa.equals("16:00")){
            hora.setText("4:00PM");
        }else  if (horaaa.equals("17:00")){
            hora.setText("5:00PM");
        }else  if (horaaa.equals("18:00")){
            hora.setText("6:00PM");
        }else  if (horaaa.equals("19:00")){
            hora.setText("7:00PM");
        }else  if (horaaa.equals("20:00")){
            hora.setText("8:00PM");
        }else  if (horaaa.equals("21:00")){
            hora.setText("9:00PM");
        }else  if (horaaa.equals("22:00")){
            hora.setText("10:00PM");
        }else  if (horaaa.equals("23:00")){
            hora.setText("11:00PM");
        }else  if (horaaa.equals("24:00")){
            hora.setText("12:00PM");
        }

        lugar.setText(lugar_);

        equipo_uno.setText(equipo_uno_);
        equipo_dos.setText(equipo_dos_);




        if (dato_final.equals("equipo")){
            if (id_equipo_ju.equals(id_equipo_uno)){
                unfo.setText( equipo_uno_ +" ha realizado un partido. Confirma tu asistencia a este!");
            }else {
                unfo.setText( equipo_dos_ +" ha realizado un partido. Confirma tu asistencia a este!");
            }

        }else if (dato_final.equals("invitacion")) {
            if (id_equipo_ju.equals(id_equipo_uno)){
                unfo.setText( equipo_uno_ +" ha realizado un partido. y quiere completar su equipo contigo");
            }else {
                unfo.setText( equipo_dos_ +" ha realizado un partido. y quiere completar su equipo contigo");
            }

        }




        String nombre_="escudo_"+logo_equipo_uno;
        String recurso="drawable";
        int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
        imagen_equipo1.setImageResource(res_imagen);


        String nombre="escudo_"+logo_equipo_dos;
        String recurso_="drawable";
        int res_imagen_ = getResources().getIdentifier(nombre, recurso_,getPackageName());
        imagen_equipo2.setImageResource(res_imagen_);

        final android.support.v7.app.AlertDialog dialog = builder.create();

        volver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        rechazar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new Partidos_usuarios.CargarDato_2().execute("http://www.sacalapp.com/rechaza_partido_equipo.php?id_notificacion="+id_noti_par);
                dialog.dismiss();
            }
        });

        Firmar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new Partidos_usuarios.ConsultarDatos_partido().execute("http://www.sacalapp.com/ids_partido.php?id_partido="+id_partido);
                dialog.dismiss();

            }

        });


        dialog.show();
    }

    private class ConsultarDatos_partido extends AsyncTask<String, Void, String> {
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

                int con=0;
                for (int i=0;i<ja.length();i++) {
                    String da=ja.get(i).toString();

                    if (id_usuario.equals(da)){
                        con=1;
                    }
                }




                if (con==0){
                    if (dato_final.equals("equipo")){

                        if(id_equipo_uno.equals(id_equipo_ju)){
                            new Partidos_usuarios.ConsultarDatos_equipo_1().execute("http://www.sacalapp.com/traer_equipos_partidos.php?id_equipo="+id_equipo_uno+"&id_partido="+id_partido);
                        }else{
                            new Partidos_usuarios.ConsultarDatos_equipo_2().execute("http://www.sacalapp.com/traer_equipos_partidos_2.php?id_equipo="+id_equipo_dos+"&id_partido="+id_partido);
                        }
                    }else  if (dato_final.equals("invitacion")){

                        if(id_equipo_uno.equals(id_equipo_ju)){
                            new Partidos_usuarios.ConsultarDatos_equipo_1().execute("http://www.sacalapp.com/traer_equipos_partidos.php?id_equipo="+id_equipo_uno+"&id_partido="+id_partido);
                        }else{
                            new Partidos_usuarios.ConsultarDatos_equipo_2().execute("http://www.sacalapp.com/traer_equipos_partidos_2.php?id_equipo="+id_equipo_dos+"&id_partido="+id_partido);
                        }
                    }
                }else {

                    new Partidos_usuarios.CargarDato_2().execute("http://www.sacalapp.com/rechaza_partido_equipo.php?id_notificacion="+id_noti_par);
                    Toast.makeText(Partidos_usuarios.this, "Ya estas convocado a este partido.", Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Partidos_usuarios.this, "Error_fichajes", Toast.LENGTH_LONG).show();

            }

        }
    }

    private class ConsultarDatos_equipo_1 extends AsyncTask<String, Void, String> {
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

                for (int i=0;i<ja.length();i++){
                    String ju=(ja.getString(i));

                    if (ju.equals(id_usuario)){

                        partido_cont=partido_cont+1;
                    }
                }

                if (partido_cont==0) {

                    jugador_2 = (ja.getString(0));
                    jugador_3 = (ja.getString(1));
                    jugador_4 = (ja.getString(2));
                    jugador_5 = (ja.getString(3));
                    jugador_6 = (ja.getString(4));
                    jugador_7 = (ja.getString(5));
                    jugador_8 = (ja.getString(6));
                    jugador_9 = (ja.getString(7));

                    if (jugador_2.equals("null")) {
                        campo = "jg_2";
                    } else if (jugador_3.equals("null")) {
                        campo = "jg_3";
                    } else if (jugador_4.equals("null")) {
                        campo = "jg_4";
                    } else if (jugador_5.equals("null")) {
                        campo = "jg_5";
                    } else if (jugador_6.equals("null")) {
                        campo = "jg_6";
                    } else if (jugador_7.equals("null")) {
                        campo = "jg_7";
                    } else if (jugador_8.equals("null")) {
                        campo = "jg_8";
                    } else if (jugador_9.equals("null")) {
                        campo = "jg_9";
                    } else {
                        campo = "lleno";
                    }

                    if (campo.equals("lleno")) {
                        Toast.makeText(Partidos_usuarios.this, "No hay cupo en este partido", Toast.LENGTH_LONG).show();

                    } else {
                        new Partidos_usuarios.CargarDato_2().execute("http://www.sacalapp.com/confirma_asistencia_partido.php?id_notificacion=" + id_noti_par + "&campo=" + campo + "&id_usuario=" + id_usuario + "&id_partido=" + id_partido);
                        Toast.makeText(Partidos_usuarios.this, "Has confirmado tu asistencia al partido", Toast.LENGTH_LONG).show();

                    }
                }else {
                    Toast.makeText(Partidos_usuarios.this, "Ys estas convocado a este partido", Toast.LENGTH_LONG).show();
                }




            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Partidos_usuarios.this, "Error 1", Toast.LENGTH_LONG).show();

            }

        }
    }

    private class ConsultarDatos_equipo_2 extends AsyncTask<String, Void, String> {
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

                for (int i=0;i<ja.length();i++){
                    String ju=(ja.getString(i));

                    if (ju.equals(id_usuario)){

                        partido_cont=partido_cont+1;
                    }
                }

                if (partido_cont==0) {

                    jugador_2 = (ja.getString(0));
                    jugador_3 = (ja.getString(1));
                    jugador_4 = (ja.getString(2));
                    jugador_5 = (ja.getString(3));
                    jugador_6 = (ja.getString(4));
                    jugador_7 = (ja.getString(5));
                    jugador_8 = (ja.getString(6));
                    jugador_9 = (ja.getString(7));

                    if (jugador_2.equals("null")) {
                        campo = "jg_11";
                    } else if (jugador_3.equals("null")) {
                        campo = "jg_12";
                    } else if (jugador_4.equals("null")) {
                        campo = "jg_13";
                    } else if (jugador_5.equals("null")) {
                        campo = "jg_14";
                    } else if (jugador_6.equals("null")) {
                        campo = "jg_15";
                    } else if (jugador_7.equals("null")) {
                        campo = "jg_16";
                    } else if (jugador_8.equals("null")) {
                        campo = "jg_17";
                    } else if (jugador_9.equals("null")) {
                        campo = "jg_18";
                    } else {
                        campo = "lleno";
                    }

                    if (campo.equals("lleno")) {
                        Toast.makeText(Partidos_usuarios.this, "No hay cupo en este partido", Toast.LENGTH_LONG).show();
                    } else {

                        new Partidos_usuarios.CargarDato_2().execute("http://www.sacalapp.com/confirma_asistencia_partido.php?id_notificacion=" + id_noti_par + "&campo=" + campo + "&id_usuario=" + id_usuario + "&id_partido=" + id_partido);
                        Toast.makeText(Partidos_usuarios.this, "Has confirmado tu asistencia al partido", Toast.LENGTH_LONG).show();
                        //traer_notificaciones();
                    }

                }else {
                    Toast.makeText(Partidos_usuarios.this, "Ys estas convocado a este partido", Toast.LENGTH_LONG).show();
                    new Partidos_usuarios.CargarDato_2().execute("http://www.sacalapp.com/actulizar_noti_par_use.php?id_notificacion=" + id_noti_par);

                }



            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Partidos_usuarios.this, "Error 2", Toast.LENGTH_LONG).show();

            }

        }
    }

    private class CargarDato_2 extends AsyncTask<String, Void, String> {
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

            //perfil();
            limpiar();

        }
    }

    private void limpiar() {
        id_equipo_par.remove(POSITION);
        nombre_equipo_par.remove(POSITION);
        logo_equipo_par.remove(POSITION);
        id_partido_par.remove(POSITION);
        id_notificacion_par.remove(POSITION);
        estado_np_par.remove(POSITION);
        equipo_1_par.remove(POSITION);

        hora_par.remove(POSITION);
        lugar_par.remove(POSITION);
        fecha_parti.remove(POSITION);
        tamaño_2=tamaño_2-1;
        Llenar();
    }

    private void perfil() {

        Intent intent = new Intent(Partidos_usuarios.this, Perfil.class);
        startActivity(intent);
        finish();
    }

    private class ImageAdater extends BaseAdapter {


        RatingBar ratinCali;
        Context ctx;
        LayoutInflater layoutInflater;
        ImageView smartImageView;
        TextView tnombre,tinfo;


        public ImageAdater(Context applicationContext){

            this.ctx=applicationContext;
            layoutInflater=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return id_partido_par.size();
        }

        @Override
        public Object getItem(int position) {

//            Toast.makeText(Fichajes.this, "Usuario "+position, Toast.LENGTH_SHORT).show();

            return position;
        }

        @Override
        public long getItemId(int position) {



            //Toast.makeText(Fichajes.this, "Usuario selecionado"+position, Toast.LENGTH_SHORT).show();
            return position;

        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {

            ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.equipo_solo_2,null);

            smartImageView =(ImageView)viewGroup.findViewById(R.id.img_equipo_perfil);

            tnombre =(TextView) viewGroup.findViewById(R.id.lbl_equipo_perfil);
            tinfo =(TextView) viewGroup.findViewById(R.id.lbl_apodo_equipo_perfil);

                String dato=(estado_np_par.get(pos).toString());
                if (dato.equals("equipo")){
                    tinfo.setText("Ha realizado un partido");
        }else if (dato.equals("invitacion")) {
            tinfo.setText("Quiere completar su equipo");
        }

        String logo=(logo_equipo_par.get(pos).toString());

        String nombre_="escudo_"+logo;
            String recurso="drawable";
            int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
            smartImageView.setImageResource(res_imagen);

            tnombre.setText(nombre_equipo_par.get(pos).toString());

            return viewGroup;
        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.comun, menu);
        menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Actualizar:

                return true;

            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
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

    private void addEventToCalendar(Partidos_usuarios activity){
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.DAY_OF_MONTH, 29);
        cal.set(Calendar.MONTH, 4);
        cal.set(Calendar.YEAR, 2013);

        cal.set(Calendar.HOUR_OF_DAY, 22);
        cal.set(Calendar.MINUTE, 45);

        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");

        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis()+60*60*1000);

        intent.putExtra(CalendarContract.Events.ALL_DAY, false);
        intent.putExtra(CalendarContract.Events.RRULE , "FREQ=DAILY");
        intent.putExtra(CalendarContract.Events.TITLE, "Título de vuestro evento");
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Descripción");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION,"Calle ....");

        activity.startActivity(intent);
    }

    @Override
    public void onPause() {

        // Toast.makeText(getApplicationContext(), "start" ,Toast.LENGTH_LONG).show();
        startService(new Intent(Partidos_usuarios.this, Notificaciones.class));
        super.onPause();
    }

    @Override
    public void onStop() {

        // Toast.makeText(getApplicationContext(), "Start" ,Toast.LENGTH_LONG).show();
        startService(new Intent(Partidos_usuarios.this, Notificaciones.class));

        super.onStop();
    }

    @Override
    public void onRestart(){

        //Toast.makeText(getApplicationContext(), "stop" ,Toast.LENGTH_LONG).show();
        stopService(new Intent(Partidos_usuarios.this, Notificaciones.class));
        super.onRestart();


    }

}
