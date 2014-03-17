package com.sej.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

public class CameraActivity extends Activity {
    private Camera mCamera;
    private CameraPreview mPreview;
    private ActionBar actBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Create an instance of Camera
        mCamera = getCameraInstance(1);
        /*int numCameras = Camera.getNumberOfCameras();

        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();

        for (int i = 0; i < numCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            int facing = cameraInfo.facing;
            int orientation = cameraInfo.orientation;
        }

        Camera.Parameters params = mCamera.getParameters();*/

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        /*getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        actBar = getActionBar();
        actBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
        actBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#550000ff")));*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_capture) {
            mCamera.takePicture(null, null, mPreview.getmPicture());
            SystemClock.sleep(1000);
            mCamera.stopPreview();
            mCamera.startPreview();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /** A safe way to get an instance of the Camera object. */
    public Camera getCameraInstance(){
        return getCameraInstance(0);
    }

    /** A safe way to get an instance of the Camera object. */
    public Camera getCameraInstance(int camId){
        Camera c = null;
        try {
            releaseCamera();
            c = Camera.open(camId); // attempt to get a Camera instance
        }
        catch (Exception e){
            Log.e("CameraActivity", "Could not get the camera: " + e.getMessage());
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();              // release the camera immediately on pause event
    }

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }
}
