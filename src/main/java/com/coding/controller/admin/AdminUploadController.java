package com.coding.controller.admin;

import com.coding.config.Const;
import com.coding.service.UploadService;
import com.coding.utils.R;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author mac
 */
@Slf4j
@RequestMapping(Const.ADMIN + "upload")
@RestController
@RequiredArgsConstructor
public class AdminUploadController {


    private final UploadService uploadService;


    @ApiOperation("file upload")
    @PostMapping(value = "files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<String> requestBodyFlux(@RequestPart("file") FilePart filePart) throws IOException {
        return uploadService.uploadFile(filePart);
    }


}
