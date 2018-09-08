package binoo.code.kit.src.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import binoo.code.kit.mycallygraphyeasy.R;
import binoo.code.kit.src.fragments.card.FontsColorFragment;
import binoo.code.kit.src.fragments.card.FontsSizeFragment;
import binoo.code.kit.src.fragments.card.FontsStyleFragment;
import binoo.code.kit.src.fragments.card.FontsTextFragment;
import binoo.code.kit.src.fragments.card.LayerGallery;
import binoo.code.kit.src.fragments.card.LoadingMainFragment;
import binoo.code.kit.src.manager.AppGlobalData;
import binoo.code.kit.src.manager.AppManager;

public final class ChoosePictureFragment extends Fragment {

    ImageView imgView;
    ImageView btnPicture;
    ImageView btnText;
    ImageView btnSave;
    ImageView btnShare;
    ImageView btnInfo;
    ImageView btnMain;
    ImageView logo;
    FrameLayout txtLayer;
    ViewPager viewPager;
    LinearLayout showView;

    String fileName = "";

    private OnFragmentInteractionListener mListener;

    public ChoosePictureFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_choose_picture, container, false);

        imgView = (ImageView) view.findViewById(R.id.imgView);
        btnPicture = (ImageView) view.findViewById(R.id.btnPicture);
        btnText = (ImageView) view.findViewById(R.id.btnText);
        btnSave = (ImageView) view.findViewById(R.id.btnSave);
        btnShare = (ImageView) view.findViewById(R.id.btnShare);
        btnInfo = (ImageView) view.findViewById(R.id.btnInfo);
        btnMain = (ImageView) view.findViewById(R.id.btnMain);
        logo = (ImageView) view.findViewById(R.id.logo);
        txtLayer = (FrameLayout) view.findViewById(R.id.txtLayer);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        showView = (LinearLayout) view.findViewById(R.id.showView);

        btnPicture.setTag( 0x880 );
        btnText.setTag( 0x888 );
        btnSave.setTag( 0x896 );
        btnShare.setTag( 0x904 );
        btnInfo.setTag( 0x912 );
        btnMain.setTag( 0x916 );

        try {
            AssetManager assetManager = AppManager.getInstance().peekActivity().getBaseContext().getAssets();
            String assetFontList[] = assetManager.list("imgs");
            int position = (int) (Math.random() * (assetFontList.length-1));
            Bitmap bitmap = BitmapFactory.decodeStream(assetManager.open( "imgs/"+assetFontList[position] ));
            imgView.setImageBitmap(bitmap);
            // assetManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        View.OnTouchListener onTouchListener = new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getTag() == btnPicture.getTag()){
                    // btnPicture 갤러리를 클릭했을때
                    // 애니메이션 효과와 함께 사진/갤러리/그림/취소 중 선택 화면을 노출한다.
                    final class CustomPagerAdapter extends FragmentStatePagerAdapter {
                        public CustomPagerAdapter(FragmentManager fm){
                            super(fm);
                        }
                        @Override
                        public Fragment getItem(int position) {
                            android.support.v4.app.Fragment fragment = new LayerGallery();
                            return fragment;
                        }
                        @Override
                        public int getCount() {
                            return 1;
                        }
                    }
                    viewPager.setAdapter( new CustomPagerAdapter( AppManager.getInstance().peekActivity().getSupportFragmentManager() ));
                    viewPager.setVisibility( View.VISIBLE );
                } else if (v.getTag() == btnText.getTag()){
                    // btnText 텍스트를 클릭했을때
                    // 애니메이션 효과와 함께 텍스트선택 뷰페이지 화면을 노출한다.
                    AppManager.getInstance().txtItem.clear();
                    AppManager.getInstance().txtItem.put("proctype", "add");
                    AppManager.getInstance().txtItem.put("content", "");
                    AppManager.getInstance().txtItem.put("fontName", "fonts/malgun.ttf");
                    AppManager.getInstance().txtItem.put("fontColor", Color.argb(0xff,0x00,0x00,0x00)+"");
                    AppManager.getInstance().txtItem.put("fontSize", "14");
                    final class CustomPagerAdapter extends FragmentStatePagerAdapter {
                        public CustomPagerAdapter(FragmentManager fm){
                            super(fm);
                        }
                        @Override
                        public Fragment getItem(int position) {
                            android.support.v4.app.Fragment fragment = null;
                            switch ( position ){
                                case 0:
                                    fragment = new FontsTextFragment();
                                    break;
                                case 1:
                                    fragment = new FontsStyleFragment();
                                    break;
                                case 2:
                                    fragment = new FontsColorFragment();
                                    break;
                                case 3:
                                    fragment = new FontsSizeFragment();
                                    break;
                                default:
                                    break;
                            }
                            return fragment;
                        }
                        @Override
                        public int getCount() {
                            return 4;
                        }
                    }
                    viewPager.setAdapter( new CustomPagerAdapter( AppManager.getInstance().peekActivity().getSupportFragmentManager() ));
                    viewPager.setVisibility( View.VISIBLE );
                } else if (v.getTag() == btnSave.getTag()){
                    // btnSave 저장을 클릭했을때
                    // 애니메이션 효과와 함께 알럿이후 저장처리한다.
                    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                // 저장처리
                                final class LoadingAsynk extends AsyncTask<Void, Void, Void>{
                                    boolean finish = false;
                                    @Override
                                    protected void onPreExecute() {
                                        final class CustomPagerAdapter extends FragmentStatePagerAdapter {
                                            public CustomPagerAdapter(FragmentManager fm){
                                                super(fm);
                                            }
                                            @Override
                                            public Fragment getItem(int position) {
                                                android.support.v4.app.Fragment fragment = new LoadingMainFragment();
                                                return fragment;
                                            }
                                            @Override
                                            public int getCount() {
                                                return 1;
                                            }
                                        }
                                        viewPager.setAdapter( new CustomPagerAdapter( AppManager.getInstance().peekActivity().getSupportFragmentManager() ));
                                        AppManager.getInstance().hideCustomAlert();
                                        viewPager.setVisibility( View.VISIBLE );
                                        super.onPreExecute();
                                    }
                                    @Override
                                    protected void onPostExecute(Void aVoid) {

                                        if (finish){
                                            AppManager.getInstance().makeCustomAlert(new View.OnTouchListener() {
                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    AppManager.getInstance().hideCustomAlert();
                                                    return false;
                                                }
                                            }, AppManager.getInstance().peekActivity().getResources().getString(R.string.success_img_save), 2);
                                        } else {
                                            AppManager.getInstance().makeCustomAlert(new View.OnTouchListener() {
                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    if (event.getAction() == MotionEvent.ACTION_MOVE)
                                                        AppManager.getInstance().hideCustomAlert();
                                                    return false;
                                                }
                                            }, AppManager.getInstance().peekActivity().getResources().getString(R.string.fail_img_save), 1);
                                        }

                                        viewPager.setVisibility(View.INVISIBLE);
                                        super.onPostExecute(aVoid);
                                    }
                                    @Override
                                    protected Void doInBackground(Void... voids) {
                                        List<View> viewList = AppManager.getInstance().viewList;
                                        for (int i=0; i<viewList.size(); i++){
                                            View findView = viewList.get(i);
                                            if (findView instanceof LinearLayout){
                                                LinearLayout instanceView = ((LinearLayout) findView);
                                                final ImageView btnUnable = (ImageView) instanceView.findViewById(R.id.btnUnable);
                                                final ImageView btnEdit = (ImageView) instanceView.findViewById(R.id.btnEdit);
                                                final ImageView btnAlign = (ImageView) instanceView.findViewById(R.id.btnAlign);
                                                final ImageView btnRotate = (ImageView) instanceView.findViewById(R.id.btnRotate);

                                                btnUnable.setVisibility(View.INVISIBLE);
                                                btnEdit.setVisibility(View.INVISIBLE);
                                                btnAlign.setVisibility(View.INVISIBLE);
                                                btnRotate.setVisibility(View.INVISIBLE);
                                            }
                                        }
                                        logo.buildDrawingCache();
                                        txtLayer.buildDrawingCache();
                                        imgView.buildDrawingCache();
                                        Bitmap logoView = logo.getDrawingCache();
                                        Bitmap txtCaptureView = txtLayer.getDrawingCache();
                                        Bitmap imgCaptureView = imgView.getDrawingCache();

                                        int width = imgCaptureView.getWidth();
                                        int height = imgCaptureView.getHeight();
                                        int logowidth = logoView.getWidth();
                                        int logoheight = logoView.getHeight();
                                        for (int i=0; i<width; i++){
                                            for (int j=0; j<height; j++){
                                                int pixel = imgCaptureView.getPixel(i, j);
                                                int a = Color.alpha(pixel);
                                                int r = Color.red(pixel);
                                                int g = Color.green(pixel);
                                                int b = Color.blue(pixel);
                                                if ((width-logowidth-20 < i && width-20 > i) &&
                                                        (height-logoheight-20 < j && height-20 > j)){
                                                    // 로고뷰의 영역
                                                    pixel = logoView.getPixel(i-(width-logowidth-20), j-(height-logoheight-20));
                                                    int a2 = Color.alpha(pixel);    // (color*alpha)/255
                                                    r = (a*r)/255+(Color.red(pixel)*a2)/255;
                                                    g = (a*g)/255+(Color.green(pixel)*a2)/255;
                                                    b = (a*b)/255+(Color.blue(pixel)*a2)/255;
                                                } else {
                                                    // 텍스트 뷰의 영역
                                                    pixel = txtCaptureView.getPixel(i, j);
                                                    int a2 = Color.alpha(pixel);    // (color*alpha)/255
                                                    if (a2 != 0){
                                                        // r 30, a 0f r ff a f0
                                                        // (r*(0xff-a2)+r2*a2)/255
                                                        r = (r*(0xff-a2)+Color.red(pixel)*a2)/255;
                                                        g = (g*(0xff-a2)+Color.green(pixel)*a2)/255;
                                                        b = (b*(0xff-a2)+Color.blue(pixel)*a2)/255;
                                                    }
                                                }
                                                // 범위 정상화
                                                a = ( a>=255 ? 255 : a<0? 0 : a );
                                                r = ( r>=255 ? 255 : r<0? 0 : r );
                                                g = ( g>=255 ? 255 : g<0? 0 : g );
                                                b = ( b>=255 ? 255 : b<0? 0 : b );

                                                imgCaptureView.setPixel( i, j, Color.argb(a,r,g,b) );
                                            }
                                        }

                                        FileOutputStream fos = null;
                                        try {
                                            File f = new File(Environment.getExternalStorageDirectory().toString() + "/data");
                                            if (!f.exists()) f.mkdirs();
                                            f = new File(Environment.getExternalStorageDirectory().toString() + "/data/img");
                                            if (!f.exists()) f.mkdirs();
                                            fileName = "/data/img/MHB_CG_"+new SimpleDateFormat("yyyyMMddHHMMssSSS", Locale.KOREA).format(new Date())+".jpg";
                                            fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString()+ fileName);

                                            imgCaptureView.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                            finish = true;
                                            fos.flush();
                                            fos.close();
                                        } catch (FileNotFoundException e) {
                                            finish = false;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } finally {
                                            if (fos!=null) fos = null;
                                            System.gc();
                                        }
                                        return null;
                                    }
                                }
                                new LoadingAsynk().execute();
                            }
                            return false;
                        }
                    };
                    AppManager.getInstance().makeCustomAlert(onTouchListener, AppManager.getInstance().peekActivity().getResources().getString(R.string.question_img_save), 0);
                } else if (v.getTag() == btnShare.getTag()){
                    // btnShare 공유를 클릭했을때
                    // 애니메이션 효과와 함께 알럿이후 공유한다.
                    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            // 공유처리
                            if (event.getAction() == MotionEvent.ACTION_DOWN){
                                File file = new File(Environment.getExternalStorageDirectory() + fileName);
                                if (!file.canRead() || "".equals(fileName)){
                                    AppManager.getInstance().makeCustomAlert(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            if (event.getAction() == MotionEvent.ACTION_MOVE)
                                                AppManager.getInstance().hideCustomAlert();
                                            return false;
                                        }
                                    }, AppManager.getInstance().peekActivity().getResources().getString(R.string.fail_img_share), 1);
                                    return false;
                                }
                                AppManager.getInstance().hideCustomAlert();

                                Uri uri = FileProvider.getUriForFile(
                                        AppManager.getInstance().peekActivity().getBaseContext(),
                                        AppManager.getInstance().peekActivity().getBaseContext().getApplicationContext().getPackageName() + ".fileprovider",
                                        file);
                                Intent shareIntent = new Intent();
                                shareIntent.setAction(Intent.ACTION_SEND);
                                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                shareIntent.setType("image/jpeg");
                                startActivity(Intent.createChooser(shareIntent, AppManager.getInstance().peekActivity().getResources().getString(R.string.success_img_share)));
                            }
                            return false;
                        }
                    };
                    AppManager.getInstance().makeCustomAlert(onTouchListener, AppManager.getInstance().peekActivity().getResources().getString(R.string.question_img_share), 0);
                } else if (v.getTag() == btnInfo.getTag()){
                    final class CustomPagerAdapter extends FragmentStatePagerAdapter {
                        public CustomPagerAdapter(FragmentManager fm){
                            super(fm);
                        }
                        @Override
                        public Fragment getItem(int position) {
                            android.support.v4.app.Fragment fragment = new OptionFragment();
                            return fragment;
                        }
                        @Override
                        public int getCount() {
                            return 1;
                        }
                    }
                    viewPager.setAdapter( new CustomPagerAdapter( AppManager.getInstance().peekActivity().getSupportFragmentManager() ));
                    viewPager.setVisibility( View.VISIBLE );
                } else if (v.getTag() == btnMain.getTag()){
                    AppManager.getInstance().setImgName("");
                    AppManager.getInstance().setFontName("");
                    AppManager.getInstance().txtItem.clear();
                    AppManager.getInstance().setView( AppGlobalData.VIEW_TAG_TEXT_LAYOUT, null );
                    AppManager.getInstance().setView( AppGlobalData.VIEW_TAG_IMAGEVIEW, null );
                    AppManager.getInstance().setView( AppGlobalData.VIEW_TAG_VIEWPAGER, null );
                    AppManager.getInstance().setView( AppGlobalData.VIEW_TAG_SHOW_VIEW, null );
                    AppManager.getInstance().peekActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainLinear, new MainSlideFragment(), AppGlobalData.ACTIVITY_MAIN_FRAME_LAYOUT).commit();
                    System.gc();
                }
                return false;
            }
        };
        final InputMethodManager inputMethodManager = (InputMethodManager) AppManager.getInstance().peekActivity().getSystemService( Context.INPUT_METHOD_SERVICE );
        txtLayer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() ==MotionEvent.ACTION_MOVE){
                    View viewItem = txtLayer.getFocusedChild();
                    for (int i=0; i<txtLayer.getChildCount(); i++){
                        View instanceView = txtLayer.getChildAt(i);
                        if (instanceView instanceof LinearLayout){
                            final ImageView btnUnable = (ImageView) viewItem.findViewById(R.id.btnUnable);
                            final ImageView btnEdit = (ImageView) viewItem.findViewById(R.id.btnEdit);
                            final ImageView btnAlign = (ImageView) viewItem.findViewById(R.id.btnAlign);
                            final ImageView btnRotate = (ImageView) viewItem.findViewById(R.id.btnRotate);
                            final EditText editText = (EditText) instanceView.findViewById(R.id.editText);
                            if (instanceView == viewItem){
                                btnUnable.setVisibility(View.VISIBLE);
                                btnEdit.setVisibility(View.VISIBLE);
                                btnAlign.setVisibility(View.VISIBLE);
                                btnRotate.setVisibility(View.VISIBLE);
                            } else {
                                btnUnable.setVisibility(View.INVISIBLE);
                                btnEdit.setVisibility(View.INVISIBLE);
                                btnAlign.setVisibility(View.INVISIBLE);
                                btnRotate.setVisibility(View.INVISIBLE);
                            }
                            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        }
                    }
                }
                return false;
            }
        });

        btnPicture.setOnTouchListener( onTouchListener );
        btnText.setOnTouchListener( onTouchListener );
        btnSave.setOnTouchListener( onTouchListener );
        btnShare.setOnTouchListener( onTouchListener );
        btnInfo.setOnTouchListener( onTouchListener );
        btnMain.setOnTouchListener( onTouchListener );

        viewPager.setVisibility( View.INVISIBLE );
        showView.setVisibility( View.INVISIBLE );

        AppManager.getInstance().setView( AppGlobalData.VIEW_TAG_TEXT_LAYOUT, txtLayer );
        AppManager.getInstance().setView( AppGlobalData.VIEW_TAG_IMAGEVIEW, imgView );
        AppManager.getInstance().setView( AppGlobalData.VIEW_TAG_VIEWPAGER, viewPager );
        AppManager.getInstance().setView( AppGlobalData.VIEW_TAG_SHOW_VIEW, showView );

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
