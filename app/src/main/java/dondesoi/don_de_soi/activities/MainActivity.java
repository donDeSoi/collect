package dondesoi.don_de_soi.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dondesoi.don_de_soi.ressources.ConstantValues;
import dondesoi.don_de_soi.R;
import dondesoi.don_de_soi.ressources.MethodsUtils;
import dondesoi.don_de_soi.ressources.SaveData;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.content_main_activity_menu_drawer, view_stub);
        setTitle(this.getTitle());
        //display info dons
        Bundle extras = getIntent().getExtras();

        List<Integer> piclist = ConstantValues.getpiclist();
        if (extras != null ) {
            int index = extras.getInt(ConstantValues.INDEX_LIST);

            TextView textview = (TextView) findViewById(R.id.textview);
            textview.setTextSize(16);

            //add informations
            List<String> infoList = ConstantValues.getInfoList();
            String infos = infoList.get(index);
            if(! infos.isEmpty()){
                textview.setText(infos);
            }
            //decodeSampledBitmapFromResource("@drawable/plasmadonation",textview.getWidth(),465);
            textview.setCompoundDrawablesWithIntrinsicBounds(0,piclist.get(index),0,0);
        }

        //notifications
        //do not forget IF the notifications have been enabled
        if(SaveData.getPreferences(ConstantValues.NOTIFICATIONS, this, "true").equals("true")){
            String date = SaveData.getPreferences(ConstantValues.DATE, this, ConstantValues.DEFAULT_DATE);
            if(MethodsUtils.compareDate(date)){
                MethodsUtils.makeNotification(this, ConstantValues.APP_NAME, ConstantValues.NOTIFICATION_CONTENT, R.drawable.ic_notification, SearchCenterActivity.class);
                if(Boolean.valueOf(SaveData.getPreferences(ConstantValues.VIBRATE, this, "false"))){
                    MethodsUtils.vibrate(this);
                }
                String ringtoneValue = SaveData.getPreferences("sonnerie", this, "");
                if(!ringtoneValue.isEmpty()){
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            this, Uri.parse(ringtoneValue));
                    ringtone.play();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}