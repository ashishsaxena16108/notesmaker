package com.keepnotes.notemaker.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.keepnotes.notemaker.entities.Notes;

public interface NotesRepository extends JpaRepository<Notes,Integer>{
    @Query("from Notes as n where n.user.id =:user_id")
    public List<Notes> findNotesbyUserId(@Param("user_id")int user_id);
}
