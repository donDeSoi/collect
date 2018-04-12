package dondesoi.don_de_soi.ressources;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dondesoi.don_de_soi.R;
import dondesoi.don_de_soi.activities.SearchCenterActivity;

/**
 * Created by Vitaly on 11/16/2017.
 */

public class MethodsUtils {

    public static boolean containsIgnoreCase(String src, String what) {
        final int length = what.length();
        if (length == 0)
            return true; // Empty string is contained

        final char firstLo = Character.toLowerCase(what.charAt(0));
        final char firstUp = Character.toUpperCase(what.charAt(0));

        for (int i = src.length() - length; i >= 0; i--) {
            // Quick check before calling the more expensive regionMatches() method:
            final char ch = src.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;

            if (src.regionMatches(true, i, what, 0, length))
                return true;
        }

        return false;
    }

    public static int countValidAddresses(List<Center> centerList){
        int numValidCenter = 0;
        for(Center center : centerList){
            if(center.getLatLng() != null){
                numValidCenter++;
            }
        }
        return numValidCenter;
    }

    public static int getCurrentYear(){
        Calendar rightNow = Calendar.getInstance();
        return rightNow.get(Calendar.YEAR);
    }

    public static int getCurrentMonth(){
        Calendar rightNow = Calendar.getInstance();
        return rightNow.get(Calendar.MONTH);
    }

    public static int getCurrentDay(){
        Calendar rightNow = Calendar.getInstance();
        return rightNow.get(Calendar.DAY_OF_MONTH);
    }

    public static String getCurrentDatePickerFormat(){
        return String.valueOf(getCurrentYear()) + ConstantValues.SEPARATOR +
                getCurrentMonth()+ ConstantValues.SEPARATOR + getCurrentDay();
    }
    public static String getCurrentDate(){
        return String.valueOf(getCurrentYear()) + ConstantValues.SEPARATOR +
                getCurrentMonth()+ ConstantValues.MONTH_OFFSET + ConstantValues.SEPARATOR + getCurrentDay();
    }
    public static void makeNotification(Context context, String title, String content, int icon, Class nextActivity){
        //display notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        //optional : attach an action for example search a blood collect
        Intent resultIntent = new Intent(context, nextActivity);//SearchCenterActivity.class
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(nextActivity);//SearchCenterActivity.class

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        //add options
        mBuilder.setSmallIcon(icon).setContentTitle(title).setContentText(content).setContentIntent(resultPendingIntent);
        //build & send the notification
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // notificationID allows you to update the notification later on.
        int notificationID = 0;
        mNotificationManager.notify(notificationID, mBuilder.build());
    }

    public static boolean compareDate(String date){
        String[] dateArray = date.split(ConstantValues.SEPARATOR);
        int currentYear = MethodsUtils.getCurrentYear(), currentMonth = MethodsUtils.getCurrentMonth(),
                currentDay = MethodsUtils.getCurrentYear(), lastGiveYear = Integer.valueOf(dateArray[0]), lastGiveMonth = Integer.valueOf(dateArray[1]),
                lastGiveDay = Integer.valueOf(dateArray[2]);
        return(lastGiveYear < currentYear || lastGiveYear == currentYear && lastGiveMonth < currentMonth + ConstantValues.DIFF_MONTH_NEW_GIVE
                && lastGiveDay < currentDay );
    }
    public static void vibrate(Context context) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        else{
            //deprecated in API 26
            v.vibrate(500);
        }
    }

    private MethodsUtils(){
        throw new RuntimeException();
    }
}
