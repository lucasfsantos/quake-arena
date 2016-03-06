package br.com.quake.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.quake.entity.GameEntity;
import br.com.quake.entity.KillEntity;
import br.com.quake.parser.GameLogParser;
import br.com.quake.vo.GameScoreVO;

/**
 * Classe responsável por obter os dados dos jogos e retornar as informações
 * 
 * @author Lucas Santos
 *
 * @version 1.0
 */
@Service
public class GameScoreService {

	private static final Logger LOGGER = LogManager.getLogger(GameScoreService.class);
	
	@Autowired
	private GameLogParser gameLogParser;
	
	/**
	 * Método responsável por realziar a consulta de jogos
	 * 
	 * @return Todos os jogos
	 */
	public List<GameScoreVO> findAll(){
		
		List<GameScoreVO> listGamesScore = new ArrayList<>();
		
		try {
			List<GameEntity> listGames = gameLogParser.findAll();
			
			this.adjustGamesScores(listGamesScore, listGames);
			
		} catch (Exception e) {
			LOGGER.error("Ocorreu um erro ao realizar a consulta de informações dos jogos.", e);
		}
		
		return listGamesScore;
	}

	/**
	 * Método responsável por realizar o depara dos dados retornados pelo arquivo de log e ajustar as pontuações dos jogos
	 * 
	 * @param listGamesScore - Lista de Jogos com pontuações
	 * @param listGames - Lista de jogos sem pontuações
	 */
	private void adjustGamesScores(List<GameScoreVO> listGamesScore, List<GameEntity> listGames) {
		for (GameEntity game : listGames) {
			GameScoreVO gameVO = new GameScoreVO();
			listGamesScore.add(gameVO);
			
			int totalKills = 0;
			for (KillEntity kill : game.getKills()) {
				if(!kill.getKiller().equals("<world>")){
					this.scoreAdjustment(gameVO, kill.getKiller(), 1);
					this.scoreAdjustment(gameVO, kill.getKilled(), 0);
				}
				else if(kill.getKiller().equals("<world>")){
					this.scoreAdjustment(gameVO, kill.getKilled(), -1);
				}					
				totalKills++;
			}
			gameVO.setTotalKills(totalKills);
		}
	}

	/**
	 * Realiza o ajuste de pontuação dos jogadores
	 * 
	 * @param gameVO Jogo
	 * @param key nome do jogador
	 * @param score pontos somados
	 */
	private void scoreAdjustment(GameScoreVO gameVO, String key, int score) {
		if(gameVO.getKills().containsKey(key)){
			Map<String, Integer> map = gameVO.getKills();
			int currentScore = map.get(key);
			map.put(key, currentScore + score);
		}else {
			gameVO.getKills().put(key, score);
		}
		gameVO.getPlayers().add(key);
	}
}
