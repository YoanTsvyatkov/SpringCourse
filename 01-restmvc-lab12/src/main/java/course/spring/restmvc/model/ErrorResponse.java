package course.spring.restmvc.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Required;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    @NonNull
    private int code;
    @NonNull
    private String message;
    private List<String> violation = new ArrayList<>();
    private LocalDateTime timeStamp = LocalDateTime.now();

}
