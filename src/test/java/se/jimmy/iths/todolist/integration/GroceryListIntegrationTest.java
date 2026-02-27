package se.jimmy.iths.todolist.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import se.jimmy.iths.todolist.model.GroceryList;
import se.jimmy.iths.todolist.repository.GroceryListRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class GroceryListIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GroceryListRepository repository;

    @Test
    @DisplayName("Create grocery and verify it is saved")
    public void createGrocery_ShouldSaveEntity() throws Exception {

        mockMvc.perform(post("/groceries")
                        .param("name", "Milk")
                        .param("quantity", "2")
                        .param("category", "Dairy")
                        .param("purchased", "false"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groceries"));

        List<GroceryList> groceryList = repository.findAll();
        assertEquals(1, groceryList.size());
        assertEquals("Milk", groceryList.getFirst().getName());
    }

    @Test
    @DisplayName("Get a grocery by ID and verify it is returned")
    public void getGroceryById_ShouldReturnExistingGrocery() throws Exception {
        GroceryList grocery = new GroceryList("Apple", 3, "Fruit", false);
        GroceryList saved = repository.save(grocery);

        mockMvc.perform(get("/groceries/" + saved.getId()))
                .andExpect(status().isOk());
        GroceryList retrieved = repository.findById(saved.getId()).orElse(null);
        assertThat(retrieved).isNotNull();
        assertThat(retrieved.getName()).isEqualTo("Apple");
    }

    @Test
    @DisplayName("Get all groceries and verify count")
    public void getAllGroceries_ShouldReturnCorrectCount() throws Exception {
        repository.save(new GroceryList("Milk", 1, "Dairy", false));
        repository.save(new GroceryList("Bread", 2, "Bakery", false));
        repository.save(new GroceryList("Eggs", 12, "Dairy", false));

        mockMvc.perform(get("/groceries"))
                .andExpect(status().isOk());

        assertThat(repository.findAll()).hasSize(3);
    }

}
