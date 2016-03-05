package br.com.quake;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "quake")
@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScoreController {

	@ApiOperation(value = "Relatório de Jogos", notes = "Realiza consulta e pontuação de cada jogo")
	@ApiResponses(value = { @ApiResponse(code = 412, message = "") })
	@RequestMapping(value = "/score", method = RequestMethod.GET)
	@ResponseBody
	public String get() throws Exception {
		return "Ok";
	}
}
