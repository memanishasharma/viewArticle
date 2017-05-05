package culturealley.com.viewarticle.viewWebPage;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class StartServiceActivity extends AppCompatActivity {
    String sharedText = "";
    CountDownTimer cdt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        super.onCreate(savedInstanceState);
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                            Log.d("Tag0", "Value: " + sharedText);
                            Toast.makeText(getApplicationContext(), "Saved to Hello English", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.d("Tag1", "Value" + e);
                        }
                    }
                });
            }
        }
        if (sharedText != "") {

            cdt = new CountDownTimer(7000, 1000) {

                public void onTick(long millisUntilFinished) {
                    Log.i("Tag3", "Timer Start");
                    Intent srv = new Intent(StartServiceActivity.this, TopService.class);
                    srv.putExtra("url", sharedText);
                    startService(srv);

                }

                public void onFinish() {
                    Intent srv = new Intent(StartServiceActivity.this, TopService.class);
                    Log.i("TAG2", "Timer finished");
                    stopService(srv);
                   // finish();
                }
            };
            cdt.start();
            Log.i("ggg", "executing task");

        }
        if(sharedText=="") {
            Intent intent1 = new Intent(StartServiceActivity.this, MainActivity.class);
            startActivity(intent1);
            Log.d("Data","Vale"+sharedText);
           // intent.putExtra("url",sharedText);
        }finish();
    }

}