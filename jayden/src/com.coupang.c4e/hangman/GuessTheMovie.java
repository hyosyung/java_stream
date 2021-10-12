package src.com.coupang.c4e.hangman;

public class GuessTheMovie {


    public static void main(String[] args){
        play();
    }

    private static void play(){
        String movieName = new RandomMovieGenerator().getRandomMovie();
        HangManHandler hangManHandler = new HangManHandler(movieName);
        hangManHandler.game();
    }

}
