package tracking.com.trackingandroid.apps.tours.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import tracking.com.trackingandroid.R;
import tracking.com.trackingandroid.apps.tours.ToursPresenter;
import tracking.com.trackingandroid.data.model.Tour;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToursFragment extends Fragment implements ToursView {

    @Inject
    ToursPresenter toursPresenter;

    @Inject
    Context context;

    @BindView(R.id.recycler_tours) RecyclerView recyclerViewTours;
    @BindView(R.id.progress_bar_tours) ProgressBar progressBar;
    @BindView(R.id.empty_view) TextView emptyView;

    private ToursAdapter toursAdapter;
    private Unbinder unbinder;

    public ToursFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tours, container, false);

        unbinder = ButterKnife.bind(this, view);
        toursPresenter.getTours();
        setupAdapter();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        toursPresenter.onDestroy();
    }

    @Override
    public void showTours(List<Tour> tourList) {
        if (tourList.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
        }
        toursAdapter.updateToursAdapter(tourList);
        progressBar.setVisibility(View.GONE);
    }

    private void setupAdapter() {
        toursAdapter = new ToursAdapter(new ArrayList<>(), context);
        recyclerViewTours.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewTours.setAdapter(toursAdapter);
    }
}
