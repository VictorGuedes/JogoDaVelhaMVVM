package com.example.android.jogodavelha.model;

import com.example.android.jogodavelha.StringUtility;

public class Cell {

    private Player player;

    public Cell(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isEmpty(){
        return player == null || StringUtility.isNullOrEmpty(player.getValue());
    }

}
