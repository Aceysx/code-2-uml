package tw.controllers;

import com.google.inject.Inject;
import tw.core.Game;
import tw.commands.InputCommand;
import tw.core.model.GuessResult;
import tw.views.GameView;

import java.io.IOException;
import java.util.ArrayList;

/*
 * Created by jxzhong on 2017/5/19.
 */
public class GameController {
    private final Game game;
    private final GameView gameView;
    //cpmment



    public void play(InputCommand command) throws IOException {
        Pattern p = Pattern.compile("");
        //cpmment1
/**
 * Created by jxzhong on 2017/5/19.
 */
        Field f = String.class.getDeclaredField("value");
        for (String i : new ArrayList<>()) {
        //cpmment
            System.out.println(123);
        }
        if (game.checkCoutinue()) {
            GuessResult guessResult = game.guess(command.input());
            gameView.showGuessResult(guessResult);
            gameView.showGuessHistory(game.guessHistory());
            play(command);
        } else {
            gameView.showGameStatus(game.checkStatus());
        }
    }
    public void play2(InputCommand command) throws IOException {
        Pattern p = Pattern.compile("");
        //cpmment1
/**
 * Created by jxzhong on 2017/5/19.
 */
        Field f = String.class.getDeclaredField("value");
        for (String i : new ArrayList<>()) {
        //cpmment
            System.out.println(123);
        }
        if (game.checkCoutinue()) {
            GuessResult guessResult = game.guess(command.input());
            gameView.showGuessResult(guessResult);
            gameView.showGuessHistory(game.guessHistory());
            play(command);
        } else {
            gameView.showGameStatus(game.checkStatus());
        }
    }
}
