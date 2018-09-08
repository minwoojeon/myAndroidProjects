package binoo.code.kit.src.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;

import binoo.code.kit.picture.entertain.mybeautycamera.R;
import binoo.code.kit.src.fragments.MainFragment;
import binoo.code.kit.src.fragments.MainSlideFragment;
import binoo.code.kit.src.fragments.card.help.CustomHelpCardPageAdapter;
import binoo.code.kit.src.manager.AppGlobalData;
import binoo.code.kit.src.manager.AppManager;
import binoo.code.kit.src.utils.BackPressUtils;

/**
 * Created by junminwoo on 2018.01.20.
 */
public final class MainActivity extends ActivitiesImplements{

    private BackPressUtils backPressUtils;
    private ViewPager viewPager;
    private AdView mAdView;
    private boolean isPause = false;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                // 광고 호출
                if (!isPause){
                    AdRequest adRequest = new AdRequest.Builder().build();
                    mAdView.loadAd(adRequest);
                }
                new AdAsynk().execute();
            }
        }
    };
    class AdAsynk extends AsyncTask<Void,Void,Void>{

        public AdAsynk(){
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // 일정시간 뒤에 다시 자기자신을 호출한다.
            long length = (long)(Math.random()*8+7)*1000+7000;
            handler.sendEmptyMessageDelayed(0, length);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while(!mAdView.isLoading() && !isPause){
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_frame);
        AppManager.getInstance().setAlertbuilder(new AlertDialog.Builder(this));

        getSupportFragmentManager().beginTransaction().add(R.id.mainLinear, new MainFragment(), AppGlobalData.ACTIVITY_MAIN_FRAME_LAYOUT).commit();
/*
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setTag( AppGlobalData.VIEW_TAG_HELP_PAGER );
        viewPager.setAdapter(new CustomHelpCardPageAdapter(getSupportFragmentManager()));
        AppManager.getInstance().setView( AppGlobalData.VIEW_TAG_HELP_PAGER, viewPager );
        if (AppManager.getInstance().getInit() == -0x01){
            viewPager.setVisibility(View.VISIBLE);
        } else {
            viewPager.setVisibility(View.INVISIBLE);
        }*/

        backPressUtils = new BackPressUtils(this);
        /* addMob */
        MobileAds.initialize(this, this.getResources().getString(R.string.ad_mob_app_id));
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        new AdAsynk().execute();
    }

    @Override
    public void onBackPressed() {
        backPressUtils.onBackPressed();
    }

    @Override
    public void finish(){
        finishAffinity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppGlobalData.ACTIVITY_REQ_CODE_GALLERY) {
            // 갤러리에서 이미지 가져오기
            if(resultCode== Activity.RESULT_OK){
                View view = AppManager.getInstance().getView( AppGlobalData.VIEW_TAG_IMAGEVIEW );
                if (view != null && view instanceof ImageView){
                    ((ImageView) view).setImageURI(  data.getData() );
                }
            }
        } else if (requestCode == AppGlobalData.ACTIVITY_REQ_CODE_CAMERA){
            // 사진을 찍고, 그 이미지 가져오기
            if(resultCode== Activity.RESULT_OK){
                View view = AppManager.getInstance().getView( AppGlobalData.VIEW_TAG_IMAGEVIEW );
                if (view != null && view instanceof ImageView){
                    String fileName = AppManager.getInstance().getImgName();
                    if (fileName!=null && !"".equals(fileName)){
                        File f = new File(fileName);
                        if (f.exists()){
                            Bitmap bitmap = BitmapFactory.decodeFile( fileName );

                            Matrix matrix = new Matrix();
                            matrix.postRotate(90);
                            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                            bitmap.recycle();

                            ((ImageView) view).setImageBitmap(resizedBitmap);
                        }
                    }
                }
            }
        } else if (requestCode == AppGlobalData.ACTIVITY_REQ_CODE_FILE_SEARCH){
            // 폰트 파일 찾기
            /*if(resultCode== Activity.RESULT_OK){
                View view = AppManager.getInstance().getView( AppGlobalData.VIEW_TAG_TXT_FONT_FILENAME );
                if (view != null && view instanceof TextView){
                    AppManager.getInstance().setFontName( data.getData().toString() );
                    ((TextView) view).setText(
                            data.getData().toString().substring(data.getData().toString().lastIndexOf("/"))
                    );
                }
            }*/
        }
        AppManager.getInstance().getView( AppGlobalData.VIEW_TAG_VIEWPAGER ).setVisibility(View.INVISIBLE);
    }
    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
    }
    @Override
    protected void onResume() {
        super.onResume();
        isPause = false;
    }
}
