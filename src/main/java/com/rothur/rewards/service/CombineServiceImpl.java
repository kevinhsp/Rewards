package com.rothur.rewards.service;

import com.rothur.rewards.dao.PurchaseRepository;
import com.rothur.rewards.dao.UserRepository;
import com.rothur.rewards.entity.PurchaseEntity;
import com.rothur.rewards.entity.UserEntity;
import com.rothur.rewards.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.rothur.rewards.utils.PointsCalculation.calculatePoints;

@Service
public class CombineServiceImpl implements CombineService {

    private UserRepository userRepository;
    private PurchaseRepository purchaseRepository;

    @Autowired
    public CombineServiceImpl(UserRepository userRepository, PurchaseRepository purchaseRepository){
        this.userRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
    };
    @Override
    public PurchaseEntity savePurchase(Purchase purchase, Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setDate(purchase.getDate());
        purchaseEntity.setUserEntity(user);
        purchaseEntity.setPrice(purchase.getPrice());
        purchaseEntity.setPoints(calculatePoints(purchase.getPrice()));
        user.getPurchases().add(purchaseEntity);
        purchaseRepository.save(purchaseEntity);
        return purchaseEntity;
    }

    @Override
    public Set<PurchaseEntity> findPurchasesByUserIdAndMonth(String month, Long userId) {
        return null;
    }


    @Override
    public Set<PurchaseEntity> findPurchasesByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getPurchases();
    }

    @Override
    public Long getAllPointsByUserId(Long userId) {
        return findPurchasesByUserId(userId)
                .stream()
                .mapToLong(PurchaseEntity::getPoints)
                .sum();
    }

    @Override
    public UserEntity saveUser(UserEntity user) {
        userRepository.save(user);
        return user;
    }
}
