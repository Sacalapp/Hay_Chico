package sacalapp.hay_chico;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class Reservas extends AppCompatActivity {

    static final int DATE_ID = 0;
    private int Saño, Smes, Sdia, seaño, semes, sedia, Edad,DATO,DATO2;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String año, mes, dia, fecha, edad, MES, id_usuario, cancha,hora_va,id_reserva__,nombre,telefono,hora,id_unico, enviar="Null",hora2,enviar_2,reserva_hoy,cancha_se, foto, nombre_lugar, lblcancha, cancha_id, hourOfDayfi, hourOfDayin, id_equipo, can_canchas,cancha_07;
    private TextView FECHA, HORA, CANCHA, LUGAR,estatus_reservas;
    AppCompatButton equipos, reserva;

    ArrayList id_cancha = new ArrayList();
    ArrayList nombre_cancha = new ArrayList();
    ArrayList direcion_cancha = new ArrayList();
    ArrayList imagen_cancha = new ArrayList();

    ArrayList _id_cancha_hoy = new ArrayList();
    ArrayList _cancha = new ArrayList();
    ArrayList _hora = new ArrayList();
    ArrayList _fecha = new ArrayList();
    ArrayList _cancha_nombre = new ArrayList();
    ArrayList _imagen_cancha = new ArrayList();
    ArrayList _id_cancha = new ArrayList();

    ArrayList id_reserva = new ArrayList();
    ArrayList fecha_partido = new ArrayList();
    ArrayList hora_inicio = new ArrayList();
    ArrayList id_cancha_ = new ArrayList();
    ArrayList cancha_id_ = new ArrayList();
    ArrayList cantidad_canchas = new ArrayList();

    ArrayList comp = new ArrayList();
    TextInputLayout til_numero;

    SmartImageView lugar;
    private int aa, bb, cc, can;
    private String Cancha_1, Cancha_2, Cancha_3, Cancha_4, Cancha_5, Cancha_6, Cancha_7, Cancha_8, nombre_equipo_;
    SmartImageView smartImageView_canchas;
    ListView listView_canchas;
    GridView canchas;
    private int dato = 0, ref = 1;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date fechaActual,fechaSelecion;
    Calendar act,sel,fechama20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_reservas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_reservas);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reserva");
        id_usuario = getIntent().getExtras().getString("id_jugador");
        id_equipo = getIntent().getExtras().getString("id_equipo");
        nombre_equipo_ = getIntent().getExtras().getString("nombre_equipo");

        equipos = (AppCompatButton) findViewById(R.id.ir_a_equipos);
        equipos.setVisibility(View.INVISIBLE);
        reserva = (AppCompatButton) findViewById(R.id.btn_reserva);
        Calendar C = Calendar.getInstance();
        seaño = C.get(Calendar.YEAR);
        semes = C.get(Calendar.MONTH);
        sedia = C.get(Calendar.DAY_OF_MONTH);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FECHA = (TextView) findViewById(R.id.lbl_fecha_partido);
        HORA = (TextView) findViewById(R.id.lbl_hora_reservas);
        LUGAR = (TextView) findViewById(R.id.lbl_lugar);

        estatus_reservas = (TextView) findViewById(R.id.lbl_estatus_reserva);

        lugar = (SmartImageView) findViewById(R.id.img_cancha_datos);

        listView_canchas = (ListView) findViewById(R.id.list_cancha);
        canchas = (GridView) findViewById(R.id.Grid_canchas);

        canchas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (enviar.equals("Null")){
                    equipos.setVisibility(View.VISIBLE);
                    nombre_lugar=(_cancha_nombre.get(position).toString());
                    fecha=_fecha.get(position).toString();
                    hora=hora_va;
                    hora2=(_hora.get(position).toString());
                    enviar_2=(_id_cancha.get(position).toString());
                    id_unico=(_id_cancha.get(position).toString());
                    cancha_se=(_cancha.get(position).toString());
                    cancha_id=(_cancha.get(position).toString());
                    reserva_hoy=(_id_cancha_hoy.get(position).toString());
                    foto=(_imagen_cancha.get(position).toString());
                }else {
                    cancha_id = "Cancha" + (position + 1);
                    String can = "Cancha" + (position + 1);

                    if (cancha_id_.size() == 0) {
                        equipos.setVisibility(View.VISIBLE);
                    } else {
                        for (int i = 0; i < cancha_id_.size(); i++) {
                            cancha_07 = (cancha_id_.get(i).toString());
                            if (can.equals(cancha_07)) {
                                equipos.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), "Ya han realizado una reserva", Toast.LENGTH_SHORT).show();
                            } else {
                                equipos.setVisibility(View.VISIBLE);
                            }
                        }


                    }

                    //cancha_id = "Cancha" + (position + 1);
                    //Toast.makeText(getApplicationContext(), cancha_id , Toast.LENGTH_SHORT).show();
                    //reserva.setVisibility(View.INVISIBLE);


                }
            }
        });

        listView_canchas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // final String enviar=(id_usuarios.get(position).toString());
                //id_jugador_notificacion = enviar;

                    reset();
                    foto = (imagen_cancha.get(position).toString());
                    enviar = (id_cancha.get(position).toString());
                    id_unico = (id_cancha.get(position).toString());
                    nombre_lugar = (nombre_cancha.get(position).toString());
                    equipos.setEnabled(false);
                    equipos.setVisibility(View.INVISIBLE);
                    reserva.setVisibility(View.VISIBLE);
                    cc = 1;
                    datos();

            }
        });

        equipos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmar_reserva();
            }
        });

        reserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aa == 1 & bb == 1 & cc == 1) {
                    descargar_reservas_comp();
                    equipos.setVisibility(View.INVISIBLE);
                    estatus_reservas.setText("Seleciona una cancha");
                } else {
                    Toast.makeText(getApplicationContext(), "Falta datos, seleciona lugar, fecha y hora", Toast.LENGTH_LONG).show();
                }

            }
        });

        descargarImagen();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reser, menu);
        menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Reserva_fija:
                Ingresar_codigo();
                return true;

            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void reset() {
        aa = 0;
        bb = 0;
        cc = 0;
        FECHA.setText(" ");
        HORA.setText(" ");
        canchas.setAdapter(null);


    }

    private void datos() {

        LUGAR.setText(nombre_lugar);

        String urlfinal = "http://sacalapp.com/jarvicf/canchas_img/" + foto;
        Rect rect = new Rect(lugar.getLeft(), lugar.getTop(), lugar.getRight(), lugar.getBottom());

        lugar.setImageUrl(urlfinal, rect);


    }

    private void descargarImagen() {

        id_cancha.clear();
        nombre_cancha.clear();
        direcion_cancha.clear();
        imagen_cancha.clear();


        final ProgressDialog progressDialog = new ProgressDialog(Reservas.this);
        progressDialog.setMessage("Cargardo Canchas...");
        progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/lista_canchas.php", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        int Cantidad=jsonArray.length();
                        if (Cantidad==0){
                            createSimpleDialog();
                        }else {
                            for (int i = 0; i < jsonArray.length(); i++) {

                                id_cancha.add(jsonArray.getJSONObject(i).getString("id_cancha"));
                                nombre_cancha.add(jsonArray.getJSONObject(i).getString("cancha_nombre"));
                                direcion_cancha.add(jsonArray.getJSONObject(i).getString("direccion_cancha"));
                                imagen_cancha.add(jsonArray.getJSONObject(i).getString("imagen_cancha"));


                                listView_canchas.setAdapter(new Reservas.ImageAdater(getApplicationContext()));

                            }
                            canchas_hoy();
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

    private class ImageAdater extends BaseAdapter {


        Context ctx;
        LayoutInflater layoutInflater;
        ImageView smartImageView_cancha;
        TextView tnombre, tdirecion;


        public ImageAdater(Context applicationContext) {

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

            ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.cancha_sola, null);


            smartImageView_cancha = (ImageView) viewGroup.findViewById(R.id.img_cancha_portada);


            tnombre = (TextView) viewGroup.findViewById(R.id.lbl_nombre_cancha);
            tdirecion = (TextView) viewGroup.findViewById(R.id.lbl_dir_cancha);

            String urlfinal="http://sacalapp.com/jarvicf/canchas_img/"+imagen_cancha.get(pos).toString();

            Glide.with(smartImageView_cancha.getContext())
                    .load(urlfinal)
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.drawable.cancha_equipos)
                    .signature(new StringSignature(urlfinal))
                    .into(smartImageView_cancha);

            tnombre.setText(nombre_cancha.get(pos).toString());
            tdirecion.setText(direcion_cancha.get(pos).toString());
            return viewGroup;
        }


    }

    private class datos_capi extends AsyncTask<String, Void, String> {
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

                nombre=ja.get(0).toString();
                telefono=ja.get(1).toString();

                if (DATO==1){
                    new Reservas.CargarDato().execute("http://www.sacalapp.com/reservar.php?fecha_partido="+fecha+"&hora_inicio="+hourOfDayin+"&id_cancha="+enviar+"&cancha_id="+cancha_id+"&id_responsable="+id_usuario+"&id_equipo="+id_equipo+"&nombre="+nombre+"&telefono="+telefono);
                }else if (DATO==2){
                    new Reservas.CargarDato().execute("http://www.sacalapp.com/reservar.php?fecha_partido="+fecha+"&hora_inicio="+hora2+"&id_cancha="+enviar_2+"&cancha_id="+cancha_se+"&id_responsable="+id_usuario+"&id_equipo="+id_equipo+"&nombre="+nombre+"&telefono="+telefono);
                }



            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(Perfil.this, "Error_fichajes", Toast.LENGTH_LONG).show();

            }

        }
    }

    public android.app.AlertDialog createSimpleDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        builder.setTitle("Importante")

                .setMessage(R.string.reservas_off_)
                .setPositiveButton("volver",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })

                .setNegativeButton("Buscar rival",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Reservas.this, Reservas_off.class);
                                intent.putExtra("id_equipo", id_equipo);
                                intent.putExtra("id_jugador", id_usuario);
                                intent.putExtra("nombre_equipo", nombre_equipo_);
                                startActivity(intent);
                                finish();
                            }
                        });

        builder.show();


        return builder.create();
    }

