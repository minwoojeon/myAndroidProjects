package binoo.code.kit.src.fragments.card;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import binoo.code.kit.mycallygraphyeasy.R;
import binoo.code.kit.src.manager.AppGlobalData;
import binoo.code.kit.src.manager.AppManager;

/**
 * Created by binoo on 2017-12-28.
 */

public final class FontsSizeFragment extends Fragment {

    public static TextView editText;
    private FontsSizeFragment.OnFragmentInteractionListener mListener;

    public FontsSizeFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_layer_card_size, container, false);
        // 크기를 설정하고 출력/임시저장한다.
        SeekBar seekBarSize = (SeekBar) view.findViewById(R.id.seekBarSize);
        Button btnAdd = (Button) view.findViewById(R.id.btnAdd);

        editText = (TextView) view.findViewById(R.id.editText);

        seekBarSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
                AppManager.getInstance().txtItem.put( "fontSize", progress+"" );
                editText.setTextSize( progress );

                if (FontsColorFragment.editText != null){
                    FontsColorFragment.editText.setTextSize( progress );
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        btnAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    AppManager.getInstance().getView( AppGlobalData.VIEW_TAG_VIEWPAGER ).setVisibility(View.INVISIBLE);
                    View view = AppManager.getInstance().getView( AppGlobalData.VIEW_TAG_TEXT_LAYOUT );
                    if (view != null && view instanceof FrameLayout){
                        String proctype = AppManager.getInstance().txtItem.get("proctype");
                        // 새로추가의 경우.
                        // 기존의 내용에 수정의 경우.

                        final String content = (AppManager.getInstance().txtItem.get("content") != null ? AppManager.getInstance().txtItem.get("content") + "" : "" );
                        final String fontName = (AppManager.getInstance().txtItem.get("fontName") != null ? AppManager.getInstance().txtItem.get("fontName") + "" : "" );
                        final String fontColor = (AppManager.getInstance().txtItem.get("fontColor") != null ? AppManager.getInstance().txtItem.get("fontColor") + "" : "0" );
                        final String fontSize = (AppManager.getInstance().txtItem.get("fontSize") != null ? AppManager.getInstance().txtItem.get("fontSize") + "" : "0" );

                        if (proctype == null || "".equals(proctype) || "add".equals(proctype)){

                            LayoutInflater layoutInflater = LayoutInflater.from( view.getContext() );
                            final View instanceView = layoutInflater.inflate(R.layout.layout_txt_item, ((FrameLayout)view), false);
                            final EditText editText = (EditText) instanceView.findViewById(R.id.editText);
                            final ImageView btnUnable = (ImageView) instanceView.findViewById(R.id.btnUnable);
                            final ImageView btnEdit = (ImageView) instanceView.findViewById(R.id.btnEdit);
                            final ImageView btnAlign = (ImageView) instanceView.findViewById(R.id.btnAlign);
                            final ImageView btnRotate = (ImageView) instanceView.findViewById(R.id.btnRotate);

                            String tagBar = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
                            instanceView.setTag( Long.parseLong(tagBar+"0") );
                            editText.setTag( Long.parseLong(tagBar+"1") );
                            btnUnable.setTag( Long.parseLong(tagBar+"2") );
                            btnEdit.setTag( Long.parseLong(tagBar+"3") );
                            btnAlign.setTag( Long.parseLong(tagBar+"4") );
                            btnRotate.setTag( Long.parseLong(tagBar+"5") );

                            editText.setText( content );
                            editText.setTypeface(Typeface.createFromAsset( view.getContext().getAssets(), fontName ));
                            editText.setTextColor( Integer.parseInt(fontColor) );
                            editText.setTextSize( Integer.parseInt(fontSize) );

                            View.OnTouchListener onTouchListener = new View.OnTouchListener() {
                                int alignment = 0;
                                float viewX;
                                float viewY;
                                float preX;
                                float preY;
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    if (event.getAction() == MotionEvent.ACTION_DOWN){
                                        // 클릭된 instanceView 만 옵션 선택 기능이 발생.
                                        List<View> viewList = AppManager.getInstance().viewList;

                                        if (v.getTag() == btnUnable.getTag()){
                                            // 안보이기
                                            instanceView.setVisibility(View.INVISIBLE);
                                            instanceView.destroyDrawingCache();
                                            for (int i=0; i<viewList.size(); i++){
                                                View findView = viewList.get(i);
                                                if (findView.getTag() == v.getTag()){
                                                    viewList.remove(i);
                                                    break;
                                                }
                                            }
                                            System.gc();
                                        } else if (v.getTag() == btnEdit.getTag()){
                                            AppManager.getInstance().txtItem.clear();
                                            AppManager.getInstance().txtItem.put("content", editText.getText()+"");
                                            AppManager.getInstance().txtItem.put("fontName", fontName);
                                            AppManager.getInstance().txtItem.put("fontColor", editText.getCurrentTextColor()+"");
                                            AppManager.getInstance().txtItem.put("fontSize", (int)editText.getTextSize()+"");

                                            AppManager.getInstance().txtItem.put("proctype", "modify");
                                            AppManager.getInstance().txtItem.put("viewtag", instanceView.getTag()+"");
                                            final class CustomPagerAdapter extends FragmentStatePagerAdapter {
                                                public CustomPagerAdapter(FragmentManager fm){
                                                    super(fm);
                                                }
                                                @Override
                                                public Fragment getItem(int position) {
                                                    android.support.v4.app.Fragment fragment = null;
                                                    switch ( position ){
                                                        case 0:
                                                            fragment = new FontsTextFragment();
                                                            break;
                                                        case 1:
                                                            fragment = new FontsStyleFragment();
                                                            break;
                                                        case 2:
                                                            fragment = new FontsColorFragment();
                                                            break;
                                                        case 3:
                                                            fragment = new FontsSizeFragment();
                                                            break;
                                                        default:
                                                            break;
                                                    }
                                                    return fragment;
                                                }
                                                @Override
                                                public int getCount() {
                                                    return 4;
                                                }
                                            }
                                            AppManager.getInstance().setView( AppGlobalData.VIEW_TAG_TEXTVIEW, editText );
                                            View viewPager = AppManager.getInstance().getView( AppGlobalData.VIEW_TAG_VIEWPAGER );
                                            if (viewPager instanceof ViewPager){
                                                ((ViewPager) viewPager).setAdapter( new CustomPagerAdapter( AppManager.getInstance().peekActivity().getSupportFragmentManager() ));
                                                ((ViewPager) viewPager).setVisibility( View.VISIBLE );
                                            }
                                        } else if (v.getTag() == btnAlign.getTag()){
                                            // 정렬
                                            editText.setTextAlignment(
                                                    (alignment == 0 ? EditText.TEXT_ALIGNMENT_TEXT_START :
                                                            alignment == 1 ? EditText.TEXT_ALIGNMENT_TEXT_END :
                                                                    EditText.TEXT_ALIGNMENT_CENTER)
                                            );
                                            if (alignment == 0){
                                                alignment++;
                                                btnAlign.setImageResource(R.drawable.text_align_left);
                                            } else if (alignment == 1){
                                                btnAlign.setImageResource(R.drawable.text_align_right);
                                                alignment++;
                                            } else {
                                                alignment = 0;
                                                btnAlign.setImageResource(R.drawable.text_align_center);
                                            }
                                        } else if (v.getTag() == btnRotate.getTag()){
                                            // 회전
                                            viewX = event.getX();
                                            viewY = event.getY();
                                            preX = event.getX();
                                            preY = event.getY();
                                            return true;
                                        } else if (v.getTag() == instanceView.getTag()){
                                            viewX = event.getX();
                                            viewY = event.getY();

                                            for (int i=0; i<viewList.size(); i++){
                                                View findView = viewList.get(i);
                                                if ( v.getTag() != findView.getTag() ){
                                                    if (findView instanceof LinearLayout){
                                                        LinearLayout instanceView = ((LinearLayout) findView);
                                                        final ImageView btnUnable = (ImageView) instanceView.findViewById(R.id.btnUnable);
                                                        final ImageView btnEdit = (ImageView) instanceView.findViewById(R.id.btnEdit);
                                                        final ImageView btnAlign = (ImageView) instanceView.findViewById(R.id.btnAlign);
                                                        final ImageView btnRotate = (ImageView) instanceView.findViewById(R.id.btnRotate);

                                                        btnUnable.setVisibility(View.INVISIBLE);
                                                        btnEdit.setVisibility(View.INVISIBLE);
                                                        btnAlign.setVisibility(View.INVISIBLE);
                                                        btnRotate.setVisibility(View.INVISIBLE);
                                                    }
                                                } else {
                                                    LinearLayout instanceView = ((LinearLayout) findView);
                                                    final ImageView btnUnable = (ImageView) instanceView.findViewById(R.id.btnUnable);
                                                    final ImageView btnEdit = (ImageView) instanceView.findViewById(R.id.btnEdit);
                                                    final ImageView btnAlign = (ImageView) instanceView.findViewById(R.id.btnAlign);
                                                    final ImageView btnRotate = (ImageView) instanceView.findViewById(R.id.btnRotate);

                                                    btnUnable.setVisibility(View.VISIBLE);
                                                    btnEdit.setVisibility(View.VISIBLE);
                                                    btnAlign.setVisibility(View.VISIBLE);
                                                    btnRotate.setVisibility(View.VISIBLE);
                                                }
                                            }
                                            return true;
                                        }
                                    } else if (event.getAction() == MotionEvent.ACTION_MOVE){
                                        if (v.getTag() == btnRotate.getTag()){
                                            float dx = event.getX() - viewX;
                                            float dy = event.getY() - viewY;
                                        /*dx = (dx <0 ? -dx : dx);
                                        dy = (dy <0 ? -dy : dy);*/
                                            if (dx != 0 && dy != 0){
                                                float dgree = instanceView.getRotation() + (float) Math.atan2( dy, dx );
                                                if (dgree >= 180){
                                                    dgree -= 180;
                                                } else if (dgree <= -180){
                                                    dgree += 180;
                                                }
                                                instanceView.setRotation( dgree );
                                            }
                                            preX = event.getX();
                                            preY = event.getY();
                                        } else if (v.getTag() == instanceView.getTag()){
                                            v.setX(event.getRawX() - viewX);
                                            v.setY(event.getRawY() - (viewY + v.getHeight()));
                                        }
                                        return true;
                                    } else if (event.getAction() == MotionEvent.ACTION_UP){

                                        if (v.getTag() == btnRotate.getTag()){

                                        } else if (v.getTag() == instanceView.getTag()){
                                            int width = ((ViewGroup) v.getParent()).getWidth() - v.getWidth();
                                            int height = ((ViewGroup) v.getParent()).getHeight() - v.getHeight();

                                            if (v.getX() > width && v.getY() > height) {
                                                v.setX(width);
                                                v.setY(height);
                                            } else if (v.getX() < 0 && v.getY() > height) {
                                                v.setX(0);
                                                v.setY(height);
                                            } else if (v.getX() > width && v.getY() < 0) {
                                                v.setX(width);
                                                v.setY(0);
                                            } else if (v.getX() < 0 && v.getY() < 0) {
                                                v.setX(0);
                                                v.setY(0);
                                            } else if (v.getX() < 0 || v.getX() > width) {
                                                if (v.getX() < 0) {
                                                    v.setX(0);
                                                    v.setY(event.getRawY() - viewX - v.getHeight());
                                                } else {
                                                    v.setX(width);
                                                    v.setY(event.getRawY() - viewY - v.getHeight());
                                                }
                                            } else if (v.getY() < 0 || v.getY() > height) {
                                                if (v.getY() < 0) {
                                                    v.setX(event.getRawX() - viewX);
                                                    v.setY(0);
                                                } else {
                                                    v.setX(event.getRawX() - viewY);
                                                    v.setY(height);
                                                }
                                            }
                                        }
                                        return true;
                                    }
                                    return false;
                                }
                            };

                            btnUnable.setOnTouchListener(onTouchListener);
                            btnEdit.setOnTouchListener(onTouchListener);
                            btnAlign.setOnTouchListener(onTouchListener);
                            btnRotate.setOnTouchListener(onTouchListener);
                            instanceView.setOnTouchListener(onTouchListener);

                            ((FrameLayout) view).addView(instanceView);

                            List<View> viewList = AppManager.getInstance().viewList;

                            for (int i=0; i<viewList.size(); i++){
                                View findView = viewList.get(i);
                                if ( v.getTag() != findView.getTag() ){
                                    if (findView instanceof LinearLayout){
                                        LinearLayout innerInstanceView = ((LinearLayout) findView);
                                        final ImageView innerBtnUnable = (ImageView) innerInstanceView.findViewById(R.id.btnUnable);
                                        final ImageView innerBtnEdit = (ImageView) innerInstanceView.findViewById(R.id.btnEdit);
                                        final ImageView innerBtnAlign = (ImageView) innerInstanceView.findViewById(R.id.btnAlign);
                                        final ImageView innerBtnRotate = (ImageView) innerInstanceView.findViewById(R.id.btnRotate);

                                        innerBtnUnable.setVisibility(View.INVISIBLE);
                                        innerBtnEdit.setVisibility(View.INVISIBLE);
                                        innerBtnAlign.setVisibility(View.INVISIBLE);
                                        innerBtnRotate.setVisibility(View.INVISIBLE);
                                    }
                                }
                            }
                            AppManager.getInstance().viewList.add( instanceView );

                        } else if ("modify".equals(proctype)){

                            long viewTag = Long.parseLong(AppManager.getInstance().txtItem.get("viewtag"));
                            List<View> viewList = AppManager.getInstance().viewList;

                            for (int i=0; i<viewList.size(); i++){
                                View findView = viewList.get(i);
                                if ( viewTag == Long.parseLong(findView.getTag()+"") ){
                                    if (findView instanceof LinearLayout){
                                        LinearLayout instanceView = ((LinearLayout) findView);
                                        final EditText editText = (EditText) instanceView.findViewById(R.id.editText);
                                        editText.setText( content );
                                        editText.setTypeface(Typeface.createFromAsset( view.getContext().getAssets(), fontName ));
                                        editText.setTextColor( Integer.parseInt(fontColor) );
                                        editText.setTextSize( Integer.parseInt(fontSize) );
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        if (AppManager.getInstance().txtItem.get("content") != null){
            editText.setText( AppManager.getInstance().txtItem.get("content") + "" );
        }
        if (AppManager.getInstance().txtItem.get("fontName") != null){
            editText.setTypeface(Typeface.createFromAsset( this.getContext().getAssets(), AppManager.getInstance().txtItem.get("fontName") + "" ));
        }
        if (AppManager.getInstance().txtItem.get("fontColor") != null){
            editText.setTextColor( Integer.parseInt(AppManager.getInstance().txtItem.get("fontColor") + "") );
        }
        if (AppManager.getInstance().txtItem.get("fontSize") != null){
            editText.setTextSize( Integer.parseInt(AppManager.getInstance().txtItem.get("fontSize") + "") );
        }
        super.onResume();
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
        if (context instanceof FontsSizeFragment.OnFragmentInteractionListener) {
            mListener = (FontsSizeFragment.OnFragmentInteractionListener) context;
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
