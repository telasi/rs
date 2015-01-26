package ge.telasi.reading.system;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MyMessages {

     private static final int gravity = 0;

	public void getMyToast(Context c, int myanote1, int mypicture , int duration){
    	Toast t = Toast.makeText(c, myanote1, duration);
    	t.setGravity(gravity, 0 , 0 );
    	t.setDuration(duration);
    	LinearLayout toastView = (LinearLayout)t.getView();
    	ImageView imageNotes = new ImageView(c);
    	imageNotes.setImageResource(mypicture);
    	toastView.addView(imageNotes, 0);
    	t.show(); 
     }
	
}
