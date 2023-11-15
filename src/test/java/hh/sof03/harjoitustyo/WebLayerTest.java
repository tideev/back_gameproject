package hh.sof03.harjoitustyo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
@SpringBootTest
@AutoConfigureMockMvc
public class WebLayerTest {

    @Autowired
    private MockMvc mockMvc;

   @Test
public void testWebLayer() throws Exception {
    this.mockMvc.perform(get("/"))
            .andDo(print())
            .andExpect(status().is2xxSuccessful()) //Tarkistetaan, että status on 200
            .andExpect(MockMvcResultMatchers.view().name("fpsgames")) // Oletettu näkymän nimi
            .andExpect(model().attributeExists("games")); // Oletettu attribuutin nimi
}
}
