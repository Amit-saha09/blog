package com.example.blog.services;

import com.example.blog.helper.Response;
import com.example.blog.helper.ValidationHelper;
import com.example.blog.helper.messages.CommonMessageConstants;
import com.example.blog.model.Faq;
import com.example.blog.payload.FaqResponse;
import com.example.blog.payload.requests.FaqRequest;
import com.example.blog.repositories.FaqRepository;
import com.example.blog.repositories.ServiceRepository;
import com.example.blog.services.iService.IFaqService;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;


@Service
@Transactional
public class FaqService extends
        BaseService<Faq, FaqRequest, FaqResponse> implements IFaqService {

    private static FaqService instance;  // Singleton instance
    private final FaqRepository faqRepository;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(FaqService.class);

    // Private constructor for Singleton
    private FaqService(FaqRepository faqRepository, ModelMapper modelMapper) {
        this.faqRepository = faqRepository;
        this.modelMapper = modelMapper;
    }

    // Public method to get the Singleton instance
    public static synchronized FaqService getInstance(FaqRepository faqRepository, ModelMapper modelMapper) {
        if (instance == null) {
            instance = new FaqService(faqRepository, modelMapper);
        }
        return instance;

    }

    //******added method to address Professor's comments****** // Simi
    // Method to destroy the current Singleton instance
    public static synchronized void resetInstance()
    {
        instance = null;
    }


    protected FaqRepository getFaqRepository() {
        return faqRepository;
    }


    @Override
    protected ServiceRepository<Faq> getServiceRepo() {
        return getFaqRepository();
    }

    /**
     * This service will create faq.
     * @author Amit Saha
     * @since 25th september
     */

    @Override
    public ResponseEntity<Response<FaqResponse>> createFaq(FaqRequest faqRequest, BindingResult bindingResult) {
        Response<FaqResponse> response = new Response<>();
        try {
            if(bindingResult.hasErrors()){
                String fieldErrors = ValidationHelper.getFieldError(bindingResult);
                response.setMessage("Faq not created for a validation error.");
                response.setSuccess(false);
                response.setErrorMessage(fieldErrors);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            Long isExistsFaq = faqRepository.countAllByIsDeletedFalseAndQuestionTitle(faqRequest.getQuestionTitle());
            if(isExistsFaq == 0){
                Faq faq = new Faq();
                faq.setQuestionTitle(faqRequest.getQuestionTitle());
                faq.setAnswerDetails(faqRequest.getAnswerDetails());
                faq.setFaqStatus(faqRequest.isFaqStatus());
                faq.setIsDeleted(false);
                Faq faqSave = faqRepository.save(faq);

                FaqResponse faqResponse = new FaqResponse();
                modelMapper.map(faqSave, faqResponse);
                response.setObj(faqResponse);
                //********

                return new ResponseEntity<>(getSuccessResponse(CommonMessageConstants.SAVED_EN, response), HttpStatus.OK);
            }
            return new ResponseEntity<>(getErrorResponse(CommonMessageConstants.FAQ_QUESTION_TITLE_ALREADY_EXIST), HttpStatus.CONFLICT);
        }catch(Exception ex){
            logger.error(CommonMessageConstants.NOT_SAVED_EN, ex);
            ex.printStackTrace();
            System.out.println(ex);
            response.setMessage(CommonMessageConstants.SOMETHING_WRONG_EN);
            response.setSuccess(false);
            response.setErrorMessage(ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * This service will update faq.
     * @author Amit Saha
     * @since 25th september
     */
    @Override
    public ResponseEntity<Response<FaqResponse>> updateFaq(FaqRequest faqRequest,BindingResult bindingResult) {
        Response<FaqResponse> response = new Response();
        try{
            if(bindingResult.hasErrors()){
                String fieldErrors = ValidationHelper.getFieldError(bindingResult);
                response.setMessage("Error for a validation.");
                response.setSuccess(false);
                response.setErrorMessage(fieldErrors);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            Long isExistsFaq = faqRepository.countAllByIsDeletedFalseAndQuestionTitleAndIdNot(faqRequest.getQuestionTitle(),faqRequest.getId());
            if(isExistsFaq == 0){
                Faq faq = faqRepository.findById(faqRequest.getId()).orElseThrow(()-> new EntityNotFoundException("Faq Not Found"));
                Faq faqUpdate = new Faq();
                if(!ObjectUtils.isEmpty(faq)){
                    faq.setQuestionTitle(faqRequest.getQuestionTitle());
                    faq.setAnswerDetails(faqRequest.getAnswerDetails());
                    faq.setFaqStatus(faqRequest.isFaqStatus());
                    faq.setIsDeleted(false);
                    faqUpdate = faqRepository.save(faq);
                }
                FaqResponse faqResponse = new FaqResponse();
                modelMapper.map(faqUpdate, faqResponse);
                response.setObj(faqResponse);
                return new ResponseEntity<>(getSuccessResponse(CommonMessageConstants.UPDATED_EN, response), HttpStatus.OK);
            }
            return new ResponseEntity<>(getErrorResponse(CommonMessageConstants.NOT_FOUND_EN), HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error(CommonMessageConstants.SOMETHING_WRONG_EN, ex);
            ex.printStackTrace();
            System.out.println(ex);
            response.setMessage(CommonMessageConstants.SOMETHING_WRONG_EN);
            response.setSuccess(false);
            response.setErrorMessage(ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
