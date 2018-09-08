package binoo.code.kit.src.fragments;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import binoo.code.kit.picture.entertain.mybeautycamera.R;
import binoo.code.kit.src.views.CameraCanvasView;

public final class MainFragment extends Fragment{

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        SurfaceView surfaceView = (SurfaceView) view.findViewById(R.id.surfaceView);
        LinearLayout surfaceLayout = (LinearLayout) view.findViewById(R.id.surfaceLayout);

        ImageView take = (ImageView) view.findViewById(R.id.take);
        ImageView share = (ImageView) view.findViewById(R.id.share);
        ImageView inverse = (ImageView) view.findViewById(R.id.inverse);
        final ImageView filterView = (ImageView) view.findViewById(R.id.filterView);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekBar);

        final CameraCanvasView canvasView = new CameraCanvasView(surfaceView, filterView, Camera.CameraInfo.CAMERA_FACING_FRONT);
        take.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                canvasView.onTouchEvent(event, 0x001);
                return false;
            }
        });
        share.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                canvasView.onTouchEvent(event, 0x002);
                return false;
            }
        });
        inverse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                canvasView.onTouchEvent(event, 0x003);
                return false;
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                filterView.setImageAlpha((int)(progress*1.0f/1000*254));
                // filterView.setColorFilter(Color.argb(Color.alpha((int)(progress*1.0f)/1000*254), Color.red(0xff), Color.green(0x40), Color.blue(0x81)));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        return view;
    }
}
