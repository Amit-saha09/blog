package com.example.blog.controllers;


import com.example.blog.constatnts.BaseConstant;
import com.example.blog.helper.Response;
import com.example.blog.model.AuditModel;
import com.example.blog.payload.BooleanValueHolderDTO;
import com.example.blog.payload.IIdHolderRequestBodyDTO;
import com.example.blog.payload.IdSetRequestBodyDTO;
import com.example.blog.services.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This controller is to provide all the base type relevant api's
 *
 * @author Amit Saha
 * @since 25th september
 */

public abstract class BaseController<E extends AuditModel, I extends IIdHolderRequestBodyDTO, O> {

    protected abstract BaseService<E, I, O> getService();


    /**
     * This API will get pageable list.
     * @author Amit Saha
     * @since 25th september
     */

    @Operation(summary = "Get List", description = "Get pageable list from DB")
    @GetMapping(path = BaseConstant.GET_LIST, produces = "application/json")
    public @ResponseBody ResponseEntity<Response<O>> getList(Pageable pageable) {
        return getService().getList(pageable);
    }



    /**
     * This API will return get by id data.
     * @author Amit Saha
     * @since 25th september
     */
    @Operation(summary = "Get By ID", description = "Get by id data from DB")
    @GetMapping(path = BaseConstant.GET_BY_ID + "/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<Response<O>> getById(@PathVariable Long id) {
        return getService().getById(id);
    }

    /**
     * This API will return get by id set data.
     * @author Amit Saha
     * @since 25th september
     */
    @Operation(summary = "Get By ID Set", description = "Get by id set data from DB")
    @PostMapping(path = BaseConstant.GET_BY_ID_SET, produces = "application/json")
    public @ResponseBody ResponseEntity<Response> getByIdSet(@RequestBody IdSetRequestBodyDTO requestBodyDTO) {
        return getService().getByIds(requestBodyDTO.getIds());
    }


    /**
     * This API will delete data by id.
     * @author Amit Saha
     * @since 25th september
     */
    @Operation(summary = "Delete Data", description = "Delete data by id from DB")
    @DeleteMapping(path = BaseConstant.DELETE + "/{id}", produces = "application/json")
    public @ResponseBody BooleanValueHolderDTO delete(@PathVariable Long id) {
        return getService().delete(id);
    }
}
