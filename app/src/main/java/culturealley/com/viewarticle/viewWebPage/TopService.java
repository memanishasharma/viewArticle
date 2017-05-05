
package culturealley.com.viewarticle.viewWebPage;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import culturealley.com.viewarticle.R;

public class TopService extends Service {
 private String url;
    private WindowManager mWindowManager;
    private ImageView mImgView;

    private boolean mIsViewAttached = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        url = intent.getStringExtra("url");
        Log.d("Tag4","value"+url);
        if(!mIsViewAttached){
            mWindowManager.addView(mImgView, mImgView.getLayoutParams());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();


        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        mImgView = new ImageView(this);
        mImgView.setImageResource(R.drawable.app);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.BOTTOM | Gravity.CENTER;


        mWindowManager.addView(mImgView, params);

        mImgView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent1 = new Intent(TopService.this, MainActivity.class);
                intent1.putExtra("url",url);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                return false;
            }
        });

        mIsViewAttached = true;
    }

    public void removeView() {
        if (mImgView != null){
            mWindowManager.removeView(mImgView);
            mIsViewAttached = false;
        }
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        removeView();
    }
}
