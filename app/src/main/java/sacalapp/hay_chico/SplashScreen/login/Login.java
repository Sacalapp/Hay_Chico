package sacalapp.hay_chico.SplashScreen.login;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import dmax.dialog.SpotsDialog;
import sacalapp.hay_chico.AdminSQLiteOpenHelper;
import sacalapp.hay_chico.Compl_Perfil;
import sacalapp.hay_chico.Perfil;
import sacalapp.hay_chico.R;
import sacalapp.hay_chico.Registro;
import sacalapp.hay_chico.Terminos_Y_Condiciones;


public class Login extends AppCompatActivity implements  View.OnClickListener{

    private AppCompatButton buttonLogin, buttonRegistro;
    public static  String email_2;
    String EMAIL;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    RelativeLayout relativeLayout;
    private String nombre,posicion,edad,nick,ciudad,sexo,Haychico,descali,usuarui_id,Equipo,califica, Ema,pierna_habil,nombre_,estatus_jugador,Bienvenida;
    private boolean loggedIn = false;
    public static final int segundo = 10;
    public static int miliosegundo = segundo * 1000;
    TextView info;
    int foto=0,RUTA=0;
    ImageView imageView;
    //ProgressDialog pd;
    private TextInputLayout tilCodigo;
    private String name = "",email = "",birthday = "",id = "",telefono="0000000000",id_usuario,Ema2,password,codigo;
    CallbackManager mFacebookCallbackManager;
    LoginButton loginfacebook;
    ArrayList CORREOS=new ArrayList();
    ArrayList id_face=new ArrayList();
    SpotsDialog iniciando;

