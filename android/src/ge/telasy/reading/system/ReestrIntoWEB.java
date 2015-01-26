package ge.telasy.reading.system;

import android.app.ListActivity;
import android.database.Cursor;
import android.util.Log;


// ჯერ არაა გამოყენებული   

//   რეესტრის მონაცემების ატვირთვა ვებში
public class ReestrIntoWEB  extends ListActivity  {
	 
	DB db;
	Cursor cursor; 
	final String LOG_TAG ="mylog";	
	
	
	@SuppressWarnings("deprecation")
  public   ReestrIntoWEB (){
		
		db = new DB(this);
		
        db.open();
        cursor = db.getAllData("1=1");                // kursoris Secvla pirobiT
        startManagingCursor(cursor);
        
        if (cursor.moveToFirst()) {
                   do {
                       Log.d(LOG_TAG,  " -----  კურსორი დატრიალდა     -----------");
                   } while (cursor.moveToNext());
 	     } else {
 	    	Log.d(LOG_TAG,  " -----  მონაცემთა ბაზა ცარიელია    -----------");
 	     }
	
	}
	
	
	
}
