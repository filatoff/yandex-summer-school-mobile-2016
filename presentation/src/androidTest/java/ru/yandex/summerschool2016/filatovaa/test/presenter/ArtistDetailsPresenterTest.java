package ru.yandex.summerschool2016.filatovaa.test.presenter;

import android.content.Context;
import android.test.AndroidTestCase;

import ru.yandex.summerschool2016.filatovaa.domain.interactor.GetArtistDetails;
import ru.yandex.summerschool2016.filatovaa.presentation.mapper.ArtistModelDataMapper;
import ru.yandex.summerschool2016.filatovaa.presentation.presenter.ArtistDetailsPresenter;
import ru.yandex.summerschool2016.filatovaa.presentation.view.ArtistDetailsView;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Subscriber;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class ArtistDetailsPresenterTest extends AndroidTestCase {

    private static final int FAKE_ID = 123;

    private ArtistDetailsPresenter artistDetailsPresenter;

    @Mock
    private Context mockContext;
    @Mock
    private ArtistDetailsView mockArtistDetailsView;
    @Mock
    private GetArtistDetails mockGetArtistDetails;
    @Mock
    private ArtistModelDataMapper mockArtistModelDataMapper;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        artistDetailsPresenter = new ArtistDetailsPresenter(mockGetArtistDetails, mockArtistModelDataMapper);
        artistDetailsPresenter.setView(mockArtistDetailsView);
    }

    public void testArtistDetailsPresenterInitialize() {
        given(mockArtistDetailsView.context()).willReturn(mockContext);

        artistDetailsPresenter.initialize();

        verify(mockArtistDetailsView).showLoading();
        verify(mockGetArtistDetails).execute(any(Subscriber.class));
    }
}
