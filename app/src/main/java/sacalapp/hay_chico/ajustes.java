package sacalapp.hay_chico;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
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
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import sacalapp.hay_chico.SplashScreen.SplashScreen;
import sacalapp.hay_chico.SplashScreen.login.Login;
import sacalapp.hay_chico.SplashScreen.login.config;

import static sacalapp.hay_chico.SplashScreen.login.config.EMAIL_SHARED_PREF;


public class ajustes extends AppCompatActivity {

    private String id_usuario,numero_cel="",cuenta,codigo=" ",numero_nuevo,pass1,pass2,pass3,pass_ac,app_ver,lvl;
    int par=0,par1=0,conut=0,equipos=0;
    Dialog customDialog = null;
    Dialog customDialog_2 = null;

    int dato=0,datoo=0;

    private TextView textView_info,numero_telefonico,version;
    ToggleButton toggleButton;
    Button darm,verificar_numero,cambiar_contaseña;
    ArrayList id_equipo=new ArrayList();
    ArrayList id_partido=new ArrayList();
    private TextInputLayout tilCodigo,til_numero,til_vi_pa,til_nu_pa,til_co_pa;

    public static final String OTP_REGEX = "[0-9]{1,6}";
    String otp;
    private ImageButton btn_recre,btn_ama,btn_sem,btn_pr,btn_ley;



    @Override
    protected void onCreate(Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_ajustes);

