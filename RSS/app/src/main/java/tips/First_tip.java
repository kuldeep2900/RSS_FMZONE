package tips;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.Constants;
import home.example.com.rss.MainActivity;
import home.example.com.rss.R;
import tips.SimpleGestureFilter.SimpleGestureListener;

public class First_tip extends Activity implements SimpleGestureListener {

    static int i = 1,l=0;
    static String ty="1";

    private tips.SimpleGestureFilter detector;

    Spinner spcat, spSubcat;
    ImageButton addtip;
    TextView tipfirst, currenttrending;
    RatingBar ratingbar;
    Button button,click_button;
    String tip_id,newTip;

   private String rating,remarks_detail;
    EditText remarksDetail,edit_text;

  static ArrayList<String> list ;



    public String JSON_URL = "http://www.trinityapplab.in/RSS/tips.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_list);
         addListenerOnButtonClick();


        currenttrending=(TextView)findViewById(R.id.tips);
        list= new ArrayList<String>();
        tipfirst = (TextView) findViewById(R.id.tips_list_view);
        remarksDetail=(EditText)findViewById(R.id.remarks_edit);
        addtip=(ImageButton)findViewById(R.id.imageButton);
        edit_text=(EditText)findViewById(R.id.edit_text);
        click_button=(Button)findViewById(R.id.click_button1);
        edit_text.setVisibility(View.GONE);
        click_button.setVisibility(View.GONE);


        spcat=(Spinner)findViewById(R.id.spinner_category);
        spSubcat=(Spinner)findViewById(R.id.spinner_subcategory);
        spcat.setVisibility(View.GONE);
        spSubcat.setVisibility(View.GONE);



        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        if(Constants.checkNetworkStatus(First_tip.this))
        {
            new LongOperation().execute(JSON_URL);
        }
        else
        {
            Toast.makeText(First_tip.this,"Network Error",Toast.LENGTH_LONG).show();
        }

        detector = new tips.SimpleGestureFilter(this, this);


        addTipsListner();
        HideAddListener();

    }





