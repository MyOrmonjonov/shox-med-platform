package uz.shox.med.dto.appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentCreateRequest {

    @NotNull(message = "Filial majburiy")
    private Long branchId;

    @NotNull(message = "Shifokor majburiy")
    private Long doctorId;

    @NotNull(message = "Xizmat majburiy")
    private Long serviceItemId;

    @NotNull(message = "Vaqt sloti majburiy")
    private Long timeSlotId;

    @Size(max = 1000)
    private String patientComment; // ixtiyoriy

    private String source;
}