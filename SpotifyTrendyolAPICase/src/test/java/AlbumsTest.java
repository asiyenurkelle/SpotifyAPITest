import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import spec.RequestSpec;
import spec.ResponseSpec;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

public class AlbumsTest extends RequestSpec {
    ResponseSpec responseSpec= new ResponseSpec();
    public AlbumsTest(String baseUrl) {
        super("https://api.spotify.com/v1");
    }

    //albume verilen limit kontrolu
    @Test
    public void GetAlbums() {
        Response albumsGetResponse =
                given()
                        .spec((super.getRequestSpecification()))
                        .queryParam("limit", "3")
                        .queryParam("market", "TR")
                        .when()
                        .get("/me/albums")
                        .then()
                        .spec(responseSpec.checkStatusCodeOk())
                        .extract()
                        .response();
        albumsGetResponse.prettyPeek();
        String limitControl = albumsGetResponse.getBody().jsonPath().getString("limit");
        assertThat(limitControl, Matchers.equalTo("3"));
    }
    @Test
    public void GetAlbumsTrack() {
        Response albumsTrackGetResponse =
                given()
                        .spec((super.getRequestSpecification()))
                        .queryParam("limit", "10")
                        .queryParam("offset", "5")
                        .queryParam("market", "TR")
                        .when()
                        .get("albums/{id}/tracks","4aawyAB9vmqN3uQ7FjRGTy")
                        .then()
                        .spec(responseSpec.checkStatusCodeOk())
                        .extract()
                        .response();
        albumsTrackGetResponse.prettyPeek();
        String name = albumsTrackGetResponse.getBody().jsonPath().getString("name");
        assertThat(name, Matchers.containsString("Pitbull"));

    }
    //kaydedilen albumumun olmadığının kontrolu
    @Test
    public void GetCurrentUsersSavedAlbums() {
        Response GetCurrentUsersSavedAlbums =
                given()
                .spec((super.getRequestSpecification()))
                .queryParam("limit", "3")
                .queryParam("offset", "3")
                .queryParam("market", "TR")
                .when()
                .get("/me/albums")
                .then()
                .spec(responseSpec.checkStatusCodeOk())
                .extract()
                .response();

        GetCurrentUsersSavedAlbums.prettyPeek();
        String totalSavedAlbum = GetCurrentUsersSavedAlbums.getBody().jsonPath().getString("total");
        assertThat(totalSavedAlbum, Matchers.equalTo("0"));
    }

}
