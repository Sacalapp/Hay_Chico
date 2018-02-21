package sacalapp.hay_chico;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;


import android.app.Dialog;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.PeriodicSync;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;


import android.widget.AdapterView;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;

import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.github.snowdream.android.widget.SmartImageView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.MobileAds;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


import org.json.JSONArray;
import org.json.JSONException;


import java.io.ByteArrayOutputStream;
import java.io.File;
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
import java.util.Date;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;


import de.hdodenhof.circleimageview.CircleImageView;
import sacalapp.hay_chico.Rss.ReadRss;
import sacalapp.hay_chico.SplashScreen.login.Login;
import sacalapp.hay_chico.SplashScreen.login.config;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static sacalapp.hay_chico.R.id.Hay_chico;
import static sacalapp.hay_chico.SplashScreen.login.config.EMAIL_SHARED_PREF;

import static android.Manifest.permission.CAMERA;



public class Perfil extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private LinearLayout mRlView;
    private static String APP_DIRECTORY = "Sacalapp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "Hay Chico";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    Intent CamIntent,GalIntent,CropIntent;

    Uri output;
    File newFile;
    private String mPath, Apodo,mPath_2,da;

    private String descali, posi,calificacion_actualizada, dato = "no", usuaRio,estatus_jugador, Email, Haychico, pierna, estado1 = "Activado",Bienvenida, estado2 = "Desactivado", id_equipo_equipo, nombre_equipo_, compara_noti, nombre_,nick_,id_equ;
    //Switch switch_hay_chico;
    ToggleButton switch_hay_chico_2;
    Float califica;
    RatingBar ratingBar_calificacion;
    TextView nombre, nick, posicion, edad, ciudad, informacion, no_equipo;
    Dialog customDialog = null;
    ListView listView_equipos, listView_fichejes,listView_equipo;
    RecyclerView recyclerView;
    private TextView txtViewCount, txtViewCount_par,infoma;
   // private int hot_number = 0;
    private String nombre_ra,TUTO, posicion_ra, edad_ra, ciudad_ra,sexo_usuario, foto_user_ra, perfil_ra, id_users, calificar_ra,fecha_actividad=null,fecha_ref,Fecha_oficial,hora_ref,estado,Hora_oficial,tipo_partido;
    Date eventDate2,eventDate,date_oficial_hora,date_partido_hora;
    SimpleDateFormat sdfConvert = new SimpleDateFormat("HH:mm");
    Uri uri;
   // File file_2;
   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private long mLastPress = 0;    // Cuándo se pulsó atrás por última vez
    private long mTimeLimit = 2000; // Límite de tiempo entre pulsaciones, en ms

    ArrayList nombre_equipo = new ArrayList();
    ArrayList apodo_equipo = new ArrayList();
    ArrayList imagen_equipo = new ArrayList();
    ArrayList id_equipo = new ArrayList();

    ArrayList fecha_par = new ArrayList();
    ArrayList hora_par = new ArrayList();

    ArrayList Level = new ArrayList();

    ArrayList id_equipo__2 = new ArrayList();

    ArrayList id_equipo_rank = new ArrayList();
    ArrayList nombre_equipo_rank = new ArrayList();
    ArrayList apodo_equipo_rank = new ArrayList();
    ArrayList imagen_equipo_rank = new ArrayList();

    ArrayList notificacion_x_equipo = new ArrayList();

    ArrayList nombre_jugador = new ArrayList();

    //ImageView smartImageView;

    ArrayList posicion_datos = new ArrayList();

    private TextInputLayout tilApodo;

   // ArrayList id_equipo_notificacion = new ArrayList();

    ArrayList edad_1 = new ArrayList();
    ArrayList nick_1 = new ArrayList();
    ArrayList imagen = new ArrayList();
    private String ruta, cargar;
    ArrayList rating = new ArrayList();
    ArrayList id_usuarios = new ArrayList();


    ArrayList id_equipo_eq = new ArrayList();
    ArrayList nombre_equipo_eq = new ArrayList();
    ArrayList nick_equipo_eq = new ArrayList();
    ArrayList logo_equipo_eq = new ArrayList();

    ArrayList partidos_ = new ArrayList();

    TextView notifCount, name, mail, count_ñ,no_partido;
    int count = 0, count1 = 0, count2 = 0, noti, count_par = 0, rank = 1, ref = 0,part,datoP= 0;
    MenuItem menuItem;
    NavigationView navigationView;
    private Menu mymenu;
    ProgressDialog pd,pd2;
    ProgressBar parPD;

    LinearLayout linearLayout,linearLayout_2;

    //Bitmap circularBitmap;

    ImageView imageView_no_equipo, posicion_img, perfil_img,imageView_no_partido;
    CircleImageView smartImageView,imagen_fichaje;
    AdView mAdView, mAdView_2;

    //decoimagen------------------------------------------
    //private static File file;
    //private static Uri _imagefileUri;
    //private TextView resultText;
    private static String _bytes64Sting, _imageFileName;
    public static String URL = "http://sacalapp.com/jarvicf/img_users/actualizar_imagen_perfil.php";
    String URL_2="null";

    //Partido//_________________________________________


    int ref_=0,item=0,i=0,aa=0,bb=0,ref_2_=0,ref_3_=0,count_=0,cap;
    ListView listView_partido,listView_canchas_2;

    private String nick_1_1,nick_2_,id_ca_,id_ca_2,par,LEvel;
    private String id_usuario,nom_equipo_1,nom_equipo_2,logo_eq1, logo_eq2,tu_equipo,eq1,eq2,id_reserva,capitan,id_partido__,equipo_1_,nombre_equipo_1_,logo_equipo_1_,equipo_2_,nombre_equipo_2_,logo_equipo_2_,id_equi_1,id_equi_2,c1,c2;



    ArrayList id_equipo_ = new ArrayList();
    ArrayList nombre_equipo__ = new ArrayList();
    ArrayList logo_equipo_ = new ArrayList();
    ArrayList id_partido_ = new ArrayList();
    ArrayList nick_1_ = new ArrayList();
    ArrayList id_capi_ = new ArrayList();



    ArrayList ciudad_ = new ArrayList();
    ArrayList perfil_pierna = new ArrayList();
    ArrayList id_reserva_ = new ArrayList();


    ArrayList id_equipo_2 = new ArrayList();
    ArrayList nombre_equipo_2 = new ArrayList();
    ArrayList logo_equipo_2 = new ArrayList();
    ArrayList id_partido_2 = new ArrayList();
    ArrayList nick_2 = new ArrayList();
    ArrayList id_capi_2 = new ArrayList();

    ArrayList id_califica = new ArrayList();
    ArrayList id_usuario_cal = new ArrayList();


    //-----------------------------------------tutorial-----------------------

    private ShowcaseView showcaseView;
    private int contador=0;
    private Target t1,t2,t3,t4,t5,t6,t7;
    RelativeLayout relativeLayout_2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        pd2 = new ProgressDialog(Perfil.this);
        mAdView = (AdView) findViewById(R.id.adView);
        mAdView_2= (AdView) findViewById(R.id.adView2);
        //MobileAds.initialize(this, "ca-app-pub-8067834228961607~6348334168");


        mRlView = (LinearLayout) findViewById(R.id.msn_linear);
        nombre = (TextView) findViewById(R.id.lbl_nombre_jugador);
        nick = (TextView) findViewById(R.id.lbl_apodo_jugador);
        //posicion = (TextView) findViewById(R.id.lbl_posi);
       // edad = (TextView) findViewById(R.id.lbl_edad);
        ciudad = (TextView) findViewById(R.id.lbl_ciudad);
        informacion = (TextView) findViewById(R.id.lbl_info);
        ratingBar_calificacion = (RatingBar) findViewById(R.id.Rtb_calificacion);
        //switch_hay_chico = (Switch) findViewById(R.id.switch_hay_chico);
        switch_hay_chico_2 = (ToggleButton) findViewById(R.id.switch_hay_chico_2);
        no_equipo = (TextView) findViewById(R.id.lbl_no_equipo);
        no_partido= (TextView) findViewById(R.id.lbl_no_partido);


        posicion_img = (ImageView) findViewById(R.id.img_posicion);
        perfil_img = (ImageView) findViewById(R.id.img_perfil);

        smartImageView = (CircleImageView) findViewById(R.id.img_perfil_jugador);

        imageView_no_equipo = (ImageView) findViewById(R.id.ing_equipo);
        imageView_no_partido = (ImageView) findViewById(R.id.ing_partido);

        //perfil_img.setImageResource(R.drawable.pie_derecho);
        linearLayout = (LinearLayout) findViewById(R.id.conte_imagenes);
        linearLayout_2 = (LinearLayout) findViewById(R.id.conte_imagenes_2);

        listView_equipos = (ListView) findViewById(R.id.lst_equipos_perfil);
        listView_fichejes = (ListView) findViewById(R.id.lst_comtenido);


        listView_equipos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                id_equipo_equipo = (id_equipo.get(position).toString());
                nombre_equipo_ = (nombre_equipo.get(position).toString());

                //Toast.makeText(Perfil.this, "Usuario selecionado"+id_equipo_equipo, Toast.LENGTH_SHORT).show();
                ir_equipo();
            }
        });

        listView_fichejes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                id_users = (id_usuarios.get(position).toString());

                nombre_ra= (nombre_jugador.get(position).toString());
                posicion_ra=(posicion_datos.get(position).toString());
                ciudad_ra= (ciudad_.get(position).toString());
                edad_ra= (edad_1.get(position).toString());
                calificar_ra= (rating.get(position).toString());
                foto_user_ra= (imagen.get(position).toString());
                perfil_ra= (perfil_pierna.get(position).toString());
                nick_= (nick_1.get(position).toString());
                LEvel = (Level.get(position).toString());
                Dialogo_info_usuario();
                //new Perfil.ConsultarDatos_user().execute("http://www.sacalapp.com/datos_fichajes.php?email_usu=" + id_users);

            }
        });



        if (mayRequestStoragePermission())
            smartImageView.setEnabled(true);
        else
            smartImageView.setEnabled(false);

        parPD=(ProgressBar) findViewById(R.id.progressBar2);
        smartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOptions();
            }
        });

        TabHost tabs = (TabHost) findViewById(R.id.tabhost);
        tabs.setup();

        Resources res = getResources();


        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.Fichajes);
        spec.setIndicator("",
                res.getDrawable(R.drawable.ic_equipos));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.Perfil);
        spec.setIndicator("",
                res.getDrawable(R.drawable.ic_perfil));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab3");
        spec.setContent(R.id.Tendencias);

        spec.setIndicator("",
                res.getDrawable(R.drawable.ic_tendencias));
        tabs.addTab(spec);
        tabs.setCurrentTab(1);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);


        name = (TextView) header.findViewById(R.id.textView2);
        mail = (TextView) header.findViewById(R.id.textView);

        bd();

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId == "mitab1") {


                    AdRequest adRequest = new AdRequest.Builder().build();
                    mAdView_2.loadAd(adRequest);
                    descargarRanking();
                    //Toast.makeText(Perfil.this, "fichajes ", Toast.LENGTH_SHORT).show();
                    getSupportActionBar().setTitle("Ranking");


                }
                if (tabId == "mitab2") {
                    listView_equipos.setAdapter(null);
                    listView_partido.setAdapter(null);
                    clear_ranking();
                    bd();
                    //Toast.makeText(Perfil.this, "perfil ", Toast.LENGTH_SHORT).show();
                    getSupportActionBar().setTitle("Perfil");
                }
                if (tabId == "mitab3") {

                    AdRequest adRequest = new AdRequest.Builder().build();
                    mAdView.loadAd(adRequest);
                    rss();

                    //Toast.makeText(Perfil.this, "tendencias ", Toast.LENGTH_SHORT).show();
                    getSupportActionBar().setTitle("Tendencias");
                }
            }
        });

        switch_hay_chico_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked == true) {
                    new CargarDato().execute("http://www.sacalapp.com/estado.php?id_usuario=" + usuaRio);
                    actualizar();
                    if (TUTO.equals("TutoSi")){
                        new CargarDato().execute("http://www.sacalapp.com/estado.php?id_usuario=" + usuaRio);
                        actualizar();
                        actualizar_tuto();
                    }

                } else if (bChecked == false) {
                    new CargarDato().execute("http://www.sacalapp.com/estado_desactivado.php?id_usuario=" + usuaRio);
                    actualizar_2();

                }
            }
        });




        listView_partido=(ListView) findViewById(R.id.lst_partidos_jugador);

        listView_partido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                fecha_ref=(fecha_par.get(position).toString());
                hora_ref=(hora_par.get(position).toString());



                try {
                    eventDate2 = dateFormat.parse(fecha_ref);
                    eventDate = dateFormat.parse(Fecha_oficial);
                    date_partido_hora=sdfConvert.parse(hora_ref);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (eventDate.before(eventDate2)) {

                    eq1 = (id_equipo_.get(position).toString());
                    eq2 = (id_equipo_2.get(position).toString());
                    id_partido__ = (id_partido_.get(position).toString());
                    par= (partidos_.get(position).toString());

                    pd = new ProgressDialog(Perfil.this);
                    pd.setMessage("Ingresando...");
                    pd.setCancelable(false);
                    pd.show();

                    count = position;
                    new Perfil.ConsultarDatos_ids().execute("http://www.sacalapp.com/ids_partidos.php?id_partido=" + id_partido__);

                }else if (eventDate.equals(eventDate2)) {

                        if (date_oficial_hora.before(date_partido_hora)) {
                            eq1 = (id_equipo_.get(position).toString());
                            eq2 = (id_equipo_2.get(position).toString());
                            id_partido__ = (id_partido_.get(position).toString());
                            par= (partidos_.get(position).toString());

                            pd = new ProgressDialog(Perfil.this);
                            pd.setMessage("Ingresando...");
                            pd.setCancelable(false);
                            pd.show();

                            count = position;
                            new Perfil.ConsultarDatos_ids().execute("http://www.sacalapp.com/ids_partidos.php?id_partido=" + id_partido__);
                        } else if (date_oficial_hora.after(date_partido_hora)) {

                            Toast.makeText(getApplicationContext(), "El partido ha terminado" + dato, Toast.LENGTH_LONG).show();


                        }
                }
                //pd.dismiss();
            }

        });

        t1 = new ViewTarget(R.id.lbl_nombre_jugador, this);
        t2 = new ViewTarget(R.id.img_perfil_jugador, this);
        t7 = new ViewTarget(R.id.switch_hay_chico_2, this);
        t4 = new ViewTarget(R.id.conte_imagenes_2, this);
        t5 = new ViewTarget(R.id.conte_imagenes, this);
        t6 = new ViewTarget(R.id.Rtb_calificacion, this);
        t3 = new ViewTarget(R.id.imageView, this);

        SharedPreferences prefe=getSharedPreferences("datos",Context.MODE_PRIVATE);
         TUTO = prefe.getString("tuto", "");

        if (TUTO.equals("TutoSi")){

            showcaseView = new ShowcaseView.Builder(this)
                    .setTarget(Target.NONE)
                    .setContentTitle("Te presentaremos ahora un tutorial corto de las funciones mas importantes de la aplicación")
                    .setOnClickListener(this)
                    .setStyle(R.style.cuerpo)
                    .build();
        }


    }

    public void info_tengo_chico(View v) {

        Mostrar_info_tengo_chico();

    }

    public void Mostrar_info_tengo_chico(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Perfil.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.info_tengo_chico,null);

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

    private void setMenuCounter(@IdRes int itemId, int count) {
        TextView view = (TextView) navigationView.getMenu().findItem(itemId).getActionView();
        view.setText(count > 0 ? String.valueOf(count) : null);
    }

    private void traer_posi() {


        if (posi.equals("Portero")) {
            nombre_ = "ic_portero";
        } else if (posi.equals("Defensa")) {
            nombre_ = "ic_defensa";
        } else if (posi.equals("Medio")) {
            nombre_ = "ic_medio";
        } else {
            nombre_ = "ic_delantero";
        }

        String recurso = "drawable";
        int res_imagen = getResources().getIdentifier(nombre_, recurso, getPackageName());
        posicion_img.setImageResource(res_imagen);

        if (pierna.equals("Derecha")) {
            nombre_ = "ic_derecha";
        } else {
            nombre_ = "ic_izquierda";
        }

        String recurso_ = "drawable";
        int res_imagen_ = getResources().getIdentifier(nombre_, recurso_, getPackageName());
        perfil_img.setImageResource(res_imagen_);

    }

    private void ir_equipo() {

        //pd2.dismiss();
        Intent intent = new Intent(Perfil.this, Equipo.class);
        intent.putExtra("parametro", id_equipo_equipo);
        intent.putExtra("id_usuario", usuaRio);
        intent.putExtra("nombre_equipo", nombre_equipo_);
        intent.putExtra("sexo", sexo_usuario);
        finish();
        startActivity(intent);
    }

    private void rss() {

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        ReadRss readRss = new ReadRss(this, recyclerView);
        readRss.execute();
    }

    private void actualizar_2() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "user", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("estatus", estado2);

