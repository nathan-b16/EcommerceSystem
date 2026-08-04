package Product.Model;


public record PurchaseProductResponse(
        String productId,
        String productName,
        double price,
        ProductCategory category,
        Integer quantity

) {
}
