package com.innogent.PMS.repository;

import com.innogent.PMS.entities.Goal;
import com.innogent.PMS.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    public Goal findByUser(User user);
}
