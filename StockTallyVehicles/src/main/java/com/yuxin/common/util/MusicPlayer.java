package com.yuxin.common.util;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MusicPlayer {
    public void MusicPlayer(File file) throws FileNotFoundException, JavaLayerException {
        FileInputStream stream=new FileInputStream(file);
        Player player=new Player(stream);
        player.play();
    }

}
