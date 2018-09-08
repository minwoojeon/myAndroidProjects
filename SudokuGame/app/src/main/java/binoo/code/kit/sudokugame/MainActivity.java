package binoo.code.kit.sudokugame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import binoo.code.kit.src.activitys.StartProjectActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, StartProjectActivity.class));
    }
}
