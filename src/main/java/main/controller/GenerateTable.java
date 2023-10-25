package main.controller;

import javafx.collections.FXCollections;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class GenerateTable<T> {

    private TableView<T> table;
    private String tableStyle = ("-fx-selection-bar: red;" +
                                 "-fx-selection-bar-non-focused: salmon;");

    private String columnStyle = ("-fx-alignment: CENTER; " +
            "-fx-background-color: rgba(255, 255, 255, 0.5);" +
            "-fx-border-color: gray;");
    public GenerateTable(ArrayList<T> list){
        table = new TableView<T>();
        table.setStyle(tableStyle);
        for (Field f: list.get(0).getClass().getDeclaredFields()){
            if (f.getName().equals("logo")) {
                TableColumn<T, ImageView> column = new TableColumn<>(f.getName());
                column.setStyle(columnStyle);

                column.setCellValueFactory(new PropertyValueFactory<T, ImageView>(f.getName()));
                table.getColumns().add(column);
            } else {
                TableColumn<T, String> column = new TableColumn<>(f.getName());
                column.setStyle(columnStyle);
                column.setCellValueFactory(new PropertyValueFactory<T, String>(f.getName()));
                table.getColumns().add(column);
            }
        }

        table.getItems().addAll(FXCollections.observableArrayList(list));
    }

    public TableView<T> generateTable() {
        return table;
    }

    public GenerateTable<T> setLayoutX(double num){
        table.setLayoutX(num);
        return this;
    }
    public GenerateTable<T> setLayoutY(double num){
        table.setLayoutY(num);
        return this;
    }
    public GenerateTable<T> setPrefHeight(double num){
        table.setPrefHeight(num);
        return this;
    }
    public GenerateTable<T> setPrefWidth(double num){
        table.setPrefWidth(num);
        return this;
    }

    public TableColumn<T, ?> getColumn(String nameCol){
        for (TableColumn<T, ?> column: table.getColumns()){
            if (column.getText().equals(nameCol)){
                return column;
            }
        }
        return null;
    }
    public void addColumnImage(){
        TableColumn<T, ImageView> column = new TableColumn<>("logo");
        column.setCellValueFactory(new PropertyValueFactory<T, ImageView>("logo"));
        table.getColumns().add(column);

    }
    public void delColumn(TableColumn<T, ?> column){
        table.getColumns().remove(column);
    }

    public void delColumn(String nameCol){
        table.getColumns().remove(getColumn(nameCol));
    }
}
