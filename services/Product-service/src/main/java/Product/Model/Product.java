package Product.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product")
public class Product {

    @Id
    private String productId;
    private String productName;
    private double price;
    private ProductCategory category;
    private Integer quantity;
}