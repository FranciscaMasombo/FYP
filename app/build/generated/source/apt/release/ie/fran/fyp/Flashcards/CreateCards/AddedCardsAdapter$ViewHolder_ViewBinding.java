// Generated code from Butter Knife. Do not modify!
package ie.fran.fyp.Flashcards.CreateCards;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import ie.fran.fyp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddedCardsAdapter$ViewHolder_ViewBinding implements Unbinder {
  private AddedCardsAdapter.ViewHolder target;

  @UiThread
  public AddedCardsAdapter$ViewHolder_ViewBinding(AddedCardsAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.tvCardFront = Utils.findRequiredViewAsType(source, R.id.tvCardFront, "field 'tvCardFront'", TextView.class);
    target.tvCardBack = Utils.findRequiredViewAsType(source, R.id.tvCardBack, "field 'tvCardBack'", TextView.class);
    target.btnDeleteCard = Utils.findRequiredViewAsType(source, R.id.btnDeleteCard, "field 'btnDeleteCard'", Button.class);
    target.btnEditCard = Utils.findRequiredViewAsType(source, R.id.btnEditCard, "field 'btnEditCard'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddedCardsAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvCardFront = null;
    target.tvCardBack = null;
    target.btnDeleteCard = null;
    target.btnEditCard = null;
  }
}
