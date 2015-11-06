package wifi.example.vnapnic.enabledisablewifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public ConnectedDetector isConnected;
    Button btEnable, btDisable;
    WifiManager wifi;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isConnected =  new ConnectedDetector(getApplicationContext());
        tv = (TextView) findViewById(R.id.isconnection);
        btEnable = (Button) findViewById(R.id.btenable);
        btDisable = (Button) findViewById(R.id.btdisable);


        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if(isConnected.isConnectingToInternet()==true)
        {
            tv.setText("Connected\n"+ isConnected.arrString.get(0));
            btEnable.setEnabled(false);
            btDisable.setEnabled(true);
        }
        else
        {
            btEnable.setEnabled(true);
            btDisable.setEnabled(false);
            tv.setText("Disconnect");
        }


        btEnable.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                wifi.setWifiEnabled(true);
                tv.setText("Connected\n"+ isConnected.arrString.get(0));
                btEnable.setEnabled(false);
                btDisable.setEnabled(true);
            }
        });


        btDisable.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                wifi.setWifiEnabled(false);
                tv.setText("Disconnect");
                btEnable.setEnabled(true);
                btDisable.setEnabled(false);

            }
        });
    }


    public static class ConnectedDetector {

        private Context _context;
        public static ArrayList<String> arrString;

        public ConnectedDetector(Context context){
            this._context = context;
            arrString = new ArrayList<String>();
        }

        public boolean isConnectingToInternet(){
            ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null)
            {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                    {
                        String temp =  "Type name : "+info[i].getTypeName()+"\nType : "+info[i].getType()
                                +"\nReason : "+info[i].getReason();
                        arrString.add(temp);//get infor

                        if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        {
                            return true;
                        }
                    }

            }
            return false;
        }
    }
}
