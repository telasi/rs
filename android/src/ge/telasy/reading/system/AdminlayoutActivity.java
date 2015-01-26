package ge.telasy.reading.system;

import ge.telasy.reading.system.R;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdminlayoutActivity extends Activity implements Waiting {
 
	public String user;
	public String ins_kode;
	
	final String LOG_TAG = "my_Admin_log";
	DB db;
	
	

	// XmlPullParser parser = getResources().getXml(R.xml.reestrsdata);

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminlayout);
		
		ManageMyApps.AdminlayoutActivity = this;
		
		user     = getIntent().getExtras().getString("user");
		ins_kode = getIntent().getExtras().getString("ins_kode");
		
	}

	// -----------------------------
	public void import_reestr(View v) {
		// რეესტრის მონაცემების ატვირთვა XML ფაილიდან
		// ატვირთვამდე უნდა მოხდეს შემოწმება, რათა არ მოახდინოს დამუშავებულ
		// რეესტრზე
		// ახალი რეესტრის გადაწერა
		Toast.makeText(this, "არჩელია რეესტრის ატვირთვა", Toast.LENGTH_LONG).show();
		Log.d(LOG_TAG, "მონაცემთ ბაზის ატვირთვის დასაწყისი");

		db = new DB(this);
		db.open();
		Log.d(LOG_TAG, "ბაზა გაიხსნა");
		// სპეციალური ანალიზის შემდეგ რეესტრის ცხრილის(ების) გასუფთავება
		db.clearTableRreestr();                                 

		// აქ მოხდეს XML-ის პარსირება და მონაცემთა ბაზაში ატვირთვა
		String tag_name = "";
		String v_name = "", v_endTag = "", v_rownum = "", v_address = "", v_accnumb = "", v_account = "";
		String v_mricxvnumb = "", v_modeliskodi = "", v_brjenisnumb = "", v_isdisconected = "", v_araqvsmricxv = "";
		String v_koeficienti = "", v_winachveneba = "", v_winachvenebistar = "", v_min_xarji = "", v_max_xarji = "";

		// String DATE_FORMAT = "dd-mm-yyyy";
		// java.text.SimpleDateFormat sdf = new
		// java.text.SimpleDateFormat(DATE_FORMAT);

		v_winachvenebistar = "1";

		try {
			XmlPullParser parser = getResources().getXml(R.xml.reestrsdata);
			while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
				switch (parser.getEventType()) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG: {
					tag_name = parser.getName();
					break;
				}
				case XmlPullParser.END_TAG: {
					v_endTag = parser.getName();
					break;
				}
				case XmlPullParser.TEXT: {
					// Log.d(LOG_TAG, " ტექსტის გამოტნა" + parser.getText() +
					// " tag_name =  " + tag_name );
					if (tag_name.equals("name")) {
						v_name = parser.getText();
						break;
					} else if (tag_name.equals("rownum")) {
						v_rownum = parser.getText();
						break;
					} else if (tag_name.equals("address")) {
						v_address = parser.getText();
						break;
					} else if (tag_name.equals("accnumb")) {
						v_accnumb = parser.getText();
						break;
					} else if (tag_name.equals("acccount")) {
						v_account = parser.getText();
						break;
					} else if (tag_name.equals("mricxvnumb")) {
						v_mricxvnumb = parser.getText();
						break;
					} else if (tag_name.equals("modeliskodi")) {
						v_modeliskodi = parser.getText();
						break;
					} else if (tag_name.equals("brjenisnumb")) {
						v_brjenisnumb = parser.getText();
						break;
					} else if (tag_name.equals("isdisconected")) {
						v_isdisconected = parser.getText();
						break;
					} else if (tag_name.equals("araqvsmricxv")) {
						v_araqvsmricxv = parser.getText();
						break;
					} else if (tag_name.equals("koeficienti")) {
						v_koeficienti = parser.getText();
						break;
					} else if (tag_name.equals("winachveneba")) {
						v_winachveneba = parser.getText();
						break;
					} else if (tag_name.equals("min_xarji")) {
						v_min_xarji = parser.getText();
						break;
					} else if (tag_name.equals("max_xarji")) {
						v_max_xarji = parser.getText();
						break;
					} else if (tag_name.equals("winachvenebistar")) {
						v_winachvenebistar = parser.getText();
						break;
					} else if (tag_name.equals("axalichveneba")) {
						break;
					} else if (tag_name.equals("note")) {
						break;
					}
					;
					break;
				}
				default:
					break;
				}

				// ამის მერე დაიწყოს მონაცემთა ბაზაში სტრიქონის ჩასმა
				if (v_endTag.equals("row")) {
//					db.addRec(Integer.parseInt(v_rownum), v_address, v_accnumb, v_name, v_account, v_mricxvnumb, v_modeliskodi, v_brjenisnumb, v_isdisconected, v_araqvsmricxv, Integer.parseInt(v_koeficienti), Double.parseDouble(v_winachveneba), 1, // Integer.parseInt(v_winachvenebistar)
//					    Double.parseDouble(v_min_xarji), Double.parseDouble(v_max_xarji), 1, 1,1,"1:,"1","1" // aq
//					);
					v_mricxvnumb = v_koeficienti = v_winachveneba = v_modeliskodi = v_account = v_accnumb = v_address = v_rownum = v_name = "";
					v_araqvsmricxv = v_brjenisnumb = "";
					v_winachvenebistar = v_isdisconected = v_endTag = v_min_xarji = v_max_xarji = "";
				}
				;

				parser.next();

			}
			;
		} catch (Throwable t) {
			Toast.makeText(this, "შეცდომა დაფიქსირდა  XML-საბუის ატვირთვი  დროს: " + t.toString(), 4000).show();
		}
		

		Log.d(LOG_TAG, "მონაცემების ატვირთვა დასრულდა");
		// აქ მოდება დასასრული
		// მოხდება საკონტროლო შედეგების გამოტანა
		db.close();
		Log.d(LOG_TAG, "ბაზა დაიხურა");
	}

	// -------------------------------
	public void export_reestr(View v) {
		Intent ii = new Intent(getApplicationContext(), routemanager.class);
		startActivity(ii);
	}

	// ------------------------------
	public void make_users(View v) throws InterruptedException {
		// მომხმარებლის ადმინისტრირება
		// ახალი მომხმარებლის შექმნა, არსებულზე პაროლის შეცლა
		// ან არსებული მომხმარებლის გაუქმება
		Toast.makeText(this, "არჩეულია სისტემის მომხმარებლის \n ადმინისტრირება", Toast.LENGTH_LONG).show();
		
		// sms-ის გაგზავნა პროგრამულად
		
//		SmsManager sms = SmsManager.getDefault();
//		for (int i = 1; i <= 20; i++) {	
//   		sms.sendTextMessage("551212911", null, "Privet", null, null);
//      		Thread.sleep(15000);
//		}
	//	db.clearTableUsers();   // droebitia mere iyos layouti da sruli menegmenti
	//	db.close();
		
	 	Intent ii = new Intent(getApplicationContext(), UserManager.class);
		startActivity(ii);
		
	}

	// ------------------------------
	public void back_to_admin(View v) {
		// ადმინისტრირების ფანჯარაში დაბრუნება
		// Toast.makeText(this, "არჩეულია უკან დაბრუნება",
		// Toast.LENGTH_LONG).show();
 
		finish();
  	
	}

	public void go_to_reestr(View v) {
		// რეესტრზე გადასვლა
		Intent ii = new Intent(getApplicationContext(), TARSAktivity.class);
		startActivity(ii);
	}

	// -------------------------------------------------------------------------------

	@Override
	public void setWait(boolean wait) {
	 Button btonImpWeb = (Button) findViewById(R.id.b_import_from_web);
	 Button btonGoReestr = (Button) findViewById(R.id.b_go_to_reestr);
	 Button btonExport = (Button) findViewById(R.id.b_export);
	 Button btonImport = (Button) findViewById(R.id.b_import);
	 Button btonAdminPass = (Button) findViewById(R.id.b_admin_pass);
	 Button bton_back = (Button) findViewById(R.id.b_back);
	 
	 
		btonImpWeb.setEnabled(!wait);
		btonGoReestr.setEnabled(!wait);
		btonExport.setEnabled(!wait);
		btonImport.setEnabled(!wait);
		btonAdminPass.setEnabled(!wait);
		bton_back.setEnabled(!wait);
		
	}
	
	public void import_reestr_from_web(View v) {
		//C.loadReester(this, C.ROUTE_ID);
		
	  
    Bundle bundle = new Bundle();
    bundle.putString("user", user);
    bundle.putString("ins_kode", ins_kode);
		
		Intent ii = new Intent(getApplicationContext(), routedownload.class);
		ii.putExtras(bundle);
		startActivity(ii);
		
	}
	
}
