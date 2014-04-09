package com.satya.Persistence;

import com.satya.BusinessObjects.Promotion;

public interface PromotionsDataStoreI {

	public Promotion findBySeq(long seq);
	public Promotion findByCode(String code);
	
}
