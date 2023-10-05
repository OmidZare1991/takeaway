package employee.service.management.core.base_mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BaseMapper {
    @Named("toDate")
    default String toDate(Long dateValue) {
        Date date = new Date(dateValue);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    @Named("getId")
    default String getId() {
        return UUID.randomUUID().toString();
    }
}
