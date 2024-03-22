package com.crio.jukebox.services;

import java.util.List;
import java.util.Optional;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;

public interface IPlaylistService {
    public Playlist create(String userId, String playlistName, List<String> songs);
    public Playlist modifyPlaylist(String modifyType, String playlistId, String userId, List<String> songs);
    public String delete(String Id, String userId);
    public Song playPlaylist(String id, String userId); 
    public Song playSong(String userId, String play); 
}
