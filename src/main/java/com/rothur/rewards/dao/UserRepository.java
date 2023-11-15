package com.rothur.rewards.dao;

//import com.rothur.rewards.HibernateUtil;
import com.rothur.rewards.entity.UserEntity;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
}
