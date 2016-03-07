package br.com.quake.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.quake.service.GameScoreService;
import br.com.quake.vo.GameScoreVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "quake")
@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameScoreController {

	@Autowired
	private GameScoreService gameScoreService;

	@ApiOperation(value = "Relatório de Jogos", notes = "Realiza consulta dos jogos com suas pontuações")
	@RequestMapping(value = "/scores", method = RequestMethod.GET)
	@ResponseBody
	public List<GameScoreVO> get() {
		List<GameScoreVO> list = gameScoreService.findAll();
		return list;
	}
}
