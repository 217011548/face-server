package com.coding.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.coding.s3.S3Properties;
import com.coding.utils.JsonUtil;
import com.coding.utils.MimeUtil;
import com.coding.utils.R;
import com.coding.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author felix
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UploadService {

    private final AmazonS3 s3;

    private final S3Properties s3Properties;

    public R<String> uploadFile(File file, String originalFilename) throws IOException {
//        File file = filePath.toFile();

//        String originalFilename = filePart.filename();
        String extend = StringUtil.getExtend(originalFilename);
        String name = StringUtil.getExtend(originalFilename) + "/" + UUID.randomUUID() + "." + extend;
        String bucketName = s3Properties.getBucket();
        log.info("upload:{}", originalFilename);
        // check s3 is exist
        boolean isExist = s3.doesBucketExistV2(bucketName);
        if (isExist) {
            log.info("Bucket already exists.");
        } else {
            // create bucket for save file
            s3.createBucket(bucketName);
        }
        ArrayList<String> images = Lists.newArrayList("png", "jpg", "jpeg", "JPG");
        if (images.contains(extend)) {
            return uploadImage(file, bucketName, name);
        }

        return uploadCommon(file, bucketName, name);
    }

    public R<String> uploadFile(FilePart filePart) throws IOException {
        log.info(filePart.filename());
        Path filePath = Files.createTempFile("test", filePart.filename());

        AsynchronousFileChannel channel = AsynchronousFileChannel.open(filePath, StandardOpenOption.WRITE);
        AtomicBoolean writeFinish = new AtomicBoolean(false);
        DataBufferUtils.write(filePart.content(), channel, 0).doOnComplete(() -> {
            writeFinish.set(true);
            log.info("finish");
        }).subscribe();
        int waitCount = 0;
        while (!writeFinish.get() && waitCount < 20) {
            try {
                waitCount++;
                Thread.sleep(100);
            } catch (InterruptedException ignore) {
            }
        }
        log.info("upload finish");

        return uploadFile(filePath.toFile(), filePart.filename());
    }

    private R<String> uploadCommon(File file, String bucketName, String name) {
        PutObjectResult putObjectResult = s3.putObject(bucketName, name, file);
        log.info("result:{}", JsonUtil.toJson(putObjectResult));
        return R.createBySuccess(s3Properties.getAccessUrl() + "/" + bucketName + "/" + name);
    }

    @SneakyThrows
    private R<String> uploadImage(File file, String bucketName, String name) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, name, file);  
        ObjectMetadata metadata = new ObjectMetadata();
        String mimetype = MimeUtil.getMimetype(name);
        metadata.setContentType(mimetype);
        putObjectRequest.setMetadata(metadata);
        PutObjectResult putObjectResult = s3.putObject(putObjectRequest);
        log.info("result:{}", JsonUtil.toJson(putObjectResult));
        return R.createBySuccess(s3Properties.getAccessUrl() + "/" + bucketName + "/" + name);
    }
}
