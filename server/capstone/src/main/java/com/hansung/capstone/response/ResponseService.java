package com.hansung.capstone.response;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    public<T> SingleResponse<T> getSuccessSingleResponse(T data){
        SingleResponse singleResponse = new SingleResponse();
        singleResponse.data = data;
        setSuccessResponse(singleResponse);

        return singleResponse;
    }

    public<T> SingleResponse<T> getFailureSingleResponse(T data){
        SingleResponse singleResponse = new SingleResponse();
        singleResponse.data = data;
        setFailResponse(singleResponse);

        return singleResponse;
    }

    public<T> ListResponse<T> getListResponse(List<T> data){
        ListResponse listResponse = new ListResponse();
        listResponse.data = data;
        setSuccessResponse(listResponse);

        return listResponse;
    }

    void setSuccessResponse(CommonResponse res){
        res.code = 100;
        res.success = true;
        res.message = "SUCCESS";
    }

    void setFailResponse(CommonResponse res){
        res.code = 900;
        res.success = false;
        res.message = "FAILURE";
    }
}
