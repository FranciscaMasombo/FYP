// Generated code from Butter Knife. Do not modify!
package ie.fran.fyp;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddDeckActivity_ViewBinding implements Unbinder {
  private AddDeckActivity target;

  private View view7f080039;

  private View view7f080045;

  @UiThread
  public AddDeckActivity_ViewBinding(AddDeckActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddDeckActivity_ViewBinding(final AddDeckActivity target, View source) {
    this.target = target;

    View view;
    target.etTitle = Utils.findRequiredViewAsType(source, R.id.etTitle, "field 'etTitle'", EditText.class);
    target.recyclerAddedCards = Utils.findRequiredViewAsType(source, R.id.recyclerAddedCards, "field 'recyclerAddedCards'", RecyclerView.class);
    target.switchPriv = Utils.findRequiredViewAsType(source, R.id.switchPriv, "field 'switchPriv'", CompoundButton.class);
    view = Utils.findRequiredView(source, R.id.btnAddCard, "method 'addCardClick'");
    view7f080039 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addCardClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnSend, "method 'sendClick'");
    view7f080045 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.sendClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    AddDeckActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etTitle = null;
    target.recyclerAddedCards = null;
    target.switchPriv = null;

    view7f080039.setOnClickListener(null);
    view7f080039 = null;
    view7f080045.setOnClickListener(null);
    view7f080045 = null;
  }
}
