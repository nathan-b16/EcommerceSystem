package Product.Model;

import jakarta.validation.constraints.NotNull;

public record PurchaseProductRequest(
    @NotNull(message = "Id is required")
    String productId,
    @NotNull(message = "Quantity is must")
    Integer quantity
) {
}
