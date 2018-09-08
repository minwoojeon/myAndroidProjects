package binoo.code.kit.src.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.util.HashMap;

import binoo.code.kit.src.manager.AppGlobalData;
import binoo.code.kit.src.manager.AppManager;
import binoo.code.kit.sudokugame.R;

public final class LoadingActivity extends ActivitiesImplements {
    TextView txtValue;
    TextView txtLoading;
    NumberProgressBar progressBar;
    Handler nextHandler;
    Handler toolTipHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txtValue = (TextView)findViewById(R.id.txtValue);
        txtLoading = (TextView)findViewById(R.id.txtLoading);
        progressBar = (NumberProgressBar)findViewById(R.id.progressBar);

        txtValue.setText("");

        AppManager.getInstance().setAlertbuilder(new AlertDialog.Builder(this));

        nextHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0){
                    new DataIninAsynk().execute();
                } else if (msg.what == 1){
                     /* 특수 권한 필요없음 */
                    nextHandler.sendEmptyMessageDelayed(0, 1000);
                }
            }
        };
        toolTipHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 10){
                    String tooltips[] = AppManager.getInstance().peekActivity().getResources().getStringArray(R.array.list_tooltips);
                    int ranval = (int)(Math.random()*tooltips.length);
                    txtLoading.setText( tooltips[ranval] );
                    toolTipHandler.sendEmptyMessageDelayed(10, 4143);
                }
            }
        };
        nextHandler.sendEmptyMessage(1);
        toolTipHandler.sendEmptyMessage(10);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    private class DataIninAsynk extends AsyncTask<Void, Void, Void>{

        final Context context;
        private int progress = 0;

        public DataIninAsynk(){
            context = AppManager.getInstance().peekActivity().getBaseContext();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setMax(10000);
            progressBar.setProgress(10000);
            txtValue.setText("100.0 %");
            try{
                toolTipHandler.removeMessages( 10 );
            }catch (NullPointerException e){
            }
            try{
                nextHandler.removeMessages( 0 );
            }catch (NullPointerException e){
            }
            try{
                nextHandler.removeMessages( 1 );
            }catch (NullPointerException e){
            }
            try{
                nextHandler.removeMessages( 2 );
            }catch (NullPointerException e){
            }
            toolTipHandler = null;
            nextHandler = null;
            AppManager.getInstance().peekActivity().startActivity(new Intent(AppManager.getInstance().peekActivity(), MainActivity.class));
            super.onPostExecute(aVoid);
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            txtValue.setText( (progressBar.getProgress()*100.0f)/progressBar.getMax()+" %" );
            progressBar.setProgress(progress);

            super.onProgressUpdate(values);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                AppGlobalData appGlobalData = new AppGlobalData();
                HashMap<String, Integer> map;
                map = appGlobalData.loadProperties();
                boolean init = false;
                if (map != null){
                    if (map.size() == 0){
                        init = true;
                    }
                    if (map.get("init") != null){
                        if (map.get("init") != 0x90){
                            init = true;
                        }
                    } else {
                        init = true;
                    }
                } else {
                    init = true;
                }
                if (init){
                    map.put("init", 0x90);
                    map.put("help", -0x01);
                    appGlobalData.saveValues(map);
                } else {
                    map.put("help", 0x01);
                    appGlobalData.saveValues(map);
                }
                //AppManager.getInstance().setInit( map.get("help") );
            } catch (Exception e) {
                e.printStackTrace() ;
                //AppManager.getInstance().setInit( -0x01 );
            }
            return null;
        }
    }
}
