package com.toskey.framework.core.base.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.toskey.framework.core.base.entity.DataTable;
import com.toskey.framework.core.exception.BusinessException;
import com.toskey.framework.core.exception.BusinessExceptionEnum;
import com.toskey.framework.core.util.HttpUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;

/**
 * BaseController
 * 提供了公共的基础方法
 *
 * @author toskey
 */
public class BaseController {

    protected static final String SUCCESS = "SUCCESS";
    protected static final String ERROR = "ERROR";

    protected static final String REDIRECT = "redirect:";
    protected static final String FORWARD = "forward:";

    /**
     * 返回前台文件流
     */
    protected ResponseEntity<byte[]> renderFile(String fileName, byte[] fileBytes) {
        String dfileName = null;
        try {
            dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        return new ResponseEntity<byte[]>(fileBytes, headers, HttpStatus.CREATED);
    }

    /**
     * 将page对象封装为datatables需要的数据格式
     * @param page
     * @param <T>
     * @return
     */
    protected <T> DataTable<T> queryPage(Page<T> page) {
        DataTable<T> dataTable = new DataTable<T>();
        dataTable.setData(page.getRecords());
        dataTable.setRecordsTotal(page.getTotal());
        dataTable.setLength(page.getSize());
        dataTable.setRecordsFiltered(page.getTotal());
        dataTable.setDraw(Integer.valueOf(HttpUtils.getRequest().getParameter("draw")));
        return dataTable;
    }

    /**
     * 检查上传的文件
     * @param file
     */
    protected void validateFile(MultipartFile file) {
        if(null == file) {
            throw new BusinessException(BusinessExceptionEnum.REQUEST_NULL);
        }

        String fileName = file.getOriginalFilename();

        long size = file.getSize();
        if(size == 0) {
            throw new BusinessException(BusinessExceptionEnum.FILE_READING_ERROR);
        }
    }

}
