package com.rothur.rewards.controller;

import com.rothur.rewards.entity.PurchaseEntity;
import com.rothur.rewards.entity.UserEntity;
import com.rothur.rewards.model.AllPurchaseResponse;
import com.rothur.rewards.model.ErrorResponses;
import com.rothur.rewards.model.Purchase;
import com.rothur.rewards.model.ResponseMessage;
import com.rothur.rewards.service.CombineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class CombineController {

    @Autowired
    CombineService combineService;

    @PostMapping("/user")
    public ResponseEntity<ResponseMessage> createUser(@Validated @RequestBody UserEntity user) {
        combineService.saveUser(user);
        return new ResponseEntity<>(new ResponseMessage("user has been created", user), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/purchase")
    public ResponseEntity<ResponseMessage> createPurchase(@Validated @RequestBody Purchase purchase, @PathVariable("id") long id) {
        combineService.savePurchase(purchase, id);
        return new ResponseEntity<>(new ResponseMessage("purchase has been created", purchase), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/AllPoints")
    public ResponseEntity<AllPurchaseResponse> getAllPoints(@PathVariable("id") long id) {
        AllPurchaseResponse allPurchaseResponse= AllPurchaseResponse.builder()
                .allPoints(combineService.getAllPointsByUserId(id))
                .purchaseEntityList(combineService.findPurchasesByUserId(id))
                .build();
        return new ResponseEntity<>(allPurchaseResponse, HttpStatus.OK);

    }

    @PutMapping("/{userId}/purchaseUpdate/{purchaseId}")
    public ResponseEntity<ResponseMessage> updatePurchase(@PathVariable("userId") long userId, @PathVariable("purchaseId") long purchaseId) {
        return new ResponseEntity<>(new ResponseMessage(), HttpStatus.OK);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponses> exceptionHandlerStudentNotFound(Exception ex) {
        ErrorResponses error = new ErrorResponses();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
