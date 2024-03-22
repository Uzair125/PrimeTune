package com.crio.jukebox.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Song extends BaseEntity {

    private final String name;
    private final String album;
    private final String albumArtist;
    private final String featArtist;

    public Song(Song song){
        this(song.id,song.name,song.album,song.albumArtist,song.featArtist);
    }

    public Song(String id, String name, String album, String albumArtist, String featArtist) {
        this(name, album, albumArtist, featArtist);
        this.id = id;
    }

    public Song(String name, String album, String albumArtist, String featArtist) {
        this.name = name;
        this.album = album;
        this.albumArtist = albumArtist;
        this.featArtist = featArtist;
    }

    public String getName() {
        return name;
    }

    public String getAlbum() {
        return album;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public String getFeatArtist() {
        return featArtist;
    }

    @Override
    public String toString() {
        return "Song [id=" + id + ", name=" + name + ", Album=" + album + ", Album Artist=" + albumArtist + ", Feat. Artist=" + featArtist + "]";
    }
    
}
