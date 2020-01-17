package fr.enseeiht.braymond.enseeiht_camera_shot;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/** Utility class for Camera
 * @author Benjamin RAYMOND
 */
public class CameraHelper {
    private CameraHelper() { }

    /** Tells if the camera is available on this device
     * @param context
     * @return Weather the camera is available
     */
    public static boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * Ask for permission to use the camera if needed
     * @param activityContext
     * @return
     */
    public static boolean askForCamera(Activity activityContext) {
        if (ContextCompat.checkSelfPermission(activityContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activityContext, new String[]{Manifest.permission.CAMERA}, 50);
            return true;
        } else {
            return false;
        }
    }

    /** Get the Camera instance
     * @return Camera instance
     */
    public static Camera getInstance(int id) {
        try {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(id, info);
            return Camera.open(id);
        } catch (Exception e) {
            return null;
        }
    }

    /** Get the first found camera on the device
     * @return Camera instance
     */
    public static Camera getInstance() {
        return getInstance(0);
    }

    /** Get the id of the first found camera facing 'facing'
     * @param facing Expected side of the camera
     * @return The id of the camera or '-1' if not found
     */
    public static int getFacingCameraId(int facing) {
        for (int i = 0; i < Camera.getNumberOfCameras(); ++ i) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == facing)
                return i;
        }
        return -1;
    }

    /** Return the camera instance of the first back facing camera found. Null if not found.
     * @return Camera instance
     */
    public static Camera getBackFacingCamera() {
        int id = getFacingCameraId(Camera.CameraInfo.CAMERA_FACING_BACK);
        return id == -1 ? null : getInstance(id);
    }

    /** Return the camera instance of the first back facing camera found. Null if not found.
     * @return Camera instance
     */
    public static Camera getFrontFacingCamera() {
        int id = getFacingCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT);
        return id == -1 ? null : getInstance(id);
    }
}
