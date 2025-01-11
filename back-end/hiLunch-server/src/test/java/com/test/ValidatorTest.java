package com.test;


import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;



    public class ValidatorTest {
        public static void main(String[] args) {
            try {
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                Validator validator = factory.getValidator();
                System.out.println("Validator loaded successfully!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

