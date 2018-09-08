package binoo.code.kit.src.fragments.card;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import binoo.code.kit.mycallygraphyeasy.R;
import binoo.code.kit.src.manager.AppGlobalData;
import binoo.code.kit.src.manager.AppManager;

/**
 * Created by binoo on 2017-12-28.
 */

public final class LayerGallery extends Fragment {

    private LayerGallery.OnFragmentInteractionListener mListener;

    public LayerGallery() {
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
        View view = inflater.inflate(R.layout.fragment_layer_gallery, container, false);
        // 폰트 색상을 선택한다.
        final ImageView btnGallery = (ImageView) view.findViewById(R.id.btnGallery);
        btnGallery.setTag( 0x1199 );
        final ImageView btnCamera = (ImageView) view.findViewById(R.id.btnCamera);
        btnCamera.setTag( 0x1211 );
        final ImageView btnColor = (ImageView) view.findViewById(R.id.btnColor);
        btnColor.setTag( 0x1220 );
        final ImageView btnRes = (ImageView) view.findViewById(R.id.btnRes);
        btnRes.setTag( 0x1310 );

        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    AppManager.getInstance().getView( AppGlobalData.VIEW_TAG_VIEWPAGER ).setVisibility(View.INVISIBLE);
                    if (v.getTag() == btnGallery.getTag()){
                        // 갤러리에서 이미지 가져오기
                        // 갤러리로 인텐트를 넘긴다.
                        // 리턴결과와 함께 배경 이미지를 변경한다.
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        AppManager.getInstance().peekActivity().startActivityForResult(intent, AppGlobalData.ACTIVITY_REQ_CODE_GALLERY);
                    } else if (v.getTag() == btnCamera.getTag()){
                        // 카메라에서 이미지 가져오기
                        // 카메라로 인텐트를 넘긴다.
                        // 리턴 결과와 함께 배경 이미지를 변경한다.
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        File f = new File(Environment.getExternalStorageDirectory().toString() + "/data");
                        if (!f.exists()) f.mkdirs();
                        f = new File(Environment.getExternalStorageDirectory().toString() + "/data/img");
                        if (!f.exists()) f.mkdirs();
                        String filePath = Environment.getExternalStorageDirectory()+"/data/img/MCE"+
                                new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss S").format(new Date())+".jpg";

                        Uri clsUri =
                                FileProvider.getUriForFile(
                                        AppManager.getInstance().peekActivity().getBaseContext(),
                                        AppManager.getInstance().peekActivity().getBaseContext().getApplicationContext().getPackageName() + ".fileprovider",
                                        new File(filePath));
                        intent.putExtra( android.provider.MediaStore.EXTRA_OUTPUT, clsUri );
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        AppManager.getInstance().setImgName( filePath );
                        AppManager.getInstance().peekActivity().startActivityForResult(intent, AppGlobalData.ACTIVITY_REQ_CODE_CAMERA);
                    } else if (v.getTag() == btnColor.getTag()){
                        // 색상환에서 이미지 만들어서 적용하기
                        // 색상환 카드뷰를 출력한다.
                        // 갤러리뷰는 인비지블처리한다.
                        LinearLayout view = (LinearLayout) AppManager.getInstance().getView( AppGlobalData.VIEW_TAG_SHOW_VIEW );
                        AppManager.getInstance().peekActivity().getSupportFragmentManager().beginTransaction().replace( view.getId(), new PaintColorCardFragment() ).commit();
                        view.setVisibility(View.VISIBLE);
                    } else if (v.getTag() == btnRes.getTag()){
                        // 기존 리소스를 긁어온다.
                        LinearLayout view = (LinearLayout) AppManager.getInstance().getView( AppGlobalData.VIEW_TAG_SHOW_VIEW );
                        AppManager.getInstance().peekActivity().getSupportFragmentManager().beginTransaction().replace( view.getId(), new GalleryResCardFragment() ).commit();
                        view.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }
        };
        btnGallery.setOnTouchListener( onTouchListener );
        btnCamera.setOnTouchListener( onTouchListener );
        btnColor.setOnTouchListener( onTouchListener );
        btnRes.setOnTouchListener( onTouchListener );
        view.setOnTouchListener( onTouchListener );

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
        if (context instanceof LayerGallery.OnFragmentInteractionListener) {
            mListener = (LayerGallery.OnFragmentInteractionListener) context;
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