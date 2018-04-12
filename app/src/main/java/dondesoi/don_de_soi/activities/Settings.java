package dondesoi.don_de_soi.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import dondesoi.don_de_soi.R;
import dondesoi.don_de_soi.fragments.SettingsFragment;
import dondesoi.don_de_soi.ressources.MethodsUtils;

public class Settings extends BaseActivity implements SettingsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_settings);
        getLayoutInflater().inflate(R.layout.activity_settings, view_stub);
        setTitle(this.getTitle());
        String defaultDate = MethodsUtils.getCurrentDatePickerFormat();
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        String date = settings.getString("date", defaultDate);
        System.out.println(date);
        SettingsFragment preferences = SettingsFragment.newInstance(date);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, preferences).commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.preferences:{
                Intent intent = new Intent();
                intent.setClassName(this, "dondesoi.don_de_soi.activities.Settings");
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onFragmentInteraction(String key, String value) {
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key,value);
        editor.commit();
    }
}
