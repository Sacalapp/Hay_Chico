package sacalapp.hay_chico;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
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
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class Registro extends AppCompatActivity {

    private TextInputLayout tilNombre;
    private TextInputLayout tilTelefono;
    private TextInputLayout tilCorreo;
    private TextInputLayout tilconCorreo;
    private TextInputLayout tilpassword;
    private TextInputLayout tilpassword_conf;
    public static  String email_2,nombre_usu,correo_com,nombre,telefono,correo,concorreo,clave_c,clave,id_insert,mPhoneNumber;

    ArrayList CORREOS=new ArrayList();
    EditText correo_f,numero_celular;
    int count=0;
    private static final long SPLASH_SCREEN_DELAY = 1000;
    public static final int segundo = 5;
    public static final int miliosegundo = segundo * 1000;
    public static final int dalay = 2;
    ProgressDialog pd;

    HorizontalStepView setpview5;
    EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        tilNombre = (TextInputLayout) findViewById(R.id.til_nombre);
        tilTelefono = (TextInputLayout) findViewById(R.id.til_telefono);
        tilCorreo = (TextInputLayout) findViewById(R.id.til_correo);
        tilconCorreo= (TextInputLayout) findViewById(R.id.til_conf_correo);
        tilpassword_conf = (TextInputLayout) findViewById(R.id.til_passconf);
        tilpassword = (TextInputLayout) findViewById(R.id.til_pass);
        pass=(EditText)findViewById(R.id.campo_password);

        numero_celular=(EditText)findViewById(R.id.campo_telefono);

        correo_f=(EditText)findViewById(R.id.campo_conf_correo);


        numero_celular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //traer_numero();
            }
        });

        pass.getText().toString();

        Button botonAceptar = (Button) findViewById(R.id.buttonRegistrate);
        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(Registro.this);
                pd.setMessage("Registrando");
                pd.setCancelable(false);
                pd.show();
                validarDatos();
            }
        });

        setpview5 = (HorizontalStepView) findViewById(R.id.step_view);


        List<StepBean> stepsBeanList = new ArrayList<>();

        StepBean stepBean2 = new StepBean("",0);
        StepBean stepBean3 = new StepBean( "",-1);
        StepBean stepBean4 = new StepBean("Tu Perfil",-1);


        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);
        stepsBeanList.add(stepBean4);


        setpview5
                .setStepViewTexts(stepsBeanList)
                .setTextSize(12)
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, android.R.color.white))
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, R.color.uncompleted_text_color))
                .setStepViewComplectedTextColor(ContextCompat.getColor(this, android.R.color.white))
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.uncompleted_text_color))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.drawable.complted))
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, R.drawable.default_icon))
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.attention));






    }

    @Override
    public void onBackPressed (){
createSimpleDialog();
        }

    public AlertDialog createSimpleDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Salir")
                .setMessage("Deseas Salir")
                .setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            
                            }
                        });

        builder.show();
        return builder.create();
    }


private void traer_numero(){

    TelephonyManager tMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
     mPhoneNumber = tMgr.getLine1Number();
    numero_celular.setText(mPhoneNumber);



}

    private void validarDatos() {
        boolean d=false;
         nombre = tilNombre.getEditText().getText().toString();
        nombre_usu=nombre;
         telefono = tilTelefono.getEditText().getText().toString();
         correo = tilCorreo.getEditText().getText().toString().trim();
         concorreo = tilconCorreo.getEditText().getText().toString().trim();
         clave = tilpassword_conf.getEditText().getText().toString();
         clave_c = tilpassword.getEditText().getText().toString();

        boolean a = esNombreValido(nombre);
        boolean b = esTelefonoValido(telefono);
        boolean c = esCorreoValido(correo);
        boolean cc = esCorreoValidocon(concorreo);


        if (pass.length() < 8){

            pass.setError( "La contraseña es muy corta" );
            pd.dismiss();
        }else{


            if (clave.equals(clave_c)){
                d = true;
                pass.setError(null);
                tilpassword_conf.setError(null);
            }else{

                tilpassword_conf.setError("Contraseñas Diferente");
                pd.dismiss();
            }

        }
        if (a && b && c && d && cc) {
            // OK, se pasa a la siguiente acción


            if(correo.equals(concorreo)){
               // Toast.makeText(this, "Se guarda el registro", Toast.LENGTH_LONG).show();
                correo_com=correo;
                Mostraraquipos();
            }else {
                tilconCorreo.setError("Correo electrónico no coinciden");
                correo_f.setText("");
                pd.dismiss();
            }




        }else{
            //pass.setError( "La contraseña es muy corta" );
            pd.dismiss();

        }

    }

    private void Mostraraquipos() {

        CORREOS.clear();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/correos.php", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        for (int i=0;i<jsonArray.length();i++){
                            CORREOS.add(jsonArray.getJSONObject(i).getString("email"));
                        }

                        for (int i=0;i<CORREOS.size();i++){

                            String Email=(CORREOS.get(i).toString());

                            if (Email.equals(correo_com)){
                                count=count+1;
                            }

                        }

                        if (count==0){
                            //Toast.makeText(getApplicationContext(), "Completa perfil", Toast.LENGTH_LONG).show();
                           new CargarDatos().execute("http://www.sacalapp.com/insertar_usuario.php?nombre="+nombre+"&codigo_jugador="+telefono+"&email="+correo+"&clave="+clave_c);
                        }else {
                            tilconCorreo.setError("El correo ya existe.");
                            count=0;
                            pd.dismiss();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error equipo", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void ayuda(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Importante");
        builder.setMessage("La contraseña debe tener letras y números, y la longitud mínima es 8 caracteres");
        builder.setPositiveButton("OK",null);
        builder.create();
        builder.show();
    }

    private boolean esNombreValido(String nombre) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$"); //[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]
        if (!patron.matcher(nombre).matches() || nombre.length() > 20) {
            tilNombre.setError("Nombre inválido, no utilices tilde");
            return false;
        } else {
            tilNombre.setError(null);
        }

        return true;
    }

    private boolean esTelefonoValido(String telefono) {
        if (!Patterns.PHONE.matcher(telefono).matches()) {
            tilTelefono.setError("Teléfono inválido");
            return false;
        } else {
            tilTelefono.setError(null);
        }

        return true;
    }

    private boolean esCorreoValido(String correo) {
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            tilCorreo.setError("Correo electrónico inválido");
            return false;
        } else {
            tilCorreo.setError(null);
        }

        return true;
    }

    private boolean esCorreoValidocon(String correo) {
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            tilconCorreo.setError("Correo electrónico inválido");
            return false;
        } else {
            tilconCorreo.setError(null);
        }

        return true;
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

            //Toast.makeText(getApplicationContext(), "Se almacenaron los datos correctamente", Toast.LENGTH_LONG).show();

            JSONArray ja = null;
            try {
                ja = new JSONArray(result);
                id_insert=ja.getString(0);
                pd.dismiss();
            }catch (Exception e){
                e.printStackTrace();
            }

            email_2  = tilCorreo.getEditText().getText().toString().trim();
            Intent intent = new Intent(Registro.this, Compl_Perfil.class);
            intent.putExtra("parametro", email_2);
            intent.putExtra("nombre", nombre_usu);
            intent.putExtra("clave",clave_c);
            startActivity(intent);
            finish();
            pd.dismiss();

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
