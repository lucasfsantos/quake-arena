package br.com.quake.parser;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.com.quake.ApplicationBoot;
import br.com.quake.entity.GameEntity;
import br.com.quake.entity.KillEntity;

/**
 * Testes da classe {@link GameLogParser}
 * 
 * @author Lucas
 *
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationBoot.class)
@WebAppConfiguration
public class LogParserTest {

	@Autowired
	private GameLogParser logParser;

	@Test
	public void deveRealizarParseLog() {

		// WHEN
		List<GameEntity> listGames = logParser.findAll();

		// THEN
		assertNotNull(listGames);
	}
	
	@Test
	public void deveRetornar21Jogos() {

		// GIVEN
		long expectedGameCount = 21;

		// WHEN
		List<GameEntity> listGames = logParser.findAll();

		// THEN
		assertEquals(expectedGameCount, listGames.size());
	}
	
	@Test
	public void deveValidarEstruturaRetorno(){
		
		// WHEN
		List<GameEntity> listGames = logParser.findAll();
		
		// THEN
		for (GameEntity game : listGames) {
			assertNotNull(game);
			for (KillEntity kill : game.getKills()) {
				assertNotNull(kill.getKiller());
				assertNotNull(kill.getKilled());
				assertNotEquals("", kill.getKiller().trim());
				assertNotEquals("", kill.getKilled().trim());
			}
		}
	}
}
