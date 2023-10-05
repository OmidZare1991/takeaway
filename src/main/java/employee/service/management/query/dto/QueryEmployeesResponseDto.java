package employee.service.management.query.dto;

import java.util.List;

public record QueryEmployeesResponseDto(List<EmployeeResponseDto> employees, Long totalElements, Integer totalPages, Integer currentPageNumber, Integer currentPageSize) {
}
