package Training;

import module.Product;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;

public class Sweatband {

    @Test
    public void createSweatband(){
        String endpoint = "http://localhost:80/api_testing/product/create.php";
        Product product = new Product(
                "Sweatband",
                "Practiceitem",
                5,
                3
        );
        var response = given().body(product).when().post(endpoint).then();
        response.log().body();
    }

    @Test
    public void updateSweatband(){
        String endpoint = "http://localhost:80/api_testing/product/update.php";
        String apibody = """
                {
                    "id" : 1004,
                    "price" : 6
                }                
                """;
        var response = given().body(apibody).put(endpoint).then();
        response.log().body();
    }

    @Test
    public void querySweatband(){
        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        var response = given().queryParam("id", 1004).when().get(endpoint).then();
        response.log().body();

    }

    @Test
    public void deleteSweatband(){
        String endpoint = "http://localhost:80/api_testing/product/delete.php";
        String body = """
                {
                    "id" : 1004
                }
                """;
        var response = given().body(body).when().delete(endpoint).then();
        response.log().body();
    }

}
