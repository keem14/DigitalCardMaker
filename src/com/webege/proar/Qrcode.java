package com.webege.proar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import com.webege.proar.R;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class Qrcode extends ActionBarActivity {

	 Button load_img;
	  ImageView img;
	  Bitmap bitmap;
	  ProgressDialog pDialog;
	  String card_ID;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qrcode);
		
	    Intent intent = getIntent();
	    card_ID = intent.getStringExtra(AddCard.CARD_ID_content);
	    
		    img = (ImageView)findViewById(R.id.qrimage);
		    new LoadImage().execute("http://chart.apis.google.com/chart?chs=200x200&cht=qr&chl=http://proar.webege.com/bsnscrd_a.php?card_id=" + card_ID);
		    
		
	}

	
	  private class LoadImage extends AsyncTask<String, String, Bitmap> {
		    @Override
		        protected void onPreExecute() {
		            super.onPreExecute();
		            pDialog = new ProgressDialog(Qrcode.this);
		            pDialog.setMessage("Loading QR code ....");
		            pDialog.show();
		    }
		       protected Bitmap doInBackground(String... args) {
		         try {
		               bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());
		        } catch (Exception e) {
		              e.printStackTrace();
		        }
		      return bitmap;
		       }
		       protected void onPostExecute(Bitmap image) {
		         if(image != null){
		           img.setImageBitmap(image);
		           pDialog.dismiss();
		         }else{
		           pDialog.dismiss();
		           Toast.makeText(Qrcode.this, "Network Error", Toast.LENGTH_SHORT).show();
		         }
		       }
		   }
  
	  
	    void saveMyImage() {
	      
	        File filename;
	        try {
	            String path1 = android.os.Environment.getExternalStorageDirectory()
	                    .toString();
	            Log.i("in save()", "after mkdir");
	            File file = new File(path1 + "/" + "card_maker");
	            if (!file.exists())
	                file.mkdirs();
	            filename = new File(file.getAbsolutePath() + "/QRcode" + card_ID + ".jpg");
	            Log.i("in save()", "after file");
	            FileOutputStream out = new FileOutputStream(filename);
	            Log.i("in save()", "after outputstream");
	            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
	            out.flush();
	            out.close();
	            Log.i("in save()", "after outputstream closed");
	            
	            ContentValues image = getImageContent(filename);
	            Uri result = getContentResolver().insert(
	                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, image);
	            Toast.makeText(getApplicationContext(),
	                    "File is Saved in  " + filename, Toast.LENGTH_SHORT).show();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	    }

	    public ContentValues getImageContent(File parent) {
	        ContentValues image = new ContentValues();
	        image.put(Images.Media.TITLE, "card_maker");
	        image.put(Images.Media.DISPLAY_NAME, "QR code");
	        image.put(Images.Media.DESCRIPTION, "App Image");
	        image.put(Images.Media.DATE_ADDED, System.currentTimeMillis());
	        image.put(Images.Media.MIME_TYPE, "image/jpg");
	        image.put(Images.Media.ORIENTATION, 0);
	        image.put(Images.ImageColumns.BUCKET_ID, parent.toString()
	                .toLowerCase().hashCode());
	        image.put(Images.ImageColumns.BUCKET_DISPLAY_NAME, parent.getName()
	                .toLowerCase());
	        image.put(Images.Media.SIZE, parent.length());
	        image.put(Images.Media.DATA, parent.getAbsolutePath());
	        return image;
	    }
	  
	  
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.qrcode, menu);
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


