package com.bookstore;

import com.bookstore.request.dto.BookDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@AutoConfigureWebTestClient(timeout = "360000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookServiceApplicationTests {

    @Autowired
    private WebTestClient webClient;
    private static final String BOOKS = "/api/v1/books";
    
    public static final String INVALID_JWT = "JhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2OTM1OTAyMzAsInN1YiI6ImpvaG4tZG9lLWFkbWluQGxvZ2ljZWEuY29tIiwidXNlcklkIjoxLCJlbWFpbCI6ImpvaG4tZG9lLWFkbWluQGxvZ2ljZWEuY29tIiwiZ3JvdXBzIjpbeyJ1c2VyR3JvdXBJZCI6MSwiZ3JvdXBzIjp7Imdyb3VwSWQiOjEsImdyb3VwTmFtZSI6IkFkbWluIEdyb3VwIiwiZ3JvdXBEZXNjcmlwdGlvbiI6IkFkbWluIEdyb3VwIENhcGFiaWxpdGllcyIsImRhdGVDcmVhdGVkIjoxNjkzMjUxNDc3MDAwLCJwZXJtaXNzaW9ucyI6W3sicGVybWlzc2lvbklkIjo0LCJwZXJtaXNzaW9uU2NvcGUiOiJBRE1JTiIsInBlcm1pc3Npb25EZXNjcmlwdGlvbiI6IkFkbWluIGNhcGFiaWxpdGllcyIsImRhdGVDcmVhdGVkIjoxNjkzNDIxMTEzMDAwfV19LCJkYXRlQ3JlYXRlZCI6MTY5MzI1MTg2MTAwMH1dLCJleHAiOjMxNjkzNTkwMjMwfQ.LQOAQ83CrKqbQFyL4CUmvlAQ6CelGmMfkVtjBFGJnFk";
    public static final String VALID_JWT = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MTkxNjkwOTMsInN1YiI6InRlc3RlciIsInVzZXJJZCI6MSwiZW1haWwiOiJ0ZXN0ZXIiLCJncm91cHMiOlt7InVzZXJHcm91cElkIjozLCJncm91cHMiOnsiZ3JvdXBJZCI6MSwiZ3JvdXBOYW1lIjoiQmFzaWMgVXNlcnMiLCJncm91cERlc2NyaXB0aW9uIjoiQSBCYXNpYyB1c2VyIGNhbiBhZGQsIHVwZGF0ZSwgZmV0Y2ggYm9va3MiLCJkYXRlQ3JlYXRlZCI6MTcxOTE2ODcwMTAwMCwicGVybWlzc2lvbnMiOlt7InBlcm1pc3Npb25JZCI6NiwicGVybWlzc2lvblNjb3BlIjoiQ1JFQVRFIiwicGVybWlzc2lvbkRlc2NyaXB0aW9uIjoiQ3JlYXRlIFBlcm1pc3Npb24iLCJkYXRlQ3JlYXRlZCI6MTcxMDc1OTM0NzAwMH0seyJwZXJtaXNzaW9uSWQiOjcsInBlcm1pc3Npb25TY29wZSI6IlJFQUQiLCJwZXJtaXNzaW9uRGVzY3JpcHRpb24iOiJSZWFkIFBlcm1pc3Npb24iLCJkYXRlQ3JlYXRlZCI6MTcxMDc1OTM0NzAwMH0seyJwZXJtaXNzaW9uSWQiOjksInBlcm1pc3Npb25TY29wZSI6IlVQREFURSIsInBlcm1pc3Npb25EZXNjcmlwdGlvbiI6IlVQREFURSBQRVJNSVNTSU9OIiwiZGF0ZUNyZWF0ZWQiOjE3MTA3NTkzNDcwMDB9XX0sImRhdGVDcmVhdGVkIjoxNzE5MTY4NzY0MDAwfV0sImV4cCI6MTcxOTE2OTEyM30.D7CLwY0y86VUhEzno2EiAI7ORwot7XZaMTNAblj3Sb0";


    @Test
    @DisplayName("Test that we return no record found if no books are found.")
    public void testThatWeReturnNoRecordFoundIfNoBookIsFound() {
        this.webClient.get()
                .uri(BOOKS + "/84")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.statusmessage").isEqualTo("Book not found");
    }

    @Test
    @DisplayName("Test that a book is deleted successfully")
    public void testThatABookIsDeletedSuccessfully() {
        // Perform the deletion.
        this.webClient.delete()
                .uri(BOOKS + "/4")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.statuscode").isEqualTo(3);

        // Confirm that the task was deleted successfully.
        this.webClient.get()
                .uri(BOOKS + "/4")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.statusmessage").isEqualTo("Book not found");
    }

    @Test
    @DisplayName("Test that update of an existing book")
    public void testThatABookIsUpdatedSuccessfully() {
        var createBook = BookDTO.builder()
                .title("Man in the middle")
                .author("Jane Doe")
                .isbn("1234-5678-98766-7")
                .price(1000)
                .build();

        this.webClient.put()
                .uri(BOOKS + "/3")
                .body(BodyInserters.fromValue(createBook))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.statuscode").isEqualTo(3);

        this.webClient.get()
                .uri(BOOKS + "/3")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.statuscode").isEqualTo(3)
                .jsonPath("$.response.title").isEqualTo("Man in the middle");
    }

    @Test
    @DisplayName("Test that the user is able to fetch a list of books")
    public void testThatAUserCanFetchBooks() {
        this.webClient.get()
                .uri(BOOKS)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.statuscode").isEqualTo(3)
                .jsonPath("$.response.length()").isEqualTo("4");
    }

    @Test
    @DisplayName("Test that the user is able to search a list of books")
    public void testThatAUserCanSearchAListOfBooks() {
        this.webClient.get()
                .uri(BOOKS + "/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.statuscode").isEqualTo(3)
                .jsonPath("$.response.length()").isEqualTo("5");
    }

    @Test
    @DisplayName("Test that a book is saved successfully")
    public void testThatBookIsCreatedSuccessfully() {

        var createBook = BookDTO.builder()
                .title("This is a test record")
                .author("Jane Doe")
                .isbn("1234-5678-98766-7")
                .price(1000)
                .build();

        this.webClient.post()
                .uri(BOOKS)
                .body(BodyInserters.fromValue(createBook))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.statuscode").isEqualTo(3)
                .jsonPath("$.response.title").isEqualTo("This is a test record");
    }
}
