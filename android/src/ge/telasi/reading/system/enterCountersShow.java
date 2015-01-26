package ge.telasi.reading.system;

import ge.telasy.reading.system.R;
import telasi.android.reading.model.ReesterItem;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class enterCountersShow extends Activity {
	private static class Notes {
		int id;
		String name;
		public Notes(int id, String name) {
	    setId(id);
	    setName(name);
    }
		
		public int getId() {
	    return id;
    }
		public void setId(int id) {
	    this.id = id;
    }
		public String getName() {
	    return name;
    }
		public void setName(String name) {
	    this.name = name;
    }
		@Override
		public String toString() {
		  return name;
		}
	}
	
	
    private EditText name;  
    private Long mRowId;  
    private EditText t_AXALICHVENEBA;
    private EditText t_modeliskodinw;
    private EditText t_brjenisnumbnw;
    private EditText t_koeficientinw;
    
    private  double d_cur_read;
    private  double d_min_xarji;
    private  double d_max_xarji;
    private  double d_wina_chveneba;
    private  double d_koeficienti;
    private  int i_digits;
    
     String  V_id;
     String  v_name;
     String  v_address;
     String  v_accnumb;
     String  v_account;
     String  v_mricxvnumb;
     String  v_modeliskodi;
     String  v_brjennumb;
     String  v_isdisconected;
     String  v_armricxv;
     String  v_koeficienti;
     String  v_winacveneba;
     String  v_winaCvenebisTar;
     String  v_AXALICHVENEBA;
     String  v_cur_read;
     String  V_COLUMN_NOTE;
     int     V_COLUMN_NOTE_int;
     String  mRowId1;
     
     String  rid_status;
     String  v_min_xarji;
     String  v_max_xarji;
     String  v_reading_status;
     String  v_wina_chveneba;
     String  v_digits;
     
     String v_modelkodID;
     String v_brjID;
     String v_koefID;
     
     private String choised_note; 
     
     
     
     int posit;
     int choised_item;
     Spinner mySpinner;
     
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.counterseditor);
        
        ManageMyApps.enterCountersShow = this;
        
        v_AXALICHVENEBA = getIntent().getStringExtra(DB.COLUMN_AXALICHVENEBA);
        if  (v_AXALICHVENEBA.equals("0")) {
        	v_AXALICHVENEBA = "";
        }
        	
        t_AXALICHVENEBA    = (EditText) findViewById(R.id.vnAxaliChveneba);
        t_AXALICHVENEBA.setText(v_AXALICHVENEBA);
        v_address = getIntent().getStringExtra(DB.COLUMN_ADDESS);
        TextView  t_address = (TextView)findViewById(R.id.vnAddress);
        t_address.setText(v_address);

        v_name  = getIntent().getStringExtra(DB.COLUMN_NAME);
        TextView  t_name  = (TextView)findViewById(R.id.vnName);
        t_name.setText(v_name); 

        v_accnumb = getIntent().getStringExtra(DB.COLUMN_ACCNUMB);
        TextView t_accnumb = (TextView)findViewById(R.id.vnAccNumb);
        t_accnumb.setText(v_accnumb);

        v_account = getIntent().getStringExtra(DB.COLUMN_ACCOUNT);
        TextView  t_account  = (TextView)findViewById(R.id.vnAngNumb);
        t_account.setText(v_account);

        v_mricxvnumb = getIntent().getStringExtra(DB.COLUMN_MRICXVNUMB);
        TextView  t_mricxvnumb       = (TextView)findViewById(R.id.vnMricxvNumb);
        t_mricxvnumb.setText(v_mricxvnumb);
        
        v_modeliskodi = getIntent().getStringExtra(DB.COLUMN_MODELISKODI);
        TextView  t_modeliskodi   = (TextView)findViewById(R.id.vnModelKod);
        t_modeliskodi.setText(v_modeliskodi);
        
        v_brjennumb = getIntent().getStringExtra(DB.COLUMN_BRJENISNUMB);
        TextView  t_brjennumb    = (TextView)findViewById(R.id.vnBrdgLuk); 
        t_brjennumb.setText(v_brjennumb);
        
        v_isdisconected = getIntent().getStringExtra(DB.COLUMN_ISDISCONECTED);
        TextView  t_isdisconected    = (TextView)findViewById(R.id.vnIsDisconected);
        t_isdisconected.setText(v_isdisconected) ;
        
        v_armricxv = getIntent().getStringExtra(DB.COLUMN_ARACVSMRICXVELI);
        TextView  t_armricxv         = (TextView)findViewById(R.id.vnAraacvsMricxveli);
        t_armricxv.setText(v_armricxv);
        
        v_koeficienti = getIntent().getStringExtra(DB.COLUMN_KOEFICIENTI);
        TextView  t_koeficienti  = (TextView)findViewById(R.id.vnKoefocienti);  
        t_koeficienti.setText(v_koeficienti);
        
        v_winacveneba = getIntent().getStringExtra(DB.COLUMN_WINACHVENEBA);
        TextView  t_winacveneba      = (TextView)findViewById(R.id.vnWinaChveneba);
        t_winacveneba.setText(v_winacveneba);
        
        v_winaCvenebisTar = getIntent().getStringExtra(DB.COLUMN_WINACHVENEBISTAR);
        TextView  t_winaCvenebisTar  = (TextView)findViewById(R.id.vnWinaChvenebisTar);
        t_winaCvenebisTar.setText(v_winaCvenebisTar);
        
