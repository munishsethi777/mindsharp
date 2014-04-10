package com.satya.Managers;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.satya.BusinessObjects.Game;
import com.satya.BusinessObjects.Tag;
import com.satya.BusinessObjects.User;
import com.satya.enums.GameSkillType;

public class GamesMgr {

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
}
