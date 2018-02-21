package sacalapp.hay_chico;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.github.snowdream.android.widget.SmartImageView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
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
import java.util.GregorianCalendar;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class Equipo extends AppCompatActivity {
    public static final int segundo = 30;
    public static final int miliosegundo = segundo * 1000;

    int jugadores_lista=0;
    TextView selec_capitan;
    String Hora_oficial,Fecha_oficial,hora_ref,fecha_ref,estado;
    Date date_oficial_hora,date_partido_hora;
    SimpleDateFormat sdfConvert = new SimpleDateFormat("HH:mm");
    Date eventDate2,eventDate;
        ViewGroup viewGroup;
    private TextInputLayout tilMensaje;
    TextView nombre_equi,califi_equi,logo_equi,capitan,txtViewCount,txtViewCount_2,cantidad_jug,no_partidos;
    private String datos,id_equi,id_usuario="Null",logo_equipo,equipo_1_,equipo_1_logo_,nombre_equipo_="Null",equipo_2,dato_comparar,sexo_usuario="Null";
    int cantidad_jugadores=9;
    private String j2,j3,j4,j5,j6,j7,j8,j9,j10,j11,j12;
    ImageButton usuarios,eliminar;
    private ListView listView,list_partido_vs,lista_ceder;
    private SwipeRefreshLayout swipeContainer,swipeContainer_equipo;
    private ListView lv2Refresh, lv_equipo;
    Button salir;
    private String nombre2,posicion2,edad2,nick2,ciudad2,sexo2,foto_user,id_jugador_notificacion,nombre_equipo_q,capitane,nick_equipo,nombre_capi,enviar,campo,datoo="Null";
    private String califica2,ref_capi,tipo_partido,esta_eq,LEvel,Per;


    ArrayList foto=new ArrayList();
    ArrayList id_jugador_equipo=new ArrayList();
    ArrayList califi=new ArrayList();
    ArrayList nombre_jugador_equipo=new ArrayList();
    ArrayList posicion_jugador_equipo=new ArrayList();
    ArrayList fotoo=new ArrayList();


    ArrayList nombre_equipo=new ArrayList();
    ArrayList id_equipo=new ArrayList();
    ArrayList logo_equipoo=new ArrayList();
    ArrayList id_partido=new ArrayList();
    ArrayList id_reserva=new ArrayList();
    ArrayList hora_par=new ArrayList();
    ArrayList fecha_par=new ArrayList();

    ArrayList partidos_1=new ArrayList();
    ArrayList partidos_2=new ArrayList();


    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList id_msn= new ArrayList();
    ArrayList id_jugador_msn= new ArrayList();
    ArrayList cuerpo_msn= new ArrayList();
    ArrayList fecha_msn= new ArrayList();
    ArrayList hora_msn= new ArrayList();
    ArrayList id_nombre_msn= new ArrayList();
    ArrayList nick= new ArrayList();
    ArrayList estado1=new ArrayList();
    ArrayList usuario_cap=new ArrayList();

    ArrayList foto_2=new ArrayList();
    ArrayList nombre_equipo_v=new ArrayList();
    ArrayList id_equipo_v=new ArrayList();
    ArrayList logo_equipoo_v=new ArrayList();
    ArrayList id_partido_v=new ArrayList();
    ArrayList Id_reserva_=new ArrayList();
    ArrayList hora_par_v=new ArrayList();
    ArrayList fecha_par_v=new ArrayList();
    ArrayList nick_2= new ArrayList();
    ArrayList estado2=new ArrayList();
    ArrayList usuario_cap_2=new ArrayList();
    ArrayList Level=new ArrayList();

    private float promedio=0,promedio_total=0;

    int dato_1=0,dato_2=0,dato_3=0,dato_4=0,dato=0,result=0,count=0,count_2=0;
    int noti_1,noti_2,noti_3,noti_4,DATOS;

    Double hora_oficial,hora_partido;
    String fecha;
    int hora, minutos, segundos;
    SmartImageView smartImageView_equipo;
    RatingBar ratingBar;
    Calendar calendario = new GregorianCalendar();
    AdView mAdView_equipo,mAdView_partidos;
    ProgressDialog pd;
    ProgressBar progressBar,progressBar_partido;
    ImageView info_;
    ToggleButton toggleButton;
    AppCompatButton appCompatButton,appCompatButton2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_equipo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        appCompatButton=(AppCompatButton)findViewById(R.id.appCompatButton);
        appCompatButton2=(AppCompatButton)findViewById(R.id.app_reserva_off);
        //nombre_equi=(TextView)findViewById(R.id.lbl_nombre_equipo);
        capitan=(TextView)findViewById(R.id.lbl_capitan_equipo);
        datoo=getIntent().getExtras().getString("parametro");
        id_usuario=getIntent().getExtras().getString("id_usuario");
        nombre_equipo_=getIntent().getExtras().getString("nombre_equipo");
        sexo_usuario=getIntent().getExtras().getString("sexo");
        smartImageView_equipo=(SmartImageView)findViewById(R.id.img_equipo_perfil);
        progressBar=(ProgressBar)findViewById(R.id.progressBar3);
        progressBar_partido=(ProgressBar)findViewById(R.id.progressBar4);
        progressBar.setVisibility(View.VISIBLE);
        //cupo 9 jugadores
        //new Equipo.ConsultarDatos().execute("http://www.sacalapp.com/equipo_datos.php?dato=" + datoo);
        //cupo 12 jugadors
        new Equipo.ConsultarDatos().execute("http://www.sacalapp.com/equipo_datos_12.php?dato=" + datoo);

        no_partidos=(TextView)findViewById(R.id.lbl_no_hay_partido);
        toggleButton=(ToggleButton)findViewById(R.id.toggleButton_hay_equipo);
        mAdView_equipo = (AdView) findViewById(R.id.adView_equipo);
        mAdView_partidos = (AdView) findViewById(R.id.adView_prtidos);
        info_=(ImageView)findViewById(R.id.img_hay_equipo);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView_equipo.loadAd(adRequest);


        jugadores_lista=0;
        ratingBar=(RatingBar)findViewById(R.id.ratingBar_equipo_calificacion);
        result=0;
        usuarios=(ImageButton)findViewById(R.id.btn_agregar_jugador);
        eliminar=(ImageButton)findViewById(R.id.eliminar_bnt);
        Resources res = getResources();

        TabHost tabs=(TabHost)findViewById(R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.equipo);
        spec.setIndicator("",
                res.getDrawable(R.drawable.ic_equipo));
        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.chat);

        spec.setIndicator("",
                res.getDrawable(R.drawable.ic_chat));
        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab3");
        spec.setContent(R.id.partidos);
        spec.setIndicator("",
                res.getDrawable(R.drawable.ic_vs));
        tabs.addTab(spec);

        tabs.setCurrentTab(0);


        FloatingActionButton chat_ = (FloatingActionButton) findViewById(R.id.Nuevo_msn);
        chat_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialogo_chat();
            }
        });

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked == true) {
                    new Equipo.CargarDato().execute("http://www.sacalapp.com/estado_acv.php?id_equipo=" + id_equi);
                } else if (bChecked == false) {
                    new Equipo.CargarDato().execute("http://www.sacalapp.com/estado_des.php?id_equipo=" + id_equi);
                }
            }
        });

        list_partido_vs=(ListView) findViewById(R.id.list_partidos);
        list_partido_vs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //String id_reserva_2=(id_reserva.get(position).toString());
                //new Equipo.Consultar_reserva().execute("http://www.sacalapp.com/reserva_datos.php?id_reserva=" + id_reserva_2);
                final String partido=(id_partido.get(position).toString());
                String id_equipo_2=(id_equipo.get(position).toString());
                String nombre_equipo_2=(nombre_equipo.get(position).toString());
                String logo_equipo_2=(logo_equipoo.get(position).toString());
                String id_reserva_2=(id_reserva.get(position).toString());
                String nick_equipo2=(nick.get(position).toString());
                tipo_partido=(partidos_1.get(position).toString());

                String capi2=(usuario_cap.get(position).toString());

                fecha_ref=(fecha_par.get(position).toString());
                hora_ref=(hora_par.get(position).toString());

                try {
                    eventDate2 = dateFormat.parse(fecha_ref);
                    eventDate = dateFormat.parse(Fecha_oficial);
                    date_partido_hora=sdfConvert.parse(hora_ref);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String estado_par=(estado1.get(position).toString());

                if (estado_par.equals("Previa")) {



                if (eventDate.before(eventDate2)) {

                    Intent intent = new Intent(Equipo.this, Partidos.class);
                    intent.putExtra("id_jugador", id_usuario);
                    intent.putExtra("partido", tipo_partido);
                    intent.putExtra("id_reserva", id_reserva_2);
                    intent.putExtra("tu_equipo", id_equi);
                    intent.putExtra("capitan", capitane);
                    intent.putExtra("id_partido", partido);
                    intent.putExtra("id_equipo", datoo);
                    intent.putExtra("nombre_equipo", equipo_1_);
                    intent.putExtra("logo_equipo", logo_equipo);
                    intent.putExtra("nick_1", nick_equipo);
                    intent.putExtra("id_equipo_2", id_equipo_2);
                    intent.putExtra("nombre_equipo_2", nombre_equipo_2);
                    intent.putExtra("logo_equipo_2", logo_equipo_2);
                    intent.putExtra("nick_2", nick_equipo2);
                    intent.putExtra("capi1", nombre_capi);
                    intent.putExtra("capi2", capi2);
                    intent.putExtra("sexo", sexo_usuario);
                    startActivity(intent);

                }else if (eventDate.equals(eventDate2)) {

                    if (date_oficial_hora.before(date_partido_hora)) {

                        Intent intent = new Intent(Equipo.this, Partidos.class);
                        intent.putExtra("id_jugador", id_usuario);
                        intent.putExtra("partido", tipo_partido);
                        intent.putExtra("id_reserva", id_reserva_2);
                        intent.putExtra("tu_equipo", id_equi);
                        intent.putExtra("capitan", capitane);
                        intent.putExtra("id_partido", partido);
                        intent.putExtra("id_equipo", datoo);
                        intent.putExtra("nombre_equipo", equipo_1_);
                        intent.putExtra("logo_equipo", logo_equipo);
                        intent.putExtra("nick_1", nick_equipo);
                        intent.putExtra("id_equipo_2", id_equipo_2);
                        intent.putExtra("nombre_equipo_2", nombre_equipo_2);
                        intent.putExtra("logo_equipo_2", logo_equipo_2);
                        intent.putExtra("nick_2", nick_equipo2);
                        intent.putExtra("capi1", nombre_capi);
                        intent.putExtra("capi2", capi2);
                        startActivity(intent);

                    }else if (date_oficial_hora.after(date_partido_hora)) {

                        Toast.makeText(getApplicationContext(), "El partido ha terminado" , Toast.LENGTH_LONG).show();
                    }

                }else if (eventDate.after(eventDate2)) {
                    Toast.makeText(getApplicationContext(), "El partido ha terminado" , Toast.LENGTH_LONG).show();
                }

                }else if (estado_par.equals("Terminado")){
                    Toast.makeText(Equipo.this, "el partido ya ah terminado! "+partido, Toast.LENGTH_LONG).show();
                }


            }
        });

        lv_equipo = (ListView) findViewById(R.id.lv2_equipo);
        //listView_chat = (ListView) findViewById(R.id.list_msn);
        lv_equipo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  enviar=(id_jugador_equipo.get(position).toString());
                new Equipo.ConsultarDatos_jugador().execute("http://www.sacalapp.com/datos_fichajes_.php?email_usu="+enviar);
            }
        });


        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.srlContainer);
        swipeContainer_equipo = (SwipeRefreshLayout) findViewById(R.id.srlContainer_equipo);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Update data in ListView
                        Traer_msn();
                        // Remove widget from screen.
                        swipeContainer.setRefreshing(false);
                    }
                }, 1500);
            }
        });

        swipeContainer_equipo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Update data in ListView
                        TraerEquipo();
                        // Remove widget from screen.
                        swipeContainer_equipo.setRefreshing(false);
                    }
                }, 1000);
            }
        });




        lv2Refresh = (ListView) findViewById(R.id.lv2Refresh);



        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id_usuario.equals(ref_capi)) {
                    Dialogo_usuario_ceder();
                }else {
                    enviar=id_usuario;
                    new Equipo.ConsultarDatos_jugador().execute("http://www.sacalapp.com/datos_fichajes_.php?email_usu="+enviar);
                }


            }
        });


        usuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (cantidad_jugadores<=8){

                    if(id_usuario.equals(ref_capi)) {
                        Intent intent = new Intent(Equipo.this, Fichajes.class);
                        intent.putExtra("id_equipo", id_equi);
                        intent.putExtra("id_usuario", id_usuario);
                        intent.putExtra("dato","uno");
                        intent.putExtra("nombre_equipo", nombre_equipo_);
                        intent.putExtra("img", logo_equipo);
                        intent.putExtra("sexo", sexo_usuario);
                        startActivity(intent);
                    }else {
                        Toast.makeText(Equipo.this, "Solo el capitan puede fichar jugadores", Toast.LENGTH_LONG).show();

                    }
                }else{

                    Toast.makeText(Equipo.this, "No Tienes Cupos", Toast.LENGTH_LONG).show();
                }

            }
        });
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (capitane.equals(id_usuario)){
                    Intent intent = new Intent(Equipo.this, Reservas.class);
                    intent.putExtra("id_equipo", id_equi);
                    intent.putExtra("id_jugador", id_usuario);
                    intent.putExtra("nombre_equipo", nombre_equipo_);
                    startActivity(intent);
                    //finish();
                }else {
                    Toast.makeText(Equipo.this, "Solo el capitan puede realizar reservas", Toast.LENGTH_LONG).show();
                }


            }
        });

        appCompatButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Equipo.this, "No Disponible", Toast.LENGTH_LONG).show();

              if (capitane.equals(id_usuario)){
                    Intent intent = new Intent(Equipo.this, Reservas_off.class);
                    intent.putExtra("id_equipo", id_equi);
                    intent.putExtra("id_jugador", id_usuario);
                    intent.putExtra("nombre_equipo", nombre_equipo_);
                    startActivity(intent);
                    //finish();
                }else {
                    Toast.makeText(Equipo.this, "Solo el capitan puede realizar reservas", Toast.LENGTH_LONG).show();
                }


            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_equipo);
        setSupportActionBar(toolbar);

        cantidad_jug=(TextView)findViewById(R.id.lbl_can_jugadores);

        toolbar.setTitleTextColor(getResources().getColor(R.color.Blanco));
        getSupportActionBar().setTitle(nombre_equipo_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId.equals("mitab1")){
                    new Equipo.ConsultarDatos().execute("http://www.sacalapp.com/equipo_datos_12.php?dato=" + datoo);
                }
                if(tabId.equals("mitab2")){
                    Traer_msn();
                }
                if(tabId.equals("mitab3")){
                    progressBar_partido.setVisibility(View.VISIBLE);
                    AdRequest adRequest = new AdRequest.Builder().build();
                    mAdView_partidos.loadAd(adRequest);
                    new Equipo.Consultar_hora().execute("http://www.sacalapp.com/hora.php");

                    list_partido_vs.setAdapter(null);

                }
            }
        });


        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(getIntent().getExtras().getInt("notificationID4"));


    }

    private void Comparar() {

        if (j2.equals(enviar)) {
            campo = "jugador_2";
        } else if (j3.equals(enviar)) {
            campo = "jugador_3";
        } else if (j4.equals(enviar)) {
            campo = "jugador_4";
        } else if (j5.equals(enviar)) {
            campo = "jugador_5";
        } else if (j6.equals(enviar)) {
            campo = "jugador_6";
        } else if (j7.equals(enviar)) {
            campo = "jugador_7";
        } else if (j8.equals(enviar)) {
            campo = "jugador_8";
        } else if (j9.equals(enviar)) {
            campo = "jugador_9";
        }else if (j10.equals(enviar)) {
            campo = "jugador_10";
        }else if (j11.equals(enviar)) {
            campo = "jugador_11";
        }else if (j12.equals(enviar)) {
            campo = "jugador_12";
        }

        if(campo.equals("campo")){

        }

        new Equipo.CargarDato_capi().execute("http://www.sacalapp.com/actualiza_capitan.php?id_equipo=" + id_equi + "&campo=" + campo + "&id_usuario=" + enviar);

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
                //hora_oficial=Double.parseDouble(Hora_oficial);
                date_oficial_hora= sdfConvert.parse(Hora_oficial);

                new Equipo.Consultar_fecha().execute("http://www.sacalapp.com/fecha.php");

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Equipo.this, "Error hora", Toast.LENGTH_LONG).show();
            } catch (ParseException e) {
                e.printStackTrace();
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
                Traerpartidos();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Equipo.this, "Error hora", Toast.LENGTH_LONG).show();
            }

        }
    }

    //---------------------------------------------------

    private void Traerpartidos() {


        id_equipo.clear();
        nombre_equipo.clear();
        logo_equipoo.clear();
        id_partido.clear();
        id_reserva.clear();
        hora_par.clear();
        fecha_par.clear();
        nick.clear();
        estado1.clear();
        usuario_cap.clear();
        partidos_1.clear();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/equipo_vs_1.php?id_equipo="+id_equi, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        dato_1=jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++){

                            id_equipo.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                            nombre_equipo.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                            logo_equipoo.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                            id_partido.add(jsonArray.getJSONObject(i).getString("id_partido"));
                            id_reserva.add(jsonArray.getJSONObject(i).getString("id_reserva"));
                            hora_par.add(jsonArray.getJSONObject(i).getString("hora_inicio"));
                            fecha_par.add(jsonArray.getJSONObject(i).getString("fecha_partido"));
                            nick.add(jsonArray.getJSONObject(i).getString("nick_equipo"));
                            estado1.add(jsonArray.getJSONObject(i).getString("estado"));
                            usuario_cap.add(jsonArray.getJSONObject(i).getString("nombre"));
                            partidos_1.add(jsonArray.getJSONObject(i).getString("tipo_partido"));

                        }
                        Traerpartidos_vi();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error cargar equipos 1", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void Traerpartidos_vi() {
        id_equipo_v.clear();
        nombre_equipo_v.clear();
        logo_equipoo_v.clear();
        id_partido_v.clear();
        Id_reserva_.clear();
        hora_par_v.clear();
        fecha_par_v.clear();
        nick_2.clear();
        estado2.clear();
        usuario_cap_2.clear();
        partidos_2.clear();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/equipo_vs_2.php?id_equipo="+id_equi, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        dato_2=jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++) {

                            id_equipo_v.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                            nombre_equipo_v.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                            logo_equipoo_v.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                            id_partido_v.add(jsonArray.getJSONObject(i).getString("id_partido"));
                            Id_reserva_.add(jsonArray.getJSONObject(i).getString("id_reserva"));
                            hora_par_v.add(jsonArray.getJSONObject(i).getString("hora_inicio"));
                            fecha_par_v.add(jsonArray.getJSONObject(i).getString("fecha_partido"));
                            nick_2.add(jsonArray.getJSONObject(i).getString("nick_equipo"));
                            estado2.add(jsonArray.getJSONObject(i).getString("estado"));
                            usuario_cap_2.add(jsonArray.getJSONObject(i).getString("nombre"));
                            partidos_2.add(jsonArray.getJSONObject(i).getString("tipo_partido"));
                        }
                        Traerpartidos_1();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error cargar equipos 2", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void Traerpartidos_1() {

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/equipo_vs_1_off.php?id_equipo="+id_equi, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        dato_3=jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++){

                            id_equipo.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                            nombre_equipo.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                            logo_equipoo.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                            id_partido.add(jsonArray.getJSONObject(i).getString("id_partido"));
                            id_reserva.add(jsonArray.getJSONObject(i).getString("id_reserva"));
                            hora_par.add(jsonArray.getJSONObject(i).getString("hora_inicio"));
                            fecha_par.add(jsonArray.getJSONObject(i).getString("fecha_partido_off"));
                            nick.add(jsonArray.getJSONObject(i).getString("nick_equipo"));
                            estado1.add(jsonArray.getJSONObject(i).getString("estado"));
                            usuario_cap.add(jsonArray.getJSONObject(i).getString("nombre"));
                            partidos_1.add(jsonArray.getJSONObject(i).getString("tipo_partido"));

                        }
                        Traerpartidos_vi_2();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error cargar equipos 1", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void Traerpartidos_vi_2() {

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/equipo_vs_2_off.php?id_equipo="+id_equi, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        dato_4=jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++) {

                            id_equipo_v.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                            nombre_equipo_v.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                            logo_equipoo_v.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                            id_partido_v.add(jsonArray.getJSONObject(i).getString("id_partido"));
                            Id_reserva_.add(jsonArray.getJSONObject(i).getString("id_reserva"));
                            hora_par_v.add(jsonArray.getJSONObject(i).getString("hora_inicio"));
                            fecha_par_v.add(jsonArray.getJSONObject(i).getString("fecha_partido_off"));
                            nick_2.add(jsonArray.getJSONObject(i).getString("nick_equipo"));
                            estado2.add(jsonArray.getJSONObject(i).getString("estado"));
                            usuario_cap_2.add(jsonArray.getJSONObject(i).getString("nombre"));
                            partidos_2.add(jsonArray.getJSONObject(i).getString("tipo_partido"));
                        }
                        id_equipo.addAll(id_equipo_v);
                        nombre_equipo.addAll(nombre_equipo_v);
                        logo_equipoo.addAll(logo_equipoo_v);
                        id_partido.addAll(id_partido_v);
                        id_reserva.addAll(Id_reserva_);
                        hora_par.addAll(hora_par_v);
                        fecha_par.addAll(fecha_par_v);
                        nick.addAll(nick_2);
                        estado1.addAll(estado2);
                        usuario_cap.addAll(usuario_cap_2);
                        partidos_1.addAll(partidos_2);

                        result=dato_1+dato_2+dato_3+dato_4;

                        if (result==0){
                            list_partido_vs.setEnabled(false);
                            no_partidos.setEnabled(true);
                            no_partidos.setText("No Tienes Partidos");
                        }else{
                            list_partido_vs.setEnabled(true);
                            no_partidos.setEnabled(false);
                        }
                        for (int i=0;i<result;i++) {
                            list_partido_vs.setAdapter(new Equipo.ImageAdater_partido(getApplicationContext()));
                        }

                        progressBar_partido.setVisibility(View.INVISIBLE);


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error cargar equipos 2", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    //-----------------------------------------

    private void TraerEquipo() {

        id_jugador_equipo.clear();
        nombre_jugador_equipo.clear();
        posicion_jugador_equipo.clear();
        califi.clear();
        foto.clear();
        Level.clear();




        AsyncHttpClient cliente = new AsyncHttpClient();//Consultar_jugadores_equipo
        cliente.get("http://sacalapp.com/Consultar_jugadores_equipo_12.php?id_equipo="+id_equi, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i=0;i<jsonArray.length();i++){

                            id_jugador_equipo.add(jsonArray.getJSONObject(i).getString("usuario_id"));
                            nombre_jugador_equipo.add(jsonArray.getJSONObject(i).getString("nombre"));
                            posicion_jugador_equipo.add(jsonArray.getJSONObject(i).getString("posicion"));
                            califi.add(jsonArray.getJSONObject(i).getString("calificacion"));
                            foto.add(jsonArray.getJSONObject(i).getString("foto"));
                            Level.add(jsonArray.getJSONObject(i).getString("level"));

                            promedio=Float.parseFloat(califi.get(i).toString());
                            promedio_total=promedio_total+promedio;

                            String comparacion =(id_jugador_equipo.get(i).toString());
                            if(comparacion.equals(capitane)){
                                ref_capi=(id_jugador_equipo.get(i).toString());
                                nombre_capi=nombre_jugador_equipo.get(i).toString();
                                capitan.setText("Capitan: "+nombre_jugador_equipo.get(i).toString());
                            }
                                lv_equipo.setAdapter(new Equipo.ImageAdater(getApplicationContext()));
                        }

                        progressBar.setVisibility(View.INVISIBLE);

                            promedio_total = promedio_total / 12;
                            ratingBar.setRating(promedio_total);
                            if (ref_capi.equals(capitane)) {
                                new Equipo.CargarDato().execute("http://www.sacalapp.com/actualizar_califi_equipo.php?id_equipo=" + id_equi + "&calificacion=" + promedio_total);
                        }
                        traer_notificaciones();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error cargar jugadores", Toast.LENGTH_LONG).show();

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
        califi.clear();
        fotoo.clear();


        final ProgressDialog progressDialog = new ProgressDialog(Equipo.this);
        progressDialog.setMessage("Cargardo Tu Equipo");
        progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/consultar_jugadores_cambio_capitan.php?id_equipo="+id_equi+"&id_usuario="+id_usuario, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i=0;i<jsonArray.length();i++){

                            id_jugador_equipo.add(jsonArray.getJSONObject(i).getString("usuario_id"));
                            nombre_jugador_equipo.add(jsonArray.getJSONObject(i).getString("nombre"));
                            posicion_jugador_equipo.add(jsonArray.getJSONObject(i).getString("posicion"));
                            califi.add(jsonArray.getJSONObject(i).getString("calificacion"));
                            fotoo.add(jsonArray.getJSONObject(i).getString("foto"));

                            lista_ceder.setAdapter(new Equipo.ImageAdater_2(getApplicationContext()));

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error cargar jugadores equipos", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private class ImageAdater extends BaseAdapter {



        Context ctx;
        LayoutInflater layoutInflater;
        SmartImageView go,img_capi;
        ImageView lvl;
        CircleImageView smartImageView ;

        TextView tposicion,tnombre;


        public ImageAdater(Context applicationContext){

            this.ctx=applicationContext;
            layoutInflater=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return nombre_jugador_equipo.size();
        }

        @Override
        public Object getItem(int position) {

            Toast.makeText(Equipo.this, "Usuario "+position, Toast.LENGTH_SHORT).show();

            return position;
        }

        @Override
        public long getItemId(int position) {



            //Toast.makeText(Fichajes.this, "Usuario selecionado"+position, Toast.LENGTH_SHORT).show();
            return position;

        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {

                 viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.jugador_equipo,null);


            smartImageView =(CircleImageView) viewGroup.findViewById(R.id.img_jugador_equipo);
            go =(SmartImageView)viewGroup.findViewById(R.id.smartImageView_adelante);
            img_capi =(SmartImageView)viewGroup.findViewById(R.id.imm_capi_equipo);
            lvl =(ImageView)viewGroup.findViewById(R.id.img_lvl3);


            String URL_2="null";
             URL_2 = "http://sacalapp.com/jarvicf/img_users/"+foto.get(pos).toString();


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
            } else if (posi.equals("Medio")) {
                tposicion.setText("MED");
            } else if (posi.equals("Defensa")) {
                tposicion.setText("DEF");
            } else if (posi.equals("Portero")) {
                tposicion.setText("POR");
            }

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


            if (capitane.equals(id_jugador_equipo.get(pos).toString())){
                img_capi.setVisibility(View.VISIBLE);
            }

            return viewGroup;
        }


    }

    private class ImageAdater_2 extends BaseAdapter {



        Context ctx;
        LayoutInflater layoutInflater;
        SmartImageView go,img_capi;
        CircleImageView circleImageView;

        TextView tposicion,tnombre;


        public ImageAdater_2(Context applicationContext){

            this.ctx=applicationContext;
            layoutInflater=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return nombre_jugador_equipo.size();
        }

        @Override
        public Object getItem(int position) {

            Toast.makeText(Equipo.this, "Usuario "+position, Toast.LENGTH_SHORT).show();

            return position;
        }

        @Override
        public long getItemId(int position) {



            //Toast.makeText(Fichajes.this, "Usuario selecionado"+position, Toast.LENGTH_SHORT).show();
            return position;

        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {



            viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.jugador_equipo_capi,null);


            circleImageView =(CircleImageView) viewGroup.findViewById(R.id.img_jugador_equipo);
            go =(SmartImageView)viewGroup.findViewById(R.id.smartImageView_adelante);
            img_capi =(SmartImageView)viewGroup.findViewById(R.id.imm_capi_equipo);

            tnombre =(TextView)viewGroup.findViewById(R.id.lbl_nombre_jugador_equipo);
            tposicion =(TextView) viewGroup.findViewById(R.id.lbl_posicion_jugador_equipo);

            String urlfinal="http://sacalapp.com/jarvicf/img_users/"+fotoo.get(pos).toString();

            Glide.with(circleImageView.getContext())
                    .load(urlfinal)
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.drawable.perfil)
                    .signature(new StringSignature(urlfinal))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    //
                    // .skipMemoryCache(true)
                    .into(circleImageView);

            tnombre.setText(nombre_jugador_equipo.get(pos).toString());
            tposicion.setText(posicion_jugador_equipo.get(pos).toString());



            return viewGroup;
        }


    }

    public android.app.AlertDialog createSimpleDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        builder.setTitle("Importante")

                .setMessage(R.string.ceder)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TraerEquipo_2();
                            }
                        });

        builder.show();


        return builder.create();
    }

    private class ImageAdater_partido extends BaseAdapter {



        Context ctx;
        LayoutInflater layoutInflater;
        ImageView smartImageView_1,smartImageView_2;
        TextView equipo_1,equipo_2,Testado;


        public ImageAdater_partido(Context applicationContext){

            this.ctx=applicationContext;
            layoutInflater=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return result;
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

            ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.equipos_vs,null);

            smartImageView_1 =(ImageView)viewGroup.findViewById(R.id.smartImageView_1);
            smartImageView_2=(ImageView)viewGroup.findViewById(R.id.smartImageView_2);

            Testado =(TextView)viewGroup.findViewById(R.id.lbl_estado);
            equipo_1 =(TextView)viewGroup.findViewById(R.id.lbl_equipo_1);
            equipo_2 =(TextView) viewGroup.findViewById(R.id.lbl_equipo_2);


            equipo_1.setText(equipo_1_);


            equipo_2.setText(nombre_equipo.get(pos).toString());
            hora_ref=(hora_par.get(pos).toString());
            //hora_partido=Double.parseDouble(hora_ref);
            fecha_ref=(fecha_par.get(pos).toString());




            try {
                 eventDate2 = dateFormat.parse(fecha_ref);
                 eventDate = dateFormat.parse(Fecha_oficial);
                date_partido_hora=sdfConvert.parse(hora_ref);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (eventDate.before(eventDate2)){
                Testado.setText("Por Jugar");
            }else if (eventDate.after(eventDate2)) {
                Testado.setText("Terminado");
                //new Equipo.CargarDato().execute("http://www.sacalapp.com/actualiar_estatus_partido.php?id_partido="+id_partido.get(pos).toString()+"&id_reserva="+id_reserva.get(pos).toString());
                //estado1.set(pos,"Terminado");
            }else if (eventDate.equals(eventDate2)) {

                if(date_oficial_hora.before(date_partido_hora)){
                    Testado.setText("Previa");
                }else if(date_oficial_hora.after(date_partido_hora)){
                    Testado.setText("Terminado");
                    //new Equipo.CargarDato().execute("http://www.sacalapp.com/actualiar_estatus_partido.php?id_partido="+id_partido.get(pos).toString()+"&id_reserva="+id_reserva.get(pos).toString());
                   // estado1.set(pos,"Terminado");
                }
            }


            String nombre_="escudo_"+ equipo_1_logo_;
            String recurso="drawable";
            int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
            smartImageView_1.setImageResource(res_imagen);

            String nombre_1="escudo_"+ logo_equipoo.get(pos);
            String recurso1="drawable";
            int res_imagen1 = getResources().getIdentifier(nombre_1, recurso1,getPackageName());
            smartImageView_2.setImageResource(res_imagen1);

            return viewGroup;
        }


    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {

            Intent reg = new Intent(this, Perfil.class);
            startActivity(reg);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.equipo, menu);
        //menuItem = menu.findItem(R.id.notificaciones);
        traer_notificaciones();
        // menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        final View notificaitons = menu.findItem(R.id.actionNotifications).getActionView();
        final View reservas = menu.findItem(R.id.reservas).getActionView();

        txtViewCount = (TextView) notificaitons.findViewById(R.id.txtCount);
        txtViewCount_2 = (TextView) reservas.findViewById(R.id.txtCount_2);
        txtViewCount.setVisibility(View.INVISIBLE);
        txtViewCount_2.setVisibility(View.INVISIBLE);

        notificaitons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id_usuario.equals(capitane)){
                    ir_notificacio();
                }else {
                    Toast.makeText(Equipo.this, "solo accede el capitán", Toast.LENGTH_LONG).show();
                }


            }
        });

        reservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id_usuario.equals(capitane)){
                    ir_notificacio_reservas();
                }else {
                    Toast.makeText(Equipo.this, "solo accede el capitán", Toast.LENGTH_LONG).show();
                }

            }
        });

        // return true;
        return super.onCreateOptionsMenu(menu);

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

    public void updateHotCount_rese(final int new_hot_number) {
        count_2 = new_hot_number;
        if (count_2 < 0) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (count_2 == 0)
                    txtViewCount_2.setVisibility(View.INVISIBLE);
                else {
                    txtViewCount_2.setVisibility(View.VISIBLE);
                    txtViewCount_2.setText(Integer.toString(count_2));
                    // supportInvalidateOptionsMenu();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notificaciones:

                return true;

            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void ir_notificacio() {
        Intent intent = new Intent(Equipo.this, Notificacion_equipo.class);
        intent.putExtra("id_equipo", id_equi);
        intent.putExtra("id_jugador", id_usuario);
        startActivity(intent);
    }

    private void ir_notificacio_reservas() {
        Intent intent = new Intent(Equipo.this, Notificacion_reservas_equipos.class);
        intent.putExtra("nombre_equipo", nombre_equipo_);
        intent.putExtra("id_equipo", id_equi);
        intent.putExtra("id_jugador", id_usuario);
        startActivity(intent);
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


                nombre2=(ja.getString(0));
                posicion2=(ja.getString(1));
                nick2=(ja.getString(2));
                sexo2=(ja.getString(3));
                ciudad2=(ja.getString(4));
                edad2=(ja.getString(5));
                califica2=(ja.getString(6));
                foto_user=(ja.getString(7));
                Per=(ja.getString(9));
                LEvel=(ja.getString(10));

                if (capitane.equals(id_usuario)){

                    if (id_usuario.equals(enviar)){
                        Dialogo_usuario_capi();
                    }else {
                        Dialogo_usuario_capi();
                    }
                }else {

                    if (id_usuario.equals(enviar)){
                        Dialogo_usuario_capi();
                    }else {
                        Dialogo_usuario();
                    }
                }


                //resto(null);

           /*     if (sexo.equals("Masculino")){

                    img_sexo.setImageResource(R.mipmap.masculino);
                }else if (sexo.equals("Femenino")){

                    img_sexo.setImageResource(R.mipmap.femenino);
                }*/



            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Equipo.this, "Error_fichajes", Toast.LENGTH_LONG).show();

            }

        }
    }

    private void volver() {

        Intent intent = new Intent(Equipo.this, Perfil.class);
        startActivity(intent);
        finish();
    }

    private class Consultar_reserva extends AsyncTask<String, Void, String> {
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

                String fecha_partido,hora_partido;

                fecha_partido=(ja.getString(0).toString());
                hora_partido=(ja.getString(1).toString());

                hora =calendario.get(Calendar.HOUR_OF_DAY);
                minutos = calendario.get(Calendar.MINUTE);
                segundos = calendario.get(Calendar.SECOND);

                if (hora==2017){

                }

                new Equipo.hora().execute("http://www.sacalapp.com/hora.php");


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Equipo.this, "Error reserva", Toast.LENGTH_LONG).show();

            }

        }
    }

    private class hora extends AsyncTask<String, Void, String> {
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

            JSONArray ja2 = null;
            try {
                ja2 = new JSONArray(result);


                fecha=(ja2.getString(0).toString());

                if (fecha.equals("2017")){

                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Equipo.this, "Error hora", Toast.LENGTH_LONG).show();

            }

        }
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

                id_equi=(ja.getString(0).toString());
                //nombre_equi.setText(ja.getString(1).toString());
                equipo_1_=(ja.getString(1).toString());
                nick_equipo=(ja.getString(2).toString());
                logo_equipo=(ja.getString(3).toString());
                equipo_1_logo_=(ja.getString(3).toString());
                capitane=(ja.getString(4).toString());
                j2=(ja.getString(5).toString());
                j3=(ja.getString(6).toString());
                j4=(ja.getString(7).toString());
                j5=(ja.getString(8).toString());
                j6=(ja.getString(9).toString());
                j7=(ja.getString(10).toString());
                j8=(ja.getString(11).toString());
                j9=(ja.getString(12).toString());
                esta_eq=(ja.getString(13).toString());
                j10=(ja.getString(14).toString());
                j11=(ja.getString(15).toString());
                j12=(ja.getString(16).toString());



                if (capitane.equals(id_usuario)){
                    toggleButton.setVisibility(View.VISIBLE);
                    info_.setVisibility(View.VISIBLE);

                    if (esta_eq.equals("Activado")) {
                        toggleButton.setChecked(true);
                    } else if (esta_eq.equals("Desactivado")) {
                        toggleButton.setChecked(false);
                    }
                }else {
                    toggleButton.setVisibility(View.INVISIBLE);
                    info_.setVisibility(View.INVISIBLE);
                }






                /*
                String urlfinal="http://sacalapp.com/jarvicf/escudo_equipos/"+logo_equipo+".png";
                Rect rect = new Rect(smartImageView_equipo.getLeft(),smartImageView_equipo.getTop(),smartImageView_equipo.getRight(),smartImageView_equipo.getBottom());
                smartImageView_equipo.setImageUrl(urlfinal,rect);*/

                String nombre_="escudo_"+ logo_equipo;
                String recurso="drawable";
                int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
                smartImageView_equipo.setImageResource(res_imagen);

                TraerEquipo();
                Conteo();

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Equipo.this, "Error Usuarios", Toast.LENGTH_LONG).show();

            }

        }
    }

    private void Conteo() {

        cantidad_jugadores=12;

        if(j2.equals("null")){
            cantidad_jugadores=cantidad_jugadores-1;
        }if(j3.equals("null")){
            cantidad_jugadores=cantidad_jugadores-1;
        }if(j4.equals("null")){
            cantidad_jugadores=cantidad_jugadores-1;
        }if(j5.equals("null")){
            cantidad_jugadores=cantidad_jugadores-1;
        } if(j6.equals("null")){
            cantidad_jugadores=cantidad_jugadores-1;
        }if(j7.equals("null")){
            cantidad_jugadores=cantidad_jugadores-1;
        }if(j8.equals("null")){
            cantidad_jugadores=cantidad_jugadores-1;
        }if(j9.equals("null")){
            cantidad_jugadores=cantidad_jugadores-1;
        }if(j10.equals("null")){
            cantidad_jugadores=cantidad_jugadores-1;
        }if(j11.equals("null")){
            cantidad_jugadores=cantidad_jugadores-1;
        }if(j12.equals("null")){
            cantidad_jugadores=cantidad_jugadores-1;
        }

        cantidad_jug.setText(cantidad_jugadores+"/12");
    }

    private void traer_notificaciones() {




        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/detalles_Notificacion_equipo.php?id_equipo="+id_equi, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        count=jsonArray.length();

                        updateHotCount(count);
                        TraerReservasi();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Toast.makeText(getApplicationContext(), "Error al traer datos", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void TraerReservasi() {

      count_2=0;


        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/traer_reservas_por_equipos.php?id_equipo="+id_equi, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        noti_1=jsonArray.length();
                        TraerReservasi_1();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Toast.makeText(getApplicationContext(), "Error cargar jugadores equipos", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void TraerReservasi_1() {

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/traer_reservas_por_equipos_off.php?id_equipo="+id_equi, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        noti_2=jsonArray.length();
                        TraerReservano();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void TraerReservano() {



        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/traer_reservas_por_equipos_no.php?id_equipo="+id_equi, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        noti_3=jsonArray.length();
                        TraerReservano_1();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Toast.makeText(getApplicationContext(), "Error cargar jugadores equipos", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void TraerReservano_1() {


        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/traer_reservas_por_equipos_no_off.php?id_equipo="+id_equi, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        noti_4=jsonArray.length();
                        count_2=noti_1+noti_2+noti_3+noti_4;
                        updateHotCount_rese(count_2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Toast.makeText(getApplicationContext(), "Error cargar jugadores equipos", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void Mostrar_info_hay_equipo(View v) {

        Mostrar_info_hay_equipo();

    }

    public void Mostrar_info_hay_equipo(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Equipo.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.info_hay_equipo,null);

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

    //______________________________detalles_jugador________________

    public void Dialogo_usuario(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Equipo.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.usuarios_datos,null);

        builder.setView(dialogView);

        TextView nomb =(TextView)dialogView.findViewById(R.id.lbl_nombre_fichaje);
        TextView posicionn =(TextView)dialogView.findViewById(R.id.lbl_eda_posicion);
        TextView nick_2 =(TextView)dialogView.findViewById(R.id.lbl_nick_fichajes);
        TextView edad_2 =(TextView)dialogView.findViewById(R.id.lbl_eda_fichajes);
        TextView ciudad_2 =(TextView)dialogView.findViewById(R.id.lbl_ciuidad_fichajes);
        RatingBar ratinCali_2 = (RatingBar)dialogView.findViewById(R.id.ratingBar_calificacion_fichajes);
        CircleImageView  imagen_fichaje =(CircleImageView)dialogView.findViewById(R.id.ima_fichajes);
        ImageView lvl =(ImageView) dialogView.findViewById(R.id.smartImageView_nivel);
        ImageView perfiles =(ImageView) dialogView.findViewById(R.id.smartImageView_perfil);
        Button one = (Button) dialogView.findViewById(R.id.btn_Salir);

        nomb.setText(nombre2);
        posicionn.setText(posicion2);
        nick_2.setText(nick2);
        edad_2.setText(edad2+" Años");
        ciudad_2.setText(ciudad2);

        String urlfinal="http://sacalapp.com/jarvicf/img_users/"+foto_user.toString();

        Glide.with(imagen_fichaje.getContext())
                .load(urlfinal)
                .crossFade()
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.perfil)
                .signature(new StringSignature(urlfinal))
                .into(imagen_fichaje);

        Float cali_2=Float.parseFloat(califica2);
        ratinCali_2.setRating(cali_2);


        if (Per.equals("Derecha")){
            perfiles.setImageResource(R.drawable.ic_derecha);
        }else if (Per.equals("Izquierda")){
            perfiles.setImageResource(R.drawable.ic_izquierda);
        }

        if (LEvel.equals("Recreacion")){
            lvl.setImageResource(R.drawable.ic_recreacion);
        }else if (LEvel.equals("Amateur")){
            lvl.setImageResource(R.drawable.ic_amateur);
        }else if (LEvel.equals("SemiPro")){
            lvl.setImageResource(R.drawable.ic_semipro);
        }else if (LEvel.equals("Pro")){
            lvl.setImageResource(R.drawable.ic_pro);
        }else if (LEvel.equals("Leyenda")){
            lvl.setImageResource(R.drawable.ic_leyenda);
        }else if (LEvel.equals("null")){
            //lvl.setImageResource(R.drawable.ic_sin);
        }

        one.setText("Volver");

        final AlertDialog dialog = builder.create();

        one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public void Dialogo_usuario_capi(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Equipo.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.usuarios_datos_capi,null);

        builder.setView(dialogView);

        TextView nomb =(TextView)dialogView.findViewById(R.id.lbl_nombre_fichaje);
        TextView posicionn =(TextView)dialogView.findViewById(R.id.lbl_eda_posicion);
        TextView nick_2 =(TextView)dialogView.findViewById(R.id.lbl_nick_fichajes);
        TextView edad_2 =(TextView)dialogView.findViewById(R.id.lbl_eda_fichajes);
        TextView ciudad_2 =(TextView)dialogView.findViewById(R.id.lbl_ciuidad_fichajes);
        RatingBar ratinCali_2 = (RatingBar)dialogView.findViewById(R.id.ratingBar_calificacion_fichajes);
        CircleImageView imagen_fichaje =(CircleImageView)dialogView.findViewById(R.id.ima_fichajes);
        ImageView lvl =(ImageView) dialogView.findViewById(R.id.smartImageView_nivel);
        ImageView perfiles =(ImageView) dialogView.findViewById(R.id.smartImageView_perfil);
        AppCompatButton one = (AppCompatButton) dialogView.findViewById(R.id.btn_Salir);
        AppCompatButton two = (AppCompatButton) dialogView.findViewById(R.id.btn_terminar_contracto);



        nomb.setText(nombre2);
        posicionn.setText(posicion2);
        nick_2.setText(nick2);
        edad_2.setText(edad2+" Años");
        ciudad_2.setText(ciudad2);

        String urlfinal="http://sacalapp.com/jarvicf/img_users/"+foto_user.toString();

        Glide.with(imagen_fichaje.getContext())
                .load(urlfinal)
                .crossFade()
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.perfil)
                .signature(new StringSignature(urlfinal))
                .into(imagen_fichaje);

        Float cali_2=Float.parseFloat(califica2);
        ratinCali_2.setRating(cali_2);

        one.setText("Volver");
        if (capitane.equals(id_usuario)){

            if (capitane.equals(enviar)){
                two.setText("Eliminar Equipo");
            }else {
                two.setText("Terminar Contracto");
            }


        }else {
            two.setText("Dejar Equipo");
        }

        if (Per.equals("Derecha")){
            perfiles.setImageResource(R.drawable.ic_derecha);
        }else if (Per.equals("Izquierda")){
            perfiles.setImageResource(R.drawable.ic_izquierda);
        }

        if (LEvel.equals("Recreacion")){
            lvl.setImageResource(R.drawable.ic_recreacion);
        }else if (LEvel.equals("Amateur")){
            lvl.setImageResource(R.drawable.ic_amateur);
        }else if (LEvel.equals("SemiPro")){
            lvl.setImageResource(R.drawable.ic_semipro);
        }else if (LEvel.equals("Pro")){
            lvl.setImageResource(R.drawable.ic_pro);
        }else if (LEvel.equals("Leyenda")){
            lvl.setImageResource(R.drawable.ic_leyenda);
        }else if (LEvel.equals("null")){
            //lvl.setImageResource(R.drawable.ic_sin);
        }

        final AlertDialog dialog = builder.create();

        one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        two.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (capitane.equals(id_usuario)){

                    if (capitane.equals(enviar)){
                        Dialogo_usuario_ceder();
                    }else {
                        capi_elimina();
                    }


                }else {
                    capi_elimina();
                }


                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public void Dialogo_usuario_ceder(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Equipo.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.opciones_capitan,null);

        builder.setView(dialogView);

         selec_capitan = (TextView)dialogView.findViewById(R.id.lbl_nuevo_capi);
        ImageButton one = (ImageButton) dialogView.findViewById(R.id.imgbtn_eliminar);
        ImageButton two = (ImageButton) dialogView.findViewById(R.id.imgbtn_ceder);
        lista_ceder = (ListView) dialogView.findViewById(R.id.lst_ceder);

        final AlertDialog dialog = builder.create();



        one.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(Equipo.this);
                pd.setMessage("Espere...");
                pd.setCancelable(false);
                pd.show();
                DATOS=1;
                Traerpartidos_eli();
            }
        });
        two.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (cantidad_jugadores<=1){

                }else {

                    pd = new ProgressDialog(Equipo.this);
                    pd.setMessage("Espere...");
                    pd.setCancelable(false);
                    pd.show();
                    DATOS=2;
                    Traerpartidos_eli();
                    selec_capitan.setVisibility(View.VISIBLE);
                    selec_capitan.setText("Seleccione nuevo capitán");

                }

            }
        });

        lista_ceder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                enviar=(id_jugador_equipo.get(position).toString());
                Comparar();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void capi_elimina() {

        if (j2.equals(enviar)) {
            campo = "jugador_2";
        } else if (j3.equals(enviar)) {
            campo = "jugador_3";
        } else if (j4.equals(enviar)) {
            campo = "jugador_4";
        } else if (j5.equals(enviar)) {
            campo = "jugador_5";
        } else if (j6.equals(enviar)) {
            campo = "jugador_6";
        } else if (j7.equals(enviar)) {
            campo = "jugador_7";
        } else if (j8.equals(enviar)) {
            campo = "jugador_8";
        } else if (j9.equals(enviar)){
            campo = "jugador_9";
        }else if (j10.equals(enviar)){
            campo = "jugador_10";
        }else if (j11.equals(enviar)){
            campo = "jugador_11";
        }else if (j12.equals(enviar)){
            campo = "jugador_12";
        }

        new Equipo.CargarDato().execute("http://www.sacalapp.com/eliminar_jugador_equipo.php?id_equipo=" + id_equi + "&campo=" + campo);


        if (capitane.equals(id_usuario)){
            Toast.makeText(Equipo.this, "jugador eliminado", Toast.LENGTH_LONG).show();
            TraerEquipo();

        }else {

            Intent intent = new Intent(Equipo.this, Perfil.class);
            startActivity(intent);
            finish();

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

            //Toast.makeText(getApplicationContext(), "Se almacenaron los datos correctamente", Toast.LENGTH_LONG).show();


        }
    }

    private class CargarDato_capi extends AsyncTask<String, Void, String> {
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

            Intent intent = new Intent(Equipo.this, Perfil.class);
            startActivity(intent);
            finish();

        }
    }

    //--------------------------------chat----------------------------------
    public void Dialogo_chat(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Equipo.this);

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
        new Equipo.CargarDatos().execute("http://www.sacalapp.com/insertar_mensaje.php?id_usuario="+id_usuario+"&id_equipo="+id_equi+"&cuerpo_mensaje="+msn);

    }

    private void Traer_msn() {


        id_msn.clear();
        id_jugador_msn.clear();
        cuerpo_msn.clear();
        fecha_msn.clear();
        hora_msn.clear();
        id_nombre_msn.clear();
        foto_2.clear();


        //final ProgressDialog progressDialog = new ProgressDialog(Equipo.this);
        //progressDialog.setMessage("Cargardo Tu Equipo");
        //progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/trarer_mensajes_equipos.php?id_equipo="+id_equi, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    //progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i=0;i<jsonArray.length();i++) {

                            id_msn.add(jsonArray.getJSONObject(i).getString("id_usuario"));
                            id_jugador_msn.add(jsonArray.getJSONObject(i).getString("id_usuario"));
                            id_nombre_msn.add(jsonArray.getJSONObject(i).getString("nombre"));
                            cuerpo_msn.add(jsonArray.getJSONObject(i).getString("cuerpo_mensaje"));
                            fecha_msn.add(jsonArray.getJSONObject(i).getString("fecha_mensaje"));
                            hora_msn.add(jsonArray.getJSONObject(i).getString("hora_mensaje"));
                            foto_2.add(jsonArray.getJSONObject(i).getString("foto"));

                            lv2Refresh.setAdapter(new Equipo.ImageAdater_chat(getApplicationContext()));
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

    public void empezaranimacion() {

        new CountDownTimer(miliosegundo, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {


            }

            @Override
            public void onFinish() {

                Traer_msn();

            }
        }.start();
    }

    private class ImageAdater_chat extends BaseAdapter {



        Context ctx;
        LayoutInflater layoutInflater;
        CircleImageView smartImageView,go;


        TextView fecha_mensaje,mensaje_lbl,nombre,thora;


        public ImageAdater_chat(Context applicationContext){

            this.ctx=applicationContext;
            layoutInflater=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return id_msn.size();
        }

        @Override
        public Object getItem(int position) {



            return 9;
        }

        @Override
        public long getItemId(int position) {

            //Toast.makeText(Fichajes.this, "Usuario selecionado"+position, Toast.LENGTH_SHORT).show();
            return position;

        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {

            dato_comparar=(id_jugador_msn.get(pos).toString());

            if (dato_comparar.equals(id_usuario)){
                 viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.chat,null);
            }else if (dato_comparar!=(id_usuario)){
                 viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.chat_2,null);
            }

            smartImageView =(CircleImageView)viewGroup.findViewById(R.id.img_jugador);
            //go =(SmartImageView)viewGroup.findViewById(R.id.smartImageView_adelante);

            String URL_2="null";
            URL_2 = "http://sacalapp.com/jarvicf/img_users/"+foto_2.get(pos).toString();


            Glide.with(smartImageView.getContext())
                    .load(URL_2)
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.drawable.perfil)
                    .signature(new StringSignature(URL_2))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(smartImageView);


            thora=(TextView)viewGroup.findViewById(R.id.lbl_hora);
            fecha_mensaje =(TextView)viewGroup.findViewById(R.id.message_sender);
            mensaje_lbl =(TextView) viewGroup.findViewById(R.id.message_body);
            nombre =(TextView) viewGroup.findViewById(R.id.lbl_nombre_t);


            thora.setText(hora_msn.get(pos).toString());

            fecha_mensaje.setText(fecha_msn.get(pos).toString());

            mensaje_lbl.setText(cuerpo_msn.get(pos).toString());
            nombre.setText(id_nombre_msn.get(pos).toString());
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

    //Eliminar equipo

    private void Traerpartidos_eli() {


        id_partido.clear();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/equipo_vs_1.php?id_equipo="+id_equi, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        dato=jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++){

                            id_partido.add(jsonArray.getJSONObject(i).getString("id_partido"));

                        }
                        Traerpartidos_vi_eli();

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

    private void Traerpartidos_vi_eli() {

        id_partido_v.clear();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/equipo_vs_2.php?id_equipo="+id_equi, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        dato_1=jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++) {

                            id_partido_v.add(jsonArray.getJSONObject(i).getString("id_partido"));

                        }

                        result=dato+dato_1+count_2;

                        if (DATOS == 1) {
                            if (result==0){
                                pd.dismiss();
                                new Equipo.CargarDato_capi().execute("http://www.sacalapp.com/eliminar_equipo.php?id_equipo=" + id_equi);

                            }else{
                                pd.dismiss();
                                Toast.makeText(getApplicationContext(), "Partidos o reservas pendientes, no puede eliminar el equipo", Toast.LENGTH_LONG).show();
                            }
                        }else  if (DATOS == 2) {
                            if (result==0){
                                pd.dismiss();
                                createSimpleDialog();
                            }else{
                                pd.dismiss();
                                Toast.makeText(getApplicationContext(), "Partidos o reservas pendientes, no puede ceder la Capitanía", Toast.LENGTH_LONG).show();
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

    @Override
    public void onPause() {
        super.onPause();
        startService(new Intent(Equipo.this,
                Notificaciones.class));
    }

    @Override
    public void onStop() {
        super.onStop();
        startService(new Intent(Equipo.this,
                Notificaciones.class));
    }

    @Override
    public void onRestart(){
        super.onRestart();

        stopService(new Intent(Equipo.this,
                Notificaciones.class));


    }

}
