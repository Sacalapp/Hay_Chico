package sacalapp.hay_chico;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
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

public class Tengo_chico extends AppCompatActivity implements RewardedVideoAdListener {

    ArrayList id_equipo = new ArrayList();
    ArrayList nombre_equipo = new ArrayList();
    ArrayList logo_equipo = new ArrayList();
    ArrayList calificacion_equipo = new ArrayList();
    ArrayList id_partido = new ArrayList();
    ArrayList id_reserva = new ArrayList();
    ArrayList fecha_partido = new ArrayList();
    ArrayList hora_partido = new ArrayList();
    ArrayList nombre_cancha = new ArrayList();
    ArrayList logo_cancha = new ArrayList();
    ArrayList id_capitane = new ArrayList();

    ArrayList ids = new ArrayList();
    ListView listView_canchas_2;

    int comparar=0;

    ArrayList id_cancha = new ArrayList();
    ArrayList nombre_cancha__ = new ArrayList();
    ArrayList direcion_cancha = new ArrayList();
    ArrayList imagen_cancha = new ArrayList();

    ArrayList _id_cancha = new ArrayList();
    ArrayList _nombre_cancha__ = new ArrayList();
    ArrayList _direcion_cancha = new ArrayList();
    ArrayList _imagen_cancha = new ArrayList();

    private RewardedVideoAd mAd;


    FloatingActionButton filtrar;


