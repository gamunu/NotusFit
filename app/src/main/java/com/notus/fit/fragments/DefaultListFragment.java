package com.notus.fit.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.notus.fit.R;
import com.pnikosis.materialishprogress.ProgressWheel;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;


public abstract class DefaultListFragment extends FitnessFragment {
    protected CardArrayRecyclerViewAdapter mCardArrayAdapter;
    @Bind(R.id.progress_wheel)
    protected ProgressWheel mProgressWheel;
    @Bind(R.id.recycler_list)
    protected CardRecyclerView mRecyclerViewList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mRecyclerViewList.setHasFixedSize(true);
        mRecyclerViewList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public Runnable refreshList() {
        return new Runnable() {
            @Override
            public void run() {
                mCardArrayAdapter.notifyDataSetChanged();
                mRecyclerViewList.refreshDrawableState();
                mRecyclerViewList.invalidate();
            }
        };
    }

    public int getLayoutResource() {
        return R.layout.fragment_list;
    }
}
