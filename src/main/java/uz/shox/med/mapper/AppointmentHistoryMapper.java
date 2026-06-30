package uz.shox.med.mapper;

import org.springframework.stereotype.Component;
import uz.shox.med.dto.appointment.AppointmentHistoryResponse;
import uz.shox.med.entity.AppointmentHistory;

@Component
public class AppointmentHistoryMapper {

    public AppointmentHistoryResponse toResponse(AppointmentHistory history) {

        String changedBy = null;

        if (history.getChangedBy() != null) {
            changedBy = history.getChangedBy().getFirstName()
                    + " "
                    + history.getChangedBy().getLastName();
        }

        return AppointmentHistoryResponse.builder()
                .id(history.getId())
                .action(history.getAction().name())
                .oldStatus(history.getOldStatus() != null ? history.getOldStatus().name() : null)
                .newStatus(history.getNewStatus() != null ? history.getNewStatus().name() : null)
                .changedBy(changedBy)
                .reason(history.getReason())
                .comment(history.getComment())
                .actionTime(history.getActionTime())
                .build();
    }
}