package com.slangmaster;

import javax.management.MBeanRegistration;

public class UI {

    final private Interactor interactor = new Interactor();
    private boolean isRunning = true;

    private static void print(String message) {
        System.out.print(message);
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
            case 11:
                isRunning = false;
                break;
            default:
                unimplemented();
                break;
        }
    }

}