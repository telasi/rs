package ge.telasy.reading.system;

import ge.telasy.reading.system.R;

import java.util.List;

import telasi.android.reading.model.Reester;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class routedownload extends ListActivity implements Waiting {
	  
	public final int  BACK_INT = 1;
	public String user;
	public String ins_kode;
	
	public ProgressBar v_myProgressBar;
	
	MyMessages m = new MyMessages();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.routedownload);
		
		ManageMyApps.routedownload = this;
		
		user     = getIntent().getExtras().getString("user");
		ins_kode = getIntent().getExtras().getString("ins_kode");

		v_myProgressBar = (ProgressBar) findViewById(R.id.downloadProgressBar);
		
//		Toast.makeText(routedownload.this, "მიმდინარე მომხმარებელი არის   " + user + " მისი კოდი კი არის  =   "  + ins_kode,  Toast.LENGTH_LONG).show();

		C.getReesters(this, 1);
	}

	public void onReestersReceived(final List<Reester> reesters) {
		
	  Reestrdown adapter = new Reestrdown(this, reesters);
      setListAdapter(adapter);
     
      ListView list = getListView();
      list.setOnItemLongClickListener(new OnItemLongClickListener() {

      @Override
      public boolean onItemLongClick(AdapterView<?> parent, View view,  int position, long id) {
      	
      	int reestrid = reesters.get(position).getId();
      	int marshnum = reesters.get(position).getRoute();
      	C.progressMaxValues =reesters.get(position).getCount();  
      	
      	int k = 1;
    	DB db = new DB(getApplicationContext());
    	db.open();
      	if (db.isRouteDownloaded(marshnum)){
      	    // მარშუტი არსებობს მეტის ჩამოტვირთვა შეუძლებელია
            k = 0;	
      	}
      	db.close();
        //  C.loadReester(routedownload.this, reestrid );
        if (k == 1){
        	
        	if (C.isDownloadRunning == 0){
        	   m.getMyToast(getApplicationContext(), R.string.DovloadIsstarted,  android.R.drawable.ic_input_add, Toast.LENGTH_LONG);
               //  new downloadProgress(ManageMyApps.routedownload).execute();  
               C.loadReester(routedownload.this, reestrid );
        	} else{
          		m.getMyToast(getApplicationContext(), R.string.isDownloadRunning,  R.drawable.imagesstopnow, Toast.LENGTH_LONG);
        	}
            
        }  else{
      		m.getMyToast(getApplicationContext(), R.string.routeisdownloades,  R.drawable.imagesstopnow, Toast.LENGTH_LONG);
        }
          return true;
      }
  });
    

	}

	@Override
  public void setWait(boolean wait) {
	  // TODO Auto-generated method stub
	  
  }

	
	public void exitOnDownload(View v){
		//  რეესტრის ჩამოტვირთვის დასრულების შემდეგ გადავიდეს
		//  ან მარშუტების მართვაზე ან აქტიურ რეესტრზე
		
		DB db = new DB(this);
		db.open();
		int is_reester_down = 0; 
        Cursor InspectRoutesCount;
	    InspectRoutesCount = db.getHF( DB.COLUMN_INSP_ID + "=" + C.INSPECTOR_ID + " and  " + DB.COLUMN_R_STATUSI + " != 3" ) ;
        if  (InspectRoutesCount.getCount() > 0){
        	// მარშუტები აქვს დავადგინოთ  აქტიური მარშუტის ნომერი
        	 is_reester_down = 1;
        	 InspectRoutesCount.close();
	  	     InspectRoutesCount = db.getHF(DB.COLUMN_INSP_ID + "=" + C.INSPECTOR_ID + " and  " + DB.COLUMN_R_STATUSI + " = 5" ) ;
	  	     if (InspectRoutesCount.getCount() > 0){
	  		        //  აქვს აქტიური რეესტრი ამიტომ გადავიდეთ მასზე 
	  	    	is_reester_down =2;    
	  	     }
	  	     InspectRoutesCount.close();
        } else {
            //  არააქვს სამუშაო მარშუტები, მაგრამ შეიძლება ქონდეს დახურული მარშუტები
        	//  და ამ მარშუტებზე მოახდინოს რეგირება
        	//  შევამოწმოთ დახურული მარშუტსი არსებობა
        	InspectRoutesCount.close();
    	    InspectRoutesCount = db.getHF( DB.COLUMN_INSP_ID + "=" + C.INSPECTOR_ID + " and  " + DB.COLUMN_R_STATUSI + " = 3" ) ;
    	    if (InspectRoutesCount.getCount() > 0){
    	    	// გადაგზავნილი მარშუტი აქვს
    	    	is_reester_down  = 1;
    	    }
    	    InspectRoutesCount.close();
        }


	    switch(is_reester_down){
	         case 1:                   // მარშუტი ჩამოტვირთულია, არააქვს მაინი
	             {
	               //  მარშუტების მართვაზე გადასვლა
	               m.getMyToast(getApplicationContext(), R.string.mainreestriaragaqvt, android.R.drawable.ic_dialog_alert, Toast.LENGTH_LONG);
	    	       Intent ii = new Intent(getApplicationContext(), routemanager.class);
	      	       startActivity(ii);
	             }
	             break;
	         case 2:                  // მარშუტი ჩამოტვირთულია,  აქვს მაინი    
     	         {
     	           
                   Bundle bundle = new Bundle();
    	           bundle.putString("user", C.USER);
    	           bundle.putString("ins_kode",  Integer.toString(C.INSPECTOR_ID));
    	        
    	           Intent ii = new Intent(getApplicationContext(), TARSAktivity.class); 
			   	   ii.putExtras(bundle);
    	  		   startActivity(ii);
	             }
	    	     break;
	         case 0:
    	        {
    	       	  // შეტყობინება იმაზე რომ  არცერთი მარშუტი არააქვს  ჯერ 
    	          m.getMyToast(this, R.string.noreesterdownloads,  android.R.drawable.ic_dialog_alert, Toast.LENGTH_LONG);
	            }
	    	    break;
	    }
	    
	}
	
	  @SuppressWarnings("deprecation")
	@Override
	  public void onBackPressed() {
	  	// TODO Auto-generated method stub
	  	// super.onBackPressed();  es dakomentarebuli unda iyos
		 
		  showDialog(BACK_INT);		  
		  
	  }

	  
	  
	  //   --- d i a l o g i s   g a k e t e b a
	   
     @SuppressWarnings("deprecation")
	 protected Dialog onCreateDialog(int id) {
	 	  switch (id){
     	 	  case BACK_INT:   
	    	      {
	 	        	  AlertDialog.Builder adb = new AlertDialog.Builder(this);
	 	        	  adb.setTitle(R.string.exitTitleText);
	                  adb.setMessage(R.string.exitMessaheText);
                      adb.setIcon(android.R.drawable.ic_dialog_info);
	                  adb.setPositiveButton(R.string.btnYes, myDialogClickListener);
	                  adb.setNegativeButton(R.string.btnNo, myDialogClickListener);
	                  adb.setCancelable(false); 
	                  return adb.create();
	 	          }
     	 	  //case ttt:     
	     }
 	   return super.onCreateDialog(id);
     }
	   
	   // ---- d i a l o g i s   d a m u S a v e b a 
	   
	   OnClickListener myDialogClickListener = new OnClickListener() {
	 	@Override
	 	public void onClick(DialogInterface arg0, int arg1) {
	 		// TODO Auto-generated method stub
	 		switch (arg1) {
	 	         case Dialog.BUTTON_POSITIVE:
	 	        	   finish();
	         		   break;
	              case Dialog.BUTTON_NEGATIVE:
	 	               break;
	 	     }
	 	}
	 };

	  
}
