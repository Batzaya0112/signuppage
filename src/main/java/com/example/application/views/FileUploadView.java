package com.example.application.views;

import com.example.application.data.model.ImageEntity;
import com.example.application.data.model.User;
import com.example.application.data.repository.UserRepository;
import com.example.application.data.service.FileService;
import com.example.application.security.AuthenticatedUser;
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
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@PermitAll
@PageTitle("File Upload")
@Route(value = "File Upload", layout = MainLayout.class)
public class FileUploadView extends VerticalLayout {
    @Autowired
    private final FileService fileService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticatedUser authenticatedUser;

    Button saveButton = new Button("Save");

    public FileUploadView(FileService fileService,
            UserRepository userRepository,
            AuthenticatedUser authenticatedUser) {

        this.authenticatedUser = authenticatedUser;
        this.userRepository = userRepository;
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

    private void saveFile(MultiFileMemoryBuffer buffer) {
        if (!buffer.getFiles().isEmpty()) {
            try {
                String fileName = buffer.getFiles().iterator().next();
                Path filePath = saveFileToPath(buffer.getInputStream(fileName).readAllBytes(), fileName);
                String filePathString = filePath.toString();
                saveToDatabase(filePathString, fileName);
                Notification.show("File saved successfully");
            } catch (IOException e) {
                Notification.show("Error saving file: " + e.getMessage());
            }
        } else {
            Notification.show("Файл оруулаагүй байна.");
        }
    }

    private Path saveFileToPath(byte[] content, String fileName) throws IOException {
        String uploadDirectory = "/Users/batzaya/Documents/Huviin/Hicheel/Java/signuppage/src/main/resources/META-INF/resources/images/";

        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        Path targetPath = Path.of(uploadDirectory, uniqueFileName);
        Files.write(targetPath, content);
        System.out.println("target path =======> " + targetPath.toString());
        return targetPath;
    }

    private void saveToDatabase(String filePath, String fileName) {
        Optional<UserDetails> u = authenticatedUser.getAutenticatedUser();
        System.out.println("Person username: " + u.get().getUsername());
        User userInfo = userRepository.findByUsername(u.get().getUsername());
        System.out.println("Person userInfo: " + u.get().getUsername());
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setUserId(userInfo.getId());
        imageEntity.setUploadDirectory(filePath);
        imageEntity.setFileName(fileName);
        imageEntity.setUploadDate(LocalDateTime.now());
        fileService.saveFileToDatabase(imageEntity);
    }
}