//        v_winaCvenebisTar = getIntent().getStringExtra(DB.COLUMN_WINACHVENEBA);
//        TextView  t_winaCveneba  = (TextView)findViewById(R.id.vnWinaChveneba);
//        t_winaCveneba.setText(v_winaCvenebisTar);
        
         
    //    V_COLUMN_NOTE = getIntent().getStringExtra(DB.COLUMN_NOTE) ;       ?????
        
        choised_note = getIntent().getStringExtra(DB.COLUMN_NOTE) ;
        
        V_COLUMN_NOTE = getIntent().getStringExtra(DB.COLUMN_BILL_NOTE) ;
        //                          S P I N N E R
        mySpinner = MakeMySpinner();
        if (V_COLUMN_NOTE == null) {        	
        	V_COLUMN_NOTE = "";        	
        }
        if (V_COLUMN_NOTE.isEmpty() | V_COLUMN_NOTE.equals("")){
        	mySpinner.setSelection(0);      
        }
        else{
        	V_COLUMN_NOTE_int = Integer.parseInt(V_COLUMN_NOTE) ;
        	mySpinner.setSelection(V_COLUMN_NOTE_int);
        }
        //  მონაცემები ჩვენების კონტროლის შეტანისატვის
        v_min_xarji = getIntent().getStringExtra(DB.COLUMN_MIN_XARJI);
        TextView  t_min_xarji      = (TextView)findViewById(R.id.vnMin_xarji);  
        t_min_xarji.setText(v_min_xarji);                                       
        
        
        v_max_xarji = getIntent().getStringExtra(DB.COLUMN_MAX_XARJI);
        TextView  t_max_xarji      = (TextView)findViewById(R.id.vnmax_xarji);  
        t_max_xarji.setText(v_max_xarji);   
        
        v_digits =  getIntent().getStringExtra(DB.COLUMN_DIGITS);
        TextView  t_digits      = (TextView)findViewById(R.id.vnMricxv_tanrigi); 
        t_digits.setText( v_digits);
        
        v_modelkodID = getIntent().getStringExtra(DB.COLUMN_MODELISKODINW);  
        t_modeliskodinw = (EditText)findViewById(R.id.modelkodID);                
        t_modeliskodinw.setText(v_modelkodID); 
        
        v_brjID      = getIntent().getStringExtra(DB.COLUMN_BRJENISNUMBNW);  
        t_brjenisnumbnw  = (EditText)findViewById(R.id.brjID);                   
        t_brjenisnumbnw.setText(v_brjID);
        
        v_koefID     = getIntent().getStringExtra(DB.COLUMN_KOEFICIENTINW);  
        t_koeficientinw = (EditText)findViewById(R.id.koefID);                 
        t_koeficientinw.setText(v_koefID);
        
        v_reading_status = getIntent().getStringExtra(DB.COLUMN_READING_STATYS);
        v_wina_chveneba = getIntent().getStringExtra(DB.COLUMN_WINACHVENEBA);
        
        d_min_xarji =  Double.parseDouble(v_min_xarji); 
        d_max_xarji =  Double.parseDouble(v_max_xarji);
        d_wina_chveneba = Double.parseDouble(v_wina_chveneba);
        d_koeficienti   = Double.parseDouble(v_koeficienti); 
        i_digits        = Integer.parseInt(v_digits);
        mRowId1 = getIntent().getStringExtra(DB.COLUMN_ID);
        
        
        Button confirmButton = (Button) findViewById(R.id.confirm);  
        Button cancel = (Button) findViewById(R.id.cancel); 
        Button MarkNoRead = (Button) findViewById(R.id.MarkNoRead);
   
       // ----------------------------------------------------------- 
       confirmButton.setOnClickListener(new View.OnClickListener() {  
        	   
           public void onClick(View view) {  
               Bundle bundle = new Bundle();  
   
               
               v_cur_read = t_AXALICHVENEBA.getText().toString();  // ახალი ჩვენება უკვეშეიტანა
               
               if (v_cur_read.isEmpty()){
            	   v_cur_read = "0";
               }
               
               d_cur_read = Double.parseDouble(v_cur_read);
                
              if (d_cur_read < 0.0099) {                           //  ფაქტიურად წაკითხვა არ ხდება
            	    rid_status = "6";
            	    Toast.makeText(getApplicationContext(), "თქვენ შეიტანეთ ჩვენება: 0 \n გთხოვთ გადაამოწმოთ", Toast.LENGTH_SHORT).show();
            	    // შეტყობინება და დაფიქსირება როგორც წაკითხულის     უბრალოდ ვერ მივიდა მრიცხველთან
              } else if  (d_cur_read < d_wina_chveneba){       // ციკლი დ ატრიალდა
            	    rid_status = "4";
            	    Toast.makeText(getApplicationContext(), "დაფიქსირდა მრიცხველის ციკლი \n გთხოვთ ფადაამოწმოთ", Toast.LENGTH_SHORT).show();
            	    //  შეტყობინება დიალოგით
              } else {
                      double curent_xarji = ReesterItem.calculateCharge(d_wina_chveneba, d_cur_read, d_koeficienti, i_digits );
                      Toast.makeText(getApplicationContext(), "ხ ა რ ჯ ი  = " + curent_xarji, Toast.LENGTH_SHORT).show();
                      if (curent_xarji < d_min_xarji ) {          // დაფიქსირდა მინიმალურ ხარჯზე ნაკლები ჩვენების შეტანა
                     	    rid_status = "2";
            	            Toast.makeText(getApplicationContext(), "მინიმალურ  ხარჯზე ნაკლები ჩვენება \n გთხოვთ ფადაამოწმოთ", Toast.LENGTH_SHORT).show();
                      } else if (curent_xarji > d_max_xarji ) {          // დაფიქსირდა მოსალოდნელ ხარჯზე მეტი ხარჯი
            	            rid_status = "3";
            	            Toast.makeText(getApplicationContext(), "მაქსიმალურ ხარჯზე მეტი ჩვენება \n გთხოვთ ფადაამოწმოთ", Toast.LENGTH_SHORT).show();
                      } else {                                         // ყვლაფერი წესრიგშია და განისაზღვროს სტატუსი როგორც წკიტხული
            	            rid_status = "1";
                      }
              }
               
              if (v_mricxvnumb == null || v_mricxvnumb.isEmpty()){       // უმრიცველო ჩვენება
            	  Toast.makeText(getApplicationContext(), "უმრიცხველო ჩვენება \n გთხოვთ ფადაამოწმოთ", Toast.LENGTH_SHORT).show();
            	  rid_status = "5";
              }
 
        	  
               bundle.putString(DB.COLUMN_AXALICHVENEBA, v_cur_read );	   
               bundle.putString(DB.COLUMN_READING_STATYS,  rid_status);
               
               bundle.putString(DB.COLUMN_MODELISKODINW, t_modeliskodinw.getText().toString());
               bundle.putString(DB.COLUMN_BRJENISNUMBNW, t_brjenisnumbnw.getText().toString());
               bundle.putString(DB.COLUMN_KOEFICIENTINW, t_koeficientinw.getText().toString());
               bundle.putString(DB.COLUMN_BILL_NOTE, Integer.toString(choised_item));                         
               bundle.putString(DB.COLUMN_NOTE, choised_note);
               
               if (mRowId1 != null) {  
                  // bundle.putLong(DB.COLUMN_ID, mRowId1);
            	   bundle.putString(DB.COLUMN_ID, mRowId1);
               }  
   
               Intent mIntent = new Intent();  
               mIntent.putExtras(bundle);  
               setResult(RESULT_OK, mIntent);  
               finish();  
          }  
   
       });  
       
