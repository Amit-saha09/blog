package com.example.blog.services;


import com.example.blog.exceptions.ServiceExceptionHolder;
import com.example.blog.helper.CommonFunctions;
import com.example.blog.helper.Response;
import com.example.blog.helper.messages.CommonMessageConstants;
import com.example.blog.model.AuditModel;
import com.example.blog.model.Faq;
import com.example.blog.payload.BooleanValueHolderDTO;
import com.example.blog.payload.IIdHolderRequestBodyDTO;
import com.example.blog.repositories.ServiceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;


import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class is to provide base service
 *
 * @author Amit Saha
 * @since 25th september 2024
 */
public abstract class BaseService<E extends AuditModel, I extends IIdHolderRequestBodyDTO, O>
        implements CommonFunctions {

    @PersistenceContext
    private EntityManager entityManager;

    private ModelMapper modelMapper;
    Logger logger = LoggerFactory.getLogger(BaseService.class);



    // Replace getInstance() calls with getRepository()
    protected abstract ServiceRepository<E> getServiceRepo();

    // Remove protected abstract ServiceRepository<E> getInstance();

    private static final int DEFAULT_PAGE_SIZE = 1000000;


    /**
     * This Service will return pageable list data from db.
     * @author Amit Saha
     * @since 25th september
     */
    public ResponseEntity<Response<O>> getList(Pageable pageable) {

        Response<O> response = new Response();

        Page<E> ePage = null;

        try {
            ePage = getServiceRepo().findAllByIsDeleted(false, pageable);

            if (!CollectionUtils.isEmpty(ePage.getContent())) {
                response.setPage(
                        new PageImpl<>(convertForRead(ePage.getContent()), pageable, ePage.getTotalElements()));

                return new ResponseEntity<>(getSuccessResponse(CommonMessageConstants.DATA_FOUND_EN, response), HttpStatus.OK);
            }
            return new ResponseEntity<>(getSuccessResponse(CommonMessageConstants.NO_DATA_FOUND_EN), HttpStatus.OK);

        } catch (Exception e) {
            logger.error(CommonMessageConstants.SOMETHING_WRONG_EN, e);
            response.setMessage(CommonMessageConstants.SOMETHING_WRONG_EN);
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This Service will return data from db by id.
     * @author Amit Saha
     * @since 25th september
     */
    public ResponseEntity<Response<O>> getById(@NonNull Long id) {
        Response<O> response = new Response();
        O obj = null;

        try {
            Optional<E> e = getServiceRepo().findByIdAndIsDeleted(id, false);
            if (e.isPresent()) {
                obj = convertForRead(e.get());
                response.setObj(obj);
                return new ResponseEntity<>(getSuccessResponse(CommonMessageConstants.DATA_FOUND_EN, response), HttpStatus.OK);
            }
            return new ResponseEntity<>(getErrorResponse(CommonMessageConstants.NO_DATA_FOUND_EN), HttpStatus.OK);

        } catch (NoResultException e) {
            logger.error(CommonMessageConstants.SOMETHING_WRONG_EN, e);
            response.setMessage(CommonMessageConstants.SOMETHING_WRONG_EN);
            response.setErrorMessage(e.getMessage());
            response.setSuccess(false);
            return new ResponseEntity<>(getErrorResponse(CommonMessageConstants.SOMETHING_WRONG_EN), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Exception occurred! ", e);
            response.setMessage("Exception occurred! ");
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This Service will return data from db by id set.
     * @author Amit Saha
     * @since 25th september
     */
    public ResponseEntity<Response> getByIds(Set<Long> ids) {

        Response<O> response = new Response();
        List<E> list = null;
        try {
            list = getServiceRepo().findAllByIdInAndIsDeleted(ids, false);

            if (list.size() > 0) {
                response.setItems(convertForRead(list));
                return new ResponseEntity<>(getSuccessResponse(CommonMessageConstants.DATA_FOUND_EN, response), HttpStatus.OK);
            }
            logger.info(CommonMessageConstants.DATA_FOUND_EN);
            return new ResponseEntity<>(getSuccessResponse(CommonMessageConstants.NO_DATA_FOUND_EN), HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Failed to fetch data! ", e);
            response.setMessage("Failed to fetch data! ");
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This Service will create data .
     * @author Amit Saha
     * @since 25th september
     */
    public ResponseEntity<Response<O>> create(I i) {
        Response<O> response = new Response();
        try {
            response.setObj(convertForRead(createEntity(i)));
            return new ResponseEntity<>(getSuccessResponse(CommonMessageConstants.SAVED_EN, response), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(CommonMessageConstants.SOMETHING_WRONG_EN, e);
            response.setMessage(CommonMessageConstants.SOMETHING_WRONG_EN);
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
            System.out.println(e.getStackTrace());
            return new ResponseEntity<>(getErrorResponse(CommonMessageConstants.SOMETHING_WRONG_EN), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This Service will update data by id .
     * @author Amit Saha
     * @since 25th september
     */
    public ResponseEntity<Response<O>> update(I i) {
        Response<O> response = new Response();
        try {
            response.setObj(convertForRead(updateEntity(i)));
            return new ResponseEntity<>(getSuccessResponse(CommonMessageConstants.UPDATED_EN, response), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(CommonMessageConstants.NOT_UPDATED_EN, e);
            response.setMessage(CommonMessageConstants.NOT_UPDATED_EN);
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This Service will delete data by id .
     * @author Amit Saha
     * @since 25th september
     */
    public BooleanValueHolderDTO delete(@NonNull Long id) {
        deleteEntity(id);
        BooleanValueHolderDTO booleanValueHolderDTO = new BooleanValueHolderDTO();
        try{
            booleanValueHolderDTO.setSuccess(true);
            booleanValueHolderDTO.setMessage(CommonMessageConstants.DELETED_EN);
            return booleanValueHolderDTO;
        } catch (Exception exception){
            booleanValueHolderDTO.setSuccess(false);
            booleanValueHolderDTO.setMessage(CommonMessageConstants.NOT_DELETED_EN);
            return booleanValueHolderDTO;
        }
    }
    //safe delete


    /**
     * This Service will create entity .
     * @author Amit Saha
     * @since 25th september
     */
    protected E createEntity(I body) {
        try{
            E e = convertForCreate(body);
            e.setIsDeleted(false);
            return getServiceRepo().save(e);
        } catch(Exception ex){
            logger.error(ex.getMessage());
            System.out.println(ex.getMessage());
            return null;
        }

    }

    /**
     * This Service will update entity .
     * @author Amit Saha
     * @since 25th september
     */
    protected E updateEntity(I body) {
        try{
            Long id = body.getId();
            if (id == null)
                throw new ServiceExceptionHolder.ResourceNotFoundDuringWriteRequestException(
                        "No Id Provided for " + getEntityClass().getSimpleName());
            E e = getByIdForRead(id);
            body.setId(e.getId());
            convertForUpdate(body, e);
            e.setIsDeleted(false);
            return getServiceRepo().save(e);
        } catch (Exception ex){
            logger.error(ex.getMessage());
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * This Service will delete entity.
     * @author Amit Saha
     * @since 25th september
     */
    protected void deleteEntity(@NonNull Long id) {
        try{
            if (id == null)
                throw new ServiceExceptionHolder.ResourceNotFoundDuringWriteRequestException(
                        "No Id Provided for " + getEntityClass().getSimpleName());
            E e = getByIdForRead(id);
            e.setIsDeleted(true);
            getServiceRepo().save(e);
        } catch(Exception ex){
            logger.error(ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }

    /**
     * This Service will get by id for read .
     * @author Amit Saha
     * @since 25th september
     */
    public E getByIdForRead(@NonNull Long id) {
        return getServiceRepo().findByIdAndIsDeleted(id, false).orElse(null);
    }

    /**
     * This Service will convert entity to dto.
     * @author Amit Saha
     * @since 25th september
     */
    protected O convertForRead(E e) {
        modelMapper = new ModelMapper();
        return modelMapper.map(e, getResponseDtoClass());
    }

    /**
     * This Service will convert list of entity to dto.
     * @author Amit Saha
     * @since 25th september
     */
    protected List<O> convertForRead(List<E> e) {
        return e.stream().map(this::convertForRead).collect(Collectors.toList());
    }

    /**
     * This Service will convert entity to dto when create.
     * @author Amit Saha
     * @since 25th september
     */
    protected E convertForCreate(I i) {
        modelMapper = new ModelMapper();
        return modelMapper.map(i, getEntityClass());
    }

    /**
     * This Service will convert entity to dto when update.
     * @author Amit Saha
     * @since 25th september
     */
    protected void convertForUpdate(I i, E e) {
        BeanUtils.copyProperties(i, e);
    }

    /**
     * This Service will get entity class.
     * @author Amit Saha
     * @since 25th september
     */
    @SuppressWarnings("unchecked")
    private Class<E> getEntityClass() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * This Service will get dto class.
     * @author Amit Saha
     * @since 25th september
     */
    @SuppressWarnings("unchecked")
    private Class<I> getDtoClass() {
        return (Class<I>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    /**
     * This Service will get response dto class.
     * @author Amit Saha
     * @since 25th september
     */
    @SuppressWarnings("unchecked")
    private Class<O> getResponseDtoClass() {
        return (Class<O>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
    }

}
