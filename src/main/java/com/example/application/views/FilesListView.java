package com.example.application.views;

import com.example.application.data.model.ImageEntity;
import com.example.application.data.service.FileService;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.gridpro.ItemUpdater;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import jakarta.annotation.security.PermitAll;

import java.util.List;

@PermitAll
@PageTitle("Files List View")
@Route(value = "Files List View", layout = MainLayout.class)
public class FilesListView extends Div {
    /**
     * 
     */
    private FileService fileService;

    public FilesListView(FileService fileService) {
        this.fileService = fileService;
        Grid<ImageEntity> grid = new Grid<>();

        grid.setSelectionMode(SelectionMode.MULTI);
        grid.addColumn(ImageEntity::getFileName).setHeader("File name");
        grid.addColumn(ImageEntity::getUploadDate).setHeader("Upload date");

        List<ImageEntity> files = fileService.LoggedUserFiles();
        System.out.printf("Logged user files: ", files.isEmpty());
        if (!files.isEmpty()) {
            grid.setItems(files);
            grid.addSelectionListener(selection -> {
                System.out.printf("Selected files count: %d%n", selection.getAllSelectedItems().size());
            });
            add(grid);
        } else {
            Text notificationText = new Text("Хадгалсан файл байхгүй байна.");
            add(notification(notificationText));
        }

    }

    public Notification notification(Text text) {
        Notification notification = Notification.show(text.getText());
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.setDuration(0);
        return notification;
    }

}
