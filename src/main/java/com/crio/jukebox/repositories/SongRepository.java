package com.crio.jukebox.repositories;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.crio.jukebox.entities.Song;

public class SongRepository implements ISongRepository{

    private final Map<String,Song> songMap;
    private Integer autoIncrement = 0;

    public SongRepository(){
        songMap = new HashMap<String,Song>();
    }

    public SongRepository(Map<String, Song> songMap) {
        this.songMap = songMap;
        this.autoIncrement = songMap.size();
    }

    @Override
    public Song save(Song entity) {
        if( entity.getId() == null ){
            autoIncrement++;
            Song u = new Song(Integer.toString(autoIncrement),entity.getName(),entity.getAlbum(),entity.getAlbumArtist(),entity.getFeatArtist());
            songMap.put(u.getId(),u);
            return u;
        }
        songMap.put(entity.getId(),entity);
        return entity;
    }

    @Override
    public List<Song> findAll() {
        return songMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public Optional<Song> findById(String id) {
        return Optional.ofNullable(songMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        Optional user = Optional.ofNullable(songMap.get(id));
        if(user != null)
            return true;
        else
            return false;
    }

    @Override
    public void delete(Song entity) {
        //Auto-generated method stub
    }

    @Override
    public void deleteById(String id) {
        //Auto-generated method stub
    }

    @Override
    public long count() {
        //Auto-generated method stub
        return 0;
    }

    @Override
    public Optional<Song> findByName(String name) {
        List<Song> userList= songMap.entrySet().stream().filter(e -> e.getValue().getName() == name)
        .map(Map.Entry::getValue).collect(Collectors.toList());
        if(userList.isEmpty()){
            return Optional.empty();
        }
        return Optional.ofNullable(userList.get(0));
    }

    @Override
    public List<Song> findByAlbum(String album) {
        return songMap.entrySet().stream().filter(e -> e.getValue().getAlbum() == album)
        .map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public List<Song> findByalbumArtist(String albumArtist) {
        return songMap.entrySet().stream().filter(e -> e.getValue().getAlbumArtist() == albumArtist)
        .map(Map.Entry::getValue).collect(Collectors.toList());
    }
}

