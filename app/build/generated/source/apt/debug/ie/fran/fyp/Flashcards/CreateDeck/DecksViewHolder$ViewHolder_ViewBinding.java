// Generated code from Butter Knife. Do not modify!
package ie.fran.fyp.Flashcards.CreateDeck;

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

public class DecksViewHolder$ViewHolder_ViewBinding implements Unbinder {
  private DecksViewHolder.ViewHolder target;

  @UiThread
  public DecksViewHolder$ViewHolder_ViewBinding(DecksViewHolder.ViewHolder target, View source) {
    this.target = target;

    target.tvAuthor = Utils.findRequiredViewAsType(source, R.id.tvAuthor, "field 'tvAuthor'", TextView.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tvTitle, "field 'tvTitle'", TextView.class);
    target.btnDelete = Utils.findRequiredViewAsType(source, R.id.btnDelete, "field 'btnDelete'", Button.class);
    target.btnEdit = Utils.findRequiredViewAsType(source, R.id.btnEdit, "field 'btnEdit'", Button.class);
    target.cardView = Utils.findRequiredView(source, R.id.card_view, "field 'cardView'");
    target.deckButtonLayout = Utils.findRequiredView(source, R.id.deckButtonLayout, "field 'deckButtonLayout'");
  }

  @Override
  @CallSuper
  public void unbind() {
    DecksViewHolder.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvAuthor = null;
    target.tvTitle = null;
    target.btnDelete = null;
    target.btnEdit = null;
    target.cardView = null;
    target.deckButtonLayout = null;
  }
}
