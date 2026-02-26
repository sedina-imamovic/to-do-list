package se.jimmy.iths.todolist.integration;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import se.jimmy.iths.todolist.model.GroceryList;
import se.jimmy.iths.todolist.repository.GroceryListRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GroceryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GroceryListRepository repository;

    @Test
    @DisplayName("Create a grocery and verify it is saved")
    public void createGrocery_ShouldSaveEntity() throws Exception {

        mockMvc.perform(post("/groceries")
                        .param("name", "Milk")
                        .param("quantity", "2")
                        .param("category", "Dairy")
                        .param("purchased", "false"))
                .andExpect(status().is3xxRedirection());

        assert (repository.findAll().size() == 1);
        GroceryList saved = repository.findAll().get(0);
        assert (saved.getName().equals("Milk"));
    }

    @Test
    @DisplayName("Get a grocery by ID and verify it is returned")
    public void getGroceryById_ShouldReturnExistingEntity() throws Exception {

        GroceryList grocery = new GroceryList("Apple", 3, "Fruit", false);
        GroceryList saved = repository.save(grocery);

        mockMvc.perform(get("/groceries/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("grocery"))
                .andExpect(view().name("grocery"));
    }


    @Test
    @DisplayName("Get all groceries and verify count")
    public void getAllGroceries_ShouldReturnCorrectCount() throws Exception {

        repository.save(new GroceryList("Milk", 1, "Dairy", false));
        repository.save(new GroceryList("Bread", 2, "Bakery", false));
        repository.save(new GroceryList("Eggs", 12, "Dairy", false));

        mockMvc.perform(get("/groceries"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("groceries"))
                .andExpect(view().name("groceries"));

        assert (repository.findAll().size() == 3);
    }
}
