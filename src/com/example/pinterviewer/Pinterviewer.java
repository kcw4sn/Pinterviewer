package com.example.pinterviewer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.pinterviewer.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources.NotFoundException;
import android.view.Menu;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class Pinterviewer extends Activity implements OnClickListener{

	private Button loginButton, createAccountButton;
	private EditText usernameField, passwordField;
	private TextView outputLabel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pinterviewer);

		loginButton = (Button) findViewById(R.id.loginButton);
		createAccountButton = (Button) findViewById(R.id.createAccountButton);
		loginButton.setOnClickListener(this);
		createAccountButton.setOnClickListener(this);

		usernameField = (EditText) findViewById(R.id.usernameField);
		passwordField = (EditText) findViewById(R.id.passwordField);
		outputLabel = (TextView) findViewById(R.id.outputLabel);


		//addListenerOnButtons();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pinterviewer, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.loginButton:
			usernameField.setText(usernameField.getText());
			passwordField.setText(passwordField.getText());
			Log.d("Web", "Login button click detected");	 
			new PostData().execute("");
			break;
		case R.id.createAccountButton:

			//new LongOperation().execute("");
			break;

		default:
			break;
		}
	}


	String successString;
	private class PostData extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			//ArrayList<NameValuePair> nvPairs = params[0];

			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			try {
				URI uri = new URI("http://plato.cs.virginia.edu/~cs4720f13yam/pinterviewWebApp/");
				HttpPost httppost = new HttpPost(uri);
				Log.d("Web", "HttpPost creation was successful");	





				try {
					// Add your data
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
					nameValuePairs.add(new BasicNameValuePair("username", ""+usernameField.getText()));
					nameValuePairs.add(new BasicNameValuePair("password", ""+passwordField.getText()));

					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					Log.d("Web", "HttpPost URL encoded entity: " + EntityUtils.toString(httppost.getEntity()));	
					Log.d("Web", "URL accessed: "+httppost.getURI());
					// Execute HTTP Post Request
					HttpResponse response = httpclient.execute(httppost);
					Log.d("Web", "HttpPost executed and HttpResponse retrieved successfully! ");	        

					HttpEntity entity = response.getEntity();
					InputStream input_stream = entity.getContent();
					Log.d("Web", "Input stream opened successfully");	 

					//For testing
					//InputStream input_stream = null;
					//JSONArray finalResult = new JSONArray();


					// Parse the response        
					BufferedReader reader = new BufferedReader(new InputStreamReader(input_stream, "UTF-8"));
					StringBuilder builder = new StringBuilder();
					for (String line = null; (line = reader.readLine()) != null;) {
						builder.append(line).append("\n");
					}
					JSONTokener tokener = new JSONTokener(builder.toString());
					JSONArray finalResult = new JSONArray(tokener);
					String response_str = finalResult.toString();

					Log.d("Web", "JSON response stringified: " + response_str);	
					successString = "Login was successful! JSON output was: " 
							+ response_str 
							+ ". For future implementation, this list will be displayed as links on a new page.";

				} catch (ClientProtocolException e) {
					Log.d("Web", "ClientProtocol exception thrown!");
					successString = "ERROR";
				} catch (IOException e) {
					Log.d("Web", "IO exception thrown!");
					successString = "ERROR";
				} catch (JSONException e) {
					Log.d("Web", "JSON exception thrown!");
					successString = "ERROR";
				} catch (NotFoundException e) {
					Log.d("Web", "NotFound exception thrown!");
					successString = "ERROR";
				} catch (Exception e) {
					Log.d("Web", "Generic exception thrown!");
					successString = "ERROR";
					e.printStackTrace();
				}
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				Log.d("Web", "HttpPost creation fialed: Improper URL!");
				e1.printStackTrace();
				successString = "ERROR";
			}

			return successString;
		}

		@Override
		protected void onPostExecute(String result) {
			TextView txt = (TextView) findViewById(R.id.outputLabel);
			txt.setText(result);
			// might want to change "executed" for the returned string passed
			// into onPostExecute() but that is upto you
		}

		@Override
		protected void onPreExecute() {}

		@Override
		protected void onProgressUpdate(Void... values) {}
	}

}
