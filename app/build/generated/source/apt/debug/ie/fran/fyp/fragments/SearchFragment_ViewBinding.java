// Generated code from Butter Knife. Do not modify!
package ie.fran.fyp.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import ie.fran.fyp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchFragment_ViewBinding implements Unbinder {
  private SearchFragment target;

  private View view7f080044;

  @UiThread
  public SearchFragment_ViewBinding(final SearchFragment target, View source) {
    this.target = target;

    View view;
    target.etSearch = Utils.findRequiredViewAsType(source, R.id.etSearch, "field 'etSearch'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btnSearch, "method 'search'");
    view7f080044 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.search();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etSearch = null;

    view7f080044.setOnClickListener(null);
    view7f080044 = null;
  }
}
