package com.webege.proar;

import com.webege.proar.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Navi extends ActionBarActivity {

	
	
	public void openAddCard(View view){
		
		Intent intent = new Intent(this, AddCard.class);;
		startActivity(intent);
	}
	
	public void openQrcode(View view){
		
		Intent intent = new Intent(this, Qrcode.class);;
		startActivity(intent);
	}
	
	
	public void openShowCards(View view){
		
		Intent intent = new Intent(this, ShowCards.class);;
		startActivity(intent);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navi);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.navi, menu);
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
