package in.brainupgrade.authenticationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.brainupgrade.authenticationservice.model.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser, String> {

}