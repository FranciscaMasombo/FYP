package ie.fran.fyp.Flashcards;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ie.fran.fyp.Flashcards.fragments.MyDecksFragment;
import ie.fran.fyp.R;
import ie.fran.fyp.Flashcards.fragments.SearchFragment;
import ie.fran.fyp.Flashcards.fragments.SharedDecksFragment;


public class MainPagerAdapter extends FragmentPagerAdapter {
    private Context context;

    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new MyDecksFragment();
            case 1:
                return new SharedDecksFragment();
            case 2:
                return new SearchFragment();
            default:
                return new MyDecksFragment();
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
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
