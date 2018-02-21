package sacalapp.hay_chico;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.view.MenuItemCompat;

import com.github.snowdream.android.widget.SmartImageView;
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

public class Rivales extends AppCompatActivity {
    private ListView listView=null;
    private GridView grid;
    private String texto,nombre_equipo_2,logo_equipo_2,id_equipo_2,id_reserva_fija;
    ArrayList id_equipos=new ArrayList();
    ArrayList capi_equipo=new ArrayList();
    ArrayList nombre_equipo=new ArrayList();
    ArrayList calificacion_equipo=new ArrayList();
    ArrayList logo_equipo=new ArrayList();
    private String id_usuario,id_equipo, id_reserva, fecha_partido,hora_partido,id_cancha, nombre_cancha,nombre_equipo_,partido,id_capi,Ruta,eq1,eq2;
    ArrayList latitud=new ArrayList();
    ArrayList longitud=new ArrayList();
    int co=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rivales);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        id_usuario=getIntent().getExtras().getString("id_jugador");
        id_equipo=getIntent().getExtras().getString("id_equipo");
        nombre_equipo_=getIntent().getExtras().getString("nombre_equipo");
        partido=getIntent().getExtras().getString("partido");



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_rivales);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Equipos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //listView_rival = (ListView) findViewById(R.id.lst_comtenido_rival);
        grid=(GridView)findViewById(R.id.lbl_Grid);


        if (partido.equals("2")){
            id_reserva_fija=getIntent().getExtras().getString("id_reserva");
            new Rivales.traer_id_reserva().execute("http://www.sacalapp.com/id_reserva_rivales_off.php?id_reserva=" + id_reserva_fija);
        }if (partido.equals("3")){
            id_reserva_fija=getIntent().getExtras().getString("id_reserva");
            new Rivales.traer_id_reserva().execute("http://www.sacalapp.com/id_reserva_rivales_fija.php?id_reserva=" + id_reserva_fija);
        }

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  nombre_equipo_2=(nombre_equipo.get(position).toString());
                  logo_equipo_2=(logo_equipo.get(position).toString());
                  id_equipo_2=(id_equipos.get(position).toString());
                id_capi=(capi_equipo.get(position).toString());
                new Rivales.traer_nom_cancha().execute("http://www.sacalapp.com/traer_info_cancha.php?id_cancha=" + id_cancha);

            }
        });

        Traer();


        }

    public void detalles_equipo(){

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Rivales.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.invitacio_equipo_2,null);

        builder.setView(dialogView);

        TextView nomb_equipo =(TextView)dialogView.findViewById(R.id.lbl_nombre_equipo_rival);
        TextView info =(TextView)dialogView.findViewById(R.id.lbl_info_invitacion);
        TextView fecha =(TextView)dialogView.findViewById(R.id.lbl_fecha_par);
        TextView cancha =(TextView)dialogView.findViewById(R.id.lbl_nombre_cancha);
        TextView hora =(TextView)dialogView.findViewById(R.id.lbl_hora_par);
        SmartImageView imagen_fichaje =(SmartImageView)dialogView.findViewById(R.id.smartImageView_equipo);
        AppCompatButton invitar = (AppCompatButton)dialogView.findViewById(R.id.btn_invitar);
        AppCompatButton volver = (AppCompatButton)dialogView.findViewById(R.id.btn_volver);



        cancha.setText(nombre_cancha);
        fecha.setText(fecha_partido);
        nomb_equipo.setText(nombre_equipo_2);
        info.setText("Quieres invitar a"+" " +nombre_equipo_2+ " " +"a jugar con tu equipo?");


        String nombre_="escudo_"+logo_equipo_2;
        String recurso="drawable";
        int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
        imagen_fichaje.setImageResource(res_imagen);

        if (hora_partido.equals("8:00")){
            hora.setText("800AM");
        }else  if (hora_partido.equals("9:00")){
            hora.setText("9:00AM");
        }else if (hora_partido.equals("10:00")){
            hora.setText("10:00AM");
        }else  if (hora_partido.equals("11:00")){
            hora.setText("11:00AM");
        }else  if (hora_partido.equals("12:00")){
            hora.setText("12:00PM");
        }else if (hora_partido.equals("13:00")){
            hora.setText("1:00PM");
        }else  if (hora_partido.equals("14:00")){
            hora.setText("2:00PM");
        }else  if (hora_partido.equals("15:00")){
            hora.setText("3:00PM");
        }else  if (hora_partido.equals("16:00")){
            hora.setText("4:00PM");
        }else  if (hora_partido.equals("17:00")){
            hora.setText("5:00PM");
        }else  if (hora_partido.equals("18:00")){
            hora.setText("6:00PM");
        }else  if (hora_partido.equals("19:00")){
            hora.setText("7:00PM");
        }else  if (hora_partido.equals("20:00")){
            hora.setText("8:00PM");
        }else  if (hora_partido.equals("21:00")){
            hora.setText("9:00PM");
        }else  if (hora_partido.equals("22:00")){
            hora.setText("10:00PM");
        }else  if (hora_partido.equals("23:00")){
            hora.setText("11:00PM");
        }else  if (hora_partido.equals("24:00")){
            hora.setText("12:00PM");
        }

       /* String urlfinal="http://sacalapp.com/jarvicf/escudo_equipos/"+logo_equipo_2;
        Rect rect = new Rect(imagen_fichaje.getLeft(),imagen_fichaje.getTop(),imagen_fichaje.getRight(),imagen_fichaje.getBottom());
        imagen_fichaje.setImageUrl(urlfinal,rect);*/

        final android.support.v7.app.AlertDialog dialog = builder.create();

        volver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        invitar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (partido.equals("3")){
                    new Rivales.CargarDato().execute("http://www.sacalapp.com/notificacion_para_equipo.php?id_equipo_1="+id_equipo_2+"&id_equipo_2="+id_equipo+"&id_reserva="+id_reserva+"&id_capitan="+id_capi+"&partido="+partido);
                }else  if (partido.equals("2")){
                    new Rivales.CargarDato().execute("http://www.sacalapp.com/notificacion_para_equipo_off.php?id_equipo_1="+id_equipo_2+"&id_equipo_2="+id_equipo+"&id_reserva="+id_reserva+"&id_capitan="+id_capi+"&partido="+partido);
                }

                    Volver();
                
            }
        });
        dialog.show();
    }

    private void Volver() {

        Intent intent = new Intent(Rivales.this, Equipo.class);
        intent.putExtra("id_usuario", id_usuario);
        intent.putExtra("parametro", id_equipo);
        intent.putExtra("nombre_equipo", nombre_equipo_);
        startActivity(intent);
        finish();
    }

    private void Traer() {



        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/cantidad_equipos_jugador.php?id_usuario="+id_usuario, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    //progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        int cantida_de_equipo=jsonArray.length();

                            if (cantida_de_equipo==1){
                                Ruta="http://sacalapp.com/rivales.php?id_equipo="+id_equipo;
                            }else  if (cantida_de_equipo==2){
                                eq1=jsonArray.getJSONObject(0).getString("id_equipo");
                                eq2=jsonArray.getJSONObject(1).getString("id_equipo");

                                Ruta="http://sacalapp.com/rivales_2.php?id_equipo="+eq1+"&id_equipo2=" + eq2;
                            }

                        descargarImagen();



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

    private void descargarImagen() {

        grid.setAdapter(null);
        id_equipos.clear();
        nombre_equipo.clear();
        logo_equipo.clear();
        calificacion_equipo.clear();
        capi_equipo.clear();


        final ProgressDialog progressDialog = new ProgressDialog(Rivales.this);
        progressDialog.setMessage("Cargardo Equipos");
        progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get(Ruta, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        co=jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++){

                            id_equipos.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                            nombre_equipo.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                            calificacion_equipo.add(jsonArray.getJSONObject(i).getString("calificacion_equipo"));
                            logo_equipo.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                            capi_equipo.add(jsonArray.getJSONObject(i).getString("usuario_id"));


                            grid.setAdapter(new Rivales.ImageAdater(getApplicationContext()));

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error al caragr equipos", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void Busqueda_equipo() {


        id_equipos.clear();
        nombre_equipo.clear();
        logo_equipo.clear();
        calificacion_equipo.clear();
        capi_equipo.clear();



        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/buscar_rivales.php?busqueda="+texto+"&id_equipo="+id_equipo, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        co=jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++){

                            id_equipos.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                            nombre_equipo.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                            calificacion_equipo.add(jsonArray.getJSONObject(i).getString("calificacion_equipo"));
                            logo_equipo.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                            capi_equipo.add(jsonArray.getJSONObject(i).getString("usuario_id"));

                            grid.setAdapter(new Rivales.ImageAdater(getApplicationContext()));

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error al Buscar equipo", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rivales, menu);


        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //permite modificar el hint que el EditText muestra por defecto
        searchView.setQueryHint(getText(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                grid.setAdapter(null);
                texto=newText;
                Busqueda_equipo();
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            case R.id.Actualizar:
                descargarImagen();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class ImageAdater extends BaseAdapter {


        RatingBar ratinCali_rival;
        Context ctx;
        LayoutInflater layoutInflater;
        SmartImageView smartImageView;
        TextView tnombre;
        float cali;


        public ImageAdater(Context applicationContext){

            this.ctx=applicationContext;
            layoutInflater=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return co;
        }

        @Override
        public Object getItem(int position) {


            return position;
        }

        @Override
        public long getItemId(int position) {



            //Toast.makeText(Fichajes.this, "Usuario selecionado"+position, Toast.LENGTH_SHORT).show();
            return position;

        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {

            ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.revales_lista,null);



            smartImageView =(SmartImageView)viewGroup.findViewById(R.id.img_equipo_perfil);
            ratinCali_rival = (RatingBar)viewGroup.findViewById(R.id.calificacion_rival);


            tnombre =(TextView)viewGroup.findViewById(R.id.lbl_equipo_rival);


            String nombre_="escudo_"+logo_equipo.get(pos).toString();
            String recurso="drawable";
            int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
            smartImageView.setImageResource(res_imagen);


            /*String urlfinal="http://sacalapp.com/jarvicf/escudo_equipos/"+logo_equipo.get(pos).toString();
            Rect rect = new Rect(smartImageView.getLeft(),smartImageView.getTop(),smartImageView.getRight(),smartImageView.getBottom());
            smartImageView.setImageUrl(urlfinal,rect);*/


            cali=Float.parseFloat(calificacion_equipo.get(pos).toString());
            ratinCali_rival.setRating(cali);

            tnombre.setText(nombre_equipo.get(pos).toString());
            return viewGroup;
        }


    }

    private class traer_id_reserva extends AsyncTask<String, Void, String> {
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

                id_reserva=(ja.getString(0).toString());
                fecha_partido=(ja.getString(1).toString());
                hora_partido=(ja.getString(2).toString());
                id_cancha=(ja.getString(3).toString());

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Rivales.this, "No tienes ninguna reserva", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private class traer_nom_cancha extends AsyncTask<String, Void, String> {
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

                nombre_cancha=(ja.getString(0).toString());
                detalles_equipo();



            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Rivales.this, "Error", Toast.LENGTH_SHORT).show();

            }
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

            Toast.makeText(getApplicationContext(), "Hemos Enviado la solicitud a " +nombre_equipo_2, Toast.LENGTH_LONG).show();

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
        startService(new Intent(Rivales.this, Notificaciones.class));
        super.onPause();
    }

    @Override
    public void onStop() {

        // Toast.makeText(getApplicationContext(), "Start" ,Toast.LENGTH_LONG).show();
        startService(new Intent(Rivales.this, Notificaciones.class));

        super.onStop();
    }

    @Override
    public void onRestart(){

        //Toast.makeText(getApplicationContext(), "stop" ,Toast.LENGTH_LONG).show();
        stopService(new Intent(Rivales.this, Notificaciones.class));
        super.onRestart();


    }


}
