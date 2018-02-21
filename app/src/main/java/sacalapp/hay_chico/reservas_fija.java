package sacalapp.hay_chico;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class reservas_fija extends AppCompatActivity {

    private String id_usuario,id_equipo,nombre_equipo_,id_reserva_fija,dia_semana,hora,cancha,nombre,logo,id_reserva_;
    private TextView lbl_dia,lbl_hora,lbl_cancha,lbl_nombre_canchas;
    ImageView imageView;
    ArrayList id_reserva =new ArrayList();
    ArrayList fecha_reservas =new ArrayList();
    ArrayList estado =new ArrayList();

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_reservas_fijas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_reservas_fijas);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notificaciones");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lbl_dia=(TextView)findViewById(R.id.lbl_dia_rival);
        lbl_hora=(TextView)findViewById(R.id.lbl_hora_rival);
        lbl_cancha=(TextView)findViewById(R.id.lbl_cancha_rival);
        lbl_nombre_canchas=(TextView)findViewById(R.id.lbl_nombre_cancha);

        imageView=(ImageView)findViewById(R.id.img_cancha);
        listView=(ListView) findViewById(R.id.lst_fechas);


        id_usuario=getIntent().getExtras().getString("id_jugador");
        id_equipo=getIntent().getExtras().getString("id_equipo");
        nombre_equipo_=getIntent().getExtras().getString("nombre_equipo");

        id_reserva_fija=getIntent().getExtras().getString("id_reserva_fija");
        dia_semana=getIntent().getExtras().getString("dia_semana");
        hora=getIntent().getExtras().getString("hora");
        cancha=getIntent().getExtras().getString("cancha");
        nombre=getIntent().getExtras().getString("nombre");
        logo=getIntent().getExtras().getString("logo");


        String urlfinal="http://sacalapp.com/jarvicf/canchas_img/"+logo;

        Glide.with(imageView.getContext())
                .load(urlfinal)
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.cancha_equipos)
                .signature(new StringSignature(urlfinal))
                .into(imageView);


        lbl_nombre_canchas.setText(nombre);
        lbl_cancha.setText(cancha);
        lbl_hora.setText(hora);
        lbl_dia.setText(dia_semana);

        tarer_canchas();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                id_reserva_=id_reserva.get(position).toString();
                createSimpleDialog();
            }
        });

}

    private void tarer_canchas() {

        id_reserva.clear();
        fecha_reservas.clear();
        estado.clear();


        final ProgressDialog progressDialog = new ProgressDialog(reservas_fija.this);
        progressDialog.setMessage("Cargardo Reservas...");
        progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/traer_reservas_fijas.php?id_reserva_fija=" + id_reserva_fija, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        if (jsonArray.length() == 0) {

                        } else {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                id_reserva.add(jsonArray.getJSONObject(i).getString("id_reserva"));
                                fecha_reservas.add(jsonArray.getJSONObject(i).getString("fecha_partido"));
                                estado.add(jsonArray.getJSONObject(i).getString("estado"));

                                listView.setAdapter(new reservas_fija.ImageAdater_canchas(getApplicationContext()));
                            }

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

    private class ImageAdater_canchas extends BaseAdapter {


        Context ctx;
        LayoutInflater layoutInflater;
        ImageView cancha;
        TextView fecha_rese,estado_reserva;



        public ImageAdater_canchas(Context applicationContext) {

            this.ctx = applicationContext;
            layoutInflater = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return id_reserva.size();
        }

        @Override
        public Object getItem(int position) {

            // Toast.makeText(Reservas.this, "Usuario "+position, Toast.LENGTH_SHORT).show();

            return position;
        }

        @Override
        public long getItemId(int position) {


            return position;

        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {

            ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.detalles_reserva_unica, null);



            cancha= (ImageView) viewGroup.findViewById(R.id.img_reservafija);
            fecha_rese = (TextView) viewGroup.findViewById(R.id.lbl_fecha_reserva);
            estado_reserva = (TextView) viewGroup.findViewById(R.id.lbl_estado_reserva);

            estado_reserva.setText("Por Confirmar");
            fecha_rese.setText(fecha_reservas.get(pos).toString());



            return viewGroup;
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public AlertDialog createSimpleDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Importante")
                .setMessage("¿Desea Asignar esta reserva a un partido?")
                .setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new reservas_fija.traer_nom_cancha().execute("http://www.sacalapp.com/actualizar_reserva_asig.php?id_reserva=" + id_reserva_+"&id_usuario=" + id_usuario+"&id_equipo="+id_equipo+"&reserva_fija="+id_reserva_fija);
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

        builder.show();
        return builder.create();
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

            Intent intent = new Intent(reservas_fija.this, Rivales.class);
            intent.putExtra("id_jugador", id_usuario);
            intent.putExtra("id_equipo", id_equipo);
            intent.putExtra("nombre_equipo", nombre_equipo_);
            intent.putExtra("id_reserva", id_reserva_);
            intent.putExtra("partido", "3");
            startActivity(intent);
            finish();

        }
    }

    private String downloadUrl(String myurl) throws IOException {
        Log.i("URL", "" + myurl);
        myurl = myurl.replace(" ", "%20");
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
        startService(new Intent(reservas_fija.this, Notificaciones.class));
        super.onPause();
    }

    @Override
    public void onStop() {

        // Toast.makeText(getApplicationContext(), "Start" ,Toast.LENGTH_LONG).show();
        startService(new Intent(reservas_fija.this, Notificaciones.class));

        super.onStop();
    }

    @Override
    public void onRestart(){

        //Toast.makeText(getApplicationContext(), "stop" ,Toast.LENGTH_LONG).show();
        stopService(new Intent(reservas_fija.this, Notificaciones.class));
        super.onRestart();


    }

}
