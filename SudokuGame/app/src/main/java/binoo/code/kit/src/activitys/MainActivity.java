package binoo.code.kit.src.activitys;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import binoo.code.kit.src.fragments.GameMainFragment;
import binoo.code.kit.src.fragments.MainFragment;
import binoo.code.kit.src.fragments.card.help.CustomHelpCardPageAdapter;
import binoo.code.kit.src.manager.AppGlobalData;
import binoo.code.kit.src.manager.AppManager;
import binoo.code.kit.src.utils.BackPressUtils;
import binoo.code.kit.sudokugame.R;

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

        getSupportFragmentManager().beginTransaction().add(R.id.mainLinear, new GameMainFragment(), AppGlobalData.ACTIVITY_MAIN_FRAME_LAYOUT).commit();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setTag( AppGlobalData.VIEW_TAG_HELP_PAGER );
        viewPager.setAdapter(new CustomHelpCardPageAdapter(getSupportFragmentManager()));
        AppManager.getInstance().setView( AppGlobalData.VIEW_TAG_HELP_PAGER, viewPager );

        backPressUtils = new BackPressUtils(this);
        /* addMob *//*
        MobileAds.initialize(this, this.getResources().getString(R.string.ad_mob_app_id));
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
*/
        //new AdAsynk().execute();
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
