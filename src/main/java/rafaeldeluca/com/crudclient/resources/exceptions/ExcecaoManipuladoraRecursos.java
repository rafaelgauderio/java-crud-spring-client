package rafaeldeluca.com.crudclient.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import rafaeldeluca.com.crudclient.services.exceptions.ExcecaoRecursoNaoEncontrado;

@ControllerAdvice // permite que essa classe interpepte qualquer exceção que disparar
public class ExcecaoManipuladoraRecursos {
	
	@ExceptionHandler(ExcecaoRecursoNaoEncontrado.class)
	public ResponseEntity<ErroPadrao> entidadeNaoEncontrada(ExcecaoRecursoNaoEncontrado error, HttpServletRequest requisicao) {
		
		//agora vai disparar o codigo erro http 404
		//disparar um codito erro http 500 pro usuario e log de excecao é deselengante em um projeto web
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErroPadrao erro = new ErroPadrao();
		
		erro.setTimestamp(Instant.now());
		erro.setStatus(status.value());
		erro.setError("Recurso não foi encotrado com o Id informado.");
		erro.setMessage(error.getMessage());
		erro.setPath(requisicao.getRequestURI());
		
		return ResponseEntity.status(status).body(erro);
		
		
	}

}
