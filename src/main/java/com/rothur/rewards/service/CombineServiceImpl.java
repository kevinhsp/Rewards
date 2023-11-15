package com.rothur.rewards.service;

import com.rothur.rewards.dao.PurchaseRepository;
import com.rothur.rewards.dao.UserRepository;
import com.rothur.rewards.entity.PurchaseEntity;
import com.rothur.rewards.entity.UserEntity;
import com.rothur.rewards.model.Purchase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rothur.rewards.utils.PointsCalculation.calculatePoints;

@Service
public class CombineServiceImpl implements CombineService {

    private UserRepository userRepository;
    private PurchaseRepository purchaseRepository;

    @Autowired
    public CombineServiceImpl(UserRepository userRepository, PurchaseRepository purchaseRepository){
        this.userRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
    }
    @Override
    @Transactional
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
    @Transactional
    public Set<PurchaseEntity> findPurchasesByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getPurchases();
    }

    @Override
    @Transactional
    public Long getAllPointsByUserId(Long userId) {
        return findPurchasesByUserId(userId)
                .stream()
                .mapToLong(PurchaseEntity::getPoints)
                .sum();
    }

    @Override
    @Transactional
    public Set<PurchaseEntity> findYearMonthPurchasesByUserId(Long userId, int year, int month) {
        return findPurchasesByUserId(userId).stream()
                .filter(purchase ->
                        purchase.getDate().getMonthValue() == month &&
                                purchase.getDate().getYear() == year)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Long getYearMonthPointsByUserId(Long userId, int year, int month) {
        return findYearMonthPurchasesByUserId(userId, year, month)
                .stream()
                .mapToLong(PurchaseEntity::getPoints)
                .sum();
    }


    @Override
    @Transactional
    public UserEntity saveUser(UserEntity user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public UserEntity getUserByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public PurchaseEntity updatePurchase(Long userId, Long purchaseId, Purchase purchase) {
        PurchaseEntity previousPurchase = purchaseRepository.findById(
                purchaseId).orElseThrow(
                        () -> new RuntimeException("Purchase Id not found."));
        if(!previousPurchase.getUserEntity().getId().equals(userId)) throw new RuntimeException("Not correct user or purchase");
        previousPurchase.setDate(purchase.getDate());
        previousPurchase.setPrice(purchase.getPrice());
        previousPurchase.setPoints(calculatePoints(purchase.getPrice()));
        purchaseRepository.save(previousPurchase);
        return previousPurchase;
    }

    @Override
    @Transactional
    public PurchaseEntity deletePurchase(Long userId, Long purchaseId) {
        PurchaseEntity previousPurchase = purchaseRepository.findById(
                purchaseId).orElseThrow(
                () -> new RuntimeException("Purchase Id not found."));
        if(!previousPurchase.getUserEntity().getId().equals(userId))
            throw new RuntimeException("Not correct user or purchase");
        purchaseRepository.deleteById(purchaseId);
        return previousPurchase;
    }
}
