package com.example.blog.services;

import com.example.blog.helper.Response;
import com.example.blog.helper.messages.CommonMessageConstants;
import com.example.blog.model.ContactUs;
import com.example.blog.payload.requests.ContactUsRequest;
import com.example.blog.payload.responses.ContactUsResponse;
import com.example.blog.repositories.ContactUsRepository;
import com.example.blog.repositories.ServiceRepository;
import com.example.blog.services.iService.IContactUsService;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;


@Service
@Transactional
public class ContactUsService extends
        BaseService<ContactUs, ContactUsRequest, ContactUsResponse> implements IContactUsService {

    private static ContactUsService instance;  // Singleton instance
    private final ContactUsRepository repository;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(ContactUsService.class);

    // Private constructor for Singleton
    private ContactUsService(ContactUsRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    // Public method to get the Singleton instance
    public static synchronized ContactUsService getInstance(ContactUsRepository repository, ModelMapper modelMapper) {
        if (instance == null) {
            instance = new ContactUsService(repository, modelMapper);
        }
        return instance;
    }


    protected ContactUsRepository getContactUsRepository() {
        return repository;
    }


    @Override
    protected ServiceRepository<ContactUs> getServiceRepo() {
        return getContactUsRepository();
    }

    /**
     * This service will create ContactUs.
     * @author Amit Saha
     * @since 25th september
     */
    @Override
    public ResponseEntity<Response<ContactUsResponse>> createContactUs(ContactUsRequest request) {
        Response<ContactUsResponse> response = new Response<>();
        try {

            ContactUs contactUs = new ContactUs();
            contactUs.setContent(request.getContent());
            contactUs.setEmail(request.getEmail());
            contactUs.setSubject(request.getSubject());
            contactUs.setReviewed(false);
            contactUs.setIsDeleted(false);
            ContactUs contactUsSave = repository.save(contactUs);

            ContactUsResponse contactUsResponse = new ContactUsResponse();
            modelMapper.map(contactUsSave, contactUsResponse);
            response.setObj(contactUsResponse);
            return new ResponseEntity<>(getSuccessResponse(CommonMessageConstants.SAVED_EN, response), HttpStatus.OK);
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

}
