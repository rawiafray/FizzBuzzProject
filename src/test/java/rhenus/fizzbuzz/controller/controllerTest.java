package rhenus.fizzbuzz.controller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import rhenus.fizzbuzz.FizzbuzzApplication;
import rhenus.fizzbuzz.model.FizzBuzzModel;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = FizzbuzzApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class controllerTest {

    private static final String URL = "/fizzbuzz/";
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;
    public void FizzBuzzTest() {

    }

    private String Root() {
        return "http://localhost:" + port;
    }

    @Test
    public void testSizeFizzBuzz() {
        final int number = 16;
        final ResponseEntity<FizzBuzzModel> fizzBuzz =
                template.getForEntity(Root() + URL + number, FizzBuzzModel.class);
        assertArrayEquals(
                new String[] { "1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13",
                        "14", "FizzBuzz", "16" },fizzBuzz.getBody().getFizzBuzz().toArray());
        assertEquals(number,fizzBuzz.getBody().getFizzBuzz().size());
    }

    @Test
    public void fizzNumbers() {
        final int number = 7;
        final ResponseEntity<FizzBuzzModel> fizzBuzz =
                template.getForEntity(Root() + URL + number, FizzBuzzModel.class);

        assertArrayEquals(
                new String[]{"1", "2", "Fizz", "4", "Buzz", "Fizz", "7"}, fizzBuzz.getBody().getFizzBuzz().toArray());
        assertTrue(fizzBuzz.getBody().getFizzBuzz().get(2).equals("Fizz"));
        assertTrue(fizzBuzz.getBody().getFizzBuzz().get(5).equals("Fizz"));
    }

    @Test
    public void BuzzNumbers() {
        final int number = 11;
        final ResponseEntity<FizzBuzzModel> fizzBuzz =
                template.getForEntity(Root() + URL + number, FizzBuzzModel.class);

        assertArrayEquals(
                new String[]{"1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11"}, fizzBuzz.getBody().getFizzBuzz().toArray());
        assertTrue(fizzBuzz.getBody().getFizzBuzz().get(4).equals("Buzz"));
        assertTrue(fizzBuzz.getBody().getFizzBuzz().get(9).equals("Buzz"));
    }

    @Test
    public void FizzBuzzNumbers() {
        final int number = 16;
        final ResponseEntity<FizzBuzzModel> fizzBuzz =
                template.getForEntity(Root() + URL + number, FizzBuzzModel.class);
        assertArrayEquals(
                new String[]{"1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13",
                        "14", "FizzBuzz", "16"}, fizzBuzz.getBody().getFizzBuzz().toArray());
        assertTrue(fizzBuzz.getBody().getFizzBuzz().get(14).equals("FizzBuzz"));
    }


    @Test
    public void StatusCode() {
        final int number = 10;
        final ResponseEntity<FizzBuzzModel> fizzBuzz =
                template.getForEntity(Root() + URL + number, FizzBuzzModel.class);
        assertNotNull(fizzBuzz);
        assertEquals(number,fizzBuzz.getBody().getFizzBuzz().size());
        assertTrue(fizzBuzz.getStatusCode().is2xxSuccessful());
    }


    @Test
    public void InvalidNumber() {
        final String invalidPathVariable = "aaaaaaaa";
        final ResponseEntity<FizzBuzzModel> fizzBuzz =
                template.getForEntity(Root() + URL + invalidPathVariable, FizzBuzzModel.class);
        assertNotNull(fizzBuzz);
        assertTrue(fizzBuzz.getStatusCode().is4xxClientError());
        assertEquals(HttpStatus.BAD_REQUEST.value(), fizzBuzz.getStatusCodeValue());
    }



}
