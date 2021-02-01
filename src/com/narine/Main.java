package com.narine;

import java.util.Random;

public class Main {

    public static int[] heroesHealth = {270, 280, 250, 520, 290, 260, 300}; // жизни героев
    public static int[] heroesDamage = {20, 15, 25, 10, 20, 15, 25}; //урон который они несут при ударе
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Golem", "Lucky", "Berserk", "Thor"}; //тип атаки героев


    public static int bossHealth = 1000;
    public static int bossDamage = 50;
    public static String bossDefenceType = ""; //защита
    public static int round_number = 0;

    public static int doctorHealth = 250;

    

    public static void main(String[] args) {
        printStatistics(); // распечатаем результат борьбы
        while (!isGameFinished()) {  //если gamefinished true, т.е. кто-то выиграл, то тру с отрицанием дает нам false
            round(); //при false цикл while закрывается и игра заканч-ся и наоборот если фолс то с ! будет тру
        }

    }

    public static void round() {
        round_number++;
        bossDefenceType = changeBossDefence();
        System.out.println("Boss choose: " + bossDefenceType);

        bossHits();
        heroesHit();
        doctorsTreatment();
        printStatistics();

    }

    public static boolean isGameFinished() {    //проверка закончена ли игра
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }

        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }

        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static String changeBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // будет случайным образом выбираться что-то из heroesAttack
        return heroesAttackType[randomIndex];
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) { //fori
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                heroesHealth[i] = heroesHealth[i] - ((bossDamage /5) * 4); //босс бьет героев, 1/5 уходит голем
                if (heroesHealth[i] < 0) {
                    heroesHealth[i] = 0;
                }
            }
        }
        if (doctorHealth > 0 && bossHealth > 0) {
            doctorHealth = doctorHealth - ((bossDamage /5) * 4); // 1/5 уходит голему
            if (doctorHealth < 0) {
                doctorHealth = 0;
            }
        }


    }

    public static void doctorsTreatment() {

        Random random = new Random();
        int medicalCoeff = random.nextInt(3) + 2; // либо 2 либо 3
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth [i] < 100 && heroesHealth[i] > 0 && doctorHealth > 0) {
                heroesHealth [i] = heroesHealth [i] + (20 * medicalCoeff);
                System.out.println("Doctor's treatment: " +
                        (20 * medicalCoeff) + " [" + medicalCoeff + "] ");
            }
            else {
                heroesHealth [i] = heroesHealth [i] + (0 * medicalCoeff);
            }
        }
    }

    public static void heroesHit() {
        Random random = new Random();
        int coeff = random.nextInt(8) + 2; // 2,3,4,5,6,7,8,9 // +2 потому что нам не нужны цифры 0 и 1
        for (int i = 0; i < heroesDamage.length; i++) { //fori
            if (bossHealth > 0 && heroesHealth[i] > 0) {
                if (heroesAttackType[i] == bossDefenceType) {//если атака героя совпадет с защитой босса
                    bossHealth = bossHealth - heroesDamage[i] * coeff; // то внести критич урон * коэфф урона
                    System.out.println("Critical damage: " + heroesDamage[i] * coeff + " [" + coeff + "] ");
                } else {
                    bossHealth = bossHealth - heroesDamage[i];//герои бьют босса
                }
                if (bossHealth < 0) {
                    bossHealth = 0;
                }
            }
        }
    }


    public static void printStatistics() {
        System.out.println("                    ");
        System.out.println("-----------ROUND [" + round_number + "]");
        System.out.println("                    ");
        System.out.println("Boss health: " + bossHealth + " [" + bossDamage + "]");
        System.out.println("Doctor Health: " + doctorHealth );
        for (int i = 0; i < heroesAttackType.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " [" +
                    heroesDamage[i] + "]");

        }
        System.out.println("                    ");
        System.out.println("--------------------");
    }
}
