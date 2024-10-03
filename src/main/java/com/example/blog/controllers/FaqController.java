package com.example.blog.controllers;


import com.example.blog.constatnts.FaqConstant;
import com.example.blog.helper.Response;
import com.example.blog.model.Faq;
import com.example.blog.payload.FaqResponse;
import com.example.blog.payload.requests.FaqRequest;
import com.example.blog.repositories.FaqRepository;
import com.example.blog.services.BaseService;
import com.example.blog.services.FaqService;
import com.example.blog.services.ServiceFactory;
import com.example.blog.services.iService.IFaqService;
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
 * This controller is to provide all the faq relevant api's
 * @author Amit Saha
 * @since 25th september
 */
@RestController
@RequestMapping(FaqConstant.FAQ)
public class FaqController extends BaseController<Faq, FaqRequest, FaqResponse> {

    private final IFaqService faqService;

    public FaqController(FaqRepository faqRepository, ModelMapper modelMapper) {
        this.faqService = (IFaqService) ServiceFactory.getService(FaqService.class, faqRepository, modelMapper);
    }

    @Override
    protected BaseService<Faq, FaqRequest, FaqResponse> getService() {
        return (BaseService<Faq, FaqRequest, FaqResponse>) faqService; // Cast to BaseService
    }

   /* public FaqController(BaseService<Faq, FaqRequest, FaqResponse> service, IFaqService iFaqService) {
        super(service);
        this.iFaqService = iFaqService;
    }*/


    /**
     ** This API will Create Faq
     * @author Amit Saha
     * @since 25th september
     */
    @Operation(summary = "Create Faq", description = "Create new faq into DB")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Faq data store into database", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Faq.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400, 401", description = "Bad Request, could not save faq ", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))) })
    @PostMapping(path = FaqConstant.CREATE_FAQ, produces = "application/json")
    public ResponseEntity<Response<FaqResponse>> createFaq(@RequestBody @Valid FaqRequest faqRequest ,BindingResult bindingResult){
        return faqService.createFaq(faqRequest,bindingResult);
    }

    /**
     ** This API will update Faq
     * @author Amit Saha
     * @since 25th september
     */
    @Operation(summary = "Update Faq", description = "Update faq into DB")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Faq data update into database", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Faq.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400, 401", description = "Bad Request, could not update faq ", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))) })
    @PutMapping(path = FaqConstant.UPDATE_FAQ, produces = "application/json")
    public ResponseEntity<Response<FaqResponse>> updateFaq(@RequestBody @Valid FaqRequest faqRequest, BindingResult bindingResult){
        return faqService.updateFaq(faqRequest,bindingResult);
    }


}
