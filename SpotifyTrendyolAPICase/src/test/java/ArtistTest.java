import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import spec.RequestSpec;
import spec.ResponseSpec;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

public class ArtistTest extends RequestSpec {
    ResponseSpec responseSpec = new ResponseSpec();

    public ArtistTest(String baseUrl) {
        super("https://api.spotify.com/v1");
    }
    String pinhaniArtistId="4Bdqzh78prwuqwInMb555P";
    @Test
    public void GetArtistTopTrack() {
        Response artıstGetResponse =
                given()
                        .spec((super.getRequestSpecification()))
                        .queryParam("limit", "3")
                        .queryParam("market", "TR")
                        .when()
                        .get("artists/{id}/top-tracks",pinhaniArtistId)
                        .then()
                        .spec(responseSpec.checkStatusCodeOk())
                        .extract()
                        .response();
        artıstGetResponse.prettyPeek();

    //populerlik durumunu inceleyip en populer 3 şarkıyı seç
    }
}
