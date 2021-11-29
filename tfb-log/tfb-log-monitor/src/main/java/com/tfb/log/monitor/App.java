package com.tfb.log.monitor;

import com.tfb.log.common.model.NormalEvent;

/**
 * Hello world!
 */
public class App {
//    public static void main(String[] args) {
//        System.out.println("Hello World!");
//    }


    public static void main(String[] args) {
        NormalEvent normalEvent = new NormalEvent();
        normalEvent.addError("1");

        System.out.println(normalEvent);
        normalEvent.addError("2");
        System.out.println(normalEvent);
    }
}
