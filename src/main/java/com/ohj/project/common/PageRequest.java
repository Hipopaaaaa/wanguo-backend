package com.ohj.project.common;

import com.ohj.project.constant.CommonConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页请求
 *
 * @author ohj
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    @ApiModelProperty("页码")
    private long current = 1;

    /**
     * 页面大小
     */
    @ApiModelProperty("每页总数")
    private long pageSize = 10;

    /**
     * 排序字段
     */
    @ApiModelProperty("排序字段")
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    @ApiModelProperty("排序顺序")
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}
