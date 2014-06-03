package com.satya.Managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.satya.ApplicationContext;
import com.satya.IConstants;
import com.satya.BusinessObjects.Game;
import com.satya.BusinessObjects.Tag;
import com.satya.BusinessObjects.User;
import com.satya.Persistence.GamesDataStoreI;
import com.satya.Persistence.UserDataStoreI;
import com.satya.enums.GameSkillType;

public class GamesMgr {

	private static final String TAG_SEQ_IS_NULL = "Tag Seq is null";
	private static final String SKILL_TYPE_IS_NULL = "SkillType is null";

	public static JSONArray GetJSON(List<Game> games) {
		JSONArray jsonArray = new JSONArray();
		try {
			for (Game game : games) {
				JSONObject json = new JSONObject();
				json.put("seq", game.getSeq());
				json.put("gameName", game.getName());
				json.put("gamePath", game.getPath());
				json.put("gameDescription", game.getDescription());
				json.put("gameSkillTypeName", game.getGameSkillType().name());

				JSONArray jsonTags = new JSONArray();
				for (Tag tag : game.getTags()) {
					jsonTags.put(tag.getTag());
				}

				json.put("tags", jsonTags);
				jsonArray.put(json);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return jsonArray;
	}

	public static JSONArray GetTagsJSON(List<Tag> tags) {
		JSONArray jsonArray = new JSONArray();
		try {
			for (Tag tag : tags) {
				JSONObject json = new JSONObject();
				json.put("seq", tag.getSeq());
				json.put("tag", tag.getTag());
				jsonArray.put(json);
			}
		} catch (Exception e) {

		}
		return jsonArray;
	}

	public JSONArray getGamesByTag(HttpServletRequest request,
			HttpServletResponse response) {
		GamesDataStoreI GDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getGamesDataStore();
		String tagSeqStr = (String) request.getParameter("id");
		String skillType = (String) request.getParameter("skill");
		List<Game> games = new ArrayList<Game>();
		if (skillType == null || skillType.equals(IConstants.BLANK)) {
			throw new RuntimeException(SKILL_TYPE_IS_NULL);
		}
		long tagSeq = 0;
		if (tagSeqStr != null && !tagSeqStr.equals(IConstants.BLANK)) {
			tagSeq = Long.parseLong(tagSeqStr);
		} else {
			throw new RuntimeException(TAG_SEQ_IS_NULL);
		}
		if (skillType.equals(IConstants.ALL)) {
			games = GDS.findByTag(tagSeq);
		} else {
			games = GDS.findByTagAndSkill(tagSeq, skillType);
		}

		JSONArray gamesJson = GetJSON(games);
		return gamesJson;
	}

	public JSONObject getAvailableSkills(User user) {
		GameSkillType[] allSkills = GameSkillType.attention.getDeclaringClass()
				.getEnumConstants();
		List<GameSkillType> userSkills = user.getMySkills();
		JSONObject json = new JSONObject();
		try {
			for (GameSkillType skillType : allSkills) {
				if (userSkills == null || !userSkills.contains(skillType)) {
					json.put(skillType.key(), skillType.name());
				}
			}
		} catch (Exception e) {

		}
		return json;
	}

	public JSONArray getGamesBySkills(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		GamesDataStoreI GDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getGamesDataStore();
		UserDataStoreI UDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getUserDataStore();
		User user = UDS.findBySeq(ApplicationContext.getApplicationContext()
				.getLoggedinUser(request).getSeq());
		List<Game> games = new ArrayList<Game>();
		for (GameSkillType skillType : user.getMySkills()) {
			List<Game> gamesList = GDS.findBySkill(skillType);
			games.addAll(gamesList);
		}
		JSONArray json = GetJSON(games);
		return json;

	}

	public JSONArray getLastPlayedGamesJson(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Game> games = new ArrayList<Game>();
		UserDataStoreI UDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getUserDataStore();
		User user = UDS.findBySeq(ApplicationContext.getApplicationContext()
				.getLoggedinUser(request).getSeq());
		// games = getLastPlayedGames(user);
		JSONArray json = GetJSON(games);
		return json;
	}
}
