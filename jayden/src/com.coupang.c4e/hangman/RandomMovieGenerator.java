package src.com.coupang.c4e.hangman;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RandomMovieGenerator {
    private final static String MOVIES_TEXT_PATH = "jayden/src/movies.txt";
    private final static String KOBIS_API_PATH = "http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=dd8b8ada05f4ab169f6a11ceae157e40&itemPerPage=100";
    private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

    public enum MovieSrc {
        FROM_API("API"),FROM_TEXT("TEXT");

        private String source;

        MovieSrc(String source){
            this.source=source;
        }

        public String getSource(){
            return source;
        }

        public static MovieSrc srcOf(String str){
            if(str.equals(FROM_API.getSource())){
                return FROM_API;
            }
            else{
                return FROM_TEXT;
            }
        }
    }

    public String getRandomMovie(MovieSrc src) throws Exception {
        if(MovieSrc.FROM_TEXT.equals(src)) {
            try {
                List<String> movies = Files.lines(Paths.get(MOVIES_TEXT_PATH)).filter(canWin()).map(String::trim).collect(Collectors.toList());
                return movies.get(new Random().nextInt(movies.size())).toLowerCase();
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalStateException(e.getMessage());
            }
        }
        else{
            List<String> movies = getMoviesFromApi().stream().filter(str -> str!=null && !str.isBlank()).collect(Collectors.toList());
            if(movies.isEmpty()){
                throw new EmptyListException("movie list is empty. please retry.");
            }
            return movies.get(new Random().nextInt(movies.size())).toLowerCase();
        }
    }

    private Predicate<String> canWin() {
        return str -> {
            Set<Character> letterSet = str.chars().mapToObj(c->(char)c).collect(Collectors.toSet());
            letterSet.add(' ');
            if (letterSet.size() > HangManStatus.MAX_COUNT+1) {
                return false;
            }
            return letterSet.size() > 1;
        };
    }

    private List<String> getMoviesFromApi(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(KOBIS_API_PATH)).build();
        try {
            HttpResponse<String> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).get();
            if(response.statusCode()!=200){
                System.out.println("Failed to get response. errorCode: "+response.statusCode());
                return Collections.EMPTY_LIST;
            }
            else{
                KobisDto dto = getDtoFromResponse(response);
                return dto.getMovieListResult().getMovieList().stream()
                        .map(MovieInfo::getMovieName)
                        .collect(Collectors.toList());
            }
        } catch (Exception e){
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    private KobisDto getDtoFromResponse(HttpResponse<String> response) throws JsonProcessingException {
        return mapper.readValue(response.body(),KobisDto.class);
    }
}
