package binoo.code.kit.src.activitys;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Message;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.util.HashMap;

import binoo.code.kit.picture.entertain.mybeautycamera.R;
import binoo.code.kit.src.manager.AppGlobalData;
import binoo.code.kit.src.manager.AppManager;

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
                     /* 마시멜로 이상인 경우. */
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                        boolean readable = ContextCompat.checkSelfPermission(AppManager.getInstance().peekActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_GRANTED;
                        boolean writeable = ContextCompat.checkSelfPermission(AppManager.getInstance().peekActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_GRANTED;

                        // 읽기 권한이 없을경우 -> 읽기 권한 부여 -> 쓰기 권한 부여 -> 앱 실행
                        // 쓰기 권한이 없을 경우 -> 쓰기 권한 부여 -> 앱 실행
                        if ( readable && writeable ){
                            // 둘 다 권한이 있는 경우.
                            nextHandler.sendEmptyMessageDelayed(0, 300);
                        } else {
                            AppManager.getInstance().makeAlert( AppManager.getInstance().peekActivity().getResources().getString(R.string.permission_alert) )
                                    .setPositiveButton("확인",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    nextHandler.sendEmptyMessageDelayed(2, 300);
                                                }
                                            }).setNegativeButton("취소",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            AppManager.getInstance().peekActivity().finishAffinity();
                                            return;
                                        }
                                    })
                                    .create().show();
                        }
                    } else {
                        nextHandler.sendEmptyMessageDelayed(0, 1000);
                    }
                } else if (msg.what == 2){
                    ActivityCompat.requestPermissions(AppManager.getInstance().peekActivity(), new String[]{
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA },
                            MY_PERMISSIONS_REQUEST_WR_CONTACTS);
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

    private final int MY_PERMISSIONS_REQUEST_WR_CONTACTS = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WR_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    nextHandler.sendEmptyMessageDelayed(0, 300);
                } else {
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
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
                AppManager.getInstance().setInit( map.get("help") );
            } catch (Exception e) {
                e.printStackTrace() ;
                AppManager.getInstance().setInit( -0x01 );
            }
            return null;
        }
    }
}
