package com.formationandroid.bddwspreferences;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "memos")
public class MemoDTO
{
	
	// Attributs :
	@PrimaryKey(autoGenerate = true)
	public long memoId = 0;
	public String intitule;
	
	
	/**
	 * Constructeur public vide.
	 */
	public MemoDTO() {}
	
	/**
	 * Constructeur.
	 * @param intitule Intitulé du mémo
	 */
	@Ignore
	public MemoDTO(String intitule)
	{
		this.intitule = intitule;
	}
	
}
