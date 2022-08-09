package com.coding.controller.api;

import com.coding.config.Const;
import com.coding.domain.LinkDO;
import com.coding.service.LinkService;
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
 * @author felix
 */
@Slf4j
@RequestMapping(Const.API + "upload")
@RestController
@RequiredArgsConstructor
public class ApiUploadController {


    private final UploadService uploadService;
    private final LinkService linkService;


    @ApiOperation("file upload controller")
    @PostMapping(value = "files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<String> requestBodyFlux(@RequestPart("file") FilePart filePart) throws IOException {
        R<String> upload = uploadService.uploadFile(filePart);
        /*If upload success，save the link to DB*/
        if (upload.isSuccess()) {
            LinkDO param = new LinkDO();
            param.setUrl(upload.getData());
            param.setRemark("error");
            linkService.save(param);
        }
        return upload;
    }


}
