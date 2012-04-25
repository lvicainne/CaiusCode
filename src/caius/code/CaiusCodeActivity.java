package caius.code;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CaiusCodeActivity extends MyActivity {
	private boolean DECODE_MODE = false;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<Code> adapter = new ArrayAdapter<Code>(this,
                    android.R.layout.simple_spinner_item);
        adapter.add(new MorseCode(this.getString(R.string.morseCode)));
        
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        // the destination number
        /*String number = "6508570720";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));*/
        
        

        /*final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ address.getText().toString()});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject.getText());
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailtext.getText());
     Email.this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));*/
        
        findViewById(R.id.leftButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String sourceText = ((EditText) findViewById(R.id.input)).getText().toString();
                Code currentCode = (Code) ((Spinner) findViewById(R.id.spinner1)).getSelectedItem();
                try {
                	String destText;
                	
                    if(DECODE_MODE) {
                    	destText = currentCode.decode(sourceText);
                    } else {
                    	destText = currentCode.encode(sourceText);
                    }
                    
                    ((EditText) findViewById(R.id.output)).setText(destText);
                } catch(Exception e) {
                    
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, e.toString(), duration);
                    toast.show();
                }
            }
        });
        
        
        ((RadioGroup) findViewById(R.id.radioGroup1)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.decodeRadio) {
					setDecodeMode();
				} else {
					setEncodeMode();
				}
				
			}
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    public void setEncodeMode() {
    	this.DECODE_MODE = false;
    	((Button) findViewById(R.id.leftButton)).setText(this.getString(R.string.encode));
    	((TextView) findViewById(R.id.encodeTextView)).setText(this.getString(R.string.encodeText));
    }
    
    public void setDecodeMode() {
    	this.DECODE_MODE = true;
    	((Button) findViewById(R.id.leftButton)).setText(this.getString(R.string.decode));
    	((TextView) findViewById(R.id.encodeTextView)).setText(this.getString(R.string.decodeText));
    }
}