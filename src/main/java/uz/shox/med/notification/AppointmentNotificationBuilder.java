package uz.shox.med.notification;

import org.springframework.stereotype.Component;
import uz.shox.med.entity.Appointment;

@Component
public class AppointmentNotificationBuilder {

    public String appointmentCreated(Appointment appointment) {

        String doctorName = appointment.getDoctor().getUser().getFirstName() + " " +
                appointment.getDoctor().getUser().getLastName();

        String serviceName = appointment.getServiceItem().getName();
        String branchName = appointment.getBranch().getName();

        return "✅ Qabulingiz yaratildi.\n\n" +
                "Shifokor: " + doctorName + "\n" +
                "Xizmat: " + serviceName + "\n" +
                "Filial: " + branchName + "\n" +
                "Vaqt: " + appointment.getAppointmentTime();
    }

    public String appointmentConfirmed(Appointment appointment) {
        return "✅ Qabulingiz tasdiqlandi.\n\n" +
                "Shifokor: " + appointment.getDoctor().getUser().getFirstName() + " " +
                appointment.getDoctor().getUser().getLastName() + "\n" +
                "Vaqt: " + appointment.getAppointmentTime();
    }

    public String appointmentCancelled(Appointment appointment) {
        return "❌ Qabulingiz bekor qilindi.\n\n" +
                "Sabab: " + appointment.getCancelReason();
    }
}