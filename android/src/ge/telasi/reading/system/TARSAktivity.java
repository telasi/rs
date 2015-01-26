package ge.telasi.reading.system;

import ge.telasy.reading.system.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TARSAktivity extends ListActivity implements Waiting {
	
   
	DB db;
	Cursor cursor;
	xmlparser myparser;
	ListAdapter scAdapter;
	final String LOG_TAG ="mylog";	
	String where = DB.COLUMN_MARSH_NUM   + "="  +   C.ROUTE_NUMB;
	String NoFilter = where; 
	int   isQueryMode = 0;
	
	private static final int JobCloseReester = 21;
	private static final int JobUploadReester = 22;
	
    private static final int ACTIVITY_CREATE=0;  
    private static final int ACTIVITY_EDIT=1;
    private static final int ACTIVITY_QUERY=5;
 
    public TextView  v_normall;
    public TextView  v_mricxvelis_brunva;
    public TextView  v_min_xarji;
    public TextView  v_max_xarji;
    public TextView  v_umricxvelo;
    public TextView  v_nulovaniwakitxva;
    public TextView  V_saeTvo;
    public int  V_saeTvo_count;
    
    public  EditText myText_find; 

    
	String    COLUMN_ID_PK; 
	
    /** Called when the activity is first created. */	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ManageMyApps.TARSAktivity = this;
        db = new DB(this);
        db.open();
        
        TextView  v_sul = (TextView)findViewById(R.id.sul_chanaweri);    
        v_sul.setText(v_sul.getText() + "   " + db.getReadingCount( DB.COLUMN_MARSH_NUM + "=" + C.ROUTE_NUMB));
        
        TextView v_insp = (TextView)findViewById(R.id.tvwamkitxv);
        v_insp.setText(db.getReadingValues(DB.COLUMN_R_WAMKITXVELI));

        TextView v_raioni = (TextView)findViewById(R.id.tvraion);
        v_raioni.setText(db.getReadingValues(DB.COLUMN_R_AMC ));
        
        TextView v_Text = (TextView)findViewById(R.id.tvText);
        v_Text.setText(db.getReadingValues(DB.COLUMN_R_MARSH_NAME )); 
        
        TextView v_marstnumb = (TextView)findViewById(R.id.tvmarstnumb);
        v_marstnumb.setText(C.ROUTE_NUMB); 
        
        
        // -------------------------
        //v_normall = (TextView)findViewById(R.id.normal);
        //v_normall.setText(v_normall.getText() + "   " + db.getReadingCount( DB.COLUMN_MARSH_NUM + "=" + C.ROUTE_NUMB + "  and  " + DB.COLUMN_READING_STATYS + " = 1" )); 

        //v_mricxvelis_brunva = (TextView)findViewById(R.id.mricxvelis_brunva); 
        //v_mricxvelis_brunva.setText(v_mricxvelis_brunva.getText() + "   " + db.getReadingCount( DB.COLUMN_MARSH_NUM + "=" + C.ROUTE_NUMB + "  and  " + DB.COLUMN_READING_STATYS + " = 4" ));
        
        //v_min_xarji         = (TextView)findViewById(R.id.min_xarji);  
        //v_min_xarji.setText(v_min_xarji.getText() + "   " + db.getReadingCount( DB.COLUMN_MARSH_NUM + "=" + C.ROUTE_NUMB + "  and  " + DB.COLUMN_READING_STATYS + " = 2" ));
        
        //v_max_xarji         = (TextView)findViewById(R.id.max_xarji); 
        //v_max_xarji.setText(v_max_xarji.getText() + "   " + db.getReadingCount( DB.COLUMN_MARSH_NUM + "=" + C.ROUTE_NUMB + "  and  " + DB.COLUMN_READING_STATYS + " = 3" )); 
        
        //v_umricxvelo        = (TextView)findViewById(R.id.umricxvelo);  
        //v_umricxvelo.setText(v_umricxvelo.getText() + "   " + db.getReadingCount( DB.COLUMN_MARSH_NUM + "=" + C.ROUTE_NUMB + "  and  " + DB.COLUMN_READING_STATYS + " = 5" ));
        
        //v_nulovaniwakitxva  = (TextView)findViewById(R.id.nulovaniwakitxva);
        //v_nulovaniwakitxva.setText(v_nulovaniwakitxva.getText() + "   " + db.getReadingCount( DB.COLUMN_MARSH_NUM + "=" + C.ROUTE_NUMB + "  and  " + DB.COLUMN_READING_STATYS + " = 6" ));  
        
        //V_saeTvo = (TextView)findViewById(R.id.saeTvo);
        //V_saeTvo_count  = db.getReadingCount( DB.COLUMN_MARSH_NUM + "=" + C.ROUTE_NUMB + "  and  " + DB.COLUMN_READING_STATYS + " != 0  and "  +   DB.COLUMN_READING_STATYS + " != 1" );
        //V_saeTvo.setText(Integer.toString(V_saeTvo_count));
        
        myText_find = (EditText)findViewById(R.id.text_for_find);
        myText_find.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
        	{
        	    
            	if (keyCode == KeyEvent.KEYCODE_ENTER){
            		if(event.getAction() == KeyEvent.ACTION_DOWN){
        			    String strFindString =  myText_find.getText().toString();
        			    strFindString = DB.COLUMN_MRICXVNUMB + "  like   '%" +  strFindString + "'";
        			    isQueryMode = 1; 
        			    fillData(strFindString);
            		}else{
                        // klaviaturi mocileba 
             			// прячем клавиатуру. butCalculate - это кнопка
            			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            			imm.hideSoftInputFromWindow(myText_find.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            		}
            		return true;
            	}
            	
        		return false;
        	}
        }
        );
        
        // ------------------------------
        db.getReadingCount(DB.COLUMN_MARSH_NUM + " == " + C.ROUTE_NUMB);
        
        fillData(where);
    }

	@Override
	public void setWait(boolean wait) {
		// TODO Auto-generated method stub

	}

   @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
    	Cursor c = cursor;
        c.moveToPosition(position);
        
        Intent i = new Intent(this, enterCountersShow.class);
        
        i.putExtra(DB.COLUMN_AXALICHVENEBA,   c.getString(c.getColumnIndexOrThrow(DB.COLUMN_AXALICHVENEBA))); 
        i.putExtra(DB.COLUMN_ID,     c.getString(c.getColumnIndexOrThrow(DB.COLUMN_ID)));        
        i.putExtra(DB.COLUMN_ADDESS, c.getString(c.getColumnIndexOrThrow(DB.COLUMN_ADDESS)));
        i.putExtra(DB.COLUMN_ACCNUMB, c.getString(c.getColumnIndexOrThrow(DB.COLUMN_ACCNUMB)));
        i.putExtra(DB.COLUMN_NAME,    c.getString(c.getColumnIndexOrThrow(DB.COLUMN_NAME)));
        i.putExtra(DB.COLUMN_ACCOUNT, c.getString(c.getColumnIndexOrThrow(DB.COLUMN_ACCOUNT)));
        i.putExtra(DB.COLUMN_MRICXVNUMB, c.getString(c.getColumnIndexOrThrow(DB.COLUMN_MRICXVNUMB)));
        i.putExtra(DB.COLUMN_MODELISKODI,  c.getString(c.getColumnIndexOrThrow(DB.COLUMN_MODELISKODI)));
        i.putExtra(DB.COLUMN_BRJENISNUMB,  c.getString(c.getColumnIndexOrThrow(DB.COLUMN_BRJENISNUMB)));
        i.putExtra(DB.COLUMN_ISDISCONECTED,  c.getString(c.getColumnIndexOrThrow(DB.COLUMN_ISDISCONECTED)));
        i.putExtra(DB.COLUMN_ARACVSMRICXVELI,  c.getString(c.getColumnIndexOrThrow(DB.COLUMN_ARACVSMRICXVELI)));
        i.putExtra(DB.COLUMN_KOEFICIENTI,  c.getString(c.getColumnIndexOrThrow(DB.COLUMN_KOEFICIENTI)));
        i.putExtra(DB.COLUMN_WINACHVENEBA,  c.getString(c.getColumnIndexOrThrow(DB.COLUMN_WINACHVENEBA)));
        i.putExtra(DB.COLUMN_WINACHVENEBISTAR,   c.getString(c.getColumnIndexOrThrow(DB.COLUMN_WINACHVENEBISTAR)));
        i.putExtra(DB.COLUMN_NOTE, c.getString(c.getColumnIndexOrThrow(DB.COLUMN_NOTE)));                                     
        i.putExtra(DB.COLUMN_BILL_NOTE, c.getString(c.getColumnIndexOrThrow(DB.COLUMN_BILL_NOTE)));
        i.putExtra(DB.COLUMN_MIN_XARJI, c.getString(c.getColumnIndexOrThrow(DB.COLUMN_MIN_XARJI)));
        i.putExtra(DB.COLUMN_MAX_XARJI, c.getString(c.getColumnIndexOrThrow(DB.COLUMN_MAX_XARJI)));
        i.putExtra(DB.COLUMN_READING_STATYS,  c.getString(c.getColumnIndexOrThrow(DB.COLUMN_READING_STATYS)));
        i.putExtra(DB.COLUMN_WINACHVENEBA,  c.getString(c.getColumnIndexOrThrow(DB.COLUMN_WINACHVENEBA)));
        i.putExtra(DB.COLUMN_DIGITS,  c.getString(c.getColumnIndexOrThrow(DB.COLUMN_DIGITS)));
        i.putExtra(DB.COLUMN_MODELISKODINW, c.getString(c.getColumnIndexOrThrow(DB.COLUMN_MODELISKODINW))); 
        i.putExtra(DB.COLUMN_BRJENISNUMBNW, c.getString(c.getColumnIndexOrThrow(DB.COLUMN_BRJENISNUMBNW)));
        i.putExtra(DB.COLUMN_KOEFICIENTINW, c.getString(c.getColumnIndexOrThrow(DB.COLUMN_KOEFICIENTINW)));
  
        
        startActivityForResult(i, ACTIVITY_EDIT);
 
    }
    
    
    @SuppressWarnings("deprecation")
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {  
        super.onActivityResult(requestCode, resultCode, intent);
        
        if (requestCode == ACTIVITY_EDIT){
               Bundle extras = intent.getExtras();  
               String rowId = extras.getString(DB.COLUMN_ID);  
               if (rowId != null) {  
                    String axali_chveneba = extras.getString(DB.COLUMN_AXALICHVENEBA); 
                    String rdst = extras.getString(DB.COLUMN_READING_STATYS);
                    String modelkodenew = extras.getString(DB.COLUMN_MODELISKODINW);
                    String brjenisnumbnew = extras.getString(DB.COLUMN_BRJENISNUMBNW);
                    String koeficientinew = extras.getString(DB.COLUMN_KOEFICIENTINW) ;
                    
                    String note = extras.getString(DB.COLUMN_NOTE);
                    String note1 = extras.getString(DB.COLUMN_BILL_NOTE);     
                                                                                                                          
                    db.updateChveneba(rowId, axali_chveneba, rdst, modelkodenew, brjenisnumbnew, koeficientinew, note1,  note );   
                    cursor.requery();
               }  

             }  else if (requestCode == ACTIVITY_QUERY){
            	 Bundle extras = intent.getExtras(); 
            	 // ზაპროსის შედგენის ფანჯარის  შედეგი დაბრუნდა
            	 isQueryMode = 1;
            	 where =  extras.getString("WHERE");   
                 fillData(where);     // მონაცემების  განახლებას თავად ლისტი აკეთებს. მაგრამ როცა კურსორის მონაცემების გამოტანის პირობები იცვლება  მჭირდება კურსორის განახლება
                                     // ანუ გავუშვა  fillData(where);
            	 
             } 
    }  

    
    @SuppressWarnings("deprecation")
    public void fillData(String where){

    	cursor = db.getAllData(where);      
        startManagingCursor(cursor);
        
        String[] from = new String[] { "address", "accnumb", "name" , "mricxvnumb",  "winachveneba", "axalichveneba"  };
        int[] to = new int[] { R.id.tvAddress, R.id.tvAccNumb, R.id.tvName,  R.id.tvmMricxvNumb,R.id.tvWinaChveneba, R.id.tvAxaliChveneba };
        
        scAdapter = new MyCursorAdapter(this,  R.layout.rowlayout, cursor, from, to );
        
        setListAdapter(scAdapter);
    }
      
 public void more(View v)  {     //  გაფართოებული შესაძლებლობის ფანჯარის გამოძახება რეესტრისათვის, ძებნა , ფილტრები, კოორდინატები
	//	Toast.makeText(this, "არჩეულია სისტემის დამატებითი \n შესაძლებლობების გამოძახება", Toast.LENGTH_LONG).show(); 
		
		
   		Intent ii1 = new Intent(getApplicationContext(), FromQuery.class);
   		
   		ii1.putExtra(DB.COLUMN_MARSH_NUM, DB.COLUMN_MARSH_NUM); 
   		ii1.putExtra(DB.COLUMN_STREET_ID, DB.COLUMN_STREET_ID);
   		ii1.putExtra(DB.COLUMN_SADARB_NUM, DB.COLUMN_SADARB_NUM);
   		ii1.putExtra(DB.COLUMN_KORP_NUM, DB.COLUMN_KORP_NUM);
   		ii1.putExtra(DB.COLUMN_ADDESS, DB.COLUMN_ADDESS);

   		
  		startActivityForResult(ii1, ACTIVITY_QUERY);
		  	
  }
    
    
  
  
