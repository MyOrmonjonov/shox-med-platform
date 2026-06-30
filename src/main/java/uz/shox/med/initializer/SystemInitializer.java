package uz.shox.med.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.shox.med.entity.Role;
import uz.shox.med.enums.RoleName;
import uz.shox.med.repository.RoleRepository;

@Component
@RequiredArgsConstructor
public class SystemInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        createRole(RoleName.PATIENT);
        createRole(RoleName.ADMIN);
        createRole(RoleName.REGISTRAR);
        createRole(RoleName.DOCTOR);
        createRole(RoleName.LABORATORY);
        createRole(RoleName.CALL_CENTER);
        createRole(RoleName.MARKETING);
        createRole(RoleName.MANAGER);
        createRole(RoleName.SYS_ADMIN);

        System.out.println("======================================");
        System.out.println(" Shox Med Platform initialized");
        System.out.println("======================================");
    }

    private void createRole(RoleName roleName) {

        if (!roleRepository.existsByName(roleName)) {

            Role role = Role.builder()
                    .name(roleName)
                    .build();

            roleRepository.save(role);

            System.out.println("Created role : " + roleName);

        }

    }

}