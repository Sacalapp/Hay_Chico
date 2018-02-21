package sacalapp.hay_chico;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class Notificacion_reservas_equipos extends AppCompatActivity {

    private String id_equipo_e,id_usuario,nombre_equipo;
    ListView listView;
    ArrayList id_reserva=new ArrayList();
    ArrayList cancha_nombre=new ArrayList();
    ArrayList imagen_cancha=new ArrayList();
    ArrayList hora_inicio=new ArrayList();
    ArrayList fecha_partido=new ArrayList();
    ArrayList estatus=new ArrayList();

    ArrayList tipo_reserva=new ArrayList();
    ArrayList tipo_reserva2=new ArrayList();

    ArrayList id_reserva_2=new ArrayList();
    ArrayList cancha_nombre_2=new ArrayList();
    ArrayList imagen_cancha_2=new ArrayList();
    ArrayList hora_inicio_2=new ArrayList();
    ArrayList fecha_partido_2=new ArrayList();
    ArrayList estatus_2=new ArrayList();

    TextView reser;
    private String fecha_,hora_,img,nomb_cam,enviar,est,par;
    ProgressDialog pd;
    private int dato1,dato2,dato3,dato4,datot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_reservas_equipo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_reser);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tus Reservas");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pd = new ProgressDialog(Notificacion_reservas_equipos.this);

        id_equipo_e=getIntent().getExtras().getString("id_equipo");
        id_usuario=getIntent().getExtras().getString("id_jugador");
        nombre_equipo=getIntent().getExtras().getString("nombre_equipo");
        reser=(TextView)findViewById(R.id.lbl_reservas_equipo);
        listView=(ListView)findViewById(R.id.listView_res);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                  enviar=(id_reserva.get(position).toString());
                  fecha_=(fecha_partido.get(position).toString());
                  hora_=(hora_inicio.get(position).toString());
                  img=(imagen_cancha.get(position).toString());
                  nomb_cam=(cancha_nombre.get(position).toString());
                est=(estatus.get(position).toString());
                par=(tipo_reserva.get(position).toString());

                if  (est.equals( "no" )){

                    Dialogo_reserva_2();

                }else {
                    Dialogo_reserva();
                }


            }
        });

        TraerReservasi();


    }

    public void Dialogo_reserva(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Notificacion_reservas_equipos.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.info_reserva_noti,null);

        builder.setView(dialogView);

        TextView hora =(TextView)dialogView.findViewById(R.id.lbl_hora_partido);
        TextView fecha =(TextView)dialogView.findViewById(R.id.lbl_fecha_partido);
        TextView info =(TextView)dialogView.findViewById(R.id.lbl_info_de_reserva);
        TextView dia =(TextView)dialogView.findViewById(R.id.lbl_dia_semana);
        SmartImageView imagen_fichaje =(SmartImageView)dialogView.findViewById(R.id.img_equipo_perfil);
        ImageView img_estado =(ImageView)dialogView.findViewById(R.id.img_estatus);
        Button one = (Button) dialogView.findViewById(R.id.btn_cancelar);
        Button two = (Button) dialogView.findViewById(R.id.btn_volver);


        if (par.equals("3")){
            info.setText("Has realizado una reserva en"+" "+nomb_cam);
        }else  if (par.equals("2")){
            info.setText("Información de partido, lugar"+" "+nomb_cam);
        }

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
        fecha.setText(fecha_);


        if (est.equals("enproceso"))
        {
            img_estado.setImageResource(R.drawable.ic_pendiente);
            dia.setText("Equipo, pendiente por confirmar");
            if (par.equals("3")){
                one.setText("Cancelar reserva y partido");
            }else  if (par.equals("2")){
                one.setText("Cancelar Partido");
            }
        }else {
            img_estado.setImageResource(R.drawable.ic_pendiente);
            dia.setText("equipo, ha aceptado invitacion, revisa en tus partidos");

            if (par.equals("3")){
                one.setText("cancelar reserva y partido");
            }else  if (par.equals("2")){
                one.setText("Cancelar Partido");
            }
        }

        String urlfinal="http://sacalapp.com/jarvicf/canchas_img/"+img.toString();
        Rect rect = new Rect(imagen_fichaje.getLeft(),imagen_fichaje.getTop(),imagen_fichaje.getRight(),imagen_fichaje.getBottom());
        imagen_fichaje.setImageUrl(urlfinal,rect);

        two.setText("volver");

        final AlertDialog dialog = builder.create();

        one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (est.equals("enproceso"))
                {
                    if (par.equals("3")){
                        new Notificacion_reservas_equipos.CargarDato_capi().execute("http://www.sacalapp.com/cancelar_reserva.php?id_reserva=" + enviar);
                    }else  if (par.equals("2")){
                        new Notificacion_reservas_equipos.CargarDato_capi().execute("http://www.sacalapp.com/cancelar_reserva_off.php?id_reserva=" + enviar);
                    }

                }else {

                    if (par.equals("3")){
                        new Notificacion_reservas_equipos.CargarDato_capi().execute("http://www.sacalapp.com/eliminar_reserva_y_partido.php?id_reserva=" + enviar);
                    }else  if (par.equals("2")){
                        new Notificacion_reservas_equipos.CargarDato_capi().execute("http://www.sacalapp.com/eliminar_reserva_y_partido_off.php?id_reserva=" + enviar);
                    }
                }

                dialog.dismiss();
            }
        });

        two.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void rival() {

        Intent intent = new Intent(Notificacion_reservas_equipos.this, Rivales.class);
        intent.putExtra("id_jugador", id_usuario);
        intent.putExtra("id_equipo", id_equipo_e);
        intent.putExtra("nombre_equipo", nombre_equipo);
        intent.putExtra("id_reserva", enviar);
        intent.putExtra("partido", par);
        startActivity(intent);
        finish();
    }

    private void rival_2() {

        Intent intent = new Intent(Notificacion_reservas_equipos.this, Rivales.class);
        intent.putExtra("id_jugador", id_usuario);
        intent.putExtra("id_equipo", id_equipo_e);
        intent.putExtra("nombre_equipo", nombre_equipo);
        intent.putExtra("id_reserva", enviar);
        intent.putExtra("partido", par);
        startActivity(intent);
        finish();
    }

    public void Dialogo_reserva_2(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Notificacion_reservas_equipos.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.info_reserva_noti_2,null);

        builder.setView(dialogView);

        TextView hora =(TextView)dialogView.findViewById(R.id.lbl_hora_partido);
        TextView fecha =(TextView)dialogView.findViewById(R.id.lbl_fecha_partido);
        TextView info =(TextView)dialogView.findViewById(R.id.lbl_info_de_reserva);
        TextView dia =(TextView)dialogView.findViewById(R.id.lbl_dia_semana);
        SmartImageView imagen_fichaje =(SmartImageView)dialogView.findViewById(R.id.img_equipo_perfil);
        ImageView img_estado =(ImageView)dialogView.findViewById(R.id.img_estatus);
        Button one = (Button) dialogView.findViewById(R.id.btn_cancelar);
        Button two = (Button) dialogView.findViewById(R.id.btn_volver);
        Button theer = (Button) dialogView.findViewById(R.id.btn_invitar);

        if (par.equals("3")){
            one.setText("Cancelar Reserva");
            info.setText("Has realizado una reserva en"+" "+nomb_cam);
        }else  if (par.equals("2")){
            one.setText("Cancelar Invitación");
            info.setText("Información de partido, lugar"+" "+nomb_cam);
        }



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
        fecha.setText(fecha_);


        img_estado.setImageResource(R.drawable.ic_close);


            dia.setText("Te han rechazado la invitación o no has asignado un equipo");
            theer.setText("Buscar otro Equipo");



        String urlfinal="http://sacalapp.com/jarvicf/canchas_img/"+img.toString();
        Rect rect = new Rect(imagen_fichaje.getLeft(),imagen_fichaje.getTop(),imagen_fichaje.getRight(),imagen_fichaje.getBottom());
        imagen_fichaje.setImageUrl(urlfinal,rect);

        two.setText("volver");

        final AlertDialog dialog = builder.create();

        one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (par.equals("3")){
                    new Notificacion_reservas_equipos.CargarDato_capi().execute("http://www.sacalapp.com/cancelar_reserva.php?id_reserva=" + enviar);
                }else  if (par.equals("2")){
                    new Notificacion_reservas_equipos.CargarDato_capi().execute("http://www.sacalapp.com/cancelar_reserva_off.php?id_reserva=" + enviar);
                }

                dialog.dismiss();
            }
        });

        two.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        theer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (par.equals("3")){
                    rival();
                }else  if (par.equals("2")){
                    rival_2();
                }


                dialog.dismiss();
            }
        });


        dialog.show();
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
                TraerReservasi();
                return true;

            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void TraerReservasi() {

        pd.setMessage("Cargando...");
        pd.setCancelable(true);
        pd.show();

        dato1=0;dato2=0;dato3=0;dato4=0;datot=0;
        listView.clearFocus();
        id_reserva.clear();
        cancha_nombre.clear();
        imagen_cancha.clear();
        hora_inicio.clear();
        fecha_partido.clear();
        estatus.clear();
        tipo_reserva.clear();
        id_reserva_2.clear();
        cancha_nombre_2.clear();
        imagen_cancha_2.clear();
        hora_inicio_2.clear();
        fecha_partido_2.clear();
        tipo_reserva2.clear();


        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/traer_reservas_por_equipos.php?id_equipo="+id_equipo_e, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        dato1=jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++){

                            id_reserva.add(jsonArray.getJSONObject(i).getString("id_reserva"));
                            cancha_nombre.add(jsonArray.getJSONObject(i).getString("cancha_nombre"));
                            imagen_cancha.add(jsonArray.getJSONObject(i).getString("imagen_cancha"));
                            hora_inicio.add(jsonArray.getJSONObject(i).getString("hora_inicio"));
                            fecha_partido.add(jsonArray.getJSONObject(i).getString("fecha_partido"));
                            estatus.add(jsonArray.getJSONObject(i).getString("estatus"));
                            tipo_reserva.add(i,"3");
                        }

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
        cliente.get("http://sacalapp.com/traer_reservas_por_equipos_off.php?id_equipo="+id_equipo_e, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        dato4=jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++){

                            id_reserva_2.add(jsonArray.getJSONObject(i).getString("id_reserva_off"));
                            cancha_nombre_2.add(jsonArray.getJSONObject(i).getString("cancha_nombre"));
                            imagen_cancha_2.add(jsonArray.getJSONObject(i).getString("imagen_cancha"));
                            hora_inicio_2.add(jsonArray.getJSONObject(i).getString("hora_inicio"));
                            fecha_partido_2.add(jsonArray.getJSONObject(i).getString("fecha_partido_off"));
                            estatus_2.add(jsonArray.getJSONObject(i).getString("estatus"));
                            tipo_reserva2.add(i,"2");
                        }

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
        cliente.get("http://sacalapp.com/traer_reservas_por_equipos_no.php?id_equipo="+id_equipo_e, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        dato2=jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++){

                            id_reserva.add(jsonArray.getJSONObject(i).getString("id_reserva"));
                            cancha_nombre.add(jsonArray.getJSONObject(i).getString("cancha_nombre"));
                            imagen_cancha.add(jsonArray.getJSONObject(i).getString("imagen_cancha"));
                            hora_inicio.add(jsonArray.getJSONObject(i).getString("hora_inicio"));
                            fecha_partido.add(jsonArray.getJSONObject(i).getString("fecha_partido"));
                            estatus.add(i,"no");
                            tipo_reserva.add(i,"3");

                        }
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
        cliente.get("http://sacalapp.com/traer_reservas_por_equipos_no_off.php?id_equipo="+id_equipo_e, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        dato3=jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++){

                            id_reserva_2.add(jsonArray.getJSONObject(i).getString("id_reserva_off"));
                            cancha_nombre_2.add(jsonArray.getJSONObject(i).getString("cancha_nombre"));
                            imagen_cancha_2.add(jsonArray.getJSONObject(i).getString("imagen_cancha"));
                            hora_inicio_2.add(jsonArray.getJSONObject(i).getString("hora_inicio"));
                            fecha_partido_2.add(jsonArray.getJSONObject(i).getString("fecha_partido_off"));
                            estatus_2.add(i,"no");
                            tipo_reserva2.add(i,"2");

                        }
                        agrupar();
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

    private void agrupar(){

        cancha_nombre.addAll(cancha_nombre_2);
        imagen_cancha.addAll(imagen_cancha_2);
        hora_inicio.addAll(hora_inicio_2);
        fecha_partido.addAll(fecha_partido_2);
        estatus.addAll(estatus_2);
        id_reserva.addAll(id_reserva_2);
        tipo_reserva.addAll(tipo_reserva2);

        datot=dato1+dato2+dato3+dato4;

        pd.dismiss();
        if(datot!=0){
            reser.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);

            for (int i=0;i<datot;i++){
                listView.setAdapter(new Notificacion_reservas_equipos.ImageAdater(getApplicationContext()));
            }

        }



        }

    private class ImageAdater extends BaseAdapter {


        RatingBar ratinCali;
        Context ctx;
        ImageView imageView,reserva_estatus;
        LayoutInflater layoutInflater;
        SmartImageView smartImageView;
        TextView tnombre,tfecha,thora;


        public ImageAdater(Context applicationContext){

            this.ctx=applicationContext;
            layoutInflater=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return cancha_nombre.size();
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

            ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.info_reserva,null);



            smartImageView =(SmartImageView)viewGroup.findViewById(R.id.img_equipo_perfil);
            reserva_estatus =(ImageView)viewGroup.findViewById(R.id.img_reserva);
            imageView=(ImageView) viewGroup.findViewById(R.id.img_estatus);


            tnombre =(TextView) viewGroup.findViewById(R.id.lbl_nombre_cancha);
            tfecha =(TextView) viewGroup.findViewById(R.id.lbl_fecha_partido);
            thora =(TextView) viewGroup.findViewById(R.id.lbl_hora_partido);


            String urlfinal="http://sacalapp.com/jarvicf/canchas_img/"+imagen_cancha.get(pos).toString();
            Rect rect = new Rect(smartImageView.getLeft(),smartImageView.getTop(),smartImageView.getRight(),smartImageView.getBottom());

            smartImageView.setImageUrl(urlfinal,rect);


            tnombre.setText(cancha_nombre.get(pos).toString());
            tfecha.setText(fecha_partido.get(pos).toString());

            String data=(hora_inicio.get(pos).toString());
            if (data.equals("8:00")){
                thora.setText("800AM");
            }else  if (data.equals("9:00")){
                thora.setText("9:00AM");
            }else if (data.equals("10:00")){
                thora.setText("10:00AM");
            }else  if (data.equals("11:00")){
                thora.setText("11:00AM");
            }else  if (data.equals("12:00")){
                thora.setText("12:00PM");
            }else if (data.equals("13:00")){
                thora.setText("1:00PM");
            }else  if (data.equals("14:00")){
                thora.setText("2:00PM");
            }else  if (data.equals("15:00")){
                thora.setText("3:00PM");
            }else  if (data.equals("16:00")){
                thora.setText("4:00PM");
            }else  if (data.equals("17:00")){
                thora.setText("5:00PM");
            }else  if (data.equals("18:00")){
                thora.setText("6:00PM");
            }else  if (data.equals("19:00")){
                thora.setText("7:00PM");
            }else  if (data.equals("20:00")){
                thora.setText("8:00PM");
            }else  if (data.equals("21:00")){
                thora.setText("9:00PM");
            }else  if (data.equals("22:00")){
                thora.setText("10:00PM");
            }else  if (data.equals("23:00")){
                thora.setText("11:00PM");
            }else  if (data.equals("24:00")){
                thora.setText("12:00PM");
            }


            String estato=(estatus.get(pos).toString());
            if (estato.equals("enproceso"))
            {
                imageView.setImageResource(R.drawable.ic_pendiente);
            }else if (estato.equals("no")){
                imageView.setImageResource(R.drawable.ic_pendiente);
            }else if (estato.equals("Aceptada")){
                imageView.setImageResource(R.drawable.ic_confirmado);
            }else {
                imageView.setImageResource(R.drawable.ic_close);
            }


            String reserv=(tipo_reserva.get(pos).toString());
            if (reserv.equals("3")) {
                reserva_estatus.setImageResource(R.drawable.ic_reserva);
            }else if (reserv.equals("2")){
                reserva_estatus.setImageResource(R.drawable.ic_noreserva);
            }




            return viewGroup;
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

            TraerReservasi();
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
