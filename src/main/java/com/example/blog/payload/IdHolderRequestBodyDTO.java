package com.example.blog.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


/**
 * This class is to provide id holder request dto
 *
 * @author Amit Saha
 * @since 25th september
 */
@Data
public class IdHolderRequestBodyDTO implements IIdHolderRequestBodyDTO {

    private Long id;
}
