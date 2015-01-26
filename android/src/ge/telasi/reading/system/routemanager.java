package ge.telasi.reading.system;

import ge.telasy.reading.system.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class routemanager extends ListActivity implements Waiting {
	
	  DB db;
	  Cursor cursor;
	  ListAdapter scAdapter;
	  final String LOG_TAG ="mylog";	
	  String where =  DB.COLUMN_INSP_ID + " =  " + C.INSPECTOR_ID;
	  ListView lv;
	  
	  public Bundle bundle;
	  
	  
	  private static final int CM_MAKE_MAIN_ID = 1;
	  private static final int CM_CLOSE_ID = 2;
	  private static final int CM_INT_ATVIRTVA_ID = 3;
	  private static final int CM_DETAILS_ID = 5;
	  private static final int CM_DELETE_ID = 7;
	  private static final int go_back = 8;
	  private long   RowID; 
	  private int   amc_pos;
	  
	 /** Called when the activity is first created. */	
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.routemanager);
      
      ManageMyApps.routemanager = this;
      
      db = new DB(this);
      db.open();
      
      fillData(where);    //  დაკონეცტებული მომხმარებლის ყველა მარშუტის ჩვენება
       
  }
  
  
  @Override
  public void setWait(boolean wait) {
  	// TODO Auto-generated method stub
  
  }
  
 // ------------------------------------------------------------- 
  
  @SuppressWarnings("deprecation")
  public void fillData(String where){
  	
    	cursor = db.getHF(where);      
      startManagingCursor(cursor);
      
      
      String[] from = new String[] { "routis_tarigi",  "amc_name", "marsh_name", "marsh_num", "wamkitxveli", "wasakitxia_sul", "saechvo_wakitxva", "route_status" };
         int[] to = new int[]      { R.id.routis_tarigiID, R.id.amc_nameID, R.id.marsh_nameID,      R.id.marsh_numID,      R.id.wamkitxveliID,      R.id.wasakitxia_sulID,    R.id.saechvo_wakitxvaID, R.id.statusID};
      
      scAdapter = new SimpleCursorAdapter(this,  R.layout.routemanagerrov, cursor, from, to );
      
      setListAdapter(scAdapter);
 //  -----
      
      lv = getListView();
     
      registerForContextMenu(lv);
    
  }
  
  
  
  @Override
  public void onCreateContextMenu(ContextMenu menu, View v,  ContextMenuInfo menuInfo) {
    // TODO Auto-generated method stub
  	 super.onCreateContextMenu(menu, v, menuInfo);
  	 
  	 
  	 menu.add(0, CM_MAKE_MAIN_ID, 0, "წაკითხვისათვის მარშუტის არჩევა");
  	 menu.add(0, CM_CLOSE_ID, 0, "მარშუტის წაკითხვა დასრულებულია");
  	 menu.add(0, CM_INT_ATVIRTVA_ID, 0, "მარშუტის გადაგზავნა");
  	 menu.add(0, CM_DETAILS_ID, 0, "დეტალური ინფორმაცია");
  	 menu.add(0, CM_DELETE_ID, 0, "მარშუტის წაშლა");

  }
  
  
  @SuppressWarnings("deprecation")
