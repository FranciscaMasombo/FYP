// Generated code from Butter Knife. Do not modify!
package ie.fran.fyp.Flashcards;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import ie.fran.fyp.Flashcards.view.FlipLayout;
import ie.fran.fyp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ViewCardsActivity_ViewBinding implements Unbinder {
  private ViewCardsActivity target;

  private View view7f080097;

  private View view7f080098;

  @UiThread
  public ViewCardsActivity_ViewBinding(ViewCardsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ViewCardsActivity_ViewBinding(final ViewCardsActivity target, View source) {
    this.target = target;

    View view;
    target.tvFront = Utils.findRequiredViewAsType(source, R.id.tvFront, "field 'tvFront'", TextView.class);
    target.tvBack = Utils.findRequiredViewAsType(source, R.id.tvBack, "field 'tvBack'", TextView.class);
    target.tvDeckHeader = Utils.findRequiredViewAsType(source, R.id.tvDeckHeader, "field 'tvDeckHeader'", TextView.class);
    target.tvDeckPosition = Utils.findRequiredViewAsType(source, R.id.tvDeckPosition, "field 'tvDeckPosition'", TextView.class);
    target.flipLayout = Utils.findRequiredViewAsType(source, R.id.flipCards, "field 'flipLayout'", FlipLayout.class);
    view = Utils.findRequiredView(source, R.id.fabCorrect, "field 'fabCorrect' and method 'Correct'");
    target.fabCorrect = Utils.castView(view, R.id.fabCorrect, "field 'fabCorrect'", Button.class);
    view7f080097 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.Correct();
      }
    });
    view = Utils.findRequiredView(source, R.id.fabIncorrect, "field 'fabIncorrect' and method 'Incorrect'");
    target.fabIncorrect = Utils.castView(view, R.id.fabIncorrect, "field 'fabIncorrect'", Button.class);
    view7f080098 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.Incorrect();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ViewCardsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvFront = null;
    target.tvBack = null;
    target.tvDeckHeader = null;
    target.tvDeckPosition = null;
    target.flipLayout = null;
    target.fabCorrect = null;
    target.fabIncorrect = null;

    view7f080097.setOnClickListener(null);
    view7f080097 = null;
    view7f080098.setOnClickListener(null);
    view7f080098 = null;
  }
}
