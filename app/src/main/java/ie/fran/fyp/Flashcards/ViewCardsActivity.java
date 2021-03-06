package ie.fran.fyp.Flashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ie.fran.fyp.Flashcards.FlipAnimation.FlipLayout;
import ie.fran.fyp.Flashcards.CreateDeck.Deck;
import ie.fran.fyp.R;

public class ViewCardsActivity extends AppCompatActivity {
    private Deck deck;
    private int listPos;

    @BindView(R.id.tvFront)
    TextView tvFront;
    @BindView(R.id.tvBack)
    TextView tvBack;
    @BindView(R.id.tvDeckHeader)
    TextView tvDeckHeader;
    @BindView(R.id.tvDeckPosition)
    TextView tvDeckPosition;
    @BindView(R.id.flipCards)
    FlipLayout flipLayout;
    @BindView(R.id.fabCorrect)
    Button fabCorrect;
    @BindView(R.id.fabIncorrect)
    Button fabIncorrect;

    private int score;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        deck = (Deck) bd.get(FlashcardsMainActivity.KEY_DECK);

        listPos = ViewCardsData.getInstance().getListPos();
        score = ViewCardsData.getInstance().getScore();

        if(ViewCardsData.getInstance().isComplete()) {
            showCompleteMessage();
        }
        else {
            setupCardviews();
            updateCardInfo();

            flipLayout.setOnFlipListener(new FlipLayout.OnFlipListener() {
                @Override
                public void onFlipStart(FlipLayout view) {

                }

                @Override
                public void onFlipEnd(FlipLayout view) {
                    fabCorrect.setVisibility(View.VISIBLE);
                    fabIncorrect.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void setupCardviews() {
        setContentView(R.layout.flashcard_activity_view_cards);
        ButterKnife.bind(this);
        tvDeckHeader.setText(deck.getTitle()+" by "+deck.getAuthor());
    }

    @OnClick(R.id.fabCorrect)
    void Correct(){
        score++;
        ViewCardsData.getInstance().incrementScore();
        flipLayout.reset();
        nextCard();
    }

    @OnClick(R.id.fabIncorrect)
    void Incorrect(){
        flipLayout.reset();
        nextCard();
    }

    public void nextCard() {
        if(listPos == deck.getFronts().size()-1) {
            ViewCardsData.getInstance().setComplete(true);
            ViewCardsData.getInstance().setScore(score);
            setContentView(R.layout.flashcard_completion_screen);
            showCompleteMessage();
        }
        else {
            listPos++;
            ViewCardsData.getInstance().incrementListPos();
            updateCardInfo();
        }
    }

    private void showCompleteMessage() {
        setContentView(R.layout.flashcard_completion_screen);
        TextView tvCompletion = (TextView) findViewById(R.id.tvCompletion);
        tvCompletion.setText(getString(R.string.score_completion)+
                score+"/"+deck.getFronts().size());
        Button btnCompAccept = (Button) findViewById(R.id.btnCompAccept);
        btnCompAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewCardsData.getInstance().setComplete(false);
                ViewCardsData.getInstance().setScore(0);
                ViewCardsData.getInstance().setListPos(0);
                finish();
            }
        });
    }

    public void updateCardInfo() {
        String front = deck.getFronts().get(listPos);
        String back = deck.getBacks().get(listPos);
        tvDeckPosition.setText(Integer.toString(listPos+1)+" / "+deck.getFronts().size());
        tvFront.setText(front);
        tvBack.setText(back);
        fabCorrect.setVisibility(View.INVISIBLE);
        fabIncorrect.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        ViewCardsData.getInstance().setComplete(false);
        ViewCardsData.getInstance().setScore(0);
        ViewCardsData.getInstance().setListPos(0);
        super.onBackPressed();
    }
}
