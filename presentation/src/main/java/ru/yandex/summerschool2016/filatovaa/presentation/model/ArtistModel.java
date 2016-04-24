package ru.yandex.summerschool2016.filatovaa.presentation.model;

import java.util.List;

/**
 * Class that represents an artist in the presentation layer.
 */
public class ArtistModel {

    private final int id;

    public ArtistModel(int id) {
        this.id = id;
    }

    private String name;
    private String description;
    private String link;
    private int tracks;
    private int albums;
    private List<String> genres;
    private String coverSmall;
    private String coverBig;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }

    public int getAlbums() {
        return albums;
    }

    public void setAlbums(int albums) {
        this.albums = albums;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getCoverSmall() {
        return coverSmall;
    }

    public void setCoverSmall(String coverSmall) {
        this.coverSmall = coverSmall;
    }

    public String getCoverBig() {
        return coverBig;
    }

    public void setCoverBig(String coverBig) {
        this.coverBig = coverBig;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("***** Artist Model Details *****\n");
        stringBuilder.append("id=" + this.getId() + "\n");
        stringBuilder.append("name=" + this.getName() + "\n");
        stringBuilder.append("description=" + this.getDescription() + "\n");
        stringBuilder.append("link=" + this.getLink() + "\n");
        stringBuilder.append("tracks=" + String.valueOf(this.getTracks()) + "\n");
        stringBuilder.append("albums=" + String.valueOf(this.getAlbums()) + "\n");
        stringBuilder.append("genres=" + this.getGenres().toString() + "\n");
        stringBuilder.append("coverSmall=" + this.getCoverSmall() + "\n");
        stringBuilder.append("coverBig=" + this.getCoverBig() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }
}
