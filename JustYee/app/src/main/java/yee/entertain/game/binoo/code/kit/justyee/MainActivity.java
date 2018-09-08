package yee.entertain.game.binoo.code.kit.justyee;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    AdView mAdView;

    class SoundMagic{
        private SoundPool soundpool;
        private HashMap soundHash;
        private AudioManager audioManager;
        private Context context;

        public SoundMagic(Context context){
            soundpool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
            soundHash = new HashMap();
            audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            this.context = context;
        }
        public void addSound(int idx, int s_id){
            int id = soundpool.load(context, s_id, 1);
            soundHash.put(idx, id);
        }
        public void play(int idx){
            float st_vol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            float sm_vol = st_vol / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

            soundpool.play((Integer) soundHash.get(idx), st_vol, sm_vol, 1, 0, 1f);
        }
    }
    private SoundMagic[] soundMagics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundMagics = new SoundMagic[4];
        ImageView[] imageViews = new ImageView[4];
        int[] res = new int[]{ R.id.imageView, R.id.imageView2, R.id.imageView3, R.id.imageView4 };
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    int tag = Integer.parseInt(v.getTag()+"");
                    soundMagics[tag].play(tag);
                }
                return false;
            }
        };
        for (int i=0; i<4; i++){
            soundMagics[i] = new SoundMagic( this );
            soundMagics[i].addSound(i, R.raw.yee);
            imageViews[i] = (ImageView) findViewById( res[i] );
            imageViews[i].setTag( i );
            imageViews[i].setOnTouchListener(onTouchListener);
        }
        /* addMob */
        MobileAds.initialize(this, this.getResources().getString(R.string.ad_mob_app_id));

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void finish() {
        super.finish();
    }
}
