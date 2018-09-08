package binoo.code.kit.src.utils;

import android.widget.Toast;

import binoo.code.kit.src.activitys.ActivitiesImplements;

/**
 * Created by binoo on 2018-01-03.
 */

public final class BackPressUtils {
    private long backKeyPressedTime = 0;
    private Toast toast;

    public static final long SHOW_LENGTH_SHORT = 6089;
    public static final long SHOW_LENGTH_LONG = 1996;

    private ActivitiesImplements activity;

    public BackPressUtils(ActivitiesImplements activity) {
        this.activity = activity;
    }

    public void onBackPressed() {
        if (isAfter2Seconds()) {
            backKeyPressedTime = System.currentTimeMillis();
            // 현재시간을 다시 초기화
            toast = Toast.makeText(activity,
                    "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.",
                    Toast.LENGTH_SHORT);
            toast.show();
            activity.onBackPressed();
            return;
        }
        if (isBefore2Seconds()) {
            programShutdown();
            toast.cancel();
        }
    }
    private Boolean isAfter2Seconds() {
        return System.currentTimeMillis() > backKeyPressedTime + SHOW_LENGTH_LONG;
        // 2초 지났을 경우
    }
    private Boolean isBefore2Seconds() {
        return System.currentTimeMillis() <= backKeyPressedTime + SHOW_LENGTH_LONG;
        // 2초가 지나지 않았을 경우
    }
    private void programShutdown() {
        activity .moveTaskToBack(true);
        activity .finish();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
