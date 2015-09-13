package com.notus.fit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.notus.fit.R;

import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

public abstract class DefaultFragment extends Fragment {
    protected static final boolean DEBUG = true;
    protected static final String LOG_TAG = DefaultFragment.class.getSimpleName();
    protected CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    protected boolean unsubscribe = DEBUG;

    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        FragmentManager fm = getFragmentManager();
        if (fm != null) {
            fm.beginTransaction().remove(this).commit();
        }
        super.onInflate(context, attrs, savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(getLayoutResource(), container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(DEBUG);
        setRetainInstance(DEBUG);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    public int getLayoutResource() {
        return R.layout.default_fragment;
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.unsubscribe) {
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
