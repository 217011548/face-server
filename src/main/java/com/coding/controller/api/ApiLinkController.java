package com.coding.controller.api;

import com.coding.config.Const;
import com.coding.config.ReactiveRequestContextHolder;
import com.coding.domain.LinkDO;
import com.coding.pojo.param.DeleteParam;
import com.coding.pojo.param.SearchRequestPageParam;
import com.coding.service.LinkService;
import com.coding.utils.PageResult;
import com.coding.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


/**
 * @author felix
 */
@Slf4j
@Api(tags = "API controller")
@RequestMapping(Const.API + "link")
@RequiredArgsConstructor
@RestController
public class ApiLinkController {

    private final LinkService linkService;




    @ApiOperation(value = "Save", notes = "Add item no need id，only update need add id")
    @PostMapping("save")
    public Mono<R<String>> save(@RequestBody @Validated LinkDO param) {
        return ReactiveRequestContextHolder.getUserId().map(userId -> linkService.save(param));
    }




}