     private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginfacebook = (LoginButton) findViewById(R.id.login_button);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mFacebookCallbackManager = CallbackManager.Factory.create();
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email_login);
        mPasswordView = (EditText) findViewById(R.id.pass_login);
        buttonLogin = (AppCompatButton) findViewById(R.id.buttonLogin);
        //buttonRegistro = (AppCompatButton) findViewById(R.id.buttonRegistrar);
        //pd = new ProgressDialog(Login.this);

        buttonLogin.setOnClickListener(this);
        //buttonRegistro.setOnClickListener(this);
         iniciando = new SpotsDialog(Login.this,R.style.Custom);
         relativeLayout=(RelativeLayout)findViewById(R.id.login);



        //imageView=(ImageView) findViewById(R.id.imageView_fonfdo);
        //empezaranimacion();
        loginfacebook.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));
        loginfacebook.registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    // get profile information

                                    if (object.getString("name") != null) {
                                        name = object.getString("name");
                                        //Revisar_id_facebook();
                                    }
                                    if (object.getString("email") != null) {
                                        email = object.getString("email");
                                    }

                                    if (object.getString("id") != null) {
                                        id = object.getString("id");

                                    }

                                    if (name == null) {

                                    }

                                    RUTA=1;
                                    Revisar_id_facebook();
                                    //pd.setMessage("Validando...");
                                   // pd.setCancelable(false);
                                    //pd.show();


                                    iniciando.show();
                                    LoginManager.getInstance().logOut();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,birthday,email");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) { Log.d(Login.class.getCanonicalName(), error.getMessage());

                Toast.makeText(Login.this, "Error", Toast.LENGTH_LONG).show();
            }



        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    protected void onResume() {
        super.onResume();

        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){

            //We will start the Profile Activity
            Intent intent = new Intent(Login.this, Perfil.class);
            startActivity(intent);
        }
    }

    public void empezaranimacion() {

        new CountDownTimer(miliosegundo, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {


            }

            @Override
            public void onFinish() {
                foto=foto+1;

                if (foto==1){
                     nombre_="fondo_ini_7";
                    conte_foto();
                    empezaranimacion();
                }else if (foto==2){
                    nombre_="fondo_ini_2";
                    conte_foto();
                    empezaranimacion();
                }else if (foto==3){
                    nombre_="fondo_ini_3";
                    conte_foto();
                    empezaranimacion();
                }else if (foto==4){
                    nombre_="fondo_ini_4";
                    conte_foto();
                    empezaranimacion();
                }else if (foto==5){
                    nombre_="fondo_ini_6";
                    conte_foto();
                    empezaranimacion();
                }else if (foto==6) {
                    nombre_ = "fondo_ini_1";
                    conte_foto();
                    foto = 0;
                    empezaranimacion();

                }
            }
        }.start();
    }

    private void conte_foto() {
        String recurso="drawable";
        int res_imagen = getResources().getIdentifier(nombre_, recurso,getPackageName());
        imageView.setImageResource(res_imagen);
    }

    private void login(){

        if (RUTA == 0) {
             email = mEmailView.getText().toString().trim();
             password = mPasswordView.getText().toString().trim();
        }else if (RUTA == 1) {
            //email = mEmailView.getText().toString().trim();
            password = id;
        }

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.LOGIN_URL, new Response.Listener<String>() {



            @Override
            public void onResponse(String response) {
                //If we are getting success from server
                if(response.equalsIgnoreCase(config.LOGIN_SUCCESS)){
                    //Creating a shared preference
                    SharedPreferences sharedPreferences = Login.this.getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    //Adding values to editor
                    editor.putBoolean(config.LOGGEDIN_SHARED_PREF, true);
                    editor.putString(config.EMAIL_SHARED_PREF, email);

                    //Saving values to editor
                    editor.commit();

                        //Starting profile activity
                        Intent intent = new Intent(Login.this, Perfil.class);
                        intent.putExtra("tab", "1");
                        iniciando.dismiss();
                        //pd.dismiss();
                        startActivity(intent);
                        finish();
                        // Toast.makeText(Login.this, "Bienvenido", Toast.LENGTH_LONG).show();

                }else{
                    //If the server response is not success
                    //Displaying an error message on toast
                    iniciando.dismiss();
                    //pd.dismiss();

                    Snackbar.make(relativeLayout,"Usuario o Contaseña invalidas",Snackbar.LENGTH_SHORT).show();
                }
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put(config.KEY_EMAIL, email);
                params.put(config.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {


        if (view.getId()==R.id.buttonLogin){


            iniciando.show();
            //pd.setMessage("Validando...");
            //pd.setCancelable(false);
            //pd.show();
            RUTA=0;
            db_hel(view);
        }

    }

    public void db_hel(View v){
        Ema = mEmailView.getText().toString().trim();
        new ConsultarDatos().execute("http://www.sacalapp.com/perfil_datos.php?email=" + Ema);

    }

    public void db_hel_2(){
        Ema = email;
        new ConsultarDatos().execute("http://www.sacalapp.com/perfil_datos.php?email=" + Ema);

    }

    public void terminos_y_condicones(View v){

        Intent reg = new Intent(this, Terminos_Y_Condiciones.class);
        startActivity(reg);

    }

    public void resto(View view){

        if (RUTA == 0) {
             Ema2 = mEmailView.getText().toString().trim();
        }else if (RUTA == 1) {
            Ema2=email;
        }

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"user", null, 1);

        SQLiteDatabase bd = admin.getWritableDatabase();

        int dni=0;

        ContentValues registro = new ContentValues();

        registro.put("codigo", dni);
        registro.put("email", Ema2);
        registro.put("nombre", nombre);
        registro.put("posicion", posicion);
        registro.put("calificacion",descali );
        registro.put("ciudad", ciudad);
        registro.put("nick", nick);
        registro.put("edad", edad);
        registro.put("estatus", Haychico);
        registro.put("usuario_id", usuarui_id);
        registro.put("pierna", pierna_habil);
        registro.put("estatus_jugador", estatus_jugador);
        registro.put("Bienvenida", Bienvenida);
        registro.put("sexo", sexo);

        bd.insert("usuario", null, registro);

        bd.close();

        login();

    }

    public void olvide_mi_contraseña(View v) {
        ingrese_Email();
    }

    public void ingrese_Email(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.ingresar_email,null);

        builder.setView(dialogView);

        tilCodigo = (TextInputLayout) dialogView.findViewById(R.id.til_codigo);
        Button enviar = (Button)dialogView.findViewById(R.id.btn_ver);

        final AlertDialog dialog = builder.create();

        enviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                 codigo = tilCodigo.getEditText().getText().toString().trim();
                boolean a = esCorreoValido(codigo);

                if (a) {
                    comparar_correo();
                    dialog.dismiss();
                }


            }
        });

        dialog.show();
    }

    private void comparar_correo() {



        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://sacalapp.com/restable_contra_face.php?correo=" + codigo, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        if (jsonArray.length() == 1) {
                            Toast.makeText(getApplicationContext(), "Correo vinculado a una cuenta de Facebook. Acede con tu cuenta de Facebook", Toast.LENGTH_LONG).show();
                        }else if (jsonArray.length() == 0) {
                            new Login.ConsultarDatos_jugador().execute("http://www.sacalapp.com/restablecer_contrase.php?correo=" + codigo);

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
            Toast.makeText(Login.this, "Te hemos enviado un correo, con las instrucciones para Restablecer tu contraseña", Toast.LENGTH_LONG).show();
        }

    }

    private boolean esCorreoValido(String correo) {
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            tilCodigo.setError("Correo electrónico inválido");
            return false;
        } else {
            tilCodigo.setError(null);
        }

        return true;
    }


    //_____________________________________________________________________________

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

                nombre=(ja.getString(0));
                posicion=(ja.getString(1));
                nick=(ja.getString(2));
                sexo=(ja.getString(3));
                ciudad=(ja.getString(4));
                edad=(ja.getString(5));
                Haychico=(ja.getString(6));
                descali=(ja.getString(7));
                usuarui_id=(ja.getString(8));
                pierna_habil=(ja.getString(9));
                estatus_jugador=(ja.getString(11));
                Bienvenida=(ja.getString(12));

                if (nick.equals("null")){

                    Toast.makeText(Login.this, "Completa tu perfil", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this, Compl_Perfil.class);
                    intent.putExtra("parametro", Ema);
                    intent.putExtra("nombre", nombre);
                    iniciando.dismiss();
                    //pd.dismiss();
                    startActivity(intent);
                    finish();

                }else {
                    resto(null);
                }


           /*     if (sexo.equals("Masculino")){

                    img_sexo.setImageResource(R.mipmap.masculino);
                }else if (sexo.equals("Femenino")){

                    img_sexo.setImageResource(R.mipmap.femenino);
                }*/



            } catch (JSONException e) {
                e.printStackTrace();
                //pd.dismiss();
                iniciando.dismiss();
                Snackbar.make(relativeLayout,"Usuario o Contaseña invalidas",Snackbar.LENGTH_SHORT).show();
                //Toast.makeText(Login.this, "Error inicio", Toast.LENGTH_LONG).show();

            }

        }
    }

    private void Revisar_id_facebook() {

        CORREOS.clear();

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get("http://www.sacalapp.com/Login_facebook.php?id=" + id, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        count=jsonArray.length();
                        if (count==0){
                            Revisar_correos();
                        }else if (count==1){
                            for (int i=0;i<jsonArray.length();i++){
                                id_face.add(jsonArray.getJSONObject(i).getString("id_facebook"));
                            }
                            String id_2=id_face.get(0).toString();

                            if (id_2.equals(id)) {
                                db_hel_2();
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

    private void Revisar_correos() {

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
                        count=0;

                        for (int i=0;i<CORREOS.size();i++){

                            String Email=(CORREOS.get(i).toString());

                            if (Email.equals(email)){
                                count=count+1;
                            }

                        }

                        if (count==0){

                            new Login.CargarDatos().execute("http://www.sacalapp.com/insertar_usuario.php?nombre="+name+"&codigo_jugador="+telefono+"&email="+email+"&clave="+id);

                        }else {
                            Toast.makeText(Login.this, "El correo ya esta vinculado a una cuenta", Toast.LENGTH_SHORT).show();

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

            new traer_id().execute("http://www.sacalapp.com/traer_id_face.php?email=" + email);

        }
    }

    private class traer_id extends AsyncTask<String, Void, String> {
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
                id_usuario=ja.getString(0);
                new guardar_id().execute("http://www.sacalapp.com/Login_facebook_insertar.php?id=" + id+"&usuario_id="+id_usuario);
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }

    private class guardar_id extends AsyncTask<String, Void, String> {
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

            Intent intent = new Intent(Login.this, Compl_Perfil.class);
            intent.putExtra("parametro", email);
            intent.putExtra("nombre", name);
            intent.putExtra("clave",id);
            startActivity(intent);
            finish();




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


