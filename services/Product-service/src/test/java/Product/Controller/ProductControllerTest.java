package Product.Controller;

import Product.Exception.ProductNotFoundException;
import Product.Model.*;
import Product.Service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getProduct() throws Exception {
        List<ProductResponse> output = new ArrayList<>();
        when(service.getProduct()).thenReturn(output);

        mockMvc.perform(get("/api/v1/product"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(output.size()));
    }

    @Test
    void getProductByID() throws Exception {
        String id = "TestUnit-1";
        ProductResponse response = new ProductResponse(
                "TestUnit-1",
                "milk",
                5.80,
                ProductCategory.FOOD,
                50
        );
        when(service.getProductById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/product/productInfo/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Id").value("TestUnit-1"))
                .andExpect(jsonPath("$.ProductName").value("milk"));
    }
    @Test
    void getProduct_notFound() throws Exception {
        when(service.getProductById("TestUnit-2")).thenThrow(new ProductNotFoundException("Not Found"));

        mockMvc.perform(get("/api/v1/product/productInfo/{id}","TestUnit-2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getProductByCategory() throws Exception {
        ProductCategory category = ProductCategory.FOOD;
        List<ProductResponse> output = new ArrayList<>();
        when(service.getProductByCategory(category)).thenReturn(output);

        mockMvc.perform(get("/api/v1/product/category/{category}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(output.size()));
    }

    @Test
    void addProduct() throws Exception {
        ProductRequest request = new ProductRequest("TestUnit-3", "Bread",
                9.90, ProductCategory.FOOD,50
        );

        when(service.addProduct(request)).thenReturn(request.productId());

        mockMvc.perform(post("/api/v1/product/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("TestUnit-3"));
    }

    @Test
    void purchaseProducts() {
        List<PurchaseProductResponse> request = new ArrayList<>();



    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}