// ---------  m e n i u ----------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
    //  მენიუს ლემენტების დამატება 
		getMenuInflater().inflate(R.menu.main4reester, menu);
		return true;
	}

	
  @SuppressWarnings("deprecation")
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
	  // TODO Auto-generated method stub
		
    		switch (item.getItemId()) {
    		    case R.id.extend_find:            //გაფართოებული ძებნა
   	    	    	more(null);	
      	    		return true;
	    	    case R.id.new_account:            //მოიძებნა ახალი მრიცხველი
	       	 	    Intent ii = new Intent(getApplicationContext(), outOfReester.class);
	    		    startActivity(ii);
  			        return true;
	    	    case R.id.cloes_reester:          //რეესტრის დახურვა
    	    	    showDialog(JobCloseReester);
  			        return true;
	    	    case  R.id.goToManageReester:     // რეესტრების მართვაზე გადასვლა  
            		Intent ii2 = new Intent(getApplicationContext(), routemanager.class);
          	     	startActivity(ii2);  
	    	    	return true;
	    	    case R.id.upload_reester:         //რეესტრის ატვირთვა 
	    	    	//აქ გაკეტდეს მიმდინარე რეესტრის  ატვირთვა
	    	    	// წესით ყველაფერი კარგად უნდა მუშაობდეს 
	    	    	// მოხდეს შეტყობინების დამუშავება
	    	    	// შესაძლოა დასრულება დაუსრულებლობაზეც შემოწმება 
	    	    	//                                          აქედან უნდა გავაგრძელო
      	    	    showDialog(JobUploadReester);
	    	    	return true;
                
	    	    case R.id.download_rester:        //რეესტრის ჩამოტვირთვა
	    	    	  
	    	    	Bundle bundle = new Bundle();
	    	        bundle.putString("user", C.USER);                     
	    	        bundle.putString("ins_kode", Integer.toString(C.INSPECTOR_ID));
	    	   		
	    	   	    Intent ii1 = new Intent(getApplicationContext(), routedownload.class);
	    	   		ii1.putExtras(bundle);
	    	   		startActivity(ii1);
	    	   		  
	    	   		return true;
	    	    case R.id.exit:                  //პროგრამიდან გამოსვლა
	    	    	
	    	      ManageMyApps.close();
  			       return true;
	    	    default:
			         return super.onOptionsItemSelected(item);
	}
   
	}
	
	
