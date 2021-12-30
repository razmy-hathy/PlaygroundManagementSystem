package com.playground;

public class TestHelper {

    public static void waitForSeconds(long seconds){
        try {
            Thread.sleep(1000*seconds);
        } catch (InterruptedException e) {

        }
    }
}
