package com.example.templet.service;

import com.example.templet.service.parent.FileService;
import com.example.templet.template.file.S3Service;
import org.springframework.stereotype.Service;

@Service
public class ProjectFileervice extends FileService {
  public ProjectFileervice(ProjectService projectService, S3Service s3Service) {
    super(projectService, s3Service);
  }
}
