package com.example.pinterviewer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.pinterviewer.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class Pinterviewer extends Activity implements OnClickListener{

	private Button loginButton, createAccountButton;
	private EditText usernameField, passwordField;
	
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

				postData();
			break;
		case R.id.createAccountButton:

				postData();
			break;

		default:
			break;
		}
	}
	
	
	//Adapted from http://www.androidsnippets.com/executing-a-http-post-request-with-httpclient
	public void postData() {
	    // Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
		//HttpURLConnection httpclient = null; //new DefaultHttpURLConnection();
	    HttpPost httppost = new HttpPost("http://plato.cs.virginia.edu/~cs4720f13yam/pinterviewWebApp/");

	    try {
	    	// Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("username", ""+usernameField.getText()));
	        nameValuePairs.add(new BasicNameValuePair("password", ""+passwordField.getText()));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        //Log.d("Web", ""+ nameValuePairs.toString());

	        // Execute HTTP Post Request
	        HttpResponse response_json = httpclient.execute(httppost);
	        
Log.d("Web", "GOT TO THE STATEMENT");	 


/*BufferedReader rd = new BufferedReader(new InputStreamReader(response_json.getEntity().getContent()));

String line = "";
while ((line = rd.readLine()) != null) {
  System.out.println(line);
  if (line.startsWith("Auth=")) {
    String key = line.substring(5);
    // do something with the key
  }*/







	        /*// Parse the response        
	        BufferedReader reader = new BufferedReader(new InputStreamReader(response_json.getEntity().getContent(), "UTF-8"));
	        StringBuilder builder = new StringBuilder();
	        for (String line = null; (line = reader.readLine()) != null;) {
	            builder.append(line).append("\n");
	        }
	        JSONTokener tokener = new JSONTokener(builder.toString());
	        JSONArray finalResult = new JSONArray(tokener);
	        String response_str = finalResult.toString();
	        System.out.println(response_str);
	        
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    } catch (JSONException e) {*/
	    	// TODO Auto-generated catch block
	    } catch (Exception e) {
	    	
	    }
	} 

}
