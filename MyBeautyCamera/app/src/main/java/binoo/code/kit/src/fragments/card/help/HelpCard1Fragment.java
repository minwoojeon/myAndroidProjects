package binoo.code.kit.src.fragments.card.help;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import binoo.code.kit.picture.entertain.mybeautycamera.R;
import binoo.code.kit.src.manager.AppGlobalData;
import binoo.code.kit.src.manager.AppManager;

public final class HelpCard1Fragment extends Fragment {

    public HelpCard1Fragment() {
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

        View view = inflater.inflate(R.layout.fragment_help_card1, container, false);
        ((ImageView) view.findViewById(R.id.btnImgClose)).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    AppManager.getInstance().getView( AppGlobalData.VIEW_TAG_HELP_PAGER ).setVisibility(View.INVISIBLE);
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
