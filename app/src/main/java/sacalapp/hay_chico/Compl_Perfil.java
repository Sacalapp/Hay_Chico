package sacalapp.hay_chico;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sacalapp.hay_chico.SplashScreen.SplashScreen;
import sacalapp.hay_chico.SplashScreen.login.config;

import static sacalapp.hay_chico.SplashScreen.login.config.EMAIL_SHARED_PREF;

public class Compl_Perfil extends AppCompatActivity implements View.OnClickListener {

    EditText nick;
    TextView posi,sexo,Año, Mes, Dia,Pierna,ciudad_,nombre_view,lE;
    private AppCompatButton buttonActualizar;
    private String año,mes,dia,fecha,edad,nombre,insert;
    boolean bb=false,cc=false,dd=false;
    private String MES,apodo="no",email_2,foto;
    private int Saño,Smes,Sdia,seaño,semes,sedia,Edad;
    private String ciudad,Haychico,descali,usuarui_id,Equipo,califica, Ema,pierna_habil,nombre_,nick_,sexo_,lvl;
    Dialog customDialog = null;
    ImageView img_portero,img_defensa,img_medio,img_delantero;
    ProgressDialog pd;
    static final int DATE_ID =0;
    String posicion,sexo_2,pierna,Ciudad_;
    private AppCompatButton buttonLogin;
    private TextInputLayout tilApodo;
    private int pp=0,ss=0,ff=0,aa=0,pi=0,ci=0,ni=0,datoo;

   private ImageButton btn_recre,btn_ama,btn_sem,btn_pr,btn_ley;

