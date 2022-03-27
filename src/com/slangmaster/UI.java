package com.slangmaster;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.function.Function;

public class UI {

    final private String slangFilename;
    final private String definitionIndexFilename;
    final private String meaningsIndexFilename;

    final private Interactor interactor = new Interactor();
    final private Database database;
    final private Random random = new Random();
    private boolean isRunning = true;

    UI(String slangFilename, String definitionIndexFilename, String meaningIndexFilename) {
        this.slangFilename = slangFilename;
        this.definitionIndexFilename = definitionIndexFilename;
        this.meaningsIndexFilename = meaningIndexFilename;

        this.database = new Database(
                FileManager.readSlangList(slangFilename),
                FileManager.readIndex(definitionIndexFilename),
                FileManager.readIndex(meaningIndexFilename),
                null
        );
    }

    private static void println(String message) {
        System.out.println(message);
    }

    private void unimplemented() {
        println("Chuc nang nay chua duoc phat trien");
    }

    public void run() {
        while (isRunning) {
            mainMenu();
        }
    }

    private void findByDefinition() {
        String word = interactor.getString("Nhap tu khoa: ").trim();
        Slang slang = database.queryByDefinition(word.trim());
        println(slang != null ? slang.toString() : "Khong co trong tu dien!");
    }

    private void findByMeaning() {
        String word = interactor.getString("Nhap nghia: ").trim();
        Slang slang = database.queryByMeaning(word);
        println(slang != null ? slang.toString() : "Khong co trong tu dien!");
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
    }

    private void addSlang() {
        String definition = interactor.getString("Nhap slang: ");

        if (database.find(definition, (def, s) -> def.equals(s.word())) != null) {
            println("Slang da co trong tu dien!");
            return;
        }

        String meaning;
        ArrayList<String> meanings = new ArrayList<>();

        println("Nhap tung nghia cua slang. Nhan trong de bo qua");
        while (true) {
            meaning = interactor.getStringCanBeEmpty("Nhap nghia: ");
            if (meaning.isBlank()) {
                break;
            }
            meanings.add(meaning);
        }

        database.insertUnChecked(new Slang(definition, meanings));

        println("Them slang vao tu dien thanh cong");
    }

    private void removeSlang() {
        String definition = interactor.getString("Nhap slang ban muon xoa: ");

        if (database.remove(definition)) {
            println("Xoa slang thanh cong");
        } else {
            println("Xoa slang that bai");
        }
    }

    private void random() {
        println("Slang ngau nhien: " + database.randomSlang().toString());
    }

    private void reset() {
        ArrayList<Slang> slangList = FileManager.readSlangList(Constants.DEFAULT_SLANG_FILENAME);
        database.reset(slangList);

        println("Reset thanh cong");
    }

    private void definitionQuiz() {
        quiz("Doan nghia cho tu slang '%s': ", Slang::word, Slang::meaningsString);
    }

    private void meaningQuiz() {
        quiz("Doan slang cho tu co nghia '%s': ", Slang::meaningsString, Slang::word);
    }

    private void quiz(String question, Function<Slang, String> questionFunc, Function<Slang, String> answerFunc) {
        Slang[] slangs = {
                database.randomSlang(),
                database.randomSlang(),
                database.randomSlang(),
                database.randomSlang(),
        };
        int answer = random.nextInt(4);

        println(String.format(Locale.getDefault(), question, questionFunc.apply(slangs[answer])));
        for (int i = 0; i < slangs.length; ++i) {
            println(String.format("%d: %s", i + 1, answerFunc.apply(slangs[i])));
        }

        int cmd = interactor.getInt("Nhap dap an: ", 4);
        println("Ban da tra loi " + (cmd == answer + 1 ? "dung" : "sai") + "!");
    }

    private void quit() {
        isRunning = false;
        FileManager.writeSlangList(slangFilename, this.database.getSlangList());
        FileManager.writeIndex(definitionIndexFilename, this.database.getDefinitionIndex());
        FileManager.writeIndex(meaningsIndexFilename, this.database.getMeaningIndex());
    }

    private void mainMenu() {
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
            case 4 -> addSlang();
            case 6 -> removeSlang();
            case 7 -> reset();
            case 8 -> random();
            case 9 -> definitionQuiz();
            case 10 -> meaningQuiz();
            case 11 -> quit();
            default -> unimplemented();
        }

        if (isRunning) {
            interactor.pause();
        }
    }

}