public void    addTipsListner(){

        addtip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                tipfirst.setVisibility(View.GONE);
                        remarksDetail.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
                ratingbar.setVisibility(View.GONE);
                addtip.setVisibility(View.GONE);
                currenttrending.setVisibility(View.GONE);
                edit_text.setVisibility(View.VISIBLE);
                click_button.setVisibility(View.VISIBLE);
                spcat.setVisibility(View.VISIBLE);
                spSubcat.setVisibility(View.VISIBLE);

            }
        });

    }

    public  void HideAddListener(){

        click_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tipfirst.setVisibility(View.VISIBLE);
                remarksDetail.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                ratingbar.setVisibility(View.VISIBLE);
                addtip.setVisibility(View.VISIBLE);
                currenttrending.setVisibility(View.VISIBLE);
                edit_text.setVisibility(View.GONE);
                click_button.setVisibility(View.GONE);

                spcat.setVisibility(View.GONE);
                spSubcat.setVisibility(View.GONE);



                newTip=edit_text.getText().toString();
                new SendtoServerTip().execute();
                edit_text.setText(null);

            }
        });


    }


    // for Rating
    public void addListenerOnButtonClick() {


        ratingbar = (RatingBar) findViewById(R.id.ratingBar1);

        button = (Button) findViewById(R.id.button123);
        //Performing action on Button Click


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if(remarksDetail.getText().toString().isEmpty()) {
                    Toast.makeText(First_tip.this, "Please give Remarks", Toast.LENGTH_LONG).show();
                }
                else
                {

                    tip_id = "1";
                    rating = String.valueOf(ratingbar.getRating());
                    remarks_detail = remarksDetail.getText().toString();
                    if (Constants.checkNetworkStatus(First_tip.this)) {
                        new SendtoServer().execute();
                    } else {
                        Toast.makeText(First_tip.this, "Network Error", Toast.LENGTH_LONG).show();
                    }


                    remarksDetail.setText(null);
                    ratingbar.setRating(0);
                }
                }


        });
    }


    private class LongOperation extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String tips_content;

        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(First_tip.this);


        protected void onPreExecute() {

          Dialog.setMessage("Current Trending...");

            Dialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.plus_tip));

            Dialog.show();
        }


        protected Void doInBackground(String... urls) {
            try {

                HttpGet httpget = new HttpGet(urls[0]);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                tips_content = Client.execute(httpget, responseHandler);
                try {
                    JSONArray jsonArray = new JSONArray(tips_content);
                            for (int j= 0; j < jsonArray.length(); j++) {
                            JSONObject jsnobject=jsonArray.getJSONObject(j);
                           list.add(jsnobject.getString("tips_content"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (ClientProtocolException e) {
                Error = e.getMessage();
                cancel(true);
            } catch (IOException e) {
                Error = e.getMessage();
                cancel(true);
            }

            return null;
        }


        protected void onPostExecute(Void unused) {
            Dialog.dismiss();
            if (Error != null) {
            } else {
                if(list.size()>0) {
                    i=0;
                    tipfirst.setVisibility(View.VISIBLE);
                    tipfirst.setText(list.get(0));
                    l=0;
                }
                }
        }
    }


                @Override
                public boolean dispatchTouchEvent(MotionEvent me) {
                    // Call onTouchEvent of SimpleGestureFilter class
                    this.detector.onTouchEvent(me);
                    return super.dispatchTouchEvent(me);
                }

                @Override
                public void onSwipe(int direction) {

                    switch (direction) {

                      case tips.SimpleGestureFilter.SWIPE_RIGHT :

                          Intent intent =new Intent(First_tip.this, MainActivity.class);
                          startActivity(intent);

                 break;
               case tips.SimpleGestureFilter.SWIPE_LEFT :

                   Intent intent1 =new Intent(First_tip.this, MainActivity.class);
                   startActivity(intent1);

                  break;
            case tips.SimpleGestureFilter.SWIPE_DOWN:

if(list.size()>0)
{
if(l==0)
{
    l=0;
    tipfirst.setText(list.get(l));
}
    else
{
    if(l>0&&l<(list.size()))
    {
        l--;
        tipfirst.setText(list.get(l));
    }
    else
    {
        if (l ==  list.size() ) {
            l = l--;
            tipfirst.setText(list.get(l));
        } else {
            l=0;
            tipfirst.setText(list.get(l));
        }


    }
}
}
else
{
    Toast.makeText(First_tip.this,"Network Error",Toast.LENGTH_LONG).show();
}

break;



            case tips.SimpleGestureFilter.SWIPE_UP:
                if(list.size()>0)
                {
                    if(l==(list.size()-1))
                    {
                        l=list.size()-1;
                        tipfirst.setText(list.get(l));
                    }
                    else
                    {
                        if(l>0&&l<(list.size()))
                        {
                            l++;
                            tipfirst.setText(list.get(l));
                        }
                        else {
                            if (l == 0) {
                                l = 1;
                                tipfirst.setText(list.get(l));
                            } else {
                                l = list.size() - 1;
                                tipfirst.setText(list.get(l));
                            }
                        }
                    }
                }
                else
                {
                    Toast.makeText(First_tip.this,"Network Error",Toast.LENGTH_LONG).show();
                }
                break;





        }
    }



    @Override
    public void onDoubleTap() {
       // Toast.makeText(this, "You tapped double", Toast.LENGTH_SHORT).show();
Intent intent =new Intent(First_tip.this,MainActivity.class);
        startActivity(intent);
    }



    class SendtoServer extends AsyncTask<String, Void, String> {


        ProgressDialog dlg;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlg = new ProgressDialog(First_tip.this);
            dlg.setMessage("Saving...");
            dlg.show();

        }


        @Override
        protected String doInBackground(String... params) {
             tip_id=Integer.toString(l);

            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            nameValuePairList.add(new BasicNameValuePair("tip_id", tip_id));
            nameValuePairList.add(new BasicNameValuePair("remark", remarks_detail));
            nameValuePairList.add(new BasicNameValuePair("rating", rating));


            // edt1, edt4, edt2,edt3


            String result = new ServiceHandler().makeServiceCall("http://www.trinityapplab.in/RSS/rating.php", 1, nameValuePairList);
        //  Log.d("result =", result.toString());

            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dlg.dismiss();

        }


    }

    class SendtoServerTip extends AsyncTask<String, Void, String> {


        ProgressDialog dlg;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlg = new ProgressDialog(First_tip.this);
          // dlg.setMessage("Adding Tip...");
          //  dlg.setIndeterminateDrawable(getResources().getDrawable(R.drawable.plus_tip));



            dlg.show();

        }


        @Override
        protected String doInBackground(String... params) {
           // tip_id=Integer.toString(l);

            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            nameValuePairList.add(new BasicNameValuePair("F5", newTip));



            // edt1, edt4, edt2,edt3


            String result = new ServiceHandler().makeServiceCall("http://trinityapplab.in/slump/registration.php", 1, nameValuePairList);
            //  Log.d("result =", result.toString());

            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dlg.dismiss();

        }


    }
    private void confirmDialog(Context context){

        final AlertDialog alert = new AlertDialog.Builder(
                new ContextThemeWrapper(context,android.R.style.Theme_Dialog))
                .create();
        alert.setTitle("Alert");
        alert.setMessage("Do you want to exit ?");
        alert.setIcon(R.drawable.oie);
        alert.setCancelable(false);
        alert.setCanceledOnTouchOutside(false);

        alert.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        alert.dismiss();

                        finish();

                    }
                });

        alert.setButton(DialogInterface.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        alert.dismiss();

                    }
                });

        alert.show();
    }

    @Override
    public void onBackPressed() {



    cancleTip();


    confirmDialog(First_tip.this);

    }
    public void cancleTip(){


        tipfirst.setVisibility(View.VISIBLE);
        remarksDetail.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        ratingbar.setVisibility(View.VISIBLE);
        addtip.setVisibility(View.VISIBLE);
        currenttrending.setVisibility(View.VISIBLE);
        edit_text.setVisibility(View.GONE);
        click_button.setVisibility(View.GONE);
        spcat.setVisibility(View.GONE);
        spSubcat.setVisibility(View.GONE);
    }

}



