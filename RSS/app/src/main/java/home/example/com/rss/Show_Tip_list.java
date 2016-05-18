package home.example.com.rss;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by ayush on 16/3/16.
 */
public class Show_Tip_list extends Activity {
    TextView text;
    public static int x=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_list);




        ActionBar actionBar = getActionBar();


        actionBar.setDisplayHomeAsUpEnabled(false);


        Intent intent = getIntent();

       text =(TextView)findViewById(R.id.tips_list_view);
        //TextView text1=(TextView)findViewById(R.id.txtview2);

      String tips = getIntent().getStringExtra("tipss_content");
     //   String content = getIntent().getStringExtra("contentss");




        text.setText(tips);
      //  text1.setText(content);


    }


}
