package com.innogent.PMS.repository;

import com.innogent.PMS.entities.Goal;
import com.innogent.PMS.entities.Stage;
import com.innogent.PMS.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {
    public Stage findByGoals(Goal goal);
}
