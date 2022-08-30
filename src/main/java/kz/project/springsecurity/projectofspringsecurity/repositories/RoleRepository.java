package kz.project.springsecurity.projectofspringsecurity.repositories;

import kz.project.springsecurity.projectofspringsecurity.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role,Long> {
}
