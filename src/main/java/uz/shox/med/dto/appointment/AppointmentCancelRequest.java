package uz.shox.med.dto.appointment;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentCancelRequest {

    @NotBlank
    private String reason;
}