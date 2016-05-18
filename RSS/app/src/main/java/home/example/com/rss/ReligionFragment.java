package home.example.com.rss;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ServerConstant.ServerConstants;

public class ReligionFragment extends Fragment {

	View view3;
	LinearLayout linear;

	String parshingresponce=null;
	  ArrayList <String> Submenu=new ArrayList<String>();

	JSONArray OuterContentjsonArray;
 	ArrayList<String> sendtointentlist = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.tab1_layout, container, false);
  linear=(LinearLayout)rootView.findViewById(R.id.tab1);
		view3=rootView;
		Button	buttonid=(Button)view3.findViewById(R.id.buttonid);
		initiateNewActivity();
		return rootView;
}
/*
	@Override
	public void onResume() {
		super.onResume();

		// Tracking the screen view
		MyApplication.getInstance().trackScreenView("Religion Fragment");
	}*/
	private void initiateNewActivity() {
		parshingresponce=null;
	final 	ServerConstants  serverConstants=new ServerConstants(getActivity());

	/*	final ProgressDialog progressDialog = new ProgressDialog(
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

					Toast.makeText(getActivity() ,
							"Network Error", Toast.LENGTH_SHORT)
							.show();
				}
				//progressDialog.dismiss();
			}

		};

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {



				parshingresponce=serverConstants.tab1parshing();



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
	final	JSONObject jsonObjectouter=new JSONObject(response);


			if(jsonObjectouter.has("content"))
			{

				OuterContentjsonArray=jsonObjectouter.getJSONArray("content");

			}

			if(jsonObjectouter.has("SubMenu"))
			{
				Submenu=new ArrayList<String>();
				JSONArray jsonArrayouter=jsonObjectouter.getJSONArray("SubMenu");
				for(int m=0;m<jsonArrayouter.length();m++)
				{
					JSONObject jsonObjectouterlevel=jsonArrayouter.getJSONObject(m);
					Submenu.add(jsonObjectouterlevel.getString("label"));}
				for (  int i = 1; i  <= Submenu.size(); i++) {
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
					Button btn = new Button(getActivity());
					//final int id_ = btn.getId();

					btn.setId(i);
					 final int id_ = btn.getId();
				 btn.setText("button " + id_);
					btn.setBackground(getActivity().getResources().getDrawable(R.drawable.button_selector));
					linear.addView(btn, params);


					params.setMargins(30, 50, 30, 0);
					//	params.setMargins(30, 30, 30, 0);



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



			}

	}catch(JSONException e)
		{

		}
	}
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



