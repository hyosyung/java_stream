package src.com.coupang.c4e.hangman;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RandomMovieGenerator {
    private final static String MOVIES_PATH = "jayden/src/movies.txt";

    public String getRandomMovie(){
        try {
            List<String> movies = Files.lines(Paths.get(MOVIES_PATH)).filter(canWin()).map(String::trim).collect(Collectors.toList());
            return movies.get(new Random().nextInt(movies.size()));
        }
        catch (IOException e){
            e.printStackTrace();
            throw new IllegalStateException(e.getMessage());
        }
    }

    private Predicate<String> canWin() {
        return str -> {
            Set<Character> letterSet = new HashSet<>();
            letterSet.add(' ');
            for (char c : str.toCharArray()) {
                letterSet.add(c);
            }
            if (letterSet.size() > HangManStatus.MAX_COUNT+1) {
                return false;
            }
            return letterSet.size() > 1;
        };
    }


}
