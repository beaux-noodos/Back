package com.example.templet.template.file;

import org.springframework.stereotype.Service;

@Service
public interface HavePictureService {
  HavePicture findById(String id);

  HavePicture save(HavePicture havePicture);
}
