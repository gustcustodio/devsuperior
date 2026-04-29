package com.devsuperior.dscommerce.controllers.it;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.tests.CategoryFactory;
import com.devsuperior.dscommerce.tests.ProductFactory;
import com.devsuperior.dscommerce.tests.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private String productName;
    private String clientUsername, clientPassword, adminUsername, adminPassword;
    private String clientToken, adminToken, invalidToken;
    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() throws Exception {
        productName = "Macbook";
        clientUsername = "maria@gmail.com";
        clientPassword = "123456";
        adminUsername = "alex@gmail.com";
        adminPassword = "123456";
        clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
        adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
        invalidToken = adminToken + "xpto"; // Simulates a wrong token
        product = ProductFactory.createProduct();
        product.getCategories().add(CategoryFactory.createCategory());
        productDTO = new ProductDTO(product);
    }

    @Test
    public void findAllShouldReturnPageWhenNameParamIsEmpty() throws Exception {
        ResultActions result = mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].name").value("The Lord of the Rings"))
                .andExpect(jsonPath("$.content[0].price").value(90.5))
                .andExpect(jsonPath("$.content[0].imgUrl").value("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg"));
    }

    @Test
    public void findAllShouldReturnPageWhenProductNameParamIsNotEmpty() throws Exception {
        ResultActions result = mockMvc.perform(get("/products?name={productName}", productName).accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(3L))
                .andExpect(jsonPath("$.content[0].name").value("Macbook Pro"))
                .andExpect(jsonPath("$.content[0].price").value(1250.0))
                .andExpect(jsonPath("$.content[0].imgUrl").value("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/3-big.jpg"));
    }

    @Test
    public void insertShouldReturnProductDTOCreatedWhenLoggedAsAdmin() throws Exception {
        ResultActions result = mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(objectMapper.writeValueAsString(productDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(26))
                .andExpect(jsonPath("$.name").value("Console PlayStation 5"))
                .andExpect(jsonPath("$.description").value("consectetur adipiscing elit, sed"));
    }

    // * possibilidade de refatorar para teste parametrizado * //

    @Test
    public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndInvalidName() throws Exception {
        product.setName("ab");
        productDTO = new ProductDTO(product);
        ResultActions result = mockMvc.perform(post("/products")
                .header("Authorization", "Bearer " + adminToken)
                .content(objectMapper.writeValueAsString(productDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndInvalidDescription() throws Exception {
        product.setDescription("ab");
        productDTO = new ProductDTO(product);
        ResultActions result = mockMvc.perform(post("/products")
                .header("Authorization", "Bearer " + adminToken)
                .content(objectMapper.writeValueAsString(productDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndPriceIsNegative() throws Exception {
        product.setPrice(-2.0);
        productDTO = new ProductDTO(product);
        ResultActions result = mockMvc.perform(post("/products")
                .header("Authorization", "Bearer " + adminToken)
                .content(objectMapper.writeValueAsString(productDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndPriceIsZero() throws Exception {
        product.setPrice(0.0);
        productDTO = new ProductDTO(product);
        ResultActions result = mockMvc.perform(post("/products")
                .header("Authorization", "Bearer " + adminToken)
                .content(objectMapper.writeValueAsString(productDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndProductHasNoCategory() throws Exception {
        product.getCategories().clear();
        productDTO = new ProductDTO(product);
        ResultActions result = mockMvc.perform(post("/products")
                .header("Authorization", "Bearer " + adminToken)
                .content(objectMapper.writeValueAsString(productDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isUnprocessableEntity());
    }

    // * fim - possibilidade de refatorar para teste parametrizado * //

}
