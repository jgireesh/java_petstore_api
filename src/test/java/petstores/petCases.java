package petstores;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.delete;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;
import org.junit.Test;
import java.util.Random;

public class petCases {
    static int petID;

    /*
        Test 1 - This test is about calling petstore and validate response code is 200
     */
        @Test
        public void callThePetStoreandValidateResponse() {
            given().when().get("https://petstore.swagger.io/#/pet").then().statusCode(200);
        }

    /*
    Test 2 - This test is about adding pet to the petstore
    */
    @Test
    public void IAddPetToTheStore() {
        Random rand = new Random();
        try {
            int id = rand.nextInt(9999);
            String jsonPet = "{\n" +
                    "  \"id\": "+id+"+,\n" +
                    "  \"category\": {\n" +
                    "    \"id\": 1,\n" +
                    "    \"name\": \"tiger\"\n" +
                    "  },\n" +
                    "  \"name\": \"tigers\",\n" +
                    "  \"photoUrls\": [\n" +
                    "    \"https://www.w3schools.com/html/tryit.asp?filename=tryhtml_images_alt_chania\"\n" +
                    "  ],\n" +
                    "  \"tags\": [\n" +
                    "    {\n" +
                    "      \"id\": 1,\n" +
                    "      \"name\": \"animal image\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"status\": \"available\"\n" +
                    "}";

            given().contentType("application/json")
                    .content(jsonPet).post("https://petstore.swagger.io/v2/pet").then().statusCode(200);

            petID=id;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /*
   Test 3 - This test is about Pet should be available
   */
    @Test
    public void cratedPetShouldbeAvailable() {
        given().get("http://petstore.swagger.io/v2/pet/2")
                .then().statusCode(200)
                .and().body("name",equalTo("Misha"));
    }

    /*
      Test 4 - This test is to Delete the Pet
      */
    @Test
    public void iDeletePet() {
        delete("http://petstore.swagger.io/v2/pet/2")
                .then().statusCode(200)
                .and().body("name", equalTo("Misha"));
    }

    /*
  Test 5 - This test is to validate Deleted Pet
  */
    @Test
    public void deletedPetShouldNotAvailable() {
        given().get("http://petstore.swagger.io/v2/pet/2")
                .then().statusCode(200)
                .and().body("name",containsString("NotFound"));
    }

}
