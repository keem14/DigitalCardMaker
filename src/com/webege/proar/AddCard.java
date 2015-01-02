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
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddCard extends ActionBarActivity implements OnClickListener{

	public final static String CARD_ID_content = "com.webege.proar.card_id";
	private EditText name, pos, company, address, phone, 
	ext, fax, email, twitter, message;
	
	private Button  mSubmit, mReset;
	
	 // Progress Dialog
    private ProgressDialog pDialog;
 
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    
    
    private static final String ADD_CARD_URL = "http://proar.webege.com/addcard.php";
    

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_LASTID = "lastID";
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_card);

		
		name = (EditText)findViewById(R.id.name);
		pos = (EditText)findViewById(R.id.pos);
		company = (EditText)findViewById(R.id.company);
		address = (EditText)findViewById(R.id.address);
		phone = (EditText)findViewById(R.id.phone);
		ext = (EditText)findViewById(R.id.ext);
		fax = (EditText)findViewById(R.id.fax);
		email = (EditText)findViewById(R.id.email);
		twitter = (EditText)findViewById(R.id.twitter);


		
		
		mSubmit = (Button)findViewById(R.id.submit);
		mSubmit.setOnClickListener(this);
		
		mReset = (Button)findViewById(R.id.reset);
		mReset.setOnClickListener(this);
		
		
	}
	
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit:
			 new CreatCard().execute();
			break;
		case R.id.reset:{
		     name.getText().clear();
		     pos.getText().clear();
		     company.getText().clear();
		     address.getText().clear();
		     phone.getText().clear();
		     ext.getText().clear();
		     fax.getText().clear();
		     email.getText().clear();
		     twitter.getText().clear();}
			
			break;

		default:
			break;
		}
		
		
		
				
	}
	
	
	class CreatCard extends AsyncTask<String, String, String> {
		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddCard.this);
            pDialog.setMessage("Creating Card...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
		
		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			 // Check for success tag
            int success;
            
            
            String post_name = name.getText().toString();
            String post_pos = pos.getText().toString();
            String post_company = company.getText().toString();
            String post_address = address.getText().toString();
            String post_phone = phone.getText().toString();
            String post_ext = ext.getText().toString();
            String post_fax = fax.getText().toString();
            String post_email = email.getText().toString();
            String post_twitter = twitter.getText().toString();

            
          //Retrieving Saved Username Data:

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(AddCard.this);
            String post_user_id = sp.getString("user", "");
            
            try {
            	
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("user_id", post_user_id));
                params.add(new BasicNameValuePair("name", post_name));
                params.add(new BasicNameValuePair("pos", post_pos));
                params.add(new BasicNameValuePair("company", post_company));
                params.add(new BasicNameValuePair("address", post_address));
                params.add(new BasicNameValuePair("phone", post_phone));
                params.add(new BasicNameValuePair("ext", post_ext));
                params.add(new BasicNameValuePair("fax", post_fax));
                params.add(new BasicNameValuePair("email", post_email));
                params.add(new BasicNameValuePair("twitter", post_twitter));
               
                
                
                Log.d("request!", "starting");
                
                //Posting user data to script 
                JSONObject json = jsonParser.makeHttpRequest(
                		ADD_CARD_URL, "POST", params);
 
                // full json response
                Log.d("Creat Card attempt", json.toString());
 
                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                	Log.d("Card Added!", json.toString());   
                	String card_id = json.getString(TAG_LASTID);
                	System.out.println(card_id);
                	
                	
                	
                	
                	Intent intent = new Intent(AddCard.this, Qrcode.class);
                	intent.putExtra(CARD_ID_content, card_id);
                	startActivity(intent);
                	
                	finish();
                	return json.getString(TAG_MESSAGE);
                }else{
                	Log.d("Creating Failure!", json.getString(TAG_MESSAGE));
                	return json.getString(TAG_MESSAGE);
                
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
			
		}
		
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
            	Toast.makeText(AddCard.this, file_url, Toast.LENGTH_LONG).show();
            }
 
        }
		
	}
	

	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_card, menu);
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