@Override
  public boolean onContextItemSelected(MenuItem item) {
  	
  	AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
  	 
      bundle = new Bundle();
	  bundle.putString("user", C.USER);
	  String ins_kode = new  Integer(C.INSPECTOR_ID).toString();
	  bundle.putString("ins_kode", ins_kode);
	  
	    
    switch (item.getItemId()){
  	     case CM_MAKE_MAIN_ID:
  	         {
  	        	int v_status = db.getReesterStstus(acmi.id); 
  	        	if (v_status == 4 | v_status == 3 ){
  	        		   RowID  = acmi.id;
  	        		   C.ROUTE_NUMB = db.getMarshNum(acmi.id);
  	        		   showDialog(110);
  	        	} else {
        	           MakeReestersStatusToMain(acmi.id);
 	                   C.ROUTE_NUMB = db.getMarshNum(acmi.id);
		   	           Intent ii = new Intent(getApplicationContext(), TARSAktivity.class);  
			           ii.putExtras(bundle);
    	  	           startActivity(ii);
  	        	}
  	         }
            	break;
       	 case CM_CLOSE_ID:
       		    int mdgm = db.closeReester(acmi.id);
       		    RowID =  acmi.id;
       		    if (mdgm == 101){          // რეესტრი დამუშავებულია ბოლომდე
   	        	    db.updateRouteStatus(4, RowID);
        	        cursor.requery();
                } else if(mdgm ==  104 | mdgm ==103){
                	MyMessages msg = new MyMessages();
                	msg.getMyToast(this, R.string.noDeletedRoute,  R.drawable.imagesstopnow, Toast.LENGTH_LONG);
                } else {
                   showDialog(102);                
                }
    		    break;
  	     case CM_INT_ATVIRTVA_ID:              //   -----------------    როუტის გადაგზავნა
  	    	    int mdmg = db.UploadRoute(acmi.id);    // მიმდინარე მარშუტი რა სტატუსისაა
  	    	    RowID = acmi.id;
  	    	    amc_pos = acmi.position;
  	    	    if(mdmg == 106  |  mdmg == 105){                              //  როუტი დამუშავდა  ბოლომდე, ან  დახურულია, მოხდეს მისი ატვირთვა
                	MyMessages msg = new MyMessages();
                	msg.getMyToast(this, R.string.DovloadIsstarted,  android.R.drawable.ic_input_add, Toast.LENGTH_LONG);
  	  	        	
                	UploadReester(acmi.position);
  	  	            db.updateRouteStatus(3, RowID); 
  	  	        	
  	  	            //  შეტყობინება როუტის დასასრული
  	    	    } else if(mdmg == 107){   // შეტყობინება მარშუტის სრულად არაა დამუშავებული
  	    	    	showDialog(107); 
  	    	    } else if (mdmg == 108){   // მარშუტი ცარიელია   მის ატვირთვას აზრი არააქვს
  	               	MyMessages msg = new MyMessages();
                	msg.getMyToast(this, R.string.noCloseRoute,  R.drawable.imagesstopnow, Toast.LENGTH_LONG);
  	    	    } else if (mdmg == 109){   // მარშუტი უკვე ატვირთულია
  	    	    	showDialog(109);  	
  	    	    }
             	break;
	       case CM_DETAILS_ID:
	      	    ShowReesterDetails();
       	        break;
  	     case CM_DELETE_ID:
  	    	   RowID =  acmi.id;
          	   showDialog(CM_DELETE_ID);
    	   	   break;
    }
 
    return true;
  }
  
  
  @SuppressWarnings("deprecation")
  private void openReester(long rowID) {
	  // TODO Auto-generated method stub
	  db.openReester(rowID);
	  cursor.requery();
  	
  }


  @SuppressWarnings("deprecation")
  private void DeteteReester(long rowID) {
	  // TODO Auto-generated method stub
		db.DeleteReester(rowID);
		cursor.requery();
  }


	private void ShowReesterDetails() {
	  // TODO Auto-generated method stub
	  
  }

	@SuppressWarnings("deprecation")
  private void CloseReester(long rowID) {    
	  // TODO Auto-generated method stub
	  // როუტის დახურვა	
      db.closeReester(rowID);
  	  cursor.requery();
  }

	
	@SuppressWarnings("deprecation")
    public void UploadReester(int position){
  	    // რეესტრის ატვირთვა ინტერნეტით
  	    Cursor c1 = cursor;
        c1.moveToPosition(position);
        int reestrid;
        
        int marshnumb;                                                                   // ---------  davamate 2014  09 17    
        marshnumb  =  c1.getInt(c1.getColumnIndexOrThrow(DB.COLUMN_R_MARSH_NUM));
        
   	    reestrid = c1.getInt(c1.getColumnIndexOrThrow(DB.COLUMN_R_REESTRID)); 
   	    
   	    C.uploadReester(this, reestrid, marshnumb);                            // bolo parametri davamate  2014  09 17
   	    c1.requery();
  }
  
  
  
  @SuppressWarnings("deprecation")
  public void MakeReestersStatusToMain(long position){
  	// რეესტრის სტატუსის შეცვლა
  	db.updateReesterStstus(position);
  	cursor.requery();
  	
  }
  
  
  

  @SuppressWarnings("deprecation")
