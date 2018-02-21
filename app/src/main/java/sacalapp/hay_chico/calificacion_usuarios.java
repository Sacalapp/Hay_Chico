package sacalapp.hay_chico;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
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
import de.hdodenhof.circleimageview.CircleImageView;

public class calificacion_usuarios extends AppCompatActivity {

    private String id_jugador,id_partido,id_equipo,id_calificacion,id_equi_1,id_equi_2,nombre_ju,logo_ju,calificacion_ju,partidos_ju,ruta;

    ArrayList id_jugador_equipo=new ArrayList();
    ArrayList nombre_jugador_equipo=new ArrayList();
    ArrayList posicion_jugador_equipo=new ArrayList();
    ArrayList logo_jugador=new ArrayList();
    ArrayList calificacion_usu=new ArrayList();
    ArrayList partidos_jugados=new ArrayList();

    ListView listView;
    ViewGroup viewGroup;
    int posi=0,partidos=0,comp=0;
    float calificacion=0,cali=0,calificacion_final;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_calificar_users);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cali_usu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Califica a");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        id_jugador=getIntent().getExtras().getString("id_jugador");
        id_partido=getIntent().getExtras().getString("id_partido");
        id_equipo=getIntent().getExtras().getString("id_equipo");
        id_calificacion=getIntent().getExtras().getString("id_calificacion");

        listView=(ListView)findViewById(R.id.list_usuarios);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                posi=position;
                nombre_ju=nombre_jugador_equipo.get(position).toString();
                logo_ju=logo_jugador.get(position).toString();
                id_jugador=id_jugador_equipo.get(position).toString();
                partidos = Integer.parseInt(partidos_ju=partidos_jugados.get(position).toString());

                cali=Float.parseFloat(calificacion_ju=calificacion_usu.get(position).toString());
                Dialogo_usuario();

            }
        });

        new calificacion_usuarios.ConsultarDatos_ids().execute("http://www.sacalapp.com/ids_partidos.php?id_partido=" + id_partido);


    }

    public void Dialogo_usuario(){

        AlertDialog.Builder builder = new AlertDialog.Builder(calificacion_usuarios.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.usuarios_calificacion,null);

        builder.setView(dialogView);

        TextView nomb =(TextView)dialogView.findViewById(R.id.lbl_nombre_fichaje);
         final RatingBar ratinCali_2 = (RatingBar)dialogView.findViewById(R.id.ratingBar);
        CircleImageView  imagen_fichaje =(CircleImageView)dialogView.findViewById(R.id.ima_fichajes);
        Button one = (Button) dialogView.findViewById(R.id.btn_calificar);

        nomb.setText(nombre_ju);


        ratinCali_2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            public void onRatingChanged(RatingBar ratingBar, float rating,  boolean fromUser) {


                calificacion=ratinCali_2.getRating();
            }

        });


        String urlfinal="http://sacalapp.com/jarvicf/img_users/"+logo_ju.toString();

        Glide.with(imagen_fichaje.getContext())
                .load(urlfinal)
                .crossFade()
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.perfil)
                .signature(new StringSignature(urlfinal))
                .into(imagen_fichaje);


        one.setText("Calificar");

        final AlertDialog dialog = builder.create();

        one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                comp=comp+1;

                partidos=partidos+1;
                cali=cali+calificacion;
                calificacion_final=cali/partidos;

                new calificacion_usuarios.CargarDato().execute("http://www.sacalapp.com/actualizar_calificacion.php?id_usuario=" + id_jugador+"&partidos="+partidos+"&calificacion="+calificacion_final+"&notificacion="+id_calificacion);


                id_jugador_equipo.remove(posi);
                    nombre_jugador_equipo.remove(posi);
                    posicion_jugador_equipo.remove(posi);
                    logo_jugador.remove(posi);
                    partidos_jugados.remove(posi);
                    calificacion_usu.remove(posi);

                    datos();

                dialog.dismiss();
            }
        });



        dialog.show();
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
                id_equi_1=(ja.getString(19));
                id_equi_2=(ja.getString(20));

                if (id_equipo.equals(id_equi_1)){
                    ruta="http://sacalapp.com/traer_usuarios_calificacion.php?id_equipo="+id_equi_1+"&id_partido="+id_partido;
                    TraerEquipo();
                }else if (id_equipo.equals(id_equi_2)){
                    ruta="http://sacalapp.com/traer_usuarios_calificacion_2.php?id_equipo="+id_equi_2+"&id_partido="+id_partido;
                    TraerEquipo();
                }



            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(calificacion_usuarios.this, "Error_fichajes", Toast.LENGTH_LONG).show();

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

            if (comp==1){
                new calificacion_usuarios.CargarDato().execute("http://www.sacalapp.com/actualizr_estado_calificacion.php?id_calificacion="+id_calificacion);
                comp=comp+1;
            }


        }
    }


    private void TraerEquipo() {

        id_jugador_equipo.clear();
        nombre_jugador_equipo.clear();
        posicion_jugador_equipo.clear();
        logo_jugador.clear();
        calificacion_usu.clear();
        partidos_jugados.clear();

        final ProgressDialog progressDialog = new ProgressDialog(calificacion_usuarios.this);
        progressDialog.setMessage("Cargardo Tu Equipo");
        progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get(ruta, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        for (int i = 0; i < jsonArray.length(); i++) {

                            id_jugador_equipo.add(jsonArray.getJSONObject(i).getString("usuario_id"));
                            nombre_jugador_equipo.add(jsonArray.getJSONObject(i).getString("nombre"));
                            posicion_jugador_equipo.add(jsonArray.getJSONObject(i).getString("posicion"));
                            logo_jugador.add(jsonArray.getJSONObject(i).getString("foto"));
                            calificacion_usu.add(jsonArray.getJSONObject(i).getString("calificacion"));
                            partidos_jugados.add(jsonArray.getJSONObject(i).getString("partidos_jugados"));


                        }
                        datos();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }



    private void datos() {


        int da=id_calificacion.length();

        for (int i = 0; i < da; i++) {

            listView.setAdapter(new calificacion_usuarios.ImageAdater(getApplicationContext()));

        }

    }

    private class ImageAdater extends BaseAdapter {



        Context ctx;
        LayoutInflater layoutInflater;
        SmartImageView go,img_capi;
        CircleImageView smartImageView ;
        Button calificar;


        TextView tposicion,tnombre;


        public ImageAdater(Context applicationContext){

            this.ctx=applicationContext;
            layoutInflater=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return id_jugador_equipo.size();
        }

        @Override
        public Object getItem(int position) {



            Toast.makeText(calificacion_usuarios.this, "Usuario "+position, Toast.LENGTH_SHORT).show();

            return position;
        }

        @Override
        public long getItemId(int position) {

            //Toast.makeText(calificacion_usuarios.this, "Usuario "+position, Toast.LENGTH_SHORT).show();
            return position;

        }

        @Override
        public View getView(final int pos, View convertView, ViewGroup parent) {

            viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.jugador_califica,null);


            smartImageView =(CircleImageView) viewGroup.findViewById(R.id.img_jugador_equipo);
            go =(SmartImageView)viewGroup.findViewById(R.id.smartImageView_adelante);


            String URL_2="null";
            URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jugador.get(pos).toString();


            Glide.with(smartImageView.getContext())
                    .load(URL_2)
                    .signature(new StringSignature(URL_2))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.drawable.perfil)
                    //.skipMemoryCache(true)
                    .into(smartImageView);

            tnombre =(TextView)viewGroup.findViewById(R.id.lbl_nombre_jugador_equipo);
            tposicion =(TextView) viewGroup.findViewById(R.id.lbl_posicion_jugador_equipo);

            tnombre.setText(nombre_jugador_equipo.get(pos).toString());





            String posi = (posicion_jugador_equipo.get(pos).toString());
            if (posi.equals("Delantero")) {
                tposicion.setText("DEL");
            } else if (posi.equals("MEdio")) {
                tposicion.setText("MED");
            } else if (posi.equals("Defensa")) {
                tposicion.setText("DEF");
            } else if (posi.equals("Portero")) {
                tposicion.setText("POR");
            }




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
}
