package Customer.DTO;

import jakarta.validation.constraints.NotNull;


public record CustomerRequest(

     String id,
     @NotNull(message = "Firstname is required")
     String firstname,
     @NotNull(message = "Lastname is required")
     String lastname,
     @NotNull(message = "Email is required")
     String email,
     Address address
) {
}
