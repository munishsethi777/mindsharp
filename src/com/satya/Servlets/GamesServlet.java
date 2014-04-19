package com.satya.Servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.satya.ApplicationContext;
import com.satya.BusinessObjects.Game;
import com.satya.BusinessObjects.GameResult;
import com.satya.BusinessObjects.Tag;
import com.satya.BusinessObjects.User;
import com.satya.BusinessObjects.UserGame;
import com.satya.Managers.GameResultMgr;
import com.satya.Managers.GamesMgr;
import com.satya.Persistence.GameResultDataStoreI;
import com.satya.Persistence.GamesDataStoreI;
import com.satya.Persistence.TagDataStoreI;
import com.satya.Persistence.UserGameDataStoreI;
import com.satya.enums.GameSkillType;

/**
 * Servlet implementation class GamesServlet
 */

public class GamesServlet extends BaseServletClass {
	private static final long serialVersionUID = 1L;
	private static final String SHOW = "showGames";
	private static final String SHOWMYGAMES = "showMyGames";
	private static final String SHOWGAME = "showGame";
	private static final String HISTORY = "history";
	private static final String ENROLL = "enroll";
	private static final String SHOWBYSKILL = "showGamesBySkill";
	private static final String SHOW_BY_TAG = "showGamesByTag";
	private static final String GETALLTAGS = "getAllTags";
	private static final String SAVE_RESULT = "saveResult";

	public GamesServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		super.checkSession(request, response);

		GamesDataStoreI GDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getGamesDataStore();
		GameResultDataStoreI GRDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getGameResultDataStore();
		UserGameDataStoreI UGDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getUserGameDataStore();
		TagDataStoreI TDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getTagDataStore();
		ApplicationContext.getApplicationContext();
		GamesMgr gameMgr = ApplicationContext.getGamesMgr();
		String action = request.getParameter("action");
		/*
		 * Show Games on Games page
		 */
		if (action.equals(SHOW)) {
			List<Game> games = GDS.findAllGames();
			request.setAttribute("games", games);
			request.getRequestDispatcher("games.jsp")
					.forward(request, response);
		} else if (action.equals(SHOWBYSKILL)) {
			String skill = (String) request.getParameter("skill");
			if (skill != null && !skill.equals("") & !skill.equals("all")) {
				GameSkillType gameSkillType = GameSkillType.valueOf(skill);
				List<Game> games = GDS.findBySkill(gameSkillType);
				JSONArray jsonArray = GamesMgr.GetJSON(games);
				response.getWriter().print(jsonArray.toString());
			} else if (skill != null && skill.equals("all")) {
				List<Game> games = GDS.findAllGames();
				JSONArray jsonArray = GamesMgr.GetJSON(games);
				response.getWriter().print(jsonArray.toString());
			}
		} else if (action.equals(SHOW_BY_TAG)) {
			JSONArray jsonArray = gameMgr.getGamesByTag(request, response);
			response.getWriter().print(jsonArray.toString());
		}
		/*
		 * Get All Tags to enable search
		 */
		else if (action.equals(GETALLTAGS)) {
			List<Tag> tags = TDS.findAll();
			JSONArray jsonArray = GamesMgr.GetTagsJSON(tags);
			response.getWriter().print(jsonArray.toString());
		}
		/*
		 * Show Game Page
		 */
		else if (action.equals(SHOWGAME)) {
			ShowGamePage(request, response);
		}
		/*
		 * Show My Games Results History
		 */
		else if (action.equals(SHOWMYGAMES)) {
			List<UserGame> userGames = UGDS.getGameByUser(super
					.getLoggedinUser(request).getSeq());
			request.setAttribute("userGames", userGames);
			request.getRequestDispatcher("myGames.jsp").forward(request,
					response);
		}
		/*
		 * Show Games History with Scoring
		 */
		else if (action.equals(HISTORY)) {
			List<GameResult> gameResults = GRDS.findResultsByUser(super
					.getLoggedinUser(request).getSeq());
			request.setAttribute("gameResults", gameResults);
			request.getRequestDispatcher("history.jsp").forward(request,
					response);
		}
		/*
		 * Enroll a game for your login
		 */
		else if (action.equals(ENROLL)) {
			String gameSeq = (String) request.getParameter("id");
			if (gameSeq != null) {
				UserGame userGame = new UserGame(
						super.getLoggedinUser(request), new Game(
								Long.parseLong(gameSeq)), new Date());
				UGDS.saveUserGame(userGame);
				List<Game> games = GDS.findAllGames();
				request.setAttribute("games", games);
				request.getRequestDispatcher("games.jsp").forward(request,
						response);
			}
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		GamesDataStoreI GDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getGamesDataStore();
		UserGameDataStoreI UGDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getUserGameDataStore();

		GameResultMgr gameResultMgr = ApplicationContext
				.getApplicationContext().getGameResultsMgr();

		String getOrUpdate = request.getParameter("getorUpdate");
		response.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		/*
		 * Saving GameResult after game level achieved
		 */
		if (action.equals(SAVE_RESULT)) {
			gameResultMgr.saveGameResult(request, response);

		} else if (action.equals(SHOWGAME)) {
			ShowGamePage(request, response);

		} else {
			/*
			 * Called when game loads to get game path from server.
			 */
			if (getOrUpdate != null && getOrUpdate.equals("get")) {
				String gameId = request.getParameter("game_id");
				Game game = GDS.findBySeq(Long.valueOf(gameId));
				response.getWriter().print(
						"gamepath=" + game.getPath() + "&amp;gamepath="
								+ game.getPath() + "&amp;gamename=rrr");

			}
			/*
			 * Game loading calls ths for multiple times, we need to print
			 * success on flash player. needs to study more on this, why is it
			 * called multiple times
			 */
			else {
				response.getWriter().print("success=true");
			}
		}
	}

	private void ShowGamePage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		GamesDataStoreI GDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getGamesDataStore();
		GameResultDataStoreI GRDS = ApplicationContext.getApplicationContext()
				.getDataStoreMgr().getGameResultDataStore();
		User user = ApplicationContext.getApplicationContext().getLoggedinUser(
				request);
		String gameSeq = (String) request.getParameter("id");
		if (gameSeq != null) {
			Game game = GDS.findBySeq(Long.parseLong(gameSeq));
			if (game != null) {
				int myBestScore = GRDS.findMaxScore(game.getSeq(),
						user.getSeq());
				game.setMyBestScore(myBestScore);
				request.setAttribute("game", game);
			} else {
				// user entered bad gameID
				request.getRequestDispatcher("games.jsp").forward(request,
						response);
				return;
			}
			request.getRequestDispatcher("game.jsp").forward(request, response);
		}
	}

}
