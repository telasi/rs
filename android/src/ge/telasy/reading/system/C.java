package ge.telasy.reading.system;

import ge.telasy.reading.system.R;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import telasi.android.reading.model.Config;
import telasi.android.reading.model.Information;
import telasi.android.reading.model.Reading;
import telasi.android.reading.model.ReadingController;
import telasi.android.reading.model.Reester;
import telasi.android.reading.model.ReesterItem;
import telasi.android.reading.model.User;
import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * controleri!
 * 
 * @author Ilia Lomsadze
 * 
 */
public class C {

	static String USER;                  // = "dimitri";
	static String PSWD;                  // = "sami4xuti";
	static int INSPECTOR_ID ;            //=  150;   -- გადმოეცემა რეესტრის ჩამოსატვირთ  ACTIVIT-ში
    static String ROUTE_NUMB;
    static  int progressMaxValues;
    public static int isDownloadRunning = 0;
    
	static final String TRUE = "კი";
	static final String FALSE = "არა";

	static {
		//Config.setHost("service.telasi.ge");   // აქაური ვაი-ფაი  
	    Config.setHost("92.241.77.36");          //ამც ებისათვის
		//Config.setHost("192.168.1.12");       // aqauri Sida qseli kompistvis
		Config.setPort("80");
	}

	public static void setUserNamePassword(String p_username, String p_password){
		USER    =  	p_username;
		PSWD    =   p_password;
	}
	
	public static void loadReester(Activity context, int reesterId) {
		((Waiting) context).setWait(true);
		new ReesterLoadingTask(context).execute(USER, PSWD, String.valueOf(reesterId));
	}

	public static void uploadReester(Activity context, int reesterId, int marshNUMB) {             //  mesame parametri chavamate 2014 09 17
		((Waiting) context).setWait(true);
		new ResterUploadTask(context).execute(USER, PSWD, String.valueOf(reesterId), String.valueOf(marshNUMB) );
	}

	public static void getReesters(Activity context, int page) {
		new GetReestersTask(context).execute(USER, PSWD, String.valueOf(page));
	}

	public static void userLogin(Activity context, String username, String password) {
		new LoginTask(context).execute(username, password);
	}

	private static class ReesterLoadingTask extends AsyncTask<String, Integer, Void> {
		Activity context;
		Reester reester;
		Exception exception;

		public ReesterLoadingTask(Activity context) {
			this.context = context;
		}

