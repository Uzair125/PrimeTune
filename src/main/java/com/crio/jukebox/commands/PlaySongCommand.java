package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.services.IPlaylistService;

public class PlaySongCommand implements ICommand {

    private final IPlaylistService playlistService;

    public PlaySongCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        String userId = tokens.get(1);
        String play = tokens.get(2);
        Song playSong = playlistService.playSong(userId, play);
        System.out.println("Current Song Playing");
        System.out.println("Song  -  " + playSong.getName());
        System.out.println("Album  -  " + playSong.getAlbum());
        System.out.println("Artists  -  " + playSong.getFeatArtist().replace("#",","));
    }
    
}
