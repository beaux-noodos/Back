package com.example.templet.service;

import com.example.templet.service.parent.FileService;
import com.example.templet.template.file.S3Service;
import org.springframework.stereotype.Service;

@Service
public class CourseFileervice extends FileService {
  public CourseFileervice(CourseService courseService, S3Service s3Service) {
    super(courseService, s3Service);
  }
}
