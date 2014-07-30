package net.vrallev.android.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * @author Ralf Wondratschek
 */
@SuppressWarnings("UnusedDeclaration")
public class BaseFragment extends Fragment {

    private SaveInstanceHelper mSaveInstanceHelper;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof BaseActivitySupport) {
            BaseActivitySupport baseActivitySupport = (BaseActivitySupport) activity;
            mSaveInstanceHelper = new SaveInstanceHelper(baseActivitySupport, this);

            // stupid cast, http://stackoverflow.com/questions/10386264/getclass-in-abstract-class-gives-ambiguous-method-call
            if (BaseActivitySupport.isRequiringInject(((Object) this).getClass())) {
                baseActivitySupport.inject(this);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (mSaveInstanceHelper != null) {
            mSaveInstanceHelper.onPreOnCreate(savedInstanceState);
        }

        super.onCreate(savedInstanceState);

        if (mSaveInstanceHelper != null) {
            mSaveInstanceHelper.onPostOnCreate(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mSaveInstanceHelper != null) {
            mSaveInstanceHelper.onPostOnStart();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mSaveInstanceHelper != null) {
            mSaveInstanceHelper.onPreSaveInstanceState();
        }

        super.onSaveInstanceState(outState);

        if (mSaveInstanceHelper != null) {
            mSaveInstanceHelper.onPostSaveInstanceState(outState);
        }
    }

}
