package nl.giorgos.appie;

import java.util.List;

import nl.giorgos.appie.country_news_list.CountryNews;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsFetcher {
    @GET("{category}")
    Call<CountryNews> news(
            @Path("category") String category,
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
}
