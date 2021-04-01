package spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpec {
    RequestSpecification requestSpecification;
    String baseUrl= "https://api.spotify.com/v1";
    public static String AUTH_TOKEN="BQD_rB7H_rFwRdWr1ogb6XYOFLHODMIa6O0F9mjihS1Ib6GWgv1-gPyfhCKIE9dXhvHONGSwFCDmM4U57V0QS-xRxbyGUcSGAej-n5X4c1XXkIO3sOoIHMh0CDh9ANeTZ2dyI8LZgesGJC5uzr9dDtH-f5O4_Vyy9fvZzv5bKeOlVgk_Icc5HVW1vKXSuJJWNftL6FpERm7lnXljlV76WoY52kAhrU-8IxW9jg1OZ_uIa6c2JXwKFaMM3rt7GWPUvdX5JB7SL5HN7RxHFTYiqDtX2FTA8MNE1bUUY9TU";
    public RequestSpec(String baseUrl){
        requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + AUTH_TOKEN)
                .setBaseUri(baseUrl)
                .setContentType("application/json")
                .build();
    }

    public RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }
}