//---------  m e n i u    dasasruli  ----------	
  
@Override
  public void onBackPressed() {
  	// TODO Auto-generated method stub
  	// super.onBackPressed();  es dakomentarebuli unda iyos
	if (isQueryMode == 1) {
       fillData(NoFilter);   // ფილტრის მოხსნისათვის გამოვიყენოთ უკან დაბრუნების ღილაკი
       isQueryMode = 0;
       myText_find.setText("");
	}
  }
  


// დიალოგის ფანჯრების კონფიგურირება

@SuppressWarnings("deprecation")
protected Dialog onCreateDialog(int id) {
	 switch (id){
	  
	  case JobCloseReester:                  //  რეესტრის დახურვა
	      {
	    	  AlertDialog.Builder adb = new AlertDialog.Builder(this);
	    	  adb.setTitle(R.string.CloseReesterNoteText);
              adb.setMessage(R.string.CloseReesterMessageText1);
              adb.setIcon(android.R.drawable.ic_dialog_info);
              adb.setPositiveButton(R.string.btnYes, DialogCloseListener);
              adb.setNegativeButton(R.string.btnNo,  DialogCloseListener);
              adb.setCancelable(false); 
              return adb.create(); 
	      }
	   case JobUploadReester:
	     {
    	      AlertDialog.Builder adb = new AlertDialog.Builder(this);
    	      adb.setTitle(R.string.UploadReesterTitleText0);
              adb.setMessage(R.string.UploadReesterMessageText0);
              adb.setIcon(android.R.drawable.ic_dialog_info);
              adb.setPositiveButton(R.string.btnYes, DialogUploadListener);
              adb.setNegativeButton(R.string.btnNo,  DialogUploadListener);
              adb.setCancelable(false); 
              return adb.create(); 
	  }
	}
	  return super.onCreateDialog(id);
}


 
// დიალოგის დამუშავება