        equipos=0;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ajustes);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ajustes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        darm= (Button)findViewById(R.id.btn_darme_alta);
        verificar_numero= (Button)findViewById(R.id.btn_verificar);
        cambiar_contaseña= (Button)findViewById(R.id.btn_contra);
        toggleButton= (ToggleButton) findViewById(R.id.tg_verificado);
        textView_info=(TextView)findViewById(R.id.lbl_info_verificado);
        numero_telefonico=(TextView)findViewById(R.id.lbl_numero);
        version=(TextView)findViewById(R.id.lbl_version_app);
        id_usuario=getIntent().getExtras().getString("id_jugador");

        btn_recre=(ImageButton)findViewById(R.id.btn_recreativo_a);
        btn_ama=(ImageButton)findViewById(R.id.btn_amateur_a);
        btn_sem=(ImageButton)findViewById(R.id.btn_semipro_a);
        btn_pr=(ImageButton)findViewById(R.id.btn_pro_a);
        btn_ley=(ImageButton)findViewById(R.id.btn_leyenda_a);



         app_ver = BuildConfig.VERSION_NAME;
        version.setText(app_ver);


        new ajustes.ConsultarDatos_jugador().execute("http://www.sacalapp.com/ajustes.php?id_usuario=" + id_usuario);

        toggleButton.setEnabled(false);


        verificar_numero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrar_2();
            }
        });


        cambiar_contaseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cambiar_pass();
            }
        });


        darm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (equipos==0){
                    mostrar();

                }else if (equipos>0){
                    Toast.makeText(getApplicationContext(), "No puedes salir, estas convocado a un partido o perteneces algún equipo", Toast.LENGTH_LONG).show();
                }
            }
        });

        Mostraraquipos();

        btn_recre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            lvl="Recreativo";
            Cambiar_nivel();
            }
        });
        btn_ama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lvl="Amateur";
                Cambiar_nivel();
            }
        });
        btn_sem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lvl="SemiPro";
                Cambiar_nivel();
            }
        });
        btn_pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lvl="Pro";
                Cambiar_nivel();
            }
        });
        btn_ley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lvl="Leyenda";
                Cambiar_nivel();
            }
        });

        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {

                //From the received text string you may do string operations to get the required OTP
                //It depends on your SMS format
                Log.e("Message",messageText);
                Toast.makeText(ajustes.this,"Message: "+messageText,Toast.LENGTH_LONG).show();

                // If your OTP is six digits number, you may use the below code

                Pattern pattern = Pattern.compile(OTP_REGEX);
                Matcher matcher = pattern.matcher(messageText);

                while (matcher.find())
                {
                    otp = matcher.group();
                }

                Toast.makeText(ajustes.this,"OTP: "+ otp ,Toast.LENGTH_LONG).show();

            }
        });




    }


    public AlertDialog Cambiar_nivel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Cambiar nivel de juego")
                .setMessage("Cambiar tu nivel a "+lvl)
                .setPositiveButton("CAMBIAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                dato=4;
                                new ajustes.CargarDato_actualizar().execute("http://www.sacalapp.com/actualiza_Nivel.php?usuario_id=" + id_usuario+"&Nivel="+lvl);

                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
            builder.show();
        return builder.create();
    }

    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.comun_2, menu);


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

    private void Mostraraquipos() {
        id_equipo.clear();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/consulta_equipos_perfil.php?id_jugador=" + id_usuario, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        conut=jsonArray.length();
                        equi_1();
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

    private void equi_1() {

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/partidos_user.php?id_usuario="+id_usuario, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        par=jsonArray.length();
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

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/partidos_user_2.php?id_usuario="+id_usuario, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        par1=jsonArray.length();

                        equipos=conut+par1+par;

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

            borrar();


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

            Dialogo_inser();

        }
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


            if (dato==1){
                Toast.makeText(getApplicationContext(), "Cuenta Verificada", Toast.LENGTH_LONG).show();
            }else if (dato==2){
                Toast.makeText(getApplicationContext(), "Se ha cambiado el número correctamente, ahora verifícalo.", Toast.LENGTH_LONG).show();
            }else if (dato==3){
                Toast.makeText(getApplicationContext(), "Cambio de Contraseña Correctamente", Toast.LENGTH_LONG).show();
            }else if (dato==4){
                Toast.makeText(getApplicationContext(), "Cambio de NIVEL Correctamente", Toast.LENGTH_LONG).show();
            }

            new ajustes.ConsultarDatos_jugador().execute("http://www.sacalapp.com/ajustes.php?id_usuario=" + id_usuario);


        }
    }

    private void borrar() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "user", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        bd.delete("usuario", "codigo=" + 0, null);



        bd.close();
        Toast.makeText(getApplicationContext(), "Gracias por ser parte de ¿Hay chico?", Toast.LENGTH_LONG).show();
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
        titulo.setText("Darme de alta");

        TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
        contenido.setText("¿Deseas Eliminar tu cuenta?");


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

                new ajustes.CargarDato().execute("http://www.sacalapp.com/eliminar_usuario.php?id_usuario=" + id_usuario);

                Intent intent = new Intent(ajustes.this, SplashScreen.class);
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

    public void mostrar_2() {
        // con este tema personalizado evitamos los bordes por defecto
        customDialog_2 = new Dialog(this, R.style.Theme_Dialog_Translucent);
        //deshabilitamos el título por defecto
        customDialog_2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog_2.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog_2.setContentView(R.layout.verificar_tu_numero);

        CheckBox permiso = (CheckBox) customDialog_2.findViewById(R.id.ch_acepto);

        final Button aceptar = (Button) customDialog_2.findViewById(R.id.aceptar);
        Button cancelar = (Button) customDialog_2.findViewById(R.id.cancelar);

        aceptar.setVisibility(View.INVISIBLE);

        permiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = ((CheckBox)view).isChecked();

                if (isChecked==true) {
                    aceptar.setVisibility(View.VISIBLE);

                }
                else   if (isChecked==false){
                    aceptar.setVisibility(View.INVISIBLE);

                }
            }
        });




        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog_2.dismiss();
                new ajustes.CargarDato_2().execute("http://www.sacalapp.com/msn.php?numero="+numero_cel+"&codigo="+cuenta );

            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customDialog_2.dismiss();
            }
        });

        customDialog_2.show();
    }

    public void Dialogo_inser(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ajustes.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.ingresar_codigo,null);

        builder.setView(dialogView);

        tilCodigo = (TextInputLayout) dialogView.findViewById(R.id.til_codigo);
        Button enviar = (Button)dialogView.findViewById(R.id.btn_ver);



        final AlertDialog dialog = builder.create();

        enviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                codigo = tilCodigo.getEditText().getText().toString().trim();

                if (codigo.equals(cuenta)){
                    new ajustes.CargarDato_actualizar().execute("http://www.sacalapp.com/actualizar_estado_verificacion.php?usuario_id=" + id_usuario);
                    dato=1;
                    customDialog_2.dismiss();
                }else {
                    customDialog_2.dismiss();
                    Toast.makeText(getApplicationContext(), "Código incorrecto", Toast.LENGTH_LONG).show();
                }

                dialog.dismiss();

            }
        });


        dialog.show();
    }

    public void cambiar_pass(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ajustes.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.cambiar_contrase,null);

        builder.setView(dialogView);

        til_vi_pa = (TextInputLayout) dialogView.findViewById(R.id.til_pass_vie);
        til_nu_pa = (TextInputLayout) dialogView.findViewById(R.id.til_nuw_pass);
        til_co_pa = (TextInputLayout) dialogView.findViewById(R.id.til_pass_con);
        Button cambiar = (Button)dialogView.findViewById(R.id.btn_cambiar);



        final AlertDialog dialog = builder.create();

        cambiar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                pass1 = til_vi_pa.getEditText().getText().toString().trim();
                pass2 = til_nu_pa.getEditText().getText().toString().trim();
                pass3 = til_co_pa.getEditText().getText().toString().trim();


                if (pass1.equals(pass_ac)){

                    if (pass2.length() < 8 || pass3.length() < 8){
                        Toast.makeText(getApplicationContext(), "Contraseña muy cortas", Toast.LENGTH_LONG).show();
                    }else if (pass2.equals(pass3)){
                        dato=3;
                        new ajustes.CargarDato_actualizar().execute("http://www.sacalapp.com/cambiar_contrase.php?usuario_id=" + id_usuario+"&pass="+pass3);
                        dialog.dismiss();
                    }else {
                        Toast.makeText(getApplicationContext(), "Contraseña no coinciden", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Contraseña actual incorrecta", Toast.LENGTH_LONG).show();
                }


            }
        });


        dialog.show();
    }

    public void tengo_codigo(View v) {
        Dialogo_inser();
    }

    public void numero(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ajustes.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.cambiar_numero,null);

        builder.setView(dialogView);

        til_numero = (TextInputLayout) dialogView.findViewById(R.id.til_telefono);
        Button enviar = (Button)dialogView.findViewById(R.id.btn_cambiar);



        final AlertDialog dialog = builder.create();

        enviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                numero_nuevo = til_numero.getEditText().getText().toString().trim();
                dato=2;
                new ajustes.CargarDato_actualizar().execute("http://www.sacalapp.com/actualiza_numero.php?usuario_id=" + id_usuario+"&numero="+numero_nuevo);

                dialog.dismiss();

            }
        });


        dialog.show();
    }

    public void cambiar_numero(View v) {

        numero();

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

                dato=0;

                String num=ja.getString(0);
                numero_cel="57"+ja.getString(0);
                cuenta=ja.getString(1);
                pass_ac=ja.getString(2);

                if (num.equals("0000000000")){
                    numero_telefonico.setText("Añadir un número de teléfono");
                    verificar_numero.setEnabled(false);
                }else {
                    numero_telefonico.setText("+57"+" "+ja.getString(0));
                    verificar_numero.setEnabled(true);
                }



                toggleButton.setVisibility(View.VISIBLE);

                if (cuenta.equals("Verificado")){
                    toggleButton.setChecked(true);
                    textView_info.setText("Número Verificado");
                    verificar_numero.setEnabled(false);
                }else {
                    toggleButton.setChecked(false);
                    textView_info.setText("Número No Verificado");
                }

                Mostraraquipos();

            } catch (JSONException e) {
                e.printStackTrace();

            }

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
        startService(new Intent(ajustes.this, Notificaciones.class));
        super.onPause();
    }

    @Override
    public void onStop() {

        // Toast.makeText(getApplicationContext(), "Start" ,Toast.LENGTH_LONG).show();
        startService(new Intent(ajustes.this, Notificaciones.class));

        super.onStop();
    }

    @Override
    public void onRestart(){

        //Toast.makeText(getApplicationContext(), "stop" ,Toast.LENGTH_LONG).show();
        stopService(new Intent(ajustes.this, Notificaciones.class));
        super.onRestart();


    }

}


