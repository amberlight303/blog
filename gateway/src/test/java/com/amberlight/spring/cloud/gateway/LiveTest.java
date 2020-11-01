package com.amberlight.spring.cloud.gateway;

import static io.restassured.RestAssured.config;

public class LiveTest {
//
//    private final String ROOT_URI = "http://localhost:8080";
//    private final FormAuthConfig formConfig = new FormAuthConfig("/login", "username", "password");
//
//    @Before
//    public void setup() {
//        RestAssured.config = config().redirect(RedirectConfig.redirectConfig()
//            .followRedirects(false));
//    }
//
//    @Test
//    public void whenGetAllBooks_thenSuccess() {
//        final Response response = RestAssured.get(ROOT_URI + "/book-service/books");
//        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//        Assert.assertNotNull(response.getBody());
//    }
//
//    @Test
//    public void whenAccessProtectedResourceWithoutLogin_thenRedirectToLogin() {
//        final Response response = RestAssured.get(ROOT_URI + "/home/index.html");
//        Assert.assertEquals(HttpStatus.FOUND.value(), response.getStatusCode());
//        Assert.assertEquals("http://localhost:8080/login", response.getHeader("Location"));
//    }
//
//    @Test
//    public void whenAccessProtectedResourceAfterLogin_thenSuccess() {
//        final Response response = RestAssured.given()
//            .auth()
//            .form("user", "password", formConfig)
//            .get(ROOT_URI + "/book-service/books/1");
//        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//        Assert.assertNotNull(response.getBody());
//    }
//
//    @Test
//    public void whenAccessAdminProtectedResource_thenForbidden() {
//        final Response response = RestAssured.given()
//            .auth()
//            .form("user", "password", formConfig)
//            .get(ROOT_URI + "/rating-service/ratings");
//        Assert.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode());
//
//    }
//
//    @Test
//    public void whenAdminAccessProtectedResource_thenSuccess() {
//        final Response response = RestAssured.given()
//            .auth()
//            .form("admin", "admin", formConfig)
//            .get(ROOT_URI + "/rating-service/ratings");
//        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//        Assert.assertNotNull(response.getBody());
//    }
//
//    @Test
//    public void whenAdminAccessDiscoveryResource_thenSuccess() {
//        final Response response = RestAssured.given()
//            .auth()
//            .form("admin", "admin", formConfig)
//            .get(ROOT_URI + "/discovery");
//        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//    }
//
//    @Test
//    public void whenAddnewRating_thenSuccess() {
//
//        final Rating rating = new Rating();
//        rating.setBookId(1L);
//        rating.setStars(4);
//
//        // request the protected resource
//        final Response ratingResponse = RestAssured.given()
//            .auth()
//            .form("admin", "admin", formConfig)
//            .and()
//            .contentType(ContentType.JSON)
//            .body(rating)
//            .post(ROOT_URI + "/rating-service/ratings");
//        final Rating result = ratingResponse.as(Rating.class);
//        Assert.assertEquals(HttpStatus.OK.value(), ratingResponse.getStatusCode());
//        Assert.assertEquals(rating.getBookId(), result.getBookId());
//        Assert.assertEquals(rating.getStars(), result.getStars());
//    }
//
//    @Test
//    public void whenAddnewBook_thenSuccess() {
//        final Post post = new Post();
//        post.setTitle("How to spring cloud");
//        post.setAuthor("amberlight");
//
//        // request the protected resource
//        final Response bookResponse = RestAssured.given()
//            .auth()
//            .form("admin", "admin", formConfig)
//            .and()
//            .contentType(ContentType.JSON)
//            .body(post)
//            .post(ROOT_URI + "/book-service/books");
//        final Post result = bookResponse.as(Post.class);
//        Assert.assertEquals(HttpStatus.OK.value(), bookResponse.getStatusCode());
//        Assert.assertEquals(post.getAuthor(), result.getAuthor());
//        Assert.assertEquals(post.getTitle(), result.getTitle());
//
//    }
//
//    @Test
//    public void accessCombinedEndpoint() {
//        final Response response = RestAssured.given()
//            .auth()
//            .form("user", "password", formConfig)
//            .get(ROOT_URI + "/combined?bookId=1");
//        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//        Assert.assertNotNull(response.getBody());
//        final Post result = response.as(Post.class);
//        Assert.assertEquals(new Long(1), result.getId());
//        Assert.assertNotNull(result.getRatings());
//        Assert.assertTrue(result.getRatings().size() > 0);
//    }
}