		@Override
		protected Void doInBackground(String... params) {
			String username = params[0];
			String password = params[1];
			int routeId = Integer.parseInt(params[2]);

			DB db = new DB(context);
			db.open();

			try {

				Reester reester = ReadingController.getReesterOverHTTP(username, password, routeId);

				//String route_tarigi = reester.getCycleDate().toString();      03-feb-2014   
				SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
				String route_tarigi = String.valueOf(ff.format(reester.getCycleDate()));
				String amc_name = reester.getRegionName();
				int route_name = reester.getRoute();
				String route_nameR = reester.getRouteName();
				String route_rider_name = reester.getInspectorName();
				int routr_sul = reester.getItems().length;
				int reestrid = reester.getId();
				db.addRecINtoTHF(route_tarigi, amc_name, route_nameR, route_name, route_rider_name, routr_sul, reestrid, INSPECTOR_ID);

				HashMap<Integer, String> streets = reester.getStreets();
				for (Integer id : streets.keySet()) {
					String name = streets.get(id);

					db.addRecINtoStreet(id, name, route_name);

				}
				
				for (int i = 0; i < reester.getItems().length; i++) {
					ReesterItem item = reester.getItems()[i];
      		     
					 publishProgress(i);

					int itemId = item.getId();
					int seq = item.getSequence();
					int dig = item.getMeter().getDigits();
					// int acckey = item.getAccount().getAcckey();
					// int custkey = item.getAccount().getCustkey();
					String accnumb = item.getAccount().getAccountNumber();
					String accid = item.getAccount().getAccountID();
					String address = item.getAccount().getAddress().getFullAddress();
					String meterNumber = item.getMeter().getNumber();
					String custName = item.getAccount().getCustomerName();
					String sealNumber = item.getMeter().getSealNumber();
					String cut = item.getAccount().isCut() ? TRUE : FALSE;
					int meterCoeff = item.getMeter().getCoeff();
//					double prevReading = item.getReading().getPreviousReading();
					double prevReading = item.getReading().getPreviousRealReading();
					double axali_chveneba = item.getReading().getReading();
					SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
					String wina_wak_tarigi = String.valueOf(f.format(item.getReading().getPreviousReadingDate()));
					double maxCharge = item.getAccount().getMaxCharge(); // TODO
					double minCharge = item.getAccount().getMinCharge(); // TODO
 
					String meterType = item.getMeter().getType();
					String hasMeter = item.getMeter().isWithout() ? FALSE : TRUE;

					int StreetID = item.getAccount().getAddress().getStreetId();
					String saxlisNUM = item.getAccount().getAddress().getHouse();
					String korpNUM = item.getAccount().getAddress().getBuilding();
					String sadarbazo = item.getAccount().getAddress().getPorch();

					String notes     = item.getReading().getNote();            //  2014-20-05      ""  Sevcvli mere namdvili mniSvnelobit    roca mibrunebs dimitri magis chasmaa    
					Integer reading_stats = item.getReading().getErrorCode();  // es ori dimitrim unda gadmomces  
                    Integer noteID   = item.getReading().getNoteId();          // SeniSvnis kodi
					
					db.addRec(seq, // int P_ROWNUM,
					    address, // String P_ADDESS,
					    accnumb, // String P_ACCNUMB,
					    custName, // String P_NAME,
					    accid, // String P_ACCOUNT,
					    meterNumber, // String P_MRICXVNUMB,
					    meterType, // String P_MODELISKODI,
					    sealNumber, // String P_BRJENISNUMB,
					    cut, // String P_ISDISCONECTED,
					    hasMeter, // String P_ARACVSMRICXVELI,
					    meterCoeff, // int P_KOEFICIENTI,
					    prevReading, // double P_WINACHVENEBA,
					    wina_wak_tarigi, // int P_WINACHVENEBISTAR, //
					    minCharge, // double p_min_xarji,
					    maxCharge, // double p_max_xarji
					    itemId, // int P_item_ID
					    dig, // int p_digits
					    StreetID, // int p_StreetID
					    saxlisNUM, // String P_saxlisNUM
					    korpNUM, // String p_korpNUM
					    sadarbazo, // String P_sadarbazo
					    route_name, // int p_marshNum
					    axali_chveneba,
					    notes,                                                          //2014-20-05
					    noteID,                                                        //2014-20-05
					    reading_stats                                                 //2014-20-05
					);

					this.reester = reester;
				}

			} catch (Exception ex) {
				this.exception = ex;
				ex.printStackTrace();
			} finally {
				db.close();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (reester != null) {
    			super.onPostExecute(result);
				// ILia				
    			if (context instanceof  routedownload) {
	  				routedownload ma = ( routedownload) context;
					ma.v_myProgressBar.setVisibility(View.INVISIBLE);
					C.isDownloadRunning = 0;
				}
    			// ILia
				Toast.makeText(this.context, "რეესტრის მოღებულია  \n რეესტრი ჩამოტვირთვა დასრულდა", Toast.LENGTH_SHORT).show();
				
			} else {
				exception.printStackTrace();
				Toast.makeText(this.context, this.exception.toString(), Toast.LENGTH_SHORT).show();
			}
			((Waiting) context).setWait(false);
		}
		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (context instanceof routedownload) {
				routedownload ma = (routedownload) context;
	            ma.v_myProgressBar.setVisibility(View.VISIBLE);  
				ma.v_myProgressBar.setMax(C.progressMaxValues);
				C.isDownloadRunning = 1;
			}
		}

		
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub

			if (context instanceof routedownload) {
				routedownload ma = (routedownload) context;
				ma.v_myProgressBar.setProgress(values[0]);
			}
			super.onProgressUpdate(values);
		}
	}

	
