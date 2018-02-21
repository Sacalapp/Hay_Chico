package sacalapp.hay_chico;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class Terminos_Y_Condiciones extends AppCompatActivity implements View.OnClickListener {

    CheckBox Terms;
    TextView Term_cond;
    Button Continuar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminos__y__condiciones);

        Terms=(CheckBox)findViewById(R.id._terms);
        Continuar=(Button)findViewById(R.id.btn_continuar);
        Resources res = getResources();
        String texto = res.getString(R.string.Terminos_y_Condicones);

        CharSequence textoInterpretado = Html.fromHtml(texto);

        Term_cond=(TextView)findViewById(R.id.lbl_termino_y_Condiones);
        Term_cond.setText(textoInterpretado);
        Continuar.setOnClickListener(this);

        Terms.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true) {
                    Continuar.setVisibility(View.VISIBLE);
                }
                else {
                    Continuar.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        Intent reg = new Intent(this, Registro.class);
        startActivity(reg);
            finish();

    }


}
