package ie.fran.fyp.Flashcards.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ie.fran.fyp.R;


public class FragmentAdapter extends FragmentPagerAdapter {
    private Context context;

    public FragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new DecksFragment();
            case 1:
                return new SharedDecksFragment();
            case 2:
                return new SearchFragment();
            default:
                return new DecksFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.my_decks);
            case 1:
                return context.getString(R.string.shared_decks);
            case 2:
                return context.getString(R.string.search_decks);
            default:
                return context.getString(R.string.my_decks);
        }
    }
}
