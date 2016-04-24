package ru.yandex.summerschool2016.filatovaa.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Artist Entity used in the data layer.
 */
public class ArtistEntity {

    public static final String COVER_SMALL_JSON_KEY = "small";
    public static final String COVER_BIG_JSON_KEY = "big";

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("link")
    private String link;

    @SerializedName("tracks")
    private int tracks;

    @SerializedName("albums")
    private int albums;

    @SerializedName("genres")
    private List<String> genres;

    @SerializedName("cover")
    private Map<String, String> cover;

    public ArtistEntity() {
        //empty
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Map<String, String> getCover() {
        return cover;
    }

    public void setCover(Map<String, String> cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("***** Artist Entity Details *****\n");
        stringBuilder.append("id=" + this.getId() + "\n");
        stringBuilder.append("name=" + this.getName() + "\n");
        stringBuilder.append("description=" + this.getDescription() + "\n");
        stringBuilder.append("link=" + this.getLink() + "\n");
        stringBuilder.append("tracks=" + String.valueOf(this.getTracks()) + "\n");
        stringBuilder.append("albums=" + String.valueOf(this.getAlbums()) + "\n");
        stringBuilder.append("genres=" + this.getGenres().toString() + "\n");
        stringBuilder.append("cover=" + this.getCover().toString() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }
}
