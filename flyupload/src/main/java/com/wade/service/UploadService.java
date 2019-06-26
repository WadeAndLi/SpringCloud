package com.wade.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.wade.common.ExceptionEnum;
import com.wade.common.FlyException;
import com.wade.config.UploadProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;

@Service
@EnableConfigurationProperties(UploadProperties.class)
public class UploadService {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private UploadProperties prop;

    public String uploadImage(MultipartFile file) {

        try {
            if (!prop.getAllowTypes().contains(file.getContentType()) || ImageIO.read(file.getInputStream()) == null) {
                throw new FlyException(ExceptionEnum.FILE_TYPE_ERROR);
            }

            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            StorePath storePath = storageClient.uploadFile(
                    file.getInputStream(), file.getSize(), extension, null
            );
            return prop.getBaseUrl() + storePath.getFullPath();
        } catch (IOException e) {
            throw new FlyException(ExceptionEnum.UPLOAD_ERROR);
        }
    }
}
