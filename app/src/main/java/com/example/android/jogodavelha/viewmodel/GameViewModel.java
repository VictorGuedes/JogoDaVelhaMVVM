package com.example.android.jogodavelha.viewmodel;

import androidx.databinding.ObservableArrayMap;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.android.jogodavelha.model.Cell;
import com.example.android.jogodavelha.model.Game;
import com.example.android.jogodavelha.model.Player;
import com.example.android.jogodavelha.StringUtility;

public class GameViewModel extends ViewModel {

    private ObservableArrayMap<String, String> cells;
    private Game game;

    public ObservableArrayMap<String, String> getCells() {
        return cells;
    }

    public void setCells(ObservableArrayMap<String, String> cells) {
        this.cells = cells;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void init(String player1, String player2){
        game = new Game(player1, player2);
        cells = new ObservableArrayMap<>();
    }

    public void onClickedCellAt(int row, int column){

        if(game.getCells()[row][column] == null){
            game.getCells()[row][column] = new Cell(game.getCurrentPlayer());
            cells.put(StringUtility.stringFromNumbers(row, column), game.getCurrentPlayer().getValue());
        }

        if(game.hasGameEnded()){
            game.reset();
        } else {
            game.swichPlayer();
        }
    }

    public LiveData<Player> getWinner(){
        return game.getWinner();
    }


}
