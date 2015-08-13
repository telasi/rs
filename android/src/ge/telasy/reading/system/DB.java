package ge.telasy.reading.system;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB {

	final String LOG_TAG = "mylog";

	private static final String DB_NAME = "reestrDB";
	private static final int DB_VERSION = 24;
	private static final String DB_TABLE = "reestrs";
	private static final String DB_TABLE_HF = "thf";
	private static final String DB_TABLE_STREET = "streets";
	private static final String DB_TABLE_OUT_OF_REESTR = "out_of_reester";
	private static final String DB_TABLE_USERS = "users";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_ROWNUM = "rownum";
	public static final String COLUMN_ADDESS = "address";
	public static final String COLUMN_ACCNUMB = "accnumb";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_ACCOUNT = "account";
	public static final String COLUMN_MRICXVNUMB = "mricxvnumb";
	public static final String COLUMN_MODELISKODI = "modeliskodi";
	public static final String COLUMN_BRJENISNUMB = "brjenisnumb";
	public static final String COLUMN_ISDISCONECTED = "isdisconected";
	public static final String COLUMN_ARACVSMRICXVELI = "araqvsmricxv";
	public static final String COLUMN_KOEFICIENTI = "koeficienti";
	public static final String COLUMN_WINACHVENEBA = "winachveneba";
	public static final String COLUMN_WINACHVENEBISTAR = "winachvenebistar";
	public static final String COLUMN_AXALICHVENEBA = "axalichveneba";
	public static final String COLUMN_NOTE = "note";
	public static final String COLUMN_MIN_XARJI = "min_xarji";
	public static final String COLUMN_MAX_XARJI = "max_xarji";
	public static final String COLUMN_READING_STATYS = "reading_status";
	public static final String COLUMN_ITEMID = "itemId";
	public static final String COLUMN_XPOS = "xpos";
	public static final String COLUMN_YPOS = "ypos";
	public static final String COLUMN_DIGITS = "digits";
	public static final String COLUMN_PASSWORD = "passvord";

	public static final String COLUMN_MODELISKODINW = "modeliskodinw";
	public static final String COLUMN_BRJENISNUMBNW = "brjenisnumbnw";
	public static final String COLUMN_KOEFICIENTINW = "koeficientinw";

	public static final String COLUMN_MARSH_NUM = "marsh_num";
	public static final String COLUMN_STREET_ID = "street_id";
	public static final String COLUMN_SAXLIS_NUM = "saxlis_num";
	public static final String COLUMN_KORP_NUM = "korp_num";
	public static final String COLUMN_SADARB_NUM = "sadarb_num";
	public static final String COLUMN_BILL_NOTE = "bill_note";
	public static final String COLUMN_STREET_NAME = "street_name";
	public static final String COLUMN_INSP_ID = "insp_id";

	private static final String DB_CREATE = "create table " + DB_TABLE + "("
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_ROWNUM + " integer, " + COLUMN_ADDESS + " varchar, "
			+ COLUMN_ACCNUMB + " varchar, " + COLUMN_NAME + " varchar, "
			+ COLUMN_ACCOUNT + " varchar, " + COLUMN_MRICXVNUMB + " varchar, "
			+ COLUMN_MODELISKODI + " varchar, " + COLUMN_BRJENISNUMB
			+ " varchar, " + COLUMN_ISDISCONECTED + " varchar, "
			+ COLUMN_ARACVSMRICXVELI + " integer, " + COLUMN_KOEFICIENTI
			+ " integer, " + COLUMN_WINACHVENEBA + " double default 0, "
			+ COLUMN_WINACHVENEBISTAR + " varchar, " + COLUMN_AXALICHVENEBA
			+ " double default 0, " + COLUMN_NOTE + " varchar, "
			+ COLUMN_MIN_XARJI + " double default 0, " + COLUMN_MAX_XARJI
			+ " double default 0, " + COLUMN_READING_STATYS
			+ " integer default 0, " + COLUMN_ITEMID + " integer default 0, "
			+ COLUMN_XPOS + " integer default 0, " + COLUMN_YPOS
			+ " integer default 0, " + COLUMN_DIGITS + " integer, "
			+ COLUMN_MARSH_NUM + " varchar, " + COLUMN_STREET_ID + " integer, "
			+ COLUMN_SAXLIS_NUM + " varchar, " + COLUMN_KORP_NUM + " varchar, "
			+ COLUMN_BILL_NOTE + " varchar, " + COLUMN_SADARB_NUM
			+ " varchar, " + COLUMN_MODELISKODINW + " varchar, "
			+ COLUMN_BRJENISNUMBNW + " varchar, " + COLUMN_KOEFICIENTINW
			+ " integer " + ");";

	public static final String COLUMN_R_TARIGI = "routis_tarigi";
	public static final String COLUMN_R_AMC = "amc_name";
	public static final String COLUMN_R_MARSH_NAME = "marsh_name";
	public static final String COLUMN_R_MARSH_NUM = "marsh_num";
	public static final String COLUMN_R_WAMKITXVELI = "wamkitxveli";
	public static final String COLUMN_R_WASAKITXIA_SUL = "wasakitxia_sul";
	public static final String COLUMN_R_SAECH_WAKITXVA = "saechvo_wakitxva";
	public static final String COLUMN_R_STATUSI = "route_status";
	public static final String COLUMN_R_REESTRID = "reestr_id";

	private static final String DB_CREATE_HF = "create table " + DB_TABLE_HF
			+ "(" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_R_TARIGI + " datatime, " + COLUMN_R_AMC + " varchar,  "
			+ COLUMN_R_MARSH_NAME + " integer,  " + COLUMN_R_MARSH_NUM
			+ " integer,  " + COLUMN_R_WAMKITXVELI + " varchar,  "
			+ COLUMN_R_WASAKITXIA_SUL + " integer,  " + COLUMN_R_SAECH_WAKITXVA
			+ " integer, " + COLUMN_R_STATUSI + " integer default 1, "
			+ COLUMN_R_REESTRID + " integer, " + COLUMN_INSP_ID + " varchar "
			+ ");";

	private static final String DB_CREATE_STREET = "create table "
			+ DB_TABLE_STREET + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_STREET_ID
			+ " integer, " + COLUMN_STREET_NAME + " varchar, "
			+ COLUMN_MARSH_NUM + " varchar " + ");";

	private static final String DB_CREATE_OUT_OF_REESTR = "create table "
			+ DB_TABLE_OUT_OF_REESTR + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_MRICXVNUMB
			+ " varchar, " + COLUMN_AXALICHVENEBA + " double default 0, "
			+ COLUMN_STREET_NAME + " varchar, " + COLUMN_SAXLIS_NUM
			+ " varchar, " + COLUMN_KORP_NUM + " varchar, " + COLUMN_SADARB_NUM
			+ " varchar, " + COLUMN_BILL_NOTE + " varchar " + ");";

	private static final String DB_CREATE_USERS = "create table "
			+ DB_TABLE_USERS + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_NAME
			+ " varchar, " + COLUMN_PASSWORD + " varchar, "
			+ COLUMN_R_MARSH_NUM + " varchar " + ");";

	private final Context mCtx;
	private DBHelper mDBHelper;
	private SQLiteDatabase mDB;

	public void dbStructure() {

		Log.d(LOG_TAG, "-- მონაცემთა ბაზის შექმნის ობიექტი გაეშვა --");

	}

	public DB(Context ctx) {
		mCtx = ctx;
	}

	// открыть подключение
	public void open() {
		// Log.d(LOG_TAG, "-- მონაცემთა ბაზის    dakavSireba --");
		if (mDBHelper == null) {
			mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
			mDB = mDBHelper.getWritableDatabase();
		}
	}

	// закрыть подключение
	public void close() {
		if (mDBHelper != null)
			mDBHelper.close();
	}

	// რეესტრის ცხრილიდან ყველა მონაცემის მიღება
	public Cursor getAllData(String where) {
		return mDB.query(DB_TABLE, null, where, null, null, null, COLUMN_ITEMID); // ???????????????????????????????
																				// vnaxo
																				// zedmeti
																				// informacia
																				// xom
																				// ar
																				// gadamaqvs
	}

	public Cursor getStreets(String where) {
		if (!where.isEmpty()) {
			where = COLUMN_R_MARSH_NUM + " =  " + where;
		}
		return mDB.query(DB_TABLE_STREET, null, where, null, null, null, null);
	}

	public Cursor getHF(String where) {
		// damuSavdes where striqonis miReba
		return mDB.query(DB_TABLE_HF, null, where, null, null, null, null);
	}

	public Cursor getOutOfreester(String where) {
		// damuSavdes where striqonis miReba
		return mDB.query(DB_TABLE_OUT_OF_REESTR, null, where, null, null, null,
				null);
	}

	public Cursor getUsers(String where) {
		return mDB.query(DB_TABLE_USERS, null, where, null, null, null, null);
	}

	// ჩანაწერის დამატება რეესტრის ცხრილში -- ეს ხდება მაშინ როცა დიმიტრისგან
	// გადმომეცემა ინფორმაცია. ანდროიდთან მუშაობის დროს ხდება უპდატე ამ ცხრილისა
	public void addRec(int P_ROWNUM, String P_ADDESS, String P_ACCNUMB,
			String P_NAME, String P_ACCOUNT, String P_MRICXVNUMB,
			String P_MODELISKODI, String P_BRJENISNUMB, String P_ISDISCONECTED,
			String P_ARACVSMRICXVELI, int P_KOEFICIENTI, double P_WINACHVENEBA,
			String P_WINACHVENEBISTAR, double p_min_xarji, double p_max_xarji,
			int P_itemId, int p_digits, int p_StreetID, String P_saxlisNUM,
			String p_korpNUM, String P_sadarbazo, int p_marshNum,
			double p_cinaChveneba, String p_NOTE, // 2014-20-05
			int p_NoteID, // 2014-20-05
			Integer p_ReadingStatus // 2014-20-05
	) {
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_ROWNUM, P_ROWNUM);
		cv.put(COLUMN_ADDESS, P_ADDESS);
		cv.put(COLUMN_ACCNUMB, P_ACCNUMB);
		cv.put(COLUMN_NAME, P_NAME);
		cv.put(COLUMN_ACCOUNT, P_ACCOUNT);
		cv.put(COLUMN_MRICXVNUMB, P_MRICXVNUMB);
		cv.put(COLUMN_MODELISKODI, P_MODELISKODI);
		cv.put(COLUMN_BRJENISNUMB, P_BRJENISNUMB);
		cv.put(COLUMN_ISDISCONECTED, P_ISDISCONECTED);
		cv.put(COLUMN_ARACVSMRICXVELI, P_ARACVSMRICXVELI);
		cv.put(COLUMN_KOEFICIENTI, P_KOEFICIENTI);
		cv.put(COLUMN_WINACHVENEBA, P_WINACHVENEBA);
		cv.put(COLUMN_WINACHVENEBISTAR, P_WINACHVENEBISTAR);
		cv.put(COLUMN_AXALICHVENEBA, p_cinaChveneba);
		cv.put(COLUMN_NOTE, p_NOTE); // 2014-20-05
		cv.put(COLUMN_BILL_NOTE, p_NoteID); // 2014-20-05
		cv.put(COLUMN_MIN_XARJI, p_min_xarji);
		cv.put(COLUMN_MAX_XARJI, p_max_xarji);
		cv.put(COLUMN_ITEMID, P_itemId);
		cv.put(COLUMN_DIGITS, p_digits);
		cv.put(COLUMN_STREET_ID, p_StreetID);
		cv.put(COLUMN_SAXLIS_NUM, P_saxlisNUM);
		cv.put(COLUMN_KORP_NUM, p_korpNUM);
		cv.put(COLUMN_SADARB_NUM, P_sadarbazo);
		cv.put(COLUMN_MARSH_NUM, p_marshNum);
		cv.put(COLUMN_READING_STATYS, p_ReadingStatus); // 2014-20-05

		mDB.insert(DB_TABLE, null, cv);
	}

	// ჩანაწერის დამატება ჰედერისა და ფუტერის ცხრილში
	public void addRecINtoTHF(String P_TARIGI, String P_AMC,
			String P_MARSH_NAME, int P_MARSH_NUM, String P_WAMKITXVELI,
			int P_WASAKITXIA_SUL, int p_routeid, int P_INSP_ID) {

		ContentValues cv = new ContentValues();

		cv.put(COLUMN_R_TARIGI, P_TARIGI);
		cv.put(COLUMN_R_AMC, P_AMC);
		cv.put(COLUMN_R_MARSH_NAME, P_MARSH_NAME);
		cv.put(COLUMN_R_MARSH_NUM, P_MARSH_NUM);
		cv.put(COLUMN_R_WAMKITXVELI, P_WAMKITXVELI);
		cv.put(COLUMN_R_WASAKITXIA_SUL, P_WASAKITXIA_SUL);
		cv.put(COLUMN_R_REESTRID, p_routeid);
		cv.put(COLUMN_INSP_ID, P_INSP_ID);

		mDB.insert(DB_TABLE_HF, null, cv);
	}

	// ჩანაწერის დამატება ქუჩების ცხრილში
	public void addRecINtoStreet(int StreetID, String streetName, int marshNum) {
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_STREET_ID, StreetID);
		cv.put(COLUMN_STREET_NAME, streetName);
		cv.put(COLUMN_MARSH_NUM, marshNum);

		mDB.insert(DB_TABLE_STREET, null, cv);
	}

	// ჩანაწერის დამატება დამატებითი ინფორმაციის ცხრილში
	public void addRecInOutOfRange(String Pmricxvnumb, double Pchveneba,
			String PstreetName, String Psaxl_num, String PKORP_NUM,
			String Psadarb_num, String Pnote) {
		ContentValues cv = new ContentValues();

		cv.put(COLUMN_MRICXVNUMB, Pmricxvnumb);
		cv.put(COLUMN_AXALICHVENEBA, Pchveneba);
		cv.put(COLUMN_STREET_NAME, PstreetName);
		cv.put(COLUMN_SAXLIS_NUM, Psaxl_num);
		cv.put(COLUMN_KORP_NUM, PKORP_NUM);
		cv.put(COLUMN_SADARB_NUM, Psadarb_num);
		cv.put(COLUMN_BILL_NOTE, Pnote);

		mDB.insert(DB_TABLE_OUT_OF_REESTR, null, cv);
	}

	public void addRecIntoUsers(String Pname, String Ppassword, String PmarshNum) {
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_NAME, Pname);
		cv.put(COLUMN_PASSWORD, Ppassword);
		cv.put(COLUMN_R_MARSH_NUM, PmarshNum);
		mDB.insert(DB_TABLE_USERS, null, cv);
	}

	// удалить запись из DB_TABLE
	public void delRec(long id) {
		mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
	}

	public void delRecOnReesterOut(long id) {
		mDB.delete(DB_TABLE_OUT_OF_REESTR, COLUMN_ID + " = " + id, null);
	}

	// რეესტრის ცხრილის გასუთავება

	public void clearTableRreestr() {
		mDB.delete(DB_TABLE, null, null);
	}

	// ჰედერის და ფუტერის ცხრილის გასუთავება
	public void clearTableTHF() {
		mDB.delete(DB_TABLE_HF, null, null);
	}

	// ქუჩების ცხრილის გასუთავება
	public void clearTableStreets() {
		mDB.delete(DB_TABLE_STREET, null, null);
	}

	public void clearTableOutOfReesters() {
		mDB.delete(DB_TABLE_OUT_OF_REESTR, null, null);
	}

	public void clearTableUsers() {
		mDB.delete(DB_TABLE_USERS, null, null);
	}

	// класс по созданию и управлению БД
	private class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

		// создаем БД
		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(DB_CREATE);

			db.execSQL(DB_CREATE_HF);

			db.execSQL(DB_CREATE_STREET);

			db.execSQL(DB_CREATE_OUT_OF_REESTR);

			db.execSQL(DB_CREATE_USERS);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// აქ უნდა დაიწეროს მონაცემთა ბაზის მოდიფიკაცია დაახლოებით ასე

			db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

			db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_HF);

			db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_STREET);

			db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_OUT_OF_REESTR);

			db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_USERS);

			onCreate(db);

		}
	}

	public void updateChveneba(String rowId, String axali_chveneba,
			String rdst, String modelkodenew, String brjenisnumbnew,
			String koeficientinew, String P_note1, String P_note) {
		ContentValues cv = new ContentValues();
		cv.put("axalichveneba", axali_chveneba);
		cv.put("reading_status", rdst);
		cv.put("modeliskodinw", modelkodenew);
		cv.put("brjenisnumbnw", brjenisnumbnew);
		cv.put("koeficientinw", koeficientinew);

		cv.put(DB.COLUMN_BILL_NOTE, P_note1);
		cv.put(DB.COLUMN_NOTE, P_note);

		@SuppressWarnings("unused")
		int updCount = mDB.update(DB_TABLE, cv, COLUMN_ID + "=" + rowId, null);
	}

	// ????????????????????????????????

	public void updateRouteStatus(int statusID, long id) {
		ContentValues cv = new ContentValues();
		cv.put("route_status", statusID);
		@SuppressWarnings("unused")
		int updCount = mDB.update(DB_TABLE_HF, cv, COLUMN_ID + "==" + id, null);
	}

	public int getMainRouteID(int InspId) {
		Cursor cur = getHF(COLUMN_INSP_ID + " = " + InspId + " and  "
				+ COLUMN_R_STATUSI + " = " + 5);
		int marsh_id = 0;
		if (cur.moveToFirst()) {
			marsh_id = cur.getInt(cur.getColumnIndexOrThrow(COLUMN_R_STATUSI));
		}
		cur.close();
		return marsh_id;
	}

	public int getStatusRouteID(int rowID) {
		Cursor cur = getHF(COLUMN_ID + " = " + rowID);
		int marsh_id = 0;
		if (cur.moveToFirst()) {
			marsh_id = cur.getInt(cur.getColumnIndexOrThrow(COLUMN_ID));
		}
		cur.close();
		return marsh_id;
	}

	public void updateOutOfReester(String mricxvnumb, double axali_chveneba,
			String streetName, String saxlis_num, String korp_num,
			String sadarb_num, String note) {
		ContentValues cv = new ContentValues();
		cv.put("axalichveneba", axali_chveneba);
		cv.put("street_name", streetName);
		cv.put("saxlis_num", saxlis_num);
		cv.put("korp_num", korp_num);
		cv.put("sadarb_num", sadarb_num);
		cv.put("bill_note", note);

		@SuppressWarnings("unused")
		int updCount = mDB.update(DB_TABLE_OUT_OF_REESTR, cv, COLUMN_MRICXVNUMB
				+ "=" + mricxvnumb, null);
	}

	public void updateReesterStstus(long rowID) {
		// როუტის გამოცხადება მთავარ როუტად

		int updCount = 0;
		// ამორჩეული მარშუტის სტატუსის დადგენა
		int stat = getReesterStstus(rowID);
		ContentValues cv = new ContentValues();
		// მიმდინარე ინსპექტორისათვის აქტიური (5) იანი სტატუსის მქონე მარუტის
		// დადგენა
		String main_marsh_num = getMainMarshNum(C.INSPECTOR_ID);
		if (main_marsh_num != null) {
			if (stat == 2 | stat == 1) {
				// ინსპექტორს აქვს მაინ მარშუტი გადავიყვანოთ ის არა მაინად
				Cursor cur = getAllData(COLUMN_READING_STATYS + " != 0 "
						+ "  and  " + COLUMN_MARSH_NUM + " = " + main_marsh_num);
				if (cur.getCount() == 0) {
					cv.put("route_status", 1);
				} else {
					cv.put("route_status", 2);
				}
			}
			if (stat == 3 | stat == 4) {
				// მოხდეს მიმდინარე ინსპექტორისათვის მიმდინარე
				cv.put("route_status", 2);
			}
			updCount = mDB.update(DB_TABLE_HF, cv, COLUMN_R_MARSH_NUM + " =  "
					+ main_marsh_num, null);
		}
		cv.clear();
		cv.put(COLUMN_R_STATUSI, 5);
		updCount = mDB.update(DB_TABLE_HF, cv, COLUMN_ID + "=" + rowID, null);
	}

	// ინსპექტორის აქტიურ მარშუტის ნომერს აბრუნებს
	public String getMainMarshNum(int InspId) {
		// TODO Auto-generated method stub
		String marsh_num = null;
		Cursor cur = getHF(COLUMN_INSP_ID + " = " + InspId + " and  "
				+ COLUMN_R_STATUSI + " = " + 5);
		if (cur.moveToFirst()) {
			marsh_num = cur.getString(cur
					.getColumnIndexOrThrow(COLUMN_R_MARSH_NUM));
		}
		cur.close();
		return marsh_num;
	}

	// ინსპექტორის აქტიურ მარშუტის სტრიქონის ნომერს აბრუნება
	public int getMainMarshRowID(int InspId) {
		// TODO Auto-generated method stub
		int RowID = 0;
		Cursor cur = getHF(COLUMN_INSP_ID + " = " + InspId + " and  "
				+ COLUMN_R_STATUSI + " = " + 5);
		if (cur.moveToFirst()) {
			RowID = cur.getInt(cur.getColumnIndexOrThrow(COLUMN_ID));
		}
		cur.close();
		return RowID;
	}

	public int getReesterStstus(long rowId) {

		Cursor cur = getHF(COLUMN_ID + " = " + rowId);
		int k;
		k = 0;
		if (cur.moveToFirst()) {
			k = cur.getInt(cur.getColumnIndexOrThrow(COLUMN_R_STATUSI));
		}
		cur.close();

		return k;
	}

	// რეესტრის დახურვა
	public int closeReester(long rowId) {

		int k = 103;
		int stat = getReesterStstus(rowId);
		String marsh_numb = getMarshNum(rowId);
		if (stat == 2 | stat == 5) {
			// რეესტრი არის დამუშავების მდგომარეობაში, ან მიმდინარეა და ან არა
			Cursor cur = getAllData(DB.COLUMN_READING_STATYS + " == 0    and  "
					+ DB.COLUMN_MARSH_NUM + " == " + marsh_numb);
			if (cur.getCount() == 0) {
				k = 101; // რეესტრი დამუშავების პროცესშია, მისი ყველა ელემენტი
							// არაა დამუშავებული
			} else {
				k = 102; // რეესტრი დამუშავებულია მასში მასში არსებუბს ერთი
							// მაინც დაუმუშავებელი ჩანაწერი
			}
			cur.close();
		}
		if (stat == 1) {
			k = 104; // მარშუტი სრულიად ახალია (ჯობია წაიშალოს)
		}
		return k;
	}

	// -------------------------------------------------

	public int UploadRoute(long rowId) {

		int k = 105;
		int stat = getReesterStstus(rowId);
		String marsh_numb = getMarshNum(rowId);
		if (stat == 2 | stat == 5) {
			// რეესტრი არის დამუშავების მდგომარეობაში, ან მიმდინარეა და ან არა
			Cursor cur = getAllData(DB.COLUMN_READING_STATYS + " == 0    and  "
					+ DB.COLUMN_MARSH_NUM + " == " + marsh_numb);
			if (cur.getCount() == 0) {
				k = 106; // რეესტრი დამუშავების პროცესშია, მისი ყველა ელემენტი
							// დამუშავებულია
			} else {
				k = 107; // რეესტრი დამუშავებულია მასშიი არსებუბს ერთი მაინც
							// დაუმუშავებელი ჩანაწერი
			}
			cur.close();
		}
		if (stat == 1) {
			k = 108; // მარშუტი სრულიად ახალია (ჯობია წაიშალოს)
		}
		if (stat == 3) {
			k = 109;
		}
		return k;
	}

	// -----------------------------------------------------

	public boolean isReesterUploaded(long rowId) {
		int stat = getReesterStstus(rowId);
		if (stat == 3) {
			return true;
		} else {
			return false;
		}

	}

	public void openReester(long rowID) {
		int stat = getReesterStstus(rowID);
		if (stat == 3 | stat == 4) {
			ContentValues cv = new ContentValues();
			cv.put("route_status", 2);
			@SuppressWarnings("unused")
			int updCount = mDB.update(DB_TABLE_HF, cv, COLUMN_ID + "=" + rowID,
					null);

		}
	}

	public void changeReesterStatus(int newStatus, long rowID) {

		ContentValues cv = new ContentValues();
		cv.put("route_status", newStatus);
		@SuppressWarnings("unused")
		int updCount = mDB.update(DB_TABLE_HF, cv, COLUMN_ID + "=" + rowID,
				null);
	}

	public void DeleteReester(long rowID) {

		Cursor cur = getHF(COLUMN_ID + " = " + rowID);
		cur.moveToFirst();
		int marsh_num = cur.getInt(cur
				.getColumnIndexOrThrow(COLUMN_R_MARSH_NUM));

		mDB.delete(DB_TABLE, COLUMN_MARSH_NUM + " = " + marsh_num, null);
		mDB.delete(DB_TABLE_HF, COLUMN_R_MARSH_NUM + " = " + marsh_num, null);
		cur.close();
	}

	// _id გადაეცემა და აბრუნებს მარშუტის ნომერს ეს კარგაია ლისტ ვიუებში
	public String getMarshNum(long rowID) {
		String marsh_num = null;
		Cursor cur = getHF(COLUMN_ID + " = " + rowID);
		if (cur.moveToFirst()) {
			marsh_num = cur.getString(cur
					.getColumnIndexOrThrow(COLUMN_R_MARSH_NUM));
		}
		cur.close();
		return marsh_num;
	}

	// _id გადაეცემა და აბრუნებს რეესტრის ნომერს ეს კარგაია ლისტ ვიუებში
	public int getReesterNum(long rowID) {
		int reester_num = 0;
		Cursor cur = getHF(COLUMN_ID + " = " + rowID);
		if (cur.moveToFirst()) {
			reester_num = cur.getInt(cur
					.getColumnIndexOrThrow(COLUMN_R_REESTRID));
		}
		cur.close();
		return reester_num;
	}

	// მომხმარაბლის წაშლა მონაცმთა ბაზიდან მისი Id - ის მიხედვით
	public void delete_user(long id) {

		mDB.delete(DB_TABLE_USERS, COLUMN_ID + " = " + id, null);

	}

	public boolean isRouteDownloaded(int marshnum) {
		Cursor cur = getHF(COLUMN_R_MARSH_NUM + " = " + marshnum);
		if (cur.getCount() > 0) {
			cur.close();
			return true;
		} else {
			cur.close();
			return false;
		}
	}

	// რეესტრის ფურცელზე შედეგების გამოტანა

	public int getReadingCount(String where) {
		Cursor cursor = getAllData(where);
		int k = cursor.getCount();
		cursor.close();
		return k;
	}

	public String getReadingValues(String exp_values) {
		Cursor cursor = getHF(COLUMN_R_MARSH_NUM + " = " + C.ROUTE_NUMB);
		String InspName = "";
		if (cursor.moveToFirst()) {
			InspName = cursor.getString(cursor
					.getColumnIndexOrThrow(exp_values));
		}
		cursor.close();
		return InspName;
	}

	// რეესტრის ფურცელზე შედეგების გამოტანა (დასასრული)

}
