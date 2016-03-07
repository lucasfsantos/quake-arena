package br.com.quake.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.quake.service.GameScoreService;
import br.com.quake.vo.GameScoreVO;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = ApplicationBoot.class)
//@WebIntegrationTest({ "server.port:10100" })
@RunWith(MockitoJUnitRunner.class)
public class GameScoreControllerTest {

	@InjectMocks
	private GameScoreController gameScoreController;

	private MockMvc mockMvc;

	@Mock
	private GameScoreService gameScoreService;

	@Before
	public void setup() {
		Mockito.when(gameScoreService.findAll()).thenReturn(this.getGames());
		this.mockMvc = MockMvcBuilders.standaloneSetup(gameScoreController).build();
	}

	@Test
	public void deveRetornarListaCom2Jogos() throws Exception {

		// WHEN
		ResultActions resultActions = mockMvc
				.perform(MockMvcRequestBuilders.get("/scores").contentType(MediaType.APPLICATION_JSON));

		// THEN
		resultActions.andDo(MockMvcResultHandlers.print());

		resultActions.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
	}

	@Test
	public void deveValidarEstruturaDeJogosRetornados() throws Exception {

		// WHEN
		ResultActions resultActions = mockMvc
				.perform(MockMvcRequestBuilders.get("/scores").contentType(MediaType.APPLICATION_JSON));

		// THEN
		resultActions.andDo(MockMvcResultHandlers.print());
		resultActions.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()));

		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[0].totalKills").value(16));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[0].players", Matchers.hasSize(4)));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[0].players[0]").value("Isgalamido"));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[0].players[1]").value("Mocinha"));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[0].players[2]").value("Zeh"));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[0].players[3]").value("Dono da Bola"));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[0].kills", Matchers.notNullValue()));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[0].kills", Matchers.hasEntry("Isgalamido", 5)));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[0].kills", Matchers.hasEntry("Mocinha", 3)));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[0].kills", Matchers.hasEntry("Zeh", -2)));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[0].kills", Matchers.hasEntry("Dono da Bola", 4)));

		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[1].totalKills").value(55));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[1].players", Matchers.hasSize(4)));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[1].players[0]").value("Isgalamido"));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[1].players[1]").value("Dono da Bola"));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[1].players[2]").value("Zeh"));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[1].players[3]").value("Assasinu Credi"));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[1].kills", Matchers.notNullValue()));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[1].kills", Matchers.hasEntry("Isgalamido", 23)));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[1].kills", Matchers.hasEntry("Dono da Bola", 17)));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[1].kills", Matchers.hasEntry("Zeh", 12)));
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.[1].kills", Matchers.hasEntry("Assasinu Credi", 6)));
	}

	public List<GameScoreVO> getGames() {

		GameScoreVO game1 = new GameScoreVO();
		game1.setTotalKills(16);
		game1.setPlayers(new LinkedHashSet<>(Arrays.asList("Isgalamido", "Mocinha", "Zeh", "Dono da Bola")));

		Map<String, Integer> gameKills = new HashMap<>();
		gameKills.put("Isgalamido", 5);
		gameKills.put("Mocinha", 3);
		gameKills.put("Zeh", -2);
		gameKills.put("Dono da Bola", 4);
		game1.setKills(gameKills);

		GameScoreVO game2 = new GameScoreVO();
		game2.setTotalKills(55);
		game2.setPlayers(new LinkedHashSet<>(Arrays.asList("Isgalamido", "Dono da Bola", "Zeh", "Assasinu Credi")));

		gameKills = new HashMap<>();
		gameKills.put("Isgalamido", 23);
		gameKills.put("Dono da Bola", 17);
		gameKills.put("Zeh", 12);
		gameKills.put("Assasinu Credi", 6);
		game2.setKills(gameKills);

		return Arrays.asList(game1, game2);
	}
}
