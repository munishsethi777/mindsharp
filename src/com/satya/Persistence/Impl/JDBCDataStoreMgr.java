package com.satya.Persistence.Impl;

import com.satya.Persistence.DataStoreMgr;
import com.satya.Persistence.GameResultDataStoreI;
import com.satya.Persistence.GamesDataStoreI;
import com.satya.Persistence.OrganizationDataStoreI;
import com.satya.Persistence.PromotionsDataStoreI;
import com.satya.Persistence.TagDataStoreI;
import com.satya.Persistence.UserDataStoreI;
import com.satya.Persistence.UserGameDataStoreI;

public class JDBCDataStoreMgr implements DataStoreMgr {

	private UserDataStore userDataStore;
	private GamesDataStore gamesDataStore;
	private GameResultDataStore gameResultDataStore;
	private UserGameDataStore userGameDataStore;
	private TagDataStore tagDataStore;
	private OrganizationDataStore organizationDataStore;
	private PromotionsDataStore promotionsDataStore;
	private UserBadgeDataStore userBadgeDataStore;
	
	public JDBCDataStoreMgr(PersistenceMgr persistenceMgr){
		userDataStore = new UserDataStore(persistenceMgr);
		gamesDataStore = new GamesDataStore(persistenceMgr);
		gameResultDataStore = new GameResultDataStore(persistenceMgr);
		userGameDataStore = new UserGameDataStore(persistenceMgr);
		tagDataStore = new TagDataStore(persistenceMgr);
		organizationDataStore = new OrganizationDataStore(persistenceMgr);
		promotionsDataStore = new PromotionsDataStore(persistenceMgr);
		userBadgeDataStore = new UserBadgeDataStore(persistenceMgr);
	}

	@Override
	public UserDataStoreI getUserDataStore() {
		return userDataStore;
	}

	@Override
	public GamesDataStoreI getGamesDataStore() {
		return gamesDataStore;
	}

	@Override
	public GameResultDataStoreI getGameResultDataStore() {
		return gameResultDataStore;
	}

	@Override
	public UserGameDataStoreI getUserGameDataStore(){
		return userGameDataStore;
	}

	@Override
	public TagDataStoreI getTagDataStore() {
		return tagDataStore;
	}

	@Override
	public OrganizationDataStoreI getOrganizationDataStore() {
		return organizationDataStore;
	}

	@Override
	public PromotionsDataStoreI getPromotionsDataStore() {
		return promotionsDataStore;
	}

	public UserBadgeDataStore getUserBadgeDataStore() {
		return userBadgeDataStore;
	}

	public void setUserBadgeDataStore(UserBadgeDataStore userBadgeDataStore) {
		this.userBadgeDataStore = userBadgeDataStore;
	}
	
	
}
