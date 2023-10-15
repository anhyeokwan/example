package com.example.example.util;

import java.util.ArrayList;

public class OutOfMemoryThreadDemo {
    public static void main(String[] args) {

        ArrayList<byte[]> list = new ArrayList<>();
        int i = 0;
        while (true) {
            try {
                i++;
                list.add(new byte[1000000]);
                System.out.println(i);
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Worker implements Runnable{

    @Override
    public void run() {

    }

}
