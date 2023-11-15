package com.rothur.rewards;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rothur.rewards.controller.CombineController;
import com.rothur.rewards.entity.UserEntity;
import com.rothur.rewards.model.Purchase;
import com.rothur.rewards.service.CombineService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CombineController.class)
public class CombineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CombineService combineService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createUserTest() throws Exception {
        UserEntity user = new UserEntity();
        user.setName("Test1");
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("user has been created"));
                
        verify(combineService, times(1)).saveUser(any(UserEntity.class));
    }


    @Test
    public void createPurchaseTest() throws Exception {
        Purchase purchase = new Purchase();
        // 设置Purchase对象的属性...
        purchase.setPrice(99.99);
        purchase.setDate(LocalDate.now());

        long userId = 1L;
        String purchaseJson = objectMapper.writeValueAsString(purchase);

        mockMvc.perform(post("/" + userId + "/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(purchaseJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("purchase has been created"));

        verify(combineService, times(1)).savePurchase(any(Purchase.class), eq(userId));
    }

    @Test
    public void updatePurchaseTest() throws Exception {
        long userId = 1L;
        long purchaseId = 2L;
        Purchase purchase = new Purchase();
        // 设置Purchase对象的属性...
        purchase.setPrice(199.99);
        purchase.setDate(LocalDate.now());

        String purchaseJson = objectMapper.writeValueAsString(purchase);

        mockMvc.perform(put("/" + userId + "/purchaseUpdate/" + purchaseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(purchaseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("purchase has been updated"));

        verify(combineService, times(1)).updatePurchase(eq(userId), eq(purchaseId), any(Purchase.class));
    }

    @Test
    public void deletePurchaseTest() throws Exception {
        long userId = 1L;
        long purchaseId = 2L;

        mockMvc.perform(delete("/" + userId + "/purchaseDelete/" + purchaseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("purchase has been deleted"));

        verify(combineService, times(1)).deletePurchase(userId, purchaseId);
    }
}
