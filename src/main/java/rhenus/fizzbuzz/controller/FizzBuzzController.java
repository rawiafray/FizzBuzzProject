package rhenus.fizzbuzz.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rhenus.fizzbuzz.model.FizzBuzzModel;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RestController
public class FizzBuzzController {

    //Use produces annotation to produce json object by restful api service
    @RequestMapping(value = "/fizzbuzz/{number}", produces = "application/json")
    public ResponseEntity<FizzBuzzModel> fizzBuzz(@PathVariable("number") int number) {

        final FizzBuzzModel fizzBuzzObj = new FizzBuzzModel();
        final List<String>  fizzBuzzList = new ArrayList<>();

        // Return this error if the number is negative
        if (number < 1){
            throw new IllegalArgumentException("The number cannot be negative!");
        }

        // Loop through the range of numbers, determine if a number is fizz or buzz or fizzbuzz and build the list.
        IntStream.range(1, number + 1) .forEach(i -> {
            if (i % 3 == 0 && i % 5 == 0) {                     //multiple of 3 & 5
                fizzBuzzList.add("FizzBuzz");
            } else if (i % 3 == 0) {                            //multiple of 3
                fizzBuzzList.add("Fizz");
            } else if (i % 5 == 0) {                            //multiple of 5
                fizzBuzzList.add("Buzz");
            } else {
                fizzBuzzList.add(String.valueOf(i));
            }

        });

        fizzBuzzObj.setFizzBuzz(fizzBuzzList);
        return new ResponseEntity<>(fizzBuzzObj, HttpStatus.OK);
    }

    // Return this error if the number is non-integer.
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public void handleParameterTypeMismatch(IllegalArgumentException e, HttpServletResponse response)  throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "The number cannot be non-integer!");
    }

    // Return this error if there is an issue serializing JSON.
    @ExceptionHandler(JsonProcessingException.class)
    public void handleSerializationError(JsonProcessingException e, HttpServletResponse response)  throws IOException {
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "There was an error serializing an object to JSON.");
    }


}
