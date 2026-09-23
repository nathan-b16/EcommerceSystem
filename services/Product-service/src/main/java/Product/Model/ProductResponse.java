package Product.Model;

import java.math.BigDecimal;

public record ProductResponse(
        String Id,
        String ProductName,
        double price,
        ProductCategory categoryId,
        Integer quantity
) {
}
