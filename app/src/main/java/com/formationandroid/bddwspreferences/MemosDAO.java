package com.formationandroid.bddwspreferences;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class MemosDAO
{
	
	@Query("SELECT * FROM memos ORDER BY intitule ASC")
	abstract List<MemoDTO> getListeMemos();
	
	@Insert
	abstract void insert(MemoDTO... memos);
	
	@Update
	abstract void update(MemoDTO... memos);
	
	@Delete
	abstract void delete(MemoDTO... memos);
	
}
