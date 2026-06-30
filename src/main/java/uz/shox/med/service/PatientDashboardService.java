package uz.shox.med.service;

import uz.shox.med.dto.dashboard.PatientDashboardResponse;
import uz.shox.med.entity.User;

public interface PatientDashboardService {

    PatientDashboardResponse getDashboard(User user);
}