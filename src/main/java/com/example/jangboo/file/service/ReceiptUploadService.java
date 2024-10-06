package com.example.jangboo.file.service;

import com.example.jangboo.auth.controller.dto.Info.CurrentUserInfo;
import com.example.jangboo.file.controller.dto.response.FileUploadResponse;
import com.example.jangboo.file.controller.dto.response.FileUploadResult;
import com.example.jangboo.file.domain.File;
import com.example.jangboo.file.domain.FileRepository;
import com.example.jangboo.file.domain.FileStatus;
import com.example.jangboo.file.domain.FileType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReceiptUploadService {
    private final S3Service s3Service;
    private final FileRepository fileRepository;

    public ReceiptUploadService(FileRepository fileRepository, S3Service s3Service) {
        this.fileRepository = fileRepository;
        this.s3Service = s3Service;
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
                // s3에 파일 업로드
                String fileUrl = s3Service.uploadFile(file,"receipt");

                // 파일정보 저장
                fileRepository.save(new File(userInfo.deptId(),fileUrl,FileType.RECEIPT, FileStatus.UPLOADED));

                // 업로드 결과 추가
                fileUploadResults.add(new FileUploadResult(FileType.RECEIPT, fileName,true, fileUrl));

            } catch (Exception e){
                fileUploadResults.add(new FileUploadResult(FileType.RECEIPT, fileName,false, null));

            }
        }

        // 성공 및 실패 횟수 계산
        int successCount = (int) fileUploadResults.stream().filter(FileUploadResult::success).count();
        int failedCount = fileUploadResults.size() - successCount;

        return new FileUploadResponse(successCount, failedCount, fileUploadResults);
    }


    private void validateFile(MultipartFile file, FileType fileType) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        if (fileType == FileType.RECEIPT) {
            // 파일 확장자 확인 (예: 이미지 파일만 허용)
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null && !originalFilename.matches(".*\\.(jpg|jpeg|png|pdf)$")) {
                throw new IllegalArgumentException("Invalid file type. Only JPG, JPEG, PNG, and PDF are allowed.");
            }
        }
    }
}
