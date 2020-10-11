package rhenus.fizzbuzz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FizzBuzzModel {


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> FizzBuzz;

    //Getter
    public List<String> getFizzBuzz() {  return FizzBuzz; }

    //Setter
    public void setFizzBuzz(List<String> fizzBuzz) {
        FizzBuzz = fizzBuzz;
    }
}


