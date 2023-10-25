package main.controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TableImage {
    private ImageView image;

    public TableImage(Image img) {
        this.image = new ImageView(img);
        image.setFitHeight(50);
        image.setFitWidth(50);
    }

    public void setImage(ImageView value) {
        image = value;
    }

    public ImageView getImage() {
        return image;
    }
}

