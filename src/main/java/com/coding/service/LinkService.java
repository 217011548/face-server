package com.coding.service;

import com.coding.domain.LinkDO;
import com.coding.mapper.LinkMapper;
import com.coding.pojo.param.SearchRequestPageParam;
import com.coding.utils.PageResult;
import com.coding.utils.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class LinkService {

    private final LinkMapper linkMapper;

    public PageResult<LinkDO> page(SearchRequestPageParam page, Long userId) {
        LinkDO banner = new LinkDO();
        PageHelper.startPage(page.getPage(), page.getSize(), "id desc");
        List<LinkDO> list = linkMapper.select(banner);
        PageInfo<LinkDO> pageInfo = new PageInfo<>(list);
        return PageResult.success(pageInfo.getTotal(), pageInfo.getList());
    }

    public R<String> save(LinkDO param) {
        int count;
        if (param.getId() != null) {
            count = linkMapper.updateByPrimaryKeySelective(param);
        } else {
            count = linkMapper.insertSelective(param);
        }
        return R.createBySimple(count > 0, "Save Successfully", "Save Failure");
    }

    public R<String> delete(Long userId, Long id) {
        linkMapper.deleteByPrimaryKey(id);
        return R.createBySuccess();
    }
}
