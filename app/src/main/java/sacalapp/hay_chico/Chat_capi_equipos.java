package sacalapp.hay_chico;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
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
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
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
import de.hdodenhof.circleimageview.CircleImageView;

public class Chat_capi_equipos extends AppCompatActivity {

     private String id_equipo,id_partido,id_usuario,msn,dato_comparar;
    private Menu mymenu;
    EditText Mensaje;
    ImageView img_enviar;
    ViewGroup viewGroup;
    ArrayList id_mensaje= new ArrayList();
    ArrayList id_usuario_= new ArrayList();
    ArrayList nombre_equipos= new ArrayList();
    ArrayList logo_equipos= new ArrayList();
    ArrayList cuerpo_mensaje= new ArrayList();
    ArrayList hora_mensaje= new ArrayList();
    ArrayList fecha_mensaje= new ArrayList();
    ArrayList id_dato= new ArrayList();
    TextView textView;
    ListView lv2Refresh;
    FloatingActionButton chat_cpi;
    private TextInputLayout tilMensaje;

    public static final int segundo = 10;
    public static final int miliosegundo = segundo * 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_chat_capitanes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_capi_chat);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        chat_cpi = (FloatingActionButton) findViewById(R.id.chat_capitanes);
        id_equipo=getIntent().getExtras().getString("tu_equipo");
        id_partido=getIntent().getExtras().getString("id_partido");
        id_usuario = getIntent().getExtras().getString("id_usuario");


        lv2Refresh = (ListView) findViewById(R.id.lv2Refresh);
        textView=(TextView)findViewById(R.id.lbl_informa);
        getSupportActionBar().setTitle("Capitanes Chat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        chat_cpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialogo_chat();
            }
        });

        Traer_msn();
    }

    public void empezaranimacion() {

        new CountDownTimer(miliosegundo, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Traer();
            }
        }.start();
    }


    public void Dialogo_chat(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Chat_capi_equipos.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.mensaje_usuario,null);

        builder.setView(dialogView);

        tilMensaje = (TextInputLayout) dialogView.findViewById(R.id.til_mensaje);
        ImageButton enviar = (ImageButton)dialogView.findViewById(R.id.btn_enviar_m);

        Traer_msn();

        final AlertDialog dialog = builder.create();

        enviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                capturar_mensaje();
                dialog.dismiss();

            }
        });


        dialog.show();
    }

    private void capturar_mensaje() {
        String msn = tilMensaje.getEditText().getText().toString().trim();
        new Chat_capi_equipos.CargarDatos().execute("http://www.sacalapp.com/insertar_mensaje_capis.php?id_usuario="+id_usuario+"&id_equipo="+id_equipo+"&id_partido="+id_partido+"&cuerpo_mensaje="+msn);

    }

    private void Traer_msn() {

        lv2Refresh.setAdapter(null);
        id_mensaje.clear();
        id_usuario_.clear();
        nombre_equipos.clear();
        logo_equipos.clear();
        cuerpo_mensaje.clear();
        hora_mensaje.clear();
        fecha_mensaje.clear();


        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/traer_mensaje_prtidos.php?id_partido="+id_partido, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    //progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        int dato=jsonArray.length();

                        if (dato==0){
                            lv2Refresh.setVisibility(View.INVISIBLE);
                            textView.setVisibility(View.VISIBLE);
                            empezaranimacion();
                        }else {
                            lv2Refresh.setVisibility(View.VISIBLE);
                            textView.setVisibility(View.INVISIBLE);
                            for (int i=0;i<jsonArray.length();i++) {

                                id_mensaje.add(jsonArray.getJSONObject(i).getString("id_mensaje"));
                                id_usuario_.add(jsonArray.getJSONObject(i).getString("id_capitan"));
                                nombre_equipos.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                                logo_equipos.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                                cuerpo_mensaje.add(jsonArray.getJSONObject(i).getString("cuerpo_mensaje"));
                                hora_mensaje.add(jsonArray.getJSONObject(i).getString("hora_mensaje"));
                                fecha_mensaje.add(jsonArray.getJSONObject(i).getString("fecha_mensaje"));

                            }

                            llenar();

                           /* try{
                                Thread.sleep(5000);
                            }catch(InterruptedException e){}
                            Traer_msn();*/
                        }

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

    private void Traer() {

            id_dato.clear();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/traer_mensaje_prtidos_com.php?id_partido="+id_partido, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    //progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        int dato=jsonArray.length();

                        if (dato>id_mensaje.size()){
                            Traer_msn();
                        }

                        empezaranimacion();

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

    private void llenar() {

        for (int i=0;i<id_mensaje.size();i++) {
            lv2Refresh.setAdapter(new Chat_capi_equipos.ImageAdater_chat(getApplicationContext()));
        }
        empezaranimacion();
    }

    private class ImageAdater_chat extends BaseAdapter {



        Context ctx;
        LayoutInflater layoutInflater;
        CircleImageView smartImageView,go;


        TextView fecha_mensaje_,mensaje_lbl,nombre,thora;


        public ImageAdater_chat(Context applicationContext){

            this.ctx=applicationContext;
            layoutInflater=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return id_mensaje.size();
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

            dato_comparar=(id_usuario_.get(pos).toString());

            if (dato_comparar.equals(id_usuario)){
                viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.chat,null);
            }else if (dato_comparar!=(id_usuario)){
                viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.chat_2,null);
            }

            smartImageView =(CircleImageView)viewGroup.findViewById(R.id.img_jugador);

            String nombre_="escudo_"+logo_equipos.get(pos).toString();
            String recurso="drawable";
            int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());

            smartImageView.setImageResource(res_imagen);


            thora=(TextView)viewGroup.findViewById(R.id.lbl_hora);
            fecha_mensaje_ =(TextView)viewGroup.findViewById(R.id.message_sender);
            mensaje_lbl =(TextView) viewGroup.findViewById(R.id.message_body);
            nombre =(TextView) viewGroup.findViewById(R.id.lbl_nombre_t);


            thora.setText(hora_mensaje.get(pos).toString());

            fecha_mensaje_.setText(fecha_mensaje.get(pos).toString());

            mensaje_lbl.setText(cuerpo_mensaje.get(pos).toString());
            nombre.setText(nombre_equipos.get(pos).toString());
            return viewGroup;
        }


    }

    private class CargarDatos extends AsyncTask<String, Void, String> {
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
            Traer_msn();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_cap, menu);
        mymenu = menu;
        //menuItem = menu.findItem(R.id.notificaciones);

        menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.informacion:
                createSimpleDialog();
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

    public android.app.AlertDialog createSimpleDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        builder.setTitle("Importante")

                .setMessage(R.string.info_chat_partido)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });



        builder.show();


        return builder.create();
    }


}
