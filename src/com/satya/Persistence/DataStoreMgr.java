package com.satya.Persistence;

public interface DataStoreMgr {

	public UserDataStoreI getUserDataStore();
	public GamesDataStoreI getGamesDataStore();
	public GameResultDataStoreI getGameResultDataStore();
	public UserGameDataStoreI getUserGameDataStore();
	public TagDataStoreI getTagDataStore();
	public OrganizationDataStoreI getOrganizationDataStore();
	public PromotionsDataStoreI getPromotionsDataStore();
	public UserBadgeDataStoreI getUserBadgeDataStore();
}
