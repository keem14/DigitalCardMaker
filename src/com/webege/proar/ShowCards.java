package com.webege.proar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.webege.proar.R;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ShowCards extends ListActivity {

	public final static String CARD_ID_content = "com.webege.proar.card_id";
	// Progress Dialog
	private ProgressDialog pDialog;
 
	//php read comments script
    
    //localhost :  
    //testing on your device
    //put your local ip instead,  on windows, run CMD > ipconfig
    //or in mac's terminal type ifconfig and look for the ip under en0 or en1
   // private static final String READ_COMMENTS_URL = "http://xxx.xxx.x.x:1234/webservice/comments.php";
    
    //testing on Emulator:
    private static final String READ_Cards_URL = "http://proar.webege.com/cards.php";
    
  //testing from a real server:
    //private static final String READ_COMMENTS_URL = "http://www.mybringback.com/webservice/comments.php";
  
	
  //JSON IDS:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_CARD_ID = "card_id";
    private static final String TAG_NAME = "name";
    private static final String TAG_POS = "pos";
    private static final String TAG_COMPANY = "company";

    private static final String TAG_POSTS = "posts";

    


    //it's important to note that the message is both in the parent branch of 
    //our JSON tree that displays a "Post Available" or a "No Post Available" message,
    //and there is also a message for each individual post, listed under the "posts"
    //category, that displays what the user typed as their message.
    

   //An array of all of our comments
    private JSONArray mComments = null;
    //manages all of our comments in a list.
    private ArrayList<HashMap<String, String>> mCommentList;

	
	
	
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	//loading the comments via AsyncTask
    	new LoadCards().execute();
    }

	public void addCard(View v)
    {
        Intent i = new Intent(this, AddCard.class);
        startActivity(i);
    }

    /**
     * Retrieves json data of comments
     */
    public void updateJSONdata() {
        // Instantiate the arraylist to contain all the JSON data.
     	// we are going to use a bunch of key-value pairs, referring
     	// to the json element name, and the content, for example,
     	// message it the tag, and "I'm awesome" as the content..
     	
    	
    	
    	
         mCommentList = new ArrayList<HashMap<String, String>>();
         

         
         
         

         JSONParser jParser = new JSONParser();

    	 SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ShowCards.this);
         String post_user_id = sp.getString("user", "");
    	 
         List<NameValuePair> params = new ArrayList<NameValuePair>();
         params.add(new BasicNameValuePair("u_id", post_user_id));
        

         Log.d("request!", "starting");

         //Posting user data to script
         JSONObject json = jParser.makeHttpRequest(
        		 READ_Cards_URL, "POST", params);
         
         
         
         
         
         //when parsing JSON stuff, we should probably
         //try to catch any exceptions:
         try {
             
    

    
         	//I know I said we would check if "Posts were Avail." (success==1)
         	//before we tried to read the individual posts, but I lied...
         	//mComments will tell us how many "posts" or comments are
         	//available
             mComments = json.getJSONArray("posts");

             // looping through all posts according to the json object returned
             for (int i = 0; i < mComments.length(); i++) {
                 JSONObject c = mComments.getJSONObject(i);

                 
                 //gets the content of each tag
                 String card_id = c.getString(TAG_CARD_ID);
                 String name = c.getString(TAG_NAME);
                 String pos = c.getString(TAG_POS);
                 String company = c.getString(TAG_COMPANY);


                 

                 // creating new HashMap
                 HashMap<String, String> map = new HashMap<String, String>();
                 map.put(TAG_CARD_ID, card_id);
                 map.put(TAG_NAME, name);
                 map.put(TAG_POS, pos);
                 map.put(TAG_COMPANY, company);

                 

              
                 // adding HashList to ArrayList
                 mCommentList.add(map);
                 
               
             }

         } catch (JSONException e) {
             e.printStackTrace();
         }
     }

    

    /**
     * Inserts the parsed data into our listview
     */
    private void updateList() {

    	
    	
		ListAdapter adapter = new SimpleAdapter(this, mCommentList,
				R.layout.single_post, new String[] { TAG_CARD_ID, TAG_NAME, TAG_COMPANY, TAG_POS}, 
													new int[] { R.id.tvCardId, R.id.tvName,R.id.tvCompany,R.id.tvPos});

		// I shouldn't have to comment on this one:
		setListAdapter(adapter);
		
		// Optional: when the user clicks a list item we 
		//could do something.  However, we will choose
		//to do nothing...
		ListView lv = getListView();	
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// This method is triggered if an item is click within our
				// list. For our example we won't be using this, but
				// it is useful to know in real life applications.
				
				
				TextView tv = (TextView)view.findViewById(R.id.tvCardId);
				String crdId = tv.getText().toString();
				
				Log.d("card id", crdId);		
				Intent intent = new Intent(ShowCards.this, OneCard.class);
            	intent.putExtra(CARD_ID_content, crdId);
            	startActivity(intent);
				
				
				
			}
		});
    	
    	
    	
    }

    
    
    
    
    
    
    
    
    public class LoadCards extends AsyncTask<Void, Void, Boolean> {

    	@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ShowCards.this);
			pDialog.setMessage("Loading Cards...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
        @Override
        protected Boolean doInBackground(Void... arg0) {
        	//we will develop this method in version 2
            updateJSONdata();
            return null;

        }


        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pDialog.dismiss();
          //we will develop this method in version 2
            updateList();
        }
    }

	
	
	
	
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_cards);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_cards, menu);
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

