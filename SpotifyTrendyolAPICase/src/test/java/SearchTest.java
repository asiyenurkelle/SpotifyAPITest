import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import spec.RequestSpec;
import spec.ResponseSpec;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

public class SearchTest extends RequestSpec {
    ResponseSpec responseSpec= new ResponseSpec();

    public SearchTest(String baseUrl) {
        super("https://api.spotify.com/v1");
    }


    public void searchSong(String artistName) {
        Response searchSongResponse =
                given()
                        .contentType("application/json; charset=UTF-8")
                        .spec((super.getRequestSpecification()))
                        .queryParam("q", artistName)
                        .queryParam("type", "artist")
                        .queryParam("market", "TR")
                        .queryParam("limit", "3")
                        .when()
                        .get("search")
                        .then()
                        .spec(responseSpec.checkStatusCodeOk())
                        .extract()
                        .response();
        searchSongResponse.prettyPeek();
    }
    @Test
    public void SearchTest(){
        searchSong("Pinhani");

    }
}
