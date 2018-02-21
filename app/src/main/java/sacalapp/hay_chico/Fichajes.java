package sacalapp.hay_chico;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
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
import de.hdodenhof.circleimageview.CircleImageView;


import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;


public class Fichajes extends AppCompatActivity {

    private ListView listView;
    ArrayList posicion_datos=new ArrayList();
    ArrayList edad=new ArrayList();
    ArrayList ids=new ArrayList();
    ArrayList nick=new ArrayList();
    ArrayList imagen=new ArrayList();
    ArrayList Level=new ArrayList();

    ArrayList nombre_jug=new ArrayList();

    int adapter=0,camino=0;

    ArrayList id_equipo=new ArrayList();
    ArrayList id_equipo_2=new ArrayList();

    ArrayList latitud_user=new ArrayList();
    ArrayList longitud_user=new ArrayList();
    private String ruta,cargar,texto;
    private String nombre2,posicion2,edad2,nick2,ciudad2,sexo2,sexo_usuario,foto_user,id_jugador_notificacion,dato_equipo,dato_usuario,nombre_equipo_,Comparar,equipo_1,equipo_2,id_partido,logo,latitud_c,longitud_c,dis;
    String califica2,distancia_final,dato1,dato2;
    Double latitud_can,longitud_can, latitud_j, longitud_j,distancia;
    int dato_usu=0;
    ArrayList rating=new ArrayList();
    ArrayList id_usuarios=new ArrayList();
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3,floatingActionButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fichajes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setSupportActionBar(toolbar);
        Comparar=getIntent().getExtras().getString("dato");

        if (Comparar.equals("uno")){
            dato_equipo=getIntent().getExtras().getString("id_equipo");
            nombre_equipo_=getIntent().getExtras().getString("nombre_equipo");
            dato_usuario=getIntent().getExtras().getString("id_usuario");
            logo=getIntent().getExtras().getString("img");
            sexo_usuario=getIntent().getExtras().getString("sexo");
            new Fichajes.ConsultarDatosIds().execute("http://www.sacalapp.com/equipo_datos.php?dato=" + dato_equipo);
        }else if (Comparar.equals("Dos")){
            dato_equipo=getIntent().getExtras().getString("id_equipo");
            dato_usuario=getIntent().getExtras().getString("capitan");
            equipo_1=getIntent().getExtras().getString("id_equipo_1");
            equipo_2=getIntent().getExtras().getString("id_equipo_2");
            id_partido=getIntent().getExtras().getString("id_partido");
            sexo_usuario=getIntent().getExtras().getString("sexo");

           latitud_can=Double.parseDouble(latitud_c=getIntent().getExtras().getString("latitud_cancha"));
            longitud_can=Double.parseDouble(longitud_c=getIntent().getExtras().getString("longitud_cancha"));

            new Fichajes.ConsultarDatosIds().execute("http://www.sacalapp.com/equipo_datos.php?dato=" + dato_equipo);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.fab_menu);

