package com.github.liubt.mall.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 返回结果
 */
public class ResultDTO<T> {

    private static final long serialVersionUID = 1668914867578552488L;

    /**
     * 处理结果状态
     */
    protected Status status;

    /**
     * 对象数据
     */
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "data", index = 2)
    public T getData() {
        return this.data;
    }

    private void setData(final T data) {
        this.data = data;
    }

    public ResultDTO() {
    }

    ResultDTO(final Status status) {
        this.status = status;
    }

    public static <T> ResultDTO<T> success(final T data) {
        final ResultDTO<T> result = new ResultDTO<>(Status.SUCCESS);
        result.setData(data);
        return result;
    }

    public static <T> ResultDTO<T> fail(final T data) {
        final ResultDTO<T> result = new ResultDTO<>(Status.FAIL);
        return result;
    }

    public enum Status {
        SUCCESS,
        FAIL;
    }
}
