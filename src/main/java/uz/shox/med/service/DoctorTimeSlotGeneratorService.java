package uz.shox.med.service;

import java.time.LocalDate;

public interface DoctorTimeSlotGeneratorService {

    void generateSlotsForDoctor(Long doctorId, LocalDate fromDate, LocalDate toDate);

    void generateSlotsForAllDoctors(LocalDate fromDate, LocalDate toDate);
}