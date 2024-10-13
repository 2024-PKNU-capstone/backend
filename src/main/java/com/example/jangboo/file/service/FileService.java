package com.example.jangboo.file.service;

import com.example.jangboo.file.domain.File;
import com.example.jangboo.file.domain.FileRepository;
import com.example.jangboo.file.domain.FileStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.Optional;

@Service
@Slf4j
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Transactional
    public void updateFileStatus(Long fileId, FileStatus status) {
        File file = fileRepository.findById(fileId).orElseThrow();
        file.updateStatus(status);
    }
}
