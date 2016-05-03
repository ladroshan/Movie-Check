package com.tassioauad.moviecheck.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.tassioauad.moviecheck.MovieCheckApplication;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.dagger.CastCrewViewModule;
import com.tassioauad.moviecheck.model.entity.Cast;
import com.tassioauad.moviecheck.model.entity.Crew;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.presenter.CastCrewPresenter;
import com.tassioauad.moviecheck.view.CastCrewView;
import com.tassioauad.moviecheck.view.adapter.CastListAdapter;
import com.tassioauad.moviecheck.view.adapter.CrewListAdapter;
import com.tassioauad.moviecheck.view.adapter.OnItemClickListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CastCrewFragment extends Fragment implements CastCrewView {

    @Inject
    CastCrewPresenter presenter;

    @Bind(R.id.recyclerview_cast)
    RecyclerView recyclerViewCast;
    @Bind(R.id.recyclerview_crew)
    RecyclerView recyclerViewCrew;
    @Bind(R.id.linearlayout_cast_anyfounded)
    LinearLayout linearLayoutAnyCastFounded;
    @Bind(R.id.linearlayout_cast_loadfailed)
    LinearLayout linearLayoutCastLoadFailed;
    @Bind(R.id.progressbar_cast)
    ProgressBar progressBarCast;
    @Bind(R.id.linearlayout_crew_anyfounded)
    LinearLayout linearLayoutAnyCrewFounded;
    @Bind(R.id.linearlayout_crew_loadfailed)
    LinearLayout linearLayoutCrewLoadFailed;
    @Bind(R.id.progressbar_crew)
    ProgressBar progressBarCrew;

    private static final String KEY_MOVIE = "MOVIE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MovieCheckApplication) getActivity().getApplication())
                .getObjectGraph().plus(new CastCrewViewModule(this)).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_castcrew, container, false);
        ButterKnife.bind(this, view);

        presenter.init((Movie) getArguments().getParcelable(KEY_MOVIE));

        return view;
    }

    public static CastCrewFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_MOVIE, movie);
        CastCrewFragment fragment = new CastCrewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void hideLoadingCrew() {
        progressBarCrew.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingCrew() {
        progressBarCrew.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCrews(List<Crew> crewList) {
        linearLayoutCrewLoadFailed.setVisibility(View.GONE);
        linearLayoutAnyCrewFounded.setVisibility(View.GONE);
        recyclerViewCrew.setVisibility(View.VISIBLE);
        recyclerViewCrew.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        recyclerViewCrew.setAdapter(new CrewListAdapter(crewList, new OnItemClickListener<Crew>() {
            @Override
            public void onClick(Crew crew) {

            }
        }));
    }

    @Override
    public void warnAnyCrewFounded() {
        linearLayoutCrewLoadFailed.setVisibility(View.GONE);
        linearLayoutAnyCrewFounded.setVisibility(View.VISIBLE);
        recyclerViewCrew.setVisibility(View.GONE);
    }

    @Override
    public void warnFailedToLoadCrews() {
        linearLayoutCrewLoadFailed.setVisibility(View.VISIBLE);
        linearLayoutAnyCrewFounded.setVisibility(View.GONE);
        recyclerViewCrew.setVisibility(View.GONE);
    }

    @Override
    public void hideLoadingCast() {
        progressBarCast.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingCast() {
        progressBarCast.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCasts(List<Cast> castList) {
        linearLayoutCastLoadFailed.setVisibility(View.GONE);
        linearLayoutAnyCastFounded.setVisibility(View.GONE);
        recyclerViewCast.setVisibility(View.VISIBLE);
        recyclerViewCast.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        recyclerViewCast.setAdapter(new CastListAdapter(castList, new OnItemClickListener<Cast>() {
            @Override
            public void onClick(Cast cast) {

            }
        }));
    }

    @Override
    public void warnAnyCastFounded() {
        linearLayoutCastLoadFailed.setVisibility(View.GONE);
        linearLayoutAnyCastFounded.setVisibility(View.VISIBLE);
        recyclerViewCast.setVisibility(View.GONE);
    }

    @Override
    public void warnFailedToLoadCasts() {
        linearLayoutCastLoadFailed.setVisibility(View.VISIBLE);
        linearLayoutAnyCastFounded.setVisibility(View.GONE);
        recyclerViewCast.setVisibility(View.GONE);
    }
}