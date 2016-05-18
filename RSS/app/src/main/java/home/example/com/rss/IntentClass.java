package home.example.com.rss;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ServerConstant.ServerConstants;
import adapter.ListViewAdapter;

/**
 * Created by ayush on 12/5/16.
 */
public class IntentClass extends Activity {
    ListView listView ;

    ListViewAdapter listviewadapter;
    CustomAdapter adapter;
   //public  IntentClass CustomListView = null;

    public  ArrayList<String> stringArrayList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intentlist);
        listView = (ListView) findViewById(R.id.regList);

        Resources res =getResources();

        //This should be getIntent();
        stringArrayList = new ArrayList<String>();
        stringArrayList= getIntent().getStringArrayListExtra("stock_list");
        if(stringArrayList.size()>0) {
ServerConstants serverConstants=new ServerConstants(IntentClass.this);
            listviewadapter =new ListViewAdapter(IntentClass.this,stringArrayList);
            listView.setAdapter(listviewadapter);
serverConstants.setListViewHeightBasedOnChildren(listView);
        }else
        {

        }

    }
}
