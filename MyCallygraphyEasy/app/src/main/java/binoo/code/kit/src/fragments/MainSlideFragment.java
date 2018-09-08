package binoo.code.kit.src.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import binoo.code.kit.mycallygraphyeasy.R;
import binoo.code.kit.src.manager.AppGlobalData;
import binoo.code.kit.src.manager.AppManager;

public final class MainSlideFragment extends Fragment {

    private Button btnStart;
    private Button btnHelp;

    private OnFragmentInteractionListener mListener;

    public MainSlideFragment() {
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
        View view = inflater.inflate(R.layout.fragment_main_slide, container, false);

        String fontName = AppManager.getInstance().peekActivity().getResources().getString(R.string.default_font);
        Typeface typeface = Typeface.createFromAsset(AppManager.getInstance().peekActivity().getAssets(), fontName);
        int color = Color.argb( 0xff,0xff,0xff,0xff );
        int colorshadow = Color.argb( 0xef,0x0f,0x0f,0x0f );

        btnStart = (Button) view.findViewById(R.id.btnStart);
        btnHelp = (Button) view.findViewById(R.id.btnHelp);
        btnStart.setTypeface( typeface );
        btnStart.setTextColor( color );
        btnStart.setVisibility( View.VISIBLE );
        btnHelp.setTypeface( typeface );
        btnHelp.setTextColor( color );
        btnHelp.setVisibility( View.VISIBLE );

        btnStart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    AppManager.getInstance().peekActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainLinear, new ChoosePictureFragment()).commit();
                }
                return false;
            }
        });
        btnHelp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    AppManager.getInstance().getView( AppGlobalData.VIEW_TAG_HELP_PAGER ).setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        return view;
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

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
