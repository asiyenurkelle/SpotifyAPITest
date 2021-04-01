import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import com.google.common.io.Resources;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;
import spec.RequestSpec;
import spec.ResponseSpec;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class PlayListTest extends RequestSpec {
    String userId = "";
    String playlistId="3U218SDwnc2gmzhzqIXqCs";
    String trackUri="";

    ResponseSpec responseSpec= new ResponseSpec();
    public PlayListTest() {
        super("https://api.spotify.com/v1");
    }

    //userId almak.
    public void getRegisteredUserID() throws IOException {

        Response responseBody =
                given()
                       .spec((super.getRequestSpecification()))
                        .when()
                        .get("/me")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();


        userId = responseBody.getBody().jsonPath().getString("id");
        System.out.println(userId);

    }

    //Playlist oluşturmak
    public void createNewPlaylist() throws IOException {
        URL file = Resources.getResource("newPlayList.json");
        String myJson = Resources.toString(file, Charset.defaultCharset());
        JSONObject json = new JSONObject(myJson);
        Response playlistResponse =
                given()
                        .contentType("application/json; charset=UTF-8")
                        .spec((super.getRequestSpecification()))
                        .body(json.toString())
                        .when()
                        .post("users/{userId}/playlists",userId)
                        .then()
                        .statusCode(201)
                        .extract()
                        .response();

        playlistId = playlistResponse.getBody().jsonPath().getString("id");
        System.out.println("PlaylistID: "+  playlistId);
        playlistResponse.prettyPeek();
    }



    //Playliste track eklemek.
    public void addTracksToPlaylist(){
        given()
                .contentType("application/json; charset=UTF-8")
                .spec((super.getRequestSpecification()))
                .queryParam("playlist_id",playlistId)
                .when()
                .post("playlists/{playlist_id}/tracks",playlistId)
                .then()
                .statusCode(201);
    }
    //playlistten track silmek.
    public void deleteTrack(String trackUri, String playlistId){
        URL file = Resources.getResource("deleteTrack.json");
        JSONObject json = new JSONObject(file);
        json.getJSONArray("tracks").getJSONObject(0).put("uri",trackUri);

        given()
                .spec(super.getRequestSpecification())
                .body(json.toString())
                .delete("/{playlist_id}/tracks",playlistId)
                .then()
                .statusCode(400);

    }
    @Test
    public void playListTest() throws IOException {
        getRegisteredUserID();
        createNewPlaylist();
        addTracksToPlaylist();

    }


}