package binoo.code.kit.src.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by junminwoo on 2018.01.20.
 */
public final class CameraCanvasView implements SurfaceHolder.Callback {
    // private CameraCanvasThread camThread;
    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;
    private int facing;
    ImageView filterView;
    Bitmap drawingCache;

    private Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
        }
    };
    private Camera.PictureCallback pictureCallbackRAW = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
        }
    };
    private Camera.PictureCallback pictureCallbackJPEG = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            drawingCache = BitmapFactory.decodeByteArray(data, 0, data.length);
            filterView.buildDrawingCache();
            Bitmap filterViewDrawingCache = filterView.getDrawingCache();
            Log.d("w","("+drawingCache.getWidth()+", "+filterViewDrawingCache.getHeight()+")");
            Log.d("h","("+drawingCache.getWidth()+", "+filterViewDrawingCache.getHeight()+")");

            String fileName = "";
            FileOutputStream fos = null;
            try {
                File f = new File(Environment.getExternalStorageDirectory().toString() + "/data");
                if (!f.exists()) f.mkdirs();
                f = new File(Environment.getExternalStorageDirectory().toString() + "/data/img");
                if (!f.exists()) f.mkdirs();
                fileName = "/data/img/MHB_CG_"+new SimpleDateFormat("yyyyMMddHHMMssSSS", Locale.KOREA).format(new Date())+".jpg";
                fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString()+ fileName);

                drawingCache.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                Log.d("save", fileName);
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos!=null) fos = null;
                System.gc();
            }
        }
    };


    public CameraCanvasView(SurfaceView surfaceView, ImageView filterView, int facing){
        init(surfaceView, filterView, facing);
    }

    public void init(SurfaceView surfaceView, ImageView filterView, int facing){
        this.facing = facing;
        this.filterView = filterView;
        camera = Camera.open((facing == Camera.CameraInfo.CAMERA_FACING_FRONT ?
                Camera.CameraInfo.CAMERA_FACING_FRONT : Camera.CameraInfo.CAMERA_FACING_BACK));
        // Camera.CameraInfo.CAMERA_FACING_FRONT: Camera.CameraInfo.CAMERA_FACING_BACK; open(int 앞/뒤)
        Camera.Parameters parameters = camera.getParameters();
        parameters.setPictureFormat(PixelFormat.JPEG);
        camera.setDisplayOrientation(90);
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        parameters.setPreviewSize(supportedPreviewSizes.get(0).width, supportedPreviewSizes.get(0).height);
        camera.setParameters(parameters);
        this.surfaceView = surfaceView;
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
    }

    public void draw(Canvas canvas){
        surfaceView.draw(canvas);
    }

    public boolean onTouchEvent(MotionEvent motionEvent, int type){
        /*
        * 1. 세퍼트/반전효과/ 흑백/ 명암 등 효과
        * 2. 필터
        * 3. 문양
        * 4. 스티커
        * 5. 얼굴 트래킹
        * 6. 저장
        * 7. 공유
        * 8. 블러
        * 9. 픽셀 보간법
        * 10. 돌아가기
        * */

        /*
        *
        // surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // camera.startSmoothZoom(2); // 확대-축소
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    surfaceLayout.buildDrawingCache();
                    Bitmap drawingCache = surfaceLayout.getDrawingCache();
                    String fileName = "";
                    FileOutputStream fos = null;
                    try {
                        File f = new File(Environment.getExternalStorageDirectory().toString() + "/data");
                        if (!f.exists()) f.mkdirs();
                        f = new File(Environment.getExternalStorageDirectory().toString() + "/data/img");
                        if (!f.exists()) f.mkdirs();
                        fileName = "/data/img/MHB_CG_"+new SimpleDateFormat("yyyyMMddHHMMssSSS", Locale.KOREA).format(new Date())+".jpg";
                        fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString()+ fileName);

                        drawingCache.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.flush();
                        fos.close();
                    } catch (FileNotFoundException e) {
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (fos!=null) fos = null;
                        System.gc();
                    }
                }
                return false;
            }
        });
        카메라 회전
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }
        int result  = (90 - degrees + 360) % 360;
        camera.setDisplayOrientation(result);
        * */

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            switch (type){
                case 0x001: // take a p
                    camera.takePicture(shutterCallback, pictureCallbackRAW, pictureCallbackJPEG);
                    break;
                case 0x002: // share
                    break;
                case 0x003: // inverse
                    facing = (facing == Camera.CameraInfo.CAMERA_FACING_FRONT ?
                            Camera.CameraInfo.CAMERA_FACING_BACK : Camera.CameraInfo.CAMERA_FACING_FRONT);
                    surfaceDestroyed(this.surfaceHolder);
                    surfaceCreated(this.surfaceHolder);
                    break;
                case 0x004:
                    break;
                default:
                    break;
            }
        }
        return true; // AppManager.getS_instance().getState().onTouchEvent(motionEvent);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (camera == null) {
                camera = Camera.open((facing == Camera.CameraInfo.CAMERA_FACING_FRONT ?
                        Camera.CameraInfo.CAMERA_FACING_FRONT : Camera.CameraInfo.CAMERA_FACING_BACK));
                Camera.Parameters parameters = camera.getParameters();
                parameters.setPictureFormat(PixelFormat.JPEG);
                camera.setDisplayOrientation(90);
                List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
                parameters.setPreviewSize(supportedPreviewSizes.get(0).width, supportedPreviewSizes.get(0).height);
                camera.setParameters(parameters);
                camera.setPreviewDisplay(holder);
                camera.startPreview();
            }
        } catch (IOException e) {
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // View 가 존재하지 않을 때
        if (surfaceHolder.getSurface() == null) {
            return;
        }
        // 작업을 위해 잠시 멈춘다
        try {
            camera.stopPreview();
        } catch (Exception e) {
            // 에러가 나더라도 무시한다.
        }
        // 카메라 설정을 다시 한다.
        Camera.Parameters parameters = camera.getParameters();
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        camera.setParameters(parameters);
        // View 를 재생성한다.
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
}