package com.chameleon.credithistory.service;

import com.chameleon.credithistory.entity.Transaction;
import com.chameleon.credithistory.model.CreditSummary;
import com.chameleon.credithistory.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class CreditSummaryService {

    @Autowired
    private TransactionRepository transactionRepository;

    public CreditSummary getSummary(Long customerId){
        List<Transaction> txn = transactionRepository.findBycustomerId(customerId);
        double total = txn.stream().mapToDouble(Transaction::getAmount).sum();

        return new CreditSummary(customerId,
                calculateCategoryTotal(txn,"food",total),
                calculateCategoryTotal(txn,"travel",total),
                calculateCategoryTotal(txn,"fuel",total),
                calculateCategoryTotal(txn,"groceries",total));
    }
    //Find percentage of category average to total
    private double calculateCategoryTotal(List<Transaction> transactions, String category, double total) {
       double catSum = transactions.stream().filter(txn -> txn.getCategory().equalsIgnoreCase(category)).
                        mapToDouble(Transaction::getAmount).sum();
        BigDecimal percentage = BigDecimal.valueOf((catSum/total) *100).setScale(2, RoundingMode.HALF_UP);
        return percentage.doubleValue();
    }
}