        floatingActionButton1=(FloatingActionButton)findViewById(R.id.fab1);
        floatingActionButton2=(FloatingActionButton)findViewById(R.id.fab2);
        floatingActionButton3=(FloatingActionButton)findViewById(R.id.fab3);
        floatingActionButton4=(FloatingActionButton)findViewById(R.id.fab4);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cargar="Delantero";
                filtrar();
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cargar="Medio";
                filtrar();
            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cargar="Defensa";
                filtrar();
            }
        });
        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cargar="Portero";
                filtrar();
            }
        });
        camino=0;
        listView = (ListView) findViewById(R.id.lst_comtenido);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String enviar=(id_usuarios.get(position).toString());
                id_jugador_notificacion = enviar;
                dato1 = (id_equipo_2.get(position).toString());
                if (dato1.equals("libre")){
                    new Fichajes.ConsultarDatos().execute("http://www.sacalapp.com/datos_fichajes.php?email_usu="+enviar);
                }else {
                    Toast.makeText(Fichajes.this, "Jugador pertenece a tu equipo", Toast.LENGTH_SHORT).show();
                }


            }
        });


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                int btn_initPosY=materialDesignFAM.getScrollY();
                if (i == SCROLL_STATE_TOUCH_SCROLL) {
                    materialDesignFAM.animate().cancel();
                    materialDesignFAM.animate().translationYBy(350);

                } else {
                    materialDesignFAM.animate().cancel();
                    materialDesignFAM.animate().translationY(btn_initPosY);
                }

            }
            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });


    }

    private void filtrar(){

        ruta="http://sacalapp.com/fichajes_filtro.php?ruta="+cargar+"&id_jugador="+dato_usuario+"&sexo="+sexo_usuario;
        FiltarBusqueda();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fichajes, menu);
        menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //permite modificar el hint que el EditText muestra por defecto
        searchView.setQueryHint(getText(R.string.search2));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                    camino=1;
                     listView.setAdapter(null);
                    texto=newText;

                    Buscar_por_nombre();

                    return true;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Actualizar:
                descargarImagen();
                return true;
            case R.id.Volver:

                volver();

                return true;

            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void volver() {

        Intent intent = new Intent(Fichajes.this, Equipo.class);
        intent.putExtra("parametro", dato_equipo);
        intent.putExtra("id_usuario", dato_usuario);
        intent.putExtra("nombre_equipo", nombre_equipo_);
        startActivity(intent);
        finish();
    }

    public void Dialogo_info_usuario(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Fichajes.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fichajes_datos,null);

        builder.setView(dialogView);

        TextView nomb =(TextView)dialogView.findViewById(R.id.lbl_nombre_fichaje);
        TextView posicionn =(TextView)dialogView.findViewById(R.id.lbl_posi_fichajes);
        TextView nick_2 =(TextView)dialogView.findViewById(R.id.lbl_nick_fichajes);
        TextView edad_2 =(TextView)dialogView.findViewById(R.id.lbl_eda_fichajes);
        TextView ciudad_2 =(TextView)dialogView.findViewById(R.id.lbl_ciuidad_fichajes);
        RatingBar ratinCali_2 = (RatingBar)dialogView.findViewById(R.id.ratingBar_calificacion_fichajes);
        CircleImageView imagen_fichaje =(CircleImageView)dialogView.findViewById(R.id.ima_fichajes);
        AppCompatButton fic_inv = (AppCompatButton)dialogView.findViewById(R.id.fic_inv);
        AppCompatButton btn_Salir = (AppCompatButton)dialogView.findViewById(R.id.btn_Salir);


        nomb.setText(nombre2);
        posicionn.setText(posicion2);
        nick_2.setText(nick2);
        edad_2.setText(edad2+" Años");
        ciudad_2.setText(ciudad2);


        String urlfinal="http://sacalapp.com/jarvicf/img_users/"+foto_user.toString();

        Glide.with(imagen_fichaje.getContext())
                .load(urlfinal)
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.perfil)
                .signature(new StringSignature(urlfinal))
                .into(imagen_fichaje);


        Float cali_2=Float.parseFloat(califica2);
        ratinCali_2.setRating(cali_2);

            if (Comparar.equals("Uno")){
                fic_inv.setText("Fichar");
            }else  if (Comparar.equals("Dos")){
                fic_inv.setText("Invitar");
            }

        final AlertDialog dialog = builder.create();

        btn_Salir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        fic_inv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (Comparar.equals("uno")){
                    fichaje();
                    //Toast.makeText(getApplicationContext(), "equipo", Toast.LENGTH_LONG).show();
                   // new Fichajes.ConsultarDatos_ficheje().execute("http://www.sacalapp.com/fichaje_existente.php?id_jugador="+id_jugador_notificacion+"&id_equipo="+dato_equipo);
                    dialog.dismiss();
                }else  if (Comparar.equals("Dos")){
                    //Toast.makeText(getApplicationContext(), "partido", Toast.LENGTH_LONG).show();
                    new Fichajes.ConsultarDatos_ids().execute("http://www.sacalapp.com/ids_partido.php?id_partido="+id_partido);
                    dialog.dismiss();
                }

            }
        });
        dialog.show();
    }

    private void FiltarBusqueda() {

        listView.setAdapter(null);
        posicion_datos.clear();
        edad.clear();
        nick.clear();
        imagen.clear();
        rating.clear();
        id_usuarios.clear();
        latitud_user.clear();
        nombre_jug.clear();
        longitud_user.clear();
        id_equipo_2.clear();



        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get(ruta, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    materialDesignFAM.close(true);
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        adapter=jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++){

                            id_usuarios.add(jsonArray.getJSONObject(i).getString("usuario_id"));
                            nombre_jug.add(jsonArray.getJSONObject(i).getString("nombre"));
                            posicion_datos.add(jsonArray.getJSONObject(i).getString("posicion"));
                            edad.add(jsonArray.getJSONObject(i).getString("edad"));
                            imagen.add(jsonArray.getJSONObject(i).getString("foto"));
                            rating.add(jsonArray.getJSONObject(i).getString("calificacion"));
                            latitud_user.add(jsonArray.getJSONObject(i).getString("Latitud"));
                            longitud_user.add(jsonArray.getJSONObject(i).getString("Longitud"));
                            id_equipo_2.add(i,"libre");


                            id_equipo_2.add(i,"libre");
                            dato1 = (id_usuarios.get(i).toString());
                            for (int j=0;j<id_equipo.size();j++){
                                dato2 = (id_equipo.get(j).toString());

                                if (dato1.equals(dato2)){
                                    id_equipo_2.set(i,"equipo");
                                }
                            }

                            listView.setAdapter(new ImageAdater(getApplicationContext()));

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error 2", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
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


                nombre2=(ja.getString(0));
                posicion2=(ja.getString(1));
                nick2=(ja.getString(2));
                sexo2=(ja.getString(3));
                ciudad2=(ja.getString(4));
                edad2=(ja.getString(5));
                califica2=(ja.getString(6));
                foto_user=(ja.getString(7));


                Dialogo_info_usuario();
                //resto(null);

           /*     if (sexo.equals("Masculino")){

                    img_sexo.setImageResource(R.mipmap.masculino);
                }else if (sexo.equals("Femenino")){

                    img_sexo.setImageResource(R.mipmap.femenino);
                }*/



            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Fichajes.this, "Error_fichajes", Toast.LENGTH_LONG).show();

            }

        }
    }

    private class ConsultarDatosIds extends AsyncTask<String, Void, String> {
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

                for (int i=5;i<14;i++){
                    id_equipo.add(ja.getString(i).toString());
                }

                    descargarImagen();


            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(Fichajes.this, "Error", Toast.LENGTH_LONG).show();

            }

        }
    }

    private class ConsultarDatos_ids extends AsyncTask<String, Void, String> {
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
                String dee;
                int a=0;
                if (ja != null) {
                    for (int i=0;i<ja.length();i++){

                        dee = (ja.get(i).toString());

                        if (dee.equals(id_jugador_notificacion)){
                            Toast.makeText(Fichajes.this, "el jugados ya esta convocado para este partido", Toast.LENGTH_SHORT).show();
                            i=ja.length();
                        }else {
                         a=a+1;
                        }
                    }

                    if (a == ja.length()) {
                        fichaje2();

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Fichajes.this, "Error_fichajes", Toast.LENGTH_LONG).show();

            }

        }
    }

    private void fichaje() {

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://www.sacalapp.com/fichaje_existente.php?id_jugador="+id_jugador_notificacion+"&id_equipo="+dato_equipo, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        if (jsonArray.length()==0){
                            new Fichajes.CargarDato().execute("http://www.sacalapp.com/estado_notificacion.php?id_jugador="+id_jugador_notificacion+"&id_equipo="+dato_equipo);
                        }else {
                            Toast.makeText(Fichajes.this, "Ya le has enviado la solicitud al jugador", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error al traer jugadores", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void fichaje2() {

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://www.sacalapp.com/fichaje_invitacion.php?id_jugador="+id_jugador_notificacion+"&id_equipo2="+equipo_2+"&id_equipo="+dato_equipo, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        if (jsonArray.length()==0){
                            new Fichajes.CargarDato().execute("http://www.sacalapp.com/notificacion_partido_invi.php?id_equipo1="+equipo_1+"&id_equipo2="+equipo_2+"&id_jugador="+id_jugador_notificacion+"&id_partido="+id_partido);
                        }else {
                            Toast.makeText(Fichajes.this, "Ya le has enviado la invitación al jugador", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error al traer jugadores", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void descargarImagen() {

        posicion_datos.clear();
        edad.clear();
        nick.clear();
        imagen.clear();
        rating.clear();
        id_usuarios.clear();
        latitud_user.clear();
        nombre_jug.clear();
        longitud_user.clear();
        Level.clear();
        id_equipo_2.clear();


        final ProgressDialog progressDialog = new ProgressDialog(Fichajes.this);
        progressDialog.setMessage("Cargardo Jugadores");
        progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/fichajes_.php?id_jugador="+dato_usuario+"&sexo="+sexo_usuario, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        adapter=jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++) {

                            id_usuarios.add(jsonArray.getJSONObject(i).getString("usuario_id"));
                            nombre_jug.add(jsonArray.getJSONObject(i).getString("nombre"));
                            posicion_datos.add(jsonArray.getJSONObject(i).getString("posicion"));
                            edad.add(jsonArray.getJSONObject(i).getString("edad"));
                            imagen.add(jsonArray.getJSONObject(i).getString("foto"));
                            rating.add(jsonArray.getJSONObject(i).getString("calificacion"));
                            latitud_user.add(jsonArray.getJSONObject(i).getString("Latitud"));
                            longitud_user.add(jsonArray.getJSONObject(i).getString("Longitud"));
                            Level.add(jsonArray.getJSONObject(i).getString("level"));
                            id_equipo_2.add(i,"libre");
                            dato1 = (id_usuarios.get(i).toString());
                            for (int j=0;j<id_equipo.size();j++){
                                dato2 = (id_equipo.get(j).toString());
                                if (dato1.equals(dato2)){
                                    id_equipo_2.set(i,"equipo");
                                }
                            }


                            listView.setAdapter(new Fichajes.ImageAdater(getApplicationContext()));
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error al traer jugadores", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void Buscar_por_nombre() {


        posicion_datos.clear();
        edad.clear();
        nick.clear();
        imagen.clear();
        rating.clear();
        id_usuarios.clear();
        latitud_user.clear();
        nombre_jug.clear();
        longitud_user.clear();
        id_equipo_2.clear();



      /*  final ProgressDialog progressDialog = new ProgressDialog(Fichajes.this);
        progressDialog.setMessage("Cargardo Jugadores");
        progressDialog.show();*/

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/buscar_jugador.php?id_texto="+texto+"&id_usuario="+dato_usuario+"&sexo="+sexo_usuario, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                   // progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        adapter=jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++){

                                nick.add(jsonArray.getJSONObject(i).getString("nick"));
                                posicion_datos.add(jsonArray.getJSONObject(i).getString("posicion"));
                                edad.add(jsonArray.getJSONObject(i).getString("edad"));
                                nombre_jug.add(jsonArray.getJSONObject(i).getString("nombre"));
                                imagen.add(jsonArray.getJSONObject(i).getString("foto"));
                                rating.add(jsonArray.getJSONObject(i).getString("calificacion"));
                                id_usuarios.add(jsonArray.getJSONObject(i).getString("usuario_id"));
                            latitud_user.add(jsonArray.getJSONObject(i).getString("Latitud"));
                            longitud_user.add(jsonArray.getJSONObject(i).getString("Longitud"));
                                id_equipo_2.add(i,"libre");
                            dato1 = (id_usuarios.get(i).toString());
                            for (int j=0;j<id_equipo.size();j++){
                                dato2 = (id_equipo.get(j).toString());

                                if (dato1.equals(dato2)){
                                    id_equipo_2.set(i,"equipo");
                                }
                            }

                                listView.setAdapter(new ImageAdater(getApplicationContext()));
                            }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error al traer jugadores", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

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
            if (Comparar.equals("uno")){
                Toast.makeText(getApplicationContext(), "Hemos Enviado la solicitud al jugador", Toast.LENGTH_LONG).show();

            }else  if (Comparar.equals("Dos")){
                Toast.makeText(getApplicationContext(), "Hemos Enviado la invitacion al jugador", Toast.LENGTH_LONG).show();

            }

        }
    }

    private class ImageAdater extends BaseAdapter {


        RatingBar ratinCali;
        Context ctx;
        LayoutInflater layoutInflater;
        ImageView imd_d,lvl;
        CircleImageView smartImageView;
        TextView tposicion,tedad,tnombre;
        float cali;


        public ImageAdater(Context applicationContext){

            this.ctx=applicationContext;
            layoutInflater=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return adapter;
        }

        @Override
        public Object getItem(int position) {
            //Toast.makeText(Fichajes.this, "Usuario "+position, Toast.LENGTH_SHORT).show();
            return position;
        }

        @Override
        public long getItemId(int position) {
            //Toast.makeText(Fichajes.this, "Usuario selecionado"+position, Toast.LENGTH_SHORT).show();
            return position;

        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {

            ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.contenido_fichajes,null);

            imd_d =(ImageView) viewGroup.findViewById(R.id.img_equipo_deta);
            smartImageView =(CircleImageView)viewGroup.findViewById(R.id.imagenperfil);
            ratinCali = (RatingBar)viewGroup.findViewById(R.id.ratingBar_calificacion);
            lvl = (ImageView) viewGroup.findViewById(R.id.img_lvl2);

            ImageView smartImageView2 = (ImageView) viewGroup.findViewById(R.id.smartImageView);
            tnombre =(TextView) viewGroup.findViewById(R.id.lbl_nombre_ran);
            tposicion =(TextView) viewGroup.findViewById(R.id.lbl_posicion);
            tedad =(TextView)viewGroup.findViewById(R.id.lbl_eda);

                try {

                    String urlfinal="http://sacalapp.com/jarvicf/img_users/"+imagen.get(pos).toString();

                    Glide.with(smartImageView.getContext())
                            .load(urlfinal)
                            .centerCrop()
                            .dontAnimate()
                            .placeholder(R.drawable.perfil)
                            .signature(new StringSignature(urlfinal))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            //
                            // .skipMemoryCache(true)
                            .into(smartImageView);


                    cali=Float.parseFloat(rating.get(pos).toString());
                    //cali=Float.parseFloat(rating.get(pos).toString());
                    ratinCali.setRating(cali);

                    //tposicion.setText(posicion_datos.get(pos).toString());

                    String posi = (posicion_datos.get(pos).toString());
                    if (posi.equals("Delantero")) {
                        tposicion.setText("DEL");
                    } else if (posi.equals("Medio")) {
                        tposicion.setText("MED");
                    } else if (posi.equals("Defensa")) {
                        tposicion.setText("DEF");
                    } else if (posi.equals("Portero")) {
                        tposicion.setText("POR");
                    }

                    String Años = (edad.get(pos).toString());
                    tedad.setText(Años + " Años");
                    tnombre.setText(nombre_jug.get(pos).toString());

                    String le = (Level.get(pos).toString());
                    if (le.equals("Recreacion")){
                        lvl.setImageResource(R.drawable.ic_recreacion);
                    }else if (le.equals("Amateur")){
                        lvl.setImageResource(R.drawable.ic_amateur);
                    }else if (le.equals("SemiPro")){
                        lvl.setImageResource(R.drawable.ic_semipro);
                    }else if (le.equals("Pro")){
                        lvl.setImageResource(R.drawable.ic_pro);
                    }else if (le.equals("Leyenda")){
                        lvl.setImageResource(R.drawable.ic_leyenda);
                    }else if (le.equals("null")){
                        //lvl.setImageResource(R.drawable.ic_sin);
                    }

                    String nombre_="escudo_"+logo;
                    String recurso="drawable";
                    int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
                    imd_d.setImageResource(res_imagen);
                    //imd_d.setImageResource(R.drawable.escudo_1);

                    dato1 = (id_equipo_2.get(pos).toString());
                    if (dato1.equals("libre")){
                        imd_d.setVisibility(View.INVISIBLE);
                    }

                    if (Comparar.equals("Dos")){

                        latitud_j=Double.parseDouble(latitud_user.get(pos).toString());
                        longitud_j=Double.parseDouble(longitud_user.get(pos).toString());

                        if (latitud_j!=0 & longitud_j!=0){
                            double radioTierra = 6371;//en kilómetros
                            double dLat = Math.toRadians(latitud_j - latitud_can);
                            double dLng = Math.toRadians(longitud_j - longitud_can);
                            double sindLat = Math.sin(dLat / 2);
                            double sindLng = Math.sin(dLng / 2);
                            double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                                    * Math.cos(Math.toRadians(latitud_can)) * Math.cos(Math.toRadians(latitud_j));
                            double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
                            double distancia = radioTierra * va2;
                            dis=Double.toString(distancia);

                            if (distancia<2.5){
                                smartImageView2.setImageResource(R.drawable.ic_etapa_1);
                            }else if(distancia>2.5 || distancia<5.0) {
                                smartImageView2.setImageResource(R.drawable.ic_etapa_2);
                            }else{
                                smartImageView2.setImageResource(R.drawable.ic_etapa_3);
                            }


                        }
                    }



                }catch (Exception e){

                }

            return viewGroup;
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


}
