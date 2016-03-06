package br.com.quake.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.quake.entity.GameEntity;
import br.com.quake.entity.KillEntity;
import br.com.quake.parser.GameLogParser;
import br.com.quake.vo.GameScoreVO;


/**
 * Testes da classe {@link GameScoreService}
 * 
 * @author Lucas
 *
 * @version 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class GameScoreServiceTest {

	@InjectMocks
	private GameScoreService gameScoreService;
	
	@Mock
	private GameLogParser gameLogParser;
	
	@Test
	public void deveRetornarTodosJogos(){
		
		// GIVEN
		Integer listSize = 2;
		List<GameEntity> games = this.getGames();
		
		Mockito.when(gameLogParser.findAll()).thenReturn(games);

		// WHEN
		List<GameScoreVO> listGames = gameScoreService.findAll();
		
		// THEN
		Assert.assertTrue(listGames.size() == listSize);
	}
	
	@Test
	public void deveValidarPontuacoesDosJogos(){
	
		// GIVEN
		Integer listSize = 2;
		long game1TotalKills = 5;
		Map<String, Integer> game1ExpectedKillScores = new HashMap<>();
		game1ExpectedKillScores.put("Mocinha", -1);
		game1ExpectedKillScores.put("Isgalamido", 1);
		game1ExpectedKillScores.put("Zeh", 2);
		game1ExpectedKillScores.put("Maluquinho", -1);
		game1ExpectedKillScores.put("Dono da Bola", 0);
		
		long game2TotalKills = 8;
		Map<String, Integer> game2ExpectedKillScores = new HashMap<>();
		game2ExpectedKillScores.put("Mocinha", 2);
		game2ExpectedKillScores.put("Isgalamido", -1);
		game2ExpectedKillScores.put("Dono da Bola", 0);
		game2ExpectedKillScores.put("Zeh", 1);
		game2ExpectedKillScores.put("Maluquinho", 1);
		game2ExpectedKillScores.put("Assasinu Credi", -1);
		
		List<GameEntity> games = this.getGames();
		
		Mockito.when(gameLogParser.findAll()).thenReturn(games);

		// WHEN
		List<GameScoreVO> listGames = gameScoreService.findAll();
		
		// THEN
		Assert.assertTrue(listGames.size() == listSize);
		
		GameScoreVO game1 = listGames.get(0);	
		Map<String, Integer> game1KillScores = game1.getKills();
		
		Assert.assertEquals(game1TotalKills, game1.getTotalKills());
		Assert.assertEquals(game1ExpectedKillScores, game1KillScores);
		
		GameScoreVO game2 = listGames.get(1);
		Map<String, Integer> game2KillScores = game2.getKills();
		
		Assert.assertEquals(game2TotalKills, game2.getTotalKills());
		Assert.assertEquals(game2ExpectedKillScores, game2KillScores);
	}
	
	@Test
	public void deveValidarPlayersDosJogos(){
		
		// GIVEN
		Integer listSize = 2;
		List<String> game1Players = Arrays.asList("Mocinha", "Isgalamido", "Zeh", "Dono da Bola", "Maluquinho");
		List<String> game2Players = Arrays.asList("Mocinha", "Isgalamido", "Zeh", "Dono da Bola", "Maluquinho", "Assasinu Credi");
		
		List<GameEntity> games = this.getGames();
		
		Mockito.when(gameLogParser.findAll()).thenReturn(games);

		// WHEN
		List<GameScoreVO> listGames = gameScoreService.findAll();
		
		// THEN
		Assert.assertTrue(listGames.size() == listSize);
		
		GameScoreVO game1 = listGames.get(0);	
		Map<String, Integer> game1KillScores = game1.getKills();
		
		game1Players.stream().forEach(player -> Assert.assertTrue(game1KillScores.containsKey(player)));
		
		GameScoreVO game2 = listGames.get(1);
		Map<String, Integer> game2KillScores = game2.getKills();
		game2Players.stream().forEach(player -> Assert.assertTrue(game2KillScores.containsKey(player)));
	}
	
	public List<GameEntity> getGames(){
				
		GameEntity game1 = new GameEntity(1);
		game1.getKills().add(new KillEntity("<world>", "Mocinha"));
		game1.getKills().add(new KillEntity("Isgalamido", "Mocinha"));
		game1.getKills().add(new KillEntity("Zeh", "Isgalamido"));
		game1.getKills().add(new KillEntity("Zeh", "Dono da Bola"));
		game1.getKills().add(new KillEntity("<world>", "Maluquinho"));

		GameEntity game2 = new GameEntity(2);
		game2.getKills().add(new KillEntity("Mocinha", "Isgalamido"));
		game2.getKills().add(new KillEntity("Mocinha", "Dono da Bola"));
		game2.getKills().add(new KillEntity("Mocinha", "Assasinu Credi"));
		game2.getKills().add(new KillEntity("Zeh", "Mocinha"));
		game2.getKills().add(new KillEntity("Maluquinho", "Zeh"));
		game2.getKills().add(new KillEntity("<world>", "Isgalamido"));
		game2.getKills().add(new KillEntity("<world>", "Assasinu Credi"));
		game2.getKills().add(new KillEntity("<world>", "Mocinha"));
		
		return Arrays.asList(game1, game2);
	}
}
