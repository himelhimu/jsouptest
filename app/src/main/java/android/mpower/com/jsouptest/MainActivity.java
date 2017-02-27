package android.mpower.com.jsouptest;

import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    public String url="http://192.168.19.21/agro/Seeds/view/106/zxzc/language:eng";
    public String bUrl="http://192.168.19.21/agro/Seeds/view/31/%E0%A6%AC_%E0%A6%9C_%E0%A6%86%E0%A6%B0%E0%A6%86%E0%A6%87_%E0%A6%A4_%E0%A6%B7_%E0%A6%AA_%E0%A6%9F_%E0%A7%AA_%E0%A6%93_%E0%A7%AD%E0%A7%A8/language:bn";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=(TextView) findViewById(R.id.tv);
        getWebSiteData(tv);
    }

    private void getWebSiteData(TextView tv) {
       new GetWebSiteTask().execute();
    }

   class GetWebSiteTask extends AsyncTask<Void,Void,String>{

       @Override
       protected String doInBackground(Void... params) {
           String linkText="";
           String div="";
           try {
               Document document= Jsoup.connect(bUrl).get();
               Element title=document.getElementById("blog");
               String t=title.text();
               linkText+=title;
               Log.i("full blog",linkText);
               Elements links = document.getElementsByTag("p");
               Elements masthead = document.select("div.col-md-12");
               Log.i("Head",masthead.toString());
               /*for (Element link : links) {
                  // String linkHref = link.attr("href");
                    linkText = link.text();
               }*/
               for (Element x:masthead){
                   String s=x.text();
                   div.concat(s);
                   Log.i("div",div);
               }
               Log.i("gg",linkText);
               return masthead.toString();
           } catch (IOException e) {
               e.printStackTrace();
           }
           return null;
       }

       @Override
       protected void onPostExecute(String s) {
           super.onPostExecute(s);

           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
               tv.setText(Html.fromHtml(s,Html.FROM_HTML_MODE_LEGACY));
           }else {
               tv.setText(Html.fromHtml(s));
           }
       }
   }
}