//Actualizamos el registro en la base de datos
        bd.update("usuario", valores, "codigo=" + 0, null);

        bd.close();
    }

    private void actualizar() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "user", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("estatus", estado1);

//Actualizamos el registro en la base de datos
        bd.update("usuario", valores, "codigo=" + 0, null);

        bd.close();

        ir_ubicacion();


    }

    private void ir_ubicacion() {
        Intent intent = new Intent(Perfil.this, Mi_Ubicacion.class);
        intent.putExtra("id_usuario", usuaRio);
        startActivity(intent);
        finish();

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

    @SuppressLint("ResourceAsColor")
    private void bd() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "user", null, 1);

        SQLiteDatabase bd = admin.getWritableDatabase();

        //Cursor fila = bd.rawQuery("select nombre from usuario where codigo=" + dni, null);
        Cursor fila = bd.rawQuery("select nombre, posicion, edad, calificacion, ciudad, nick ,estatus, email, usuario_id, pierna,estatus_jugador,Bienvenida,sexo from usuario where codigo=0", null);

        if (fila.moveToFirst()) {

            nombre.setText(fila.getString(0));
            name.setText(fila.getString(0));
//            posicion.setText(fila.getString(1));
            posi = (fila.getString(1));
 //           edad.setText(fila.getString(2) + " años");
            descali = (fila.getString(3));
            ciudad.setText(fila.getString(4));
            nick.setText(fila.getString(5));
            Haychico = (fila.getString(6));
            //cabe_email.setText(fila.getString(7));
            Email = (fila.getString(7));
            mail.setText(fila.getString(7));
            usuaRio = (fila.getString(8));
            pierna = (fila.getString(9));
            estatus_jugador= (fila.getString(10));
            Bienvenida= (fila.getString(11));
            sexo_usuario= (fila.getString(12));


            if (Bienvenida.equals("NO")){
                Intent intent = new Intent(Perfil.this, Bienvenido.class);
                intent.putExtra("id_jugador", usuaRio);
                intent.putExtra("perfil", 1);
                startActivity(intent);

            }

            //smartImageView.setBorderColor(R.color.ameteur);


            if (estatus_jugador.equals("Activo")) {

                califica = Float.parseFloat(fila.getString(3));
                ratingBar_calificacion.setRating(califica);

                if (Haychico.equals("Activado")) {
                    switch_hay_chico_2.setChecked(true);
                } else if (Haychico.equals("Desactivado")) {
                    switch_hay_chico_2.setChecked(false);
                }


                caragr_imagen();
                traer_posi();
                actualizar_calificacion();

                new Perfil.CargarDato_2().execute("http://www.sacalapp.com/fecha_actividad.php?id_usuario="+usuaRio);



            } else  if (estatus_jugador.equals("Suspendido")) {

                Toast.makeText(this, "Tu cuenta esta SUSPENDIDA!", Toast.LENGTH_SHORT).show();
                salir();
            }

        } else
            //Toast.makeText(this, "No existe ningún usuario con ese dni", Toast.LENGTH_SHORT).show();
        bd.close();


        traer_notificaciones_EQUIPO();
        //notificaciones();

    }

    private void actualizar_calificacion() {
        new Perfil.actualizar_calificacion().execute("http://www.sacalapp.com/actualizar_calificacion_usuario.php?id_jugador=" + usuaRio);
    }

    private void salir() {

        //Getting out sharedpreferences
        SharedPreferences preferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        //Getting editor
        SharedPreferences.Editor editor = preferences.edit();

        //Puting the value false for loggedin
        editor.putBoolean(config.LOGGEDIN_SHARED_PREF, false);

        //Putting blank value to email
        editor.putString(EMAIL_SHARED_PREF, "");

        //Saving the sharedpreferences
        editor.commit();

        borrar();

        stopService(new Intent(Perfil.this, Notificaciones.class));
        super.onRestart();


        //Starting login activity

        if (estatus_jugador.equals("Suspendido")) {
            Intent intent = new Intent(Perfil.this, Suspendido.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(Perfil.this, Login.class);
            startActivity(intent);
            finish();

        }

    }

    private void caragr_imagen() {

        //smartImageView.clearFocus();

       URL_2 = "http://sacalapp.com/jarvicf/img_users/" + Email + ".jpg";

      Glide.clear(smartImageView);
        Glide.with(smartImageView.getContext())
                .load(URL_2)
                .crossFade()
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.perfil)
                .signature(new StringSignature(URL_2))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                //.skipMemoryCache(true)
                .into(smartImageView);

    }

    private Bitmap descargarImagen(String imageHttpAddress) {
        URL imageUrl = null;
        Bitmap imagen = null;
        try {
            imageUrl = new URL(imageHttpAddress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imagen = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return imagen;
    }

    @Override
    public void onBackPressed() {

        Toast onBackPressedToast = Toast.makeText(this, "Pulse de nuevo para salir", Toast.LENGTH_SHORT);
        long currentTime = System.currentTimeMillis();
        onBackPressedToast.show();
        if (currentTime - mLastPress > mTimeLimit) {
            mLastPress = currentTime;

        } else {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            onBackPressedToast.cancel();
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.perfil, menu);
        mymenu = menu;
        //menuItem = menu.findItem(R.id.notificaciones);
        traer_notificaciones();
        // menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);


        final View notificaitons = menu.findItem(R.id.actionNotifications).getActionView();
        final View partidos = menu.findItem(R.id.partidos).getActionView();

        txtViewCount = (TextView) notificaitons.findViewById(R.id.txtCount);
        txtViewCount.setVisibility(View.INVISIBLE);

        txtViewCount_par = (TextView) partidos.findViewById(R.id.txtCount_2);
        txtViewCount_par.setVisibility(View.INVISIBLE);

        partidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Perfil.this, Partidos_usuarios.class);
                intent.putExtra("id_jugador", usuaRio);
                startActivity(intent);
                finish();
            }
        });

        notificaitons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Notificacion_usuario.class);
                intent.putExtra("id_jugador", usuaRio);
                startActivity(intent);
                finish();
            }
        });

        // return true;
        return super.onCreateOptionsMenu(menu);


    }

    public void updateHotCount(final int new_hot_number) {
        count = new_hot_number;
        if (count < 0)
            return;
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

    public void updateHotCount_partidos(final int new_hot_number) {
        count_par = new_hot_number;
        if (count_par < 0) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (count_par == 0)
                    txtViewCount_par.setVisibility(View.INVISIBLE);
                else {
                    txtViewCount_par.setVisibility(View.VISIBLE);
                    txtViewCount_par.setText(Integer.toString(count_par));
                    // supportInvalidateOptionsMenu();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.partidos) {

            return true;
        }

        if (id == R.id.Actualizar) {
            return true;
        }
        if (id == R.id.Volver) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.equipo) {
            Intent intent = new Intent(Perfil.this, Crear_equipo.class);
            intent.putExtra("id_jugador", usuaRio);
            startActivity(intent);
        } else if (id == R.id.Hay_chico) {

            Intent intent = new Intent(Perfil.this, Tengo_chico.class);
            intent.putExtra("id_jugador", usuaRio);
            startActivity(intent);

        } else if (id == R.id.ajustes) {

            Intent intent = new Intent(Perfil.this, ajustes.class);
            intent.putExtra("id_jugador", usuaRio);
            startActivity(intent);

        } else if (id == R.id.cerrar_sesion) {

            mostrar();
        }else if (id == R.id.Compartir) {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Que nunca te falte PARCHE para jugar. Llega ¿Hay Chico?. Descárgalo aquí > https://play.google.com/store/apps/details?id=sacalapp.hay_chico");
            startActivity(Intent.createChooser(intent, "Compartir por"));
        }else if (id == R.id.partido) {
            Dialogo_traer_equipos();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //__________________Crear Partidos_________________

    public void Dialogo_traer_equipos(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Perfil.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.filtra_por_canchas,null);

        builder.setView(dialogView);
        infoma = (TextView)dialogView.findViewById(R.id.lbl_infor);
        listView_canchas_2 = (ListView) dialogView.findViewById(R.id.lista_canchas);
        infoma.setText("Buscando...");
        Traer_equipos();

        final AlertDialog dialog = builder.create();


        listView_canchas_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String nombre_eq=nombre_equipo_eq.get(position).toString();
                String id_equipo_edd=id_equipo_eq.get(position).toString();
                Intent intent = new Intent(Perfil.this, Reservas.class);
                intent.putExtra("id_equipo", id_equipo_edd);
                intent.putExtra("id_jugador", usuaRio);
                intent.putExtra("nombre_equipo", nombre_eq);
                startActivity(intent);
                dialog.dismiss();
            }
        });



        dialog.show();
    }

    private void Traer_equipos() {

        id_equipo_eq.clear();
        nombre_equipo_eq.clear();
        nick_equipo_eq.clear();
        logo_equipo_eq.clear();

        final ProgressDialog progressDialog = new ProgressDialog(Perfil.this);

        progressDialog.setMessage("Cargardo Canchas...");
        progressDialog.show();

        //id_can_equipos.clear();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/equipos_jugador.php?id_usuario="+usuaRio, new AsyncHttpResponseHandler() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        int equipos=jsonArray.length();
                        if (equipos>0){
                            infoma.setText("Selecione un equipo");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                id_equipo_eq.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                                nombre_equipo_eq.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                                nick_equipo_eq.add(jsonArray.getJSONObject(i).getString("nick_equipo"));
                                logo_equipo_eq.add(jsonArray.getJSONObject(i).getString("logo_equipo"));

                                listView_canchas_2.setAdapter(new Perfil.ImageAdater_equipos(getApplicationContext()));

                            }

                        }else {
                            infoma.setText("Para poder Crear un Partido, debes crear un equipo.");
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

    private class ImageAdater_equipos extends BaseAdapter {


        Context ctx;
        LayoutInflater layoutInflater;
        ImageView smartImageView_cancha;
        TextView tnombre, tdirecion;


        public ImageAdater_equipos(Context applicationContext) {

            this.ctx = applicationContext;
            layoutInflater = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return id_equipo_eq.size();
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

            ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.equipos_usuarios_eq, null);


            smartImageView_cancha = (ImageView) viewGroup.findViewById(R.id.img_cancha_portada);


            tnombre = (TextView) viewGroup.findViewById(R.id.lbl_nombre_cancha);
            tdirecion = (TextView) viewGroup.findViewById(R.id.lbl_dir_cancha);

            String urlfinal = "http://sacalapp.com/jarvicf/escudo_equipos/" + logo_equipo_eq.get(pos)+".png".toString();
            Glide.with(smartImageView_cancha.getContext())
                    .load(urlfinal)
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.drawable.perfil_equipo)
                    .signature(new StringSignature(urlfinal))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    //.skipMemoryCache(true)
                    .into(smartImageView_cancha);

            tnombre.setText(nombre_equipo_eq.get(pos).toString());
            tdirecion.setText(nick_equipo_eq.get(pos).toString());
            return viewGroup;
        }


    }

    //_________________________________________________


    private void borrar() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "user", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        bd.delete("usuario", "codigo=" + 0, null);

        bd.close();
    }

    public void mostrar() {
        // con este tema personalizado evitamos los bordes por defecto
        customDialog = new Dialog(this, R.style.Theme_Dialog_Translucent);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.dialogo_cerra_seccion);

        TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
        titulo.setText("Salir");

        TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
        contenido.setText("Desea cerra la sección");


        Button aceptar = (Button) customDialog.findViewById(R.id.aceptar);
        Button cancelar = (Button) customDialog.findViewById(R.id.cancelar);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customDialog.dismiss();

                //Getting out sharedpreferences
                SharedPreferences preferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                //Getting editor
                SharedPreferences.Editor editor = preferences.edit();

                //Puting the value false for loggedin
                editor.putBoolean(config.LOGGEDIN_SHARED_PREF, false);

                //Putting blank value to email
                editor.putString(EMAIL_SHARED_PREF, "");

                //Saving the sharedpreferences
                editor.commit();


                borrar();


                //Starting login activity
                Intent intent = new Intent(Perfil.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customDialog.dismiss();
            }
        });

        customDialog.show();
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

    private void traer_notificaciones_EQUIPO() {

        notificacion_x_equipo.clear();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/notificacion_perfil_equipo.php?id_usuario=" + usuaRio, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            notificacion_x_equipo.add(jsonArray.getJSONObject(i).getString("id_equipo"));

                        }
                        new Perfil.Consultar_hora().execute("http://www.sacalapp.com/hora.php");
                        Mostraraquipos();

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

    private void Mostraraquipos() {
        id_equipo.clear();
        nombre_equipo.clear();
        apodo_equipo.clear();
        imagen_equipo.clear();
        listView_equipos.setAdapter(null);

       // final ProgressDialog progressDialog = new ProgressDialog(Perfil.this);
        //progressDialog.setMessage("Cargardo Equipos");
        //progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/consulta_equipos_perfil.php?id_jugador=" + usuaRio, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                   // progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        if (jsonArray.length() == 0) {
                            listView_equipos.setVisibility(View.INVISIBLE);
                            no_equipo.setVisibility(View.VISIBLE);
                            no_equipo.setText("No tienes Equipos, toca el escudo y crea uno!");
                            imageView_no_equipo.setVisibility(View.VISIBLE);
                            linearLayout.setVisibility(View.VISIBLE);
                        } else {
                            listView_equipos.setVisibility(View.VISIBLE);
                            no_equipo.setVisibility(View.INVISIBLE);
                            linearLayout.setVisibility(View.INVISIBLE);
                        }

                        for (int i = 0; i < jsonArray.length(); i++) {
                            id_equipo.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                            nombre_equipo.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                            apodo_equipo.add(jsonArray.getJSONObject(i).getString("nick_equipo"));
                            imagen_equipo.add(jsonArray.getJSONObject(i).getString("logo_equipo"));

                            listView_equipos.setAdapter(new Perfil.equipoAdater(getApplicationContext()));

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "Revisa tu conexión a internet", Toast.LENGTH_LONG).show();

            }
        });
    }

    private class equipoAdater extends BaseAdapter {

        Context ctx;
        LayoutInflater layoutInflater;
        SmartImageView equipo;
        ImageView tonieq, notificacion_eq;
        TextView tnombre, tnick;

        public equipoAdater(Context applicationContext) {

            this.ctx = applicationContext;
            layoutInflater = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return nombre_equipo.size();
        }

        @Override
        public Object getItem(int position) {


            return position;
        }

        @Override
        public long getItemId(int position) {

          /*  final String enviar=(id_usuarios.get(dato_usu).toString());
            new Fichajes.ConsultarDatos().execute("http://www.sacalapp.com/datos_fichajes.php?email_usu=" + enviar);

            dato_usu=position;
            //Toast.makeText(Fichajes.this, "Usuario selecionado"+position, Toast.LENGTH_SHORT).show();*/
            return position;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {

            ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.equipo_solo, null);

            equipo = (SmartImageView) viewGroup.findViewById(R.id.img_equipo_perfil);
            notificacion_eq = (ImageView) viewGroup.findViewById(R.id.noti_equipo_img);
            tonieq = (ImageView) viewGroup.findViewById(R.id.noti_img);

            tnombre = (TextView) viewGroup.findViewById(R.id.lbl_equipo_perfil);
            tnick = (TextView) viewGroup.findViewById(R.id.lbl_apodo_equipo_perfil);


           /* String urlfinal = "http://sacalapp.com/jarvicf/escudo_equipos/" + imagen_equipo.get(pos).toString() + ".png";
            Rect rect = new Rect(equipo.getLeft(), equipo.getTop(), equipo.getRight(), equipo.getBottom());
            equipo.setImageUrl(urlfinal, rect);*/

            String nombre_="escudo_"+ imagen_equipo.get(pos).toString();
            String recurso="drawable";
            int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
            equipo.setImageResource(res_imagen);

            tnombre.setText(nombre_equipo.get(pos).toString());
            tnick.setText(apodo_equipo.get(pos).toString());

            compara_noti = (id_equipo.get(pos).toString());

            for (int i = 0; i < notificacion_x_equipo.size(); i++) {
                String dato = (notificacion_x_equipo.get(i).toString());
                if (compara_noti.equals(dato)) {
                    notificacion_eq.setVisibility(View.VISIBLE);
                }
            }


            return viewGroup;
        }
    }

    private void traer_notificaciones() {


        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/notificaion_para_usuario.php?id_usuario=" + usuaRio, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        count1 = jsonArray.length();


                        traer_notificaciones_calficacion();



                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error al traer notificaciones", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void traer_notificaciones2() {

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/notificacion_para_usuario_partido.php?id_usuario=" + usuaRio, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        count2 = jsonArray.length();

                        traer_notificaciones_partido_off();

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

    private void traer_notificaciones_partido_off() {


        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/notificacion_para_usuario_partido_off.php?id_usuario="+usuaRio, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        count2=count2+jsonArray.length();

                        updateHotCount_partidos(count2);
                        equipo_1();

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

    private void traer_notificaciones_calficacion() {


        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/notificacion_calificacion.php?id_usuario="+usuaRio, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        count1=count1+jsonArray.length();

                        updateHotCount(count1);
                        traer_notificaciones2();

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

    private void equipo_1() {

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/equipos_hay_chico.php", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        part=jsonArray.length();
                        equipo_1_off();

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

    private void equipo_1_off() {

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/equipos_hay_chico_off.php", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        part=part+jsonArray.length();
                        setMenuCounter(Hay_chico,part);

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

    public void crear_equ(View v) {

        Intent intent = new Intent(Perfil.this, Crear_equipo.class);
        intent.putExtra("id_jugador", usuaRio);
        startActivity(intent);
    }

    public void hay_chico(View v) {

        Intent intent = new Intent(Perfil.this, Tengo_chico.class);
        intent.putExtra("id_jugador", usuaRio);
        startActivity(intent);
    }

    private class actualizar_calificacion extends AsyncTask<String, Void, String> {
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

                calificacion_actualizada=(ja.getString(0));
                actualizar_cali();





            } catch (JSONException e) {
                e.printStackTrace();
                // Toast.makeText(Partidos_usuarios.this, "error capitan", Toast.LENGTH_LONG).show();

            }

        }
    }

    private void actualizar_cali(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "user", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("calificacion",calificacion_actualizada);

//Actualizamos el registro en la base de datos
        bd.update("usuario", valores, "codigo="+0, null);
        bd.close();

    }

    //Mis PArtidos____________________________

    private void equi_1() {
        parPD.setVisibility(View.VISIBLE);
        id_equipo_.clear();
        nombre_equipo__.clear();
        logo_equipo_.clear();
        id_partido_.clear();
        nick_1_.clear();
        id_capi_.clear();
        fecha_par.clear();
        hora_par.clear();
        id_reserva_.clear();
        partidos_.clear();


        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/partidos_user.php?id_usuario="+usuaRio, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        ref_2_=jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++) {

                            id_equipo_.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                            nombre_equipo__.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                            logo_equipo_.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                            id_partido_.add(jsonArray.getJSONObject(i).getString("id_partido"));
                            nick_1_.add(jsonArray.getJSONObject(i).getString("nick_equipo"));

                            id_capi_.add(jsonArray.getJSONObject(i).getString("jugador_1_cap"));

                            fecha_par.add(jsonArray.getJSONObject(i).getString("fecha_partido"));
                            hora_par.add(jsonArray.getJSONObject(i).getString("hora_inicio"));
                            id_reserva_.add(jsonArray.getJSONObject(i).getString("id_reserva"));

                            partidos_.add(jsonArray.getJSONObject(i).getString("tipo_partido"));



                        }
                        equi_2();
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

    private void equi_2() {

        id_equipo_2.clear();
        nombre_equipo_2.clear();
        logo_equipo_2.clear();
        id_partido_2.clear();
        nick_2.clear();
        id_capi_2.clear();


        //final ProgressDialog progressDialog = new ProgressDialog(Perfil.this);
        //progressDialog.setMessage("Cargardo...");
        //progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/partidos_user_2.php?id_usuario="+usuaRio, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        //progressDialog.dismiss();
                        ref_3_=jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++) {


                            id_equipo_2.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                            nombre_equipo_2.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                            logo_equipo_2.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                            id_partido_2.add(jsonArray.getJSONObject(i).getString("id_partido"));
                            nick_2.add(jsonArray.getJSONObject(i).getString("nick_equipo"));
                            id_capi_2.add(jsonArray.getJSONObject(i).getString("jugador_1_cap"));



                        }

                       /* if (ref_ == 0) {
                            listView_partido.setVisibility(View.INVISIBLE);
                            no_partido.setVisibility(View.VISIBLE);
                            no_partido.setText("No tienes Partidos, toca la imagen y busca uno!");
                            imageView_no_partido.setVisibility(View.VISIBLE);
                            linearLayout_2.setVisibility(View.VISIBLE);
                        } else {
                            listView_partido.setVisibility(View.VISIBLE);
                            no_partido.setVisibility(View.INVISIBLE);
                            linearLayout_2.setVisibility(View.INVISIBLE);
                        }*/
                        //lista();
                        equi_1_1();
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

    private void equi_1_1() {


        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/partidos_user_off.php?id_usuario="+usuaRio, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i=0;i<jsonArray.length();i++) {

                            id_equipo_.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                            nombre_equipo__.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                            logo_equipo_.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                            id_partido_.add(jsonArray.getJSONObject(i).getString("id_partido"));
                            nick_1_.add(jsonArray.getJSONObject(i).getString("nick_equipo"));

                            id_capi_.add(jsonArray.getJSONObject(i).getString("jugador_1_cap"));

                            fecha_par.add(jsonArray.getJSONObject(i).getString("fecha_partido_off"));
                            hora_par.add(jsonArray.getJSONObject(i).getString("hora_inicio"));
                            id_reserva_.add(jsonArray.getJSONObject(i).getString("id_reserva"));

                            partidos_.add(jsonArray.getJSONObject(i).getString("tipo_partido"));


                        }
                        equi_2_2();
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

    private void equi_2_2() {


        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/partidos_user_2_off.php?id_usuario="+usuaRio, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i=0;i<jsonArray.length();i++) {


                            id_equipo_2.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                            nombre_equipo_2.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                            logo_equipo_2.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                            id_partido_2.add(jsonArray.getJSONObject(i).getString("id_partido"));
                            nick_2.add(jsonArray.getJSONObject(i).getString("nick_equipo"));
                            id_capi_2.add(jsonArray.getJSONObject(i).getString("jugador_1_cap"));



                        }
                        aa=id_partido_.size()+id_partido_2.size();
                        aa=aa/2;

                        if (aa == 0) {
                            listView_partido.setVisibility(View.INVISIBLE);
                            no_partido.setVisibility(View.VISIBLE);
                            no_partido.setText("No tienes Partidos, toca la imagen y busca uno!");
                            imageView_no_partido.setVisibility(View.VISIBLE);
                            linearLayout_2.setVisibility(View.VISIBLE);
                        } else {
                            listView_partido.setVisibility(View.VISIBLE);
                            no_partido.setVisibility(View.INVISIBLE);
                            linearLayout_2.setVisibility(View.INVISIBLE);
                        }
                        lista();
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

    private void lista() {

        for (int ll=0;ll<aa;ll++) {
            bb=ll;
            listView_partido.setAdapter(new Perfil.ImageAdater_partido(getApplicationContext()));
        }
        parPD.setVisibility(View.INVISIBLE);

    }

    private class ImageAdater_partido extends BaseAdapter {


        Context ctx;
        LayoutInflater layoutInflater;
        //SmartImageView smartImageView_3;
        ImageView smartImageView_1,smartImageView_2;
        //ImageView imageView_op;
        //TextView lbl_equipo_2,lbl_equipo_1;


        public ImageAdater_partido(Context applicationContext){

            this.ctx=applicationContext;
            layoutInflater=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return aa;
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
        public View getView(final int pos, View convertView, ViewGroup parent) {

            ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.equipos_vs_user,null);

            smartImageView_1 =(ImageView)viewGroup.findViewById(R.id.smartImageView_1);
            smartImageView_2 =(ImageView)viewGroup.findViewById(R.id.smartImageView_2);

            TextView lbl_equipo_2 =(TextView) viewGroup.findViewById(R.id.id_estado_parti);
            //lbl_equipo_1 =(TextView) viewGroup.findViewById(R.id.lbl_equipo_1);

            nom_equipo_1 = (nombre_equipo__.get(pos).toString());
            nom_equipo_2 = (nombre_equipo_2.get(pos).toString());
            fecha_ref=(fecha_par.get(pos).toString());
            hora_ref=(hora_par.get(pos).toString());
            logo_eq1 = (logo_equipo_.get(pos).toString());
            logo_eq2 = (logo_equipo_2.get(pos).toString());
            tipo_partido=(partidos_.get(pos).toString());

            String nombre_="escudo_"+ logo_eq1;
            String recurso="drawable";
            int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
            smartImageView_1.setImageResource(res_imagen);


            String nombre_1="escudo_"+ logo_eq2;
            String recurso1="drawable";
            int res_imagen1 = getResources().getIdentifier(nombre_1, recurso1,getPackageName());
            smartImageView_2.setImageResource(res_imagen1);



            try {
                eventDate2 = dateFormat.parse(fecha_ref);
                eventDate = dateFormat.parse(Fecha_oficial);
                date_partido_hora=sdfConvert.parse(hora_ref);


            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (eventDate.before(eventDate2)){
                lbl_equipo_2.setText("Por Jugar");
            }else if (eventDate.after(eventDate2)) {
                lbl_equipo_2.setText("Terminado");
                id_partido__ = (id_partido_.get(pos).toString());
                if (tipo_partido.equals("1")){
                    new Perfil.CargarDato_elimina().execute("http://www.sacalapp.com/actualiar_estatus_partido.php?id_partido="+id_partido_.get(pos).toString()+"&id_reserva="+id_reserva_.get(pos).toString());
                }else if (tipo_partido.equals("2")){
                    new Perfil.CargarDato_elimina().execute("http://www.sacalapp.com/actualiar_estatus_partido_2.php?id_partido="+id_partido_.get(pos).toString());
                }

            }else if (eventDate.equals(eventDate2)) {

                if(date_oficial_hora.before(date_partido_hora)){
                    lbl_equipo_2.setText("Previa");
                }else if(date_oficial_hora.after(date_partido_hora)){
                    lbl_equipo_2.setText("Terminado");
                    if (tipo_partido.equals("2")){
                        new Perfil.CargarDato_elimina().execute("http://www.sacalapp.com/actualiar_estatus_reserva_off.php?id_reserva="+id_reserva_.get(pos).toString());
                    }


                }
            }

            return viewGroup;
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
                String dato;
                id_reserva=(ja.getString(18));
                id_equi_1=(ja.getString(19));
                id_equi_2=(ja.getString(20));
                estado=(ja.getString(21));
                par=(ja.getString(22));

                if (estado.equals("Previa")){
                    for(int i=0;i<ja.length()-3;i++){
                        dato=(ja.getString(i));
                        if (dato.equals(usuaRio)){
                            if (i<8){
                                tu_equipo=eq1;
                            }else {
                                tu_equipo=eq2;
                            }
                        }
                    }

                    if (tu_equipo.equals(eq1)){

                        equipo_1_ = (id_equipo_.get(count).toString());
                        nombre_equipo_1_ = (nombre_equipo__.get(count).toString());
                        logo_equipo_1_ = (logo_equipo_.get(count).toString());
                        nick_1_1 = (nick_1_.get(count).toString());
                        c1=(id_capi_.get(count).toString());


                        equipo_2_ = (id_equipo_2.get(count).toString());
                        nombre_equipo_2_ = (nombre_equipo_2.get(count).toString());
                        logo_equipo_2_ = (logo_equipo_2.get(count).toString());
                        nick_2_ = (nick_2.get(count).toString());
                        c2=(id_capi_2.get(count).toString());
                    }else {
                        equipo_2_ = (id_equipo_.get(count).toString());
                        nombre_equipo_2_ = (nombre_equipo__.get(count).toString());
                        logo_equipo_2_ = (logo_equipo_.get(count).toString());
                        nick_2_ = (nick_1_.get(count).toString());
                        c2=(id_capi_.get(count).toString());

                        equipo_1_ = (id_equipo_2.get(count).toString());
                        nombre_equipo_1_ = (nombre_equipo_2.get(count).toString());
                        logo_equipo_1_ = (logo_equipo_2.get(count).toString());
                        nick_1_1 = (nick_2.get(count).toString());
                        c1=(id_capi_2.get(count).toString());
                    }
                    new Perfil.ConsultarDatos_capi().execute("http://www.sacalapp.com/capitan.php?id_equipo="+tu_equipo);
                }else {
                    Toast.makeText(Perfil.this, "Partido ya Termino!", Toast.LENGTH_LONG).show();
                }
           } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Perfil.this, "Partido Cancelado", Toast.LENGTH_LONG).show();

            }

        }

    }

    private class ConsultarDatos_capi extends AsyncTask<String, Void, String> {
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

                capitan=(ja.getString(0));
                cap=0;
                new Perfil.ConsultarDatos_capitanes().execute("http://www.sacalapp.com/capitanes.php?capi1="+c1);
                //partidos();


            } catch (JSONException e) {
                e.printStackTrace();
                // Toast.makeText(Partidos_usuarios.this, "error capitan", Toast.LENGTH_LONG).show();

            }

        }
    }

    private class ConsultarDatos_capitanes extends AsyncTask<String, Void, String> {
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

                if (cap==0){
                    c1=(ja.getString(0));
                    cap=1;
                }else {
                    c2=(ja.getString(0));
                    partidos();
                }
                new Perfil.ConsultarDatos_capitanes().execute("http://www.sacalapp.com/capitanes.php?capi1="+c2);

            } catch (JSONException e) {
                e.printStackTrace();
                // Toast.makeText(Partidos_usuarios.this, "error capitan", Toast.LENGTH_LONG).show();

            }

        }
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

                new Perfil.Consultar_fecha().execute("http://www.sacalapp.com/fecha.php");

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Perfil.this, "Error hora", Toast.LENGTH_LONG).show();
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
                equi_1();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Perfil.this, "Error Fecha", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void partidos() {

        pd.dismiss();
        Intent intent = new Intent(Perfil.this, Partidos.class);

        intent.putExtra("id_jugador", usuaRio);
        intent.putExtra("id_reserva", id_reserva);

        intent.putExtra("tu_equipo", tu_equipo);
        intent.putExtra("capitan", capitan);

        intent.putExtra("partido", par);

        intent.putExtra("id_partido", id_partido__);

        intent.putExtra("id_equipo", equipo_1_);
        intent.putExtra("nombre_equipo", nombre_equipo_1_);
        intent.putExtra("logo_equipo", logo_equipo_1_);
        intent.putExtra("nick_1", nick_1_1);

        intent.putExtra("id_equipo_2", equipo_2_);
        intent.putExtra("nombre_equipo_2", nombre_equipo_2_);
        intent.putExtra("logo_equipo_2", logo_equipo_2_);
        intent.putExtra("nick_2", nick_2_);

        intent.putExtra("sexo", sexo_usuario);

        intent.putExtra("capi1", c1);
        intent.putExtra("capi2", c2);

        startActivity(intent);
        finish();

    }

    private class CargarDato_elimina extends AsyncTask<String, Void, String> {
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
            califica();
        }
    }

    private void califica() {


        id_califica.clear();


        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://www.sacalapp.com/estado_calificar.php?id_partido="+id_partido__, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i = 0; i < jsonArray.length(); i++) {

                            id_califica.add(jsonArray.getJSONObject(i).getString("id_calificacion"));
                            new CargarDato().execute("http://www.sacalapp.com/actualizar_estado_califica.php?id_calificacion=" + id_califica.get(i).toString());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                       // Toast.makeText(getApplicationContext(), "Error 2", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "Revisa tu conexión a internet", Toast.LENGTH_LONG).show();

            }
        });
    }

    //Ranking___________________

    private void descargarRanking() {



        posicion_datos.clear();
        edad_1.clear();
        nick_1.clear();
        imagen.clear();
        rating.clear();
        nombre_jugador.clear();
        id_usuarios.clear();
        ciudad_.clear();
        perfil_pierna.clear();
        Level.clear();

        final ProgressDialog progressDialog = new ProgressDialog(Perfil.this);
        progressDialog.setMessage("Cargardo Jugadores");
        progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/ranking_jugadores_.php?sexo="+sexo_usuario, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        ref = jsonArray.length();
                        for (int i = 0; i < jsonArray.length(); i++) {

                            nick_1.add(jsonArray.getJSONObject(i).getString("nick"));
                            posicion_datos.add(jsonArray.getJSONObject(i).getString("posicion"));
                            edad_1.add(jsonArray.getJSONObject(i).getString("edad"));
                            imagen.add(jsonArray.getJSONObject(i).getString("foto"));
                            rating.add(jsonArray.getJSONObject(i).getString("calificacion"));
                            id_usuarios.add(jsonArray.getJSONObject(i).getString("usuario_id"));
                            nombre_jugador.add(jsonArray.getJSONObject(i).getString("nombre"));
                            ciudad_.add(jsonArray.getJSONObject(i).getString("ciudad"));
                            perfil_pierna.add(jsonArray.getJSONObject(i).getString("perfil_pierna"));
                            Level.add(jsonArray.getJSONObject(i).getString("level"));

                            listView_fichejes.setAdapter(new Perfil.ImageAdater(getApplicationContext()));

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "Revisa tu conexión a internet", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void clear_ranking() {
        posicion_datos.clear();
        edad_1.clear();
        nick_1.clear();
        imagen.clear();
        rating.clear();
        nombre_jugador.clear();
        id_usuarios.clear();
        Level.clear();
    }

    private class ImageAdater extends BaseAdapter {


        RatingBar ratinCali;
        Context ctx;
        LayoutInflater layoutInflater;
        ImageView smartImageView,lvl;
        TextView tposicion, tedad, tranting, tnombre;
        float cali;


        public ImageAdater(Context applicationContext) {

            this.ctx = applicationContext;
            layoutInflater = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }


        @Override
        public int getCount() {

            return nick_1.size();
        }

        @Override
        public Object getItem(int position) {

            Toast.makeText(Perfil.this, "Usuario " + position, Toast.LENGTH_SHORT).show();

            return position;
        }

        @Override
        public long getItemId(int position) {


            //Toast.makeText(Fichajes.this, "Usuario selecionado"+position, Toast.LENGTH_SHORT).show();
            return position;

        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {

            ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.ref_fichajes, null);


            CircleImageView smartImageView_ranking = (CircleImageView) viewGroup.findViewById(R.id.imagenperfil);
            ratinCali = (RatingBar) viewGroup.findViewById(R.id.ratingBar_calificacion);

            lvl = (ImageView) viewGroup.findViewById(R.id.img_lvl);

            tranting = (TextView) viewGroup.findViewById(R.id.lbl_count);
            tposicion = (TextView) viewGroup.findViewById(R.id.lbl_posicion);
            tedad = (TextView) viewGroup.findViewById(R.id.lbl_eda);
            tnombre = (TextView) viewGroup.findViewById(R.id.lbl_nombre_ran);



            URL_2 = "http://sacalapp.com/jarvicf/img_users/"+imagen.get(pos).toString();



            Glide.with(smartImageView_ranking.getContext())
                    .load(URL_2)
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.drawable.perfil)
                    .signature(new StringSignature(URL_2))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    //.skipMemoryCache(true)
                    .into(smartImageView_ranking);

            //String urlfinal="null";
            //urlfinal="http://sacalapp.com/jarvicf/img_users/"+imagen.get(pos).toString();
            //Rect rect = new Rect(smartImageView_ranking.getLeft(),smartImageView_ranking.getTop(),smartImageView_ranking.getRight(),smartImageView_ranking.getBottom());
            //smartImageView_ranking.setImageUrl(urlfinal,rect);

            //smartImageView.setImage(R.drawable.calendario);
            cali = Float.parseFloat(rating.get(pos).toString());
            ratinCali.setRating(cali);


            String cadena;
            cadena = String.valueOf(pos + 1);
            tranting.setText(cadena + "-");


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
            String Años = (edad_1.get(pos).toString());
            tedad.setText(Años + " Años");
            tnombre.setText(nombre_jugador.get(pos).toString());

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

            return viewGroup;
        }

    }

    public void Dialogo_info_usuario() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Perfil.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.partido_datos_3, null);

        builder.setView(dialogView);

        TextView nomb = (TextView) dialogView.findViewById(R.id.lbl_nombre_fichaje);
        TextView posicionn = (TextView) dialogView.findViewById(R.id.lbl_eda_posicion);
        TextView nick_2 = (TextView) dialogView.findViewById(R.id.lbl_nick_fichajes);
        TextView edad_2 = (TextView) dialogView.findViewById(R.id.lbl_eda_fichajes);
        TextView ciudad_2 = (TextView) dialogView.findViewById(R.id.lbl_ciuidad_fichajes);
        RatingBar ratinCali_2 = (RatingBar) dialogView.findViewById(R.id.ratingBar_calificacion_fichajes);
        imagen_fichaje = (CircleImageView) dialogView.findViewById(R.id.ima_fichajes);
        ImageView imagen_pierna = (ImageView) dialogView.findViewById(R.id.smartImageView_perfil);
        ImageView imagen_nivel = (ImageView) dialogView.findViewById(R.id.smartImageView_nivel);
         AppCompatButton fic_inv = (AppCompatButton)dialogView.findViewById(R.id.fic_inv);
        AppCompatButton btn_Salir = (AppCompatButton) dialogView.findViewById(R.id.btn_Salir);


        nomb.setText(nombre_ra);
        posicionn.setText(posicion_ra);
        edad_2.setText(edad_ra + " años");
        nick_2.setText(nick_);
        ciudad_2.setText(ciudad_ra);


        String urlfinal = "http://sacalapp.com/jarvicf/img_users/" + foto_user_ra.toString();

        Glide.with(imagen_fichaje.getContext())
                .load(urlfinal)
                .crossFade()
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.perfil)
                .signature(new StringSignature(urlfinal))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                //.skipMemoryCache(true)
                .into(imagen_fichaje);

        if (perfil_ra.equals("Derecha")) {
            nombre_ = "ic_derecha";
        } else {
            nombre_ = "ic_izquierda";
        }
        String recurso_ = "drawable";
        int res_imagen_ = getResources().getIdentifier(nombre_, recurso_, getPackageName());
        imagen_pierna.setImageResource(res_imagen_);


        if (LEvel.equals("Recreacion")){
            imagen_nivel.setImageResource(R.drawable.ic_recreacion);
        }else if (LEvel.equals("Amateur")){
            imagen_nivel.setImageResource(R.drawable.ic_amateur);
        }else if (LEvel.equals("SemiPro")){
            imagen_nivel.setImageResource(R.drawable.ic_semipro);
        }else if (LEvel.equals("Pro")){
            imagen_nivel.setImageResource(R.drawable.ic_pro);
        }else if (LEvel.equals("Leyenda")){
            imagen_nivel.setImageResource(R.drawable.ic_leyenda);
        }else if (LEvel.equals("null")){
            //lvl.setImageResource(R.drawable.ic_sin);
        }




        Float cali_2 = Float.parseFloat(calificar_ra);
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
                Dialogo_equipos();
                dialog.dismiss();
            }
        });

        dialog.show();
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


                nombre_ra=(ja.getString(0));
                posicion_ra=(ja.getString(1));
                ciudad_ra=(ja.getString(4));
                edad_ra=(ja.getString(5));
                calificar_ra=(ja.getString(6));
                foto_user_ra=(ja.getString(7));
                perfil_ra=(ja.getString(9));
                nick_=(ja.getString(2));


                Dialogo_info_usuario();


            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(Perfil.this, "Error_fichajes", Toast.LENGTH_LONG).show();

            }

        }
    }

    public void Dialogo_equipos(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Perfil.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.filtra_por_canchas,null);

        builder.setView(dialogView);
        infoma = (TextView)dialogView.findViewById(R.id.lbl_infor);
        listView_equipo = (ListView) dialogView.findViewById(R.id.lista_canchas);
        infoma.setText("Buscando...");
        Mostrarequipos();

        final AlertDialog dialog = builder.create();


        listView_equipo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                id_equ = (id_equipo_rank.get(position).toString());
                Mostrarequipo_fichar();
                dialog.dismiss();
            }
        });



        dialog.show();
    }

    private void Mostrarequipo_fichar (){

        id_equipo__2.clear();


        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/consulta_equipos_perfil.php?id_jugador=" + id_users, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    // progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        int con=0;
                        String datta;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            id_equipo__2.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                            datta = (id_equipo__2.get(i).toString());

                            if (datta.equals(id_equ)){
                                con=1;
                            }
                        }
                        if (con==0){
                            new Perfil.CargarDato().execute("http://www.sacalapp.com/estado_notificacion.php?id_jugador="+id_users+"&id_equipo="+id_equ);
                            Toast.makeText(getApplicationContext(), "Hemos Enviado la solicitud al jugador", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "EL jugador ya pertenece a tu equipo", Toast.LENGTH_LONG).show();
                        }
                        } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "Revisa tu conexión a internet", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void Mostrarequipos() {
        id_equipo_rank.clear();
        nombre_equipo_rank.clear();
        apodo_equipo_rank.clear();
        imagen_equipo_rank.clear();

        // final ProgressDialog progressDialog = new ProgressDialog(Perfil.this);
        //progressDialog.setMessage("Cargardo Equipos");
        //progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/consulta_equipos_fichajes.php?id_jugador=" + usuaRio, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    //

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        if (jsonArray.length()==0){
                            infoma.setText("No eres capitan de un equipo");
                        }else {
                            infoma.setText("Mis equipos");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                id_equipo_rank.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                                nombre_equipo_rank.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                                apodo_equipo_rank.add(jsonArray.getJSONObject(i).getString("nick_equipo"));
                                imagen_equipo_rank.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                                listView_equipo.setAdapter(new Perfil.ImageAdater_equipo_ranking(getApplicationContext()));
                            }



                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "Revisa tu conexión a internet", Toast.LENGTH_LONG).show();

            }
        });
    }

    private class ImageAdater_equipo_ranking extends BaseAdapter {


        Context ctx;
        LayoutInflater layoutInflater;
        ImageView smartImageView_cancha;
        TextView tnombre, tdirecion;


        public ImageAdater_equipo_ranking(Context applicationContext){

            this.ctx=applicationContext;
            layoutInflater=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return id_equipo_rank.size();
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

            ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.cancha_sola_2, null);

            smartImageView_cancha = (ImageView) viewGroup.findViewById(R.id.img_cancha_portada);
            tnombre = (TextView) viewGroup.findViewById(R.id.lbl_nombre_cancha);
            tdirecion = (TextView) viewGroup.findViewById(R.id.lbl_dir_cancha);

            String nombre_="escudo_"+imagen_equipo_rank.get(pos).toString();
            String recurso="drawable";
            int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
            smartImageView_cancha.setImageResource(res_imagen);

            tnombre.setText(nombre_equipo_rank.get(pos).toString());
            tdirecion.setText(apodo_equipo_rank.get(pos).toString());
            return viewGroup;
        }


    }

    @Override
    public void onPause() {

       // Toast.makeText(getApplicationContext(), "start" ,Toast.LENGTH_LONG).show();
        startService(new Intent(Perfil.this, Notificaciones.class));
        super.onPause();
    }

    @Override
    public void onStop() {

       // Toast.makeText(getApplicationContext(), "Start" ,Toast.LENGTH_LONG).show();
        startService(new Intent(Perfil.this, Notificaciones.class));

        super.onStop();
    }

    @Override
    public void onRestart(){

        //Toast.makeText(getApplicationContext(), "stop" ,Toast.LENGTH_LONG).show();
        stopService(new Intent(Perfil.this, Notificaciones.class));
        super.onRestart();


    }

    //Apodo
    public void apodo(View v) {

        Dialogo_editar_apodo();

    }

    public void Dialogo_editar_apodo(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Perfil.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.editar_apodo,null);

        Button actu=(Button)dialogView.findViewById(R.id.btn_actualizar_apodo);
        tilApodo = (TextInputLayout)dialogView. findViewById(R.id.til_nick);

        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();

        actu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarDatos();
                dialog.dismiss();
            }

        });

        dialog.show();
    }

    private void validarDatos() {

        Apodo = tilApodo.getEditText().getText().toString();
        boolean b = esApodoValido(Apodo);
        if (b){
            new Perfil.CargarDato_apodo().execute("http://www.sacalapp.com/actualizar_apodo.php?apodo="+Apodo+"&id_usuario="+usuaRio);




        }

    }

    private boolean esApodoValido(String nombre) {
        Pattern patron = Pattern.compile("^[a-zA-Z\\s]+$"); //[a-zA-Z]
        if (!patron.matcher(nombre).matches() || nombre.length() > 30) {
            tilApodo.setError("Apodo inválido");
            return false;
        } else {
            tilApodo.setError(null);
        }

        return true;
    }

    private void actualizar_apodo(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "user", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nick",Apodo);

//Actualizamos el registro en la base de datos
        bd.update("usuario", valores, "codigo="+0, null);

        bd.close();

        bd();
    }

    private class CargarDato_apodo extends AsyncTask<String, Void, String> {
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

            actualizar_apodo();

            Snackbar.make(findViewById(android.R.id.content), "Apodo actualizado", Snackbar.LENGTH_LONG)
                    .show();
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

        }
    }

    //__________________foto_______

    private boolean mayRequestStoragePermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        //realizar cambio para hay chico

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(mRlView, "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            });
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;
    }

    private void showOptions() {
        final CharSequence[] option = {/*"Tomar foto",*/ "Elegir de galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(Perfil.this);
        builder.setTitle("Eleige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 if(option[which] == "Elegir de galeria"){
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Selecciona imagen para tu perfil"), SELECT_PICTURE);
                }else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    /*if(option[which] == "Tomar foto"){
                    openCamera();
                }else*/

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){

            String imageName =usuaRio+".jpg";//nombre de la imagen

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY+ File.separator + imageName;

            newFile = new File(mPath);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            output=Uri.fromFile(newFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);


        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mPath = savedInstanceState.getString("file_path");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {super.onActivityResult(requestCode, resultCode, data);



        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this,
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri2) {

                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + uri2);
                                }
                            });



                    //Bitmap bitmap = BitmapFactory.decodeFile(mPath);

                   /* ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] b = baos.toByteArray();
                    String image_str = Base64.encodeToString(b, Base64.DEFAULT);*/

                    ContentResolver cr=this.getContentResolver();
                    Bitmap bit= null;
                    try {

                        //Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                        bit=android.provider.MediaStore.Images.Media.getBitmap(cr,output);
                        File newFile=new File(mPath);
                        int rotate = 90;
                        ExifInterface exif = new ExifInterface(newFile.getAbsolutePath());
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                rotate = 270;
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                rotate = 180;
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                rotate = 90;
                                break;
                        }

                        Matrix matrix=new Matrix();
                        matrix.postRotate(rotate);
                        bit= Bitmap.createBitmap(bit , 0, 0, bit.getWidth(), bit.getHeight(), matrix, true);

                        Uri ur_ = data.getData();

                        //uri = Uri.parse(mPath);
                        if (uri.equals("me")){

                        }
                        cropCapturedImage(uri);
