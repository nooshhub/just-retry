package com.nooshhub;

public class RecursiveGoldService {

    private final GoldRepository goldRepository = new GoldRepository();

    // 递归
    public void saveGold(Integer amount, int i, int maxRetries) {
        System.out.println("Run once!");

        try {
            goldRepository.save(amount);
        } catch (ConnectionException ex) {
            if (i > maxRetries) {
                throw new RuntimeException(ex);
            }

            System.out.println("retry" + i);
            saveGold(amount, ++i, maxRetries);
        }
    }

    public static void main(String[] args) {
        RecursiveGoldService goldService = new RecursiveGoldService();
        goldService.saveGold(1, 1, 3);
    }
}





