package employee.service.management.query.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record PaginationSetting(
        Integer page
        , @Min(value = 0 ,message = "page size cannot be a negative number") @Max(value = 100,message = "page size cannot be greater than 100")
        Integer size) {
}
