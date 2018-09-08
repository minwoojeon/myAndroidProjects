package binoo.code.kit.src.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import binoo.code.kit.src.manager.AppGlobalData;
import binoo.code.kit.src.manager.AppManager;
import binoo.code.kit.sudokugame.R;

/**
 * Created by binoo on 2018-01-11.]
 * 4x4 / 9x9 중 결정
 * 세부
 * 게임선택(어떤 스도쿠)
 * 게임 난이도 선택
 * 게임시작 버튼
 */

public final class GameChooseLevel extends Fragment {

    public GameChooseLevel() {
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
        final View view = inflater.inflate(R.layout.fragment_game_choose_level, container, false);
        final RadioButton rbtn4 = (RadioButton) view.findViewById(R.id.rbtn4);
        final RadioButton rbtn9 = (RadioButton) view.findViewById(R.id.rbtn9);
        final Spinner spinnerType = (Spinner) view.findViewById(R.id.spinnerType);
        final Spinner spinnerLevel = (Spinner) view.findViewById(R.id.spinnerLevel);
        Button btnNew = (Button) view.findViewById(R.id.btnNew);

        rbtn4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    // 할 수 있는 게임형태 리스트 변경
                    // 할 수 있는 난이도 리스트 변경
                    // 기존 삽입된 정보 초기화
                    AppManager.getInstance().getItems().clear();

                    final ArrayAdapter typeAdapter = ArrayAdapter.createFromResource(
                            view.getContext(), R.array.list_game_type_4, android.R.layout.simple_spinner_dropdown_item);
                    typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerType.setAdapter( typeAdapter );
                    spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            AppManager.getInstance().getItems().put("len", position);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            AppManager.getInstance().getItems().put("len", 0);
                        }
                    });

                    final ArrayAdapter levelAdapter = ArrayAdapter.createFromResource(
                            view.getContext(), R.array.list_game_level_4, android.R.layout.simple_spinner_dropdown_item);
                    levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerLevel.setAdapter( levelAdapter );
                    spinnerLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            AppManager.getInstance().getItems().put("level", position);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            AppManager.getInstance().getItems().put("level", 0);
                        }
                    });
                }
            }
        });
        rbtn9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    // 할 수 있는 게임형태 리스트 변경
                    // 할 수 있는 난이도 리스트 변경
                    // 기존 삽입된 정보 초기화
                    AppManager.getInstance().getItems().clear();

                    final ArrayAdapter typeAdapter = ArrayAdapter.createFromResource(
                            view.getContext(), R.array.list_game_type_9, android.R.layout.simple_spinner_dropdown_item);
                    typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerType.setAdapter( typeAdapter );
                    spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            AppManager.getInstance().getItems().put("len", position);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            AppManager.getInstance().getItems().put("len", 0);
                        }
                    });

                    final ArrayAdapter levelAdapter = ArrayAdapter.createFromResource(
                            view.getContext(), R.array.list_game_level_9, android.R.layout.simple_spinner_dropdown_item);
                    levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerLevel.setAdapter( levelAdapter );
                    spinnerLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            AppManager.getInstance().getItems().put("level", position);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            AppManager.getInstance().getItems().put("level", 0);
                        }
                    });
                }
            }
        });

        btnNew.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    AppManager.getInstance().getItems().put("extype", 0);
                    if (AppManager.getInstance().getItems().get("level") == null){
                        AppManager.getInstance().getItems().put("level", 0);
                    }
                    if (AppManager.getInstance().getItems().get("len") == null){
                        AppManager.getInstance().getItems().put("len", 0);
                    }
                    AppManager.getInstance().peekActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.mainLinear, new NormalSudokuFragment(), AppGlobalData.ACTIVITY_MAIN_FRAME_LAYOUT).commit();
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
