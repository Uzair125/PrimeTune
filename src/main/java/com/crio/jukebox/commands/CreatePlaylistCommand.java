package com.crio.jukebox.commands;

import java.util.ArrayList;
import java.util.List;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.services.IPlaylistService;

public class CreatePlaylistCommand implements ICommand {

    private final IPlaylistService playlistService;

    public CreatePlaylistCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        String userId = tokens.get(1);
        String playlistName = tokens.get(2);
        List<String> songsList = new ArrayList<String>();
        for(int i=3; i<tokens.size(); i++) {
            songsList.add(tokens.get(i));
        }
        Playlist newPlaylist = playlistService.create(userId, playlistName, songsList);
        System.out.println("Playlist ID - " + newPlaylist.getId());
    }
    
}
