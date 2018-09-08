package binoo.code.kit.src.views;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import binoo.code.kit.src.manager.AppManager;
import binoo.code.kit.sudokugame.R;

/**
 * Created by binoo on 2018-01-07.
 */

public final class CustomBinooTextView {

    private final int value;
    private final View view;
    private ImageView imageView;
    private TextView txtView;
    private ImageView imgView;
    private final TextView[] txtLittles;
    private final ImageView[] imgLittles;
    private final char[] charArr;
    private final String[] imgArr;
    private final int dep;
    private boolean isRight = false;
    private boolean isWall = false;

    public CustomBinooTextView(Context context, ViewGroup container, int value){
        this.value = value;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.view_text_custom_binoo, container, false);
        imageView = (ImageView) view.findViewById(R.id.imgView);
        txtView = (TextView) view.findViewById(R.id.txtView);
        // txtView = (TextView) view.findViewById(R.id.txtView);
        txtView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/NanumSquareRoundB.ttf"));
        txtView.setText("");
        int[] littleTxtRes = new int[]{
                R.id.txtLittle1, R.id.txtLittle2, R.id.txtLittle3, R.id.txtLittle4, R.id.txtLittle5,
                R.id.txtLittle6, R.id.txtLittle7, R.id.txtLittle8, R.id.txtLittle9
        };
        txtLittles = new TextView[littleTxtRes.length];
        for (int i=0; i<littleTxtRes.length; i++){
            txtLittles[i] = (TextView) view.findViewById( littleTxtRes[i] );
            txtLittles[i].setVisibility(View.INVISIBLE);
            txtLittles[i].setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/NanumSquareRoundB.ttf"));
        }
        charArr = "         ".toCharArray();
        this.imgArr = null;
        imgLittles = null;
        dep = 0;
    }
    public CustomBinooTextView(Context context, ViewGroup container, int value, char[] charArr){
        this.value = value;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.view_text_custom_binoo, container, false);
        imageView = (ImageView) view.findViewById(R.id.imgView);
        txtView = (TextView) view.findViewById(R.id.txtView);
        // imgView = (ImageView) view.findViewById(R.id.imgView);
        txtView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/NanumSquareRoundB.ttf"));
        int[] littleTxtRes = new int[]{
                R.id.txtLittle1, R.id.txtLittle2, R.id.txtLittle3, R.id.txtLittle4, R.id.txtLittle5,
                R.id.txtLittle6, R.id.txtLittle7, R.id.txtLittle8, R.id.txtLittle9
        };
        txtLittles = new TextView[littleTxtRes.length];
        for (int i=0; i<littleTxtRes.length; i++){
            txtLittles[i] = (TextView) view.findViewById( littleTxtRes[i] );
            txtLittles[i].setVisibility(View.INVISIBLE);
            txtLittles[i].setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/NanumSquareRoundB.ttf"));
            txtLittles[i].setText(charArr[i]);
        }
        this.imgArr = null;
        imgLittles = null;
        this.charArr = charArr;
        dep = 1;
    }
    public CustomBinooTextView(Context context, ViewGroup container, int value, String[] imgArr){
        this.value = value;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.view_text_custom_binoo, container, false);
        imageView = (ImageView) view.findViewById(R.id.imgView);
        txtView = (TextView) view.findViewById(R.id.txtView);
        imgView = (ImageView) view.findViewById(R.id.imgView);
        txtView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/NanumSquareRoundB.ttf"));
        int[] littleImgRes = new int[]{
                R.id.txtLittle1, R.id.txtLittle2, R.id.txtLittle3, R.id.txtLittle4, R.id.txtLittle5,
                R.id.txtLittle6, R.id.txtLittle7, R.id.txtLittle8, R.id.txtLittle9
        };
        imgLittles = new ImageView[littleImgRes.length];
        for (int i=0; i<littleImgRes.length; i++){
            imgLittles[i] = (ImageView) view.findViewById( littleImgRes[i] );
            imgLittles[i].setVisibility(View.INVISIBLE);
            try {
                imgLittles[i].setImageBitmap(BitmapFactory.decodeStream(context.getAssets().open(imgArr[i])));
            } catch (IOException e) {
                e.printStackTrace();
            }
            // imgLittles[i].setImageBitmap();
        }
        imgView.setVisibility(View.INVISIBLE);
        txtLittles = null;
        this.imgArr = imgArr;
        charArr = "         ".toCharArray();
        dep = 2;
    }

    public int getValue(){
        return value;
    }
    public boolean isRight(){
        return isRight;
    }
    public void setViewColor(int color){
        imageView.setColorFilter( color );
    }
    public void setWall(boolean isWall){
        // 초기에 값이 들어간 친구들은 수정을 할 수 없다.
        this.isWall = isWall;
        if (isWall){
            if (dep != 2){
                for (TextView txt : txtLittles){
                    txt.setVisibility(TextView.INVISIBLE);
                }
                txtView.setVisibility( TextView.VISIBLE );
                txtView.setText( (dep == 0 ? value+"" : charArr[value-1]+"") );
            } else {
                for (ImageView img : imgLittles){
                    img.setVisibility(ImageView.INVISIBLE);
                }
                imgView.setVisibility( TextView.VISIBLE );
                try {
                    imgView.setImageBitmap(BitmapFactory.decodeStream(view.getContext().getAssets().open(imgArr[value-1])));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            isRight = true;
        }
    }
    // X스도쿠의 경우 바인드 색상값의 변환
    // 킬러스도쿠의 경우 오버 뷰의 숫자값 출력
    // 스도쿠 배경/스도쿠 점선등의 효과
    // 메모
    public void setMemo(int num){
        if (!isWall){
            if (num >=0 && num < 9){
                if (dep != 2){
                    txtLittles[num].setVisibility( (txtLittles[num].getVisibility() == TextView.VISIBLE ? TextView.INVISIBLE : TextView.VISIBLE) );
                    txtView.setVisibility( TextView.INVISIBLE );
                } else {
                    imgLittles[num].setVisibility( (imgLittles[num].getVisibility() == ImageView.VISIBLE ? ImageView.INVISIBLE : ImageView.VISIBLE) );
                    imgView.setVisibility( ImageView.INVISIBLE );
                }
            } else {
                Log.d("error","input over");
            }
        }
    }
    public void setGuess(int num){
        if (!isWall){
            if (num >=0 && num < 9){
                if (dep != 2){
                    for (TextView txt : txtLittles){
                        txt.setVisibility(TextView.INVISIBLE);
                    }
                    txtView.setVisibility( TextView.VISIBLE );
                    txtView.setText( (dep == 0 ? (num+1)+"" : charArr[num]+"") );
                } else {
                    for (ImageView img : imgLittles){
                        img.setVisibility(ImageView.INVISIBLE);
                    }
                    imgView.setVisibility( TextView.VISIBLE );
                    try {
                        imgView.setImageBitmap(BitmapFactory.decodeStream(view.getContext().getAssets().open(imgArr[num])));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (num == -1) {
                if (dep != 2){
                    for (TextView txt : txtLittles){
                        txt.setVisibility(TextView.INVISIBLE);
                    }
                    txtView.setVisibility( TextView.INVISIBLE );
                } else {
                    for (ImageView img : imgLittles){
                        img.setVisibility(ImageView.INVISIBLE);
                    }
                    imgView.setVisibility( TextView.INVISIBLE );
                }
            } else {
                    Log.d("error","input over");
            }
            isRight = value == num+1;
        }
    }
    public View getView(){
        return view;
    }
}
