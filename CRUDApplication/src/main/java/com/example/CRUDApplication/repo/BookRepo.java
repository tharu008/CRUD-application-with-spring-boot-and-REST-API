package com.example.CRUDApplication.repo;

import com.example.CRUDApplication.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//inside<> mention the objects which you going to have the crud operations, unique/primary id
@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

}
