package binoo.code.kit.src.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import binoo.code.kit.src.manager.AppGlobalData;
import binoo.code.kit.src.manager.AppManager;
import binoo.code.kit.sudokugame.R;

/**
 * Created by binoo on 2018-01-11.
 * 이어하기/새로하기만 결정
 */

public final class GameChooseLoad extends Fragment {

    public GameChooseLoad() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_choose_load, container, false);

        Button buttonNew = (Button) view.findViewById(R.id.buttonNew);
        Button buttonLoad = (Button) view.findViewById(R.id.buttonLoad);
        Button buttonMain = (Button) view.findViewById(R.id.buttonMain);
        buttonNew.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    AppManager.getInstance().getItems().clear();
                    AppManager.getInstance().peekActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.mainLinear, new GameChooseLevel(), AppGlobalData.ACTIVITY_MAIN_FRAME_LAYOUT).commit();
                }
                return false;
            }
        });
        buttonLoad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    // 불러오기 부분 리스트뷰 출력으로 바꾸어야함.
                    AppManager.getInstance().getItems().clear();
                    AppManager.getInstance().peekActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.mainLinear, new NormalSudokuFragment(), AppGlobalData.ACTIVITY_MAIN_FRAME_LAYOUT).commit();
                }
                return false;
            }
        });
        buttonMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    AppManager.getInstance().getItems().clear();
                    AppManager.getInstance().peekActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.mainLinear, new GameMainFragment(), AppGlobalData.ACTIVITY_MAIN_FRAME_LAYOUT).commit();
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
