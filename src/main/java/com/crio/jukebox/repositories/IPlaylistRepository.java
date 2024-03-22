package com.crio.jukebox.repositories;

import java.util.List;
import java.util.Optional;
import com.crio.jukebox.entities.Playlist;

public interface IPlaylistRepository extends CRUDRepository<Playlist,String> {
    public List<Playlist> findByUserId(String userId);
    public String getActivePlaylistOfUser(String userId);
    public void setActivePlaylistOfUser(String userId, String playlistId);
    public Integer getCurrentSong();
    public void setCurrentSong(Integer songNum);
}
