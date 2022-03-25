package com.slangmaster;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Slang> slangList = FileManager.readFile("sample.txt");
        System.out.println(slangList.size());
    }
}
