package home.example.com.rss;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import ServerConstant.ServerConstants;

public class DevotionalFragment extends Fragment {
	View view3;
	LinearLayout linear;
	JSONArray OuterContentjsonArray;
	ArrayList<String> sendtointentlist = new ArrayList<String>();
	String parshingresponce = null;
	static ArrayList<String> Submenu = new ArrayList<String>();
	private void initiateNewActivity() {
		parshingresponce=null;
		final ServerConstants serverConstants=new ServerConstants(getActivity());

		/*final ProgressDialog progressDialog = new ProgressDialog(
				getActivity());
		progressDialog.setMessage("wait  connect  to server...");
		progressDialog.setCancelable(false);
		progressDialog.show();*/
		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {

				super.handleMessage(msg);

				if (msg.what == 0) {
					parshingouter(parshingresponce);

				} else {

					Toast.makeText(getActivity(),
							"Network Error", Toast.LENGTH_SHORT)
							.show();
				}
				//progressDialog.dismiss();
			}

		};

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {



				parshingresponce=serverConstants.tab1parshingdevotio();



				Message msg = new Message();
				if (parshingresponce!=null)
				{
					msg.what = 0;
				}
				else
					msg.what = 1;
				handler.sendMessage(msg);

			}
		});
		th.start();
	}
	public void parshingouter(String response)
	{
		try
		{
			JSONObject jsonObjectouter=new JSONObject(response);
			if(jsonObjectouter.has("content"))
			{

				OuterContentjsonArray=jsonObjectouter.getJSONArray("content");

			}
			if(jsonObjectouter.has("SubMenu"))
			{Submenu=new ArrayList<String>();
				JSONArray jsonArrayouter=jsonObjectouter.getJSONArray("SubMenu");
				for(int m=0;m<jsonArrayouter.length();m++)
				{
					JSONObject jsonObjectouterlevel=jsonArrayouter.getJSONObject(m);
					Submenu.add(jsonObjectouterlevel.getString("label"));}
				for (int i = 1; i  <= Submenu.size(); i++) {
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
					Button btn = new Button(getActivity());
					btn.setId(i);
					final int id_ = btn.getId();
					btn.setText("button " + id_);
					btn.setBackground(getActivity().getResources().getDrawable(R.drawable.button_selector));
					linear.addView(btn, params);
					//params.setMargins(30, 20, 30, 0);
					params.setMargins(30, 50, 30, 0);
					btn  = ((Button)view3.findViewById(id_));
					btn .setText(Submenu.get(i-1));
					btn.setOnClickListener(new View.OnClickListener() {
						public void onClick(View view) {
							/*Toast.makeText(view.getContext(),
									"Button clicked index = " + id_, Toast.LENGTH_SHORT)
									.show();*/
							sendtointentlist=new ArrayList<String>();
							sendtointentlist=createJsonfortracking(OuterContentjsonArray,Submenu.get(id_-1),id_);
							Intent intent = new Intent(getActivity(), IntentClass.class);
							intent.putStringArrayListExtra("stock_list",sendtointentlist);
							startActivity(intent);
						}
					});
				}

				/*for(int j=1, k=0;j<Submenu.size();j++,k++)
				{
					JSONArray jsonArrayinner=jsonObjectouter.getJSONArray(Submenu.get(k));
					JSONObject jsonObjectinner=jsonArrayinner.getJSONObject(j);

				}*/

			}

		}catch(JSONException e)
		{

		}
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.tab1_layout, container, false);

		linear=(LinearLayout)rootView.findViewById(R.id.tab1);
         view3=rootView;
		initiateNewActivity();
		return rootView;

	}
/*
	@Override
	public void onResume() {
		super.onResume();

		// Tracking the screen view
		MyApplication.getInstance().trackScreenView("Devotional Fragment");
	}*/

	private ArrayList<String> createJsonfortracking( JSONArray jArray,String s,int i) {

		ArrayList<String> stringArrayList=new ArrayList<String>();
		try {


			if (jArray != null) {
				JSONObject jsonObjectintent= jArray.getJSONObject(i-1);
				if(jsonObjectintent.has(s)) {
					JSONArray jsonArrayinnerforintent=jsonObjectintent.getJSONArray(s);
					for (int j = 0;j < jsonArrayinnerforintent.length();j++) {
						JSONObject jsonArrayinnerforintent11 = jsonArrayinnerforintent.getJSONObject(j);
						stringArrayList.add(jsonArrayinnerforintent11.getString("contents"));
					}
				}
			}


		} catch (JSONException e) {
			e.printStackTrace();
		}


		return  stringArrayList;
	}

}





