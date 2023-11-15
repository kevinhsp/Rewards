package com.rothur.rewards.service;

import com.rothur.rewards.entity.PurchaseEntity;
import com.rothur.rewards.entity.UserEntity;
import com.rothur.rewards.model.Purchase;

import java.util.List;
import java.util.Set;

public interface CombineService {

    PurchaseEntity savePurchase(Purchase purchase, Long userId);

    Set<PurchaseEntity> findYearMonthPurchasesByUserId(Long userId, int year, int month);

    Long getYearMonthPointsByUserId(Long userId, int year, int month);
    Set<PurchaseEntity> findPurchasesByUserId(Long userId);

    Long getAllPointsByUserId(Long userId);

    UserEntity saveUser(UserEntity user);

    UserEntity getUserByUserId(Long userId);

    PurchaseEntity updatePurchase(Long userId, Long purchaseId, Purchase purchase);

    PurchaseEntity deletePurchase(Long userId, Long purchaseId);

}
