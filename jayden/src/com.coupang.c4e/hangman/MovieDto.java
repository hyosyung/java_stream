package src.com.coupang.c4e.hangman;

import java.util.List;

public class MovieDto {
    List<MovieInfo> movieList;

    public MovieDto(){}

    public List<MovieInfo> getMovieList(){
        return this.movieList;
    }
}
