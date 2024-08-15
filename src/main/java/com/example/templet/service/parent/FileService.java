package com.example.templet.service.parent;

import com.example.templet.constant.FileConstant;
import com.example.templet.template.file.HavePicture;
import com.example.templet.template.file.HavePictureService;
import com.example.templet.template.file.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FileService {
  private final HavePictureService havePictureService;
  private final S3Service s3Service;

  public String getDirectory(String entityId) {
    HavePicture havePicture = findById(entityId);
    return havePicture.getDirectory();
  }

  public String getKey(String entityId) {
    return getDirectory(entityId) + "/" + entityId;
  }

  public HavePicture findById(String uid) {
    return havePictureService.findById(uid);
  }

  public HavePicture uploadPicture(String uid, byte[] file) {
    String fileBucketKey = getKey(uid);
    s3Service.uploadObjectToS3Bucket(fileBucketKey, file);
    StartUploadFile(uid);
    return findById(uid);
  }

  public void StartUploadFile(String entityId) {
    HavePicture havePicture = findById(entityId);
    havePicture.setPictureIsImplemented(true);
    havePictureService.save(havePicture);
  }

  public String getUrl(String entityId) {
    String fileBucketKey = getKey(entityId);
    HavePicture havePicture = findById(entityId);
    String fileURL = null;
    if (havePicture.getPictureIsImplemented()) {
      fileURL =
          String.valueOf(s3Service.generatePresignedUrl(fileBucketKey, FileConstant.URL_DURATION));
    }
    return fileURL;
  }
  ;
}
