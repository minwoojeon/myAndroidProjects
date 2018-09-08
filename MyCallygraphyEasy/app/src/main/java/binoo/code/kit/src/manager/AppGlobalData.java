package binoo.code.kit.src.manager;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by binoo on 2017-11-30.
 */

public final class AppGlobalData {
    // 여기에 둘 데이터는 계속적으로 사용되는 데이터는 아니지만, 중요하게 사용될 상수, 문자->숫자형 변환을 모두 통칭한다.
    public final static int ACTIVITY_REQ_CODE_GALLERY = 0x200;
    public final static int ACTIVITY_REQ_CODE_CAMERA = 0x300;
    public final static int ACTIVITY_REQ_CODE_FILE_SEARCH = 0x400;

    public final static int GALLERY_SELLECT = 0x01;
    public final static int TEXT_SELLECT = 0x02;
    public final static int VIEW_TAG_TEXT_LAYOUT = 0x20;
    public final static int VIEW_TAG_VIEWPAGER = 0x30;
    public final static int VIEW_TAG_TEXTVIEW = 0x31;
    public final static int VIEW_TAG_IMAGEVIEW = 0x32;
    public final static int VIEW_TAG_SHOW_VIEW = 0x33;
    public final static int VIEW_TAG_HELP_PAGER = 0xaf;
    public final static int VIEW_TAG_TXT_FONT_FILENAME = 0x101;

    public final static String ACTIVITY_MAIN_FRAME_LAYOUT = "layout";

    public HashMap<String, Integer> loadProperties(){
        HashMap<String, Integer> result = new HashMap<String, Integer>();

        SharedPreferences sp = AppManager.getInstance().peekActivity().getSharedPreferences("config.properties", Activity.MODE_PRIVATE);
        result.putAll( (HashMap<String, Integer>)sp.getAll() );

        return result;
    }

    public void saveValues(HashMap<String, Integer> map){
        SharedPreferences sp = AppManager.getInstance().peekActivity().getSharedPreferences("config.properties", Activity.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        for (String keys : map.keySet()){
            int value = map.get(keys);
            e.putInt(keys, value);
        }
        e.commit();
    }
}
