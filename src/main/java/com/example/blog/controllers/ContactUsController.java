package com.example.blog.controllers;


import com.example.blog.constatnts.ContactUsConstant;
import com.example.blog.helper.Response;
import com.example.blog.model.ContactUs;

import com.example.blog.payload.requests.ContactUsRequest;
import com.example.blog.payload.responses.ContactUsResponse;
import com.example.blog.repositories.ContactUsRepository;
import com.example.blog.services.BaseService;
import com.example.blog.services.ContactUsService;
import com.example.blog.services.ServiceFactory;
import com.example.blog.services.iService.IContactUsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


/**
 * This controller is to provide all the ContactUs relevant api's
 * @author Amit Saha
 * @since 25th september
 */
@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping(ContactUsConstant.CONTACT_US)
public class ContactUsController extends BaseController<ContactUs, ContactUsRequest, ContactUsResponse> {

    private final IContactUsService contactUsService;

    public ContactUsController(ContactUsRepository repository, ModelMapper modelMapper) {
        this.contactUsService = (IContactUsService) ServiceFactory.getService(ContactUsService.class, repository, modelMapper);
    }

    @Override
    protected BaseService<ContactUs, ContactUsRequest, ContactUsResponse> getService() {
        return (BaseService<ContactUs, ContactUsRequest, ContactUsResponse>) contactUsService; // Cast to BaseService
    }

   /* public ContactUsController(BaseService<ContactUs, ContactUsRequest, ContactUsResponse> service, IContactUsService iContactUsService) {
        super(service);
        this.iContactUsService = iContactUsService;
    }*/


    /**
     ** This API will Create ContactUs
     * @author Amit Saha
     * @since 25th september
     */
    @Operation(summary = "Create ContactUs", description = "Create new ContactUs into DB")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "ContactUs data store into database", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ContactUs.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400, 401", description = "Bad Request, could not save ContactUs ", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))) })
    @PostMapping(path = ContactUsConstant.CREATE_CONTACT_US, produces = "application/json")
    public ResponseEntity<Response<ContactUsResponse>> createContactUs(@RequestBody ContactUsRequest request){
        return contactUsService.createContactUs(request);
    }

}
