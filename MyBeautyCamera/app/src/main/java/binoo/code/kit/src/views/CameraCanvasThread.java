package binoo.code.kit.src.views;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by junminwoo on 2018.01.20.
 */
public final class CameraCanvasThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private CameraCanvasView cameraCanvasView;

    private boolean isRun = false;

    public CameraCanvasThread(SurfaceHolder surfaceHolder, CameraCanvasView cameraCanvasView){
        this.cameraCanvasView = cameraCanvasView;
        this.surfaceHolder = surfaceHolder;
    }
    public void setRunning(boolean isRun){
        this.isRun = isRun;
    }
    public void run(){
        Canvas canvas;
        while(isRun){
            canvas = null;
            try{
                // cameraCanvasView.update();
                //서페이스홀더를 통해 서페이스에 접근, 가져옴
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder){
                    cameraCanvasView.draw(canvas);
                }
            }finally {
                if (canvas != null){
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}