package binoo.code.kit.src.fragments.card;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import binoo.code.kit.mycallygraphyeasy.R;
import binoo.code.kit.src.manager.AppGlobalData;
import binoo.code.kit.src.manager.AppManager;

/**
 * Created by binoo on 2017-12-29.
 */

public final class PaintColorCardFragment extends Fragment {

    public PaintColorCardFragment() {
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
        View view = inflater.inflate(R.layout.fragment_layer_card_color_img, container, false);
        final ImageView imgView = (ImageView) view.findViewById(R.id.imgView);
        final SeekBar seekBarAlpha = (SeekBar) view.findViewById(R.id.seekBarAlpha);
        final SeekBar seekBarRed = (SeekBar) view.findViewById(R.id.seekBarRed);
        final SeekBar seekBarGreen = (SeekBar) view.findViewById(R.id.seekBarGreen);
        final SeekBar seekBarBlue = (SeekBar) view.findViewById(R.id.seekBarBlue);
        final Button btnProccess = (Button) view.findViewById(R.id.btnProccess);
        imgView.setColorFilter(Color.argb( seekBarAlpha.getProgress(), seekBarRed.getProgress(), seekBarGreen.getProgress(), seekBarBlue.getProgress() ) );
        Log.d("inColor","welldone");

        seekBarAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                imgView.setColorFilter(Color.argb( progress, seekBarRed.getProgress(), seekBarGreen.getProgress(), seekBarBlue.getProgress() ) );
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        seekBarRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                imgView.setColorFilter(Color.argb( seekBarAlpha.getProgress(), progress, seekBarGreen.getProgress(), seekBarBlue.getProgress() ) );
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        seekBarGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                imgView.setColorFilter(Color.argb( seekBarAlpha.getProgress(), seekBarRed.getProgress(), progress, seekBarBlue.getProgress() ) );
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        seekBarBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                imgView.setColorFilter(Color.argb( seekBarAlpha.getProgress(), seekBarRed.getProgress(), seekBarGreen.getProgress(), progress ) );
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        btnProccess.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    View view = AppManager.getInstance().getView( AppGlobalData.VIEW_TAG_IMAGEVIEW );
                    if (view != null && view instanceof ImageView){
                        ((ImageView) view).setColorFilter(Color.argb( seekBarAlpha.getProgress(), seekBarRed.getProgress(), seekBarGreen.getProgress(), seekBarBlue.getProgress() ) );
                    }
                    AppManager.getInstance().getView( AppGlobalData.VIEW_TAG_SHOW_VIEW ).setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });
        System.gc();
        return view;
    }
}