package com.example.blog.controllers;


import com.example.blog.constatnts.AboutUsConstant;
import com.example.blog.helper.Response;
import com.example.blog.model.AboutUs;
import com.example.blog.payload.requests.AboutUsRequest;
import com.example.blog.payload.responses.AboutUsResponse;
import com.example.blog.repositories.AboutUsRepository;
import com.example.blog.services.AboutUsService;
import com.example.blog.services.BaseService;
import com.example.blog.services.ServiceFactory;
import com.example.blog.services.iService.IAboutUsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * This controller is to provide all the AboutUs relevant api's
 * @author Amit Saha
 * @since 25th september
 */
@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping(AboutUsConstant.ABOUT_US)
public class AboutUsController extends BaseController<AboutUs, AboutUsRequest, AboutUsResponse> {

    private final IAboutUsService aboutUsService;

    public AboutUsController(AboutUsRepository aboutUsRepository, ModelMapper modelMapper) {
        this.aboutUsService = (IAboutUsService) ServiceFactory.getService(AboutUsService.class, aboutUsRepository, modelMapper);
    }

    @Override
    protected BaseService<AboutUs, AboutUsRequest, AboutUsResponse> getService() {
        return (BaseService<AboutUs, AboutUsRequest, AboutUsResponse>) aboutUsService; // Cast to BaseService
    }

   /* public AboutUsController(BaseService<AboutUs, AboutUsRequest, AboutUsResponse> service, IAboutUsService iAboutUsService) {
        super(service);
        this.iAboutUsService = iAboutUsService;
    }*/


    /**
     ** This API will Create AboutUs
     * @author Amit Saha
     * @since 25th september
     */
    @Operation(summary = "Create AboutUs", description = "Create new AboutUs into DB")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "AboutUs data store into database", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AboutUs.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400, 401", description = "Bad Request, could not save AboutUs ", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))) })
    @PostMapping(path = AboutUsConstant.CREATE_ABOUT_US, produces = "application/json")
    public ResponseEntity<Response<AboutUsResponse>> createAboutUs(@RequestBody AboutUsRequest AboutUsRequest ){
        return aboutUsService.createAboutUs(AboutUsRequest);
    }

    /**
     ** This API will update AboutUs
     * @author Amit Saha
     * @since 25th september
     */
    @Operation(summary = "Update AboutUs", description = "Update AboutUs into DB")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "AboutUs data update into database", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AboutUs.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400, 401", description = "Bad Request, could not update AboutUs ", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))) })
    @PutMapping(path = AboutUsConstant.UPDATE_ABOUT_US, produces = "application/json")
    public ResponseEntity<Response<AboutUsResponse>> updateAboutUs(@RequestBody  AboutUsRequest AboutUsRequest){
        return aboutUsService.updateAboutUs(AboutUsRequest);
    }


}
