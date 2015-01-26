package ge.telasi.reading.system;

import ge.telasy.reading.system.R;
import telasi.android.reading.model.User;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class login extends Activity {

	private static final String TAG = "myLogs";
	
	static final String r_admin = "Admin";
	static final String r_admin_pin = "7777";

	static String r_login = null;
	static String r_pin_code = null;
	public static String r_inspect_id = null; 
	static int    is_reester_down = 0; 
	static int    connect_ok = 0; 
	
	String where_clause= null;
	
	static String route_numb= null;
	
	String login_intent;
	String pin_code_intent;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
        
		ManageMyApps.login = this;

	}

	public void b_calcel_click(View v) {
		// გამოსვლა
		finish();
	}

	public void b_ok_click(View v) {
	
		  // იდ და უსერნამეს წაკითხვა
		  TextView t_login = (TextView) findViewById(R.id.login);
		  login_intent = t_login.getText().toString();
		  TextView t_pin_code = (TextView) findViewById(R.id.pin_code);	
		  pin_code_intent = t_pin_code.getText().toString();
		
		  DB db;
		  Cursor cursor;
		 	
		  db = new DB(this);
		  db.open();
		  cursor = db.getUsers(null);

		  // კურსორიდან მონაცემის წაკითხვა და ცვლადებზე მინჭება
			cursor.moveToFirst();
			while (cursor.isAfterLast() == false){
    				 r_login      =  cursor.getString(1);
		   			 r_pin_code   =  cursor.getString(2);
			    	 r_inspect_id =  cursor.getString(3);

			    	 if(login_intent.equals(r_login) && pin_code_intent.equals(r_pin_code)){
			    		   //  სისტემაში არა ადმინის პაროლით შესვლა წარმატებულია
			    		   connect_ok  = 1;
			    		   cursor.close();
			    		   // დავადგინოთ მისი როუტების მდგომარეობა
			    		   Cursor InspectRoutesCount;
			    		   InspectRoutesCount = db.getHF( DB.COLUMN_INSP_ID + "=" + r_inspect_id + " and  " + DB.COLUMN_R_STATUSI + " != 3" ) ;
			    		   if (InspectRoutesCount.getCount() > 0 ){
			    		  	     //  ამ ინსპექტორზე რეესტრები/რესტრი ჩამოტვირთულია
			    		  	     //  ახლა დავადგინოთ აქვს თუ არა მიმდინარე რეესტრი (Main) - ანუ 5 იანი სტატუსის რეესტრი.
			    		  	     InspectRoutesCount.close();
			    		  	     is_reester_down = 1;
			    		  	     InspectRoutesCount = db.getHF(DB.COLUMN_INSP_ID + "=" +r_inspect_id + " and  " + DB.COLUMN_R_STATUSI + " = 5" ) ;
			    		  	     if (InspectRoutesCount.getCount() > 0){
			    		  		         //  აქვს აქტიური რეესტრი ამიტომ გადავიდეთ მასზე 
			    		  		         InspectRoutesCount.moveToFirst();
			    		  		         is_reester_down = 2;
			    		  		         //როუტის  დადგენა და შესაბამის რეესტრზე გადასვლა 
			    		  		         route_numb = InspectRoutesCount.getString(InspectRoutesCount.getColumnIndex(DB.COLUMN_R_MARSH_NUM));    
			    		  	     }
			    		   }
			    		   
			    		   InspectRoutesCount.close();
			    		   
					  	   break;
  		       }
				     cursor.moveToNext();
			}
			
			// ---------------------------------------------------------
   		cursor.close();   
   		db.close();

   		if (connect_ok == 1) { 
            //    სისტემაში შესვლი მცდელობა არა ადმინის პაროლით წარმატებით დამთავრდა
            t_login.setText("");
            t_pin_code.setText("");
            //
            Bundle bundle = new Bundle();
	        bundle.putString("user", r_login);
	        bundle.putString("ins_kode", r_inspect_id);
            //
	        C.INSPECTOR_ID = Integer.parseInt(r_inspect_id);
			C.USER  = r_login ;
			C.PSWD  = r_pin_code;
            C.ROUTE_NUMB = route_numb;
	    //
	    switch(is_reester_down) {
            case 1: // აქტიური რეესტრი არ ააქვს 
  	                // რეესტრების მართვაზე გადასვლა
                {
                	MyMessages msg = new MyMessages();
                	msg.getMyToast(this, R.string.mainreestriaragaqvt,  android.R.drawable.ic_dialog_alert, Toast.LENGTH_LONG);
              		Intent ii = new Intent(getApplicationContext(), routemanager.class);
              		startActivity(ii);
                }
  		          break;
  	        case 2:    //  აქტიური რეესტრი აქვს 
  	        	       //  მასზე გადასვლა 
  	              {
  	                //Toast.makeText(this, "აქტიურ რეესტრზე გადასვლა", Toast.LENGTH_LONG).show();
 			   		Intent ii = new Intent(getApplicationContext(), TARSAktivity.class);  // ფილტრი დგება  ACTIVITY-ში
 			   		ii.putExtras(bundle);
	    	  		startActivity(ii);
  	              }
  		            break;
  	        case 0: 
   	                // არააქვს რესტრები 
  	        	    //  გადასვლა რეესტრის ჩამოტვირთვაზე
  	              {
  	               	MyMessages msg = new MyMessages();
                	msg.getMyToast(this, R.string.noreesterdownloads,  android.R.drawable.ic_dialog_alert, Toast.LENGTH_LONG);
  	        	    Intent ii = new Intent(getApplicationContext(), routedownload.class);
				    ii.putExtras(bundle);
				    startActivity(ii);
  	              }  
  	                break;
       }
   		}
		  else {
		        // სისტემაში შესვლის მცდელობა წარუმატებელია
		        // შევამოწმო ადმიის პაროლიტ ხომ არ შედიოდა
      		  if ( login_intent.equals(r_admin) && pin_code_intent.equals(r_admin_pin)) {
		           // შევიდა ადმინისტრატორის პროგრამით შეეძლოს მონაცემების
		           // ატვირთვა გამოტვირთვა
		           t_login.setText("");
		           t_pin_code.setText("");
		           
		    	     Bundle bundle = new Bundle();
		    	     bundle.putString("user", r_admin);
		    	     bundle.putString("ins_kode", "0000");
      		     
		    	     //  ეს სამი ხაზი აქ ზედმეტია  მერე ადმინის მხარეს რომ მივხედავ მაშინ მოვაცილებ 
		    	     C.INSPECTOR_ID = Integer.parseInt("5383");
							 C.USER  = "gela" ;
							 C.PSWD  = "gela";
		    	     
		    	     
		    	     Intent i = new Intent(getApplicationContext(), AdminlayoutActivity.class);
      		     i.putExtras(bundle);
		           startActivity(i);
		        }
		        else{
    		         t_login.setText("");
	  	             t_pin_code.setText("");
	               	 MyMessages msg = new MyMessages();
               	     msg.getMyToast(this, R.string.incorectpassword,  android.R.drawable.ic_lock_idle_lock, Toast.LENGTH_LONG);
		        }
		  }
	    }
	
	public void onLoginComplete(User user, String password) {
		// TODO:
		
		// ეს გათიშულია არსად არ მუშაობს რაღაც პრობლემა შემექმნა ტრანზაქციულობასთან დაკავშირებით
		
		// როდესაც ინტერნეტიდან მივიღებ დასტურს მომხმარებლის არსებობის შესახებ 
		// მის მონაცემებს ვინახავ  მნაცემთა ბაზაში.
	  //Toast.makeText(this, "internetiT Sesvla WarmatebiT damtavrda /N unda gadavides insertze", Toast.LENGTH_LONG).show();
		 DB db;
	 	 Cursor cursor;
	 	 
		db = new DB(this);
		db.open();
		cursor = db.getUsers(null);
  		db.addRecIntoUsers(login_intent, pin_code_intent, null);
   		cursor.close();
   		db.close();
		 
 	    Intent ii = new Intent(getApplicationContext(), routedownload.class);
		startActivity(ii);
		
	}


	
}
