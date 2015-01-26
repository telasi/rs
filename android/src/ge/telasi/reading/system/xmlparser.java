package ge.telasi.reading.system;

import ge.telasy.reading.system.R;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public class xmlparser extends Activity {

	final String LOG_TAG = "my_Admin_log";
	DB db;

	public void import_reestr() {

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
					// " tag_name =  " +tag_name );
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
						// try{
						// Date myDate = sdf.parse(v_winachvenebistar);
						// } catch (Throwable t) {
						// Toast.makeText(this,
						// "თაღიღის პარსირების დროს დაფიქსირდა შეცდომა: " +
						// t.toString(), 4000).show();
						// }
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
					// db.addRec(
					// Integer.parseInt(v_rownum),
					// v_address,
					// v_accnumb,
					// v_name,
					// v_account,
					// v_mricxvnumb,
					// v_modeliskodi,
					// v_brjenisnumb,
					// v_isdisconected,
					// v_araqvsmricxv,
					// Integer.parseInt(v_koeficienti),
					// Integer.parseInt(v_winachveneba),
					// 1 , // Integer.parseInt(v_winachvenebistar)
					// Double.parseDouble(v_min_xarji),
					// Double.parseDouble(v_max_xarji),
					// 1, 1 // item id, digiti
					// 1,
					// "1",
					// "2",
					// "3"
					// );
					v_mricxvnumb = v_koeficienti = v_winachveneba = v_modeliskodi = v_account = v_accnumb = v_address = v_rownum = v_name = "";
					v_araqvsmricxv = v_brjenisnumb = v_min_xarji = v_max_xarji = "";
					v_winachvenebistar = v_isdisconected = v_endTag = "";
				}
				;

				parser.next();

			}
			;
		} catch (Throwable t) {
			Toast.makeText(
					this,
					"შეცდომა დაფიქსირდა  XML-დოკუმენტის ჩატვირთვის დროს: "
							+ t.toString(), 4000).show();
		}
		;

		Log.d(LOG_TAG, "მონაცემების ატვირთვა დასრულდა");
		// აქ მოდება დასასრული
		// მოხდება საკონტროლო შედეგების გამოტანა
		db.close();
		Log.d(LOG_TAG, "ბაზა დაიხურა");

	}

}
