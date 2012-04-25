package caius.code;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

	private boolean SEND_ENABLE = false;
	
	private final static int DIALOG_ABOUT = 1;
	private final static int DIALOG_HELP = 2;
	
	private static int CODE_RETOUR = 1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<Code> adapter = new ArrayAdapter<Code>(this,
                    android.R.layout.simple_spinner_item);
        adapter.add(new MorseCode(this.getString(R.string.morseCode)));
        adapter.add(new BinaryCode(this.getString(R.string.binaryCode)));
        
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        
        findViewById(R.id.leftButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String sourceText = ((EditText) findViewById(R.id.input)).getText().toString();
                Code currentCode = (Code) ((Spinner) findViewById(R.id.spinner1)).getSelectedItem();
                try {
                	String destText = "";
                	
                    if(DECODE_MODE) {
                    	destText = currentCode.decode(sourceText);
                    } else {
                    	destText = currentCode.encode(sourceText);
                    }
                    
                    ((EditText) findViewById(R.id.output)).setText(destText);
                    CaiusCodeActivity.this.setSendMessage(true);
                    
                } catch(EmptyStringException e) {
                	CaiusCodeActivity.this.setSendMessage(false);
                    sendErrorMessage(getString(R.string.emptyString));
                    
                } catch(Exception e) {
                	CaiusCodeActivity.this.setSendMessage(false);
                	sendErrorMessage(e.toString());
                	
                }
            }
        });
        
        findViewById(R.id.rightButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//Reset the editText to empty strings
            	((EditText) findViewById(R.id.input)).setText("");
            	((EditText) findViewById(R.id.output)).setText("");
            	CaiusCodeActivity.this.setSendMessage(false);
            }
        });
        
        ((RadioGroup) findViewById(R.id.radioGroup1)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.decodeRadio) {
					CaiusCodeActivity.this.setDecodeMode();
				} else {
					CaiusCodeActivity.this.setEncodeMode();
				}
				
			}
        });
    }
    
    protected void sendErrorMessage(String str) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, str, duration);
        toast.show();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.itemSend:
            /*final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ address.getText().toString()});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject.getText());
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailtext.getText());
         Email.this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));*/
        	Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        	sendIntent.setData(Uri.parse("sms:"));
        	sendIntent.putExtra("sms_body", ((EditText) findViewById(R.id.input)).getText().toString()); 
        	startActivity(sendIntent);
        	break;
        case R.id.about:
        	showDialog(CaiusCodeActivity.DIALOG_ABOUT);
          break;
        case R.id.help:
        	showDialog(CaiusCodeActivity.DIALOG_HELP);
        	break;
        case R.id.itemOptions:
        	startActivityForResult(new Intent(this, MyPreferences.class), CODE_RETOUR);
        	break;
        }
        return super.onOptionsItemSelected(item);
      }
    
    public Dialog onCreateDialog(int id) {
    	AlertDialog retour = null;
		AlertDialog.Builder builder = null;

		switch(id) {
			case DIALOG_ABOUT:
				builder = new AlertDialog.Builder(this)
				.setMessage("A")
				.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
	
					public void onClick(DialogInterface dialog, int which) {
						//Close the dialog
						dialog.dismiss();
					}
				})
				.setIcon(R.drawable.ic_menu_info_details)
				.setTitle(R.string.about);
			
			break;
			case DIALOG_HELP:
				builder = new AlertDialog.Builder(this)
				.setMessage("A")
				.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
	
					public void onClick(DialogInterface dialog, int which) {
						//Close the dialog
						dialog.dismiss();
					}
				})
				.setIcon(R.drawable.ic_menu_help)
				.setTitle(R.string.help);
			break;
    	}
		
		retour = builder.create();
		return retour;
    }
    
    public void setEncodeMode() {
    	this.setSendMessage(false);
    	((Button) findViewById(R.id.leftButton)).setText(this.getString(R.string.encode));
    	((TextView) findViewById(R.id.encodeTextView)).setText(this.getString(R.string.encodeText));
    };
    
    public void setDecodeMode() {
    	this.setSendMessage(true);
    	((Button) findViewById(R.id.leftButton)).setText(this.getString(R.string.decode));
    	((TextView) findViewById(R.id.encodeTextView)).setText(this.getString(R.string.decodeText));
    };
    
    
    
    /**
     * Set the send menu enable (or not)
     * @param enabled
     */
    public void setSendMessage(boolean enabled) {
    	this.SEND_ENABLE = enabled;
    };
    
    public boolean onPrepareOptionsMenu(Menu menu) {
    	menu.findItem(R.id.itemSend).setEnabled(this.SEND_ENABLE);
    	return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if(requestCode == CaiusCodeActivity.CODE_RETOUR) {
		    Toast.makeText(this, R.string.preferencesSaved, Toast.LENGTH_SHORT).show();
		    this.getPreferences();
	    }
    	super.onActivityResult(requestCode, resultCode, data);
    }
    
    private void getPreferences() {
    	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
/*
    	((TextView)findViewById(R.id.tvLogin)).setText("Nom d'utilisateur : " + preferences.getString("login", ""));

    	((TextView)findViewById(R.id.tvPassword)).setText("Mot de passe : " + preferences.getString("password", ""));

    	((TextView)findViewById(R.id.tvRingtone)).setText("Sonnerie : " + preferences.getString("sonnerie", ""));

    	((TextView)findViewById(R.id.tvVibrate)).setText("Vibreur : " + preferences.getBoolean("vibrate", false));
*/
    	}
}