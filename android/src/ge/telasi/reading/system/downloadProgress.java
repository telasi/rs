package ge.telasi.reading.system;

import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;

public class downloadProgress  extends AsyncTask<Void, Integer, Void>   {
	
	private Activity context;

	
	public downloadProgress(Activity cnt){
		this.context = cnt;
	}
	

	@Override
	protected Void doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		try {
            for (int i = 0; i < 100; i ++ ){
   		    	publishProgress(i);
			    TimeUnit.SECONDS.sleep(1);
            }
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return null;
	}

	
	@Override
	protected void onPostExecute(Void result) {
		if (context instanceof  routedownload) {
			routedownload ma = ( routedownload) context;
			ma.v_myProgressBar.setVisibility(View.INVISIBLE);
		}
		super.onPostExecute(result);
	}

	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		if (context instanceof routedownload) {
			routedownload ma = (routedownload) context;
            ma.v_myProgressBar.setVisibility(View.VISIBLE);  
		}
		super.onPreExecute();
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

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}


	
	
	
	
	
}
