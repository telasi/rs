package ge.telasy.reading.system;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

public class MyCursorAdapter extends SimpleCursorAdapter {

	final String LOG_TAG ="mylog";	
	
	@SuppressWarnings("deprecation")
  public MyCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		   super(context, layout, c, from, to);
		// TODO Auto-generated constructor stub
	}
	
	
	
    //   ან აქ ჩავსვა სტრიქონის ფერის შეცვლა
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		return super.newView(context, cursor, parent);
	}
	
	 

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		String rdst =  cursor.getString(cursor.getColumnIndex("reading_status"));
		
		//  "0"    ჩვენება ასაღებია და არაფერი ხდება ამ დროს 
		@SuppressWarnings("unused")
        LayoutInflater inflater = LayoutInflater.from(context);
		if (rdst.equals("0")) {                          // საწყისი (ჩვეულებრივი ფერის გამოტანა)
			view.setBackgroundColor(Color.WHITE);
		} else if (rdst.equals("1")) {                                //  ჩვენაბა ღებულია , არაფერია საეჭვო
			view.setBackgroundColor(Color.GREEN);
		}
		else if (rdst.equals("2")) {                           // ჩვენება აღებულია მაგრამ დაფიქსირდა მინიმალური ხარჯის ეჭვი
	      view.setBackgroundColor(Color.CYAN);                  
		}  else if (rdst.equals("3")) {                        // ჩვენება აღებულია მაგრამ დაფიქსირდა მაქსიმალური ხარჯის ეჭვი
			view.setBackgroundColor(Color.YELLOW);	
		}  else if (rdst.equals("4")) {                        // ჩვენება აღებულია მაგრამ დაფიქსირდა ჩვენების ციკლური ბრუნვა
			view.setBackgroundColor(Color.RED); 	
		}  else if (rdst.equals("5")) {                        // ჩვენება აღებულია უმრიცხველოდ
			view.setBackgroundColor(Color.BLUE); 
		}  else if (rdst.equals("6")){
			view.setBackgroundColor(Color.MAGENTA); 	
		}
		
		
		super.bindView(view, context, cursor);
	}
	
	//
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return super.getView(position, convertView, parent);
	}
	
}




