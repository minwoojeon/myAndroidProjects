package binoo.code.kit.src.fragments.card;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import binoo.code.kit.mycallygraphyeasy.R;
import binoo.code.kit.src.manager.AppManager;

/**
 * Created by binoo on 2017-12-28.
 */

public final class FontsColorFragment extends Fragment {

    public static TextView editText;
    SeekBar seekBarAlpha;
    SeekBar seekBarRed;
    SeekBar seekBarGreen;
    SeekBar seekBarBlue;

    private FontsColorFragment.OnFragmentInteractionListener mListener;

    public FontsColorFragment() {
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
        View view = inflater.inflate(R.layout.fragment_layer_card_color, container, false);
        // 폰트 색상을 선택한다.

        editText = (TextView) view.findViewById(R.id.editText);

        seekBarAlpha = (SeekBar) view.findViewById(R.id.seekBarAlpha);
        seekBarRed = (SeekBar) view.findViewById(R.id.seekBarRed);
        seekBarGreen = (SeekBar) view.findViewById(R.id.seekBarGreen);
        seekBarBlue = (SeekBar) view.findViewById(R.id.seekBarBlue);

        seekBarAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int color = Color.argb(
                        progress, seekBarRed.getProgress(), seekBarGreen.getProgress(), seekBarBlue.getProgress()
                );
                AppManager.getInstance().txtItem.put( "fontColor", color+"" );
                editText.setTextColor( color );

                if (FontsStyleFragment.editView != null){
                    FontsStyleFragment.editView.setTextColor( color );
                }
                if (FontsSizeFragment.editText != null){
                    FontsSizeFragment.editText.setTextColor( color );
                }
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
                int color = Color.argb(
                        seekBarAlpha.getProgress(), progress, seekBarGreen.getProgress(), seekBarBlue.getProgress()
                );
                AppManager.getInstance().txtItem.put( "fontColor", color+"" );
                editText.setTextColor( color );

                if (FontsStyleFragment.editView != null){
                    FontsStyleFragment.editView.setTextColor( color );
                }
                if (FontsSizeFragment.editText != null){
                    FontsSizeFragment.editText.setTextColor( color );
                }
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
                int color = Color.argb(
                        seekBarAlpha.getProgress(), seekBarRed.getProgress(), progress, seekBarBlue.getProgress()
                );
                AppManager.getInstance().txtItem.put( "fontColor", color+"" );
                editText.setTextColor( color );

                if (FontsStyleFragment.editView != null){
                    FontsStyleFragment.editView.setTextColor( color );
                }
                if (FontsSizeFragment.editText != null){
                    FontsSizeFragment.editText.setTextColor( color );
                }
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
                int color = Color.argb(
                        seekBarAlpha.getProgress(), seekBarRed.getProgress(), seekBarGreen.getProgress(), progress
                );
                AppManager.getInstance().txtItem.put( "fontColor", color+"" );
                editText.setTextColor( color );

                if (FontsStyleFragment.editView != null){
                    FontsStyleFragment.editView.setTextColor( color );
                }
                if (FontsSizeFragment.editText != null){
                    FontsSizeFragment.editText.setTextColor( color );
                }
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

    @Override
    public void onResume() {
        if (AppManager.getInstance().txtItem.get("content") != null){
            editText.setText( AppManager.getInstance().txtItem.get("content") + "" );
        }
        if (AppManager.getInstance().txtItem.get("fontName") != null){
            editText.setTypeface(Typeface.createFromAsset( this.getContext().getAssets(), AppManager.getInstance().txtItem.get("fontName") + "" ));
        }
        if (AppManager.getInstance().txtItem.get("fontColor") != null){
            editText.setTextColor( Integer.parseInt(AppManager.getInstance().txtItem.get("fontColor") + "") );
            int color = editText.getCurrentTextColor();
            seekBarAlpha.setProgress( Color.alpha(color) );
            seekBarRed.setProgress( Color.red(color) );
            seekBarGreen.setProgress( Color.green(color) );
            seekBarBlue.setProgress( Color.blue(color) );
        }
        if (AppManager.getInstance().txtItem.get("fontSize") != null){
            editText.setTextSize( Integer.parseInt(AppManager.getInstance().txtItem.get("fontSize") + "") );
        }
        super.onResume();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FontsColorFragment.OnFragmentInteractionListener) {
            mListener = (FontsColorFragment.OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
