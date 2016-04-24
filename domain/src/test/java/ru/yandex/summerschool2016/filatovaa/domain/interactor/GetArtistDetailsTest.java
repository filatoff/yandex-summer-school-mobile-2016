package ru.yandex.summerschool2016.filatovaa.domain.interactor;

import ru.yandex.summerschool2016.filatovaa.domain.executor.PostExecutionThread;
import ru.yandex.summerschool2016.filatovaa.domain.executor.ThreadExecutor;
import ru.yandex.summerschool2016.filatovaa.domain.repository.ArtistRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetArtistDetailsTest {

    private static final int FAKE_ID = 123;

    private GetArtistDetails getArtistDetails;

    @Mock
    private ArtistRepository mockArtistRepository;
    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        getArtistDetails = new GetArtistDetails(FAKE_ID, mockArtistRepository, mockThreadExecutor, mockPostExecutionThread);
    }

    @Test
    public void testGetArtistDetailsUseCaseObservableHappyCase() {
        getArtistDetails.buildUseCaseObservable();

        verify(mockArtistRepository).artist(FAKE_ID);
        verifyNoMoreInteractions(mockArtistRepository);
        verifyZeroInteractions(mockPostExecutionThread);
        verifyZeroInteractions(mockThreadExecutor);
    }
}
