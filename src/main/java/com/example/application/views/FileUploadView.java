package com.example.application.views;

import com.example.application.data.model.ImageEntity;
import com.example.application.data.repository.ImageEntityRepository;
import com.example.application.data.service.FileService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.UUID;

@PermitAll
@PageTitle("File Upload")
@Route(value = "File Upload", layout = MainLayout.class)
public class FileUploadView extends VerticalLayout {
    @Autowired
    private final ImageEntityRepository imageEntityRepository;
    @Autowired
    private final FileService fileService;

    private String fileName;
    private String uploadDirectory;
    private LocalDateTime uploadDate;
    Button saveButton = new Button("Save");


    public FileUploadView(ImageEntityRepository imageEntityRepository,
                          FileService fileService) {
        this.imageEntityRepository = imageEntityRepository;
        this.fileService = fileService;

        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();

        Upload upload = new Upload(buffer);
        upload.setDropAllowed(true);
        upload.setAcceptedFileTypes("image/jpeg", "image/png");
        upload.setMaxFiles(1);


        Span dropEnabledLabel = new Span("File Upload");
        upload.getStyle().set("font-weight", "600");
        upload.setId("upload-drop-enabled");

        saveButton.addClickListener(e -> saveFile(buffer));

        Div dropEnabledSection = new Div(dropEnabledLabel, upload);

        VerticalLayout layout = new VerticalLayout(dropEnabledSection, saveButton);

        add(layout);
    }

//    private void showImage(ImageEntity imageEntity) {
//        Image image = new Image(new StreamResource("image.jpg",
//                () -> new ByteArrayInputStream(imageEntity.getImageData())), "Uploaded Image");
//        image.getStyle().set("max-width", "300px");
//        add(image);
//    }
    private void saveFile(MultiFileMemoryBuffer buffer){
        if(!buffer.getFiles().isEmpty()){
            try{
                String fileName = buffer.getFiles().iterator().next();
                Path filePath = saveFileToPath(buffer.getInputStream(fileName).readAllBytes(), fileName);
                String filePathString = filePath.toString();
                saveToDatabase(filePathString, fileName);
                Notification.show("File saved successfully");

//                ImageEntity imageEntity = new ImageEntity();
//                try {
//                    imageEntity.setImageData(buffer.getInputStream(fileName).readAllBytes());
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                showImage(imageEntity);

            }catch(IOException e){
                Notification.show("Error saving file: " + e.getMessage());
            }
        }else{
            Notification.show("Файл оруулаагүй байна.");
        }
    }
    private Path saveFileToPath(byte[] content, String fileName) throws IOException{
        String uploadDirectory = "/Users/batzaya/Documents/Hicheel/Java/VaadinTutorial/src/main/resources/META-INF/resources/images/";
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        Path targetPath = Path.of(uploadDirectory, uniqueFileName);
        Files.write(targetPath, content);
        return targetPath;
    }
    private void saveToDatabase(String filePath, String fileName){
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setUploadDirectory(filePath);
        imageEntity.setFileName(fileName);
        imageEntity.setUploadDate(LocalDateTime.now());
        fileService.saveFileToDatabase(imageEntity);
    }
}
