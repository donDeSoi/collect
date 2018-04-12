package dondesoi.don_de_soi.fragments;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.RingtonePreference;
import android.preference.SwitchPreference;

import dondesoi.don_de_soi.R;
import dondesoi.don_de_soi.ressources.ConstantValues;
import dondesoi.don_de_soi.ressources.SaveData;
import dondesoi.don_de_soi.widgets.DatePreference;

/**
 * Created by Vitaly on 2/10/2018.
 */

public class SettingsFragment extends PreferenceFragment {

    private OnFragmentInteractionListener mListener;
    private static final String DATE = "";
    private String date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            date = getArguments().getString(DATE);
        }
        addPreferencesFromResource(R.xml.pref);
        setHasOptionsMenu(true);
        final DatePreference dp = (DatePreference) findPreference("datePicker");
        //read saved values
        dp.setText(date);
        dp.setSummary(date);
        //if there are values, setvalue of datePreference
        dp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference,Object newValue) {
                //your code to change values.
                String newDate = (String) newValue;
                String[] dates = ((String) newValue).split("-");
                dates[1] = String.valueOf(Integer.valueOf(dates[1])+1);
                newDate = dates[0]+"-"+dates[1]+"-"+dates[2];
                dp.setSummary(newDate);
                //save selected values
                addInteraction(ConstantValues.DATE, (String) newValue);
                return true;
            }
        });
        final SwitchPreference switchLocation = (SwitchPreference) findPreference("example_switch");
        switchLocation.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference,Object newValue) {
                addInteraction(ConstantValues.LOCALISATION, newValue.toString());
                return true;
            }
        });
        final SwitchPreference switchNotification = (SwitchPreference) findPreference("notifications_new_message");
        switchNotification.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference,Object newValue) {
                addInteraction(ConstantValues.NOTIFICATIONS, newValue.toString());
                return true;
            }
        });

        final SwitchPreference switchVibrate = (SwitchPreference) findPreference("notifications_new_message_vibrate");
        switchVibrate.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference,Object newValue) {
                addInteraction(ConstantValues.VIBRATE, newValue.toString());
                return true;
            }
        });

        final RingtonePreference ringtonePreference = (RingtonePreference) findPreference("notifications_new_message_ringtone");
        ringtonePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                System.out.println("bite");
                return true;
            }
        });

        ringtonePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Ringtone ringtone = RingtoneManager.getRingtone(
                        preference.getContext(), Uri.parse(newValue.toString()));
                addInteraction("sonnerie", newValue.toString());
                System.out.println(newValue.toString());
                if (ringtone == null) {
                    // Clear the summary if there was a lookup error.
                    ringtonePreference.setSummary(null);
                } else {
                    // Set the summary to reflect the new ringtone display
                    // name.
                    String name = ringtone.getTitle(preference.getContext());
                    ringtonePreference.setSummary(name);
                }
                return true;
            }
        });

    }
    public SettingsFragment(){
        //leave it empty
    }

    public static SettingsFragment newInstance(String date){
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(DATE, date);
        //fragment.addInteraction("date", date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }
    /*
    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_settings, container, false);//R.layout.first
        return view;
    }
    */
    public void addInteraction(String key, String value) {
        if (mListener != null) {
            mListener.onFragmentInteraction(key, value);
        }
    }

    @Override public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity. *
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String key, String value);
    }
}
