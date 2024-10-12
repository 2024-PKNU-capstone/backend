package com.example.jangboo.file.service;

import com.example.jangboo.auth.controller.dto.Info.CurrentUserInfo;
import com.example.jangboo.file.controller.dto.response.FileUploadResponse;
import com.example.jangboo.file.controller.dto.response.FileUploadResult;
import com.example.jangboo.file.domain.File;
import com.example.jangboo.file.domain.FileRepository;
import com.example.jangboo.file.domain.FileStatus;
import com.example.jangboo.file.domain.FileType;
import com.example.jangboo.file.producer.FileProducer;
import com.example.jangboo.global.dto.ocr.OcrReq;
import com.example.jangboo.infra.aws.S3FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ReceiptUploadService {
    private final FileStorageService fileStorageService;
    private final FileRepository fileRepository;
    private final FileProducer fileProducer;

    public ReceiptUploadService(FileRepository fileRepository, FileStorageService fileStorageService, FileProducer fileProducer) {
        this.fileRepository = fileRepository;
        this.fileStorageService = fileStorageService;
        this.fileProducer = fileProducer;
    }

    @Transactional
    public FileUploadResponse uploadReceipt(List<MultipartFile> files, CurrentUserInfo userInfo, Optional<Long> documentId) {
        List<FileUploadResult> fileUploadResults = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            if (fileName == null) {
                fileName = "unknown";
            }
            try {
                validateFile(file, FileType.RECEIPT);
                String fileUrl = fileStorageService.uploadFile(file,"receipt");
                File savedFile = fileRepository.save(new File(fileName, userInfo.deptId(),fileUrl,FileType.RECEIPT, FileStatus.UPLOADED));
                Long fileId = savedFile.getId();
                fileProducer.sendMessageOcrStart(OcrReq.OcrStartReq.of(fileId, fileUrl));
                fileUploadResults.add(new FileUploadResult(FileType.RECEIPT, fileName,true, fileUrl));
            } catch (Exception e){
                fileUploadResults.add(new FileUploadResult(FileType.RECEIPT, fileName,false, null));
            }
        }
        int successCount = (int) fileUploadResults.stream().filter(FileUploadResult::success).count();
        int failedCount = fileUploadResults.size() - successCount;
        return new FileUploadResponse(successCount, failedCount, fileUploadResults);
    }


    private void validateFile(MultipartFile file, FileType fileType) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        if (fileType == FileType.RECEIPT) {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null && !originalFilename.matches(".*\\.(jpg|jpeg|png|pdf)$")) {
                throw new IllegalArgumentException("Invalid file type. Only JPG, JPEG, PNG, and PDF are allowed.");
            }
        }
    }
}
