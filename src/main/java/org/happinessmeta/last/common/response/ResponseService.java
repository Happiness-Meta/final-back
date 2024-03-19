package org.happinessmeta.last.common.response;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    public <T> SingleResult<T> handleSingleResult(T data, int status) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        result.setCode(status);
        setSuccessResult(result);
        return result;
    }

    public <T> MultipleResult<T> handleListResult(List<T> list) {
        MultipleResult<T> result = new MultipleResult<>();
        result.setData(list);
        setSuccessResult(result);
        return result;
    }

    public <T> MultipleResult<T> handleListResult(List<T> list, int status) {
        MultipleResult<T> result = new MultipleResult<>();
        result.setData(list);
        result.setCode(status);
        setSuccessResult(result);
        return result;
    }

    public Result handleSuccessResult(int code) {
        Result result = new Result();
        setSuccessResult(result);
        result.setCode(code);
        return result;
    }

    public Result handleFailResult(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }

    public Result handleFailResultWithReason(int code, List<String> reasons) {
        FailResultWithReason result = new FailResultWithReason();
        result.setCode(code);
        result.setErrors(reasons);
        return result;
    }
    private <T> void setSuccessResult(Result result) {
        result.setSuccess(true);
        result.setMessage("success");
    }
}
