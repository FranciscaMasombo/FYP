package ie.fran.fyp.Flashcards.stringsimilarity;

import android.support.v4.util.Pair;

import com.google.firebase.database.DataSnapshot;

import java.util.Comparator;

public class ComparePairs implements Comparator<Pair<Double, DataSnapshot>> {
    @Override
    public int compare(Pair<Double, DataSnapshot> t1, Pair<Double, DataSnapshot> t2) {
        return (int) (t1.first - t2.first);
    }

}
