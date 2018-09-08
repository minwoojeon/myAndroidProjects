package binoo.code.kit.src.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import binoo.code.kit.src.manager.AppManager;

/**
 * Created by binoo on 2017-12-01.
 */

public class ActivitiesImplements extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AppManager.getInstance().size() == 1){
            AppManager.getInstance().popActivity();
        }
        AppManager.getInstance().pushActivity(this);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }
}
