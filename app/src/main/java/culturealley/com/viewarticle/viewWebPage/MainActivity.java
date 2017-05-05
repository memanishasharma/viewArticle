package culturealley.com.viewarticle.viewWebPage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import java.io.IOException;
import java.util.ArrayList;

import culturealley.com.viewarticle.R;

public class MainActivity extends Activity {
    private Database database;
    String sharedURL="";
    String sharedTitle="";
    ProgressDialog mProgressDialog;
    ArrayList<String> results = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapter ;
    //final ListView listView=(ListView)findViewById(R.id.listView);

    ListView listView;
    String  itemValue;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        //Log.d("check0","value"+sharedText);
        database = new Database(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent1 = getIntent();

        try {
            String Url = intent1.getStringExtra("url");
            String Title = intent1.getStringExtra("title");
            Log.d("Tag44","Value"+Url);
            Log.d("Tag123","Value"+Title);
           if(Url==null) {
               sharedURL="";
           }
            else
               sharedURL=Url;
              sharedTitle=Title;

        } catch (Exception e) {
                Log.d("Tag4","Value"+e);
        }

        //Log.d("check1","value"+sharedText);
        if(sharedURL!="") {
            Log.i("Tag5","executing task");
            new Description().execute();
        }
        results = database.viewFromDatabase();

        for (String addurl : results){
            Log.i("urldata: ", addurl);
        }

        //showing data in list view

        listView=(ListView)findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,results);
        listView.setAdapter( arrayAdapter );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item value
                itemValue    = (String) listView.getItemAtPosition(position);
                Log.d("itemvalue","value"+itemValue);

                Intent intent = new Intent(MainActivity.this, ArticleView.class);
                intent.putExtra("html",itemValue);

                //intent.putExtra("url",sharedText);
                startActivity(intent);

                // Show Alert
                // Toast.makeText(getApplicationContext(), "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG).show();
            }
        });
        Log.d("jjjjj","ggggggg"+itemValue);




        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                removeItemFromList(position);

                return true;
            }
        });
    }
    // method to remove list item

    protected void removeItemFromList(int position) {
        final int deletePosition = position;
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub
                // main code on after clicking yes
                itemValue = (String) listView.getItemAtPosition(deletePosition);
                results.remove(deletePosition);
                arrayAdapter.notifyDataSetChanged();
                arrayAdapter.notifyDataSetInvalidated();
                Log.d("ffff","ggggg"+itemValue);
               database.DeleteData(itemValue);

            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();

    }




    private class Description extends AsyncTask<Void, Void, Void> {

        String text = null;
        String title1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

                try {
                    if(sharedURL!="")
                    { mProgressDialog = new ProgressDialog(MainActivity.this);
                    mProgressDialog.setTitle("Fetching Body Tag Data");
                    mProgressDialog.setMessage("Loading...");
                    mProgressDialog.setIndeterminate(false);
                   mProgressDialog.show();
                        }
                }
                catch (Exception e) {
                    Log.d("ERR0", "Value" + e);
                }
        }



        @Override
        protected Void doInBackground(Void... params) {

            try {

                Document document = Jsoup.connect(sharedURL).get();
                title1 = document.title();

                // remove links to external style-sheets
                try {
                    document.head().getElementsByTag("link").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);
                }
                try{
                    document.head().getElementsByTag("script").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                try{
                    document.head().getElementsByTag("noscript").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                try{
                    document.body().getElementsByTag("script").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                try{
                    document.body().getElementsByTag("script").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                try{
                    document.body().getElementsByTag("footer").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                try{
                    document.body().getElementsByTag("style").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                /*try{
                    document.body().getElementsByTag("span").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }

                try{
                    document.select("span").remove();

                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }*/
                try{
                    document.body().getElementsByTag("img").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                try{
                    document.body().getElementsByTag("font").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                try{
                    document.body().getElementsByTag("header").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                try{
                    document.head().getElementsByTag("style").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                try{
                    document.body().getElementsByAttributeValueContaining("class", "header");
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                try{
                    document.body().getElementsByAttributeValueContaining("class", "header").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                try{
                    document.body().getElementsByAttributeValueContaining("id","style").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }


                try{
                    document.body().getElementsByAttributeValueContaining("span","style").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                try{
                    document.body().getElementsByAttributeValueContaining("class", "footer").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                try{
                    document.body().getElementsByAttributeValueContaining("class", "navigation").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                try{
                    document.body().getElementsByAttributeValueContaining("class", "Facebook").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                try{
                    document.body().getElementsByAttributeValueContaining("class", "sidebar").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }

                try{
                    document.body().getElementsByAttributeValueContaining("class", "Social").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                try{
                    document.body().getElementsByAttributeValueContaining("class", "twitter").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                try{
                    document.body().getElementsByAttributeValueContaining("class", "Google").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                try{
                    document.body().getElementsByAttributeValueContaining("class", "Share").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                try{
                    document.body().getElementsByAttributeValueContaining("class", "comment").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                try{
                    document.body().getElementsByAttributeValueContaining("class", "pinterest").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }
                try{
                    document.body().getElementsByAttributeValueContaining("class", "home").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }

                try{
                    document.body().getElementsByAttributeValueContaining("class", "resource").remove();
                }
                catch(IllegalArgumentException e)
                {
                    Log.d("SSSS","Valur"+e);

                }



                for( Element element : document.select("form") )
                {
                    Log.d("POS8","VALUE"+element);
                    element.remove();
                }
                //Adding Custom Style Sheet
                document.head().appendElement("link").attr("rel", "stylesheet").attr("type", "text/css").attr("href", "Custom.css");

                //Adding Styling to title
                document.select("body").first().children().first().before("<div id=title>"+title1+"</div><hr>"+"<div id=url>"+sharedURL+"</div>");

                //Taking all html page content as string and storing it in text variable
                text = document.outerHtml();

                Log.d("Todo","Value"+text);


                Whitelist wl = Whitelist.simpleText();
                wl.addTags("div", "span"); // add additional tags here as necessary
                String clean = Jsoup.clean(text, wl);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(text== null||text== "") {
                Toast.makeText(getApplicationContext(), "There Was an error downloading the article view for this page.Make sure you are connected to the internet", Toast.LENGTH_SHORT).show();
            }
            //if(text != null||text != "")
            int Flag=database.addToDatabase(sharedURL,sharedTitle,text);
            results = database.viewFromDatabase();
            arrayAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,results);
            // final ListView listView=(ListView)findViewById(R.id.listView);
            listView.setAdapter( arrayAdapter );
            if(Flag==0)
                Toast.makeText(getApplicationContext(), "Link is already in your list", Toast.LENGTH_LONG).show();

            mProgressDialog.dismiss();

        }
    }
}



