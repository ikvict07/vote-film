package org.fiit.votefilm.repository.users;

import org.fiit.votefilm.model.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for admins.
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    /**
     * Find an admin by its username.
     *
     * @param username The username of the admin.
     * @return The optional admin with the given username.
     */
    Optional<Admin> findAdminByUsername(String username);

}
