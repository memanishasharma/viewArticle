package culturealley.com.viewarticle.viewWebPage;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by memanisha on 6/7/2016.
 */
public class JavaScriptInterface {

    private TextView contentView;
        Context mContext;

        /** Instantiate the interface and set the context */


        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }
