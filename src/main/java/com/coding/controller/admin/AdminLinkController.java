package com.coding.controller.admin;

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
@Api(tags = "controller")
@RequestMapping(Const.ADMIN + "link")
@RequiredArgsConstructor
@RestController
public class AdminLinkController {

    private final LinkService linkService;


    @ApiOperation("page")
    @GetMapping("page")
    public Mono<PageResult<LinkDO>> page(@Validated SearchRequestPageParam page) {
        return ReactiveRequestContextHolder.getUserId().map(userId -> linkService.page(page, userId));
    }


    @ApiOperation(value = "save the LinkDo", notes = "add item no need id， only update need id")
    @PostMapping("save")
    public Mono<R<String>> save(@RequestBody @Validated LinkDO param) {
        return ReactiveRequestContextHolder.getUserId().map(userId -> linkService.save(param));
    }


    @ApiOperation("delete")
    @PostMapping("delete")
    public Mono<R<String>> delete(@RequestBody @Validated DeleteParam param) {
        return ReactiveRequestContextHolder.getUserId().map(userId -> linkService.delete(userId, param.getId()));
    }


}
