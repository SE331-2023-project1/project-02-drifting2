package com.drifting2.projectbackend.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import jakarta.servlet.ServletException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Component
public class CloudStorageHelper {

    private static Storage storage = null;
    static {
        InputStream serviceAccount = null;
        try {
            serviceAccount = new ClassPathResource("uploader-cf487-b2d51e09e1e3.json").getInputStream();
            storage = StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId("uploader-cf487")
                    .build().getService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StorageFileDto getStorageFileDto(MultipartFile file, final String bucket) throws IOException, ServletException {
        if (file == null || file.isEmpty()) {
            throw new ServletException("No file provided");
        }

        final String fileName = file.getOriginalFilename();
        if (fileName != null && !fileName.isEmpty()) {
            String urlName = this.uploadFile(file, bucket);
            return StorageFileDto.builder()
                    .name(urlName)
                    .build();
        }
        throw new ServletException("Invalid file name");
    }

    public StorageFileDto uploadSingleImage(MultipartFile file, final String bucket) throws IOException, ServletException {
        return getStorageFileDto(file, bucket); // Use the same logic for uploading any file, not just images
    }

    public String uploadFile(MultipartFile filePart, final String bucketName) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HHmmssSSS");
        String dtString = sdf.format(new Date());
        final String fileName = dtString + "-" + filePart.getOriginalFilename();
        InputStream is = filePart.getInputStream();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] readBuf = new byte[4096];
        while (is.available() > 0) {
            int bytesRead = is.read(readBuf);
            os.write(readBuf, 0, bytesRead);
        }

        BlobInfo blobInfo = storage.create(
                BlobInfo
                        .newBuilder(bucketName, fileName)
                        .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                        .setContentType(filePart.getContentType())
                        .build(),
                os.toByteArray());

        return blobInfo.getMediaLink();
    }

    public String getImageUrl(MultipartFile file, final String bucket) throws IOException, ServletException {
        return getStorageFileDto(file, bucket).getName();  // Reuse the logic and get the URL
    }
}