// ----------------------რ ე ე ს ტ რ ი ს   ა ტ ვ ი რ თ ვ ა ---------------------
	private static class ResterUploadTask extends AsyncTask<String, Integer, Void> {
		Activity context;
		String exception;
		String info;

		public ResterUploadTask(Activity context) {
			this.context = context;
		}

		@Override
		protected Void doInBackground(String... params) {
			String username = params[0];
			String password = params[1];
			int routeId = Integer.parseInt(params[2]);
			int marshNumb1 = Integer.parseInt(params[3]);
			
			
			  String where1 = DB.COLUMN_MARSH_NUM  + "="  +  marshNumb1;                            //davamater   2014  09  - 17
            //               ??????????????????????????????????????????????????????????????????????????????????????????????
			
			
			Log.d("MyLog","-----marsh numb-------->" +marshNumb1);
		     	
					
					
			Cursor c = null;
			DB db = new DB(context);
			db.open();

			try {
				
				 //c = db.getAllData("1=1");                                                     // kursoris Secvla pirobiT   davakomentare    2014  09  - 17    
				c = db.getAllData(where1);  
				
				if (c.moveToFirst()) {
					int axalichvenebaInd = c.getColumnIndex(DB.COLUMN_AXALICHVENEBA);
					int ridstatusInd = c.getColumnIndex(DB.COLUMN_READING_STATYS);                // NEW!
					int noteInd = c.getColumnIndex(DB.COLUMN_NOTE);                               // NEW!
					int noteBillInd = c.getColumnIndex(DB.COLUMN_BILL_NOTE);                      // NEW!  
					int itemidInd = c.getColumnIndex(DB.COLUMN_ITEMID);

					Reester reester = new Reester();
					reester.setId(routeId); // mere SevcvaloT
					do {
						ReesterItem item = new ReesterItem();
						item.setId(c.getInt(itemidInd));
						Reading reading = new Reading();
						reading.setReading(c.getDouble(axalichvenebaInd));
						reading.setErrorCode(c.getInt(ridstatusInd));                           // NEW!      // wakitxvis statusi  wiTeli yviteli .....    kodebi
						reading.setNoteId(c.getInt(noteBillInd));  
						reading.setNote(c.getString(noteInd));                                  // NEW!      // teqstiani Setyobineba  
						
	
						reading.setReadingConfirmed(true);

						item.setReading(reading);
						reester.addItem(item);
					} while (c.moveToNext());

					//   -----------------------------------------------------------------------------------
					
					Information info = ReadingController.sendReesterOverHTTP(reester, username, password);
					this.info = info.getMessage();
				} else {
					this.exception = "რეესტრი ცარიელია ";
				}

			} catch (Exception ex) {
				this.exception = ex.toString();
			} finally {
				if (c != null) {
					c.close();
				}
				db.close();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (info != null) {
               	MyMessages msg = new MyMessages();
            	msg.getMyToast(this.context, R.string.DovloadIsFinished,  android.R.drawable.ic_input_add, Toast.LENGTH_LONG);
			} else {
				Log.i("TEST", exception);
				Toast.makeText(context, exception, Toast.LENGTH_LONG).show();
			}
			super.onPostExecute(result);
			((Waiting) context).setWait(true);
		}
	}

	private static class GetReestersTask extends AsyncTask<String, Integer, Void> {
		Activity context;
		String exception;
		List<Reester> reesters;

		public GetReestersTask(Activity context) {
			this.context = context;
		}

		@Override
		protected Void doInBackground(String... params) {
			String username = params[0];
			String password = params[1];
			int page = Integer.parseInt(params[2]);

			try {
				System.out.println("here-1");
				reesters = ReadingController.getReestersOverHTTP(username, password, INSPECTOR_ID, page);
				System.out.println("here-2");
			} catch (Exception ex) {
				System.out.println("here-3");
				this.exception = ex.toString();
				ex.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (exception != null) {
				Toast.makeText(context, exception, Toast.LENGTH_LONG).show();
			} else {
				((routedownload) context).onReestersReceived(reesters);
			}
			super.onPostExecute(result);
		}
	}

	private static class LoginTask extends AsyncTask<String, Integer, Void> {
		Activity context;
		String exception;
		User user;
		String username;
		String password;

		public LoginTask(Activity context) {
			this.context = context;
		}

		@Override
		protected Void doInBackground(String... params) {
			username = params[0];
			password = params[1];

			try {
				user = ReadingController.login(username, password);
			} catch (Exception ex) {
				this.exception = ex.toString();
				ex.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (exception != null) {
				Toast.makeText(context, exception, Toast.LENGTH_LONG).show();
			} else {
				USER = user.getLogin();
				PSWD = password;
				INSPECTOR_ID = user.getId();
				((login) context).onLoginComplete(user, password);
			}
			super.onPostExecute(result);
		}
	}
	

}
