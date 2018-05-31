package cuellar.jose.pedido;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Principal extends AppCompatActivity {

    WebView wv;
    String WEB_ROOT;
    SwipeRefreshLayout swipe;
    FloatingActionButton fab, fab1, fab2;
    Animation fabOpen, fabClose, rotateforward, rotateBackward;
    boolean isOpen = false;
    boolean isClose = true;
    Intent intent;
    AlertDialog.Builder dialogo1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        WEB_ROOT = getResources().getString(R.string.web_root);
        dialogo1 = new AlertDialog.Builder(this);
        //webview a una pagina internet


        swipe = (SwipeRefreshLayout)findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                WebAction();
            }
        });
        WebAction();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);

        fabOpen = AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this,R.anim.fab_close);

        rotateforward = AnimationUtils.loadAnimation(this, cuellar.jose.pedido.R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, cuellar.jose.pedido.R.anim.rotate_backward);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animateFab();

            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Principal.this, "Llamando..", Toast.LENGTH_SHORT).show();
                llamar();
                cerrar();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comentaron();
                cerrar();
            }
        });

    }

    private void animateFab(){
        if(isOpen)
        {
            fab.startAnimation(rotateBackward);
            fab1.startAnimation(fabClose);
            fab2.startAnimation(fabClose);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isOpen=false;
        }else
        {
            fab.startAnimation(rotateforward);
            fab1.startAnimation(fabOpen);
            fab2.startAnimation(fabOpen);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isOpen=true;
        }
    }

    // esconder condo undo FoatinnButon
    private void cerrar(){
        if(isClose)
        {
            fab.startAnimation(rotateBackward);
            fab1.startAnimation(fabClose);
            fab2.startAnimation(fabClose);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isOpen=true;
        }else
        {
            fab.startAnimation(rotateforward);
            fab1.startAnimation(fabClose);
            fab2.startAnimation(fabClose);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isClose=false;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.desarrollo) {
            desarrollo();
        }
        if (id == R.id.llamar) {
            llamar();
        }

        return super.onOptionsItemSelected(item);
    }

    public void desarrollo(){

        dialogo1.setTitle("DESARROLLO");
        dialogo1.setMessage("Roque Castro Garzon / Duvan Andres Barrera. \n" +
                "Tecnologo en desarrollo de sofware\n" +
                "3219368149");

        dialogo1.show();
    }


    private void llamar() {

        intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:3219368149"));
        if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Principal.this, new String[]{Manifest.permission.CALL_PHONE}, 105);
        } else {
            startActivity(intent);
            //finish();
        }

    }


    public void WebAction(){

        wv = (WebView) findViewById(R.id.webView);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setAppCacheEnabled(true);
        //wv.loadUrl((String.valueOf(Html.fromHtml(WEB_ROOT))));
        wv.loadUrl(WEB_ROOT);
        swipe.setRefreshing(true);
        wv.setWebViewClient(new WebViewClient(){

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                wv.loadUrl("file:///android_asset/error.html");

            }

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                swipe.setRefreshing(false);
            }

        });

    }

    // comentarios codigo
    public void comentaron(){

        wv = (WebView) findViewById(R.id.webView);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setAppCacheEnabled(true);
        //wv.loadUrl((String.valueOf(Html.fromHtml(WEB_ROOT))));
        wv.loadUrl("http://appdesayunos.000webhostapp.com/comen.php");
        swipe.setRefreshing(true);
        wv.setWebViewClient(new WebViewClient(){

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                wv.loadUrl("file:///android_asset/error.html");

            }

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                swipe.setRefreshing(false);
            }

        });

    }



    @Override
    public void onBackPressed(){

        if (wv.canGoBack()){
            wv.goBack();
        }else {
            finish();
        }
    }



}
