package sacalapp.hay_chico;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sacalapp.hay_chico.SplashScreen.login.Login;

public class Suspendido extends AppCompatActivity implements View.OnClickListener {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspendido);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        button=(Button)findViewById(R.id.btn_volver_sus);
    }

    @Override
    public void onClick(View view) {
        Intent reg = new Intent(this, Login.class);
        startActivity(reg);
        finish();
    }
}
