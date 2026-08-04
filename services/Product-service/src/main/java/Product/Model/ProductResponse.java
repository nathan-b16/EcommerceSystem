package Product.Model;

import java.math.BigDecimal;

public record ProductResponse(
        String Id,
        String ProductName,
        BigDecimal price,
        ProductCategory categoryId,
        Integer quantity
) {
}
