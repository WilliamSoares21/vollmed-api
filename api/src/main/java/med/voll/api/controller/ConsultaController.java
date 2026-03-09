package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendamentoDeConsultaDTO;
import med.voll.api.domain.consulta.DetalhamentoDeConsultaDTO;
import med.voll.api.service.ConsultaService;

@RestController
@RequestMapping("/consulta")
class ConsultaController {

  @Autowired
  private ConsultaService service;

  @PostMapping
  @Transactional
  public ResponseEntity<DetalhamentoDeConsultaDTO> agendarConsulta(
      @RequestBody @Valid AgendamentoDeConsultaDTO dados) {
    var dto = service.agendar(dados);
    return ResponseEntity.ok(dto);
  }

}