    HorizontalStepView setpview5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_compl__perfil);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        email_2 = getIntent().getExtras().getString("parametro");
        nombre = getIntent().getExtras().getString("nombre");
        insert = getIntent().getExtras().getString("clave");

        btn_recre=(ImageButton)findViewById(R.id.btn_recreativo);
        btn_ama=(ImageButton)findViewById(R.id.btn_amateur);
        btn_sem=(ImageButton)findViewById(R.id.btn_semipro);
        btn_pr=(ImageButton)findViewById(R.id.btn_pro);
        btn_ley=(ImageButton)findViewById(R.id.btn_leyenda);
        lE=(TextView)findViewById(R.id.lbl_nivel);

        Calendar C = Calendar.getInstance();
        seaño = C.get(Calendar.YEAR);
        semes = C.get(Calendar.MONTH);
        sedia = C.get(Calendar.DAY_OF_MONTH);

        tilApodo = (TextInputLayout) findViewById(R.id.til_nick);

        nombre_view = (TextView) findViewById(R.id.nombre_perfil);
        posi=(TextView)findViewById(R.id.lbl_posicion);
        sexo=(TextView)findViewById(R.id.lbl_sexo_2);
        Año = (TextView) findViewById(R.id.lbl_fecha);
        Pierna=(TextView)findViewById(R.id.lbl_pierna_habil);
        ciudad_ = (TextView) findViewById(R.id.lbl_ciudad);

        Año.setText("AAAA-MM-DD");
        nombre_view.setText(nombre);


        buttonLogin = (AppCompatButton) findViewById(R.id.buttonCompletar);
        buttonLogin.setOnClickListener(this);

        setpview5 = (HorizontalStepView) findViewById(R.id.step_view);


        List<StepBean> stepsBeanList = new ArrayList<>();

        StepBean stepBean2 = new StepBean("",1);
        StepBean stepBean3 = new StepBean( "",0);
        StepBean stepBean4 = new StepBean("Tu Perfil",-1);


        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);
        stepsBeanList.add(stepBean4);


        setpview5
                .setStepViewTexts(stepsBeanList)//总步骤
                .setTextSize(12)//set textSize
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, android.R.color.white))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, R.color.uncompleted_text_color))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(this, android.R.color.white))//设置StepsView text完成线的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.uncompleted_text_color))//设置StepsView text未完成线的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.drawable.complted))//设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.attention));//设置StepsViewIndicator AttentionIcon



        btn_recre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datoo==0){
                    ni=1;
                    lE.setText("Nivel Recreativo");
                    lvl="Recreativo";
                    btn_ama.setVisibility(View.INVISIBLE);
                    btn_sem.setVisibility(View.INVISIBLE);
                    btn_pr.setVisibility(View.INVISIBLE);
                    btn_ley.setVisibility(View.INVISIBLE);
                    datoo=1;
                }else  if (datoo==1) {
                    ni=0;
                    lE.setText(" ");
                    btn_ama.setVisibility(View.VISIBLE);
                    btn_sem.setVisibility(View.VISIBLE);
                    btn_pr.setVisibility(View.VISIBLE);
                    btn_ley.setVisibility(View.VISIBLE);
                    datoo=0;
                }
            }
        });
        btn_ama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datoo==0){
                    ni=1;
                    lE.setText("Nivel Amateur");
                    lvl="Amateur";
                    btn_recre.setVisibility(View.INVISIBLE);
                    btn_sem.setVisibility(View.INVISIBLE);
                    btn_pr.setVisibility(View.INVISIBLE);
                    btn_ley.setVisibility(View.INVISIBLE);
                    datoo=1;
                }else  if (datoo==1) {
                    ni=0;
                    lE.setText(" ");
                    btn_recre.setVisibility(View.VISIBLE);
                    btn_sem.setVisibility(View.VISIBLE);
                    btn_pr.setVisibility(View.VISIBLE);
                    btn_ley.setVisibility(View.VISIBLE);
                    datoo=0;
                }
            }
        });
        btn_sem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datoo==0){
                    ni=1;
                    lE.setText("Nivel SemiPro");
                    lvl="SemiPro";
                    btn_recre.setVisibility(View.INVISIBLE);
                    btn_ama.setVisibility(View.INVISIBLE);
                    btn_pr.setVisibility(View.INVISIBLE);
                    btn_ley.setVisibility(View.INVISIBLE);
                    datoo=1;
                }else  if (datoo==1) {
                    ni=0;
                    lE.setText(" ");
                    btn_recre.setVisibility(View.VISIBLE);
                    btn_ama.setVisibility(View.VISIBLE);
                    btn_pr.setVisibility(View.VISIBLE);
                    btn_ley.setVisibility(View.VISIBLE);
                    datoo=0;
                }
            }
        });
        btn_pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datoo==0){
                    ni=1;
                    lE.setText("Nivel Pro");
                    lvl="Pro";
                    btn_recre.setVisibility(View.INVISIBLE);
                    btn_ama.setVisibility(View.INVISIBLE);
                    btn_sem.setVisibility(View.INVISIBLE);
                    btn_ley.setVisibility(View.INVISIBLE);
                    datoo=1;
                }else  if (datoo==1) {
                    ni=0;
                    lE.setText(" ");
                    btn_recre.setVisibility(View.VISIBLE);
                    btn_ama.setVisibility(View.VISIBLE);
                    btn_sem.setVisibility(View.VISIBLE);
                    btn_ley.setVisibility(View.VISIBLE);
                    datoo=0;
                }
            }
        });
        btn_ley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datoo==0){
                    ni=1;
                    lE.setText("Nivel Leyenda");
                    lvl="Leyenda";
                    btn_recre.setVisibility(View.INVISIBLE);
                    btn_ama.setVisibility(View.INVISIBLE);
                    btn_sem.setVisibility(View.INVISIBLE);
                    btn_pr.setVisibility(View.INVISIBLE);
                    datoo=1;
                }else  if (datoo==1) {
                    ni=0;
                    lE.setText(" ");
                    btn_recre.setVisibility(View.VISIBLE);
                    btn_ama.setVisibility(View.VISIBLE);
                    btn_sem.setVisibility(View.VISIBLE);
                    btn_pr.setVisibility(View.VISIBLE);
                    datoo=0;
                }
            }
        });


    }

    public void Calendario(View v) {
        showDialog(DATE_ID);
    }

    //________________________________Diagolos________________________________

    private void MostrarDialogo(){

        //Creamos un nuevo AlertDialog.Builder pasandole como parametro el contexto
        AlertDialog.Builder ADBuilder = new AlertDialog.Builder(Compl_Perfil.this);

        //ADBuilder.setIcon(R.drawable.ic_launcher);//Definimos el icono
        ADBuilder.setTitle("Posiciones ");//Asignamos un titulo al mensaje

        //Creamos un nuevo ArrayAdapter de 'Strings' y pasamos como parametros (Contexto, int id "Referencia a layout");
        final ArrayAdapter arrayAdapter = new ArrayAdapter(Compl_Perfil.this,android.R.layout.select_dialog_singlechoice);

        //Añadimos los elementos a mostrar
        arrayAdapter.add("Portero");
        arrayAdapter.add("Defensa");
        arrayAdapter.add("Medio");
        arrayAdapter.add("Delantero");

        //Creamos un boton para cancelar el dialog
        ADBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//Cerramos el dialogo
            }
        });

        //Capturamos el evento 'OnClick' de los elementos en el dialogo
        ADBuilder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int _item) {

                //Creamos un toast para mostrar el elemento selecionado
               // Toast.makeText(getApplicationContext(), arrayAdapter.getItem(_item).toString(), Toast.LENGTH_SHORT).show();
                posi.setText(arrayAdapter.getItem(_item).toString());
                posicion=arrayAdapter.getItem(_item).toString();
                foto=email_2+".jpg";
                pp=1;
                MostrarDialogosexo();

            }
        });

        ADBuilder.show();//Mostramos el dialogo
    }

    private void MostrarDialogosexo(){

        //Creamos un nuevo AlertDialog.Builder pasandole como parametro el contexto
        AlertDialog.Builder ADBuilder = new AlertDialog.Builder(Compl_Perfil.this);

        //ADBuilder.setIcon(R.drawable.ic_launcher);//Definimos el icono
        ADBuilder.setTitle("Sexo ");//Asignamos un titulo al mensaje

        //Creamos un nuevo ArrayAdapter de 'Strings' y pasamos como parametros (Contexto, int id "Referencia a layout");
        final ArrayAdapter arrayAdapter = new ArrayAdapter(Compl_Perfil.this,android.R.layout.select_dialog_singlechoice);

        //Añadimos los elementos a mostrar
        arrayAdapter.add("Masculino");
        arrayAdapter.add("Femenino");


        //Creamos un boton para cancelar el dialog
        ADBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//Cerramos el dialogo
            }
        });

        //Capturamos el evento 'OnClick' de los elementos en el dialogo
        ADBuilder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int _item) {

                //Creamos un toast para mostrar el elemento selecionado
                //Toast.makeText(getApplicationContext(), arrayAdapter.getItem(_item).toString(), Toast.LENGTH_SHORT).show();
                sexo.setText(arrayAdapter.getItem(_item).toString());
                sexo_2=arrayAdapter.getItem(_item).toString();
                ss=1;
                MostrarDialogopierna();


            }
        });

        ADBuilder.show();//Mostramos el dialogo
    }

    private void MostrarDialogopierna(){

        //Creamos un nuevo AlertDialog.Builder pasandole como parametro el contexto
        AlertDialog.Builder ADBuilder = new AlertDialog.Builder(Compl_Perfil.this);

        //ADBuilder.setIcon(R.drawable.ic_launcher);//Definimos el icono
        ADBuilder.setTitle("Pierna Hábil ");//Asignamos un titulo al mensaje

        //Creamos un nuevo ArrayAdapter de 'Strings' y pasamos como parametros (Contexto, int id "Referencia a layout");
        final ArrayAdapter arrayAdapter = new ArrayAdapter(Compl_Perfil.this,android.R.layout.select_dialog_singlechoice);

        //Añadimos los elementos a mostrar
        arrayAdapter.add("Derecha");
        arrayAdapter.add("Izquierda");


        //Creamos un boton para cancelar el dialog
        ADBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//Cerramos el dialogo
            }
        });

        //Capturamos el evento 'OnClick' de los elementos en el dialogo
        ADBuilder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int _item) {

                //Creamos un toast para mostrar el elemento selecionado
                //Toast.makeText(getApplicationContext(), arrayAdapter.getItem(_item).toString(), Toast.LENGTH_SHORT).show();
                Pierna.setText(arrayAdapter.getItem(_item).toString());
                pierna=arrayAdapter.getItem(_item).toString();
                pi=1;
                MostrarDialogociudad();

            }
        });

        ADBuilder.show();//Mostramos el dialogo
    }

    private void MostrarDialogociudad(){

        //Creamos un nuevo AlertDialog.Builder pasandole como parametro el contexto
        AlertDialog.Builder ADBuilder = new AlertDialog.Builder(Compl_Perfil.this);

        //ADBuilder.setIcon(R.drawable.ic_launcher);//Definimos el icono
        ADBuilder.setTitle("Ciudad ");//Asignamos un titulo al mensaje

        //Creamos un nuevo ArrayAdapter de 'Strings' y pasamos como parametros (Contexto, int id "Referencia a layout");
        final ArrayAdapter arrayAdapter = new ArrayAdapter(Compl_Perfil.this,android.R.layout.select_dialog_singlechoice);

        //Añadimos los elementos a mostrar
        arrayAdapter.add("Cali");
        arrayAdapter.add("Jamundi");
        arrayAdapter.add("Palmira");
        arrayAdapter.add("Yumbo");

        //Creamos un boton para cancelar el dialog
        ADBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//Cerramos el dialogo
            }
        });

        //Capturamos el evento 'OnClick' de los elementos en el dialogo
        ADBuilder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int _item) {

                //Creamos un toast para mostrar el elemento selecionado
                //Toast.makeText(getApplicationContext(), arrayAdapter.getItem(_item).toString(), Toast.LENGTH_SHORT).show();
                ciudad_.setText(arrayAdapter.getItem(_item).toString());
                Ciudad_=arrayAdapter.getItem(_item).toString();
                ci=1;

            }
        });

        ADBuilder.show();//Mostramos el dialogo
    }

    public void info_niveles() {
        // con este tema personalizado evitamos los bordes por defecto
        customDialog = new Dialog(this, R.style.Theme_Dialog_Translucent);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.info_niveles);


        Button salir = (Button) customDialog.findViewById(R.id.btn_salir);

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });


        customDialog.show();
    }

    public void posi(View v) {

        MostrarDialogo();

    }

    public void sex(View v) {

        MostrarDialogosexo();

    }

    public void pierna(View v) {

        MostrarDialogopierna();

    }

    public void ciudad(View v) {

        MostrarDialogociudad();

    }

    public void Nivel(View v) {

        info_niveles();

    }






   /* private boolean esNombreValido(String Apodo) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(Apodo).matches() || Apodo.length() > 30) {
            tilApodo.setError("Apodo inválido");
            return false;
        } else {
            tilApodo.setError(null);
        }

        return true;
    }

*/

    //-----------------------------Calendario------------------------------------------


    private void MostrarFecha(){

        Edad = seaño - Saño;
        if(semes<Smes){
            Edad=Edad-1;
        }else if(semes==Smes){
            Edad=Edad-1;
            if (sedia>Sdia){
                Edad=Edad+1;
            }else if(sedia==Sdia) {
                Edad = Edad + 1;
            }
        }



        edad = String.valueOf(Edad);


        //Año.setText(new StringBuilder().append(Saño));
        //Mes.setText(new StringBuilder().append(Smes));
        //Dia.setText(new StringBuilder().append(Sdia));
        año = String.valueOf(Saño);
        dia = String.valueOf(Sdia);

        switch (Smes){

            case 0:
                MES="01";
                break;
            case 1:
                MES="02";
                break;
            case 2:
                MES="03";
                break;
            case 3:
                MES="04";
                break;
            case 4:
                MES="05";
                break;
            case 5:
                MES="06";
                break;
            case 6:
                MES="07";
                break;
            case 7:
                MES="08";
                break;
            case 8:
                MES="09";
                break;
            case 9:
                MES="10";
                break;
            case 10:
                MES="11";
                break;
            case 11:
                MES="12";
                break;


        }

        fecha = año + "-" + MES + "-" + dia;
        ff=1;
        Año.setText(fecha);

    }


    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int año, int mes, int dia) {

                    Saño = año;
                    Smes = mes;
                    Sdia = dia;

                    int resul;

                    resul=seaño-Saño;
                    if (resul<=15) {
                        Toast.makeText(Compl_Perfil.this, "Debes tener más de 15 años.", Toast.LENGTH_SHORT).show();
                    }else if (resul>15) {
                        MostrarFecha();
                    }
                    
                }
            };

    protected Dialog onCreateDialog (int id){
        switch (id){

            case DATE_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        seaño,semes,sedia
                );
        }
        return null;
    }

    @Override
    public void onClick(View view) {

        if(apodo.equals(" ")){
            Toast.makeText(getApplicationContext(), "Por favor, Ingresa tu Apodo", Toast.LENGTH_SHORT).show();

        }else {
            apodo = tilApodo.getEditText().getText().toString();
                aa=1;
        }

        if(pp==1 & ss==1 & ff==1 & pi==1 & aa==1 & ci==1 & ni==1){

            pd = new ProgressDialog(Compl_Perfil.this);
            pd.setMessage("Completando...");
            pd.setCancelable(false);
            pd.show();

            new Compl_Perfil.ActualizarDatos().execute("http://www.sacalapp.com/actualizar_usuario_.php?nick=" + apodo + "&posicion=" + posicion + "&sexo=" + sexo_2 + "&fecha_na=" + fecha + "&edad_usu=" + edad + "&email=" + email_2+ "&piern_habil=" + pierna+ "&foto=" + foto+ "&ciudad=" + Ciudad_+ "&actividad=" + fecha+ "&Nivel=" + lvl);

        }else{

            Toast.makeText(getApplicationContext(), "Por favor, Ingresa Todos los datos", Toast.LENGTH_SHORT).show();

        }


    }

    private class ActualizarDatos extends AsyncTask<String, Void, String> {
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

            new Compl_Perfil.ConsultarDatos().execute("http://www.sacalapp.com/perfil_datos.php?email=" + email_2);

            /*Toast.makeText(getApplicationContext(), "Gracias por Registrate, BIENVENIDO", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Compl_Perfil.this, Login.class);
            startActivity(intent);
            finish();*/

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

                nombre=(ja.getString(0));
                posicion=(ja.getString(1));
                nick_=(ja.getString(2));
                sexo_=(ja.getString(3));
                ciudad=(ja.getString(4));
                edad=(ja.getString(5));
                Haychico=(ja.getString(6));
                descali=(ja.getString(7));
                usuarui_id=(ja.getString(8));
                pierna_habil=(ja.getString(9));


                resto(null);



           /*     if (sexo.equals("Masculino")){

                    img_sexo.setImageResource(R.mipmap.masculino);
                }else if (sexo.equals("Femenino")){

                    img_sexo.setImageResource(R.mipmap.femenino);
                }*/



            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Compl_Perfil.this, "Error inicio", Toast.LENGTH_LONG).show();
                pd.dismiss();

            }

        }
    }

    public void resto(View view){


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"user", null, 1);

        SQLiteDatabase bd = admin.getWritableDatabase();

        int dni=0;

        ContentValues registro = new ContentValues();

        registro.put("codigo", dni);
        registro.put("email", email_2);
        registro.put("nombre", nombre);
        registro.put("posicion", posicion);
        registro.put("calificacion",descali );
        registro.put("ciudad", ciudad);
        registro.put("nick", nick_);
        registro.put("edad", edad);
        registro.put("estatus", Haychico);
        registro.put("usuario_id", usuarui_id);
        registro.put("pierna", pierna_habil);
        registro.put("Bienvenida", "NO");
        registro.put("estatus_jugador", "Activo");
        registro.put("sexo", sexo_2);

        bd.insert("usuario", null, registro);


        bd.close();

        login();



    }

    private void login(){

        //Getting values from edit texts
        final String email = email_2;
        final String password = insert;

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.LOGIN_URL, new Response.Listener<String>() {



            @Override
            public void onResponse(String response) {
                //If we are getting success from server
                if(response.equalsIgnoreCase(config.LOGIN_SUCCESS)){
                    //Creating a shared preference
                    SharedPreferences sharedPreferences = Compl_Perfil.this.getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    //Adding values to editor
                    editor.putBoolean(config.LOGGEDIN_SHARED_PREF, true);
                    editor.putString(config.EMAIL_SHARED_PREF, email);

                    //Saving values to editor
                    editor.commit();

                    pd.dismiss();
                    //Starting profile activity
                    Intent intent = new Intent(Compl_Perfil.this, Perfil.class);
                    intent.putExtra("tab", "1");
                    startActivity(intent);
                    finish();
                     //Toast.makeText(Compl_Perfil.this, "Bienvenido", Toast.LENGTH_LONG).show();

                }else{
                    pd.dismiss();
                    //If the server response is not success
                    //Displaying an error message on toast
                    //Toast.makeText(Compl_Perfil.this, "Usuario o Contaseña invalidas", Toast.LENGTH_LONG).show();
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
