package src.com.coupang.c4e.hangman;

import java.util.*;


public class HangManStatus {
    public enum GameStatus
    {
        IN_GAME,LOSE,WIN
    }
    public static final Integer MAX_COUNT = 15;
    private GameStatus status = GameStatus.IN_GAME;
    private Integer count = 0;
    private Set<Character> wrongChars;
    private Set<Character> checkedChars;
    private final Set<Character> movieNameSet;

    public HangManStatus(Set<Character> movieNameLetterSet){
        this.movieNameSet = movieNameLetterSet;
        wrongChars = new HashSet<>();
        checkedChars = new HashSet<>();
        checkedChars.add(' ');
    }

    public void addChar(Character c){
        if(movieNameSet.stream().anyMatch(c::equals)){
            checkedChars.add(c);
        }
        else{
            wrongChars.add(c);
        }
    }

    public void increaseCount(){
        this.count+=1;
    }

    public void setStatusByCount(){
        if(this.count <= MAX_COUNT && movieNameSet.equals(checkedChars)){
            this.status = GameStatus.WIN;
        }
        else if(this.count>=MAX_COUNT){
            this.status = GameStatus.LOSE;
        }
    }

    public GameStatus getStatus(){
        return this.status;
    }

    public Set<Character> getCheckedChars(){
        return this.checkedChars;
    }

    public Set<Character> getWrongChars(){
        return this.wrongChars;
    }

    public boolean isAlreadyInputted(Character c){
        return this.wrongChars.contains(c) || this.checkedChars.contains(c);
    }
}
