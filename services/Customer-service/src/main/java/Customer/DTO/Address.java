package Customer.DTO;


import lombok.*;
import org.springframework.validation.annotation.Validated;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Validated
public class Address {

    private String country;
    private String city;
    private String zipcode;
}
