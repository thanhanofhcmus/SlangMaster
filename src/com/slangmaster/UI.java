package com.slangmaster;

import java.util.ArrayList;

public class UI {

    final private Interactor interactor = new Interactor();
    private boolean isRunning = true;
    final private Database database;

    UI(Database database) {
        this.database = database;
    }

    private static void println(String message) {
        System.out.println(message);
    }

    private void unimplemented() {
        println("Chuc nang nay chua duoc phat trien");
        interactor.pause();
    }

    public void run() {
        while (isRunning) {
            mainMenu();
        }
    }

    private void findByDefinition() {
        String word = interactor.getString("Nhap tu khoa: ");
        Slang slang = database.queryByDefinition(word);

        if (slang != null) {
            println(slang.toString());
        } else {
            println("Khong co trong tu dien");
        }
        interactor.pause();
    }

    private void findByMeaning() {
        String word = interactor.getString("Nhap nghia: ");
        Slang slang = database.queryByMeaning(word);

        if (slang != null) {
            println(slang.toString());
        } else {
            println("Khong co trong tu dien");
        }
        interactor.pause();
    }

    private void history() {
        ArrayList<History.Item> items = database.history.getItems();
        if (items.isEmpty()) {
            println("Lich su tim kiem rong");
        } else {
            println("Lich su tim kiem");
            for (final History.Item item : items) {
                println(item.toString());
            }
        }
        interactor.pause();
    }

    void reset() {
        ArrayList<Slang> slangList = FileManager.readFile("slang_default.txt");
        database.reset(slangList);

        println("Reset thanh cong");
        interactor.pause();
    }

    public void mainMenu() {
        println("=== MENU ===");
        println("1. Tim kiem theo tu khoa");
        println("2. Tim kiem theo nghia");
        println("3. Xem lich su tim kiem");
        println("4. Them slang");
        println("5. Xua slang");
        println("6. Xoa slang");
        println("7. Reset danh sach slang word");
        println("8. Ngau nhien mot slang word");
        println("9. Do vui tu khoa");
        println("10. Do vui nghia");
        println("11. Thoat");

        int cmd = interactor.getInt("Nhap lenh: ", 11);

        switch (cmd) {
            case 1 -> findByDefinition();
            case 2 -> findByMeaning();
            case 3 -> history();
            case 7 -> reset();
            case 11 -> isRunning = false;
            default -> unimplemented();
        }
    }

}