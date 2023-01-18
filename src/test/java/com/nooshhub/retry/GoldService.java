package com.nooshhub.retry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoldService {

    @Autowired
    private GoldRepository goldRepository;

    public Integer saveGold(Integer amount) {
        System.out.println("Run once!");
        return goldRepository.save(amount);
    }

}





