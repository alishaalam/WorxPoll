// Generated code from Butter Knife. Do not modify!
package com.alisha.organizerview;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class PollReplyListAdapter$$ViewInjector<T extends com.alisha.organizerview.PollReplyListAdapter> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427413, "field 'respondents_value'");
    target.respondents_value = finder.castView(view, 2131427413, "field 'respondents_value'");
    view = finder.findRequiredView(source, 2131427407, "field 'title_value'");
    target.title_value = finder.castView(view, 2131427407, "field 'title_value'");
    view = finder.findRequiredView(source, 2131427412, "field 'invitees_value'");
    target.invitees_value = finder.castView(view, 2131427412, "field 'invitees_value'");
  }

  @Override public void reset(T target) {
    target.respondents_value = null;
    target.title_value = null;
    target.invitees_value = null;
  }
}
