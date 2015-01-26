package ge.telasi.reading.system;

import ge.telasy.reading.system.R;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class outOfReester extends ListActivity {
	
	DB db;
	Cursor cursor;
	Cursor cur_mricxvnumb;
	Cursor spcursor;
	String where; 
	ListAdapter scAdapter;
	final String LOG_TAG = "my_Admin_log"; 
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.out_of_reester);

		ManageMyApps.outOfReester = this;
		
		 db = new DB(this);
     db.open();
     
     where = "";
     fillData(where);

	}
	

	@SuppressWarnings("deprecation")
  public void back_to_admin (View v){
  		cursor.close();
      stopManagingCursor(cursor);
	  	finish();
	}
	
	
	
	@SuppressWarnings("deprecation")
  public void SaveRow(View v){
		// áƒ’áƒ�áƒ�áƒœáƒ�áƒšáƒ˜áƒ–áƒ“áƒ”áƒ¡ áƒ�áƒ áƒ¡áƒ”áƒ‘áƒ�áƒ‘áƒ¡ áƒ—áƒ£ áƒ�áƒ áƒ� áƒ�áƒ¡áƒ”áƒ—áƒ˜ áƒ©áƒ�áƒœáƒ�áƒ¬áƒ”áƒ áƒ˜ áƒ£áƒ™áƒ•áƒ” áƒ“áƒ� áƒ—áƒ£ áƒ�áƒ áƒ¡áƒ”áƒ‘áƒ�áƒ‘áƒ¡ 
		// áƒ“áƒ�áƒ�áƒ‘áƒ“áƒ”áƒ˜áƒ¢áƒ“áƒ”áƒ¡, áƒ¢áƒ£ áƒ�áƒ  áƒ�áƒ áƒ¡áƒ”áƒ‘áƒ�áƒ‘áƒ¡ áƒ“áƒ�áƒ”áƒ›áƒ�áƒ¢áƒ�áƒ¡
		// áƒ¡áƒ¢áƒ áƒ˜áƒ¥áƒ�áƒœáƒ˜áƒ¡ áƒ¬áƒ�áƒ¨áƒšáƒ� áƒ®áƒ�áƒœáƒ’áƒ áƒ«áƒšáƒ˜áƒ• áƒ“áƒ�áƒ­áƒ”áƒ áƒ�áƒ–áƒ” áƒ“áƒ�áƒžáƒ áƒ�áƒ’áƒ áƒ�áƒ›áƒ“áƒ”áƒ¡
		
		 TextView  t_mricxv;
		 String mricxvnumb;
		 double chveneba;
		 String street;
		 String saxli;
		 String korpusi;
		 String sadarbazo;
		 String note;
		 
		
		 t_mricxv       = (TextView)findViewById(R.id.mricxvnumID1);
		 mricxvnumb = t_mricxv.getText().toString();
		 if (!mricxvnumb.isEmpty()){

			   t_mricxv       = (TextView)findViewById(R.id.axalichvID1);
		     chveneba = Double.parseDouble(t_mricxv.getText().toString());
		 
		     t_mricxv       = (TextView)findViewById(R.id.StreetID1);
		     street = t_mricxv.getText().toString();
		 
		     t_mricxv       = (TextView)findViewById(R.id.HouseID1);
		     saxli = t_mricxv.getText().toString();
		 
		     t_mricxv       = (TextView)findViewById(R.id.bildingID1);
		     korpusi = t_mricxv.getText().toString();
		 
		     t_mricxv       = (TextView)findViewById(R.id.forchID1);
		     sadarbazo = t_mricxv.getText().toString();
		 
		     t_mricxv       = (TextView)findViewById(R.id.noteID1);
		     note = t_mricxv.getText().toString();
		 
		     cur_mricxvnumb = db.getOutOfreester(DB.COLUMN_MRICXVNUMB + " = " + mricxvnumb );  
		     if(cur_mricxvnumb.getCount() == 0) {
		     
		     db.addRecInOutOfRange(
				    mricxvnumb,  
				    chveneba,
				    street, 
				    saxli, 
				    korpusi, 
				    sadarbazo,
				    note
         );
		     } else {
		    	 db.updateOutOfReester(
		    			 mricxvnumb, 
		    			 chveneba,
		    			 street,
		    			 saxli,
		    			 korpusi,
		    			 sadarbazo,
		    			 note                  
               ) ;
		     }
		     cur_mricxvnumb.close();
      	 cursor.requery();
      	 ClearRow(null);  
		 }
	}
	
	
	@SuppressWarnings("deprecation")
  public void fillData(String where){
     	cursor = db.getOutOfreester(where);      
      startManagingCursor(cursor);
       
      String[] from = new String[] { "mricxvnumb", "axalichveneba", "street_name", "saxlis_num", "korp_num", "sadarb_num", "bill_note"};
      int[] to = new int[] { R.id.mricxvnumID, R.id.axalichvID, R.id.StreetID, R.id.HouseID, R.id.bildingID, R.id.forchID,  R.id.noteID};
       
       scAdapter = new SimpleCursorAdapter(this,  R.layout.out_of_reester_row, cursor, from, to );
       
       setListAdapter(scAdapter);
       
       ListView lv = getListView();
       lv.setOnItemLongClickListener(new OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	        // TODO Auto-generated method stub
//        	 Toast.makeText(outOfReester.this, "Item in position " + arg3 + "  clicked   "  ,  Toast.LENGTH_LONG).show();
        	AlertDialog.Builder alertDialog = new AlertDialog.Builder(outOfReester.this);
      		alertDialog.setTitle("áƒ¬ áƒ� áƒ¨ áƒš áƒ�   ...");
      		alertDialog.setMessage("áƒœáƒ�áƒ›áƒ“áƒ•áƒ˜áƒšáƒ�áƒ“ áƒ’áƒ¡áƒ£áƒ áƒ— áƒ©áƒ�áƒœáƒ�áƒ¬áƒ”áƒ áƒ˜áƒ¡ áƒ¬áƒ�áƒ¨áƒšáƒ�?");
      		alertDialog.setCancelable(false);
        	
      		final long p = arg3;
      		
      		alertDialog.setPositiveButton("áƒ“áƒ˜áƒ�áƒ®", new DialogInterface.OnClickListener() {
      			
      			@Override
      			public void onClick(DialogInterface dialog, int which) {

      				 db.delRecOnReesterOut(p);
            	 cursor.requery();
            	 ClearRow(null);           
      			}
      		});
      		
      		
      		 
      		
      		alertDialog.setNegativeButton("áƒ�áƒ áƒ�", null);
      		alertDialog.show();
      		
        
	        return true;
	        
	        
	        
	        
        }
        
     }); 
       
       
   }
	
	// -------------------------
	public void ClearRow(View v){
		// áƒ›áƒ�áƒ®áƒ“áƒ”áƒ¡ áƒ�áƒ›áƒ�áƒ áƒ©áƒ”áƒ£áƒšáƒ˜ áƒ¡áƒ¢áƒ áƒ˜áƒ¥áƒ�áƒœáƒ˜áƒ¡ áƒ›áƒ�áƒœáƒ�áƒªáƒ”áƒ›áƒ”áƒ‘áƒ˜áƒ¡ áƒ’áƒ�áƒ“áƒ�áƒ¬áƒ”áƒ áƒ�  áƒ¨áƒ”áƒ¡áƒ�áƒ¢áƒ�áƒœ áƒ•áƒ”áƒšáƒ”áƒ‘áƒ¨áƒ˜
		
		 TextView  t_mricxv;
		 
		 t_mricxv       = (TextView)findViewById(R.id.mricxvnumID1);
		 t_mricxv.setText(null);
		 t_mricxv       = (TextView)findViewById(R.id.axalichvID1);
		 t_mricxv.setText(null);
		 t_mricxv       = (TextView)findViewById(R.id.StreetID1);
		 t_mricxv.setText(null);
		 t_mricxv       = (TextView)findViewById(R.id.HouseID1);
		 t_mricxv.setText(null);
		 t_mricxv       = (TextView)findViewById(R.id.bildingID1);
		 t_mricxv.setText(null);
		 t_mricxv       = (TextView)findViewById(R.id.forchID1);
		 t_mricxv.setText(null);
		 t_mricxv       = (TextView)findViewById(R.id.noteID1);
		 t_mricxv.setText(null);
		
		
	}

	 @Override
   protected void onListItemClick(ListView l, View v, int position, long id) {
   	  super.onListItemClick(l, v, position, id);
      TextView  t_mricxv;
   
      Cursor c = cursor;
   
      if (c.moveToPosition(position)){
     	   t_mricxv       = (TextView)findViewById(R.id.mricxvnumID1);  
	       t_mricxv.setText(c.getString(c.getColumnIndexOrThrow(DB.COLUMN_MRICXVNUMB)));
 
    	   t_mricxv       = (TextView)findViewById(R.id.axalichvID1);  
	       t_mricxv.setText(c.getString(c.getColumnIndexOrThrow(DB.COLUMN_AXALICHVENEBA)));
	 
	       t_mricxv       = (TextView)findViewById(R.id.StreetID1);  
	       t_mricxv.setText(c.getString(c.getColumnIndexOrThrow(DB.COLUMN_STREET_NAME)));
	 
	       t_mricxv       = (TextView)findViewById(R.id.HouseID1);  
	       t_mricxv.setText(c.getString(c.getColumnIndexOrThrow(DB.COLUMN_SAXLIS_NUM)));

	       t_mricxv       = (TextView)findViewById(R.id.bildingID1);  
	       t_mricxv.setText(c.getString(c.getColumnIndexOrThrow(DB.COLUMN_KORP_NUM)));

	       t_mricxv       = (TextView)findViewById(R.id.forchID1);  
	       t_mricxv.setText(c.getString(c.getColumnIndexOrThrow(DB.COLUMN_SADARB_NUM))); 
	 
	       t_mricxv       = (TextView)findViewById(R.id.noteID1);  
	       t_mricxv.setText(c.getString(c.getColumnIndexOrThrow(DB.COLUMN_BILL_NOTE))); 
      }
	 
	}
	
	  @Override
	  public void onBackPressed() {
	  	// TODO Auto-generated method stub
	  	// super.onBackPressed();  es dakomentarebuli unda iyos
		  
	  }
	  
	  
	

}
