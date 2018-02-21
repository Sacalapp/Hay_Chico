package sacalapp.hay_chico;

import android.app.AlertDialog;
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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

public class Reservas_off extends AppCompatActivity {

    private String id_usuario,id_equipo,nombre_equipo_,hora,hourOfDayfi, hourOfDayin,edad,año,dia,MES,fecha,foto,enviar,nombre_lugar,Cancha_par;
    AppCompatButton equipos,reserva;
    ListView listView_canchas;
    private int Saño, Smes, Sdia, seaño, semes, sedia, Edad,DATO,DATO2;
    private int mYear, mMonth, mDay, mHour, mMinute,aa,bb,cc,cantidad_canchas_,dd;
    static final int DATE_ID = 0;
    private TextView FECHA, HORA, CANCHA, LUGAR,estatus_reservas;

    ArrayList id_cancha = new ArrayList();
    ArrayList nombre_cancha = new ArrayList();
    ArrayList direcion_cancha = new ArrayList();
    ArrayList imagen_cancha = new ArrayList();
    ArrayList cantidad_canchas = new ArrayList();
    SmartImageView lugar;
    GridView canchas;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Date fechaActual,fechaSelecion;
    private Calendar act,sel,fechama20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_reservas_off);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Calendar C = Calendar.getInstance();
        seaño = C.get(Calendar.YEAR);
        semes = C.get(Calendar.MONTH);
        sedia = C.get(Calendar.DAY_OF_MONTH);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_reservas_off);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Información partido");
        id_usuario = getIntent().getExtras().getString("id_jugador");
        id_equipo = getIntent().getExtras().getString("id_equipo");
        nombre_equipo_ = getIntent().getExtras().getString("nombre_equipo");
        lugar = (SmartImageView) findViewById(R.id.img_cancha_datos);
        FECHA = (TextView) findViewById(R.id.lbl_fecha_partido);
        HORA = (TextView) findViewById(R.id.lbl_hora_reservas);
        LUGAR = (TextView) findViewById(R.id.lbl_lugar);
        equipos = (AppCompatButton) findViewById(R.id.ir_a_equipos);
        reserva = (AppCompatButton) findViewById(R.id.btn_reserva);
        canchas = (GridView) findViewById(R.id.Grid_canchas);

        listView_canchas = (ListView) findViewById(R.id.list_cancha);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createSimpleDialog();

        listView_canchas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                foto = (imagen_cancha.get(position).toString());
                enviar = (id_cancha.get(position).toString());
                nombre_lugar = (nombre_cancha.get(position).toString());

                String cn = (cantidad_canchas.get(position).toString());
                cantidad_canchas_ = Integer.parseInt(cn);

                cc = 1;
                datos();
                llenar_canchas();

            }
        });

        canchas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dd=1;
                 Cancha_par = "Cancha" + (position + 1);
            }
        });

        equipos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (aa==1 && bb==1 && cc==1 && dd==1){
                    new Reservas_off.CargarDato().execute("http://www.sacalapp.com/reservar_off.php?fecha_partido="+fecha+"&hora_inicio="+hourOfDayin+"&id_cancha="+enviar+"&cancha_id="+Cancha_par+"&id_equipo="+id_equipo+"&id_responsable="+id_usuario);
                }else {
                    Toast.makeText(Reservas_off.this, "Complete todos los datos", Toast.LENGTH_SHORT).show();
                }

            }
        });
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

            new Reservas_off.traer_id_reserva().execute("http://www.sacalapp.com/traer_id_reserva_off.php?fecha_partido="+fecha+"&hora_inicio="+hourOfDayin+"&id_cancha="+enviar+"&cancha_id="+Cancha_par+"&id_responsable="+id_usuario+"&id_equipo="+id_equipo);


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

                Toast.makeText(getApplicationContext(), "Busca un rival", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Reservas_off.this, Rivales.class);
                intent.putExtra("id_jugador", id_usuario);
                intent.putExtra("id_equipo", id_equipo);
                intent.putExtra("nombre_equipo", nombre_equipo_);
                intent.putExtra("id_reserva", id_rese);
                intent.putExtra("partido", "2");
                startActivity(intent);
                finish();


            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(Perfil.this, "Error_fichajes", Toast.LENGTH_LONG).show();

            }

        }
    }

    public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Importante")

                .setMessage(R.string.reservas_off)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                descargarImagen();
                            }
                        })

                .setNegativeButton("Quiero Reservar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Reservas_off.this, Reservas.class);
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

    private void llenar_canchas() {
        for (int i = 0; i < cantidad_canchas_; i++) {
            canchas.setAdapter(new Reservas_off.ImageAdater_canchas(getApplicationContext()));
        }
    }

    private class ImageAdater_canchas extends BaseAdapter {


        Context ctx;
        LayoutInflater layoutInflater;
        SmartImageView adelante,smartImageView_canchas;
        TextView tnombre_cancha;
        String cancha;



        public ImageAdater_canchas(Context applicationContext) {

            this.ctx = applicationContext;
            layoutInflater = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {

            return cantidad_canchas_;
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

                String urlfinal = "http://sacalapp.com/jarvicf/canchas_img/cancha_sin.png";
                Rect rect = new Rect(smartImageView_canchas.getLeft(), smartImageView_canchas.getTop(), smartImageView_canchas.getRight(), smartImageView_canchas.getBottom());
                smartImageView_canchas.setImageUrl(urlfinal, rect);




            return viewGroup;
        }


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
        cantidad_canchas.clear();


        final ProgressDialog progressDialog = new ProgressDialog(Reservas_off.this);
        progressDialog.setMessage("Cargardo Canchas...");
        progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/lista_cancha_no.php", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i = 0; i < jsonArray.length(); i++) {

                            id_cancha.add(jsonArray.getJSONObject(i).getString("id_cancha"));
                            nombre_cancha.add(jsonArray.getJSONObject(i).getString("cancha_nombre"));
                            direcion_cancha.add(jsonArray.getJSONObject(i).getString("direccion_cancha"));
                            imagen_cancha.add(jsonArray.getJSONObject(i).getString("imagen_cancha"));
                            cantidad_canchas.add(jsonArray.getJSONObject(i).getString("cantidad_canchas"));


                            listView_canchas.setAdapter(new Reservas_off.ImageAdater(getApplicationContext()));
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

    public void hora(View v) {

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = 00;

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

    public void Calendario_2(View v) {
        showDialog(DATE_ID);
    }

    private void MostrarFecha() {

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
                MostrarFecha();

            }else if (sel.before(act)){
                Toast.makeText(Reservas_off.this, "no puedes volver al pasado", Toast.LENGTH_SHORT).show();
                FECHA.setText("");
                bb=0;
            }


        }
    };

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
        startService(new Intent(Reservas_off.this, Notificaciones.class));
        super.onPause();
    }

    @Override
    public void onStop() {

        // Toast.makeText(getApplicationContext(), "Start" ,Toast.LENGTH_LONG).show();
        startService(new Intent(Reservas_off.this, Notificaciones.class));

        super.onStop();
    }

    @Override
    public void onRestart(){

        //Toast.makeText(getApplicationContext(), "stop" ,Toast.LENGTH_LONG).show();
        stopService(new Intent(Reservas_off.this, Notificaciones.class));
        super.onRestart();


    }
}
