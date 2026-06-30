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

    @NotNull(message = "Qabul vaqti majburiy")
    @Future(message = "Qabul vaqti kelajakda bo'lishi kerak")
    private LocalDateTime appointmentTime;

    @Size(max = 1000)
    private String patientComment;

    /**
     * Telegram Mini App
     * Mobile
     * CRM
     */
    private String source;

}