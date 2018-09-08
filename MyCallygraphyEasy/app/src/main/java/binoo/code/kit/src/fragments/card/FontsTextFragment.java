package binoo.code.kit.src.fragments.card;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import binoo.code.kit.mycallygraphyeasy.R;
import binoo.code.kit.src.manager.AppGlobalData;
import binoo.code.kit.src.manager.AppManager;

/**
 * Created by binoo on 2017-12-28.
 */

public final class FontsTextFragment extends Fragment {

    public static EditText editText;

    private FontsTextFragment.OnFragmentInteractionListener mListener;

    public FontsTextFragment() {
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
        View view = inflater.inflate(R.layout.fragment_layer_card_text, container, false);
        // 텍스트를 임시 저장한다.
        editText = (EditText) view.findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AppManager.getInstance().txtItem.put( "content", s.toString() );
                View view = AppManager.getInstance().getView( AppGlobalData.VIEW_TAG_TEXTVIEW );
                if (view != null && view instanceof EditText){
                    ((EditText) view).setText( s );
                }
                if (FontsStyleFragment.editView != null){
                    FontsStyleFragment.editView.setText( s );
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        final InputMethodManager inputMethodManager = (InputMethodManager) AppManager.getInstance().peekActivity().getSystemService( Context.INPUT_METHOD_SERVICE );
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                }
                return false;
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
        if (context instanceof FontsTextFragment.OnFragmentInteractionListener) {
            mListener = (FontsTextFragment.OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        final InputMethodManager inputMethodManager = (InputMethodManager) AppManager.getInstance().peekActivity().getSystemService( Context.INPUT_METHOD_SERVICE );
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);

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
