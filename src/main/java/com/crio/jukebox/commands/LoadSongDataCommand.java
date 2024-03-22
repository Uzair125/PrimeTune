package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.services.ISongService;

public class LoadSongDataCommand implements ICommand {

    private final ISongService songService;
    
    public LoadSongDataCommand(ISongService songService) {
        this.songService = songService;
    }

    @Override
    public void execute(List<String> tokens) {
        String filePath = tokens.get(1);
        songService.loadSongsData(filePath, ",");
        System.out.println("Songs Loaded successfully");
    }
    
}
