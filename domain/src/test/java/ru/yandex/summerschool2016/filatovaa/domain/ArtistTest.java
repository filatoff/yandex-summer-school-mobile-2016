package ru.yandex.summerschool2016.filatovaa.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ArtistTest {

    private static final int FAKE_ID = 8;

    private Artist artist;

    @Before
    public void setUp() {
        artist = new Artist(FAKE_ID);
    }

    @Test
    public void testArtistConstructorHappyCase() {
        int id = artist.getId();

        assertThat(id, is(FAKE_ID));
    }
}
