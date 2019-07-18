package com.example.android.jogodavelha;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.android.jogodavelha.model.Player;
import com.example.android.jogodavelha.viewmodel.GameViewModel;
import com.example.android.jogodavelha.databinding.ActivityMainBinding;

import static com.example.android.jogodavelha.StringUtility.isNullOrEmpty;

public class MainActivity extends AppCompatActivity {

    private static final String GAME_BEGIN_DIALOG_TAG = "game_dialog_tag";
    private static final String GAME_END_DIALOG_TAG = "game_end_dialog_tag";
    private static final String NO_WINNER = "No one";
    private GameViewModel gameViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        promptForPlayers();
    }

    public void promptForPlayers() {
        GameBeginDialog dialog = GameBeginDialog.newInstance(this);
        dialog.show(getSupportFragmentManager(), GAME_BEGIN_DIALOG_TAG);
    }

    public void onPlayersSet(String player1, String player2) {
        initDataBinding(player1, player2);
    }

    private void initDataBinding(String player1, String player2) {
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        gameViewModel.init(player1, player2);
        activityMainBinding.setGameViewModel(gameViewModel);
        setUpOnGameEndListener();
    }

    private void setUpOnGameEndListener(){
        gameViewModel.getWinner().observe(this, this::onGameWinnerChanged);
    }

    @VisibleForTesting
    public void onGameWinnerChanged(Player winner) {
        String winnerName = winner == null || isNullOrEmpty(winner.getName()) ? NO_WINNER : winner.getName();
        GameEndDialog dialog = GameEndDialog.newInstance(this, winnerName);
        dialog.show(getSupportFragmentManager(), GAME_END_DIALOG_TAG);
    }

}
