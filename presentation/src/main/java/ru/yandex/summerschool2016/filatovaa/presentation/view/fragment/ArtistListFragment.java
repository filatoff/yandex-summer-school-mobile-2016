package ru.yandex.summerschool2016.filatovaa.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seppius.i18n.plurals.PluralResources;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.yandex.summerschool2016.filatovaa.domain.interactor.GetArtistList;
import ru.yandex.summerschool2016.filatovaa.presentation.R;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.components.ArtistComponent;
import ru.yandex.summerschool2016.filatovaa.presentation.model.ArtistModel;
import ru.yandex.summerschool2016.filatovaa.presentation.presenter.ArtistListPresenter;
import ru.yandex.summerschool2016.filatovaa.presentation.view.ArtistListView;
import ru.yandex.summerschool2016.filatovaa.presentation.view.adapter.ArtistsAdapter;
import ru.yandex.summerschool2016.filatovaa.presentation.view.adapter.ArtistsLayoutManager;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Fragment that shows a list of artists.
 */
public class ArtistListFragment extends BaseFragment implements ArtistListView {

    /**
     * Interface for listening artist list events.
     */
    public interface ArtistListListener {
        void onArtistClicked(final ArtistModel artistModel);
    }

    @Inject
    Context context;
    @Inject
    ArtistListPresenter artistListPresenter;
    @Inject
    ArtistsAdapter artistsAdapter;
    @Inject
    PluralResources pluralResources;

    @Bind(R.id.artist_list__recycler_view)
    RecyclerView artistsRecyclerView;
    @Bind(R.id.progress_bar)
    RelativeLayout progressBar;
    @Bind(R.id.error__text)
    TextView errorText;
    @Bind(R.id.error__button_retry)
    Button errorButtonRetry;
    @Bind(R.id.artist_list__empty)
    TextView emptyText;

    private ArtistListListener artistListListener;

    private Menu menu;

    private boolean searchVisible = false;
    private String searchText = "";

    public ArtistListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ArtistListListener)
            this.artistListListener = (ArtistListListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(ArtistComponent.class).inject(this);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_artist_list, container, false);
        ButterKnife.bind(this, fragmentView);
        this.setupRecyclerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.artistListPresenter.setView(this);
        if (savedInstanceState == null) {
            this.artistListPresenter.initialize();
        } else {
            this.artistListPresenter.restoreView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.artistListPresenter.resume();

        // unfocus search view on back stack call
        this.unfocusSearchView();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.artistListPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.artistsRecyclerView.setAdapter(null);
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.artistListPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.artistListListener = null;
    }

    @Override
    public void showLoading() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {
        this.emptyText.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmpty() {
        this.emptyText.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message, boolean showButtonRetry) {
        if (!TextUtils.isEmpty(message)) {
            this.errorText.setText(message);
            this.errorText.setVisibility(View.VISIBLE);
        }
        if (showButtonRetry)
            this.errorButtonRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        this.errorText.setVisibility(View.GONE);
        this.errorButtonRetry.setVisibility(View.GONE);
    }

    @Override
    public void renderArtistList(Collection<ArtistModel> artistModelCollection) {
        if (artistModelCollection != null) {
            this.artistsAdapter.setArtistsCollection(artistModelCollection);
        }
    }

    @Override
    public void viewArtist(ArtistModel artistModel) {
        if (this.artistListListener != null) {
            this.artistListListener.onArtistClicked(artistModel);
        }
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    /**
     * Customize recycler view:
     *  - orientation
     *  - item decoration
     */
    private void setupRecyclerView() {
        this.artistsAdapter.setOnItemClickListener(this.onItemClickListener);

        this.artistsRecyclerView.setLayoutManager(new ArtistsLayoutManager(context(),
                (this.context.getResources().getConfiguration().orientation ==
                        Configuration.ORIENTATION_LANDSCAPE) ? 2 : 1));

        this.artistsRecyclerView.setAdapter(this.artistsAdapter);

        this.artistsRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(getResources().getColor(R.color.artist_list__item_divider))
                        .sizeResId(R.dimen.artist_list__item_divider_height)
                        .build()
        );
    }

    @OnClick(R.id.error__button_retry)
    void onButtonRetryClick() {
        ArtistListFragment.this.artistListPresenter.loadArtistList();
    }

    private ArtistsAdapter.OnItemClickListener onItemClickListener = new ArtistsAdapter.OnItemClickListener() {
        @Override
        public void onArtistItemClicked(ArtistModel artistModel) {
            if (ArtistListFragment.this.artistListPresenter != null && artistModel != null) {
                ArtistListFragment.this.artistListPresenter.onArtistClicked(artistModel);
            }
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_artist_list, menu);

        // Search on action bar
        final MenuItem searchItem = menu.findItem(R.id.menu_artist_list__item_search);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                ArtistListFragment.this.searchVisible = true;
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                ArtistListFragment.this.searchVisible = false;
                return true;
            }
        });

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArtistListFragment.this.searchText = s;
                ArtistListFragment.this.search();
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.clearFocus();
                ArtistListFragment.this.searchText = "";
                ArtistListFragment.this.search();
                return false;
            }
        });
        searchView.setQueryHint(context.getString(R.string.artist_list__menu_item_search_query_hint));
        searchView.setFocusable(false);
        // for full available width on action bar
        searchView.setMaxWidth(100_000);

        // Restore search query after configuration changes
        // Without runnable doesnt work setQuery()
        final boolean searchVisible = this.searchVisible;
        final String searchText = this.searchText;
        searchView.post(new Runnable() {
            @Override
            public void run() {
                if (searchVisible) {
                    searchItem.expandActionView();
                    searchView.setQuery(searchText, true);
                    searchView.clearFocus();
                }
            }
        });

        this.menu = menu;
        this.artistListPresenter.initializeSortMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_artist_list__item_sort:
                // unfocusable search view on click of other icons on action bar
                this.unfocusSearchView();
                return false;
            case R.id.menu_artist_list__item_sort_none:
                this.artistListPresenter.changeSort(GetArtistList.SORT_NONE);
                return false;
            case R.id.menu_artist_list__item_sort_name:
                this.artistListPresenter.changeSort(GetArtistList.SORT_BY_NAME);
                return false;
            case R.id.menu_artist_list__item_sort_track:
                this.artistListPresenter.changeSort(GetArtistList.SORT_BY_TRACKS);
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Searching
     */
    private void search() {
        this.artistListPresenter.search(this.searchText);
    }

    private void unfocusSearchView() {
        if (this.menu != null)
            MenuItemCompat.getActionView(
                    this.menu.findItem(R.id.menu_artist_list__item_search)).clearFocus();
    }

    /**
     * Sorting
     * @param sort integer of item
     */
    public void setSort(int sort) {
        if (menu != null) {
            MenuItem itemSortNone = menu.findItem(R.id.menu_artist_list__item_sort_none);
            if (sort == GetArtistList.SORT_NONE)
                itemSortNone.setIcon(R.drawable.ic_done_black);
            else
                itemSortNone.setIcon(null);

            MenuItem itemSortName = menu.findItem(R.id.menu_artist_list__item_sort_name);
            if (sort == GetArtistList.SORT_BY_NAME)
                itemSortName.setIcon(R.drawable.ic_done_black);
            else
                itemSortName.setIcon(null);

            MenuItem itemSortTracks = menu.findItem(R.id.menu_artist_list__item_sort_track);
            if (sort == GetArtistList.SORT_BY_TRACKS)
                itemSortTracks.setIcon(R.drawable.ic_done_black);
            else
                itemSortTracks.setIcon(null);
        }
    }
}
