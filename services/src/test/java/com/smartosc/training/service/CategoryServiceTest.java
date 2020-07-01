package com.smartosc.training.service;

import com.smartosc.training.dto.CategoryDTO;
import com.smartosc.training.entity.Category;
import com.smartosc.training.entity.Product;
import com.smartosc.training.repositories.CategoryRepository;
import com.smartosc.training.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * @description:
 * @author: ductd
 * @since: 21/4/2020
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private ModelMapper modelMapper;
    @Before
    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findByNameCategorySuccess(){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("name");
        List<CategoryDTO> categoryDTOList;
        Category category = new Category();
        category.setCategoryId(1);
        when(categoryRepository.findByName(anyString())).thenReturn(Arrays.asList(category, category));
        categoryDTOList = categoryService.findByNameCategory(anyString());
        assertNotNull(categoryDTOList);
    }

    @Test
    public void findByIdSuccess(){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(1);
        categoryDTO.setCategoryName("name");
        Category category = new Category();
        category.setCategoryId(1);

        Product product = new Product();
        product.setProductId(1);
        product.setImage("image");
        product.setDescription("description");
        product.setName("name");
        product.setStatus(1);
        List<Product> products = Arrays.asList(product);
        category.setProducts(products);
        when(modelMapper.map(category, CategoryDTO.class)).thenReturn(categoryDTO);
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));
        categoryDTO = categoryService.findById(anyInt());
        assertNotNull(categoryDTO);
    }
}