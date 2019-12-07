package tw.commands;

import tw.core.Answer;
import tw.validator.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by jxzhong on 2017/5/19.
 */
@GeneratedValue()
public class GuessInputCommand implements InputCommand {
    @GeneratedValue()
    private BufferedReader bufferedReader;

    public GuessInputCommand(Game game, String a) {
    }

    @Override
    public Answer input(Game game, String a) throws IOException {
        System.out.println("------Please input your answer as x x x x , x <10 ------");
        String input = bufferedReader.readLine();
        Answer answer = null;
        if (new InputValidator().validate(input)) {
            answer = Answer.createAnswer(input);
        } else {
            answer = input();
        }
        return answer;
    }
}