    private String id_equipo_,nombre_equipo_,logo_equipo_,cali,id_partido_,fecha_,hora_,nombre_cancha_,logo_cancha_,id_usuario,id_partido_filtra,texto,id_capitane_;
    TextView infoma,info;
    private int dato1=0,dato2=0,dato_total=0,dato1_off,DATO,filt_1=0,filt_2=0,filt_t=0;
    float califica;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tengo_chico);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        id_usuario=getIntent().getExtras().getString("id_jugador");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tc);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hay Chico!");
        tarer_partido_usuario();

        filtrar = (FloatingActionButton) findViewById(R.id.filtrar);
        filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comparar=1;
                tarer_partido_usuario();
            }
        });

        listView = (ListView) findViewById(R.id.lst_comtenido_tc);
        info=(TextView)findViewById(R.id.informacion);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int dataa=0;
                id_partido_=(id_partido.get(position).toString());

                for (int i=0;i<ids.size();i++){
                    String data =ids.get(i).toString();
                    if (data.equals(id_partido_)){
                        dataa=1;
                    }
                }
                    if (dataa==0){
                        id_equipo_=(id_equipo.get(position).toString());
                        nombre_equipo_=(nombre_equipo.get(position).toString());
                        logo_equipo_=(logo_equipo.get(position).toString());
                        cali=(calificacion_equipo.get(position).toString());
                        fecha_=(fecha_partido.get(position).toString());
                        hora_=(hora_partido.get(position).toString());
                        nombre_cancha_=(nombre_cancha.get(position).toString());
                        logo_cancha_=(logo_cancha.get(position).toString());
                        id_capitane_=(id_capitane.get(position).toString());

                        detalles_partido();
                    }else {
                        Toast.makeText(getApplicationContext(), "Ya estas convocado a este partido", Toast.LENGTH_LONG).show();
                    }
            }
        });

        MobileAds.initialize(getApplicationContext(),"ca-app-pub-8067834228961607~3911858975");
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

    }

    private void loadRewardedVideoAd() {

        if (!mAd.isLoaded()) {
            mAd.loadAd("ca-app-pub-8067834228961607/4448236548", new AdRequest.Builder().build());

        }
    }

    public void Dialogo_canchas(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Tengo_chico.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.filtra_por_canchas,null);

        builder.setView(dialogView);
        infoma = (TextView)dialogView.findViewById(R.id.lbl_infor);
        listView_canchas_2 = (ListView) dialogView.findViewById(R.id.lista_canchas);
        infoma.setText("Buscando...");
        descargarImagen();

        final AlertDialog dialog = builder.create();


        listView_canchas_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                id_partido_filtra=(id_cancha.get(position).toString());
                String nom=(nombre_cancha__.get(position).toString());
                getSupportActionBar().setTitle(nom);
                equipo_1_fil();

              dialog.dismiss();
            }
        });



        dialog.show();
    }

    private void equipo_1() {

        id_equipo.clear();
        nombre_equipo.clear();
        logo_equipo.clear();
        calificacion_equipo.clear();
        id_partido.clear();
        id_reserva.clear();
        fecha_partido.clear();
        hora_partido.clear();
        nombre_cancha.clear();
        logo_cancha.clear();
        id_capitane.clear();

      AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/equipos_hay_chico.php", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        dato1=jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++){


                                        id_equipo.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                                        nombre_equipo.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                                        logo_equipo.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                                        calificacion_equipo.add(jsonArray.getJSONObject(i).getString("calificacion_equipo"));
                                        id_reserva.add(jsonArray.getJSONObject(i).getString("id_reserva"));
                                        id_partido.add(jsonArray.getJSONObject(i).getString("id_partido"));
                                        hora_partido.add(jsonArray.getJSONObject(i).getString("hora_inicio"));
                                        fecha_partido.add(jsonArray.getJSONObject(i).getString("fecha_partido"));
                                        nombre_cancha.add(jsonArray.getJSONObject(i).getString("cancha_nombre"));
                                        logo_cancha.add(jsonArray.getJSONObject(i).getString("imagen_cancha"));
                                        id_capitane.add(jsonArray.getJSONObject(i).getString("jugador_1_cap"));


                                    }

                        equipo_1_off();



                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error al traer jugadores_1", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void equipo_1_off() {

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/equipos_hay_chico_off.php", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        dato1_off=jsonArray.length();
                        DATO=dato1+dato1_off;
                        if (DATO==0){
                            listView.setVisibility(View.INVISIBLE);
                            info.setVisibility(View.VISIBLE);
                        }else {
                            listView.setVisibility(View.VISIBLE);
                            info.setVisibility(View.INVISIBLE);
                        }

                        for (int i=0;i<jsonArray.length();i++){

                            id_equipo.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                            nombre_equipo.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                            logo_equipo.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                            calificacion_equipo.add(jsonArray.getJSONObject(i).getString("calificacion_equipo"));
                            id_reserva.add(jsonArray.getJSONObject(i).getString("id_reserva"));
                            id_partido.add(jsonArray.getJSONObject(i).getString("id_partido"));
                            hora_partido.add(jsonArray.getJSONObject(i).getString("hora_inicio"));
                            fecha_partido.add(jsonArray.getJSONObject(i).getString("fecha_partido_off"));
                            nombre_cancha.add(jsonArray.getJSONObject(i).getString("cancha_nombre"));
                            logo_cancha.add(jsonArray.getJSONObject(i).getString("imagen_cancha"));
                            id_capitane.add(jsonArray.getJSONObject(i).getString("jugador_1_cap"));


                        }

                        for (int i=0;i<id_partido.size();i++){
                            listView.setAdapter(new Tengo_chico.ImageAdater(getApplicationContext()));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error al traer jugadores_1", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void equipo_bus() {

        id_equipo.clear();
        nombre_equipo.clear();
        logo_equipo.clear();
        calificacion_equipo.clear();
        id_partido.clear();
        id_reserva.clear();
        fecha_partido.clear();
        hora_partido.clear();
        nombre_cancha.clear();
        logo_cancha.clear();
        id_capitane.clear();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/equipos_hay_chico_buscar.php?id_texto="+texto, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        dato1=jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++){


                            id_equipo.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                            nombre_equipo.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                            logo_equipo.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                            calificacion_equipo.add(jsonArray.getJSONObject(i).getString("calificacion_equipo"));
                            id_reserva.add(jsonArray.getJSONObject(i).getString("id_reserva"));
                            id_partido.add(jsonArray.getJSONObject(i).getString("id_partido"));
                            hora_partido.add(jsonArray.getJSONObject(i).getString("hora_inicio"));
                            fecha_partido.add(jsonArray.getJSONObject(i).getString("fecha_partido"));
                            nombre_cancha.add(jsonArray.getJSONObject(i).getString("cancha_nombre"));
                            logo_cancha.add(jsonArray.getJSONObject(i).getString("imagen_cancha"));
                            id_capitane.add(jsonArray.getJSONObject(i).getString("jugador_1_cap"));
                        }
                        equipo_bus_off();

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

    private void equipo_bus_off() {


        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/equipos_hay_chico_buscar_off.php?id_texto="+texto, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        dato1_off=jsonArray.length();
                        DATO=dato1+dato1_off;
                        if (DATO==0){
                            listView.setVisibility(View.INVISIBLE);
                            info.setVisibility(View.VISIBLE);
                        }else {
                            listView.setVisibility(View.VISIBLE);
                            info.setVisibility(View.INVISIBLE);

                            for (int i=0;i<jsonArray.length();i++){


                                id_equipo.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                                nombre_equipo.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                                logo_equipo.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                                calificacion_equipo.add(jsonArray.getJSONObject(i).getString("calificacion_equipo"));
                                id_reserva.add(jsonArray.getJSONObject(i).getString("id_reserva"));
                                id_partido.add(jsonArray.getJSONObject(i).getString("id_partido"));
                                hora_partido.add(jsonArray.getJSONObject(i).getString("hora_inicio"));
                                fecha_partido.add(jsonArray.getJSONObject(i).getString("fecha_partido_off"));
                                nombre_cancha.add(jsonArray.getJSONObject(i).getString("cancha_nombre"));
                                logo_cancha.add(jsonArray.getJSONObject(i).getString("imagen_cancha"));
                                id_capitane.add(jsonArray.getJSONObject(i).getString("jugador_1_cap"));


                            }

                            for (int i=0;i<id_partido.size();i++){
                                listView.setAdapter(new Tengo_chico.ImageAdater(getApplicationContext()));
                            }
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

    private void equipo_1_fil() {

        id_equipo.clear();
        nombre_equipo.clear();
        logo_equipo.clear();
        calificacion_equipo.clear();
        id_partido.clear();
        id_reserva.clear();
        fecha_partido.clear();
        hora_partido.clear();
        nombre_cancha.clear();
        logo_cancha.clear();
        id_capitane.clear();
        listView.clearFocus();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/equipo_hay_chico_filtrar.php?id_cancha="+id_partido_filtra, new AsyncHttpResponseHandler() {

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
                                        id_reserva.add(jsonArray.getJSONObject(i).getString("id_reserva"));
                                        id_partido.add(jsonArray.getJSONObject(i).getString("id_partido"));
                                        hora_partido.add(jsonArray.getJSONObject(i).getString("hora_inicio"));
                                        fecha_partido.add(jsonArray.getJSONObject(i).getString("fecha_partido"));
                                        nombre_cancha.add(jsonArray.getJSONObject(i).getString("cancha_nombre"));
                                        logo_cancha.add(jsonArray.getJSONObject(i).getString("imagen_cancha"));
                                        id_capitane.add(jsonArray.getJSONObject(i).getString("jugador_1_cap"));
                        }
                        equipo_1_fil_off();

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

    private void equipo_1_fil_off() {

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/equipo_hay_chico_filtrar_off.php?id_cancha="+id_partido_filtra, new AsyncHttpResponseHandler() {

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
                            id_reserva.add(jsonArray.getJSONObject(i).getString("id_reserva"));
                            id_partido.add(jsonArray.getJSONObject(i).getString("id_partido"));
                            hora_partido.add(jsonArray.getJSONObject(i).getString("hora_inicio"));
                            fecha_partido.add(jsonArray.getJSONObject(i).getString("fecha_partido_off"));
                            nombre_cancha.add(jsonArray.getJSONObject(i).getString("cancha_nombre"));
                            logo_cancha.add(jsonArray.getJSONObject(i).getString("imagen_cancha"));
                            id_capitane.add(jsonArray.getJSONObject(i).getString("jugador_1_cap"));

                        }

                        for (int i=0;i<id_partido.size();i++){
                            listView.setAdapter(new Tengo_chico.ImageAdater(getApplicationContext()));
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

    public void detalles_partido(){

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Tengo_chico.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.unirse_partido,null);

        builder.setView(dialogView);

        RatingBar calificacion_eq=(RatingBar)dialogView.findViewById(R.id.rating_cali_equi);

        TextView info =(TextView)dialogView.findViewById(R.id.lbl_info_invitacion);
        TextView fecha=(TextView)dialogView.findViewById(R.id.lbl_fecha_par);
        TextView cancha =(TextView)dialogView.findViewById(R.id.lbl_nombre_cancha);
        TextView hora =(TextView)dialogView.findViewById(R.id.lbl_hora_par);
        ImageView imagen_fichaje =(ImageView)dialogView.findViewById(R.id.smartImageView_equipo);


        AppCompatButton volver = (AppCompatButton)dialogView.findViewById(R.id.btn_volver);
        AppCompatButton aceptar = (AppCompatButton)dialogView.findViewById(R.id.btn_aceptar);


        califica=Float.parseFloat(cali.toString());
        //cali=
        calificacion_eq.setRating(califica);
        info.setText("Partido Amistoso");
       cancha.setText(nombre_cancha_);
        fecha.setText(fecha_);

        if (hora_.equals("8:00")){
            hora.setText("800AM");
        }else  if (hora_.equals("9:00")){
            hora.setText("9:00AM");
        }else if (hora_.equals("10:00")){
            hora.setText("10:00AM");
        }else  if (hora_.equals("11:00")){
            hora.setText("11:00AM");
        }else  if (hora_.equals("12:00")){
            hora.setText("12:00PM");
        }else if (hora_.equals("13:00")){
            hora.setText("1:00PM");
        }else  if (hora_.equals("14:00")){
            hora.setText("2:00PM");
        }else  if (hora_.equals("15:00")){
            hora.setText("3:00PM");
        }else  if (hora_.equals("16:00")){
            hora.setText("4:00PM");
        }else  if (hora_.equals("17:00")){
            hora.setText("5:00PM");
        }else  if (hora_.equals("18:00")){
            hora.setText("6:00PM");
        }else  if (hora_.equals("19:00")){
            hora.setText("7:00PM");
        }else  if (hora_.equals("20:00")){
            hora.setText("8:00PM");
        }else  if (hora_.equals("21:00")){
            hora.setText("9:00PM");
        }else  if (hora_.equals("22:00")){
            hora.setText("10:00PM");
        }else  if (hora_.equals("23:00")){
            hora.setText("11:00PM");
        }else  if (hora_.equals("24:00")){
            hora.setText("12:00PM");
        }

        String nombre_="escudo_"+logo_equipo_;
        String recurso="drawable";
        int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
        imagen_fichaje.setImageResource(res_imagen);


        final android.support.v7.app.AlertDialog dialog = builder.create();

        volver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fichaje2();
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    private void tarer_partido_usuario() {

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/partidos_usuarios.php?id_usuario="+id_usuario, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i=0;i<jsonArray.length();i++){
                            ids.add(jsonArray.getJSONObject(i).getString("id_partido"));
                        }

                        if (comparar==0) {
                            equipo_1();
                        }else if (comparar==1){
                            Dialogo_canchas();
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

    @Override
    public void onRewardedVideoAdLoaded() {
        mAd.show();

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        mAd.show();
        //Toast.makeText(this, "5", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        //Toast.makeText(this, "5", Toast.LENGTH_SHORT).show();
    }

    private class ImageAdater extends BaseAdapter {

        RatingBar ratinCali;
        Context ctx;
        LayoutInflater layoutInflater;
        ImageView smartImageView;
        TextView tnombre;
        float cali;


        public ImageAdater(Context applicationContext){
            this.ctx=applicationContext;
            layoutInflater=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return id_equipo.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {

            ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.hay_chico,null);

            smartImageView =(ImageView)viewGroup.findViewById(R.id.ima_equipo_tc);
            ratinCali = (RatingBar)viewGroup.findViewById(R.id.ratingBar_tg);
            tnombre =(TextView) viewGroup.findViewById(R.id.lbl_nombre_equipo_tg);

            String nombre_="escudo_"+logo_equipo.get(pos).toString();
            String recurso="drawable";
            int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
            smartImageView.setImageResource(res_imagen);

            //smartImageView.setImage(R.drawable.calendario);
            cali=Float.parseFloat(calificacion_equipo.get(pos).toString());
            //cali=Float.parseFloat(rating.get(pos).toString());
            ratinCali.setRating(cali);

            tnombre.setText(nombre_equipo.get(pos).toString());


            return viewGroup;
        }


    }

    private void fichaje2() {

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://www.sacalapp.com/fichaje_invitacion_2.php?id_jugador="+id_usuario+"&id_patido="+id_partido_, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        if (jsonArray.length()==0){
                            new Tengo_chico.CargarDato_2().execute("http://www.sacalapp.com/tengo_chico.php?id_equipo1="+id_equipo_+"&id_equipo2="+id_equipo_+"&id_jugador="+id_capitane_+"&id_partido="+id_partido_+"&id_usuario="+id_usuario);
                        }else {
                            Toast.makeText(getApplicationContext(), "Ya Tienes Invitacion a este partido", Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fichajes, menu);
        menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

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

                listView.setAdapter(null);
                texto=newText;
                equipo_bus();

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Actualizar:
                getSupportActionBar().setTitle("Hay Chico!");
                comparar=0;
                tarer_partido_usuario();
                return true;

            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void descargarImagen() {

        id_cancha.clear();
        nombre_cancha__.clear();
        direcion_cancha.clear();
        imagen_cancha.clear();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/lista_canchas_tengo.php", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        filt_1=jsonArray.length();
                        for (int i = 0; i < jsonArray.length(); i++) {

                            id_cancha.add(jsonArray.getJSONObject(i).getString("id_cancha"));
                            nombre_cancha__.add(jsonArray.getJSONObject(i).getString("cancha_nombre"));
                            direcion_cancha.add(jsonArray.getJSONObject(i).getString("direccion_cancha"));
                            imagen_cancha.add(jsonArray.getJSONObject(i).getString("imagen_cancha"));

                        }
                        descargarImagen_off();

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

    private void descargarImagen_off() {

        final ProgressDialog progressDialog = new ProgressDialog(Tengo_chico.this);
        progressDialog.setMessage("Cargardo Canchas...");
        progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/lista_canchas_tengo_off.php", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        filt_2=jsonArray.length();
                        filt_t=filt_1+filt_2;
                        if (filt_t==0){
                            infoma.setText("No hay partidos disponibles");
                        }else {
                            infoma.setText("conplejos donde ¡Hay Chico!");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                id_cancha.add(jsonArray.getJSONObject(i).getString("id_cancha"));
                                nombre_cancha__.add(jsonArray.getJSONObject(i).getString("cancha_nombre"));
                                direcion_cancha.add(jsonArray.getJSONObject(i).getString("direccion_cancha"));
                                imagen_cancha.add(jsonArray.getJSONObject(i).getString("imagen_cancha"));
                            }
                        }



                   for (int i = 0; i < id_cancha.size(); i++) {
                       listView_canchas_2.setAdapter(new Tengo_chico.ImageAdater_canchas(getApplicationContext()));
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
        ImageView smartImageView_cancha;
        TextView tnombre, tdirecion;


        public ImageAdater_canchas(Context applicationContext) {

            this.ctx = applicationContext;
            layoutInflater = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return id_cancha.size();
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

            ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.cancha_sola_2, null);


            smartImageView_cancha = (ImageView) viewGroup.findViewById(R.id.img_cancha_portada);


            tnombre = (TextView) viewGroup.findViewById(R.id.lbl_nombre_cancha);
            tdirecion = (TextView) viewGroup.findViewById(R.id.lbl_dir_cancha);

            String urlfinal = "http://sacalapp.com/jarvicf/canchas_img/" + imagen_cancha.get(pos).toString();
            Glide.with(smartImageView_cancha.getContext())
                    .load(urlfinal)
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.drawable.perfil_equipo)
                    .signature(new StringSignature(urlfinal))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    //.skipMemoryCache(true)
                    .into(smartImageView_cancha);

            tnombre.setText(nombre_cancha__.get(pos).toString());
            tdirecion.setText(direcion_cancha.get(pos).toString());
            return viewGroup;
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


            Toast.makeText(getApplicationContext(), "Hemos Enviado la solicitud", Toast.LENGTH_LONG).show();

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
