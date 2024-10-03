package com.example.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

/**
 * This class is to provide boolean id set request dto
 *
 * @author Amit Saha
 * @since 25th september
 */
@Data
public class IdSetRequestBodyDTO {
    @NotEmpty
    private Set<@NotNull Long> ids;
}
