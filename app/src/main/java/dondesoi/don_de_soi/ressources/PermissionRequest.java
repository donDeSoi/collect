package dondesoi.don_de_soi.ressources;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import java.util.List;

/**
 * Created by Camille Armingaud on 3/2/2018.
 *
 * Class used to request permissions
 *
 * Do not forget to include in the activity this code :
 @Override
 public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

 if (grantResults.length > 0
 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
 Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
 } else {
 Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
 }
 }
 */

//note : use lists of permissions and flags
public class PermissionRequest {
    private Context context;
    private Activity activity;

    public PermissionRequest(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    public boolean permissionNeeded(String permission){
        return ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED;
    }
    private boolean permissionRationnale(String permission){
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    private void showExplanation(String title, String message, final String permission, final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                requestPermission(permission, permissionRequestCode);
            }
        }).create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(activity,
                new String[]{permissionName}, permissionRequestCode);
    }

    /**
     * Prompt the user for permissions if needed
     * @param permission : list of permissions (Manifest.permission.ACCESS_COARSE_LOCATION for example)
     * @param permissionFlag : list of permissionFlags, must be initialized to final int REQUEST_PERMISSION_COARSE_LOCATION = 1;
     *                       the two lists MUST be the same size
     */
    public void promptPermissionIfNeeded(List<String> permission, List<Integer> permissionFlag){
        if(permission.size() == permissionFlag.size()){
            for(int index = 0; index < permission.size(); index ++){
                if(permissionNeeded(permission.get(index))){
                    promptPermission(permission.get(index), permissionFlag.get(index));
                }
            }
        }
    }
    public void promptPermission(String permission, int permissionFlag){
        if(permissionRationnale(permission)){
            showExplanation("Permission Needed", "Rationale", permission, permissionFlag);
        }else{
            requestPermission(permission, permissionFlag);
        }
        return;
    }
}
