package home.example.com.rss;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;


public class ClickButton extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.click_button);


        ActionBar actionBar = getActionBar();


        //actionBar.setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();


        TextView text1 = (TextView) findViewById(R.id.txtview2);



        String content1 = getIntent().getStringExtra("contentss");


        text1.setText(content1);
    }

}