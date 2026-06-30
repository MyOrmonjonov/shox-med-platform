package uz.shox.med.dto.home;

import lombok.*;
import uz.shox.med.dto.appointment.AppointmentResponse;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MiniAppHomeResponse {

    private HomeProfileDto profile;

    private AppointmentResponse nextAppointment;

    private List<HomeQuickActionDto> quickActions;
}