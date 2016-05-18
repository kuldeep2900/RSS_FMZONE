package home.example.com.rss;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import tips.First_tip;
import tips.ServiceHandler;

public class Login extends Activity {


    Button button;
    EditText first_Name, last_Name, mobile_No, email, cmp_Name;
    private TextInputLayout inputLayoutName;

    Spinner category;
    String fname, lname, mob, Smail, cmpName, sp,imei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        button = (Button) findViewById(R.id.button1);

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        first_Name = (EditText) findViewById(R.id.editText1);
        first_Name.addTextChangedListener(new MyTextWatcher(first_Name));

        last_Name = (EditText) findViewById(R.id.editText2);
        email = (EditText) findViewById(R.id.editText3);
        mobile_No = (EditText) findViewById(R.id.editText4);
        cmp_Name = (EditText) findViewById(R.id.editText5);
        category = (Spinner) findViewById(R.id.sppiner1);

        List<String> categories = new ArrayList<String>();
        categories.add("--Select--");
        categories.add("General FM");
        categories.add("Technical FM");
        categories.add("Security");
        categories.add("Soft Services");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(dataAdapter);

        addClickListners();


    }

    public void addClickListners() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               /* if (first_Name.getText().toString().isEmpty() || last_Name.getText().toString().isEmpty() || cmp_Name.getText().toString().isEmpty() || email.getText().toString().isEmpty() || mobile_No.getText().toString().isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Please Enter the Currect Information", Toast.LENGTH_LONG).show();*/
                if (!validateName()) {
                    return;
                }






                else {
                    TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                    imei=telephonyManager.getDeviceId();


                    fname = first_Name.getText().toString();
                    lname = last_Name.getText().toString();
                    Smail = email.getText().toString();
                    mob = mobile_No.getText().toString();
                    cmpName = cmp_Name.getText().toString();
                    sp = category.getSelectedItem().toString();



                    new SendtoServerTip().execute();

                    first_Name.setText(null);
                 last_Name.setText(null);
                   email.setText(null);
                   mobile_No.setText(null);
                   cmp_Name.setText(null);
                  category.setSelected(false);

                    Intent intent = new Intent(Login.this, First_tip.class);
                    startActivity(intent);
                }


            }
        });

    }
    private boolean validateName() {
        if (first_Name.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(first_Name);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.editText1:
                    validateName();
                    break;
                /*case R.id.et_middle:
                    validateMiddle();
                    break;
                case R.id.et_last:
                    validateLast();
                    break;*/
            }
        }
    }

    class SendtoServerTip extends AsyncTask<String, Void, String> {


        ProgressDialog dlg;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           dlg = new ProgressDialog(Login.this);


           dlg.setMessage("Login Please Wait...");
            //  dlg.setIndeterminateDrawable(getResources().getDrawable(R.drawable.plus_tip));

            dlg.show();

        }


        @Override
        protected String doInBackground(String... params) {
            // tip_id=Integer.toString(l);

            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            nameValuePairList.add(new BasicNameValuePair("imei", imei));
            nameValuePairList.add(new BasicNameValuePair("f_name", fname));
            nameValuePairList.add(new BasicNameValuePair("l_name ", lname));
            nameValuePairList.add(new BasicNameValuePair("email", Smail));
            nameValuePairList.add(new BasicNameValuePair("mobile", mob));
            nameValuePairList.add(new BasicNameValuePair("c_name", cmpName));
            nameValuePairList.add(new BasicNameValuePair("category", sp));


            // edt1, edt4, edt2,edt3


            String result = new ServiceHandler().makeServiceCall("http://trinityapplab.in/RSS/registration_insert.php", 1, nameValuePairList);
            //  Log.d("result =", result.toString());

            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dlg.dismiss();

        }

    }
}
