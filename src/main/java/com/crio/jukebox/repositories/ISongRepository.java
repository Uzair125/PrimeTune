package com.crio.jukebox.repositories;

import java.util.List;
import java.util.Optional;

import com.crio.jukebox.entities.Song;

public interface ISongRepository extends CRUDRepository<Song,String> {
    public Optional<Song> findByName(String name);
    public List<Song> findByAlbum(String album);
    public List<Song> findByalbumArtist(String albumArtist);
}

