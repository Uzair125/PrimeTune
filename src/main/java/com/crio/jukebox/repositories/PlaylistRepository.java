package com.crio.jukebox.repositories;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.crio.jukebox.entities.Playlist;

public class PlaylistRepository implements IPlaylistRepository{

    private final Map<String,Playlist> playlistMap;
    private Integer autoIncrement = 0;
    private final Map<String,String> activePlaylist = new HashMap<String, String>();
    private Integer currentSong = 0;

    public PlaylistRepository(){
        playlistMap = new HashMap<String,Playlist>();
    }

    public PlaylistRepository(Map<String, Playlist> userMap) {
        this.playlistMap = userMap;
        this.autoIncrement = userMap.size();
    }

    @Override
    public Playlist save(Playlist entity) {
        if( entity.getId() == null ){
            autoIncrement++;
            Playlist p = new Playlist(Integer.toString(autoIncrement),entity.getUserId(),entity.getPlaylistName(),entity.getSongs());
            playlistMap.put(p.getId(),p);
            setActivePlaylistOfUser(entity.getUserId(), p.getId());
            return p;
        }
        playlistMap.put(entity.getId(),entity);
        return entity;
    }

    @Override
    public Integer getCurrentSong() {
        return currentSong;
    }

    @Override
    public void setCurrentSong(Integer songNum) {
        this.currentSong = songNum;
    }
    
    @Override
    public String getActivePlaylistOfUser(String userId){
        return activePlaylist.get(userId);
    }
    
    @Override
    public void setActivePlaylistOfUser(String userId, String playlistId) {
        activePlaylist.put(userId, playlistId);
        setCurrentSong(0);
    }

    @Override
    public List<Playlist> findAll() {
        return playlistMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public Optional<Playlist> findById(String id) {
        return Optional.ofNullable(playlistMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        Optional playlist = Optional.ofNullable(playlistMap.get(id));
        if(playlist != null)
            return true;
        else
            return false;
    }

    @Override
    public void delete(Playlist entity) {
    }

    @Override
    public void deleteById(String id) {
        playlistMap.remove(id);
    }

    @Override
    public long count() {
        //Auto-generated method stub
        return 0;
    }

    @Override
    public List<Playlist> findByUserId(String userId) {
        List<Playlist> userPlaylist= playlistMap.entrySet().stream().filter(e -> e.getValue().getUserId() == userId)
        .map(Map.Entry::getValue).collect(Collectors.toList());
        return userPlaylist;
    }

}
