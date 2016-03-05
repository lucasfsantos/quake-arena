package br.com.quake.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
	
	public List<GameScoreVO> findAll(){
		
		List<GameScoreVO> listGamesScore = new ArrayList<>();
		
		try {
			List<GameEntity> listGames = gameLogParser.findAll();
			
			for (GameEntity game : listGames) {
				GameScoreVO gameVO = new GameScoreVO();
				listGamesScore.add(gameVO);
				
				int totalKills = 0;
				for (KillEntity kill : game.getKills()) {
					if(!kill.getKiller().equals("<world>")){
						scoreAdjustment(gameVO, kill.getKiller(), 1);
						scoreAdjustment(gameVO, kill.getKilled(), 0);
						
						//gameVO.getPlayers().add(kill.getKiller());
						//gameVO.getPlayers().add(kill.getKilled());
					}
					else if(kill.getKiller().equals("<world>")){
						scoreAdjustment(gameVO, kill.getKilled(), -1);
						//gameVO.getPlayers().add(kill.getKilled());

					}					
					totalKills++;
				}
				gameVO.setTotalKills(totalKills);
			}
			
		} catch (Exception e) {
			LOGGER.error("Ocorreu um erro ao realizar a consulta de informações dos jogos.", e);
		}
		
		return listGamesScore;
	}

	private void scoreAdjustment(GameScoreVO gameVO, String key, int score) {
		if(gameVO.getKills().containsKey(key)){
			Map<String, Integer> map = gameVO.getKills();
			map.put(key, map.get(key) + score);
		}else {
			gameVO.getKills().put(key, score);
		}
		gameVO.getPlayers().add(key);
	}
}
