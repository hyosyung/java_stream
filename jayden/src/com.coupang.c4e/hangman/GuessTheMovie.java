package src.com.coupang.c4e.hangman;

import src.com.coupang.c4e.hangman.RandomMovieGenerator.MovieSrc;

import java.util.Scanner;

import static src.com.coupang.c4e.hangman.RandomMovieGenerator.MovieSrc.FROM_API;
import static src.com.coupang.c4e.hangman.RandomMovieGenerator.MovieSrc.FROM_TEXT;

public class GuessTheMovie {


    public static void main(String[] args){
        play();
    }

    private static void play(){
        while(true) {
            System.out.print("To get movie from API, input API. To get movie from Text, input text : ");
            String fromTextOrApi = new Scanner(System.in).nextLine();
            if(!fromTextOrApi.equals(FROM_TEXT.getSource()) && !fromTextOrApi.equals(FROM_API.getSource())){
                System.out.println("Please input API or TEXT");
                continue;
            }
            try {
                String movieName = new RandomMovieGenerator().getRandomMovie(MovieSrc.srcOf(fromTextOrApi));
                HangManHandler hangManHandler = new HangManHandler(movieName);
                hangManHandler.game();
            }catch (EmptyListException e){
                System.out.println(e.getMessage());
                continue;
            }catch (Exception e){
                System.out.println("An error occurred. please retry.");
                continue;
            }
            break;
        }
    }

}
