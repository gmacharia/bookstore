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