/*

                        ByteArrayOutputStream bao = new ByteArrayOutputStream();
                        bit.compress(Bitmap.CompressFormat.JPEG, 45, bao);
                        byte[] byteArray = bao.toByteArray();
                        _bytes64Sting = Base64.encodeBytes(byteArray);
                        RequestPackage rp = new RequestPackage();
                        rp.setMethod("POST");
                        rp.setUri(URL);
                        _imageFileName=Email;
                        rp.setSingleParam("base64", _bytes64Sting);
                        rp.setSingleParam("ImageName", _imageFileName + ".jpg");
                        new uploadToServer().execute(rp);
*/


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    //smartImageView.setImageBitmap(bit);
                    break;
                case SELECT_PICTURE:
                    //Uri path = data.getData();
                    uri = data.getData();
                    cropCapturedImage(uri);

                    Glide.clear(smartImageView);

  /*                  try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path);

                        ByteArrayOutputStream bao = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 45, bao);
                        byte[] byteArray = bao.toByteArray();
                        _bytes64Sting = Base64.encodeBytes(byteArray);
                        RequestPackage rp = new RequestPackage();
                        rp.setMethod("POST");
                        rp.setUri(URL);
                        _imageFileName=Email;
                        rp.setSingleParam("base64", _bytes64Sting);
                        rp.setSingleParam("ImageName", _imageFileName + ".jpg");
                        new uploadToServer().execute(rp);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/


                    break;

                case 1:

                    Bitmap imagenCortada = (Bitmap) data.getExtras().get("data");

                    ByteArrayOutputStream bao = new ByteArrayOutputStream();
                    imagenCortada.compress(Bitmap.CompressFormat.JPEG, 45, bao);
                    byte[] byteArray = bao.toByteArray();
                    _bytes64Sting = Base64.encodeBytes(byteArray);
                    RequestPackage rp = new RequestPackage();
                    rp.setMethod("POST");
                    rp.setUri(URL);
                    _imageFileName=Email;
                    rp.setSingleParam("base64", _bytes64Sting);
                    rp.setSingleParam("ImageName", _imageFileName + ".jpg");
                    new uploadToServer().execute(rp);


                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(Perfil.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                smartImageView.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Perfil.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.show();
    }

    public void cropCapturedImage(Uri urlImagen){

        //inicializamos nuestro intent
        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        cropIntent.setDataAndType(urlImagen, "image/*");

        //Habilitamos el crop en este intent
        cropIntent.putExtra("crop", "true");

        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);

        //indicamos los limites de nuestra imagen a cortar
        cropIntent.putExtra("outputX", 240);
        cropIntent.putExtra("outputY", 240);

        //True: retornara la imagen como un bitmap, False: retornara la url de la imagen la guardada.
        cropIntent.putExtra("return-data", true);

        //iniciamos nuestra activity y pasamos un codigo de respuesta.
        startActivityForResult(cropIntent, 1);
    }

    public class uploadToServer extends AsyncTask<RequestPackage, Void, String> {

        private ProgressDialog pd = new ProgressDialog(Perfil.this);

        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = MyHttpURLConnection.getData(params[0]);
            return content;

        }

        protected void onPreExecute() {
            super.onPreExecute();
            //resultText = (TextView) findViewById(R.id.textView);
            //resultText.setText("New file "+_imageFileName+".jpg created\n");
            pd.setMessage("Atualizando imagen, espere un momento");
            pd.setCancelable(false);
            pd.show();
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pd.hide();
            pd.dismiss();

            caragr_imagen();

            //bd();
           // resultText.append(result);
        }


    }

    //-----------------------------------cambiar posicion---------------------------


    public void cambiarPosicion(View v) {
        cambiaposi();
    }

    public AlertDialog cambiaposi() {

        if (posi.equals("Portero")) {
             datoP=0;
        }else if (posi.equals("Defensa")) {
             datoP=1;
        }else if (posi.equals("Medio")) {
             datoP=2;
        }else if (posi.equals("Delantero")) {
             datoP=3;
        }

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final CharSequence[] items = new CharSequence[4];

        items[0] = "Portero";
        items[1] = "Defensa";
        items[2] = "Medio";
        items[3] = "Delantero";

        builder.setTitle("Cambia tu posición ")
                .setSingleChoiceItems(items, datoP, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (aa==0){
                            da= (String) items[which];
                            new Perfil.CargarDato_actualizar().execute("http://www.sacalapp.com/actualiza_posicion.php?usuario_id=" + usuaRio+"&posicion="+da);
                            dialog.dismiss();
                        }else {
                            Toast.makeText(Perfil.this, "Solo puedes actualizar tu posición, si no tienes partidos pendiantes!", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
        builder.show();

        return builder.create();
    }

    private class CargarDato_actualizar extends AsyncTask<String, Void, String> {
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

            actualizar_posi();

        }
    }

    private void actualizar_posi() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "user", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("posicion", da);

//Actualizamos el registro en la base de datos
        bd.update("usuario", valores, "codigo=" + 0, null);

        bd.close();

        Toast.makeText(Perfil.this, "Posición Actualizada", Toast.LENGTH_SHORT).show();
        bd();


    }



    //-----------------------------------tutorail---------------------------

    @Override
    public void onClick(View view) {

        switch (contador){

            case 0:
                showcaseView.setShowcase(t1,true);
                showcaseView.setContentTitle("Nombre y apodo. Estos serán visibles a los demás usuarios para que te ubiquen. Cambia tu apodo cuando quieras");
                showcaseView.setStyle(R.style.cuerpo);
                showcaseView.setButtonText("Siguientes");
                break;

            case 1:
                showcaseView.setShowcase(t2,true);
                showcaseView.setContentTitle("Personaliza tu perfil colocando una foto o imagen que te identifique");
                showcaseView.setStyle(R.style.cuerpo);
                showcaseView.setButtonText("Siguientes");
                break;

            case 2:
                showcaseView.setShowcase(t6,true);
                showcaseView.setContentTitle("Esta es la calificación la cual es el promedio de la calificación de los capitanes rivales");
                showcaseView.setStyle(R.style.cuerpo);
                showcaseView.setButtonText("Siguientes");
                break;

            case 3:
                showcaseView.setShowcase(t4,true);
                showcaseView.setContentTitle("Tus partidos a jugar");
                                showcaseView.setStyle(R.style.cuerpo);
                showcaseView.setButtonText("Siguientes");
                break;

            case 4:
                showcaseView.setShowcase(t5,true);
                showcaseView.setContentTitle("Esta es la lista de los equipos a los que perteneces");                showcaseView.setStyle(R.style.cuerpo);
                showcaseView.setButtonText("Siguientes");
                break;

            case 5:
                showcaseView.setShowcase(t3,true);
                showcaseView.setContentTitle("Activa(azul) el botón ¿Hay Chico? para ser visible a los demás usuarios y equipos");
                showcaseView.setStyle(R.style.cuerpo);
                showcaseView.setButtonText("Siguiente");
                break;


            case 6:
                showcaseView.setShowcase(t7,true);
                showcaseView.setContentTitle("Activalo");
                showcaseView.setStyle(R.style.cuerpo);
                showcaseView.setButtonText("Activar");
                break;

            case 7:
                SharedPreferences preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferencias.edit();
                editor.putString("tuto","TutoNo");
                editor.commit();
                new CargarDato().execute("http://www.sacalapp.com/estado.php?id_usuario=" + usuaRio);
                actualizar();
                actualizar_tuto();
                showcaseView.hide();
                finish();

                break;
        }

        contador++;

    }

    private void actualizar_tuto(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "user", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("Bienvenida","SI");

//Actualizamos el registro en la base de datos
        bd.update("usuario", valores, "codigo="+0, null);

        bd.close();

        SharedPreferences preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("tuto","TutoNo");
        editor.commit();
        finish();

    }

}
