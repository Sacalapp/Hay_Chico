package sacalapp.hay_chico;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
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

public class Crear_equipo extends AppCompatActivity implements RewardedVideoAdListener{

    private TextInputLayout tilapodo,tilnombre;
    AppCompatButton crear;
    private String nombre_equipo="null",apodo_equipo="null",id_usuario,cuenta;
    GridView escudos;
    ImageView equipo_img;
    String nombre_,recurso,dato_ ,dato,da="null";
    ArrayList datos = new ArrayList();
    int res_imagen,cantida_de_equipo;
    private RewardedVideoAd mAd;
    private String premiun_1="53",premiun_2="2";
    ArrayList id_can_equipos = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_crear_partido);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        id_usuario=getIntent().getExtras().getString("id_jugador");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_crear_equipo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Crea Tu Equipo");

        equipo_img=(ImageView)findViewById(R.id.img_equipos);
        tilnombre = (TextInputLayout) findViewById(R.id.til_nombre_equipo);
        tilapodo = (TextInputLayout) findViewById(R.id.til_apodo_equipo);
        crear=(AppCompatButton)findViewById(R.id.btn_crear_equipo);
        escudos=(GridView) findViewById(R.id.grid_escudos);

        escudos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dato_=(datos.get(position).toString());
                 da=dato_;
                nombre_="escudo_"+dato_;
                recurso="drawable";
                res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
                equipo_img.setImageResource(res_imagen);


            }
        });

        Traer();



        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nombre_equipo= tilnombre.getEditText().getText().toString();
                apodo_equipo= tilapodo.getEditText().getText().toString();

                if (nombre_equipo.equals("") || apodo_equipo.equals("") || da.equals("null")){
                    Snackbar.make(view, "Complete los campos", Snackbar.LENGTH_LONG).show();
                }else {

                    if (cantida_de_equipo < 2 || id_usuario.equals(premiun_1)|| id_usuario.equals(premiun_2)) {
                        new Crear_equipo.CargarDato().execute("http://www.sacalapp.com/insertar_equipos.php?nombre_equipo="+nombre_equipo+"&apodo_equipo="+apodo_equipo+"&capitan_equipo="+id_usuario+"&logo_equipo="+da);
                    }else {
                        Snackbar.make(view, "Solo pueder ser Capitán de dos equipos", Snackbar.LENGTH_LONG).show();
                    }

                }



            }
        });

        MobileAds.initialize(getApplicationContext(),"ca-app-pub-8067834228961607~3911858975");
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();
    }

    private void Traer() {

        id_can_equipos.clear();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/cantidad_equipos_jugador.php?id_usuario="+id_usuario, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    //progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                         cantida_de_equipo=jsonArray.length();
                        new Crear_equipo.verificacion().execute("http://www.sacalapp.com/cuenta.php?id_usuario="+id_usuario);

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

    private class verificacion extends AsyncTask<String, Void, String> {
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
                cuenta=ja.getString(0).toString();

                if (cuenta.equals("Verificado")){
                    motrar_escudos();
                }else {
                    Intent intent = new Intent(Crear_equipo.this, ajustes.class);
                    intent.putExtra("id_jugador", id_usuario);
                    startActivity(intent);
                    finish();
                    Toast.makeText(Crear_equipo.this, "Debes verificar tu cuenta para poder crear un equipo", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();

            }

        }
    }

    private void motrar_escudos() {
        for (int i=1;i<67;i++){
            datos.add(i);
        }

        for (int i=0;i<datos.size();i++){

            escudos.setAdapter(new Crear_equipo.ImageAdater(getApplicationContext()));
        }

    }

    private void loadRewardedVideoAd() {

        if (!mAd.isLoaded()) {
            mAd.loadAd("ca-app-pub-8067834228961607/2596532312", new AdRequest.Builder().build());

        }
    }

    // Required to reward the user.
    @Override
    public void onRewarded(RewardItem reward) {
        //Toast.makeText(this, "onRewarded! currency: " + reward.getType() + "  amount: " + reward.getAmount(), Toast.LENGTH_SHORT).show();
        // Reward the user.
    }

    // The following listener methods are optional.
    @Override
    public void onRewardedVideoAdLeftApplication() {
        mAd.show();
        //Toast.makeText(this, "onRewardedVideoAdLeftApplication",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
       // Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        //Toast.makeText(this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        mAd.show();
        //Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {
        //Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
        //Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        mAd.pause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAd.pause(this);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAd.destroy(this);
        super.onDestroy();
    }

    private class ImageAdater extends BaseAdapter {



        Context ctx;
        LayoutInflater layoutInflater;
        ImageView smartImageView;


        public ImageAdater(Context applicationContext){

            this.ctx=applicationContext;
            layoutInflater=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return datos.size();
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

            ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.escudos_de,null);


            dato=(datos.get(pos).toString());

            smartImageView =(ImageView) viewGroup.findViewById(R.id.img_equipo_perfil);

            nombre_="escudo_"+dato+".png";

            String URL_2 = "http://sacalapp.com/jarvicf/escudo_equipos/"+nombre_;

            Glide.with(smartImageView.getContext())
                    .load(URL_2)
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.drawable.esc)
                    .signature(new StringSignature(URL_2))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(smartImageView);

            return viewGroup;
        }


    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return super.onCreateOptionsMenu(menu);

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

            Toast.makeText(Crear_equipo.this, "Has creado un equipo. Enhorabuena", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(Crear_equipo.this, Perfil.class);
            startActivity(intent);
            finish();

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
