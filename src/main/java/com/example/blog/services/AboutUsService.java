package com.example.blog.services;

import com.example.blog.helper.Response;
import com.example.blog.helper.messages.CommonMessageConstants;
import com.example.blog.model.AboutUs;
import com.example.blog.payload.requests.AboutUsRequest;
import com.example.blog.payload.responses.AboutUsResponse;
import com.example.blog.repositories.AboutUsRepository;
import com.example.blog.repositories.ServiceRepository;
import com.example.blog.services.iService.IAboutUsService;
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
public class AboutUsService extends
        BaseService<AboutUs, AboutUsRequest, AboutUsResponse> implements IAboutUsService {

    private static AboutUsService instance;  // Singleton instance
    private final AboutUsRepository aboutUsRepository;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(AboutUsService.class);

    // Private constructor for Singleton
    private AboutUsService(AboutUsRepository aboutUsRepository, ModelMapper modelMapper) {
        this.aboutUsRepository = aboutUsRepository;
        this.modelMapper = modelMapper;
    }

    // Public method to get the Singleton instance
    public static synchronized AboutUsService getInstance(AboutUsRepository aboutUsRepository, ModelMapper modelMapper) {
        if (instance == null) {
            instance = new AboutUsService(aboutUsRepository, modelMapper);
        }
        return instance;
    }


    protected AboutUsRepository getAboutUsRepository() {
        return aboutUsRepository;
    }


    @Override
    protected ServiceRepository<AboutUs> getServiceRepo() {
        return getAboutUsRepository();
    }

    /**
     * This service will create AboutUs.
     * @author Amit Saha
     * @since 25th september
     */
    @Override
    public ResponseEntity<Response<AboutUsResponse>> createAboutUs(AboutUsRequest aboutUsRequest) {
        Response<AboutUsResponse> response = new Response<>();
        try {
            Long isExistsAboutUs = aboutUsRepository.countAllByAboutUsStatusTrue();
            if(isExistsAboutUs == 0){
                AboutUs aboutUs = new AboutUs();
                aboutUs.setAboutUsStatus(aboutUsRequest.isAboutUsStatus());
                aboutUs.setContent(aboutUsRequest.getContent());
                aboutUs.setIsDeleted(false);
                AboutUs aboutUsSave = aboutUsRepository.save(aboutUs);

                AboutUsResponse aboutUsResponse = new AboutUsResponse();
                modelMapper.map(aboutUsSave, aboutUsResponse);
                response.setObj(aboutUsResponse);
                return new ResponseEntity<>(getSuccessResponse(CommonMessageConstants.SAVED_EN, response), HttpStatus.OK);
            }
            return new ResponseEntity<>(getErrorResponse(CommonMessageConstants.ALREADY_EXIST_EN), HttpStatus.CONFLICT);
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
     * This service will update AboutUs.
     * @author Amit Saha
     * @since 25th september
     */
    @Override
    public ResponseEntity<Response<AboutUsResponse>> updateAboutUs(AboutUsRequest aboutUsRequest) {
        Response<AboutUsResponse> response = new Response();
        try{
                AboutUs aboutUs = aboutUsRepository.findById(aboutUsRequest.getId()).orElseThrow(()-> new EntityNotFoundException("AboutUs Not Found"));
                AboutUs aboutUsUpdate = new AboutUs();
                if(!ObjectUtils.isEmpty(aboutUs)){
                    aboutUs.setContent(aboutUsRequest.getContent());
                    aboutUs.setAboutUsStatus(aboutUsRequest.isAboutUsStatus());
                    aboutUs.setIsDeleted(false);
                    aboutUsUpdate = aboutUsRepository.save(aboutUs);
                }
                AboutUsResponse aboutUsResponse = new AboutUsResponse();
                modelMapper.map(aboutUsUpdate, aboutUsResponse);
                response.setObj(aboutUsResponse);
                return new ResponseEntity<>(getSuccessResponse(CommonMessageConstants.UPDATED_EN, response), HttpStatus.OK);
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
