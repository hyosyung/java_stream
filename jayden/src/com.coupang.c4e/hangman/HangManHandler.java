package src.com.coupang.c4e.hangman;

import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import static src.com.coupang.c4e.hangman.HangManStatus.GameStatus.IN_GAME;

public class HangManHandler {
    private final HangManStatus hangManStatus;
    private final String movieName;
    private Scanner sc = new Scanner(System.in);

    public HangManHandler(String movieName){
        this.movieName = movieName;
        Set<Character> movieNameSet = new HashSet<>();
        movieNameSet.add(' ');
        for(char c : movieName.toCharArray()){
            movieNameSet.add(c);
        }
        this.hangManStatus = new HangManStatus(movieNameSet);
    }

    public void game(){
        while(hangManStatus.getStatus()== IN_GAME){
            printHangmanStatus();
            System.out.print("Guess a letter: ");
            String input = sc.nextLine();
            if(input.length()!=1){
                System.out.println("Please input one letter.");
                continue;
            }
            Character letter = input.charAt(0);
            if(hangManStatus.isAlreadyInputted(letter)){
                System.out.println("This letter is already inputted. Please input other letter.");
                continue;
            }
            if(!isAlphabet(letter)){
                System.out.println("Please input an lowercase alphabet.");
                continue;
            }
            guessOneLetter(input.charAt(0));
        }
    }

    private void guessOneLetter(Character a){
        hangManStatus.addChar(a);
        hangManStatus.increaseCount();
        hangManStatus.setStatusByCount();
        switch (hangManStatus.getStatus()){
            case WIN:
                System.out.println("You win!");
                System.out.printf("You have guessed '%s' correctly%n",movieName);
                break;
            case LOSE:
                System.out.println("Lose");
                System.out.println("The movie name is "+movieName);
                break;
            default:
                break;
        }
    }

    public void printHangmanStatus(){
        StringBuilder guessResult = new StringBuilder();
        for(char c : movieName.toCharArray()){
            if(hangManStatus.getCheckedChars().contains(c)){
                guessResult.append(c);
            }
            else{
                guessResult.append('_');
            }
        }
        System.out.println("You are guessing: "+guessResult);
        String wrongLetters = String.format("You have guessed (%s) wrong letters: ",hangManStatus.getWrongChars().size());
        System.out.println(wrongLetters+hangManStatus.getWrongChars().stream().map(Objects::toString).reduce("",(a, b)->a+" "+b));
    }

    private boolean isAlphabet(Character c){
        return c.compareTo('a')>=0 && c.compareTo('z')<=0;
    }
}