//   --------------------------C A N C E L ---------------------------------     
       cancel.setOnClickListener(new View.OnClickListener() {  
    	   
           public void onClick(View view) {  
               Bundle bundle = new Bundle();  
               
               v_cur_read = v_AXALICHVENEBA;
               if (v_cur_read.equals("")){
            	   v_cur_read = "0";
               }
               
               bundle.putString(DB.COLUMN_AXALICHVENEBA, v_cur_read );
               rid_status = v_reading_status;  
               bundle.putString(DB.COLUMN_READING_STATYS,  rid_status);
               if (mRowId1 != null) {  
                  // bundle.putLong(DB.COLUMN_ID, mRowId1);
            	   bundle.putString(DB.COLUMN_ID, mRowId1);
               }  
   
               
               bundle.putString(DB.COLUMN_MODELISKODINW, v_modelkodID);
               bundle.putString(DB.COLUMN_BRJENISNUMBNW, v_brjID);          
               bundle.putString(DB.COLUMN_KOEFICIENTINW, v_koefID);             
               bundle.putString(DB.COLUMN_BILL_NOTE,V_COLUMN_NOTE); 
               bundle.putString(DB.COLUMN_NOTE,  choised_note  );                                      

               Intent mIntent = new Intent();  
               mIntent.putExtras(bundle);  
               setResult(RESULT_OK, mIntent);  
               finish();  
          }  
   
       });     

  //   --------------------------W A U K I T X A V I ---------------------------------     
       MarkNoRead.setOnClickListener(new View.OnClickListener() {  
    	   
           public void onClick(View view) {  
               Bundle bundle = new Bundle();  
               
          	   v_cur_read = "0" ; 

               
               bundle.putString(DB.COLUMN_AXALICHVENEBA, v_cur_read );
               rid_status = "0";  
               bundle.putString(DB.COLUMN_READING_STATYS,  rid_status);
               if (mRowId1 != null) {  
                  // bundle.putLong(DB.COLUMN_ID, mRowId1);
            	   bundle.putString(DB.COLUMN_ID, mRowId1);
               }  
   
               Intent mIntent = new Intent();  
               mIntent.putExtras(bundle);  
               setResult(RESULT_OK, mIntent);
               finish();  
          }  
   
       });     
       

       mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
			// TODO Auto-generated method stub
			
			String[] choose = getResources().getStringArray(R.array.Notes4Spinner);   
			choised_item = selectedItemPosition;
			choised_note =  choose[selectedItemPosition];
			Toast toast = Toast.makeText(getApplicationContext(), "ამორჩეულია: " + choose[selectedItemPosition], Toast.LENGTH_LONG);
			toast.show();
		}

		@Override
	    public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		 }
	    });
       
       
    }
    
    public Spinner MakeMySpinner(){
    	final Spinner spinner = (Spinner)findViewById(R.id.vnNote);
    	ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.Notes4Spinner, android.R.layout.simple_spinner_item);
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
    	spinner.setAdapter(adapter);
    	return spinner;
    }
    
    
    
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	// super.onBackPressed();  es dakomentarebuli unda iyos
  	  
    }
    
    
    
    
}
