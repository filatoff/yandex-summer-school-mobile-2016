package ru.yandex.summerschool2016.filatovaa.test.presenter;

import android.content.Context;
import android.test.AndroidTestCase;

import ru.yandex.summerschool2016.filatovaa.domain.interactor.GetArtistList;
import ru.yandex.summerschool2016.filatovaa.domain.interactor.SettingsSortArtistList;
import ru.yandex.summerschool2016.filatovaa.presentation.mapper.ArtistModelDataMapper;
import ru.yandex.summerschool2016.filatovaa.presentation.presenter.ArtistListPresenter;
import ru.yandex.summerschool2016.filatovaa.presentation.view.ArtistListView;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Subscriber;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class ArtistListPresenterTest extends AndroidTestCase {

    private ArtistListPresenter artistListPresenter;

    @Mock
    private Context mockContext;
    @Mock
    private ArtistListView mockArtistListView;
    @Mock
    private GetArtistList mockGetArtistList;
    @Mock
    private SettingsSortArtistList mockSettingsSortArtistList;
    @Mock
    private ArtistModelDataMapper mockArtistModelDataMapper;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        artistListPresenter = new ArtistListPresenter(mockGetArtistList, mockSettingsSortArtistList, mockArtistModelDataMapper);
        artistListPresenter.setView(mockArtistListView);
    }

    public void testArtistListPresenterInitialize() {
        given(mockArtistListView.context()).willReturn(mockContext);

        artistListPresenter.initialize();

        verify(mockArtistListView).showLoading();
        verify(mockSettingsSortArtistList).execute(any(Subscriber.class));
    }
}