//_____________________________ canchas hoy _____________________________

    private void canchas_hoy() {

        estatus_reservas.setText("Canchas Disponibles para hoy");

         _id_cancha_hoy.clear();
         _cancha.clear();
         _hora.clear();
         _fecha.clear();
         _cancha_nombre.clear();
         _imagen_cancha.clear();
         _id_cancha.clear();


        final ProgressDialog progressDialog = new ProgressDialog(Reservas.this);
        progressDialog.setMessage("Cargardo Canchas...");
        progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/lista_canchas_hoy.php", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray2 = new JSONArray(new String(responseBody));
                        for (int i = 0; i < jsonArray2.length(); i++) {

                            _id_cancha_hoy.add(jsonArray2.getJSONObject(i).getString("id_cancha_hoy"));
                            _cancha.add(jsonArray2.getJSONObject(i).getString("cancha"));
                            _hora.add(jsonArray2.getJSONObject(i).getString("hora"));
                            _fecha.add(jsonArray2.getJSONObject(i).getString("fecha"));
                            _cancha_nombre.add(jsonArray2.getJSONObject(i).getString("cancha_nombre"));
                            _imagen_cancha.add(jsonArray2.getJSONObject(i).getString("imagen_cancha"));
                            _id_cancha.add(jsonArray2.getJSONObject(i).getString("id_cancha"));


                            canchas.setAdapter(new Reservas.ImageAdater_2(getApplicationContext()));

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error canchas_hoy", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private class ImageAdater_2 extends BaseAdapter {


        Context ctx;
        LayoutInflater layoutInflater;
        ImageView adelante,logo_cancha;
        TextView tnombre_cancha,thora,nombre_cam;



        public ImageAdater_2(Context applicationContext) {

            this.ctx = applicationContext;
            layoutInflater = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return _id_cancha_hoy.size();
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

            ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.canchas_hoy, null);


            adelante = (ImageView) viewGroup.findViewById(R.id.img_go);
            logo_cancha = (ImageView) viewGroup.findViewById(R.id.img_cancha);
            //smartImageView_canchas = (ImageView) viewGroup.findViewById(R.id.img_canchas);
            tnombre_cancha = (TextView) viewGroup.findViewById(R.id.lbl_nombre_cancha);
            nombre_cam = (TextView) viewGroup.findViewById(R.id.lbl_nombre_cam);
            thora = (TextView) viewGroup.findViewById(R.id.lbl_hora);


            String urlfinal="http://sacalapp.com/jarvicf/canchas_img/"+_imagen_cancha.get(pos).toString();

            Glide.with(logo_cancha.getContext())
                    .load(urlfinal)
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.drawable.cancha_equipos)
                    .signature(new StringSignature(urlfinal))
                    .into(logo_cancha);
            String cancha=_cancha.get(pos).toString();
                if (cancha.equals("Cancha1")){
                    tnombre_cancha.setText("Cancha 1");
                }else  if (cancha.equals("Cancha2")){
                    tnombre_cancha.setText("Cancha 2");
                }else  if (cancha.equals("Cancha3")){
                    tnombre_cancha.setText("Cancha 3");
                }else  if (cancha.equals("Cancha4")){
                    tnombre_cancha.setText("Cancha 4");
                }else  if (cancha.equals("Cancha5")){
                    tnombre_cancha.setText("Cancha 5");
                }else  if (cancha.equals("Cancha6")){
                    tnombre_cancha.setText("Cancha 6");
                }else  if (cancha.equals("Cancha7")){
                    tnombre_cancha.setText("Cancha 7");
                }else  if (cancha.equals("Cancha8")){
                    tnombre_cancha.setText("Cancha 8");
                }

            String hora_c=_hora.get(pos).toString();

            if (hora_c.equals("13:00")){
                thora.setText("1:00 Pm");
                hora_va="1:00 Pm";
            }else  if (hora_c.equals("14:00")){
                thora.setText("2:00 Pm");
                hora_va="2:00 Pm";
            }else  if (hora_c.equals("15:00")){
                thora.setText("3:00 Pm");
                hora_va="3:00 Pm";
            }else  if (hora_c.equals("16:00")){
                thora.setText("4:00 Pm");
                hora_va="4:00 Pm";
            }else  if (hora_c.equals("17:00")){
                thora.setText("5:00 Pm");
                hora_va="5:00 Pm";
            }else  if (hora_c.equals("18:00")){
                thora.setText("6:00 Pm");
                hora_va="6:00 Pm";
            }else  if (hora_c.equals("19:00")){
                thora.setText("7:00 Pm");
                hora_va="7:00 Pm";
            }else  if (hora_c.equals("20:00")){
                thora.setText("8:00 Pm");
                hora_va="8:00 Pm";
            }else  if (hora_c.equals("21:00")){
                thora.setText("9:00 Pm");
                hora_va="9:00 Pm";
            }else  if (hora_c.equals("22:00")){
                thora.setText("10:00 Pm");
                hora_va="10:00 Pm";
            }else if (hora_c.equals("23:00")){
                thora.setText("11:00 Pm");
                hora_va="11:00 Pm";
            }else if (hora_c.equals("24:00")){
                thora.setText("12:00 Pm");
                hora_va="12:00 Pm";
            }

            nombre_cam.setText(_cancha_nombre.get(pos).toString());



            return viewGroup;
        }


    }

    //______________________________________________________________

    private void tarer_canchas() {


        id_reserva.clear();
        fecha_partido.clear();
        hora_inicio.clear();
        id_cancha_.clear();
        cancha_id_.clear();
        cantidad_canchas.clear();


        final ProgressDialog progressDialog = new ProgressDialog(Reservas.this);
        progressDialog.setMessage("Cargardo Canchas...");
        progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/canchas_detalles.php?id_cancha=" + enviar + "&fecha=" + fecha + "&hora=" + hourOfDayin, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        if (jsonArray.length() == 0) {
                            new Reservas.ConsultarDatos().execute("http://www.sacalapp.com/cantidad_canchas.php?id_cancha=" + enviar);
                        } else {

                            for (int i = 0; i < jsonArray.length(); i++) {

                                id_reserva.add(jsonArray.getJSONObject(i).getString("id_reserva"));
                                fecha_partido.add(jsonArray.getJSONObject(i).getString("fecha_partido"));
                                hora_inicio.add(jsonArray.getJSONObject(i).getString("hora_inicio"));
                                id_cancha_.add(jsonArray.getJSONObject(i).getString("id_cancha"));
                                cancha_id_.add(jsonArray.getJSONObject(i).getString("cancha_id"));
                                cantidad_canchas.add(jsonArray.getJSONObject(i).getString("cantidad_canchas"));

                                String cn;
                                cn = (cantidad_canchas.get(i).toString());
                                can = Integer.parseInt(cn);
                            }

                            for (int i = 0; i < can; i++) {
                                canchas.setAdapter(new Reservas.ImageAdater_canchas(getApplicationContext()));
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

    public void mostrar() {

        canchas.setAdapter(new Reservas.ImageAdater_canchas(getApplicationContext()));
    }

    private class ImageAdater_canchas extends BaseAdapter {


        Context ctx;
        LayoutInflater layoutInflater;
        SmartImageView adelante;
        TextView tnombre_cancha;



        public ImageAdater_canchas(Context applicationContext) {

            this.ctx = applicationContext;
            layoutInflater = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return can;
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

            ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.chanchas, null);


            adelante = (SmartImageView) viewGroup.findViewById(R.id.img_go);
            smartImageView_canchas = (SmartImageView) viewGroup.findViewById(R.id.img_canchas);
            tnombre_cancha = (TextView) viewGroup.findViewById(R.id.lbl_nombre_cancha);
            cancha = "";

            tnombre_cancha.setText("Cancha #"+(pos+1));
            String can ="Cancha"+(pos+1);

            if (cancha_id_.size()==0){
                sin();
            }else {

                for (int i = 0; i < cancha_id_.size(); i++) {
                    cancha_07 = (cancha_id_.get(i).toString());
                    if (can.equals(cancha_07)) {
                        con();
                    } else {
                        sin();
                    }

                }
            }


                return viewGroup;
        }


    }

    private void llenar() {

    }

    public void Ingresar_codigo(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Reservas.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.reserva_fija,null);

        builder.setView(dialogView);

         til_numero = (TextInputLayout) dialogView.findViewById(R.id.til_telefono);
        Button one = (Button) dialogView.findViewById(R.id.btn_cancelar_codigo);
        Button two = (Button) dialogView.findViewById(R.id.btn_buscar_codigo);


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

                String Code = til_numero.getEditText().getText().toString().trim();
                new Reservas.traer_id_reserva_fija().execute("http://www.sacalapp.com/traer_id_reserva_fija.php?codigo="+Code);
                dialog.dismiss();

            }
        });


        dialog.show();
    }

    private class traer_id_reserva_fija extends AsyncTask<String, Void, String> {
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
                String id_reserva_fija = (ja.getString(0));
                String dia_semana = (ja.getString(1));
                String hora = (ja.getString(2));
                String cancha = (ja.getString(3));
                String nombre = (ja.getString(4));
                String logo = (ja.getString(5));

                Intent intent = new Intent(Reservas.this, reservas_fija.class);
                intent.putExtra("id_jugador", id_usuario);
                intent.putExtra("id_equipo", id_equipo);
                intent.putExtra("nombre_equipo", nombre_equipo_);

                intent.putExtra("id_reserva_fija", id_reserva_fija);
                intent.putExtra("dia_semana", dia_semana);
                intent.putExtra("hora", hora);
                intent.putExtra("cancha", cancha);
                intent.putExtra("nombre", nombre);
                intent.putExtra("logo", logo);


                //intent.putExtra("partido", "3");
                startActivity(intent);
                finish();



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void con() {

        String urlfinal = "http://sacalapp.com/jarvicf/canchas_img/cancha_con.png";
        Rect rect = new Rect(smartImageView_canchas.getLeft(), smartImageView_canchas.getTop(), smartImageView_canchas.getRight(), smartImageView_canchas.getBottom());
        smartImageView_canchas.setImageUrl(urlfinal, rect);

    }

    private void sin() {
        String urlfinal = "http://sacalapp.com/jarvicf/canchas_img/cancha_sin.png";
        Rect rect = new Rect(smartImageView_canchas.getLeft(), smartImageView_canchas.getTop(), smartImageView_canchas.getRight(), smartImageView_canchas.getBottom());
        smartImageView_canchas.setImageUrl(urlfinal, rect);

    }

    public void hora(View v) {

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = 00;
        canchas.setAdapter(null);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        HORA.setText(hourOfDay + ":" + "00");
                        hourOfDayin = (hourOfDay + ":" + "00");

                        aa = 1;
                        switch (hourOfDay) {

                            case 8:
                                hora = "8:00 Am";
                                HORA.setText(hora);
                                break;
                            case 9:
                                hora = "9:00 Am";
                                HORA.setText(hora);
                                break;
                            case 10:
                                hora = "10:00 Am";
                                HORA.setText(hora);
                                break;
                            case 11:
                                hora = "11:00 Am";
                                HORA.setText(hora);
                                break;
                            case 12:
                                hora = "12:00 Am";
                                HORA.setText(hora);
                                break;
                            case 13:
                                hora = "1:00 Pm";
                                HORA.setText(hora);
                                break;
                            case 14:
                                hora = "2:00 Pm";
                                HORA.setText(hora);
                                break;
                            case 15:
                                hora = "3:00 Pm";
                                HORA.setText(hora);
                                break;
                            case 16:
                                hora = "4:00 Pm";
                                HORA.setText(hora);
                                break;
                            case 17:
                                hora = "5:00 Pm";
                                HORA.setText(hora);
                                break;
                            case 18:
                                hora = "6:00 Pm";
                                HORA.setText(hora);
                                break;
                            case 19:
                                hora = "7:00 Pm";
                                HORA.setText(hora);
                                break;
                            case 20:
                                hora = "8:00 Pm";
                                HORA.setText(hora);
                                break;
                            case 21:
                                hora = "9:00 Pm";
                                HORA.setText(hora);
                                break;
                            case 22:
                                hora = "10:00 Pm";
                                HORA.setText(hora);
                                break;
                            case 23:
                                hora = "11:00 Pm";
                                HORA.setText(hora);
                                break;
                            case 24:
                                hora = "12:00 Pm";
                                HORA.setText(hora);
                                break;

                        }

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void descargar_reservas_comp() {



        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://www.sacalapp.com/reserva_compa.php?id_jugador=" + id_usuario + "&hora_inicio=" + hourOfDayin + "&fecha_partido=" + fecha, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        int data=jsonArray.length();

                        if (data==0){
                            equipos.setEnabled(true);
                            tarer_canchas();
                        }else {
                            Toast.makeText(getApplicationContext(), "Ya tienes una reserva en está fecha y hora", Toast.LENGTH_LONG).show();
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

    public void Calendario_2(View v) {
        showDialog(DATE_ID);
    }

    private void MostrarFecha() {

        canchas.setAdapter(null);
        edad = String.valueOf(Edad);
        //Año.setText(new StringBuilder().append(Saño));
        //Mes.setText(new StringBuilder().append(Smes));
        //Dia.setText(new StringBuilder().append(Sdia));
        año = String.valueOf(Saño);
        dia = String.valueOf(Sdia);
        switch (Smes) {

            case 0:
                MES = "01";
                break;
            case 1:
                MES = "02";
                break;
            case 2:
                MES = "03";
                break;
            case 3:
                MES = "04";
                break;
            case 4:
                MES = "05";
                break;
            case 5:
                MES = "06";
                break;
            case 6:
                MES = "07";
                break;
            case 7:
                MES = "08";
                break;
            case 8:
                MES = "09";
                break;
            case 9:
                MES = "10";
                break;
            case 10:
                MES = "11";
                break;
            case 11:
                MES = "12";
                break;
        }

        switch (Sdia) {


            case 1:
                dia = "01";
                break;
            case 2:
                dia = "03";
                break;
            case 3:
                dia = "03";
                break;
            case 4:
                dia = "04";
                break;
            case 5:
                dia = "05";
                break;
            case 6:
                dia = "06";
                break;
            case 7:
                dia = "07";
                break;
            case 8:
                dia = "08";
                break;
            case 9:
                dia = "09";
                break;

        }

        fecha = año + "-" + MES + "-" + dia;


        bb = 1;
        FECHA.setText(fecha);

    }

    public void confirmar_reserva(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Reservas.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogo_reserva_informacion,null);

        builder.setView(dialogView);

        TextView fecha_ = (TextView) dialogView.findViewById(R.id.lbl_fecha_partido_info);
        TextView hora_ = (TextView) dialogView.findViewById(R.id.lbl_hora_reservas_info);
        TextView lugar = (TextView) dialogView.findViewById(R.id.lbl_lugar_info);
        TextView cancha = (TextView) dialogView.findViewById(R.id.lbl_cancha_info);
        Button one = (Button) dialogView.findViewById(R.id.btn_volver_);
        Button two = (Button) dialogView.findViewById(R.id.btn_reservar_);
        ImageView logo_cancha = (ImageView) dialogView.findViewById(R.id.img_lugar);

        String urlfinal="http://sacalapp.com/jarvicf/canchas_img/"+foto;

        Glide.with(logo_cancha.getContext())
                .load(urlfinal)
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.cancha_equipos)
                .signature(new StringSignature(urlfinal))
                .into(logo_cancha);

        fecha_.setText(fecha);
        hora_.setText(hora);
        lugar.setText(nombre_lugar);
        cancha.setText(cancha_id);



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

                if (enviar.equals("Null")){
                        comparacion();
                    dialog.dismiss();
                }else{
                    new Reservas.datos_capi().execute("http://www.sacalapp.com/datos_usuario.php?id_usuario=" + id_usuario);
                    DATO=1;
                }


            }
        });


        dialog.show();
    }

    private void comparacion() {


        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/compaar_reservas.php?id_cancha="+enviar_2+"&fecha="+fecha+"&hora="+hora2+"&cancha_sel="+cancha_se, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        if (jsonArray.length() == 0) {
                            new Reservas.datos_capi().execute("http://www.sacalapp.com/datos_usuario.php?id_usuario=" + id_usuario);
                            DATO=2;
                        } else {
                            DATO2=1;
                            new Reservas.eliminar_reserva_hoy().execute("http://www.sacalapp.com/eliminar_reserva_hoy.php?id_reserva_hoy="+reserva_hoy);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private class eliminar_reserva_hoy extends AsyncTask<String, Void, String> {
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
            if(DATO2==1){
                Toast.makeText(Reservas.this, "Ya se ha realizado una reserva en este horario", Toast.LENGTH_LONG).show();
                canchas.setAdapter(null);
                canchas_hoy();
            } else  if(DATO2==2){

            }

        }
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int año, int mes, int dia) {

            Saño = año;
            Smes = mes;
            Sdia = dia;

            String fa,fs;

            fa = seaño + "-" + semes + "-" + sedia;
            fs = año + "-" + mes + "-" + dia;
            try {
                fechaSelecion = dateFormat.parse(fs);
                fechaActual = dateFormat.parse(fa);
                act=Calendar.getInstance();
                sel=Calendar.getInstance();
                fechama20=Calendar.getInstance();
                act.setTime(fechaActual);
                sel.setTime(fechaSelecion);
                fechama20.setTime(fechaActual);

                act.add(Calendar.MONTH,1);
                sel.add(Calendar.MONTH,1);
                fechama20.add(Calendar.MONTH,1);
                fechama20.add(Calendar.DATE,20);

            } catch (ParseException e) {
                e.printStackTrace();
            }


            if (sel.after(act) || sel.equals(act)){

                if (sel.before(fechama20) || sel.equals(fechama20)){
                    MostrarFecha();
                }else {
                    Toast.makeText(Reservas.this, "Solo puedes reservar en un rango de 20 dias", Toast.LENGTH_SHORT).show();
                    FECHA.setText("");
                    bb=0;
                }
                //

            }else if (sel.before(act)){
                Toast.makeText(Reservas.this, "no puedes volver al pasado", Toast.LENGTH_SHORT).show();
                FECHA.setText("");
                bb=0;
            }

        }
    };

    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case DATE_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        seaño, semes, sedia
                );
        }
        return null;
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

            if (DATO==1){
                new Reservas.traer_id_reserva().execute("http://www.sacalapp.com/traer_id_reserva.php?fecha_partido="+fecha+"&hora_inicio="+hourOfDayin+"&id_cancha="+enviar+"&cancha_id="+cancha_id+"&id_responsable="+id_usuario+"&id_equipo="+id_equipo);
            }else if(DATO==2){
                new Reservas.traer_id_reserva().execute("http://www.sacalapp.com/traer_id_reserva.php?fecha_partido="+fecha+"&hora_inicio="+hora2+"&id_cancha="+enviar_2+"&cancha_id="+cancha_se+"&id_responsable="+id_usuario+"&id_equipo="+id_equipo);
            }




        }
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
                String id_rese = (ja.getString(0));
                id_reserva__=id_rese;

                if (DATO==1){
                    Toast.makeText(getApplicationContext(), "ya tienes una reserva, ahora busca un rival", Toast.LENGTH_LONG).show();
                }else if(DATO==2){
                    DATO2=2;
                    Toast.makeText(getApplicationContext(), "ya tienes una reserva, ahora busca un rival", Toast.LENGTH_LONG).show();
                    new Reservas.eliminar_reserva_hoy().execute("http://www.sacalapp.com/eliminar_reserva_hoy.php?id_reserva_hoy="+reserva_hoy);
                }

                new Reservas.notificacion_cancha().execute("http://www.sacalapp.com/notificacion_cancha.php?id_reserva="+id_rese+"&id_cancha="+id_unico);



            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(Perfil.this, "Error_fichajes", Toast.LENGTH_LONG).show();

            }

        }
    }

    private class notificacion_cancha extends AsyncTask<String, Void, String> {
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
            ir_a_buscar_rival();
        }
    }

    private void ir_a_buscar_rival() {
        Intent intent = new Intent(Reservas.this, Rivales.class);
        intent.putExtra("id_jugador", id_usuario);
        intent.putExtra("id_equipo", id_equipo);
        intent.putExtra("nombre_equipo", nombre_equipo_);
        intent.putExtra("id_reserva", id_reserva__);
        intent.putExtra("partido", "3");
        startActivity(intent);
        finish();
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
                can_canchas = (ja.getString(0));
                can = 0;

                can = Integer.parseInt(can_canchas);

                for (int i = 0; i < can; i++) {
                    canchas.setAdapter(new Reservas.ImageAdater_canchas(getApplicationContext()));
                }


            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(Perfil.this, "Error_fichajes", Toast.LENGTH_LONG).show();

            }

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
        startService(new Intent(Reservas.this, Notificaciones.class));
        super.onPause();
    }

    @Override
    public void onStop() {

        // Toast.makeText(getApplicationContext(), "Start" ,Toast.LENGTH_LONG).show();
        startService(new Intent(Reservas.this, Notificaciones.class));

        super.onStop();
    }

    @Override
    public void onRestart(){

        //Toast.makeText(getApplicationContext(), "stop" ,Toast.LENGTH_LONG).show();
        stopService(new Intent(Reservas.this, Notificaciones.class));
        super.onRestart();


    }

}
