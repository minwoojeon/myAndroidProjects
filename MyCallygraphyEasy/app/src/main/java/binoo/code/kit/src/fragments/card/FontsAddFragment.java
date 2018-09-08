package binoo.code.kit.src.fragments.card;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import binoo.code.kit.mycallygraphyeasy.R;
import binoo.code.kit.src.manager.AppGlobalData;
import binoo.code.kit.src.manager.AppManager;

/**
 * Created by binoo on 2017-12-28.
 */

public final class FontsAddFragment extends Fragment {

    private FontsAddFragment.OnFragmentInteractionListener mListener;

    public FontsAddFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_layer_card_add, container, false);
        // 폰트를 하나 가져올 수 있다.
        final Button btnFont = (Button) view.findViewById(R.id.btnFont);
        final Button btnAdd = (Button) view.findViewById(R.id.btnAdd);
        final TextView fontFileName = (TextView) view.findViewById(R.id.fontFileName);
        btnFont.setTag( 0x88 );
        btnAdd.setTag( 0x98 );
        view.setTag( 0x99 );

        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    if (btnFont.getTag() == v.getTag()){
                        // 파일 탐색기 시작
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("*/*");
                        AppManager.getInstance().setView( AppGlobalData.VIEW_TAG_TXT_FONT_FILENAME, fontFileName );
                        startActivityForResult(intent, AppGlobalData.ACTIVITY_REQ_CODE_FILE_SEARCH);
                    } else if (btnAdd.getTag() == v.getTag()){
                        // 폰트파일 에셋으로 이동.
                        AssetManager assetManager = AppManager.getInstance().peekActivity().getAssets();
                        FileInputStream in = null;
                        OutputStream out = null;
                        try {
                            Log.d("font:",""+AppManager.getInstance().getFontName());
                            String fontName = AppManager.getInstance().getFontName();
                            // 확장자가 ttf 만 가능하도록 처리
                            // ttf 확장자에서, asset 경로를 취득하여 추가를 한다.
                            if (fontName.contains(".")){
                                if ("ttf".equals(fontName.substring(fontName.lastIndexOf(".")))){
                                    if (fontName != null && !"".equals(fontName)){
                                        File fontFile = new File(fontName);
                                        if (fontFile.canRead()){
                                            in = new FileInputStream(fontFile);
                                            out = assetManager.openFd("").createOutputStream();
                                            byte[] buffer = new byte[1024];
                                            int read;
                                            while((read = in.read(buffer)) != -1){
                                                out.write(buffer, 0, read);
                                            }
                                            out.flush();
                                            out.close();
                                            in.close();
                                        } else {
                                            // 미선택시 추가없음.
                                        }
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (in != null) in.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                if (out != null) out.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (view.getTag() == v.getTag()){
                        AppManager.getInstance().getView( AppGlobalData.VIEW_TAG_VIEWPAGER ).setVisibility(View.INVISIBLE);
                    }
                }
                return false;
            }
        };

        btnFont.setOnTouchListener(onTouchListener);
        btnAdd.setOnTouchListener(onTouchListener);
        view.setOnTouchListener(onTouchListener);
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
        if (context instanceof FontsAddFragment.OnFragmentInteractionListener) {
            mListener = (FontsAddFragment.OnFragmentInteractionListener) context;
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
