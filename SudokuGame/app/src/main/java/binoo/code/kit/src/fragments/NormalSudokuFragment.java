package binoo.code.kit.src.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import binoo.code.kit.src.manager.AppGlobalData;
import binoo.code.kit.src.manager.AppManager;
import binoo.code.kit.src.utils.GameUtils;
import binoo.code.kit.src.views.CustomBinooTextView;
import binoo.code.kit.sudokugame.R;

public final class NormalSudokuFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    Integer[][] sudokuMap;
    Integer[][] maskMap;
    CustomBinooTextView[][] cationViews;
    char[] effectSudoku;
    String[] imgSudoku;
    int choose = 11;
    final int extype;

    public NormalSudokuFragment() {
        // Required empty public constructor
        GameUtils gameUtils = new GameUtils();
        String arg = AppManager.getInstance().getItems().get("isload")+"";
        if ("1".equals(arg)){
            // 불러오기 게임
            extype = 0;
        } else {
            // 새게임
            int len = Integer.parseInt(AppManager.getInstance().getItems().get("len")+"");              // 4x4 , 9x9 등
            int level = Integer.parseInt(AppManager.getInstance().getItems().get("level")+"");          // 난이도
            extype = Integer.parseInt(AppManager.getInstance().getItems().get("extype")+"");            // 0 : 노말, 1 : 문자, 2 : 그림
            int result = (len+1)*0x10 + level;
            HashMap<Integer, Object> gameMap = gameUtils.makeNormalGameMap(result);
            if (gameMap.get('s'-0) != null){
                if ("S".equals(gameMap.get('s'-0)+"")){
                    //
                    gameMap.get('y'-0);// 타입
                    sudokuMap = (Integer[][]) gameMap.get('m'-0);// 게임맵
                    maskMap = (Integer[][]) gameMap.get('g'-0);// 마스크
                    String time = (String)gameMap.get('d'-0);// 최초생성시간
                    if (len == 0){
                        cationViews = new CustomBinooTextView[4][4];
                    } else {
                        cationViews = new CustomBinooTextView[9][9];
                    }

                    // 노말스도쿠 - 없음
                    // 문자스도쿠 - 문자(9개) 세팅
                    int val = (int)(Math.random()*5);
                    switch (val){
                        case 0:
                            effectSudoku = "education".toUpperCase().toCharArray();
                            break;
                        case 1:
                            effectSudoku = "establish".toUpperCase().toCharArray();
                            break;
                        case 2:
                            effectSudoku = "frendship".toUpperCase().toCharArray();
                            break;
                        case 3:
                            effectSudoku = "becausofy".toUpperCase().toCharArray();
                            break;
                        case 4:
                            effectSudoku = "abcdefghi".toUpperCase().toCharArray();
                            break;
                        case 5:
                            effectSudoku = "copyright".toUpperCase().toCharArray();
                            break;
                        default:
                            break;
                    }
                    // 그림스도쿠 - 그림(9개) 세팅
                    // 부등식 스도쿠 - 부등식 레이어 세팅
                    // 킬러 스도쿠 - 킬러 숫자 세팅
                    return;
                }
            }
            // 오류처리
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_normal_sudoku, container, false);
        // 초기화
        // 스도쿠 배열크기만큼 커스텀뷰를 생성하고, 마스크 배열값에 따라 수정가능 여부를 체크한다.
        // 타입에 따라서 기초정보를 삽입한다.
        // 노말스도쿠 - 없음
        // 문자스도쿠 - 숫자 -> 문자(9개) 세팅
        // 그림스도쿠 - 숫자 -> 그림(9개) 세팅
        // 부등식 스도쿠 - 부등식 레이어 세팅
        // 킬러 스도쿠 - 킬러 숫자 세팅
        int len = sudokuMap.length;
        int viewWidth = view.getWidth();
        int viewHeight = view.getHeight();
        final LinearLayout gameLayout = view.findViewById(R.id.gameSpace);
        Log.d("wh", "w"+viewWidth+" h"+viewHeight);

        for(int i=0; i<len; i++){
            // 레이아웃 추가
            LinearLayout linearLayout = new LinearLayout(view.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(view.getLayoutParams());
            layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.weight = 1;
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setLayoutParams( layoutParams );

            if (gameLayout != null){
                for (int j=0; j<len; j++){
                    // 뷰 추가
                    if (extype == 0){
                        cationViews[i][j] = new CustomBinooTextView(linearLayout.getContext(), linearLayout, sudokuMap[i][j]);
                    } else if (extype == 1){
                        // 문자
                        cationViews[i][j] = new CustomBinooTextView(linearLayout.getContext(), linearLayout, sudokuMap[i][j], this.effectSudoku);
                    } else if (extype == 2){
                        // 그림
                        String[] assetsArr = null;
                        cationViews[i][j] = new CustomBinooTextView(linearLayout.getContext(), linearLayout, sudokuMap[i][j], assetsArr);
                    }

                    if (maskMap[i][j] == 0){
                        // 미리 보여주는 데이터라면,
                        cationViews[i][j].setWall(true);
                        cationViews[i][j].setGuess( sudokuMap[i][j] );
                    } else {
                        final int chooseItem = i*10+j;
                        cationViews[i][j].getView().setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                if (event.getAction() == MotionEvent.ACTION_DOWN){
                                    choose = chooseItem;
                                }
                                return false;
                            }
                        });
                    }
                    linearLayout.addView(cationViews[i][j].getView());
                }
                gameLayout.addView(linearLayout);
            }
        }

        // *기능
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            boolean isMemo = true;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 메모작성
                // -- 입력된 숫자 삭제
                // -- 한번더 누르면 메모 지우기
                // 숫자 기입
                // -- 입력된 메모 삭제
                // -- 모든숫자 기입했는지 확인. 모든숫자를 기입하고, 모두 맞으면 게임종료 화면으로 이동.
                // -- 한번더 누르면 숫자 지우기
                // 공유하기
                // 저장하기
                // 처음으로
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    if ((int)v.getTag() >= 0 && (int)v.getTag() < sudokuMap.length){
                        if (isMemo){
                            cationViews[choose/10][choose%10].setMemo((int)v.getTag());
                        } else {
                            cationViews[choose/10][choose%10].setGuess((int)v.getTag());
                            boolean isAll = true;
                            finish:
                            for (CustomBinooTextView bct[] : cationViews){
                                for (CustomBinooTextView instance : bct){
                                    if (!instance.isRight()){
                                        isAll = false;
                                        break finish;
                                    }
                                }
                            }
                            if (isAll){
                                // 모두 맞은 경우에 게임이 종료됩니다.
                                Log.d("end","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                                AppManager.getInstance().peekActivity().getSupportFragmentManager().beginTransaction().
                                        replace(R.id.mainLinear, new GameMainFragment(), AppGlobalData.ACTIVITY_MAIN_FRAME_LAYOUT).commit();
                            }
                        }
                    } else if ((int)v.getTag() == R.id.btnMemo){
                        isMemo = !isMemo;
                        if (isMemo){
                            ((Button) v).setText("memo");
                        } else {
                            ((Button) v).setText("guess");
                        }
                    } else if ((int)v.getTag() == R.id.btnDel){
                        cationViews[choose/10][choose%10].setGuess(-1);
                    } else if ((int)v.getTag() == R.id.btnReset){
                        // 다시 이 프레그먼트로 이동
                        AppManager.getInstance().peekActivity().getSupportFragmentManager().beginTransaction().
                                replace(R.id.mainLinear, new NormalSudokuFragment(), AppGlobalData.ACTIVITY_MAIN_FRAME_LAYOUT).commit();
                    } else if ((int)v.getTag() == R.id.btnSave){
                        // 저장
                    } else if ((int)v.getTag() == R.id.btnShare){
                        // 공유
                        FileOutputStream fos = null;
                        boolean finish = false;
                        try {
                            gameLayout.buildDrawingCache();
                            Bitmap gameView = gameLayout.getDrawingCache();
                            File f = new File(Environment.getExternalStorageDirectory().toString() + "/data");
                            if (!f.exists()) f.mkdirs();
                            f = new File(Environment.getExternalStorageDirectory().toString() + "/data/img");
                            if (!f.exists()) f.mkdirs();
                            String fileName = "/data/img/MHB_CG_"+new SimpleDateFormat("yyyyMMddHHMMssSSS", Locale.KOREA).format(new Date())+".jpg";
                            fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString()+ fileName);

                            gameView.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                            Uri uri = FileProvider.getUriForFile(
                                    AppManager.getInstance().peekActivity().getBaseContext(),
                                    AppManager.getInstance().peekActivity().getBaseContext().getApplicationContext().getPackageName() + ".fileprovider",
                                    new File(fileName));
                            Intent shareIntent = new Intent();
                            shareIntent.setAction(Intent.ACTION_SEND);
                            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                            shareIntent.setType("image/jpeg");
                            startActivity(Intent.createChooser(shareIntent, AppManager.getInstance().peekActivity().getResources().getString(R.string.success_img_share)));
                            finish = true;

                            fos.flush();
                            fos.close();
                        } catch (FileNotFoundException e) {
                            finish = false;
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (fos!=null) fos = null;
                            System.gc();
                        }
                        if (finish){
                            // 공유 성공
                        } else {
                            // 공유 실패
                        }
                    } else if ((int)v.getTag() == R.id.btnMain){
                        // 메인
                        AppManager.getInstance().peekActivity().
                            getSupportFragmentManager().beginTransaction().replace(R.id.mainLinear, new GameMainFragment(), AppGlobalData.ACTIVITY_MAIN_FRAME_LAYOUT).commit();
                    } else if ((int)v.getTag() == R.id.btnInfo){
                        // 정보
                        // 슬라이드 형태로 출력
                    }
                }
                return false;
            }
        };
        int[] btnRes = new int[]{
                R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnMemo, R.id.btnDel, R.id.btnReset, R.id.btnSave, R.id.btnShare, R.id.btnMain, R.id.btnInfo,
        };
        for (int i=0; i<btnRes.length; i++){
            Button btn = (Button) view.findViewById(btnRes[i]);
            if (extype == 0){
                //
            } else if (extype == 1){
                // 문자
                if (effectSudoku.length > i)  btn.setText( effectSudoku[i] );
            } else if (extype == 2){
                // 그림
                if (effectSudoku.length > i) {
                    btn.setText( "" );
                    try {
                        btn.setBackground(Drawable.createFromStream( view.getContext().getAssets().open(imgSudoku[i]), null ));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (i < sudokuMap.length){
                btn.setTag( i );
            } else {
                btn.setTag( btnRes[i] );
            }
            btn.setOnTouchListener(onTouchListener);
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
