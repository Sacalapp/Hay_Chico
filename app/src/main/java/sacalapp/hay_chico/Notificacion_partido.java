package sacalapp.hay_chico;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
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

public class Notificacion_partido extends AppCompatActivity {

    private String id_equipo,id_partido,logo,capitane,id_usu;

    ArrayList id_jugador=new ArrayList();
    ArrayList id_notificacion=new ArrayList();
    ArrayList nombre_jugador=new ArrayList();
    ArrayList posicion_jugador=new ArrayList();
    ArrayList estatus=new ArrayList();
    ArrayList estado=new ArrayList();
    ArrayList foto=new ArrayList();
    ArrayList level=new ArrayList();
    private Menu mymenu;
    private String jugador_2,jugador_3,jugador_4,jugador_5,jugador_6,jugador_7,jugador_8,jugador_9;
    private String nombre2,posicion2,edad2,nick2,ciudad2,sexo2,foto_user,califica2,estado_com,estado_np,id_notificacion_,id_usu_select,campo,id_equi_1_par;

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_noti_partido);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_noti_partido);
        setSupportActionBar(toolbar);
        listView=(ListView)findViewById(R.id.list_jugadores);
        getSupportActionBar().setTitle("Notificaciones");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (capitane.equals(id_usu)){
                    final String enviar=(id_jugador.get(position).toString());
                    estado_com=(estado.get(position).toString());
                    estado_np=(estatus.get(position).toString());
                    id_notificacion_=(id_notificacion.get(position).toString());
                    id_usu_select=(id_jugador.get(position).toString());
                    new Notificacion_partido.ConsultarDatos_jugador().execute("http://www.sacalapp.com/datos_fichajes.php?email_usu="+enviar);
                }


            }
        });


        id_equipo=getIntent().getExtras().getString("tu_equipo");
        id_partido=getIntent().getExtras().getString("id_partido");
        logo=getIntent().getExtras().getString("logo");
        capitane = getIntent().getExtras().getString("capitan");
        id_usu = getIntent().getExtras().getString("id_usuario");


        new Notificacion_partido.equipo1().execute("http://www.sacalapp.com/par_equipo_1.php?id_partido="+id_partido);



    }

    private class equipo1 extends AsyncTask<String, Void, String> {
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

                id_equi_1_par=(ja.getString(0));
                traer_notificaciones_partido();


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Notificacion_partido.this, "Error", Toast.LENGTH_LONG).show();

            }

        }
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


                if (estado_np.equals("equipo") ||(estado_np.equals("invitacion"))){
                    Dialogo_usuario();
                }else {
                    Dialogo_usuario_invitado();
                }





            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Notificacion_partido.this, "Error_fichajes", Toast.LENGTH_LONG).show();

            }

        }
    }

    private class ConsultarDatos_partido extends AsyncTask<String, Void, String> {
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

                String equ1,equ2;
                equ1=(ja.getString(0));
                equ2=(ja.getString(1));

                if (id_equipo.equals(equ1)){
                    new Notificacion_partido.id_equipo1().execute("http://www.sacalapp.com/ids_equipo_1.php?id_partido="+id_partido+"&id_equipo_1="+id_equipo);
                }else if (id_equipo.equals(equ2)){
                    new Notificacion_partido.id_equipo2().execute("http://www.sacalapp.com/ids_equipo_2.php?id_partido="+id_partido+"&id_equipo_2="+id_equipo);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Notificacion_partido.this, "Error_fichajes", Toast.LENGTH_LONG).show();

            }

        }
    }

    private class id_equipo1 extends AsyncTask<String, Void, String> {
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

                for (int i=0;i<ja.length();i++){
                    String dato=ja.get(i).toString();

                    if (dato.equals(id_usu_select)){

                        switch (i){
                            case 1:
                                campo="jg_2";
                                break;
                            case 2:
                                campo="jg_3";
                                break;
                            case 3:
                                campo="jg_4";
                                break;
                            case 4:
                                campo="jg_5";
                                break;
                            case 5:
                                campo="jg_6";
                                break;
                            case 6:
                                campo="jg_7";
                                break;
                            case 7:
                                campo="jg_8";
                                break;
                            case 8:
                                campo="jg_9";
                                break;
                        }

                    }
                }

                new Notificacion_partido.CargarDatos().execute("http://www.sacalapp.com/actualizar_confirmado.php?id_notificacion="+id_notificacion_+"&campo="+campo+"&id_partido="+id_partido);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Notificacion_partido.this, "Error_fichajes", Toast.LENGTH_LONG).show();

            }

        }
    }

    private class id_equipo2 extends AsyncTask<String, Void, String> {
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

                for (int i=0;i<ja.length();i++){
                    String dato=ja.get(i).toString();

                    if (dato.equals(id_usu_select)){

                        switch (i){
                            case 1:
                                campo="jg_11";
                                break;
                            case 2:
                                campo="jg_12";
                                break;
                            case 3:
                                campo="jg_13";
                                break;
                            case 4:
                                campo="jg_14";
                                break;
                            case 5:
                                campo="jg_15";
                                break;
                            case 6:
                                campo="jg_16";
                                break;
                            case 7:
                                campo="jg_17";
                                break;
                            case 8:
                                campo="jg_18";
                                break;
                        }

                    }
                }

                new Notificacion_partido.CargarDatos().execute("http://www.sacalapp.com/actualizar_confirmado.php?id_notificacion="+id_notificacion_+"&campo="+campo+"&id_partido="+id_partido);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Notificacion_partido.this, "Error_fichajes", Toast.LENGTH_LONG).show();

            }

        }
    }

    public void Dialogo_usuario(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Notificacion_partido.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.datos_equipo_partido,null);

        builder.setView(dialogView);

        TextView nomb =(TextView)dialogView.findViewById(R.id.lbl_nombre_fichaje);
        TextView estado2 =(TextView)dialogView.findViewById(R.id.lbl_estatus_partido);

        RatingBar ratinCali_2 = (RatingBar)dialogView.findViewById(R.id.ratingBar_calificacion_fichajes);
        CircleImageView imagen_fichaje =(CircleImageView)dialogView.findViewById(R.id.ima_fichajes);
        Button one = (Button) dialogView.findViewById(R.id.btn_Salir);
        Button two = (Button) dialogView.findViewById(R.id.btn_opcion);

        nomb.setText(nombre2);

        String urlfinal="http://sacalapp.com/jarvicf/img_users/"+ foto_user;

        Glide.with(imagen_fichaje.getContext())
                .load(urlfinal)
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.perfil)
                .crossFade()
                .signature(new StringSignature(urlfinal))
                .into(imagen_fichaje);

        Float cali_2=Float.parseFloat(califica2);
        ratinCali_2.setRating(cali_2);

        one.setText("Volver");

        final AlertDialog dialog = builder.create();

        one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (estado_com.equals("EnEspera")){
            two.setText("Cancelar Invitación");
            estado2.setText("Pendiente por confirma asistencia");
        }else  if (estado_com.equals("Aceptada")){
            two.setText("Sacar del partido");
            estado2.setText("Asistira a el partido");
        }else  if (estado_com.equals("Rechazada")){
            estado2.setText("No Asistira a el partido");
            two.setText("Invitar");
        }else  if (estado_com.equals("cancelado")) {
            estado2.setText("No Asistira a el partido");
            two.setText("Invitar");
        }

        two.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (estado_com.equals("EnEspera")){
                    new Notificacion_partido.CargarDatos().execute("http://www.sacalapp.com/actualizar_enespera.php?id_notificacion="+id_notificacion_);
                }else  if (estado_com.equals("Aceptada")){
                    new Notificacion_partido.ConsultarDatos_partido().execute("http://www.sacalapp.com/id_equipos_partido.php?id_partido="+id_partido);
                }else  if (estado_com.equals("Rechazada")){
                    new Notificacion_partido.CargarDatos().execute("http://www.sacalapp.com/actualizar_aceptado.php?id_notificacion="+id_notificacion_);
                }else  if (estado_com.equals("cancelado")){
                    new Notificacion_partido.CargarDatos().execute("http://www.sacalapp.com/actualizar_aceptado.php?id_notificacion="+id_notificacion_);
                }
                dialog.dismiss();
            }
        });


        dialog.show();
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
            traer_notificaciones_partido();
        }
    }

    public void Dialogo_usuario_invitado(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Notificacion_partido.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.usuarios_datos_2,null);

        builder.setView(dialogView);

        TextView nomb =(TextView)dialogView.findViewById(R.id.lbl_nombre_fichaje);
        TextView estado2 =(TextView)dialogView.findViewById(R.id.lbl_estatus_partido);
        TextView edad_2 =(TextView)dialogView.findViewById(R.id.lbl_eda_fichajes);

        RatingBar ratinCali_2 = (RatingBar)dialogView.findViewById(R.id.ratingBar_calificacion_fichajes);
        CircleImageView  imagen_fichaje =(CircleImageView)dialogView.findViewById(R.id.ima_fichajes);
        Button one = (Button) dialogView.findViewById(R.id.btn_Salir);
        Button two = (Button) dialogView.findViewById(R.id.btn_rechazar);
        Button theer = (Button) dialogView.findViewById(R.id.btn_aceptar);

        nomb.setText(nombre2);
        edad_2.setText(edad2+" Años");
        String urlfinal="http://sacalapp.com/jarvicf/"+foto_user.toString();


        Glide.with(imagen_fichaje.getContext())
                .load(urlfinal)
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.perfil)
                .signature(new StringSignature(urlfinal))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imagen_fichaje);
        Float cali_2=Float.parseFloat(califica2);

        ratinCali_2.setRating(cali_2);

        one.setText("Volver");

        final AlertDialog dialog = builder.create();

        estado2.setText("¿Hay Chico? para mi!");
        two.setText("Si");
        theer.setText("No");
        one.setText("atras");

        one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        two.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            if (id_equipo.equals(id_equi_1_par)){
                new Notificacion_partido.ConsultarDatos_equipo_1().execute("http://www.sacalapp.com/traer_equipos_partidos.php?id_equipo="+id_equipo+"&id_partido="+id_partido);
                dialog.dismiss();
            }else {
                new Notificacion_partido.ConsultarDatos_equipo_2().execute("http://www.sacalapp.com/traer_equipos_partidos_2.php?id_equipo="+id_equipo+"&id_partido="+id_partido);
                dialog.dismiss();
            }

            }
        });
        theer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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


                jugador_2=(ja.getString(0));
                jugador_3=(ja.getString(1));
                jugador_4=(ja.getString(2));
                jugador_5=(ja.getString(3));
                jugador_6=(ja.getString(4));
                jugador_7=(ja.getString(5));
                jugador_8=(ja.getString(6));
                jugador_9=(ja.getString(7));

                if(jugador_2.equals("null")){
                    campo="jg_2";
                }else  if(jugador_3.equals("null")){
                    campo="jg_3";
                }else if(jugador_4.equals("null")){
                    campo="jg_4";
                }else if(jugador_5.equals("null")){
                    campo="jg_5";
                }else if(jugador_6.equals("null")){
                    campo="jg_6";
                }else if(jugador_7.equals("null")){
                    campo="jg_7";
                }else if(jugador_8.equals("null")){
                    campo="jg_8";
                }else if(jugador_9.equals("null")){
                    campo="jg_9";
                }

               new Notificacion_partido.CargarDato_2().execute("http://www.sacalapp.com/confirma_asistencia_partido.php?id_notificacion="+id_notificacion_+"&campo="+campo+"&id_usuario="+id_usu_select+"&id_partido="+id_partido);
                Toast.makeText(Notificacion_partido.this, "Has confirmado tu asistencia al partido", Toast.LENGTH_LONG).show();
                listView.clearFocus();


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Notificacion_partido.this, "Error", Toast.LENGTH_LONG).show();

            }

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


                jugador_2=(ja.getString(0));
                jugador_3=(ja.getString(1));
                jugador_4=(ja.getString(2));
                jugador_5=(ja.getString(3));
                jugador_6=(ja.getString(4));
                jugador_7=(ja.getString(5));
                jugador_8=(ja.getString(6));
                jugador_9=(ja.getString(7));

                if(jugador_2.equals("null")){
                    campo="jg_11";
                }else  if(jugador_3.equals("null")){
                    campo="jg_12";
                }else if(jugador_4.equals("null")){
                    campo="jg_13";
                }else if(jugador_5.equals("null")){
                    campo="jg_14";
                }else if(jugador_6.equals("null")){
                    campo="jg_15";
                }else if(jugador_7.equals("null")){
                    campo="jg_16";
                }else if(jugador_8.equals("null")){
                    campo="jg_17";
                }else if(jugador_9.equals("null")){
                    campo="jg_18";
                }


                new Notificacion_partido.CargarDato_2().execute("http://www.sacalapp.com/confirma_asistencia_partido.php?id_notificacion="+id_notificacion_+"&campo="+campo+"&id_usuario="+id_usu_select+"&id_partido="+id_partido);
                Toast.makeText(Notificacion_partido.this, "Has confirmado tu asistencia al partido", Toast.LENGTH_LONG).show();
                listView.clearFocus();

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Notificacion_partido.this, "Error", Toast.LENGTH_LONG).show();

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.comun, menu);
        mymenu = menu;
        //menuItem = menu.findItem(R.id.notificaciones);

        menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return super.onCreateOptionsMenu(menu);

    }

    public void resetUpdating() {
        // Get our refresh item from the menu
        MenuItem m = mymenu.findItem(R.id.Actualizar);
        if(m.getActionView()!=null)
        {
            // Remove the animation.
            m.getActionView().clearAnimation();
            m.setActionView(null);
        }

    }

    private void traer_notificaciones_partido() {

        id_jugador.clear();
        nombre_jugador.clear();
        posicion_jugador.clear();
        estatus.clear();
        estado.clear();
        foto.clear();
        id_notificacion.clear();
        level.clear();

        final ProgressDialog progressDialog = new ProgressDialog(Notificacion_partido.this);
        progressDialog.setMessage("Cargardo...");
        progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/notificacion_detalle_equipo_.php?id_partido="+id_partido+"&id_equipo="+id_equipo, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    progressDialog.dismiss();
                    resetUpdating();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i=0;i<jsonArray.length();i++){

                            id_jugador.add(jsonArray.getJSONObject(i).getString("usuario_id"));
                            nombre_jugador.add(jsonArray.getJSONObject(i).getString("nombre"));
                            posicion_jugador.add(jsonArray.getJSONObject(i).getString("posicion"));
                            estatus.add(jsonArray.getJSONObject(i).getString("estado_NP"));
                            estado.add(jsonArray.getJSONObject(i).getString("estadoo"));
                            foto.add(jsonArray.getJSONObject(i).getString("foto"));
                            id_notificacion.add(jsonArray.getJSONObject(i).getString("id_notificacion"));
                            level.add(jsonArray.getJSONObject(i).getString("level"));



                            listView.setAdapter(new Notificacion_partido.ImageAdater(getApplicationContext()));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error al traer datos", Toast.LENGTH_LONG).show();

                    }
                }else {
                    resetUpdating();
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
        CircleImageView smartImageView;
        ImageView tu_equipo,img_le;
        TextView tnombre,tposicion,tinfo;
        float cali;


        public ImageAdater(Context applicationContext){

            this.ctx=applicationContext;
            layoutInflater=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return nombre_jugador.size();
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

            ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.jugador_equipo_notificacio,null);



             smartImageView =(CircleImageView) viewGroup.findViewById(R.id.img_jugador_noti);
            tu_equipo =(ImageView) viewGroup.findViewById(R.id.img_tu_equipo);
            img_le =(ImageView) viewGroup.findViewById(R.id.img_Level_);

            String com=estatus.get(pos).toString();
            if (com.equals("equipo")){

            }else if (com.equals("invitacion")) {
                tu_equipo.setImageResource(R.drawable.ic_invitacion);
            }else {
                tu_equipo.setImageResource(R.drawable.ic_info);
            }

            String le=level.get(pos).toString();

            if (le.equals("Recreacion")){
                img_le.setImageResource(R.drawable.ic_recreacion);
            }else if (le.equals("Amateur")){
                img_le.setImageResource(R.drawable.ic_amateur);
            }else if (le.equals("SemiPro")){
                img_le.setImageResource(R.drawable.ic_semipro);
            }else if (le.equals("Pro")){
                img_le.setImageResource(R.drawable.ic_pro);
            }else if (le.equals("Leyenda")){
                img_le.setImageResource(R.drawable.ic_leyenda);
            }else if (le.equals("null")){
                img_le.setImageResource(R.drawable.ic_sin);
            }


            tnombre =(TextView) viewGroup.findViewById(R.id.lbl_nombre_jugador_noti);
            tposicion =(TextView) viewGroup.findViewById(R.id.lbl_posicion_jugador_noti);
            tinfo =(TextView)viewGroup.findViewById(R.id.lbl_info_noti);

            String urlfinal="http://sacalapp.com/jarvicf/img_users/"+foto.get(pos).toString();

            Glide.with(smartImageView.getContext())
                    .load(urlfinal)
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.drawable.perfil)
                    .crossFade()
                    .signature(new StringSignature(urlfinal))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    //.skipMemoryCache(true)
                    .into(smartImageView);

            tnombre.setText(nombre_jugador.get(pos).toString());
            tposicion.setText(posicion_jugador.get(pos).toString());

            final String enviar=(estado.get(pos).toString());


            if (com.equals("equipo") || (com.equals("invitacion"))){

                if(enviar.equals("EnEspera")){
                    tinfo.setText("Pendiente por confirmar");
                    tu_equipo.setImageResource(R.drawable.ic_enespera);
                }else  if(enviar.equals("Aceptada")){
                    tinfo.setText("Ha confirmado asistencia");
                    tu_equipo.setImageResource(R.drawable.ic_check);
                }else if(enviar.equals("Rechazada")){
                    tinfo.setText("no asistira a el partido")
                    ;tu_equipo.setImageResource(R.drawable.ic_cancelado);
                }else if(enviar.equals("cancelado")){
                    tinfo.setText("has cancelado");
                    tu_equipo.setImageResource(R.drawable.ic_cancelado);
                }
            }else {

                if(enviar.equals("EnEspera")){
                    tinfo.setText("Quiere jugar este partido  con tu equipo");
                }else  if(enviar.equals("Aceptada")){
                    tinfo.setText("Has aceptado la solicitud");
                }else if(enviar.equals("Rechazada")){
                    tinfo.setText("Has rechazado la colicitu");
                }
            }


            return viewGroup;
        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Actualizar:

                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ImageView iv = (ImageView)inflater.inflate(R.layout.iv_refresh, null);
                Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate_refresh);
                rotation.setRepeatCount(Animation.INFINITE);
                iv.startAnimation(rotation);
                item.setActionView(iv);
                //new UpdateTask(this).execute();
                traer_notificaciones_partido();
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

    @Override
    public void onPause() {

        // Toast.makeText(getApplicationContext(), "start" ,Toast.LENGTH_LONG).show();
        startService(new Intent(Notificacion_partido.this, Notificaciones.class));
        super.onPause();
    }

    @Override
    public void onStop() {

        // Toast.makeText(getApplicationContext(), "Start" ,Toast.LENGTH_LONG).show();
        startService(new Intent(Notificacion_partido.this, Notificaciones.class));

        super.onStop();
    }

    @Override
    public void onRestart(){

        //Toast.makeText(getApplicationContext(), "stop" ,Toast.LENGTH_LONG).show();
        stopService(new Intent(Notificacion_partido.this, Notificaciones.class));
        super.onRestart();


    }



}