//რეესტრის სტატუსის შეცვლა როგორც დასრულებულად
OnClickListener DialogCloseListener = new OnClickListener() {
@Override
public void onClick(DialogInterface arg0, int arg1) {
	// TODO Auto-generated method stub
	switch (arg1) {

         case Dialog.BUTTON_POSITIVE:
         	  int RowID = db.getMainMarshRowID(C.INSPECTOR_ID);
              db.updateRouteStatus(4,RowID);
              //
              Intent ii2 = new Intent(getApplicationContext(), routemanager.class);
          	  startActivity(ii2);
        	 break;
         case Dialog.BUTTON_NEGATIVE:
             break;
     }
}
};



OnClickListener DialogUploadListener = new OnClickListener() {
@Override
public void onClick(DialogInterface arg0, int arg1) {
	// TODO Auto-generated method stub
	switch (arg1) {

         case Dialog.BUTTON_POSITIVE:
         	  int RowID = db.getMainMarshRowID(C.INSPECTOR_ID);
              int reestrid  = db.getReesterNum(RowID);
              int  marshnumb = Integer.parseInt(db.getMarshNum(RowID));           //      davamate 2014  09   17
        	  C.uploadReester(ManageMyApps.TARSAktivity, reestrid, marshnumb);         // mesame parametri davamate 2014  09   17
        	  
      	      db.updateRouteStatus(3, RowID);      // სტატუსი შეეცვალოს
              Intent ii2 = new Intent(getApplicationContext(), routemanager.class);
          	  startActivity(ii2);
        	 break;
         case Dialog.BUTTON_NEGATIVE:
             break;
     }
}
};



//------------------------------------------------------


}


