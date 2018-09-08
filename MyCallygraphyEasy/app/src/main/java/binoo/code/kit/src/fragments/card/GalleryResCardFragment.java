package binoo.code.kit.src.fragments.card;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import binoo.code.kit.mycallygraphyeasy.R;
import binoo.code.kit.src.manager.AppGlobalData;
import binoo.code.kit.src.manager.AppManager;

/**
 * Created by binoo on 2017-12-30.
 */

public final class GalleryResCardFragment extends Fragment {

    private GalleryResCardFragment.OnListFragmentInteractionListener mListener;

    public GalleryResCardFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_layer_card_res_img, container, false);
        final android.support.v7.widget.RecyclerView recyclerView = (android.support.v7.widget.RecyclerView) view.findViewById(R.id.recyclerView);
        if (recyclerView instanceof android.support.v7.widget.RecyclerView){
            GridLayoutManager gridLayoutManager = new GridLayoutManager( view.getContext(), 2 );
            gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(gridLayoutManager);

            final class MyGlaphicListRecyclerViewAdapter extends RecyclerView.Adapter<MyGlaphicListRecyclerViewAdapter.ViewHolder> {

                private final List<String> mValues = new ArrayList<String>();
                private final GalleryResCardFragment.OnListFragmentInteractionListener mListener;
                final AssetManager assetManager;

                public MyGlaphicListRecyclerViewAdapter(GalleryResCardFragment.OnListFragmentInteractionListener listener) {
                    Context context = view.getContext();
                    assetManager = context.getAssets();
                    try {
                        String assetFontList[] = assetManager.list("imgs");
                        for(String str : assetFontList){
                            mValues.add( "imgs/"+str );
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mListener = listener;
                }

                @Override
                public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.layout_grid_img_form, parent, false);
                    return new ViewHolder(view);
                }

                @Override
                public void onBindViewHolder(final ViewHolder holder, final int position) {
                    holder.mItem = mValues.get(position);
                    try {
                        holder.imgContent.setImageBitmap( getBitmap(position) );
                    } catch (Exception e) {
                        holder.imgContent.setImageResource(R.drawable.icon_fail);
                        e.printStackTrace();
                    }
                    holder.imgContent.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_DOWN){
                                // 적용
                                View view = AppManager.getInstance().getView( AppGlobalData.VIEW_TAG_IMAGEVIEW );
                                if (view != null && view instanceof ImageView){
                                    try {
                                        ((ImageView) view).setImageBitmap( getBitmap(position) );
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
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

                public Bitmap getBitmap(int position) throws IOException {
                    return BitmapFactory.decodeStream(assetManager.open( mValues.get(position) ));
                }

                @Override
                public int getItemCount() {
                    return mValues.size();
                }

                class ViewHolder extends RecyclerView.ViewHolder {
                    public final View mView;
                    public final ImageView imgContent;
                    public String mItem;

                    public ViewHolder(View view) {
                        super(view);
                        mView = view;
                        imgContent = (ImageView) view.findViewById(R.id.imgContent);
                    }
                }
            }
            recyclerView.setAdapter(new MyGlaphicListRecyclerViewAdapter(mListener));
        }

        final Button btnProccess = (Button) view.findViewById(R.id.btnProccess);

        btnProccess.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    AppManager.getInstance().getView( AppGlobalData.VIEW_TAG_SHOW_VIEW ).setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });
        return view;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(String str);
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