package sacalapp.hay_chico.Rss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import sacalapp.hay_chico.R;

public class Noticias_detalles extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias_detalles);
        webView=(WebView)findViewById((R.id.noticias));
        Bundle bundle = getIntent().getExtras();
        webView.loadUrl(bundle.getString("Link"));


    }
}
