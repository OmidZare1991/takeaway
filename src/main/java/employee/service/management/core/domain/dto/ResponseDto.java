package employee.service.management.core.domain.dto;

import java.util.Date;

public class ResponseDto<T> {
    private final long timeStamp;
    private T data;

    public ResponseDto() {
        this.timeStamp = new Date().getTime();
    }

    public ResponseDto(T data) {
        this();
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return "{" +
            "data=" + data +
            ", timeStamp=" + timeStamp +
            '}';
    }
}
