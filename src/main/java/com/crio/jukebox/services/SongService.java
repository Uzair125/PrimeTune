package com.crio.jukebox.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.repositories.ISongRepository;

public class SongService implements ISongService {

    private final ISongRepository songRepository;

    public SongService(ISongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public void loadSongsData(String filePath, String delimiter) {
        // TODO Auto-generated method stub
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                List<String> tokens = Arrays.asList(line.split(delimiter));
                if(tokens.size() > 4) {
                    Song t1;
                    if(tokens.size() == 6) {
                        t1 = create(tokens.get(1),tokens.get(3),tokens.get(4),tokens.get(5));
                    } else {
                        t1 = create(tokens.get(0),tokens.get(2),tokens.get(3),tokens.get(4));
                    }
                    // System.out.println(t1);
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Song create(String name, String album, String albumArtist, String featArtist) {
        final Song song = new Song(name, album, albumArtist, featArtist);
        return songRepository.save(song);
    }

    @Override
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    @Override
    public List<Song> getAllSongsInAlbum(String album) {
        return songRepository.findByAlbum(album);
    }

    @Override
    public List<Song> getAllSongsByArtist(String albumArtist) {
        return songRepository.findByalbumArtist(albumArtist);
    }
    
}
