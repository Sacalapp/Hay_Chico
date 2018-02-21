package sacalapp.hay_chico;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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

import cz.msebera.android.httpclient.Header;

public class Notificacion_usuario extends AppCompatActivity {

    ArrayList id_equipo =new ArrayList();
    ArrayList logo_equipo =new ArrayList();
    ArrayList nombre_equipo =new ArrayList();
    ArrayList calificacion_equipo =new ArrayList();
    ArrayList id_notificacion =new ArrayList();
    ArrayList id_calificacion2 =new ArrayList();
    ArrayList id_calificacion =new ArrayList();
    ArrayList id_partido =new ArrayList();
    ArrayList estado =new ArrayList();
    ArrayList nombre_equipo2 =new ArrayList();
    ArrayList logo_equipo2 =new ArrayList();
    ArrayList id_equipo2 =new ArrayList();


    ArrayList id_partido_us =new ArrayList();
    ArrayList equipo_2_us =new ArrayList();
    ArrayList equipo_1_us=new ArrayList();

    String nombre_foto,recurso_foto;


    TextView lbl_negativo;
    ListView listView;
    float califica;
    private String id_usuario,logo_equipo_,nombre_equipo_,calificacion_equipo_,notificacion_id_,id_equipo_;
    private String jugador_2,jugador_3,jugador_4,jugador_5,jugador_6,jugador_7,jugador_8,jugador_9,jugador_10,jugador_11,jugador_12,campo,logo;

