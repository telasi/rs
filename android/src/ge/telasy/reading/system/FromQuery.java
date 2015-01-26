package ge.telasy.reading.system;

import ge.telasy.reading.system.R;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

public class FromQuery extends Activity {

	private static class Street {
		int id;
		String name;
		public int getId() {
	    return id;
    }
		public void setId(int id) {
	    this.id = id;
    }
		public String getName() {
	    return name;
    }
		public void setName(String name) {
	    this.name = name;
    }
		@Override
		public String toString() {
		  return name;
		}
	}
	
	
     final String LOG_TAG = "my_Admin_log"; 
	 DB db = new DB(this);
	 Cursor cursor;
	 Cursor cursor1;
    
	 String  stmnt;
	 String  streetname;
	 int streetID;
     int check;
	   
     String routenum;
	 String stmntTemp;
	 String Street_id;
	 String saxlis_num;
	 String korp_num;
	 String sadarb_num;
	 TextView tv_route_numb;
	   
    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extend);
        
        ManageMyApps.FromQuery = this;
        
        db = new DB(this);
        db.open();
        
        routenum =  C.ROUTE_NUMB;      
        tv_route_numb = (TextView)findViewById(R.id.spRoute);   
        tv_route_numb.setText(routenum);
        makeStreets4spiner(routenum);
        db.close();     
     
    }   
	
   
    
    
 //   áƒ áƒ”áƒ”áƒ¡áƒ¢áƒ áƒ˜áƒ¡ áƒ’áƒ�áƒ¤áƒ˜áƒšáƒ¢áƒ•áƒ áƒ˜áƒ¡áƒ�áƒ—áƒ•áƒ˜áƒ¡ áƒ¡áƒ�áƒ«áƒ˜áƒ”áƒ‘áƒ� áƒ¢áƒ”áƒ¥áƒ¡áƒ¢áƒ˜áƒ¡ áƒ¨áƒ”áƒ“áƒ’áƒ”áƒœáƒ�   
   public void MakeTextQuery(View v){
  	 
  	 TextView  t_House       = (TextView)findViewById(R.id.tvHouse);
  	 TextView  t_Bilding     = (TextView)findViewById(R.id.tvBilding);
  	 TextView  t_Forch       = (TextView)findViewById(R.id.tvForch);
  	 TextView  t_Flat        = (TextView)findViewById(R.id.tvFlat);
  	 
  	 TextView  t_accnumb        = (TextView)findViewById(R.id.accnumbID);
  	 TextView  t_mricxvnumb     = (TextView)findViewById(R.id.mricxvnumbID);
  	 
  	 CheckBox ReadNo = (CheckBox)findViewById(R.id.ReadNoID);  
  	 CheckBox ReadNormal = (CheckBox)findViewById(R.id.ReadNormalID);
  	 CheckBox ReadBruni = (CheckBox)findViewById(R.id.ReadBruniID);
  	 CheckBox ReadUmricxvelo = (CheckBox)findViewById(R.id.ReadUmricxID);
  	 CheckBox ReadMinXarji = (CheckBox)findViewById(R.id.ReadMinXarjiID);
  	 CheckBox ReadMaxXarji = (CheckBox)findViewById(R.id.ReadMaxXarjiID);
  	 CheckBox ReadNulChveneba  = (CheckBox)findViewById(R.id.ReadNulChvenebaID);
  	 
    	 
  	 stmnt = "";
  	 stmntTemp = routenum;
  	 if(!stmntTemp.isEmpty()){
  		 stmnt = DB.COLUMN_MARSH_NUM + " = " + stmntTemp;
  	 }
  	 
  	 if (!streetname.isEmpty()){
    		 if (stmnt.isEmpty()) {
  		          stmnt =  DB.COLUMN_STREET_ID + " =  "  + streetID;  	 
    		 }  else {
    			      stmnt = stmnt + " and " + DB.COLUMN_STREET_ID + " =  "  + streetID;  
  			      
  	  	 }
  	 };
  	 

  	 
  	 stmntTemp = "";
  	 stmntTemp = t_House.getText().toString();
  	 if (!stmntTemp.isEmpty()){
  		  if (stmnt.isEmpty()) {
	         stmnt = DB.COLUMN_SAXLIS_NUM  + " =  \"%"  + stmntTemp + "%\"";  	 
   		  }  else {
			     stmnt = stmnt + " and " + DB.COLUMN_SAXLIS_NUM + " like  \"%"  + stmntTemp + "%\"";  
      	}
  	 }
  	 
  	 stmntTemp = "";
  	 stmntTemp = t_Bilding.getText().toString(); 
  	 if (!stmntTemp.isEmpty()){
  		  if (stmnt.isEmpty()) {
	         stmnt = DB.COLUMN_KORP_NUM  + " =  \""  + stmntTemp + "\'";  	 
   		  }  else {
			     stmnt = stmnt + " and " + DB.COLUMN_KORP_NUM + " =  \""  + stmntTemp + "\"";  
      	}
  	 }
  	 
 	
  	 stmntTemp = "";
  	 stmntTemp = t_Forch.getText().toString(); 
  	 if (!stmntTemp.isEmpty()){
  		  if (stmnt.isEmpty()) {
	         stmnt = DB.COLUMN_SADARB_NUM  + " =  \""  + stmntTemp + "\"";  	 
   		  }  else {
			     stmnt = stmnt + " and " + DB.COLUMN_SADARB_NUM + " =  \""  + stmntTemp + "\"";  
      	}
  	 }
  	 

  	 stmntTemp = "";
  	 stmntTemp = t_Flat.getText().toString(); 
  	 if (!stmntTemp.isEmpty()){
  		  if (stmnt.isEmpty()) {
	         stmnt = DB.COLUMN_ADDESS  + " LIKE \"%"  +  stmntTemp  + "%\"";  	      
   		  }  else {
			     stmnt = stmnt + " and " + DB.COLUMN_ADDESS  + " LIKE \"%"  +  stmntTemp  + "%\"";  
      	}
  	 }
  	
  	 check = 0;
  	 
  	 if(ReadNo.isChecked()) {
   		  if (stmnt.isEmpty()) {
 	          stmnt = DB.COLUMN_READING_STATYS  + " = " + 0;  	      
    		  }  else {
 			      stmnt = stmnt + " and " + DB.COLUMN_READING_STATYS  + " = " + 0;
        	}
  		  check = 1; 
  	 }
  	 
  	 
  	 
  	 

	 if(ReadUmricxvelo.isChecked()) {
		  if (stmnt.isEmpty()) {
          stmnt = DB.COLUMN_READING_STATYS  + " = " + 5;  	      
		  }  else {
		  	      if (check == 1) {
		  	      	   stmnt = stmnt + " OR " + DB.COLUMN_READING_STATYS  + " = " + 5;
		  	      } else {
		                   stmnt = stmnt + " AND " + DB.COLUMN_READING_STATYS  + " = " + 5;
		  	           }
   	}
		  check = 1;
   }	 
  	 
	 
  if(ReadNulChveneba.isChecked()) {
	   if (stmnt.isEmpty()) {
         stmnt = DB.COLUMN_READING_STATYS  + " = " + 6;  	      
		   }  else {
		  	      if (check == 1) {
		  	      	   stmnt = stmnt + " OR " + DB.COLUMN_READING_STATYS  + " = " + 6;
		  	      } else {
		                   stmnt = stmnt + " AND " + DB.COLUMN_READING_STATYS  + " = " + 6;
		  	           }
  	}
	  check = 1;
   }		  
	 
  	 if(ReadBruni.isChecked()) {
 		  if (stmnt.isEmpty()) {
	          stmnt = DB.COLUMN_READING_STATYS  + " = " + 4;  	      
  		  }  else {
  		  	      if (check == 1) {
  		  	      	   stmnt = stmnt + " OR " + DB.COLUMN_READING_STATYS  + " = " + 4;
  		  	      } else {
			                   stmnt = stmnt + " AND " + DB.COLUMN_READING_STATYS  + " = " + 4;
  		  	           }
     	}
 		  check = 1;
	   }	 
  	 
	 if(ReadMaxXarji.isChecked()) {
		  if (stmnt.isEmpty()) {
          stmnt = DB.COLUMN_READING_STATYS  + " = " + 3;  	      
		  }  else {
		  	      if (check == 1) {
		  	      	   stmnt = stmnt + " OR " + DB.COLUMN_READING_STATYS  + " = " + 3;
		  	      } else {
		                   stmnt = stmnt + " AND " + DB.COLUMN_READING_STATYS  + " = " + 3;
		  	           }
   	}
		  check = 1;
   }		
  	 
  	 
  	 if(ReadMinXarji.isChecked()) {
  		  if (stmnt.isEmpty()) {
 	          stmnt = DB.COLUMN_READING_STATYS  + " = " + 2;  	      
   		  }  else {
   		  	      if (check == 1) {
   		  	      	   stmnt = stmnt + " OR " + DB.COLUMN_READING_STATYS  + " = " + 2;
   		  	      } else {
 			                   stmnt = stmnt + " AND " + DB.COLUMN_READING_STATYS  + " = " + 2;
   		  	           }
      	}
  		  check = 1;
 	   }		 
  
  	 if(ReadNormal.isChecked()) {
 		  if (stmnt.isEmpty()) {
	               stmnt = DB.COLUMN_READING_STATYS  + " = " + 1;  	      
  		  }  else {
  		  	      if (check == 1) {
  		  	      	   stmnt = stmnt + " OR " + DB.COLUMN_READING_STATYS  + " = " + 1;
  		  	      } else {
			                   stmnt = stmnt + " AND " + DB.COLUMN_READING_STATYS  + " = " + 1;
  		  	           }
     	}
 		  check = 1;
	   }	 
  	 
  	 stmntTemp = "";
  	 stmntTemp = t_mricxvnumb.getText().toString(); 
  	 if (!stmntTemp.isEmpty()){
	         stmnt = DB.COLUMN_MRICXVNUMB  + " =  \""  + stmntTemp  + "\"";   	  
      	 }
  	 
 	 stmntTemp = "";
  	 stmntTemp = t_accnumb.getText().toString(); 
  	 if (!stmntTemp.isEmpty()){
       stmnt = DB.COLUMN_ACCNUMB  + " =  \""  + stmntTemp + "\"";  	 
  	 } 
  	 
  	 
 // 	 Toast.makeText(getBaseContext(), stmnt , Toast.LENGTH_SHORT).show();
  	 
  	 
  	 Bundle bundle = new Bundle();  
  	 
  	 
   	 
  	 bundle.putString("WHERE", stmnt );	
  	   
  	  
  	  
  	 Intent mIntent = new Intent();  
     mIntent.putExtras(bundle);  
     setResult(RESULT_OK, mIntent);  
     
  	 finish();
  	 

   }
    
    
   
  public void QueryCancel(View v){
  	
  	 
 	 Bundle bundle = new Bundle();  
 	 
 	 
 	 bundle.putString("WHERE",  DB.COLUMN_MARSH_NUM   + "="  +   C.ROUTE_NUMB );	
 	   
 	  
 	  
   Intent mIntent = new Intent();  
   mIntent.putExtras(bundle);  
   setResult(RESULT_OK, mIntent);  
  	
   finish();
  }
   
 
  
