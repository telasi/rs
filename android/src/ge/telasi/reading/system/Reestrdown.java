package ge.telasi.reading.system;

import ge.telasy.reading.system.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import telasi.android.reading.model.Reester;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Reestrdown extends BaseAdapter {

	 Context ctx;
	 LayoutInflater lInflater;
	 List<Reester> objects;
	
	 Reestrdown(Context context, List<Reester> reesters) {
	    ctx = context;
	    objects = reesters;
	    lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  }
	
	 
	@Override
  public int getCount() {
	  return objects.size();
  }

	@Override
  public Object getItem(int arg0) {
	  return objects.get(arg0);
  }

	@Override
  public long getItemId(int position) {
	  return objects.get(position).getId();
  }

	@Override
  public View getView(int position, View convertView, ViewGroup parent) {
	  // TODO Auto-generated method stub
		 View view = convertView;
	    if (view == null) {
	      view = lInflater.inflate(R.layout.routedownloadrow, parent, false);
	    }
	    
   
	    Reester r = objects.get(position);
	    
	    TextView textView = (TextView) view.findViewById(R.id.amcId);
	    textView.setText(r.getRegionName());
	    
	    TextView tV1 = (TextView) view.findViewById(R.id.marshNameId);
	    tV1.setText(String.valueOf(r.getRouteName()));
	    
	    TextView tV2 = (TextView) view.findViewById(R.id.marshNumId);
	    tV2.setText(String.valueOf(r.getRoute()));
	    
	    TextView tV3 = (TextView) view.findViewById(R.id.wamkitxId);
	    tV3.setText(r.getInspectorName());
	    
	    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
	    TextView tV4 = (TextView) view.findViewById(R.id.tarigiId);
	    tV4.setText(String.valueOf(f.format(r.getCycleDate())));
	    
	    TextView tV5 = (TextView) view.findViewById(R.id.statusiId);
	    tV5.setText(String.valueOf(r.getStatus()));
	    
	    TextView tV6 = (TextView) view.findViewById(R.id.wasakitxiId);
	    tV6.setText(String.valueOf(r.getCount()));
	    
	    
	    return view;
	   
  }

}
