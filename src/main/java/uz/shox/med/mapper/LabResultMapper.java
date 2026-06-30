package uz.shox.med.mapper;

import org.springframework.stereotype.Component;
import uz.shox.med.dto.lab.LabResultResponse;
import uz.shox.med.entity.LabResult;

@Component
public class LabResultMapper {

    public LabResultResponse toResponse(LabResult labResult) {

        String patientName = null;

        if (labResult.getPatient() != null && labResult.getPatient().getUser() != null) {
            patientName = labResult.getPatient().getUser().getFirstName() + " " +
                    labResult.getPatient().getUser().getLastName();
        }

        return LabResultResponse.builder()
                .id(labResult.getId())
                .testName(labResult.getTestName())
                .testType(labResult.getTestType())
                .status(labResult.getStatus() != null ? labResult.getStatus().name() : null)
                .patientName(patientName)
                .branchName(labResult.getBranch() != null ? labResult.getBranch().getName() : null)
                .pdfUrl(labResult.getPdfUrl())
                .resultDate(labResult.getResultDate())
                .build();
    }
}