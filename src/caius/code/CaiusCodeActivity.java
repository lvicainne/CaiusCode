package caius.code;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;

public class CaiusCodeActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        // the destination number
        /*String number = "6508570720";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));*/
        
        

        /*final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ address.getText().toString()});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject.getText());
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailtext.getText());
     Email.this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));*/
    }
}