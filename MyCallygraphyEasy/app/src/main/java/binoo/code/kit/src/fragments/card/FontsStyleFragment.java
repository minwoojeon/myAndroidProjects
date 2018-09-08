package binoo.code.kit.src.fragments.card;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import binoo.code.kit.mycallygraphyeasy.R;
import binoo.code.kit.src.manager.AppGlobalData;
import binoo.code.kit.src.manager.AppManager;

/**
 * Created by binoo on 2017-12-28.
 */

public final class FontsStyleFragment extends Fragment {

    public static TextView editView;

    private FontsStyleFragment.OnListFragmentInteractionListener mListener;

    public FontsStyleFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_layer_card_font, container, false);
        editView = (TextView) view.findViewById(R.id.editView);
        // 폰트 리스트에서 선택하면 즉시 설정한다.
        final android.support.v7.widget.RecyclerView fontListView = (android.support.v7.widget.RecyclerView) view.findViewById(R.id.fontListView);
        if (fontListView instanceof android.support.v7.widget.RecyclerView) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 3);
            gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
            fontListView.setLayoutManager(gridLayoutManager);

            final class MyFontListRecyclerViewAdapter extends RecyclerView.Adapter<MyFontListRecyclerViewAdapter.ViewHolder> {

                private final List<Typeface> mValues = new ArrayList<Typeface>();
                private final List<String> mValueString = new ArrayList<String>();
                private final FontsStyleFragment.OnListFragmentInteractionListener mListener;

                public MyFontListRecyclerViewAdapter(FontsStyleFragment.OnListFragmentInteractionListener listener) {
                    Context context = view.getContext();
                    AssetManager assetManager = context.getAssets();
                    try {
                        String assetFontList[] = assetManager.list("fonts");
                        for(String str : assetFontList){
                            Typeface typeface = Typeface.createFromAsset( context.getAssets(), "fonts/"+str );
                            mValues.add( typeface );
                            mValueString.add(str);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mListener = listener;
                }

                @Override
                public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.layout_font_row, parent, false);
                    return new ViewHolder(view);
                }

                @Override
                public void onBindViewHolder(final ViewHolder holder, final int position) {
                    holder.mItem = mValues.get(position);
                    holder.txtView.setTypeface( mValues.get(position) );
                    holder.txtView.setText( mValueString.get(position).toLowerCase().replace(".ttf","") );
                    holder.txtView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_DOWN){
                                // 적용
                                View view = AppManager.getInstance().getView( AppGlobalData.VIEW_TAG_TEXTVIEW );
                                AppManager.getInstance().txtItem.put("fontName", "fonts/"+mValueString.get(position));
                                editView.setTypeface( mValues.get(position) );
                                if (view != null && view instanceof TextView){
                                    ((TextView) view).setTypeface( mValues.get(position) );
                                }

                                if (FontsTextFragment.editText != null){
                                    FontsTextFragment.editText.setTypeface( mValues.get(position) );
                                }
                                if (FontsColorFragment.editText != null){
                                    FontsColorFragment.editText.setTypeface( mValues.get(position) );
                                }
                            }
                            return false;
                        }
                    });

                    holder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (null != mListener) {
                                // Notify the active callbacks interface (the activity, if the
                                // fragment is attached to one) that an item has been selected.
                                mListener.onListFragmentInteraction(holder.mItem);
                            }
                        }
                    });
                }

                @Override
                public int getItemCount() {
                    return mValues.size();
                }

                class ViewHolder extends RecyclerView.ViewHolder {
                    public final View mView;
                    public final TextView txtView;
                    public Typeface mItem;

                    public ViewHolder(View view) {
                        super(view);
                        mView = view;
                        txtView = (TextView) view.findViewById(R.id.txtView);
                    }
                }
            }
            fontListView.setAdapter(new MyFontListRecyclerViewAdapter(mListener));
        }
        return view;
    }

    @Override
    public void onResume() {
        if (AppManager.getInstance().txtItem.get("content") != null){
            editView.setText( AppManager.getInstance().txtItem.get("content") + "" );
        }
        if (AppManager.getInstance().txtItem.get("fontName") != null){
            editView.setTypeface(Typeface.createFromAsset( this.getContext().getAssets(), AppManager.getInstance().txtItem.get("fontName") + "" ));
        }
        if (AppManager.getInstance().txtItem.get("fontColor") != null){
            editView.setTextColor( Integer.parseInt(AppManager.getInstance().txtItem.get("fontColor") + "") );
        }
        if (AppManager.getInstance().txtItem.get("fontSize") != null){
            editView.setTextSize( Integer.parseInt(AppManager.getInstance().txtItem.get("fontSize") + "") );
        }
        super.onResume();
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Typeface typeface);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            /*throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");*/
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
