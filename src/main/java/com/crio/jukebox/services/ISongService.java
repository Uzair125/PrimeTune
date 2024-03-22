package com.crio.jukebox.services;

import java.util.List;
import com.crio.jukebox.entities.Song;

public interface ISongService {
    public void loadSongsData(String filePath, String delimiter);
    public Song create(String name, String album, String albumArtist, String featArtist);
    public List<Song> getAllSongs();
    public List<Song> getAllSongsInAlbum(String album);
    public List<Song> getAllSongsByArtist(String albumArtist);
}
