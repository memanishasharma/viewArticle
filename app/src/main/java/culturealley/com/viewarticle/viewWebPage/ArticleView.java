package culturealley.com.viewarticle.viewWebPage;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import culturealley.com.viewarticle.R;

public class ArticleView extends AppCompatActivity {
    private Database database;
    String html;
    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view);
        Intent intent = getIntent();
        html = intent.getStringExtra("html");

        database = new Database(this);
        Log.d("TAGe","Value"+html);


        text= database.ViewHtmlData(html);
        Log.d("text","value"+text);

        WebView webview = new WebView(ArticleView.this);
        setContentView(webview);
        webview.loadDataWithBaseURL("file:///android_asset/.", text, "text/html", "UTF-8", null);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topbar,menu);
        return super.onCreateOptionsMenu(menu);

    }

    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.action_webview:
                // search action
                //Intent srv = new Intent(this, BackMusicService.class);
                //stopService(srv);
                WebView webview = new WebView(ArticleView.this);
                setContentView(webview);
                if(isNetworkStatusAvialable (getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "internet available", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "internet is not available", Toast.LENGTH_SHORT).show();

                }

                Log.d("ddee","value"+html);
                webview.loadUrl(html);
                return true;
            case R.id.action_articleview:

                WebView webview1 = new WebView(ArticleView.this);
                setContentView(webview1);
                webview1.loadDataWithBaseURL("file:///android_asset/.", text, "text/html", "UTF-8", null);


                //webview1.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                webview1.setScrollContainer(false);
                Intent srv = new Intent(this,TopService.class);
                stopService(srv);


            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public static boolean isNetworkStatusAvialable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
                if(netInfos.isConnected())
                    return true;
        }
        return false;
    }
}