public void goToMainReester(View v){
	  //  აქ უნდა იყოს აქტიურ რეესტრზე გადასვლა 
	  // შეგვიშლია ეს ღილაკი გავაქროთ თუ აქტიური რეესტრი არ აქვს 
	  // გავაჩინოთ მაშინ როცა აქტიური ღილაკი აქვს
 	  int k = 0; 
	  Cursor InspectRoutesCount = db.getHF(DB.COLUMN_INSP_ID + "=" +C.INSPECTOR_ID + " and  " + DB.COLUMN_R_STATUSI + " != 3" ) ;
	  if (InspectRoutesCount.getCount() > 0){
		  // რეესტრები აქვს ჩამოტვირთული
		  k = 1;
		  InspectRoutesCount.close();
		  InspectRoutesCount  = db.getHF(DB.COLUMN_INSP_ID + "=" +C.INSPECTOR_ID + " and  " + DB.COLUMN_R_STATUSI + " = 5" ) ;
		  if(InspectRoutesCount.getCount() > 0){
			  // მანი მარშუტიც აქვს ამორჩეული
			  k = 2;
		  }
		  InspectRoutesCount.close();
	  } else{
		  InspectRoutesCount.close();
	  }

	  if( k == 2){
		  //  მაინ მარშუტზე გადასვლა
	      cursor.close();
		  stopManagingCursor(cursor);
		  db.close();
     	  Bundle bundle = new Bundle();
	      bundle.putString("user", C.USER );
	      bundle.putString("ins_kode", Integer.toString(C.INSPECTOR_ID));

	   	  Intent ii = new Intent(getApplicationContext(), TARSAktivity.class);  // ფილტრი დგება  ACTIVITY-ში
	   	  ii.putExtras(bundle);
	  	  startActivity(ii);
	  } else if (k == 1) {
		  // შეტყობინება იმაზე რომ მომხმარებელმა უნდა ამოირჩიოს
		  // მარშუტი გასააქტიურებლად
		  MyMessages mym = new MyMessages();
		  mym.getMyToast(getApplicationContext(), R.string.mainreestriaragaqvt, android.R.drawable.ic_dialog_alert, Toast.LENGTH_LONG);
	  }  else{
         // შეტყობინება იმაზე რომ მას როუტები ჯერ არ ჩამოუტვირთავს და ჩამოტვირთოს   		 
		  MyMessages mym = new MyMessages();
		  mym.getMyToast(getApplicationContext(), R.string.noreesterdownloads, android.R.drawable.ic_dialog_alert, Toast.LENGTH_LONG);
	  }
  }
  
 @SuppressWarnings("deprecation")
public void goToReesterdownload(View v){
	  // ჩამოსატვირთ რეესტრების ფანჯარაზე გადასვლა
	    Log.d("MyLog","შემოვიდა რეესტრების ჩამოტვირთვის პუნქტში");
        cursor.close();
	    stopManagingCursor(cursor);
	    db.close();
        Bundle bundle = new Bundle();
        bundle.putString("user", C.USER );
        bundle.putString("ins_kode", Integer.toString(C.INSPECTOR_ID));
	    Intent ii = new Intent(getApplicationContext(), routedownload.class);
	    ii.putExtras(bundle);
	    startActivity(ii);
  }
  

 
//------------------------------------------------------------------  
  @SuppressWarnings("deprecation")
@Override
  public void onBackPressed() {
  	// TODO Auto-generated method stub
  	// super.onBackPressed();  es dakomentarebuli unda iyos
	showDialog(go_back);  
  }
  
  
  
 //   --- d i a l o g i s   g a k e t e b a
  
  @SuppressWarnings("deprecation")
