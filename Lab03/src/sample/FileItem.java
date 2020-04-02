package sample;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileItem {

    private String shortDescription;
    private String details;
    private String path;
    private Image tripImage;
    private File loadedFile;

    public FileItem(String shortDescription, String details) {
        this.shortDescription = shortDescription;
        this.details = details;
        tripImage = null;
        loadedFile = null;
    }

    public FileItem(String shortDescription, String details, String imageURL) {
        this.shortDescription = shortDescription;
        this.details = details;

        tripImage = null;

        try {
            loadedFile = new File(imageURL);
            tripImage = new Image(new FileInputStream(loadedFile));
        } catch (FileNotFoundException e) {
            loadedFile = null;
            e.printStackTrace();
        }
    }

    public Image getTripImage() {
        return tripImage;
    }

    public void setTripImage(Image tripImage) {
        this.tripImage = tripImage;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        loadedFile = new File(path);
    }

    public File getLoadedFile() {
        return loadedFile;
    }

    public void setLoadedFile(File loadedFile) {
        this.loadedFile = loadedFile;
    }

    @Override
    public String toString() {
        return shortDescription;
    }

}