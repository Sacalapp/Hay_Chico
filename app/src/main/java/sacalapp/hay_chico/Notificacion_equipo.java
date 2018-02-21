package sacalapp.hay_chico;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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


public class Notificacion_equipo extends AppCompatActivity {

    ArrayList id_equipo=new ArrayList();
    ArrayList nombre_equipo=new ArrayList();
    ArrayList calificacion_equipo=new ArrayList();
    ArrayList logo_equipo=new ArrayList();
    ArrayList id_reserva=new ArrayList();
    ArrayList id_notificacion=new ArrayList();
    ArrayList capitan=new ArrayList();
    ArrayList partido=new ArrayList();
    int dato=0,DATO=0;
    private String j2,j2_,j3,j3_,j4,j4_,j5,j5_,j6,j6_,j7,j7_,j8,j8_,j9,j9_,j10,j11,j12;
    private String id_partido_,partido_,id_equipo_e,id_usuario,capitan_, nombre_equipo_,id_equipo_,logo_equipo_,id_reserva_,fecha_partido,hora_partido,id_cancha,nombre_cancha,imagen_cancha_,cali,id_notificacion_;
   float califica;
    ListView listView;
    TextView noti;
    private Menu mymenu;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_noti_equipo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_noti_equipo);
        setSupportActionBar(toolbar);
        id_equipo_e=getIntent().getExtras().getString("id_equipo");
        id_usuario=getIntent().getExtras().getString("id_jugador");
        getSupportActionBar().setTitle("Notificaciones equipo");
        listView=(ListView)findViewById(R.id.listView_noti);
        pd = new ProgressDialog(Notificacion_equipo.this);

        noti=(TextView)findViewById(R.id.lbl_notificacion_equipo);
        traer_notificaciones();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                capitan_=(capitan.get(position).toString());
                id_equipo_=(id_equipo.get(position).toString());
                nombre_equipo_=(nombre_equipo.get(position).toString());
                logo_equipo_=(logo_equipo.get(position).toString());
                id_reserva_=(id_reserva.get(position).toString());
                id_notificacion_=(id_notificacion.get(position).toString());
                cali=(calificacion_equipo.get(position).toString());
                partido_=(partido.get(position).toString());

                if (partido_.equals("3")){
                    new Notificacion_equipo.traer_id_reserva().execute("http://www.sacalapp.com/id_reserva.php?id_reserva=" + id_reserva_);
                }else  if (partido_.equals("2")){
                    new Notificacion_equipo.traer_id_reserva().execute("http://www.sacalapp.com/id_reserva_off.php?id_reserva=" + id_reserva_);
                }


           }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(getIntent().getExtras().getInt("notificationID4"));

    }

    private class traer_id_reserva extends AsyncTask<String, Void, String> {
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


                fecha_partido=(ja.getString(1).toString());
                hora_partido=(ja.getString(2).toString());
                id_cancha=(ja.getString(3).toString());

                new Notificacion_equipo.traer_nom_cancha().execute("http://www.sacalapp.com/traer_info_cancha.php?id_cancha=" + id_cancha);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Notificacion_equipo.this, "Error al traer Reserva", Toast.LENGTH_SHORT).show();

            }
        }
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

            JSONArray ja = null;
            try {
                ja = new JSONArray(result);

                nombre_cancha=(ja.getString(0).toString());
                imagen_cancha_=(ja.getString(1).toString());
                detalles_equipo();



            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Notificacion_equipo.this, "No tienes ninguna reserva", Toast.LENGTH_SHORT).show();

            }
        }
    }

    public AlertDialog confirmar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new Notificacion_equipo());

        builder.setTitle("Importante")
                .setMessage("Seguro deseas rechazar")
                .setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        });

        return builder.create();
    }

    public void detalles_equipo(){

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Notificacion_equipo.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.invitacio_equipo,null);

        builder.setView(dialogView);

        RatingBar calificacion_eq=(RatingBar)dialogView.findViewById(R.id.rating_cali_equi);

        TextView info =(TextView)dialogView.findViewById(R.id.lbl_info_invitacion);
        TextView fecha =(TextView)dialogView.findViewById(R.id.lbl_fecha_par);
        TextView cancha =(TextView)dialogView.findViewById(R.id.lbl_nombre_cancha);
        TextView hora =(TextView)dialogView.findViewById(R.id.lbl_hora_par);
        SmartImageView imagen_fichaje =(SmartImageView)dialogView.findViewById(R.id.smartImageView_equipo);
        SmartImageView imagen_cancha =(SmartImageView)dialogView.findViewById(R.id.smartImageView_logo_cancha);

        AppCompatButton rechazar = (AppCompatButton)dialogView.findViewById(R.id.btn_rechazar);
        AppCompatButton volver = (AppCompatButton)dialogView.findViewById(R.id.btn_volver);
        AppCompatButton aceptar = (AppCompatButton)dialogView.findViewById(R.id.btn_aceptar);


        califica=Float.parseFloat(cali.toString());
        //cali=
        calificacion_eq.setRating(califica);

        cancha.setText(nombre_cancha);
        fecha.setText(fecha_partido);
        //hora.setText(hora_partido);

        if (hora_partido.equals("8:00")){
            hora.setText("800AM");
        }else  if (hora_partido.equals("9:00")){
            hora.setText("9:00AM");
        }else if (hora_partido.equals("10:00")){
            hora.setText("10:00AM");
        }else  if (hora_partido.equals("11:00")){
            hora.setText("11:00AM");
        }else  if (hora_partido.equals("12:00")){
            hora.setText("12:00PM");
        }else if (hora_partido.equals("13:00")){
            hora.setText("1:00PM");
        }else  if (hora_partido.equals("14:00")){
            hora.setText("2:00PM");
        }else  if (hora_partido.equals("15:00")){
            hora.setText("3:00PM");
        }else  if (hora_partido.equals("16:00")){
            hora.setText("4:00PM");
        }else  if (hora_partido.equals("17:00")){
            hora.setText("5:00PM");
        }else  if (hora_partido.equals("18:00")){
            hora.setText("6:00PM");
        }else  if (hora_partido.equals("19:00")){
            hora.setText("7:00PM");
        }else  if (hora_partido.equals("20:00")){
            hora.setText("8:00PM");
        }else  if (hora_partido.equals("21:00")){
            hora.setText("9:00PM");
        }else  if (hora_partido.equals("22:00")){
            hora.setText("10:00PM");
        }else  if (hora_partido.equals("23:00")){
            hora.setText("11:00PM");
        }else  if (hora_partido.equals("24:00")){
            hora.setText("12:00PM");
        }
        info.setText( nombre_equipo_+ " " +"Quiere invitarte a un partido amistoso!?");


        String nombre_="escudo_"+logo_equipo_;
        String recurso="drawable";
        int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
        imagen_fichaje.setImageResource(res_imagen);

        String urlfinal="http://sacalapp.com/jarvicf/canchas_img/"+imagen_cancha_;

        Glide.with(imagen_cancha.getContext())
                .load(urlfinal)
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.ic_escudo)
                .signature(new StringSignature(urlfinal))
                .into(imagen_cancha);


        final android.support.v7.app.AlertDialog dialog = builder.create();

        volver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        rechazar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DATO=1;
                if (partido_.equals("3")){
                    new Notificacion_equipo.CargarDato().execute("http://www.sacalapp.com/rechazo_partido.php?id_notificacion="+id_notificacion_+"&id_reserva="+id_reserva_);
                }else    if (partido_.equals("2")){
                    new Notificacion_equipo.CargarDato().execute("http://www.sacalapp.com/rechazo_partido_off.php?id_notificacion="+id_notificacion_+"&id_reserva="+id_reserva_);
                }
                perfil();
                dialog.dismiss();
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DATO=2;

                if (partido_.equals("3")){
                    new Notificacion_equipo.CargarDato().execute("http://www.sacalapp.com/crear_partido.php?id_equipo1="+id_equipo_+"&id_equipo2="+id_equipo_e+"&id_reserva="+id_reserva_+"&id_capi_1="+capitan_+"&id_capi_2="+id_usuario+"&Notificacion_id="+id_notificacion_);
                }else    if (partido_.equals("2")){
                    new Notificacion_equipo.CargarDato().execute("http://www.sacalapp.com/crear_partido_off.php?id_equipo1="+id_equipo_+"&id_equipo2="+id_equipo_e+"&id_reserva="+id_reserva_+"&id_capi_1="+capitan_+"&id_capi_2="+id_usuario+"&Notificacion_id="+id_notificacion_);
                }

                dialog.dismiss();

            }
        });



        dialog.show();
    }

    private void perfil() {

        pd.dismiss();
        Intent intent = new Intent(Notificacion_equipo.this, Perfil.class);
        startActivity(intent);
        finish();
    }

    private class equipo_1 extends AsyncTask<String, Void, String> {
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

                j2=(ja.getString(5).toString());
                j3=(ja.getString(6).toString());
                j4=(ja.getString(7).toString());
                j5=(ja.getString(8).toString());
                j6=(ja.getString(9).toString());
                j7=(ja.getString(10).toString());
                j8=(ja.getString(11).toString());
                j9=(ja.getString(12).toString());
                j10=(ja.getString(14).toString());
                j11=(ja.getString(15).toString());
                j12=(ja.getString(16).toString());

                if(j2!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_e+"&id_equipo2="+id_equipo_+"&id_jugador="+j2+"&id_partido="+id_partido_);

                } if(j3!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_e+"&id_equipo2="+id_equipo_+"&id_jugador="+j3+"&id_partido="+id_partido_);

                } if(j4!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_e+"&id_equipo2="+id_equipo_+"&id_jugador="+j4+"&id_partido="+id_partido_);

                } if(j5!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_e+"&id_equipo2="+id_equipo_+"&id_jugador="+j5+"&id_partido="+id_partido_);

                } if(j6!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_e+"&id_equipo2="+id_equipo_+"&id_jugador="+j6+"&id_partido="+id_partido_);

                } if(j7!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_e+"&id_equipo2="+id_equipo_+"&id_jugador="+j7+"&id_partido="+id_partido_);

                } if(j8!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_e+"&id_equipo2="+id_equipo_+"&id_jugador="+j8+"&id_partido="+id_partido_);

                } if(j9!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_e+"&id_equipo2="+id_equipo_+"&id_jugador="+j9+"&id_partido="+id_partido_);

                }if(j10!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_e+"&id_equipo2="+id_equipo_+"&id_jugador="+j10+"&id_partido="+id_partido_);

                }if(j11!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_e+"&id_equipo2="+id_equipo_+"&id_jugador="+j11+"&id_partido="+id_partido_);

                }if(j12!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_e+"&id_equipo2="+id_equipo_+"&id_jugador="+j12+"&id_partido="+id_partido_);

                }


                new Notificacion_equipo.equipo_2().execute("http://www.sacalapp.com/equipo_datos_12.php?dato=" + id_equipo_);



            } catch (JSONException e) {
                pd.dismiss();
                e.printStackTrace();
                Toast.makeText(Notificacion_equipo.this, "Error", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private class equipo_2 extends AsyncTask<String, Void, String> {
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


                j2=(ja.getString(5).toString());
                j3=(ja.getString(6).toString());
                j4=(ja.getString(7).toString());
                j5=(ja.getString(8).toString());
                j6=(ja.getString(9).toString());
                j7=(ja.getString(10).toString());
                j8=(ja.getString(11).toString());
                j9=(ja.getString(12).toString());
                j10=(ja.getString(14).toString());
                j11=(ja.getString(15).toString());
                j12=(ja.getString(16).toString());

                if(j2!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_+"&id_equipo2="+id_equipo_e+"&id_jugador="+j2+"&id_partido="+id_partido_);

                } if(j3!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_+"&id_equipo2="+id_equipo_e+"&id_jugador="+j3+"&id_partido="+id_partido_);

                } if(j4!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_+"&id_equipo2="+id_equipo_e+"&id_jugador="+j4+"&id_partido="+id_partido_);

                } if(j5!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_+"&id_equipo2="+id_equipo_e+"&id_jugador="+j5+"&id_partido="+id_partido_);

                } if(j6!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_+"&id_equipo2="+id_equipo_e+"&id_jugador="+j6+"&id_partido="+id_partido_);

                } if(j7!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_+"&id_equipo2="+id_equipo_e+"&id_jugador="+j7+"&id_partido="+id_partido_);

                } if(j8!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_+"&id_equipo2="+id_equipo_e+"&id_jugador="+j8+"&id_partido="+id_partido_);

                } if(j9!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_+"&id_equipo2="+id_equipo_e+"&id_jugador="+j9+"&id_partido="+id_partido_);
                }if(j10!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_e+"&id_equipo2="+id_equipo_+"&id_jugador="+j10+"&id_partido="+id_partido_);

                }if(j11!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_e+"&id_equipo2="+id_equipo_+"&id_jugador="+j11+"&id_partido="+id_partido_);

                }if(j12!="null"){

                    new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacion_partido.php?id_equipo1="+id_equipo_e+"&id_equipo2="+id_equipo_+"&id_jugador="+j12+"&id_partido="+id_partido_);

                }


                dato=0;
                new Notificacion_equipo.Cargar_Califica().execute("http://www.sacalapp.com/calificacion_equipo.php?id_partido="+id_partido_+"&id_capi="+capitan_+"&equipo="+id_equipo_e);



            } catch (JSONException e) {
                pd.dismiss();
                e.printStackTrace();
                Toast.makeText(Notificacion_equipo.this, "Error", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void traer_notificaciones() {

        id_notificacion.clear();
        capitan.clear();
        id_equipo.clear();
        id_reserva.clear();
        nombre_equipo.clear();
        calificacion_equipo.clear();
        logo_equipo.clear();
        partido.clear();
        listView.clearFocus();


        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/detalles_Notificacion_equipo.php?id_equipo="+id_equipo_e, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){


                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        if(jsonArray.length()!=0){
                            noti.setVisibility(View.INVISIBLE);
                            listView.setVisibility(View.VISIBLE);

                        }
                        for (int i=0;i<jsonArray.length();i++){

                            id_equipo.add(jsonArray.getJSONObject(i).getString("id_equipo"));
                            nombre_equipo.add(jsonArray.getJSONObject(i).getString("nombre_equipo"));
                            calificacion_equipo.add(jsonArray.getJSONObject(i).getString("calificacion_equipo"));
                            logo_equipo.add(jsonArray.getJSONObject(i).getString("logo_equipo"));
                            id_reserva.add(jsonArray.getJSONObject(i).getString("id_reserva"));
                            id_notificacion.add(jsonArray.getJSONObject(i).getString("Notificacion_id"));
                            capitan.add(jsonArray.getJSONObject(i).getString("jugador_1_cap"));
                            partido.add(jsonArray.getJSONObject(i).getString("partido"));


                            listView.setAdapter(new Notificacion_equipo.ImageAdater(getApplicationContext()));

                        }

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

    private class ImageAdater extends BaseAdapter {


        RatingBar ratinCali;
        Context ctx;
        LayoutInflater layoutInflater;
        ImageView smartImageView;
        TextView tnombre,tinfo;


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

//            Toast.makeText(Fichajes.this, "Usuario "+position, Toast.LENGTH_SHORT).show();

            return position;
        }

        @Override
        public long getItemId(int position) {



            //Toast.makeText(Fichajes.this, "Usuario selecionado"+position, Toast.LENGTH_SHORT).show();
            return position;

        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {

            ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.equipo_solo_2,null);



            smartImageView =(ImageView)viewGroup.findViewById(R.id.img_equipo_perfil);



            tnombre =(TextView) viewGroup.findViewById(R.id.lbl_equipo_perfil);
            tinfo =(TextView) viewGroup.findViewById(R.id.lbl_apodo_equipo_perfil);


           /* String urlfinal="http://sacalapp.com/jarvicf/escudo_equipos/"+logo_equipo.get(pos).toString();
            Rect rect = new Rect(smartImageView.getLeft(),smartImageView.getTop(),smartImageView.getRight(),smartImageView.getBottom());
            smartImageView.setImageUrl(urlfinal,rect);*/

            String nombre_="escudo_"+logo_equipo.get(pos).toString();
            String recurso="drawable";
            int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
            smartImageView.setImageResource(res_imagen);


            tnombre.setText(nombre_equipo.get(pos).toString());
            tinfo.setText("Quiere invitarte a un partido");


            return viewGroup;
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

            pd.setMessage("Cargando...");
            pd.setCancelable(true);
            pd.show();

            if (DATO==1){
                new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacione_no_acepta.php?id_equipo1="+id_equipo_+"&id_equipo2="+id_equipo_e+"&id_capi_2="+capitan_);
                perfil();
            }else if(DATO==2){
                new Notificacion_equipo.CargarDato_2().execute("http://www.sacalapp.com/notificacione_acepta.php?id_equipo1="+id_equipo_+"&id_equipo2="+id_equipo_e+"&id_capi_2="+capitan_);
                new Notificacion_equipo.id_partido().execute("http://www.sacalapp.com/id_partido.php?id_equipo1="+id_equipo_+"&id_equipo2="+id_equipo_e+"&id_reserva="+id_reserva_);
            }


            //Toast.makeText(getApplicationContext(), "Hemos Enviado la solicitud a " +nombre_equipo_2, Toast.LENGTH_LONG).show();

        }
    }

    private class Cargar_Califica extends AsyncTask<String, Void, String> {
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

            if (dato==0){
                new Notificacion_equipo.Cargar_Califica().execute("http://www.sacalapp.com/calificacion_equipo.php?id_partido="+id_partido_+"&id_capi="+id_usuario+"&equipo="+id_equipo_);
                dato=1;
            }else if(dato==1){
                listView.setAdapter(null);
                finish();
                pd.dismiss();
            }

            //Toast.makeText(getApplicationContext(), "Hemos Enviado la solicitud a " +nombre_equipo_2, Toast.LENGTH_LONG).show();

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



            //Toast.makeText(getApplicationContext(), "Hemos Enviado la solicitud a " +nombre_equipo_2, Toast.LENGTH_LONG).show();

        }
    }

    private class id_partido extends AsyncTask<String, Void, String> {
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
                id_partido_=(ja.getString(0).toString());


                new Notificacion_equipo.equipo_1().execute("http://www.sacalapp.com/equipo_datos_12.php?dato=" + id_equipo_e);


            } catch (JSONException e) {
                pd.dismiss();
                e.printStackTrace();
                //Toast.makeText(Notificacion_equipo.this, "Error al traer Reserva", Toast.LENGTH_SHORT).show();

            }
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
               traer_notificaciones();
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
