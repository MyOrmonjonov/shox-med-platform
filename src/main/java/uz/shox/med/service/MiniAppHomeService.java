package uz.shox.med.service;

import uz.shox.med.dto.home.*;
import uz.shox.med.dto.branch.BranchResponse;
import uz.shox.med.dto.doctor.DoctorResponse;
import uz.shox.med.dto.specialization.SpecializationResponse;
import uz.shox.med.entity.User;

import java.util.List;

public interface MiniAppHomeService {

    MiniAppHomeResponse getHome(User user);

    List<HomeBannerDto> getBanners();

    List<BranchResponse> getBranches();

    List<SpecializationResponse> getSpecializations();

    List<DoctorResponse> getPopularDoctors();

    List<HomeNotificationDto> getNotifications(User user);
}