package com.satya.Managers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.satya.ApplicationContext;
import com.satya.IConstants;
import com.satya.BusinessObjects.Game;
import com.satya.BusinessObjects.GameResult;
import com.satya.BusinessObjects.Organization;
import com.satya.BusinessObjects.User;
import com.satya.Persistence.GameResultDataStoreI;
import com.satya.Persistence.GamesDataStoreI;
import com.satya.Persistence.UserDataStoreI;
import com.satya.Persistence.UserGameDataStoreI;
import com.satya.Utils.StringUtils;
import com.satya.enums.GameSkillType;

public class GameResultMgr {

	Logger log = Logger.getLogger(GameResultMgr.class.getName());

	public void saveGameResult(HttpServletRequest request,
			HttpServletResponse response) {
		GameResultDataStoreI GRDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getGameResultDataStore();
		UserGameDataStoreI UGDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getUserGameDataStore();

		try {
			InputStream inputStream = request.getInputStream();
			String result;
			if (inputStream != null) {
				String resultXML = StringUtils
						.ConvertStreamToString(inputStream);

				GameResult gameResult = ApplicationContext
						.getApplicationContext().getGameResultsMgr()
						.getGameResultFromXML(resultXML, request);
				/*
				 * Enroll Calle being commented right now.
				 */
				// // Enroll the game save call
				// User loggedInUser =
				// ApplicationContext.getApplicationContext().getLoggedinUser(request);
				// UserGame userGame = new UserGame(
				// loggedInUser, new Game(gameResult
				// .getGame().getSeq()), new Date());
				// UGDS.saveUserGame(userGame);
				// // enrollment call done
				try {
					GRDS.SaveGameResult(gameResult);
					ApplicationContext.getApplicationContext().getBadgeMgr()
							.allotBadge(gameResult);
				} catch (Exception e) {
				}
			} else {
				response.getWriter().print("success=true");
				return;
			}
		} catch (Exception e) {
		}
	}

	public int getTotalGamesPlayedByUser(User user) {
		GameResultDataStoreI GRDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getGameResultDataStore();
		int count = GRDS.getTotalGamesPlayedByUser(user.getSeq());
		return count;
	}

	public int getTotalResultBySkillTypeForUser(GameSkillType skillType,
			User user) {
		GameResultDataStoreI GRDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getGameResultDataStore();
		int count = GRDS.getTotalResultsBySkillTypeForUser(skillType,
				user.getSeq());
		return count;
	}

	private GameResult getGameResultFromXML(String xml,
			HttpServletRequest request) {
		GameResult gameResult = new GameResult();
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new InputSource(
					new ByteArrayInputStream(xml.getBytes("utf-8"))));

			doc.getDocumentElement().normalize();
			int gameId = getTagValue("game_id", doc);
			Date dated = new Date();
			int score = getTagValue("score", doc);
			int correct = getTagValue("num_correct", doc);
			int levels = getTagValue("session_level", doc);
			int totalAttempts = getTagValue("num_tries", doc);
			int timePlayedSeconds = getTagValue("time", doc);

			if (gameId == 2) {
				timePlayedSeconds = getTagValue("response_time", doc);
			}
			if (gameId == 7) {
				totalAttempts = getTagValue("Total_Attempts", doc);
				correct = getTagValue("correct", doc);
			}
			int opportunities = 0;
			int reactionTimeSeconds = 0;
			double accuratePercent = score / totalAttempts;