protected Dialog onCreateDialog(int id) {
	 
	  switch (id){
	  
	  case CM_DELETE_ID:                  //  არჩეულია მენიუდად რეესტრის წაშლა
	      {
	    	  AlertDialog.Builder adb = new AlertDialog.Builder(this);
	    	  adb.setTitle(R.string.deleteroutetitleText);
              adb.setMessage(R.string.deleterouteMessageText);
              //adb.setIcon(android.R.drawable.ic_dialog_info);
              adb.setIcon(R.drawable.imagesstopnow);
              adb.setPositiveButton(R.string.btnYes, myDialogClickListener);
              adb.setNegativeButton(R.string.btnNo, myDialogClickListener);
              adb.setCancelable(false); 
              return adb.create();

	      }
	  case go_back:
     	  {
     	      cursor.close();
     	      stopManagingCursor(cursor);
     	      finish();
	      }
     	  break;
	case 102:                    // reestri damuSavebulia magram masSi arsebobs erTi mainc daumuSavebeli Canaweri  (reestris daxurva)
	    	  AlertDialog.Builder adb = new AlertDialog.Builder(this);
	    	  adb.setTitle(R.string.CloseReesterNoteText);
              adb.setMessage(R.string.CloseReesterMessageText);
              adb.setIcon(android.R.drawable.ic_dialog_info);
              adb.setPositiveButton(R.string.btnYes, DialogCloseListener);
              adb.setNegativeButton(R.string.btnNo,  DialogCloseListener);
              adb.setCancelable(false); 
              return adb.create(); 
	  case 109:                                        // მარშუტი უკვე ატვირთულია	, ხელახალი ატვირთვა  
        	  AlertDialog.Builder adb1 = new AlertDialog.Builder(this);
    	      adb1.setTitle(R.string.UploadeReesterNoteText);
              adb1.setMessage(R.string.UploadReesterMessageText);
              adb1.setIcon(android.R.drawable.ic_dialog_info);
              adb1.setPositiveButton(R.string.btnYes, DialogUploadReesterListener);
              adb1.setNegativeButton(R.string.btnNo,  DialogUploadReesterListener);
              adb1.setCancelable(false); 
              return adb1.create(); 
	  case 107:
    	      AlertDialog.Builder adb2 = new AlertDialog.Builder(this);
	          adb2.setTitle(R.string.UploadeReesterTitleText);
              adb2.setMessage(R.string.UploadReesterMessageText1);
              adb2.setIcon(android.R.drawable.ic_dialog_info);
              adb2.setPositiveButton(R.string.btnYes, DialogUploadReesterListener);
              adb2.setNegativeButton(R.string.btnNo,  DialogUploadReesterListener);
              adb2.setCancelable(false); 
              return adb2.create(); 
	  case 110:   // უნდა მოხდეს მარშუტის გახსნისათვის 3,4 დიალოგის წამოწყება
     	     AlertDialog.Builder adb3 = new AlertDialog.Builder(this);
             adb3.setTitle(R.string.OpeningReesterTitleText);
             adb3.setMessage(R.string.OpeningReesterMessageText1);
             adb3.setIcon(android.R.drawable.ic_dialog_info);
             adb3.setPositiveButton(R.string.btnYes, DialogUploadReesterListener3);
             adb3.setNegativeButton(R.string.btnNo,  DialogUploadReesterListener3);
             adb3.setCancelable(false); 
             return adb3.create(); 
	  }
	  
	  return super.onCreateDialog(id);
  }
  
  
  
  
  // ---- d i a l o g i s   d a m u S a v e b a 
  
  OnClickListener myDialogClickListener = new OnClickListener() {
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		switch (arg1) {
    		 //нажатие кнопки "Yes"
	         case Dialog.BUTTON_POSITIVE:
	  	          if (C.ROUTE_NUMB  == db.getMarshNum(RowID)){
    	    	         C.ROUTE_NUMB = null;
    	          }
	    	      DeteteReester(RowID);
        		   break;
             case Dialog.BUTTON_NEGATIVE:
	               break;
	     }
	}
};


//  რეესტრის სტატუსის შეცვლა როგორც დასრულებულად
OnClickListener DialogCloseListener = new OnClickListener() {
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		switch (arg1) {
    		 //нажатие кнопки "Yes"
	         case Dialog.BUTTON_POSITIVE:
	        	 db.updateRouteStatus(4, RowID);
        	     cursor.requery();
	        	 break;
             case Dialog.BUTTON_NEGATIVE:
                 break;
	     }
	}
};




OnClickListener DialogUploadReesterListener = new OnClickListener() {
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		switch (arg1) {
    		 //нажатие кнопки "Yes"
	         case Dialog.BUTTON_POSITIVE:
	        	 MyMessages msg1 = new MyMessages();
             	 msg1.getMyToast(getApplicationContext(), R.string.UploadIsstarted,  android.R.drawable.ic_input_add, Toast.LENGTH_LONG);
	        	 UploadReester(amc_pos);
          	     db.updateRouteStatus(3, RowID);
        	     cursor.requery();                 
        	    		 
	        	 break;
             case Dialog.BUTTON_NEGATIVE:
                 break;
	     }
	}
};


// დიალოგი 3 და 4 სტატუსის მქონე მარშუტის გასხნისათვის
OnClickListener DialogUploadReesterListener3 = new OnClickListener() {
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		switch (arg1) {
    		 //нажатие кнопки "Yes"
	         case Dialog.BUTTON_POSITIVE:
                 db.updateReesterStstus(RowID);   
                 cursor.requery();
                 cursor.close();
                 db.close();
                 Intent ii = new Intent(getApplicationContext(), TARSAktivity.class);  
		         ii.putExtras(bundle);
	  	         startActivity(ii);
	        	 break;
             case Dialog.BUTTON_NEGATIVE:
                 break;
	     }
	}
};





}