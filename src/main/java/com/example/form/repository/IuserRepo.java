package com.example.form.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.form.model.User;


@Repository
public interface IuserRepo extends JpaRepository<User,Integer>{

 

}
