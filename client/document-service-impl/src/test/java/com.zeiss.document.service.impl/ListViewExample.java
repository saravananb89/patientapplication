package com.zeiss.document.service.impl;

public class ListViewExample {

    public static void main(String[] args) {

        String item = "MaAdam Martin madamma ";

        String wordToFind = "ma";

        String[] split = item.split("(?i)" + wordToFind);

        System.out.println(split);
    }
}
