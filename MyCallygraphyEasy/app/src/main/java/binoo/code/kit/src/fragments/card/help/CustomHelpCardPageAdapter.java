package binoo.code.kit.src.fragments.card.help;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by binoo on 2018-01-05.
 */

public final class CustomHelpCardPageAdapter extends FragmentStatePagerAdapter {
    public CustomHelpCardPageAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        android.support.v4.app.Fragment fragment = null;
        switch ( position ){
            case 0:
                fragment = new HelpCard1Fragment();
                break;
            case 1:
                fragment = new HelpCard2Fragment();
                break;
            case 2:
                fragment = new HelpCard3Fragment();
                break;
            case 3:
                fragment = new HelpCard4Fragment();
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