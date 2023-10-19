package main;


import main.capcha.GenerateCapcha;

public class Main {

    public static void main(String[] args) {
        System.out.println(GenerateCapcha.create("captcha.png"));
    }
}
