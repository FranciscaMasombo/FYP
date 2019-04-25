// Generated code from Butter Knife. Do not modify!
package ie.fran.fyp;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Flashcard_LoginActivity_ViewBinding implements Unbinder {
  private Flashcard_LoginActivity target;

  private View view7f080047;

  private View view7f080046;

  @UiThread
  public Flashcard_LoginActivity_ViewBinding(Flashcard_LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Flashcard_LoginActivity_ViewBinding(final Flashcard_LoginActivity target, View source) {
    this.target = target;

    View view;
    target.etEmail = Utils.findRequiredViewAsType(source, R.id.etEmail, "field 'etEmail'", EditText.class);
    target.etPassword = Utils.findRequiredViewAsType(source, R.id.etPassword, "field 'etPassword'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btnRegister, "method 'registerClick'");
    view7f080047 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.registerClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnLogin, "method 'loginClick'");
    view7f080046 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.loginClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    Flashcard_LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etEmail = null;
    target.etPassword = null;

    view7f080047.setOnClickListener(null);
    view7f080047 = null;
    view7f080046.setOnClickListener(null);
    view7f080046 = null;
  }
}
