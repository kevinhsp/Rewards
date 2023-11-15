package com.rothur.rewards;

import com.rothur.rewards.dao.PurchaseRepository;
import com.rothur.rewards.dao.UserRepository;
import com.rothur.rewards.entity.PurchaseEntity;
import com.rothur.rewards.entity.UserEntity;
import com.rothur.rewards.model.Purchase;
import com.rothur.rewards.service.CombineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CombineServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private CombineServiceImpl combineService;

    @Test
    public void savePurchaseTest() {
        // Arrange
        Long userId = 1L;
        Purchase purchase = new Purchase();
        purchase.setDate(LocalDate.now());
        purchase.setPrice(99.99);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setName("ServiceTest");
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        // Act
        PurchaseEntity result = combineService.savePurchase(purchase, userId);

        // Assert
        assertNotNull(result);
        verify(userRepository).findById(userId);
        verify(purchaseRepository).save(any(PurchaseEntity.class));
    }

    @Test
    public void getAllPointsByUserIdTest() {
        // Arrange
        Long userId = 1L;
        Set<PurchaseEntity> purchases = new HashSet<>();
        UserEntity userEntity = new UserEntity();
        userEntity.setName("TestPoints");
        PurchaseEntity purchaseEntity = new PurchaseEntity(1L, LocalDate.now(), 100.0, 50L,userEntity);
        userEntity.setPurchases(purchases);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        // Act
        Long points = combineService.getAllPointsByUserId(userId);

        // Assert
        assertNotNull(points);
        verify(userRepository).findById(userId);
    }

}
