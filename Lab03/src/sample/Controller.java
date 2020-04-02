package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.List;

public class Controller {

    @FXML
    public TextField catalougeFieldTextArea;
    @FXML
    public Button initializeListButton;
    @FXML
    public Label loadLabel;
    @FXML
    public ImageView imageView;
    //private List<FileItem> todoItems = new ArrayList<FileItem>();
    private List<String> filePathsList;

    @FXML
    private ListView<String> todoListView;

    @FXML
    private TextArea itemDetailsTextArea;

    WeakHashMap<String, FileItem> map = new WeakHashMap<>();
    private static final boolean RECURSIVE = true; // listowanie katalogow z katalogu
    String dirPathname;
    File directory;
    private int count = 0;

    public void initialize() throws IOException {
        //FileItem item1 = new FileItem("Mail birthday card", "Buy a 30th birthday card for John");
        //todoItems.add(item1);

        System.gc(); // testowanie zachowania garbage collectora, usuwanie poprzednio wwczytanego katalogu przed wczytaniem kolejnego
        filePathsList = new ArrayList<String>();

        dirPathname = catalougeFieldTextArea.getText();
        directory = new File(dirPathname);

        if(directory.exists())
            initializeFiles(directory);

        //todoListView.getItems().setAll(todoItems);
        todoListView.getItems().setAll(filePathsList);
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    public void handleClickListView() throws IOException {
        //FileItem item = todoListView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText("");
        String key = todoListView.getSelectionModel().getSelectedItem();
        if(map.containsKey(key)){
            FileItem item = map.get(key);
            loadLabel.setText("Loaded from memory");
            StringBuilder sb = new StringBuilder(item.getDetails());
            itemDetailsTextArea.setText(sb.toString());

            if(item.getTripImage() != null){
                imageView.setImage(item.getTripImage());
            }

            // oblusga niewspieranego rozszerzenia
            if(item.getPath() != null && item.getLoadedFile().exists()){
                Desktop.getDesktop().open(item.getLoadedFile());
            }
            
        }
    }

    public void initializeFiles(File directory) throws IOException {

        File[] files = directory.listFiles();

        for (File file : files) {

            if(file.isFile()){

                try {

                    StringBuilder data = new StringBuilder();
                    String space = "";
                    for(int i = 0; i < count; i++)
                        space += "        ";

                    String filepath = file.getPath();

                    // obsluga pliku txt
                    if(filepath.contains(".txt")){
                        try {
                            List<String> allLines = Files.readAllLines(Paths.get(filepath));
                            for (String line : allLines) {
                                data.append(line + "\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        FileItem item1 = new FileItem(space + filepath, data.toString());
                        map.put(space + filepath, item1);
                        //todoItems.add(item1);
                        filePathsList.add(space + filepath);
                    }

                    // obsluga obrazkow
                    else if(filepath.contains(".jpg") || filepath.contains(".png") || filepath.contains(".gif")){
                        FileItem item1 = new FileItem(space + filepath, data.toString(), filepath);
                        map.put(space + filepath, item1);
                        filePathsList.add(space + filepath);
                    }

                    // niewspierane rozszerzenia - wyliczenie kontrolnej wartosci pliku, dodanie sciezki do uruchomienia pliku
                    else{
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        md.update(Files.readAllBytes(Paths.get(filepath)));
                        byte[] digest = md.digest();
                        data = new StringBuilder(Base64.getEncoder().encodeToString(digest));
                        FileItem item1 = new FileItem(space + filepath, data.toString());
                        item1.setPath(filepath); // sciezka do uruchomienia pliku
                        map.put(space + filepath, item1);
                        //todoItems.add(item1);
                        filePathsList.add(space + filepath);
                    }


                } catch (IOException | NoSuchAlgorithmException e) {
                    System.out.println("One or more files were deleted");
                }

            } else if(file.isDirectory() && RECURSIVE){
                String space = "";
                for(int i = 0; i < count; i++)
                    space += "        ";
                space += "*";
                FileItem item1 = new FileItem(space + file.getPath(), file.getPath());
                //todoItems.add(item1);
                map.put(space + file.getPath(), item1);
                filePathsList.add(space + file.getPath());
                count ++;
                initializeFiles(file);
                count--;
            }

        }
    }
}