// ----------------------------------  
  
 @SuppressWarnings("deprecation")
public void makeStreets4spiner(String routenum){
	 
 List<Street> streets = new ArrayList<Street>();
   
   
 Street street = new Street();
 street.setId(-1);       
 street.setName("");
 streets.add(street);
 



 cursor =db.getStreets(routenum);
 startManagingCursor(cursor);                       
 
 
 if (cursor.moveToFirst()) {
            do {
                street = new Street();
                street.setId( cursor.getInt(1));             //  mere ganvazogado
                street.setName(cursor.getString(2));
                streets.add(street);
            } while (cursor.moveToNext());
 } else {
	Log.d(LOG_TAG,  " -----  áƒ›áƒ�áƒœáƒ�áƒªáƒ”áƒ›áƒ—áƒ� áƒ‘áƒ�áƒ–áƒ� áƒªáƒ�áƒ áƒ˜áƒ”áƒšáƒ˜áƒ�    -----------");
 }
 
 
final  Street[] data = streets.toArray(new Street[0]);


 
 
 // Ð°Ð´Ð°Ð¿Ñ‚ÐµÑ€
 ArrayAdapter<Street> adapter = new ArrayAdapter<Street>(this, android.R.layout.simple_spinner_item, data);
 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
 Spinner spinner = (Spinner) findViewById(R.id.spinner);
 spinner.setAdapter(adapter);

 cursor.close();
 stopManagingCursor(cursor);
	 
	
 
 
 spinner.setPrompt("áƒ¥áƒ£áƒ©áƒ”áƒ‘áƒ˜");
 // Ð²Ñ‹Ð´ÐµÐ»Ñ�ÐµÐ¼ Ñ�Ð»ÐµÐ¼ÐµÐ½Ñ‚ 
 spinner.setSelection(0);
 
 spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
   @Override
   public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

   	Street s = data[position];
   	streetname = s.getName();
   	streetID   = s.getId();
   }
   @Override
   public void onNothingSelected(AdapterView<?> arg0) {
   }
 });
	 
 }
 
 @Override
 public void onBackPressed() {
 	// TODO Auto-generated method stub
 	// super.onBackPressed();  es dakomentarebuli unda iyos
	  
 }
 
 
	
}


