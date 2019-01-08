package org.sadtech.consultant.database.repository;

import org.sadtech.consultant.database.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT u FROM Person u INNER JOIN u.socialNetworks p WHERE KEY(p) = :type2 AND VALUE(p) = :socialId")
    Person getUserBySocialNetworksId(@Param("type2") String type, @Param("socialId") Integer socialNetworksId);

}
