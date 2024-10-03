package com.example.blog.helper;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * This class is to provide Response
 * @author Amit Saha
 * @since 25th september
 */
@Data
public class Response<R> {
    private boolean success = true;
    private boolean info = false;
    private boolean warning = false;
    private String message;
    private String errorMessage;
    private boolean valid = false;

    //private Long id;
    private Map<String, R> model;
    private List<R> items;
    private R obj;
    private Page page;
}