    int notificationID = 1;
    int tamaño_total,tamaño_1,tamaño_2,cero= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_noti_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_noti_usuario);
        setSupportActionBar(toolbar);
        //id_usuario=getIntent().getExtras().getString("id_jugador");

    bd();

        lbl_negativo=(TextView)findViewById(R.id.lbl_noti_user);

        getSupportActionBar().setTitle(" Tus notificaciones");


        listView=(ListView)findViewById(R.id.listView_noti_user);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (position<tamaño_1) {
                   id_equipo_ = (id_equipo.get(position).toString());
                    nombre_equipo_ = (nombre_equipo.get(position).toString());
                    logo_equipo_ = (logo_equipo.get(position).toString());
                    calificacion_equipo_ = (calificacion_equipo.get(position).toString());
                    notificacion_id_ = (id_notificacion.get(position).toString());
                    detalles_equipo();
                }else {


                    id_equipo_ = (nombre_equipo.get(position).toString());
                    nombre_equipo_ = (id_equipo.get(position).toString());
                    notificacion_id_ = (id_calificacion2.get(position).toString());

                    Intent intent = new Intent(Notificacion_usuario.this, calificacion_usuarios.class);
                    intent.putExtra("id_jugador", id_usuario);
                    intent.putExtra("id_partido", id_equipo_);
                    intent.putExtra("id_equipo", nombre_equipo_);
                    intent.putExtra("id_calificacion", notificacion_id_);
                    startActivity(intent);
                    finish();

                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(getIntent().getExtras().getInt("notificationID"));
    }

    private void bd()   {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "user", null, 1);

        SQLiteDatabase bd = admin.getWritableDatabase();

        //Cursor fila = bd.rawQuery("select nombre from usuario where codigo=" + dni, null);
        Cursor fila = bd.rawQuery("select  usuario_id from usuario where codigo="+cero, null);

        if (fila.moveToFirst()) {

            id_usuario=(fila.getString(0));

            traer_notificaciones();
        } else Toast.makeText(this, "No existe ningún usuario con ese dni", Toast.LENGTH_SHORT).show();
        bd.close();
    }

    public void detalles_equipo(){

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Notificacion_usuario.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.invitacio_usuario,null);

        builder.setView(dialogView);

        RatingBar calificacion_eq=(RatingBar)dialogView.findViewById(R.id.rating_cali_equi);

        TextView equipo =(TextView)dialogView.findViewById(R.id.lbl_nombre_equipo);
        TextView info =(TextView)dialogView.findViewById(R.id.lbl_infor);

        ImageView imagen_fichaje =(ImageView)dialogView.findViewById(R.id.smartImageView_equipo_2);

        AppCompatButton rechazar = (AppCompatButton)dialogView.findViewById(R.id.btn_rechazar);
        AppCompatButton volver = (AppCompatButton)dialogView.findViewById(R.id.btn_volver);
        AppCompatButton Firmar = (AppCompatButton)dialogView.findViewById(R.id.btn_aceptar);



        califica=Float.parseFloat(calificacion_equipo_.toString());
        //cali=
        calificacion_eq.setRating(califica);

        equipo.setText(nombre_equipo_);


        info.setText( " " +"Quiere invitarte a unirte a su equipo");


        nombre_foto="escudo_"+logo_equipo_;
        recurso_foto="drawable";
    int res_imagen = getResources().getIdentifier(nombre_foto, recurso_foto,getPackageName());
        imagen_fichaje.setImageResource(res_imagen);




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

                new Notificacion_usuario.CargarDato().execute("http://www.sacalapp.com/rechaza_usuario_equipo.php?id_notificacion="+notificacion_id_);
                //
                dialog.dismiss();
            }
        });

        Firmar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Notificacion_usuario.ConsultarDatos_jugador().execute("http://www.sacalapp.com/equipo_fichaje.php?id_equipo="+id_equipo_);
                dialog.dismiss();
            }

                    });


        dialog.show();
    }

    private class ConsultarDatos_jugador extends AsyncTask<String, Void, String> {
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


                jugador_2=(ja.getString(0));
                jugador_3=(ja.getString(1));
                jugador_4=(ja.getString(2));
                jugador_5=(ja.getString(3));
                jugador_6=(ja.getString(4));
                jugador_7=(ja.getString(5));
                jugador_8=(ja.getString(6));
                jugador_9=(ja.getString(7));
                jugador_7=(ja.getString(5));
                jugador_8=(ja.getString(6));
                jugador_9=(ja.getString(7));
                jugador_10=(ja.getString(8));
                jugador_11=(ja.getString(9));
                jugador_12=(ja.getString(10));

                if(jugador_2.equals("null")){
                    campo="jugador_2";
                }else  if(jugador_3.equals("null")){
                    campo="jugador_3";
                }else if(jugador_4.equals("null")){
                    campo="jugador_4";
                }else if(jugador_5.equals("null")){
                    campo="jugador_5";
                }else if(jugador_6.equals("null")){
                    campo="jugador_6";
                }else if(jugador_7.equals("null")){
                    campo="jugador_7";
                }else if(jugador_8.equals("null")){
                    campo="jugador_8";
                }else if(jugador_9.equals("null")){
                    campo="jugador_9";
                }else if(jugador_10.equals("null")){
                    campo="jugador_10";
                }else if(jugador_11.equals("null")){
                    campo="jugador_11";
                }else if(jugador_12.equals("null")){
                    campo="jugador_12";
                }else {
                    campo="campo";
                }

                if (campo.equals("campo")){

                    Toast.makeText(Notificacion_usuario.this, nombre_equipo+" No tiene más cupos", Toast.LENGTH_LONG).show();

                }else {
                    traer_partidos_equipo();

                }




            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Notificacion_usuario.this, "Error_fichajes", Toast.LENGTH_LONG).show();

            }

        }
    }

    private void traer_partidos_equipo() {

        id_partido_us.clear();
        equipo_1_us.clear();
        equipo_2_us.clear();


        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://www.sacalapp.com/fichajes_partido_jugador.php?id_equipo="+id_equipo_, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){


                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i=0;i<jsonArray.length();i++){

                            id_partido_us.add(jsonArray.getJSONObject(i).getString("id_partido"));
                            equipo_1_us.add(jsonArray.getJSONObject(i).getString("equipo_1"));
                            equipo_2_us.add(jsonArray.getJSONObject(i).getString("equipo_2"));
                        }

                        if(id_partido_us.size()==0){
                            new Notificacion_usuario.CargarDato().execute("http://www.sacalapp.com/acepta_usuario_equipo.php?id_notificacion="+notificacion_id_+"&campo="+campo+"&id_equipo="+id_equipo_+"&id_usuario="+id_usuario);
                            Toast.makeText(Notificacion_usuario.this, "Felicidades ha sido fichado por "+nombre_equipo_, Toast.LENGTH_LONG).show();
                        }else {

                            new Notificacion_usuario.CargarDato_3().execute("http://www.sacalapp.com/acepta_usuario_equipo.php?id_notificacion="+notificacion_id_+"&campo="+campo+"&id_equipo="+id_equipo_+"&id_usuario="+id_usuario);
                            Toast.makeText(Notificacion_usuario.this, "Felicidades ha sido fichado por "+nombre_equipo_, Toast.LENGTH_LONG).show();

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

    private void perfil() {

        Intent intent = new Intent(Notificacion_usuario.this, Perfil.class);
        startActivity(intent);
        finish();
    }

    private void traer_notificaciones() {

        id_equipo.clear();
        nombre_equipo.clear();
        logo_equipo.clear();
        calificacion_equipo.clear();
        id_notificacion.clear();
        id_calificacion2.clear();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/notificaion_para_usuario.php?id_usuario="+id_usuario, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        tamaño_1=jsonArray.length();

                        for (int i=0;i<jsonArray.length();i++){

                            id_equipo.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                            nombre_equipo.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                            logo_equipo.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                            calificacion_equipo.add(jsonArray.getJSONObject(i).getString("calificacion_equipo"));

                            id_notificacion.add(jsonArray.getJSONObject(i).getString("Notificacion_id"));
                            id_calificacion2.add(jsonArray.getJSONObject(i).getString("Notificacion_id"));
                        }

                       /* if (tamaño_1==0){
                             lbl_negativo.setVisibility(View.VISIBLE);

                        } else {

                            listView.setVisibility(View.VISIBLE);
                            llenar_list();
                        }*/

                        traer_notificaciones_calficacion();


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

    private void traer_notificaciones_calficacion() {

        id_calificacion.clear();
        id_partido.clear();
        estado.clear();
        nombre_equipo2.clear();
        logo_equipo2.clear();
        id_equipo2.clear();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/notificacion_calificacion.php?id_usuario="+id_usuario, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        tamaño_2=jsonArray.length();
                        tamaño_total=tamaño_1+tamaño_2;
                        for (int i=0;i<jsonArray.length();i++){

                            id_calificacion.add(jsonArray.getJSONObject(i).getString("id_calificacion"));
                            id_partido.add(jsonArray.getJSONObject(i).getString("id_partido"));
                            estado.add(jsonArray.getJSONObject(i).getString("estado"));
                            nombre_equipo2.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                            logo_equipo2.add(jsonArray.getJSONObject(i).getString("logo_equipo"));

                            id_equipo2.add(jsonArray.getJSONObject(i).getString("id_equipo"));

                        }


                        id_equipo.addAll(id_equipo2);
                        nombre_equipo.addAll(id_partido);
                        id_notificacion.addAll(estado);
                        logo_equipo.addAll(logo_equipo2);
                        calificacion_equipo.addAll(nombre_equipo2);
                        id_calificacion2.addAll(id_calificacion);




                        if (tamaño_total==0){
                            lbl_negativo.setVisibility(View.VISIBLE);

                        } else {

                            listView.setVisibility(View.VISIBLE);
                            llenar_list();
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

    private void llenar_list(){

        for (int i=0;i<tamaño_total;i++){

            listView.setAdapter(new Notificacion_usuario.ImageAdater(getApplicationContext()));
        }

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

            return tamaño_total;
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

             if (pos<tamaño_1) {

                tinfo.setText("Quiere ficharte");


                 nombre_foto="fichaje";
                 recurso_foto="drawable";

                tnombre.setText(nombre_equipo.get(pos).toString());

            }else {

                tinfo.setText("Califica a este equipo ");
                 nombre_foto="escudo_"+logo_equipo.get(pos).toString();
                 recurso_foto="drawable";

                tnombre.setText(calificacion_equipo.get(pos).toString());
            }


            int res_imagen = getResources().getIdentifier(nombre_foto, recurso_foto,getPackageName());
            smartImageView.setImageResource(res_imagen);


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
                traer_notificaciones();
                return true;

            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


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
            perfil();
        }
    }

    private class CargarDato_3 extends AsyncTask<String, Void, String> {
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

            for (int i=0;i<id_partido_us.size();i++){

                String eq1,eq2,par;
                eq1 = (equipo_1_us.get(i).toString());
                eq2 = (equipo_2_us.get(i).toString());
                par = (id_partido_us.get(i).toString());

                new Notificacion_usuario.CargarDato_4().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+eq1+"&id_equipo2="+eq2+"&id_jugador="+id_usuario+"&id_partido="+par);

            }

           perfil();
        }
    }

    private class CargarDato_4 extends AsyncTask<String, Void, String> {
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

    @Override
    public void onPause() {

        // Toast.makeText(getApplicationContext(), "start" ,Toast.LENGTH_LONG).show();
        startService(new Intent(Notificacion_usuario.this, Notificaciones.class));
        super.onPause();
    }

    @Override
    public void onStop() {

        // Toast.makeText(getApplicationContext(), "Start" ,Toast.LENGTH_LONG).show();
        startService(new Intent(Notificacion_usuario.this, Notificaciones.class));

        super.onStop();
    }

    @Override
    public void onRestart(){

        //Toast.makeText(getApplicationContext(), "stop" ,Toast.LENGTH_LONG).show();
        stopService(new Intent(Notificacion_usuario.this, Notificaciones.class));
        super.onRestart();


    }

}