			if (gameId != 0) {
				// ---@Author -- Baljeet Gaheer ----
				long seq = Long.valueOf(gameId);
				GamesDataStoreI GDS = ApplicationContext
						.getApplicationContext().getDataStoreMgr()
						.getGamesDataStore();
				Game game = GDS.findBySeq(seq);
				gameResult.setGame(game);
				// ---@----
				gameResult.setUser(ApplicationContext.getApplicationContext()
						.getLoggedinUser(request));
				gameResult.setScore(Integer.valueOf(score));
				gameResult.setDated(dated);
				gameResult.setCorrect(correct);
				gameResult.setLevels(levels);
				gameResult.setTotalAttempts(totalAttempts);
				gameResult.setTimePlayedSeconds(timePlayedSeconds);
				gameResult.setOpportunities(opportunities);
				gameResult.setReactionTimeSeconds(reactionTimeSeconds);
				gameResult.setAccuratePercent(accuratePercent);

			}
		} catch (SAXParseException err) {
			System.out.println("** Parsing error" + ", line "
					+ err.getLineNumber() + ", uri " + err.getSystemId());
			System.out.println(" " + err.getMessage());

		} catch (SAXException e) {
			Exception x = e.getException();
			((x == null) ? e : x).printStackTrace();

		} catch (Throwable t) {
			t.printStackTrace();
		}
		return gameResult;
	}

	private int getTagValue(String tag, Document doc) {
		int tagValue = 0;
		try {
			NodeList gameIdList = doc.getElementsByTagName(tag);
			Element element = (Element) gameIdList.item(0);
			if (element.getFirstChild().getNodeValue() != null) {
				tagValue = Integer.parseInt(element.getFirstChild()
						.getNodeValue());
			}
		} catch (Exception e) {
		}
		return tagValue;
	}

	public String getScoreChartJSON(long userSeq) {
		try {
			GameResultDataStoreI GRDS = ApplicationContext
					.getApplicationContext().getDataStoreMgr()
					.getGameResultDataStore();
			List<GameResult> gamesResults = GRDS.findResultsByUser(userSeq);
			Map<GameSkillType, Integer> totalGames = new HashMap<GameSkillType, Integer>();
			Map<GameSkillType, Integer> totalScores = new HashMap<GameSkillType, Integer>();
			for (GameResult gameResult : gamesResults) {
				int skillScore = 0;
				int gamesTotal = 0;
				if (totalScores.get(gameResult.getGame().getGameSkillType()) != null) {
					skillScore = totalScores.get(gameResult.getGame()
							.getGameSkillType());

				}
				if (totalGames.get(gameResult.getGame().getGameSkillType()) != null) {
					gamesTotal = totalGames.get(gameResult.getGame()
							.getGameSkillType());
				}
				totalScores.put(gameResult.getGame().getGameSkillType(),
						gameResult.getScore() + skillScore);
				totalGames.put(gameResult.getGame().getGameSkillType(),
						1 + gamesTotal);
			}
			StringBuffer stringBuffer = new StringBuffer();
			double attVal = 0;
			double fleVal = 0;
			double memVal = 0;
			double proVal = 0;
			double speVal = 0;

			stringBuffer.append("[");
			GameSkillType skill = GameSkillType.speed;
			if (totalScores.get(skill) != null && totalGames.get(skill) != null) {
				speVal = totalScores.get(skill) / totalGames.get(skill);
			}
			skill = GameSkillType.flexibility;
			if (totalScores.get(skill) != null && totalGames.get(skill) != null) {
				fleVal = totalScores.get(skill) / totalGames.get(skill);
			}
			skill = GameSkillType.problemSolving;
			if (totalScores.get(skill) != null && totalGames.get(skill) != null) {
				proVal = totalScores.get(skill) / totalGames.get(skill);
			}
			skill = GameSkillType.attention;
			if (totalScores.get(skill) != null && totalGames.get(skill) != null) {
				attVal = totalScores.get(skill) / totalGames.get(skill);
			}

			skill = GameSkillType.memory;
			if (totalScores.get(skill) != null && totalGames.get(skill) != null) {
				memVal = totalScores.get(skill) / totalGames.get(skill);
			}

			stringBuffer.append(speVal + ",");
			stringBuffer.append(fleVal + ",");
			stringBuffer.append(proVal + ",");
			stringBuffer.append(attVal + ",");
			stringBuffer.append(memVal);

			stringBuffer.append("]");
			return stringBuffer.toString();

		} catch (Exception e) {
			log.error("Error while displaying chart", e);
		}
		return null;
	}

	public int getResultByDays(long userId, int Days) {
		GameResultDataStoreI GRDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getGameResultDataStore();
		int resCount = GRDS.getResultByDays(userId, Days);
		return resCount;
	}

	public JSONArray getLearderBoard(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserDataStoreI UDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getUserDataStore();
		User user = UDS.findBySeq(ApplicationContext.getApplicationContext()
				.getLoggedinUser(request).getSeq());
		GameResultDataStoreI GRDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getGameResultDataStore();
		String skillType = (String) request.getParameter("skillType");
		JSONArray jsonArr = new JSONArray();
		Organization org = user.getOrganization();

		long orgSeq = 0;
		if (org != null) {
			orgSeq = org.getSeq();
		} else {
			log.error("Organization is null for userseq  " + user.getSeq());
			throw new RuntimeException("Organization is null for userseq  "
					+ user.getSeq());
		}

		try {
			List<GameResult> gameResults = new ArrayList<GameResult>();
			if (skillType.equals(IConstants.ALL)) {
				gameResults = GRDS.getResultByOrg(orgSeq);
			} else {
				GameSkillType skill = GameSkillType.valueOf(skillType);
				gameResults = GRDS.getResultByOrgAndSkill(skill, orgSeq);
			}

			for (GameResult gameResult : gameResults) {
				JSONObject resultJson = new JSONObject();
				resultJson.put("userName", gameResult.getUser().getUserName());
				resultJson.put("avgScore", gameResult.getScore());
				jsonArr.put(resultJson);
			}

		} catch (Exception e) {
			log.error(
					"Error during call getLearderBoard for userseq "
							+ user.getSeq(), e);
			throw new RuntimeException(e);
		}

		return jsonArr;
	}

	public JSONArray getLastPlayedGamesJson(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<GameResult> gameResults = new ArrayList<GameResult>();
		UserDataStoreI UDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getUserDataStore();
		User user = UDS.findBySeq(ApplicationContext.getApplicationContext()
				.getLoggedinUser(request).getSeq());
		GameResultDataStoreI GRDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getGameResultDataStore();
		JSONArray jsonArr = new JSONArray();
		try {
			gameResults = GRDS.getLastPlayedGames(user);
			for (GameResult gameResult : gameResults) {
				JSONObject resultJson = new JSONObject();
				resultJson.put("score", gameResult.getScore());
				resultJson.put("playedDate", gameResult.getDated());
				resultJson.put("gameseq", gameResult.getGame().getSeq());
				resultJson.put("gamename", gameResult.getGame().getName());
				jsonArr.put(resultJson);
			}
		} catch (Exception e) {
			log.error("Error during call getLastPlayedGamesJson for userseq "
					+ user.getSeq(), e);
			throw new RuntimeException(e);
		}
		return jsonArr;
	}
}
