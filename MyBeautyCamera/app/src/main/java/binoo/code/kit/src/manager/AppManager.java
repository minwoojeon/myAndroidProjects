package binoo.code.kit.src.manager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import binoo.code.kit.picture.entertain.mybeautycamera.R;

/**
 * Created by binoo on 2017-11-30.
 */

public final class AppManager {
    private static AppManager instance = new AppManager();

    // res 관리

    /*
    * 관리 대상:
    * activity
    * 설정내용 (폰트/색상/음성 등)
    * 음성 데이터 재출력을 위한 음성큐 관리
    * DB 관리
    * 네트워크 관리(백그라운드)
    * */

    /* Activities Stack res */
    private Stack<AppCompatActivity> activitiesStack=new Stack<AppCompatActivity>();

    public static AppManager getInstance(){
        return instance;
    }
    public int size(){
        return activitiesStack.size();
    }

    public void pushActivity(AppCompatActivity activity){
        // 있는걸 또 넣으면 중복 삽입이 아니라 검색해서 위치수정
        Enumeration<AppCompatActivity> appCompatActivityEnumeration = activitiesStack.elements();
        while(appCompatActivityEnumeration.hasMoreElements()){
            AppCompatActivity currentActivity = appCompatActivityEnumeration.nextElement();
            if (currentActivity.getClass() == activity.getClass()){
                // 동일한 클래스라면, -인스턴스는 같을 수 없음.
                activitiesStack.remove(currentActivity);
            }
        }
        activitiesStack.push(activity);
        // readyDBManager();
    }
    public void popActivity(){
        if(activitiesStack.size()>=1 ) activitiesStack.pop();
    }
    public AppCompatActivity peekActivity(){
        return (activitiesStack.size()>=1 ? activitiesStack.peek() : null);
    }

    private AlertDialog.Builder alertbuilder = null;
    public void setAlertbuilder(AlertDialog.Builder alertbuilder){
        this.alertbuilder = alertbuilder;
    }

    public AlertDialog.Builder makeAlert(String say){
        alertbuilder
                .setMessage(say)
                .setCancelable(false)
                .setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        return alertbuilder;
    }
    Dialog dialog = null;
    public void hideCustomAlert(){
        if (dialog != null){
            dialog.hide();
            dialog.dismiss();
        }
    }
    public void makeCustomAlert(View.OnTouchListener listener, String say, int proc ){
        hideCustomAlert();
        dialog = new Dialog(peekActivity());
        dialog.setContentView( R.layout.alert_info );
        dialog.setTitle("");
        TextView txtView = (TextView) dialog.findViewById(R.id.msgBox);
        txtView.setText( say );
        ImageView imgView = (ImageView) dialog.findViewById(R.id.imgView);
        Button btnConfirm = (Button) dialog.findViewById(R.id.btnConfirm);
        btnConfirm.setOnTouchListener( listener );
        switch ( proc ){
            case 0:
                // 안내
                imgView.setImageResource( R.drawable.icon_info );
                break;
            case 1:
                imgView.setImageResource( R.drawable.icon_fail );
                // 경고
                break;
            case 2:
                imgView.setImageResource( R.drawable.icon_success );
                // 확인
                break;
            default:
                break;
        }
        dialog.show();
    }
    private HashMap<Integer, View> viewHashMap = new HashMap<Integer, View>();
    public void setView( Integer viewTag, View view ){
        viewHashMap.put( viewTag, view );
    }
    public View getView( Integer viewTag ){
        return viewHashMap.get( viewTag );
    }
    private String fontName;
    public void setFontName(String fontName){
        this.fontName = fontName;
    }
    public String getFontName(){
        return this.fontName;
    }

    private String imgName;
    public void setImgName(String imgName){
        this.imgName = imgName;
    }
    public String getImgName(){
        return imgName;
    }

    public HashMap<String, String> txtItem = new HashMap<String, String>();
    public List<View> viewList = new ArrayList<View>();

    private int init = 0;
    public void setInit(int init){
        this.init = init;
    }
    public int getInit(){
        return init;
    }
}
