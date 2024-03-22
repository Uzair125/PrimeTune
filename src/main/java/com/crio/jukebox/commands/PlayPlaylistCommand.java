package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.services.IPlaylistService;

public class PlayPlaylistCommand implements ICommand {

    private final IPlaylistService playlistService;

    public PlayPlaylistCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        String userId = tokens.get(1);
        String playlistId = tokens.get(2);
        Song playPlaylistSong = playlistService.playPlaylist(playlistId, userId);
        System.out.println("Current Song Playing");
        System.out.println("Song  -  " + playPlaylistSong.getName());
        System.out.println("Album  -  " + playPlaylistSong.getAlbum());
        System.out.println("Artists  -  " + playPlaylistSong.getFeatArtist().replace("#",","));
    }
    
}
