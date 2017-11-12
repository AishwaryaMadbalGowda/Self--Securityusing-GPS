import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import pragma.embd.android.helpmemain.CheckFirstTimeUser;
import pragma.embd.android.helpmemain.DatabaseHelper;
import pragma.embd.android.helpmemain.MainScreenActivity;
import pragma.embd.android.helpmemain.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.BaseColumns;
import android.provider.Settings;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class AlertEmergencyContactsActivity extends Activity {
	
	TextView tv_cancel_alert;
	ImageButton btn_cancel_alert;
	
	TimerTask scanTask;
	final Handler handler = new Handler();
	Timer t=new Timer();
	
	LocationManager mlocManager;
	LocationListener mlocListener ;

	double c_lat;
	double c_long;
	String address;
	
	DatabaseHelper helper;
	private SQLiteDatabase database;
	Cursor cursor;
	
	String str_Phone_nos=":";
	String[] phone_nos=new String[5];
	
	
	
	private static final String fields[] = {"phoneno", BaseColumns._ID };
	
	
	@SuppressLint("NewApi") @Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_ACTION_BAR); 
		 setContentView(R.layout.alertinfocancelscreen);
		 
		
		 
		 tv_cancel_alert=(TextView)findViewById(R.id.tv_cancel_alert);
		 btn_cancel_alert=(ImageButton)findViewById(R.id.btn_cancel_alert);
	
		 
		
		 CheckFirstTimeUser.count_cancel_emergency_alarm=1;
		
		/*  android.support.v7.app.ActionBar bar = getSupportActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
			bar.setTitle("Stop Alerting");*/
		  
		
		 doWifiScan();

		 
		 tv_cancel_alert.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					alert_box();
					
				}
			});
		 
		 btn_cancel_alert.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				alert_box();
				
			}
		});
		 
	
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main_actions, menu);


		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		switch (item.getItemId()) {
		
		case R.id.action_exit:
			if(CheckFirstTimeUser.count_cancel_emergency_alarm==1){
				Toast.makeText(getApplicationContext(),"Please stop alarm to Navigate to other Screens. Thank You." , Toast.LENGTH_SHORT).show();
				}
				else if(CheckFirstTimeUser.count_cancel_emergency_alarm==0){
		AppExit();
				}
			return true;
	
		
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void doWifiScan(){

        scanTask = new TimerTask() {
                @Override
				public void run() {
                        handler.post(new Runnable() {
                                @Override
								public void run() {
                                	try
                                	{
                                		
                                	//	get_lat_long_details();	
                                		get_lat_long_details();
                                		
    	                             Log.d("TIMER", "Timer set off");
                                	}
                                	catch (Exception ex)
                                	{
                                		
                                	}
                                }
                       });
                }};


            t.schedule(scanTask, 0, 100000); 

         }
	
	public void stopScan(){

        if(scanTask!=null){
   
           scanTask.cancel();
      }

     }
	 void alert_box(){
	   	 AlertDialog.Builder builder = new AlertDialog.Builder(this);
	   	 builder.setTitle("PLEASE CONFIRM");
	   	 builder.setIcon(R.drawable.infoicon);
	        builder.setMessage("Are You Sure to Cancel the Alert?")
	        .setCancelable(false)
	        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
	            @Override
				public void onClick(DialogInterface dialog, int id) {
	           stopScan();
	           
	           Toast.makeText(getApplicationContext(), "Alert Cancelled Successfully", Toast.LENGTH_SHORT).show();
	           CheckFirstTimeUser.count_cancel_emergency_alarm=0;
	           finish();
	           Intent i=new Intent(getApplicationContext(),MainScreenActivity.class);
	           i.putExtra("ID", "2");
	           startActivity(i);
	            }
	        })
	       
Login screen
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("NewApi")
public class LoginScreenActivity extends Activity {

	EditText et_username, et_pwd;
	Button btn_register, btn_login;
	
	DatabaseHelper helper;
	SQLiteDatabase database;
	Cursor cursor;

	private static final String fields[] = {"_ID", "username", "pwd"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		et_username = (EditText) findViewById(R.id.et_username);
		et_pwd = (EditText) findViewById(R.id.et_pwd);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_register = (Button) findViewById(R.id.btn_register);
		
		
		helper = new DatabaseHelper(getApplicationContext());

		et_username.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				et_username.setHint("");

			}
		});

		et_username.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				if (hasFocus) {

					et_username.setHint("Enter Name");
				}

			}
		});

		et_pwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et_pwd.setHint("");

			}
		});

		et_pwd.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				if (hasFocus) {

					et_pwd.setHint("Enter Password");
				}

			}
		});

		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (et_username.getText().toString().trim().length() == 0) {
					et_username.setError("Please Enter UserName");
				}

				else if (et_pwd.getText().toString().trim().length() == 0) {
					et_pwd.setError("Please Enter Password");
				}
				
				else{
					
					 try{
					 
database = helper.getReadableDatabase();
String selection = "username=? AND pwd=?";
String[] selectionArgs = {et_username.getText().toString().trim(), et_pwd.getText().toString().trim()};
 cursor = database.query(DatabaseHelper.Login_details_TABLE_NAME, fields, selection, selectionArgs, null, null,null);
  cursor.moveToFirst();
  if(cursor.getCount()>0){
				     /*   et_ID.setText(cursor.getString(cursor.getColumnIndex("_ID")));
					        et_username.setText(cursor.getString(cursor.getColumnIndex("username")));
					        et_password.setText(cursor.getString(cursor.getColumnIndex("pwd")));
					      
					      */
					        		  	
					        Intent MainScreen = new Intent(getApplicationContext(),MainScreenActivity.class);
						/*	MainScreen.putExtra("username",  cursor.getString(cursor.getColumnIndex("username")));
							MainScreen.putExtra("Id",  cursor.getString(cursor.getColumnIndex("_id")));*/
					        startActivity(MainScreen);
					        
 } 
 Else
{
						      Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
}
 }
 catch(Exception e)
{
Toast.makeText(getApplicationContext(), "error msg" + e.getMessage(), Toast.LENGTH_SHORT).show();
}
}
/*else if (et_username.getText().toString().trim().equalsIgnoreCase("a")
&& et_pwd.getText().toString().trim()
equalsIgnoreCase("a")) 
{
Toast.makeText(getApplicationContext(), "Login Success",
Toast.LENGTH_LONG).show();
Intent main = new Intent(getApplicationContext(),
MainScreenActivity.class);
startActivity(main);
}*/

}
});
btn_register.setOnClickListener(new OnClickListener() {
@Override
public void onClick(View v) {
// TODO Auto-generated method stub
Intent registerScreen = new Intent(getApplicationContext(), RegistrationScreenActivity.class);
startActivity(registerScreen);
}
});
}
@Override
protected void onStop() 
{
// TODO Auto-generated method stub
super.onStop();
finish();
}
@Override
public void onBackPressed() {
// TODO Auto-generated method stub
super.onBackPressed();
finish();
}
}
