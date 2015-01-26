package ge.telasy.reading.system;

import ge.telasy.reading.system.R;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class UserManager extends Activity  {
	
	final String LOG_TAG ="mylog";
	
	private static final int CM_DELETE_ID = 1;
	private static final int CM_UPDATE_ID = 3;
	
	DB db;
	Cursor cursor;
	ListView  lvMain;
	SimpleCursorAdapter userAdapter;
	
	
	/** Called when the activity is first created. */
	@SuppressWarnings("deprecation")
  @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manageusers);

	
		
		db = new DB(this);
		db.open();

		cursor = db.getUsers(null);
		startManagingCursor(cursor);
		
		String[] from = new String[] {DB.COLUMN_NAME, DB.COLUMN_PASSWORD , DB.COLUMN_R_MARSH_NUM, };
		int[]    to   = new int[]    {R.id.user_name, R.id.user_passwd, R.id.user_id}; 
		
		userAdapter = new SimpleCursorAdapter(this, R.layout.manageuserrow, cursor, from, to );
		
		lvMain = (ListView)findViewById(R.id.lvMain);
	
		lvMain.setAdapter(userAdapter);
		
		registerForContextMenu(lvMain);
		
	}
	
	
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, CM_DELETE_ID, 0, R.string.delere_record);
		menu.add(0, CM_UPDATE_ID, 0, R.string.update_record);
		
	}
	
	
	@SuppressWarnings("deprecation")
  public boolean onContextItemSelected(MenuItem item){
		if (item.getItemId()==CM_DELETE_ID){
			
			AdapterContextMenuInfo acmi = (AdapterContextMenuInfo ) item.getMenuInfo();
			db.delete_user(acmi.id);
			cursor.requery();
		}	
  		return false;
	}
	
	
	//ახალი მომხმარებლის დამატება
	@SuppressWarnings("deprecation")
  public void add_user(View v) {
		
		String login_intent ;
		String pin_code_intent;
		String insp_kod_intent;
		
		
	   TextView t_login = (TextView) findViewById(R.id.user_name1);
	   login_intent = t_login.getText().toString();

	   TextView t_pin_code = (TextView) findViewById(R.id.user_password1);	
	   pin_code_intent = t_pin_code.getText().toString();
	
	   TextView t_insp_kod_intent = (TextView) findViewById(R.id.user_insp_id1);
	   insp_kod_intent = t_insp_kod_intent.getText().toString(); 
	   
		
		db.addRecIntoUsers(login_intent, pin_code_intent, insp_kod_intent);
		cursor.requery();
		
	}
	
	
	
	@SuppressWarnings("deprecation")
  protected void onDestroy(){
		stopManagingCursor(cursor);
		cursor.close();
		db.close();
		
	}

}
