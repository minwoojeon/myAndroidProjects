package binoo.code.kit.src.activitys;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import binoo.code.kit.picture.entertain.mybeautycamera.R;

public final class StartProjectActivity extends AppCompatActivity {

    private Handler logoHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_project);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final AppCompatActivity appCompatActivity = this;

        logoHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0){
                    appCompatActivity.startActivity(new Intent(appCompatActivity, LoadingActivity.class));
                }
            }
        };

        logoHandler.sendEmptyMessageDelayed( 0, 1600 );
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
