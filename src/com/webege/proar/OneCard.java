package com.webege.proar;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.webege.proar.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class OneCard extends ActionBarActivity {

	private ProgressDialog pDialog;
	 

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //testing on Emulator: 
    private static final String READ_One_Card_URL = "http://proar.webege.com/one_card.php";
    private static final String DELETE_One_Card_URL = "http://proar.webege.com/delete.php";
    

  
	
  //JSON IDS:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_CARD_ID = "card_id";
    private static final String TAG_NAME = "name";
    private static final String TAG_POS = "pos";
    private static final String TAG_COMPANY = "company";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_EXT = "ext";
    private static final String TAG_FAX = "fax";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_TWITTER = "twitter";
    private static final String TAG_ADDRESS= "address";
    private static final String TAG_MESSAGE = "message";
    
	
    String card_ID;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.one_card);
		
		Intent intent = getIntent();
	    card_ID = intent.getStringExtra(ShowCards.CARD_ID_content);
	    new ReadOneCard().execute();
	}

	
	class ReadOneCard extends AsyncTask<String, String, String> {

		 /**
        * Before starting background thread Show Progress Dialog
        * */
		boolean failure = false;

       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           pDialog = new ProgressDialog(OneCard.this);
           pDialog.setMessage("Attempting...");
           pDialog.setIndeterminate(false);
           pDialog.setCancelable(true);
           pDialog.show();
       }

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			 // Check for success tag
           int success;
           String name,company,pos,phone,ext,fax,email,twitter;
           
           try {
               // Building Parameters
               List<NameValuePair> params = new ArrayList<NameValuePair>();
              params.add(new BasicNameValuePair("card_id", card_ID));


               Log.d("request!", "starting");
               // getting product details by making HTTP request
               JSONObject json = jsonParser.makeHttpRequest(
            		   READ_One_Card_URL, "POST", params);

               // check your log for json response
               Log.d("Read attempt", json.toString());

               // json success tag
               success = json.getInt(TAG_SUCCESS);
               if (success == 1) {
               	Log.d("card Successfuly loaded!", json.toString());
               	

               	name = json.getString(TAG_NAME);
               	company = json.getString(TAG_COMPANY);
               	pos = json.getString(TAG_POS);
               	phone = json.getString(TAG_PHONE);
               	ext = json.getString(TAG_EXT);
               	fax = json.getString(TAG_FAX);
               	email = json.getString(TAG_EMAIL);
               	twitter = json.getString(TAG_TWITTER);
               	
               	
                TextView textView = (TextView)findViewById(R.id.ocName);
                textView.setText(name);
                textView = (TextView)findViewById(R.id.ocCompany);
                textView.setText(company);
                textView = (TextView)findViewById(R.id.ocPos);
                textView.setText(pos);
                textView = (TextView)findViewById(R.id.ocPhone);
                textView.setText(phone);
                textView = (TextView)findViewById(R.id.ocExt);
                textView.setText(ext);
                textView = (TextView)findViewById(R.id.ocFax);
                textView.setText(fax);
                textView = (TextView)findViewById(R.id.ocEmail);
                textView.setText(email);
                textView = (TextView)findViewById(R.id.ocTwitter);
                textView.setText(twitter);
                
                
               	return json.getString(TAG_MESSAGE);
               }else{
               	Log.d("Load Failure!", json.getString(TAG_MESSAGE));
               	return json.getString(TAG_MESSAGE);

               }
           } catch (JSONException e) {
               e.printStackTrace();
           }

           return null;

		}
		/**
        * After completing background task Dismiss the progress dialog
        * **/
       protected void onPostExecute(String file_url) {
           // dismiss the dialog once product deleted
           pDialog.dismiss();
           if (file_url != null){
           	Toast.makeText(OneCard.this, file_url, Toast.LENGTH_LONG).show();
           }

       }

	}
	
	
	
	
	
	
	class DeleteOneCard extends AsyncTask<String, String, String> {

		 /**
       * Before starting background thread Show Progress Dialog
       * */
		boolean failure = false;

      @Override
      protected void onPreExecute() {
          super.onPreExecute();
          pDialog = new ProgressDialog(OneCard.this);
          pDialog.setMessage("Attempting...");
          pDialog.setIndeterminate(false);
          pDialog.setCancelable(true);
          pDialog.show();
      }

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			 // Check for success tag
          int success;

          
          try {
              // Building Parameters
              List<NameValuePair> params = new ArrayList<NameValuePair>();
             params.add(new BasicNameValuePair("card_id", card_ID));


              Log.d("request!", "starting");
              // getting product details by making HTTP request
              JSONObject json = jsonParser.makeHttpRequest(
            		 DELETE_One_Card_URL, "POST", params);

              // check your log for json response
              Log.d("Delete attempt", json.toString());

              // json success tag
              success = json.getInt(TAG_SUCCESS);
              if (success == 1) {
              	Log.d("card Successfuly deleted!", json.toString());
              	

              	finish();
              	
              	
  				
              	return json.getString(TAG_MESSAGE);
              }else{
              	Log.d("delete Failure!", json.getString(TAG_MESSAGE));
              	return json.getString(TAG_MESSAGE);

              }
          } catch (JSONException e) {
              e.printStackTrace();
          }

          return null;

		}
		/**
       * After completing background task Dismiss the progress dialog
       * **/
      protected void onPostExecute(String file_url) {
          // dismiss the dialog once product deleted
          pDialog.dismiss();
          if (file_url != null){
          	Toast.makeText(OneCard.this, file_url, Toast.LENGTH_LONG).show();
          }

      }

	}
	
	
	
public void DeleteCard(View view){
	new DeleteOneCard().execute();

	}
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.one_card, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
