package com.satya.Persistence;

import java.util.List;

import com.satya.BusinessObjects.Tag;

public interface TagDataStoreI {
	
	public List<Tag> findTagsByGame(long gameSeq);
	public List<Tag> findAll();
}
