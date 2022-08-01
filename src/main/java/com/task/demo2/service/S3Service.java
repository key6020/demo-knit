package com.task.demo2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.asList;

@Slf4j
//@RequiredArgsConstructor
@Service
public class S3Service {

    //Amazon-s3-sdk
//    private AmazonS3 s3Client;

    private final String bucketName = "knit-document";

    private final List<String> extensionList = asList("jpg", "jpeg", "png", "gif");

//    private S3Service(@Value("${aws.access-key}") final String accessKey, @Value("${aws.secret-key}") final String secretKey) {
//        createS3Client(accessKey, secretKey);
//    }
//
//    //aws S3 client 생성
//    private void createS3Client(final String accessKey, final String secretKey) {
//        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
//        this.s3Client = AmazonS3ClientBuilder
//                .standard()
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .withRegion(Regions.AP_NORTHEAST_2)
//                .build();
//    }

//    public S3ImageResDto upload(MultipartFile multipartFile, String type) throws IOException {
//        String fileExtension = FilenameUtils.getExtension(multipartFile.getOriginalFilename()).toLowerCase();
//        log.info("fileExtension : {}", fileExtension);
//        log.info("file size in MB : {}", convertMultiPartToFile(multipartFile).length() / (1024 * 1024));
//        if (!validateFileExtension(fileExtension)) {
//            throw new IllegalArgumentException("Invalid File Extension.");
//        }
//        if (type.equals("thumbnailUrl")) {
//            return uploadThumbnail(new PutObjectRequest(bucketName, "thumbnail/" + generateFileName(type, fileExtension), convertMultiPartToFile(multipartFile)));
//        } else if (type.equals("thread")) {
//            return uploadThreadFile(new PutObjectRequest(bucketName, "thread/" + generateFileName(type, fileExtension), convertMultiPartToFile(multipartFile)));
//        } else if (type.equals("cover")) {
//            return uploadThreadFile(new PutObjectRequest(bucketName, "thread/cover/" + generateFileName(type, fileExtension), convertMultiPartToFile(multipartFile)));
//        } else {
//            return new S3ImageResDto();
//        }
//    }

//    private S3ImageResDto uploadThumbnail(PutObjectRequest putObjectRequest) {
//        try {
//            this.s3Client.putObject(putObjectRequest);
//            log.info("upload complete : {}", putObjectRequest.getKey());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        S3ImageResDto res = new S3ImageResDto();
//        s3Client
//                .setObjectAcl(
//                        bucketName,
//                        putObjectRequest.getKey(),
//                        CannedAccessControlList.PublicRead
//                );
//        res.setUrl(s3Client.getUrl(bucketName, putObjectRequest.getKey()).toExternalForm());
//
//        return res;
//    }

//    private S3ImageResDto uploadThreadFile(PutObjectRequest putObjectRequest) {
//        try {
//            this.s3Client.putObject(putObjectRequest);
//            log.info("upload complete : {}", putObjectRequest.getKey());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        S3ImageResDto res = new S3ImageResDto();
//        s3Client
//                .setObjectAcl(
//                        bucketName,
//                        putObjectRequest.getKey(),
//                        CannedAccessControlList.PublicRead
//                );
//        res.setUrl(s3Client.getUrl(bucketName, putObjectRequest.getKey()).toExternalForm());
//        return res;
//    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getName());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(String type, String extension) {
        return LocalDateTime.now() + "_" + type + "." + extension;
    }

    private boolean validateFileExtension(String extension) {
        return extensionList.contains(extension);
    }

}