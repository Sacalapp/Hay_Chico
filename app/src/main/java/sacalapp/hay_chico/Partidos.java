package sacalapp.hay_chico;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TabHost;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class Partidos extends AppCompatActivity {

    private String id_usuario, id_partido, id_equipo_,id_reserva, nombre_equipo_1, nombre_equipo_2, logo_equipo_1, logo_equipo_2, id_equipo_1, id_equipo_2,latitud_cancha,longitud_cancha;
    private String fecha_partido,hora_partido,cancha,nombre_cancha,direccion_cancha,cancha_imagen,fecha,tu_equipo,cancha_telefonos,cancha_email;
    private String id_jg,non_jg,pos_jg,logo_jg,equipo_uno,equipo_dos,sexo_usuario;
    SmartImageView logo_cancha,img_equipo_1,img_equipo_2,mini_eqi_1,mini_eqi_2;
    TextView equipo_1_t, equipo_2_t,canju,ciudad_com;
    private Date fecha_pa;
    private TextView tvDay, tvHour, tvMinute, tvSecond, tvEvent,nombre_cancha_ju,fecha_p,hora,equipo_local,txtViewCount,lb_frcha,lb_hora,lb_dia,nombre_cancha_comple,direccion_complejo,telefono_complejo,email_complejo;
    private  TextView capi_1,capi_2;
    private LinearLayout linearLayout1, linearLayout2;
    private Handler handler;
    private Runnable runnable;
    private int count=0,def=0,del=0;

    String Hora_oficial,Fecha_oficial,fecha_final;

    ArrayList id_jugador_equipo=new ArrayList();
    ArrayList nombre_jugador_equipo=new ArrayList();
    ArrayList posicion_jugador_equipo=new ArrayList();
    ArrayList logo_jugador=new ArrayList();
    ArrayList estado=new ArrayList();

    ArrayList ids_judores_1=new ArrayList();
    ArrayList ids_judores_2=new ArrayList();


    ArrayList id_jugador_equipo_vis=new ArrayList();
    ArrayList nombre_jugador_equipo_vis=new ArrayList();
    ArrayList posicion_jugador_equipo_vis=new ArrayList();
    ArrayList estado_vis=new ArrayList();
    ArrayList logo_jugador_vis=new ArrayList();
    private String nombre2,posicion2,ciudad2,edad2,califica2,foto_user,partido_;
    private String jugador_1="Null",jugador_2="Null",jugador_3="Null",jugador_4="Null",jugador_5="Null",jugador_6="Null",jugador_7="Null",jugador_8="Null",jugador_9="Null",dato_user,dato_user_2,campo,campo2,nick_1,nick_2;
    private String nombre3,califica3,foto_user3,posicion3,dato,capitane,perfil,ciudad_complejo,c1,c2,Level;

    private TextView portero,Defensa_1,Defensa_2,Medio,Delantero_1,Delantero_2,jugador7,jugador8,jugador9;
    private TextView portero_vis,Defensa_1_vis,Defensa_2_vis,Medio_vis,Delantero_1_vis,Delantero_2_vis,jugador7_vis,jugador8_vis,jugador9_vis;

    ScrollView scrollView;

    LinearLayout linearLayout;
ImageView equipo_1, equipo_2;
    ListView listView_fotos;
    CheckBox local,visitante;
    int compa=0;

    private CircleImageView smartImage_portero, smartImage_Defensa_1,smartImage_Defensa_2,smartImage_Medio,smartImage_Delantero_1,smartImage_Delantero_2,smartImage_jugador7,smartImage_jugador8,smartImage_jugador9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_partidos);
        id_usuario = getIntent().getExtras().getString("id_jugador");
        id_partido = getIntent().getExtras().getString("id_partido");
        tu_equipo = getIntent().getExtras().getString("tu_equipo");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        linearLayout=(LinearLayout)findViewById(R.id.cabezera);
        scrollView=(ScrollView)findViewById(R.id.Sc);

        compa=0;
        c1 = getIntent().getExtras().getString("capi1");
        c2 = getIntent().getExtras().getString("capi2");

        capitane = getIntent().getExtras().getString("capitan");
        sexo_usuario = getIntent().getExtras().getString("sexo");

        id_reserva = getIntent().getExtras().getString("id_reserva");
        partido_ = getIntent().getExtras().getString("partido");

        id_equipo_1 = getIntent().getExtras().getString("id_equipo");
        nombre_equipo_1 = getIntent().getExtras().getString("nombre_equipo");
        logo_equipo_1 = getIntent().getExtras().getString("logo_equipo");
        nick_1 = getIntent().getExtras().getString("nick_1");

        id_equipo_2 = getIntent().getExtras().getString("id_equipo_2");
        nombre_equipo_2 = getIntent().getExtras().getString("nombre_equipo_2");
        logo_equipo_2 = getIntent().getExtras().getString("logo_equipo_2");
        nick_2 = getIntent().getExtras().getString("nick_2");

        canju=(TextView)findViewById(R.id.lbl_cancha_par) ;
        equipo_1 = (ImageView) findViewById(R.id.img_equipo_1);
        equipo_2 = (ImageView) findViewById(R.id.img_equipo_2);
        img_equipo_1 = (SmartImageView) findViewById(R.id.img_equipo_1_eq);
        img_equipo_2 = (SmartImageView) findViewById(R.id.img_equipo_2_eq);
        logo_cancha = (SmartImageView) findViewById(R.id.img_logo_cancha);

        mini_eqi_1 = (SmartImageView) findViewById(R.id.mini_logo_equipo_1);
        mini_eqi_2 = (SmartImageView) findViewById(R.id.mini_logo_equipo_2);

        //equipo_local= (TextView) findViewById(R.id.lbl_equipo_local);
        equipo_1_t = (TextView) findViewById(R.id.lbl_equipo_1);
        equipo_2_t = (TextView) findViewById(R.id.lbl_equipo_2);

        nombre_cancha_comple = (TextView) findViewById(R.id.lbl_nombre_complejo);
        direccion_complejo = (TextView) findViewById(R.id.lbl_direccion_complejo);
        telefono_complejo = (TextView) findViewById(R.id.lbl_tefefonos_complejos);
        email_complejo = (TextView) findViewById(R.id.lbl_email_complejo);
        ciudad_com = (TextView) findViewById(R.id.lbl_ciudad_complejo);
        email_complejo = (TextView) findViewById(R.id.lbl_ciudad_complejo);

        capi_1=(TextView)findViewById(R.id.lbl_capi_1);
        capi_2=(TextView)findViewById(R.id.lbl_capi_2);
        capi_1.setText(c1);
        capi_2.setText(c2);

        portero = (TextView) findViewById(R.id.lbl_arquero);
        Defensa_1 = (TextView) findViewById(R.id.lbl_defensa_1);
        Defensa_2 = (TextView) findViewById(R.id.lbl_defensa_2);
        Medio = (TextView) findViewById(R.id.lbl_medio);
        Delantero_1 = (TextView) findViewById(R.id.lbl_delantero_1);
        Delantero_2 = (TextView) findViewById(R.id.lbl_delantero_2);
        jugador7 = (TextView) findViewById(R.id.lbl_jugador_7);
        jugador8 = (TextView) findViewById(R.id.lbl_jugador_8);
        jugador9 = (TextView) findViewById(R.id.lbl_jugador_9);

        portero_vis = (TextView) findViewById(R.id.lbl_arquero_vis);
        Defensa_1_vis = (TextView) findViewById(R.id.lbl_defensa_1_vis);
        Defensa_2_vis = (TextView) findViewById(R.id.lbl_defensa_2_vis);
        Medio_vis = (TextView) findViewById(R.id.lbl_medio_vis);
        Delantero_1_vis = (TextView) findViewById(R.id.lbl_delantero_1_vis);
        Delantero_2_vis = (TextView) findViewById(R.id.lbl_delantero_2_vis);
        jugador7_vis = (TextView) findViewById(R.id.lbl_jugador_7_vis);
        jugador8_vis = (TextView) findViewById(R.id.lbl_jugador_8_vis);
        jugador9_vis = (TextView) findViewById(R.id.lbl_jugador_9_vis);

        smartImage_portero = (CircleImageView) findViewById(R.id.portero_1);
        smartImage_Defensa_1 = (CircleImageView) findViewById(R.id.jugador_2);
        smartImage_Defensa_2 = (CircleImageView) findViewById(R.id.jugador_3);
        smartImage_Medio = (CircleImageView) findViewById(R.id.jugador_4);
        smartImage_Delantero_1 = (CircleImageView) findViewById(R.id.jugador_5);
        smartImage_Delantero_2 = (CircleImageView) findViewById(R.id.jugador_6);
        smartImage_jugador7 = (CircleImageView) findViewById(R.id.jugador_7);
        smartImage_jugador8 = (CircleImageView) findViewById(R.id.jugador_8);
        smartImage_jugador9 = (CircleImageView) findViewById(R.id.jugador_9);



        fecha_p = (TextView) findViewById(R.id.lbl_email_complejo);
        hora = (TextView) findViewById(R.id.lbl_hora);

        lb_frcha = (TextView) findViewById(R.id.lb_fecha);
        lb_hora = (TextView) findViewById(R.id.lb_hora);
        lb_dia = (TextView) findViewById(R.id.lbl_dia);

        local=(CheckBox)findViewById(R.id.CB_local);
        visitante=(CheckBox)findViewById(R.id.CB_visi);

        equipo_1_t.setText(nombre_equipo_1);
        equipo_2_t.setText(nombre_equipo_2);

        listView_fotos=(ListView)findViewById(R.id.list_img_cancha) ;

        String nombre_="escudo_"+ logo_equipo_1;
        String recurso="drawable";
        int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
        equipo_1.setImageResource(res_imagen);

        String nombre_1="escudo_"+ logo_equipo_2;
        String recurso1="drawable";
        int res_imagen1 = getResources().getIdentifier(nombre_1, recurso1,getPackageName());
        equipo_2.setImageResource(res_imagen1);


        linearLayout2 = (LinearLayout) findViewById(R.id.ll2);
        tvDay = (TextView) findViewById(R.id.txtTimerDay);
        tvHour = (TextView) findViewById(R.id.txtTimerHour);
        tvMinute = (TextView) findViewById(R.id.txtTimerMinute);
        tvSecond = (TextView) findViewById(R.id.txtTimerSecond);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_partidos);
        setSupportActionBar(toolbar);


        TabHost tabs = (TabHost) findViewById(R.id.tabhost);
        tabs.setup();

        new Partidos.Consultar_hora().execute("http://www.sacalapp.com/hora.php");

        local.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true) {

                    if (equipo_uno.equals(tu_equipo)){
                        new Partidos.CargarDato_estado().execute("http://www.sacalapp.com/actualizar_estado_partido.php?id_partido="+id_partido+"&estado="+"Activado");
                    }else if (equipo_dos.equals(tu_equipo)){
                        new Partidos.CargarDato_estado().execute("http://www.sacalapp.com/actualizar_estado_partido_2.php?id_partido="+id_partido+"&estado="+"Activado");
                    }

                } else if (isChecked==false) {

                    if (equipo_uno.equals(tu_equipo)){
                        new Partidos.CargarDato_estado().execute("http://www.sacalapp.com/actualizar_estado_partido.php?id_partido="+id_partido+"&estado="+"Desactivado");
                    }else if (equipo_dos.equals(tu_equipo)){
                        new Partidos.CargarDato_estado().execute("http://www.sacalapp.com/actualizar_estado_partido_2.php?id_partido="+id_partido+"&estado="+"Desactivado");
                    }

                }
            }
        });

        Resources res = getResources();

       // String nombre_="escudo_"+logo_equipo_1;
        //String recurso="drawable";
        //int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.Tu_equipo);
        //spec.setIndicator("",res.getDrawable(res_imagen));
        spec.setIndicator(nick_1);
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.Detalles);
        //spec.setIndicator("",res.getDrawable(R.drawable.ic_ojos));
        spec.setIndicator("Info");
        tabs.addTab(spec);

        //String nombre_1="escudo_"+logo_equipo_2;
        //String recurso1="drawable";
        //int res_imagen1 = getResources().getIdentifier(nombre_1, recurso1,getPackageName());
        spec = tabs.newTabSpec("mitab3");
        //spec.setIndicator("",res.getDrawable(res_imagen1));
        spec.setContent(R.id.Rival);
        spec.setIndicator(nick_2);
        tabs.addTab(spec);

        tabs.setCurrentTab(1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Partido");

        if (partido_.equals("1")){
            new Partidos.ConsultarDatos().execute("http://www.sacalapp.com/reserva_datos.php?id_reserva=" + id_reserva);
        }else  if (partido_.equals("2")){
            new Partidos.ConsultarDatos().execute("http://www.sacalapp.com/reserva_datos_off.php?id_reserva=" + id_reserva);
        }


        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId=="mitab1"){


                   dato="mitab1";

                    String urlfinal = "http://sacalapp.com/jarvicf/escudo_equipos/" + logo_equipo_1;
                    Rect rect = new Rect(img_equipo_1.getLeft(), img_equipo_1.getTop(), img_equipo_1.getRight(), img_equipo_1.getBottom());
                    img_equipo_1.setImageUrl(urlfinal, rect);

                    if (equipo_uno.equals(tu_equipo)){
                        new Partidos.Consultarid().execute("http://www.sacalapp.com/ids_equipo_1.php?id_equipo_1="+id_equipo_1+"&id_partido="+id_partido);
                    } else if (equipo_dos.equals(tu_equipo)){
                        new Partidos.Consultarid_2().execute("http://www.sacalapp.com/ids_equipo_2.php?id_equipo_2="+id_equipo_1+"&id_partido="+id_partido);

                    }

                }
                if(tabId=="mitab2"){
                    compa=0;
                    initUI();
                    countDownStart();

                }
                if(tabId=="mitab3"){
                    compa=0;
                    dato="mitab3";
                    String urlfinal = "http://sacalapp.com/jarvicf/escudo_equipos/" + logo_equipo_2;
                    Rect rect = new Rect(img_equipo_2.getLeft(), img_equipo_2.getTop(), img_equipo_2.getRight(), img_equipo_2.getBottom());
                    img_equipo_2.setImageUrl(urlfinal, rect);
                    reset();
                    if (equipo_dos.equals(tu_equipo)){
                        new Partidos.Consultarid().execute("http://www.sacalapp.com/ids_equipo_1.php?id_equipo_1="+id_equipo_2+"&id_partido="+id_partido);
                    } else if (equipo_uno.equals(tu_equipo)){

                        new Partidos.Consultarid_2().execute("http://www.sacalapp.com/ids_equipo_2.php?id_equipo_2="+id_equipo_2+"&id_partido="+id_partido);
                    }
                    }

            }
        });






    }

    private void reset() {
        jugador_1="Null";
        jugador_2="Null";
        jugador_3="Null";
        jugador_4="Null";
        jugador_5="Null";
        jugador_6="Null";
        jugador_7="Null";
        jugador_8="Null";
        jugador_9="Null";



    }

    private class Consultar_hora extends AsyncTask<String, Void, String> {
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

                Hora_oficial=ja.getString(0).toString();


                new Partidos.Consultar_fecha().execute("http://www.sacalapp.com/fecha.php");

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Partidos.this, "Error hora", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class Consultar_fecha extends AsyncTask<String, Void, String> {
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
                Fecha_oficial=ja.getString(0).toString();

                fecha_final=Fecha_oficial+"  "+Hora_oficial+":00";

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Partidos.this, "Error hora", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void equipo_visitante() {
        String urlfinal = "http://sacalapp.com/jarvicf/escudo_equipos/" + logo_equipo_2;
        Rect rect = new Rect(img_equipo_2.getLeft(), img_equipo_2.getTop(), img_equipo_2.getRight(), img_equipo_2.getBottom());
        img_equipo_2.setImageUrl(urlfinal, rect);
    }

    private void equipo_local() {
       // equipo_local.setText(nombre_equipo_1);

        String urlfinal = "http://sacalapp.com/jarvicf/escudo_equipos/" + logo_equipo_1;
        Rect rect = new Rect(img_equipo_1.getLeft(), img_equipo_1.getTop(), img_equipo_1.getRight(), img_equipo_1.getBottom());
        img_equipo_1.setImageUrl(urlfinal, rect);

    }

    private void TraerEquipo() {

        id_jugador_equipo.clear();
        nombre_jugador_equipo.clear();
        posicion_jugador_equipo.clear();
        logo_jugador.clear();
        estado.clear();


        final ProgressDialog progressDialog = new ProgressDialog(Partidos.this);
        progressDialog.setMessage("Cargardo...");
        progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/consultar_equipo_partido.php?id_equipo="+id_equipo_1+"&id_partido="+id_partido, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        def = 0;
                        del = 0;
                        for (int i = 0; i < jsonArray.length(); i++) {

                            id_jugador_equipo.add(jsonArray.getJSONObject(i).getString("usuario_id"));
                            nombre_jugador_equipo.add(jsonArray.getJSONObject(i).getString("nombre"));
                            posicion_jugador_equipo.add(jsonArray.getJSONObject(i).getString("posicion"));
                            logo_jugador.add(jsonArray.getJSONObject(i).getString("foto"));
                            estado.add(jsonArray.getJSONObject(i).getString("Status_equipo_1"));

                            if (i==0){
                                String tengo;
                                tengo = (estado.get(i).toString());
                                if (tengo.equals("Desactivado")){
                                    local.setChecked(false);
                                }else  if (tengo.equals("Activado")){
                                    local.setChecked(true);
                                }
                            }

                    }

                    String id,id_com;
                        int xx=0;
                        for(int x=0;x<ids_judores_1.size();x++) {
                            id = (ids_judores_1.get(x).toString());

                            for (int i = 0; i < id_jugador_equipo.size(); i++) {
                                id_com = (id_jugador_equipo.get(i).toString());

                                if (id.equals(id_com)) {
                                    xx=x;
                                    id_jg = (id_jugador_equipo.get(i).toString());
                                    non_jg = (nombre_jugador_equipo.get(i).toString());
                                    pos_jg = (posicion_jugador_equipo.get(i).toString());
                                    logo_jg = (logo_jugador.get(i).toString());

                                }

                                if (xx == 0) {
                                    jugador_1="null";
                                    jugador_1 = id_jg;


                                    portero.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_portero.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil2)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_portero);

                                } else if (xx == 1) {
                                    jugador_2="null";
                                    jugador_2 = id_jg;



                                    Defensa_1.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_Defensa_1.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil2)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_Defensa_1);

                                }else if (xx == 2) {
                                    jugador_3="null";
                                    jugador_3 = id_jg;


                                    Defensa_2.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_Defensa_2.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil2)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .into(smartImage_Defensa_2);

                                }else  if (xx == 3) {
                                    jugador_4="null";
                                    jugador_4 = id_jg;

                                    Medio.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_Medio.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil2)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_Medio);

                                }else  if (xx == 4) {
                                    jugador_5="null";
                                    jugador_5 = id_jg;

                                    Delantero_1.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_Delantero_1.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil2)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_Delantero_1);

                                }else  if (xx == 5) {
                                    jugador_6="null";
                                    jugador_6 = id_jg;

                                     smartImage_Delantero_2 = (CircleImageView) findViewById(R.id.jugador_6);

                                    Delantero_2.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_Delantero_2.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil2)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_Delantero_2);

                                }else  if (xx == 6) {
                                    jugador_7="null";
                                    jugador_7 = id_jg;



                                    jugador7.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_jugador7.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil2)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_jugador7);

                                } else  if (xx == 7) {
                                    jugador_8="null";
                                    jugador_8 = id_jg;



                                    jugador8.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_jugador8.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil2)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_jugador8);

                                }else  if (xx == 8) {
                                    jugador_9="null";
                                    jugador_9 = id_jg;



                                    jugador9.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_jugador9.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil2)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_jugador9);
                                }

                            }

                        }



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

    private void TraerEquipo_1() {

        id_jugador_equipo_vis.clear();
        nombre_jugador_equipo_vis.clear();
        posicion_jugador_equipo_vis.clear();
        logo_jugador_vis.clear();


        final ProgressDialog progressDialog = new ProgressDialog(Partidos.this);
        progressDialog.setMessage("Cargardo...");
        progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/consultar_equipo_partido.php?id_equipo="+id_equipo_2+"&id_partido="+id_partido, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        def=0;
                        del=0;
                        for (int i=0;i<jsonArray.length();i++) {

                            id_jugador_equipo_vis.add(jsonArray.getJSONObject(i).getString("usuario_id"));
                            nombre_jugador_equipo_vis.add(jsonArray.getJSONObject(i).getString("nombre"));
                            posicion_jugador_equipo_vis.add(jsonArray.getJSONObject(i).getString("posicion"));
                            logo_jugador_vis.add(jsonArray.getJSONObject(i).getString("foto"));
                            estado_vis.add(jsonArray.getJSONObject(i).getString("Status_equipo_1"));

                            if (i == 0) {
                                String tengo;
                                tengo = (estado_vis.get(i).toString());
                                visitante.setEnabled(false);
                                if (tengo.equals("Desactivado")) {
                                    visitante.setChecked(false);
                                } else if (tengo.equals("Activado")) {
                                    visitante.setChecked(true);
                                }
                            }
                        }


                        String id,id_com;
                        int xx=0;
                        for(int x=0;x<ids_judores_1.size();x++) {
                            id = (ids_judores_1.get(x).toString());

                            for (int i = 0; i < id_jugador_equipo_vis.size(); i++) {
                                id_com = (id_jugador_equipo_vis.get(i).toString());

                                if (id.equals(id_com)) {
                                    xx = x;
                                    id_jg = (id_jugador_equipo_vis.get(i).toString());
                                    non_jg = (nombre_jugador_equipo_vis.get(i).toString());
                                    pos_jg = (posicion_jugador_equipo_vis.get(i).toString());
                                    logo_jg = (logo_jugador_vis.get(i).toString());

                                }

                                if (xx == 0) {
                                    jugador_1 = id_jg;

                                     smartImage_portero = (CircleImageView) findViewById(R.id.jugador_1_vis);

                                    portero_vis.setVisibility(View.VISIBLE);
                                    portero_vis.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_portero.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_portero);

                                } else if (xx == 1) {
                                    jugador_2 = id_jg;

                                     smartImage_Defensa_1 = (CircleImageView) findViewById(R.id.jugador_2_vis);

                                    Defensa_1_vis.setVisibility(View.VISIBLE);
                                    Defensa_1_vis.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_Defensa_1.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_Defensa_1);

                                } else if (xx == 2) {
                                    jugador_3 = id_jg;

                                     smartImage_Defensa_2 = (CircleImageView) findViewById(R.id.jugador_3_vis);

                                    Defensa_2_vis.setVisibility(View.VISIBLE);
                                    Defensa_2_vis.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_Defensa_2.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_Defensa_2);
                                } else if (xx == 3) {
                                    jugador_4 = id_jg;

                                     smartImage_Medio = (CircleImageView) findViewById(R.id.jugador_4_vis);

                                    Medio_vis.setVisibility(View.VISIBLE);
                                    Medio_vis.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_Medio.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_Medio);
                                } else if (xx == 4) {
                                    jugador_5 = id_jg;

                                     smartImage_Delantero_1 = (CircleImageView) findViewById(R.id.jugador_5_vis);

                                    Delantero_1_vis.setVisibility(View.VISIBLE);
                                    Delantero_1_vis.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_Delantero_1.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_Delantero_1);
                                } else if (xx == 5) {
                                    jugador_6 = id_jg;

                                     smartImage_Delantero_2 = (CircleImageView) findViewById(R.id.jugador_6_vis);

                                    Delantero_2_vis.setVisibility(View.VISIBLE);
                                    Delantero_2_vis.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_Delantero_2.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_Delantero_2);
                                } else if (xx == 6) {
                                    jugador_7 = id_jg;

                                     smartImage_jugador7 = (CircleImageView) findViewById(R.id.jugador_7_vis);

                                    jugador7_vis.setVisibility(View.VISIBLE);
                                    jugador7_vis.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_jugador7.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_jugador7);
                                } else if (xx == 7) {
                                    jugador_8 = id_jg;

                                     smartImage_jugador8 = (CircleImageView) findViewById(R.id.jugador_8_vis);

                                    jugador8_vis.setVisibility(View.VISIBLE);
                                    jugador8_vis.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_jugador8.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_jugador8);
                                } else if (xx == 8) {
                                    jugador_9 = id_jg;

                                     smartImage_jugador9 = (CircleImageView) findViewById(R.id.jugador_9_vis);

                                    jugador9_vis.setVisibility(View.VISIBLE);
                                    jugador9_vis.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_jugador9.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_jugador9);
                                }

                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void TraerEquipo_2() {

        id_jugador_equipo.clear();
        nombre_jugador_equipo.clear();
        posicion_jugador_equipo.clear();
        logo_jugador.clear();
        estado.clear();


        final ProgressDialog progressDialog = new ProgressDialog(Partidos.this);
        progressDialog.setMessage("Cargardo...");
        progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/consultar_jugadores2_partido.php?id_equipo="+id_equipo_1+"&id_partido="+id_partido, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        def=0;
                        del=0;
                        for (int i=0;i<jsonArray.length();i++) {

                            id_jugador_equipo.add(jsonArray.getJSONObject(i).getString("usuario_id"));
                            nombre_jugador_equipo.add(jsonArray.getJSONObject(i).getString("nombre"));
                            posicion_jugador_equipo.add(jsonArray.getJSONObject(i).getString("posicion"));
                            logo_jugador.add(jsonArray.getJSONObject(i).getString("foto"));

                            estado.add(jsonArray.getJSONObject(i).getString("Status_equipo_2"));

                            if (i==0){
                                String tengo;
                                tengo = (estado.get(i).toString());
                                if (tengo.equals("Desactivado")){
                                    local.setChecked(false);
                                }else  if (tengo.equals("Activado")){
                                    local.setChecked(true);
                                }
                            }

                        }

                        String id,id_com;
                        int xx=0;
                        for(int x=0;x<ids_judores_2.size();x++) {
                            id = (ids_judores_2.get(x).toString());

                            for (int i = 0; i < id_jugador_equipo.size(); i++) {
                                id_com = (id_jugador_equipo.get(i).toString());

                                if (id.equals(id_com)) {
                                    xx = x;
                                    id_jg = (id_jugador_equipo.get(i).toString());
                                    non_jg = (nombre_jugador_equipo.get(i).toString());
                                    pos_jg = (posicion_jugador_equipo.get(i).toString());
                                    logo_jg = (logo_jugador.get(i).toString());
                                }

                                if (xx == 0) {
                                    jugador_1="null";
                                    jugador_1 = id_jg;


                                    portero.setText(non_jg);

                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_portero.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil2)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_portero);

                                } else if (xx == 1) {
                                    jugador_2="null";
                                    jugador_2 = id_jg;



                                    Defensa_1.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_Defensa_1.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil2)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_Defensa_1);

                                }else if (xx == 2) {
                                    jugador_3="null";
                                    jugador_3 = id_jg;


                                    Defensa_2.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_Defensa_2.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil2)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .into(smartImage_Defensa_2);

                                }else  if (xx == 3) {
                                    jugador_4="null";
                                    jugador_4 = id_jg;



                                    Medio.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_Medio.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil2)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_Medio);

                                }else  if (xx == 4) {
                                    jugador_5="null";
                                    jugador_5 = id_jg;



                                    Delantero_1.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_Delantero_1.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil2)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_Delantero_1);

                                }else  if (xx == 5) {
                                    jugador_6="null";
                                    jugador_6 = id_jg;



                                    Delantero_2.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_Delantero_2.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil2)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_Delantero_2);

                                }else  if (xx == 6) {
                                    jugador_7="null";
                                    jugador_7 = id_jg;



                                    jugador7.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_jugador7.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil2)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_jugador7);

                                } else  if (xx == 7) {
                                    jugador_8="null";
                                    jugador_8 = id_jg;



                                    jugador8.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_jugador8.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil2)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_jugador8);

                                }else  if (xx == 8) {
                                    jugador_9="null";
                                    jugador_9 = id_jg;



                                    jugador9.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_jugador9.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil2)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_jugador9);
                                }


                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void TraerEquipo_2_1() {

        id_jugador_equipo_vis.clear();
        nombre_jugador_equipo_vis.clear();
        posicion_jugador_equipo_vis.clear();
        logo_jugador_vis.clear();
        estado_vis.clear();


        final ProgressDialog progressDialog = new ProgressDialog(Partidos.this);
        progressDialog.setMessage("Cargardo...");
        progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/consultar_jugadores2_partido.php?id_equipo="+id_equipo_2+"&id_partido="+id_partido, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        def=0;
                        del=0;
                        for (int i=0;i<jsonArray.length();i++) {

                            id_jugador_equipo_vis.add(jsonArray.getJSONObject(i).getString("usuario_id"));
                            nombre_jugador_equipo_vis.add(jsonArray.getJSONObject(i).getString("nombre"));
                            posicion_jugador_equipo_vis.add(jsonArray.getJSONObject(i).getString("posicion"));
                            logo_jugador_vis.add(jsonArray.getJSONObject(i).getString("foto"));

                            estado_vis.add(jsonArray.getJSONObject(i).getString("Status_equipo_2"));

                            if (i==0){
                                String tengo;
                                tengo = (estado_vis.get(i).toString());
                                visitante.setEnabled(false);
                                if (tengo.equals("Desactivado")){
                                    visitante.setChecked(false);
                                }else  if (tengo.equals("Activado")){
                                    visitante.setChecked(true);
                                }
                            }

                        }

                        String id,id_com;
                        int xx=0;
                        for(int x=0;x<ids_judores_2.size();x++) {
                            id = (ids_judores_2.get(x).toString());

                            for (int i = 0; i < id_jugador_equipo_vis.size(); i++) {
                                id_com = (id_jugador_equipo_vis.get(i).toString());

                                if (id.equals(id_com)) {
                                    xx = x;
                                    id_jg = (id_jugador_equipo_vis.get(i).toString());
                                    non_jg = (nombre_jugador_equipo_vis.get(i).toString());
                                    pos_jg = (posicion_jugador_equipo_vis.get(i).toString());
                                    logo_jg = (logo_jugador_vis.get(i).toString());

                                }

                                if (xx == 0) {
                                    jugador_1 = id_jg;

                                    smartImage_portero = (CircleImageView) findViewById(R.id.jugador_1_vis);

                                    portero_vis.setVisibility(View.VISIBLE);
                                    portero_vis.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_portero.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil)
                                            .centerCrop()
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_portero);

                                } else if (xx == 1) {
                                    jugador_2 = id_jg;

                                    smartImage_Defensa_1 = (CircleImageView) findViewById(R.id.jugador_2_vis);

                                    Defensa_1_vis.setVisibility(View.VISIBLE);
                                    Defensa_1_vis.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_Defensa_1.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_Defensa_1);

                                } else if (xx == 2) {
                                    jugador_3 = id_jg;
                                    smartImage_Defensa_2 = (CircleImageView) findViewById(R.id.jugador_3_vis);

                                    Defensa_2_vis.setVisibility(View.VISIBLE);
                                    Defensa_2_vis.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_Defensa_2.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_Defensa_2);
                                } else if (xx == 3) {
                                    jugador_4 = id_jg;

                                    smartImage_Medio = (CircleImageView) findViewById(R.id.jugador_4_vis);

                                    Medio_vis.setVisibility(View.VISIBLE);
                                    Medio_vis.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_Medio.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_Medio);
                                } else if (xx == 4) {
                                    jugador_5 = id_jg;

                                    smartImage_Delantero_1 = (CircleImageView) findViewById(R.id.jugador_5_vis);

                                    Delantero_1_vis.setVisibility(View.VISIBLE);
                                    Delantero_1_vis.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_Delantero_1.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_Delantero_1);
                                } else if (xx == 5) {
                                    jugador_6 = id_jg;

                                    smartImage_Delantero_2 = (CircleImageView) findViewById(R.id.jugador_6_vis);

                                    Delantero_2_vis.setVisibility(View.VISIBLE);
                                    Delantero_2_vis.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_Delantero_2.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_Delantero_2);
                                } else if (xx == 6) {
                                    jugador_7 = id_jg;

                                    smartImage_jugador7 = (CircleImageView) findViewById(R.id.jugador_7_vis);

                                    jugador7_vis.setVisibility(View.VISIBLE);
                                    jugador7_vis.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_jugador7.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_jugador7);
                                } else if (xx == 7) {
                                    jugador_8 = id_jg;

                                    smartImage_jugador8 = (CircleImageView) findViewById(R.id.jugador_8_vis);

                                    jugador8_vis.setVisibility(View.VISIBLE);
                                    jugador8_vis.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_jugador8.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_jugador8);
                                } else if (xx == 8) {
                                    jugador_9 = id_jg;

                                    smartImage_jugador9 = (CircleImageView) findViewById(R.id.jugador_9_vis);

                                    jugador9_vis.setVisibility(View.VISIBLE);
                                    jugador9_vis.setText(non_jg);
                                    String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+logo_jg;
                                    Glide.with(smartImage_jugador9.getContext())
                                            .load(URL_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .placeholder(R.drawable.perfil)
                                            .signature(new StringSignature(URL_2))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(smartImage_jugador9);
                                }


                            }
                        }





                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void Arquero(View v) {

       if (jugador_1 != "Null"){
           if (compa==0){
               dato_user=jugador_1;

               if (equipo_uno.equals(tu_equipo)){
                   campo="jg_1_cap";
               }else {
                   campo="jg_10_cap";
               }
               link();
           }else{
               dato_user=jugador_1;
               if (equipo_uno.equals(tu_equipo)){
                   campo="jg_1_cap";
               }else {
                   campo="jg_10_cap";
               }
               link_2();
           }
       }else{
           if (dato.equals("mitab1")){
               ir_a_fichar();
           }

       }
    }
    public void Defensa_1(View v) {

        if (jugador_2 != "Null"){
            if (compa==0){
                dato_user=jugador_2;

                if (equipo_uno.equals(tu_equipo)){
                    campo="jg_2";
                }else {
                    campo="jg_11";
                }
                link();
            }else{
                dato_user=jugador_2;
                if (equipo_uno.equals(tu_equipo)){
                    campo="jg_2";
                }else {
                    campo="jg_11";
                }
                link_2();
            }
        }else{
            if (dato.equals("mitab1")){
                ir_a_fichar();
            }
        }



    }
    public void Defensa_2(View v) {

        if (jugador_3 != "Null"){
            if (compa == 0) {
                dato_user = jugador_3;

                if (equipo_uno.equals(tu_equipo)) {
                    campo = "jg_3";
                } else {
                    campo = "jg_12";
                }
                link();
            } else {
                dato_user = jugador_3;
                if (equipo_uno.equals(tu_equipo)) {
                    campo = "jg_3";
                } else {
                    campo = "jg_12";
                }
                link_2();
            }
        }else{
            if (dato.equals("mitab1")){
                ir_a_fichar();
            }
        }

    }
    public void Medio(View v) {

        if (jugador_4 != "Null"){
            if (compa==0){
                dato_user = jugador_4;

                if (equipo_uno.equals(tu_equipo)) {
                    campo = "jg_4";
                } else {
                    campo = "jg_13";
                }
                link();
            } else {
                dato_user = jugador_4;
                if (equipo_uno.equals(tu_equipo)) {
                    campo = "jg_4";
                } else {
                    campo = "jg_13";
                }
                link_2();
            }
        /*}else if (compa==1){
            dato_user = jugador_4;
            if (equipo_uno.equals(tu_equipo)) {
                campo = "jg_4";
            } else {
                campo = "jg_13";
            }


            link_2();*/

        }else {
            if (dato.equals("mitab1")){
                ir_a_fichar();
            }
        }

    }
    public void Delantero_1(View v) {
        if (jugador_5 != "Null"){
            if (compa==0){
                dato_user = jugador_5;

                if (equipo_uno.equals(tu_equipo)) {
                    campo = "jg_5";
                } else {
                    campo = "jg_14";
                }
                link();
            } else {
                dato_user = jugador_5;
                if (equipo_uno.equals(tu_equipo)) {
                    campo = "jg_5";
                } else {
                    campo = "jg_14";
                }
                link_2();
            }
        }else {
            if (dato.equals("mitab1")){
                ir_a_fichar();
            }
        }


    }
    public void Delantero_2(View v) {

        if (jugador_6 != "Null"){
            if (compa==0){
                dato_user = jugador_6;

                if (equipo_uno.equals(tu_equipo)) {
                    campo = "jg_6";
                } else {
                    campo = "jg_15";
                }
                link();
            } else {
                dato_user = jugador_6;
                if (equipo_uno.equals(tu_equipo)) {
                    campo = "jg_6";
                } else {
                    campo = "jg_15";
                }
                link_2();
            }
        }else{
            if (dato.equals("mitab1")){
                ir_a_fichar();
            }
        }

    }
    public void jufador_7(View v) {
        if (jugador_7 != "Null"){
            if (compa==0){
                dato_user = jugador_7;

                if (equipo_uno.equals(tu_equipo)) {
                    campo = "jg_7";
                } else {
                    campo = "jg_16";
                }
                link();
            } else {
                dato_user = jugador_7;
                if (equipo_uno.equals(tu_equipo)) {
                    campo = "jg_7";
                } else {
                    campo = "jg_16";
                }
                link_2();
            }
        }else{
            if (dato.equals("mitab1")){
                ir_a_fichar();
            }
        }

    }
    public void jugador_8(View v) {
        if (jugador_8 != "Null"){
            if (compa==0){
                dato_user = jugador_8;

                if (equipo_uno.equals(tu_equipo)) {
                    campo = "jg_8";
                } else {
                    campo = "jg_17";
                }
                link();
            } else {
                dato_user = jugador_8;
                if (equipo_uno.equals(tu_equipo)) {
                    campo = "jg_8";
                } else {
                    campo = "jg_17";
                }
                link_2();
            }
        }else{
            if (dato.equals("mitab1")){
                ir_a_fichar();
            }
        }

    }
    public void jugador_9(View v) {
        if (jugador_9 != "Null"){
            if (compa==0){
                dato_user = jugador_9;

                if (equipo_uno.equals(tu_equipo)) {
                    campo = "jg_9";
                } else {
                    campo = "jg_18";
                }
                link();
            } else {
                dato_user = jugador_9;
                if (equipo_uno.equals(tu_equipo)) {
                    campo = "jg_9";
                } else {
                    campo = "jg_18";
                }
                link_2();
            }
        }else{
            if (dato.equals("mitab1")){
                ir_a_fichar();
            }
        }

    }

    private void ir_a_fichar() {

        Intent intent = new Intent(Partidos.this, Fichajes.class);
        intent.putExtra("id_equipo", tu_equipo);
        intent.putExtra("dato", "Dos");
        intent.putExtra("id_equipo_1", id_equipo_1);
        intent.putExtra("id_equipo_2", id_equipo_2);
        intent.putExtra("id_partido", id_partido);
        intent.putExtra("capitan", capitane);
        intent.putExtra("sexo", sexo_usuario);

        intent.putExtra("latitud_cancha", latitud_cancha);
        intent.putExtra("longitud_cancha", longitud_cancha);

        startActivity(intent);

    }

    public void link(){
        new Partidos.ConsultarDatos_user().execute("http://www.sacalapp.com/datos_fichajes_.php?email_usu="+dato_user);
    }

    public void link_2(){
/*
        if (dato_user.equals("Null")){
            new Partidos.CargarDato().execute("http://www.sacalapp.com/sustitucion_2.php?campo_1="+campo2+"&id_jugador_2="+dato_user+"&campo_2="+campo+"&id_jugador_1="+dato_user_2+"&id_partido="+id_partido);

        }else {
            new Partidos.ConsultarDatos_user_cam().execute("http://www.sacalapp.com/datos_fichajes.php?email_usu="+dato_user);
        }
*/

        new Partidos.ConsultarDatos_user_cam().execute("http://www.sacalapp.com/datos_fichajes._php?email_usu="+dato_user);


    }

    public void Dialogo_info_usuario(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Partidos.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.partido_datos,null);

        builder.setView(dialogView);

        TextView nomb =(TextView)dialogView.findViewById(R.id.lbl_nombre_fichaje);
        TextView posicionn =(TextView)dialogView.findViewById(R.id.lbl_posi_fichajes);
        TextView nick_2 =(TextView)dialogView.findViewById(R.id.lbl_nick_fichajes);
        TextView edad_2 =(TextView)dialogView.findViewById(R.id.lbl_eda_fichajes);
        TextView ciudad_2 =(TextView)dialogView.findViewById(R.id.lbl_ciuidad_fichajes);
        RatingBar ratinCali_2 = (RatingBar)dialogView.findViewById(R.id.ratingBar_calificacion_fichajes);
        CircleImageView imagen_fichaje =(CircleImageView)dialogView.findViewById(R.id.ima_fichajes);
        ImageView imagen_pierna =(ImageView)dialogView.findViewById(R.id.smartImageView_perfil);
        ImageView level =(ImageView)dialogView.findViewById(R.id.smartImageView_nivel2);
        AppCompatButton fic_inv = (AppCompatButton)dialogView.findViewById(R.id.fic_inv);
        AppCompatButton btn_Salir = (AppCompatButton)dialogView.findViewById(R.id.btn_Salir);

        nomb.setText(nombre2);
        posicionn.setText(posicion2);
        edad_2.setText(edad2+" años");
        ciudad_2.setText(ciudad2);


        String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+foto_user;
        Glide.with(imagen_fichaje.getContext())
                .load(URL_2)
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.perfil)
                .signature(new StringSignature(URL_2))
                .into(imagen_fichaje);

        /*String urlfinal="http://sacalapp.com/jarvicf/"+foto_user.toString();
        Rect rect = new Rect(imagen_fichaje.getLeft(),imagen_fichaje.getTop(),imagen_fichaje.getRight(),imagen_fichaje.getBottom());
        imagen_fichaje.setImageUrl(urlfinal,rect);*/


        String nombre_;
        if (perfil.equals("Derecha")) {
            nombre_ = "ic_derecha";
        } else {
            nombre_ = "ic_izquierda";
        }
        String recurso_ = "drawable";
        int res_imagen_ = getResources().getIdentifier(nombre_, recurso_, getPackageName());
        imagen_pierna.setImageResource(res_imagen_);

        if (Level.equals("Recreacion")){
            level.setImageResource(R.drawable.ic_recreacion);
        }else if (Level.equals("Amateur")){
            level.setImageResource(R.drawable.ic_amateur);
        }else if (Level.equals("SemiPro")){
            level.setImageResource(R.drawable.ic_semipro);
        }else if (Level.equals("Pro")){
            level.setImageResource(R.drawable.ic_pro);
        }else if (Level.equals("Leyenda")){
            level.setImageResource(R.drawable.ic_leyenda);
        }else if (Level.equals("null")){
            //lvl.setImageResource(R.drawable.ic_sin);
        }


        Float cali_2=Float.parseFloat(califica2);
        ratinCali_2.setRating(cali_2);


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

                nombre3=nombre2;
                califica3=califica2;
                foto_user3=foto_user;
                posicion3=posicion2;
                dato_user_2=dato_user;
                campo2=campo;

                compa=1;
                toast();
                //Toast.makeText(Partidos.this, "Seleciona el otro jugador...", Toast.LENGTH_LONG).show();

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void Dialogo_info_usuario_par(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Partidos.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.partido_datos,null);

        builder.setView(dialogView);

        TextView nomb =(TextView)dialogView.findViewById(R.id.lbl_nombre_fichaje);
        TextView posicionn =(TextView)dialogView.findViewById(R.id.lbl_posi_fichajes);
        TextView nick_2 =(TextView)dialogView.findViewById(R.id.lbl_nick_fichajes);
        TextView edad_2 =(TextView)dialogView.findViewById(R.id.lbl_eda_fichajes);
        TextView ciudad_2 =(TextView)dialogView.findViewById(R.id.lbl_ciuidad_fichajes);
        RatingBar ratinCali_2 = (RatingBar)dialogView.findViewById(R.id.ratingBar_calificacion_fichajes);
        CircleImageView imagen_fichaje =(CircleImageView)dialogView.findViewById(R.id.ima_fichajes);
        ImageView imagen_pierna =(ImageView)dialogView.findViewById(R.id.smartImageView_perfil);
        AppCompatButton fic_inv = (AppCompatButton)dialogView.findViewById(R.id.fic_inv);
        AppCompatButton btn_Salir = (AppCompatButton)dialogView.findViewById(R.id.btn_Salir);

        nomb.setText(nombre2);
        posicionn.setText(posicion2);
        edad_2.setText(edad2+" años");
        ciudad_2.setText(ciudad2);


        String urlfinal="http://sacalapp.com/jarvicf/img_users/"+foto_user.toString();
        Glide.with(imagen_fichaje.getContext())
                .load(urlfinal)
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.perfil)
                .crossFade()
                .signature(new StringSignature(urlfinal))
                .into(imagen_fichaje);


        String nombre_1="escudo_"+ perfil;
        String recurso1="drawable";
        int res_imagen1 = getResources().getIdentifier(nombre_1, recurso1,getPackageName());
        imagen_pierna.setImageResource(res_imagen1);

        fic_inv.setText("Dejar Partido");

        Float cali_2=Float.parseFloat(califica2);
        ratinCali_2.setRating(cali_2);


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

                if (tu_equipo.equals(equipo_uno)){

                    new Partidos.ConsultarDatos_equipo_1().execute("http://www.sacalapp.com/ids_equipo_1.php?id_equipo_1="+equipo_uno+"&id_partido="+id_partido);
                   // Toast.makeText(Partidos.this, "Salir partido, equipo1", Toast.LENGTH_LONG).show();
                }else {
                    new Partidos.ConsultarDatos_equipo_2().execute("http://www.sacalapp.com/ids_equipo_2.php?id_equipo_2="+equipo_dos+"&id_partido="+id_partido);
                   // Toast.makeText(Partidos.this, "Salir partido, equipo2", Toast.LENGTH_LONG).show();
                }

                dialog.dismiss();

            }
        });
        dialog.show();
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


                    jugador_2 = (ja.getString(1));
                    jugador_3 = (ja.getString(2));
                    jugador_4 = (ja.getString(3));
                    jugador_5 = (ja.getString(4));
                    jugador_6 = (ja.getString(5));
                    jugador_7 = (ja.getString(6));
                    jugador_8 = (ja.getString(7));
                    jugador_9 = (ja.getString(8));

                    if (jugador_2.equals(id_usuario)) {
                        campo = "jg_2";
                    } else if (jugador_3.equals(id_usuario)) {
                        campo = "jg_3";
                    } else if (jugador_4.equals(id_usuario)) {
                        campo = "jg_4";
                    } else if (jugador_5.equals(id_usuario)) {
                        campo = "jg_5";
                    } else if (jugador_6.equals(id_usuario)) {
                        campo = "jg_6";
                    } else if (jugador_7.equals(id_usuario)) {
                        campo = "jg_7";
                    } else if (jugador_8.equals(id_usuario)) {
                        campo = "jg_8";
                    } else if (jugador_9.equals(id_usuario)) {
                        campo = "jg_9";
                    } else {
                        campo = "lleno";
                    }

                new Partidos.CargarDato_estado().execute("http://www.sacalapp.com/actualizar_usuario_partido.php?id_usuario="+id_usuario+"&campo="+campo+"&id_partido="+id_partido);









            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Partidos.this, "Error", Toast.LENGTH_LONG).show();

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

                    jugador_2 = (ja.getString(1));
                    jugador_3 = (ja.getString(2));
                    jugador_4 = (ja.getString(3));
                    jugador_5 = (ja.getString(4));
                    jugador_6 = (ja.getString(5));
                    jugador_7 = (ja.getString(6));
                    jugador_8 = (ja.getString(7));
                    jugador_9 = (ja.getString(8));

                    if (jugador_2.equals(id_usuario)) {
                        campo = "jg_11";
                    } else if (jugador_3.equals(id_usuario)) {
                        campo = "jg_12";
                    } else if (jugador_4.equals(id_usuario)) {
                        campo = "jg_13";
                    } else if (jugador_5.equals(id_usuario)) {
                        campo = "jg_14";
                    } else if (jugador_6.equals(id_usuario)) {
                        campo = "jg_15";
                    } else if (jugador_7.equals(id_usuario)) {
                        campo = "jg_16";
                    } else if (jugador_8.equals(id_usuario)) {
                        campo = "jg_17";
                    } else if (jugador_9.equals(id_usuario)) {
                        campo = "jg_18";
                    } else {
                        campo = "lleno";
                    }

                new Partidos.CargarDato_estado().execute("http://www.sacalapp.com/actualizar_usuario_partido.php?id_usuario="+id_usuario+"&campo="+campo+"&id_partido="+id_partido);



            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Partidos.this, "Error", Toast.LENGTH_LONG).show();

            }

        }
    }

    public void Dialogo_info_usuario_(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Partidos.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.partido_datos_2,null);

        builder.setView(dialogView);

        TextView nomb =(TextView)dialogView.findViewById(R.id.lbl_nombre_fichaje);
        TextView posicionn =(TextView)dialogView.findViewById(R.id.lbl_posi_fichajes);
        TextView nick_2 =(TextView)dialogView.findViewById(R.id.lbl_nick_fichajes);
        TextView edad_2 =(TextView)dialogView.findViewById(R.id.lbl_eda_fichajes);
        TextView ciudad_2 =(TextView)dialogView.findViewById(R.id.lbl_ciuidad_fichajes);
        RatingBar ratinCali_2 = (RatingBar)dialogView.findViewById(R.id.ratingBar_calificacion_fichajes);
        CircleImageView imagen_fichaje =(CircleImageView) dialogView.findViewById(R.id.ima_fichajes);
        ImageView imagen_pierna =(ImageView)dialogView.findViewById(R.id.smartImageView_perfil2);
        ImageView level =(ImageView)dialogView.findViewById(R.id.smartImageView_nivel2);

        AppCompatButton btn_Salir = (AppCompatButton)dialogView.findViewById(R.id.btn_Salir);



        nomb.setText(nombre2);
        posicionn.setText(posicion2);
        edad_2.setText(edad2+" años");
        ciudad_2.setText(ciudad2);



        String URL_2 = "http://sacalapp.com/jarvicf/img_users/"+foto_user;
        Glide.with(imagen_fichaje.getContext())
                .load(URL_2)
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.perfil)
                .signature(new StringSignature(URL_2))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imagen_fichaje);



        String nombre_;
        if (perfil.equals("Derecha")) {
            nombre_ = "ic_derecha";
        } else {
            nombre_ = "ic_izquierda";
        }
        String recurso_ = "drawable";
        int res_imagen_ = getResources().getIdentifier(nombre_, recurso_, getPackageName());
        imagen_pierna.setImageResource(res_imagen_);

        if (Level.equals("Recreacion")){
            level.setImageResource(R.drawable.ic_recreacion);
        }else if (Level.equals("Amateur")){
            level.setImageResource(R.drawable.ic_amateur);
        }else if (Level.equals("SemiPro")){
            level.setImageResource(R.drawable.ic_semipro);
        }else if (Level.equals("Pro")){
            level.setImageResource(R.drawable.ic_pro);
        }else if (Level.equals("Leyenda")){
            level.setImageResource(R.drawable.ic_leyenda);
        }else if (Level.equals("null")){
            //lvl.setImageResource(R.drawable.ic_sin);
        }

        Float cali_2=Float.parseFloat(califica2);
        ratinCali_2.setRating(cali_2);

        final AlertDialog dialog = builder.create();

        btn_Salir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void Dialogo_info_usuario_cam(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Partidos.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.sustitucion,null);

        builder.setView(dialogView);

        TextView jugador_out =(TextView)dialogView.findViewById(R.id.lbl_nombre_sus_1);
        TextView jugador_int =(TextView)dialogView.findViewById(R.id.lbl_nombre_sus_2);

        RatingBar ratinCali_out = (RatingBar)dialogView.findViewById(R.id.ratingBar_sus_1);
        RatingBar ratinCali_in = (RatingBar)dialogView.findViewById(R.id.ratingBar_sus_2);

        SmartImageView imagen_out =(SmartImageView)dialogView.findViewById(R.id.ima_sus_1);
        SmartImageView imagen_in =(SmartImageView)dialogView.findViewById(R.id.ima_sus_2);

        AppCompatButton fic_inv = (AppCompatButton)dialogView.findViewById(R.id.btn_Sustituir);
        AppCompatButton btn_Salir = (AppCompatButton)dialogView.findViewById(R.id.btn_cancelar);


        jugador_out.setText(nombre3);
        jugador_int.setText(nombre2);

        String urlfinal="http://sacalapp.com/jarvicf/"+foto_user3.toString();
        Rect rect = new Rect(imagen_out.getLeft(),imagen_out.getTop(),imagen_out.getRight(),imagen_out.getBottom());
        imagen_out.setImageUrl(urlfinal,rect);

        String urlfinal2="http://sacalapp.com/jarvicf/"+foto_user.toString();
        Rect rect2 = new Rect(imagen_in.getLeft(),imagen_in.getTop(),imagen_in.getRight(),imagen_in.getBottom());
        imagen_in.setImageUrl(urlfinal2,rect2);


        Float cali_2=Float.parseFloat(califica3);
        ratinCali_out.setRating(cali_2);

        Float cali_3=Float.parseFloat(califica2);
        ratinCali_in.setRating(cali_3);


        final AlertDialog dialog = builder.create();

        btn_Salir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        fic_inv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (campo.equals("yo")){
                    if (campo2.equals("yo")){

                    }
                }
                compa=0;
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void toast(){
        Toast toast3 = new Toast(getApplicationContext());

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_sustitucion,
                (ViewGroup) findViewById(R.id.lytLayout));

        TextView juga_1 = (TextView)layout.findViewById(R.id.lbl_juga_1);
        TextView juga_2 = (TextView)layout.findViewById(R.id.lbl_juga_2);


        juga_1.setText(nombre3);
        if (compa==0){
            juga_2.setText("Por...");
        }else if (compa==2){
            juga_2.setText(nombre2);
        }


        toast3.setGravity(Gravity.BOTTOM, 0, 0);
        toast3.setDuration(Toast.LENGTH_LONG);
        toast3.setView(layout);
        toast3.show();

    }

    private class Consultarid extends AsyncTask<String, Void, String> {
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
                ids_judores_1.clear();

                if (ja != null) {
                    for (int i=0;i<ja.length();i++){
                        ids_judores_1.add(ja.getString(i));
                    }
                }

            /*    portero.setText("invitar");
                Defensa_1.setText("invitar");
                Defensa_2.setText("invitar");
                Medio.setText("invitar");
                Delantero_1 .setText("invitar");
                Delantero_2 .setText("invitar");
                jugador7.setText("invitar");
                jugador8.setText("invitar");
                jugador9 .setText("invitar");

                reset();*/

                if (dato.equals("mitab1")){
                    TraerEquipo();
                }else if (dato.equals("mitab3")){
                    TraerEquipo_1();
                }



            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Partidos.this, "Error Conexion a internet, vuelve a intentarlo", Toast.LENGTH_LONG).show();
            }

        }
    }

    private class Consultarid_2 extends AsyncTask<String, Void, String> {
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
                ids_judores_2.clear();

                if (ja != null) {
                    for (int i=0;i<ja.length();i++){
                        ids_judores_2.add(ja.getString(i));
                    }
                }
                if (dato.equals("mitab1")){
                    TraerEquipo_2();
                }else if (dato.equals("mitab3")){
                    TraerEquipo_2_1();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Partidos.this, "Error Conexion a internet, vuelve a intentarlo", Toast.LENGTH_LONG).show();
            }

        }
    }

    private class ConsultarDatos_user extends AsyncTask<String, Void, String> {
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
                ciudad2=(ja.getString(4));
                edad2=(ja.getString(5));
                califica2=(ja.getString(6));
                foto_user=(ja.getString(7));
                perfil=(ja.getString(9));
                Level=(ja.getString(10));

                if (dato.equals("mitab1")){

                    if(capitane.equals(id_usuario)){
                        Dialogo_info_usuario();
                    }else{

                        if (dato_user.equals(id_usuario)){
                            Dialogo_info_usuario_par();
                        }else {

                            Dialogo_info_usuario_();
                        }

                    }

                }else if (dato.equals("mitab3")){
                    Dialogo_info_usuario_();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Partidos.this, "Error", Toast.LENGTH_LONG).show();

            }

        }
    }

    private class ConsultarDatos_user_cam extends AsyncTask<String, Void, String> {
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
                califica2=(ja.getString(6));
                foto_user=(ja.getString(7));


                if (dato_user_2.equals(dato_user)){
                    Toast.makeText(Partidos.this, "No puedes cambiar al mismo jugador", Toast.LENGTH_LONG).show();
                }else {
                    new Partidos.CargarDato().execute("http://www.sacalapp.com/sustitucion.php?campo_1="+campo2+"&id_jugador_2="+dato_user+"&campo_2="+campo+"&id_jugador_1="+dato_user_2+"&id_partido="+id_partido);

                }




            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Partidos.this, "Error", Toast.LENGTH_LONG).show();

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
            compa=2;
          toast();
            compa=0;


            if (equipo_uno.equals(tu_equipo)){
                new Partidos.Consultarid().execute("http://www.sacalapp.com/ids_equipo_1.php?id_equipo_1="+id_equipo_1+"&id_partido="+id_partido);
            } else if (equipo_dos.equals(tu_equipo)){
                new Partidos.Consultarid_2().execute("http://www.sacalapp.com/ids_equipo_2.php?id_equipo_2="+id_equipo_1+"&id_partido="+id_partido);
            }
        }
    }

    private class CargarDato_estado extends AsyncTask<String, Void, String> {
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

           /* if (dato_user.equals(id_usuario)){
                Intent intent = new Intent(Partidos.this, Perfil.class);
                startActivity(intent);
                finish();
            }else {

            }*/



        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.partidos_2, menu);
        menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        traer_notificaciones_partido();
        //

        final View notificaitons = menu.findItem(R.id.notificaciones).getActionView();

        txtViewCount = (TextView) notificaitons.findViewById(R.id.txtCount);
        txtViewCount.setVisibility(View.INVISIBLE);

        notificaitons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 ir_notificacio();
            }
        });

        // return true;
        return super.onCreateOptionsMenu(menu);

    }

    private void ir_notificacio() {


        Intent intent = new Intent(Partidos.this, Notificacion_partido.class);
        intent.putExtra("id_equipo_1", id_equipo_1);
        intent.putExtra("id_equipo_2", id_equipo_2);
        intent.putExtra("tu_equipo", tu_equipo);
        intent.putExtra("capitan", capitane);
        intent.putExtra("id_usuario", id_usuario);
        intent.putExtra("id_partido", id_partido);
        intent.putExtra("logo", logo_equipo_1);
        startActivity(intent);
    }

    private void ir_chat() {


        Intent intent = new Intent(Partidos.this, Chat_capi_equipos.class);

        intent.putExtra("tu_equipo", tu_equipo);
        intent.putExtra("id_usuario", id_usuario);
        intent.putExtra("id_partido", id_partido);

        startActivity(intent);
    }

    public void updateHotCount(final int new_hot_number) {
        count = new_hot_number;
        if (count < 0) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (count == 0)
                    txtViewCount.setVisibility(View.INVISIBLE);
                else {
                    txtViewCount.setVisibility(View.VISIBLE);
                    txtViewCount.setText(Integer.toString(count));
                    // supportInvalidateOptionsMenu();
                }
            }
        });
    }

    private void traer_notificaciones_partido() {

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/notificacion_equipo_partidos.php?id_equipo="+id_equipo_1+"&id_partido="+id_partido, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        count=jsonArray.length();

                        updateHotCount(count);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Chat_capitanes:

                if (capitane.equals(id_usuario)){
                ir_chat();
                }

                return true;

            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void initUI() {

        linearLayout2 = (LinearLayout) findViewById(R.id.ll2);
        tvDay = (TextView) findViewById(R.id.txtTimerDay);
        tvHour = (TextView) findViewById(R.id.txtTimerHour);
        tvMinute = (TextView) findViewById(R.id.txtTimerMinute);
        tvSecond = (TextView) findViewById(R.id.txtTimerSecond);

    }

    // //////////////COUNT DOWN START/////////////////////////
    public void countDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss");
                    // Here Set your Event Date
                    Date eventDate = dateFormat.parse(fecha);
                    
                    Date currentDate = new Date();


                    if (!currentDate.after(eventDate)) {
                        long diff = eventDate.getTime()
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;

                        tvDay.setText("" + String.format("%02d", days));
                        tvHour.setText("" + String.format("%02d", hours));
                        tvMinute.setText("" + String.format("%02d", minutes));
                        tvSecond.setText("" + String.format("%02d", seconds));
                    } else {
                        linearLayout1.setVisibility(View.VISIBLE);
                        linearLayout2.setVisibility(View.GONE);
                        tvEvent.setText("Android Event Start");
                        handler.removeCallbacks(runnable);
                        // handler.removeMessages(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
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

               fecha_partido=(ja.getString(0).toString());
                hora_partido=(ja.getString(1).toString());
                String conver_2=(ja.getString(0).toString());
                String conver=(ja.getString(1).toString());
                cancha=(ja.getString(2).toString());
                equipo_uno=(ja.getString(3).toString());
                equipo_dos=(ja.getString(4).toString());
                nombre_cancha=(ja.getString(5).toString());
                direccion_cancha=(ja.getString(6).toString());
                cancha_imagen=(ja.getString(7).toString());
                cancha_telefonos=(ja.getString(8).toString());
                cancha_email=(ja.getString(9).toString());
                ciudad_complejo=(ja.getString(10).toString());
                latitud_cancha=(ja.getString(11).toString());
                longitud_cancha=(ja.getString(12).toString());


                fecha=fecha_partido+"  "+hora_partido+":00";

                if (conver.equals("8:00")){
                    lb_hora.setText("800AM");
                }else  if (conver.equals("9:00")){
                    lb_hora.setText("9:00AM");
                }else if (conver.equals("10:00")){
                    lb_hora.setText("10:00AM");
                }else  if (conver.equals("11:00")){
                    lb_hora.setText("11:00AM");
                }else  if (conver.equals("12:00")){
                    lb_hora.setText("12:00PM");
                }else if (conver.equals("13:00")){
                   lb_hora.setText("1:00PM");
                }else  if (conver.equals("14:00")){
                   lb_hora.setText("2:00PM");
               }else  if (conver.equals("15:00")){
                   lb_hora.setText("3:00PM");
               }else  if (conver.equals("16:00")){
                   lb_hora.setText("4:00PM");
               }else  if (conver.equals("17:00")){
                   lb_hora.setText("5:00PM");
               }else  if (conver.equals("18:00")){
                   lb_hora.setText("6:00PM");
               }else  if (conver.equals("19:00")){
                   lb_hora.setText("7:00PM");
               }else  if (conver.equals("20:00")){
                   lb_hora.setText("8:00PM");
               }else  if (conver.equals("21:00")){
                   lb_hora.setText("9:00PM");
               }else  if (conver.equals("22:00")){
                   lb_hora.setText("10:00PM");
               }else  if (conver.equals("23:00")){
                   lb_hora.setText("11:00PM");
               }else  if (conver.equals("24:00")){
                   lb_hora.setText("12:00PM");
               }



                String[] dias={"Domingo","Lunes","Martes", "Miércoles","Jueves","Viernes","Sábado"};
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date eventDate = dateFormat.parse(conver_2);
                Date hoy=eventDate;
                int numeroDia=0;
                Calendar cal= Calendar.getInstance();
                cal.setTime(hoy);
                numeroDia=cal.get(Calendar.DAY_OF_WEEK);
                lb_frcha.setText(conver_2);
                lb_dia.setText(dias[numeroDia - 1]);





                if (fecha.equals("dasa")){//2017-03-16 20:00:00

                }

                    mostrar();



            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Partidos.this, "Error", Toast.LENGTH_LONG).show();

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    private void mostrar() {

        String urlfinal = "http://sacalapp.com/jarvicf/canchas_img/" + cancha_imagen;
        Rect rect = new Rect(logo_cancha.getLeft(), logo_cancha.getTop(), logo_cancha.getRight(), logo_cancha.getBottom());
        logo_cancha.setImageUrl(urlfinal, rect);

        //nombre_cancha_ju.setText(nombre_cancha);
        nombre_cancha_comple.setText(nombre_cancha);
        direccion_complejo.setText(direccion_cancha);
        telefono_complejo.setText(cancha_telefonos);
        email_complejo.setText(cancha_email);
        canju.setText(cancha);
        ciudad_com.setText(ciudad_complejo);
        //hora.setText(hora_partido);ç

        for (int i=0;i<3.;i++) {
            listView_fotos.setAdapter(new Partidos.ImageAdater(getApplicationContext()));
        }


        initUI();
        countDownStart();
    }

    private class ImageAdater extends BaseAdapter {


        RatingBar ratinCali;
        Context ctx;
        LayoutInflater layoutInflater;
        ImageView imd_d;
        SmartImageView smartImageView;
        TextView tposicion,tedad,tnick,tciudad;
        float cali;


        public ImageAdater(Context applicationContext){

            this.ctx=applicationContext;
            layoutInflater=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return 1;
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

            ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.img_complemento,null);


            smartImageView =(SmartImageView)viewGroup.findViewById(R.id.smartImageView2);

            /*String urlfinal="http://sacalapp.com/jarvicf/complejo.png";
            Rect rect = new Rect(smartImageView.getLeft(),smartImageView.getTop(),smartImageView.getRight(),smartImageView.getBottom());
            smartImageView.setImageUrl(urlfinal,rect);*/


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

    @Override
    public void onPause() {
        super.onPause();
        startService(new Intent(Partidos.this,
                Notificaciones.class));
    }

    @Override
    public void onStop() {
        super.onStop();
        startService(new Intent(Partidos.this, Notificaciones.class));
    }

    @Override
    public void onRestart(){
        super.onRestart();

        stopService(new Intent(Partidos.this,
                Notificaciones.class));

    }

    public void info_tengo_chico_2(View v) {

        Mostrar_info_tengo_chico();

    }

    public void Mostrar_info_tengo_chico(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Partidos.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.info_tengo_chico_2,null);

        builder.setView(dialogView);

        Button one = (Button) dialogView.findViewById(R.id.btn_info_volver);


        final AlertDialog dialog = builder.create();

        one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

}
