package br.com.quake.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.quake.entity.GameEntity;
import br.com.quake.entity.KillEntity;

/**
 * Classe responsável por extrair os dados necessários para geração de
 * informações dos jogos.
 * 
 * @author Lucas Santos
 *
 * @version 1.0
 */
@Component
public class GameLogParser {

	private static final Logger LOGGER = LogManager.getLogger(GameLogParser.class);

	@Value("${quake.log.gametag}")
	private String quakeLogGameTag;
	
	@Value("${quake.log.killtag}")
	private String quakeLogKillTag;
	
	/**
	 * Retorna todos os jogos do arquivo de log.
	 *
	 * @return Lista de jogos
	 */
	public List<GameEntity> findAll(){
		return this.parse();
	}
	
	/**
	 * Método responsável por realizar o parse do arquivo de game.log
	 * 
	 * @return Lista de jogos
	 */
	private List<GameEntity> parse() {

		LOGGER.info("Iniciando parse do arquivo de log.");

		List<GameEntity> listGames = new ArrayList<GameEntity>();

		try {
			List<String> lines = this.getLogLies();
			GameEntity game = null;

			int countId = 1;
			for (String line : lines) {
				if (line.contains(quakeLogGameTag)) {
					game = new GameEntity(countId++);
					listGames.add(game);
				} else if (game != null) {
					if (line.contains(quakeLogKillTag)) {
						String killLine = getKillLine(line);
						KillEntity kill = getPlayersKillLine(killLine);
						game.getKills().add(kill);
					}
				}
			}

		} catch (Exception e) {
			LOGGER.error("Ocorreu um erro ao realizar o parse do arquivo de log.", e);
		}
		LOGGER.info("Fim do parse do arquivo de log.");

		return listGames;
	}

	/**
	 * Método responsável por retornar as linhas de confrontos
	 * 
	 * @param line
	 * @return linha com players
	 */
	private String getKillLine(String line) {

		String regex = Pattern.quote(":") + "(.*?)" + Pattern.quote("by");
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);

		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			line = matcher.group(0);
		}
		return line;
	}

	/**
	 * Método responsável por retornar os players em confronto de uma linha
	 * 
	 * @param line
	 * @return Players
	 */
	private KillEntity getPlayersKillLine(String line) {

		KillEntity kill = null;

		String regex = Pattern.quote(":") + "(.*?)" + Pattern.quote(":") + "(.*?)" + Pattern.quote(":");
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			String replaceText = matcher.group(0);
			line = line.replace(replaceText, "").replace("by", "");
			String[] splitUsers = line.split("killed");

			String userKiller = splitUsers[0].trim();
			String userKilled = splitUsers[1].trim();
			kill = new KillEntity(userKiller, userKilled);
		}
		return kill;
	}

	/**
	 * Método responsável por realizar a leitura do arquivo de log.
	 * 
	 * @return Lista de linhas do arquivo
	 */
	private List<String> getLogLies() {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("games.log");
		
		Stream<String> stream = new BufferedReader(
				new InputStreamReader(inputStream)).lines();

		List<String> lines = stream.map(String::valueOf).collect(Collectors.toList());

		return lines;
	}
}
