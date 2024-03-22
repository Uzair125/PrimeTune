package com.crio.jukebox.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.crio.codingame.exceptions.InvalidContestException;

public class Playlist extends BaseEntity{
    private final String userId;
    private final String playlistName;
    private final List<String> songs;

    public Playlist(Playlist playlist){
        this(playlist.id,playlist.playlistName,playlist.userId,playlist.songs);
    }

    public Playlist(String id, String userId, String playlistName, List<String> songs) {
        this(userId,playlistName,songs);
        this.id = id;
    }

    public Playlist(String userId, String playlistName, List<String> songs) {
        this.userId = userId;
        this.playlistName = playlistName;
        this.songs = songs;
    }
    
    public String getUserId() {
        return userId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public List<String> getSongs(){
        return songs.stream().collect(Collectors.toList());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Playlist other = (Playlist) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Playlist [id = " + id + ", User Id = " + userId + ", Playlist Name = " + playlistName + ", songs = " + songs +"]";
    }

}