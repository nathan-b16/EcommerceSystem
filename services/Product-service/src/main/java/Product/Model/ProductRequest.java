package Product.Model;

public record ProductRequest(
         String productId,
         String productName,
         double price,
         ProductCategory category,
         Integer quantity
) {
}
