package Training;

import module.Product;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiTests {

    @Test
    public void getCategories(){
       String endpoint = "http://localhost:80/api_testing/category/read.php";
       var response = given()
               .when()
               .get(endpoint)
               .then()
               .log()
               .headers()
               .header("Content-Type", equalTo("application/json; charset=UTF-8"))
               .assertThat()
               .statusCode(200)
               .body("records.size()", greaterThan(0))
               .body("records.id", everyItem(notNullValue()))
               .body("records.name", everyItem(notNullValue()))
               .body("records.description", everyItem(notNullValue()))
               .body("records.id[0]", equalTo("1"));

    }

    @Test
    public void getProduct(){
        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        var response =
                given()
                        .queryParam("id", 2).
                when()
                        .get(endpoint).
                then().
                    assertThat().
                        statusCode(200).
                        body("id", equalTo("2")).
                        body("name", equalTo("Cross-Back Training Tank")).
                        body("description", equalTo("The most awesome phone of 2013!")).
                        body("price", equalTo("299.00")).
                        body("category_id", equalTo("2")).
                        body("category_name", equalTo("Active Wear - Women"));

    }

    @Test
    public void createProduct(){
        String endpoint = "http://localhost:80/api_testing/product/create.php";
        String apibody = """
                {              
                    "name" : "Anna Shih",
                    "description" : "It's Anna product",
                    "price" : 100,
                    "category_id" : 3
                }
                """;
        var response = given().body(apibody).when().post(endpoint).then();
        response.log().body();
    }

    @Test
    public void updateProduct(){
        String endpoint = "http://localhost:80/api_testing/product/update.php";
        String apibody = """
                {
                "id": "1001",
                "name": "Test 2",
                "description": "It's Anna product",
                "price": "777.00",
                "category_id": "3",
                "category_name": "Active Wear - Unisex"
                }                            
                """;
        var response = given().body(apibody).when().put(endpoint).then();
        response.log().body();
    }

    @Test
    public void deleteProduct(){
        String endpoint = "http://localhost:80/api_testing/product/delete.php";
        String apibody = """
                {
                "id" : 1000
                }
                """;
        var response = given().body(apibody).when().delete(endpoint).then();
        response.log().body();
    }

    @Test
    public void createSerializedProduct(){
        String endpoint = "http://localhost:80/api_testing/product/create.php";
        Product product = new Product(
                "yayayayy",
                "20230115",
                12,
                3
        );
        var response = given().body(product).when().post(endpoint).then();
        response.log().body();
    }

    @Test
    public void getDeserializedProduct(){
        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        Product expectedProduct = new Product(
                2,
                "Cross-Back Training Tank",
                "The most awesome phone of 2013!",
                299.00,
                2,
                "Active Wear - Women"
        );

        Product actualProduct = given()
                .queryParam("id", "2")
                .when()
                .get(endpoint)
                .as(Product.class);
        assertThat(actualProduct, samePropertyValuesAs(expectedProduct));
    }
}
