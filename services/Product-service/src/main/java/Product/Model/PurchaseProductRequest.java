package Product.Model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.mapstruct.EnumMapping;

import java.math.BigDecimal;

public record PurchaseProductRequest(
    @NotNull(message = "Id is required")
    String productId,
    @NotNull(message = "Quantity is must")
    Integer quantity
) {
}
