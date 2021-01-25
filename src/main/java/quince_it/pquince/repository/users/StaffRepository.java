package quince_it.pquince.repository.users;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import quince_it.pquince.entities.users.Staff;

public interface StaffRepository  extends JpaRepository<Staff, UUID>{

}
