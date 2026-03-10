package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
// Importação estática do "when" do Mockito — estava faltando, causava erro de compilação
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import med.voll.api.domain.consulta.AgendamentoDeConsultaDTO;
import med.voll.api.domain.consulta.DetalhamentoDeConsultaDTO;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.service.ConsultaService;

// @SpringBootTest: sobe o contexto completo da aplicação (controllers, security, etc.)
// Necessário aqui pois estamos testando o comportamento HTTP do controller
@SpringBootTest
// @AutoConfigureMockMvc: injeta o MockMvc para simular requisições HTTP sem subir servidor real
@AutoConfigureMockMvc
// @AutoConfigureJsonTesters: habilita o JacksonTester para serializar/desserializar JSON nos testes
@AutoConfigureJsonTesters
// @ActiveProfiles("test"): ativa o application-test.properties, apontando para o banco de testes
@ActiveProfiles("test")
public class ConsultaControllerTest {

  // MockMvc: simula chamadas HTTP ao controller (GET, POST, PUT, DELETE...)
  @Autowired
  private MockMvc mvc;

  // JacksonTester: converte objetos Java em JSON e vice-versa dentro dos testes
  @Autowired
  private JacksonTester<AgendamentoDeConsultaDTO> agendamentoDeConsultaDTOJson;

  @Autowired
  private JacksonTester<DetalhamentoDeConsultaDTO> detalhamentoDeConsultaDTOJson;

  // @MockitoBean: cria um "dublê" do ConsultaService.
  // Em vez de chamar o serviço real (que acessaria o banco), o Mockito controla o retorno.
  // Isso isola o teste: estamos testando APENAS o controller, não o serviço.
  @MockitoBean
  private ConsultaService consultaService;

  @Test
  @DisplayName("Deveria devolver código HTTP 400 quando informações estão inválidas")
  // @WithMockUser: simula um usuário autenticado, pois a API exige autenticação JWT
  @WithMockUser
  void agendar_cenario1() throws Exception {
    // Envia POST /consultas sem body — o controller deve retornar 400 (Bad Request)
    // pois os campos @NotNull do DTO não serão satisfeitos
    var response = mvc.perform(post("/consultas"))
        .andReturn()
        .getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  @DisplayName("Deveria devolver código HTTP 200 quando informações estão válidas")
  @WithMockUser
  void agendar_cenario2() throws Exception {
    var data = LocalDateTime.now().plusHours(1);
    var especialidade = Especialidade.CARDIOLOGIA;

    // Objeto que o mock vai retornar quando consultaService.agendar() for chamado.
    // Usamos Long literals (21L, 51L) — sem o "L" seriam int, incompatível com o tipo Long do record.
    var detalhamentoDTO = new DetalhamentoDeConsultaDTO(null, 21L, 51L, data);

    // Configura o mock: "quando agendar() for chamado com qualquer argumento, retorne detalhamentoDTO"
    // Isso evita acessar o banco real e torna o teste previsível e rápido
    when(consultaService.agendar(any())).thenReturn(detalhamentoDTO);

    var response = mvc.perform(post("/consultas")
        .contentType(MediaType.APPLICATION_JSON)
        // Converte o DTO de agendamento para JSON e envia no body da requisição
        .content(agendamentoDeConsultaDTOJson.write(
            new AgendamentoDeConsultaDTO(21L, 51L, data, especialidade)).getJson()))
        .andReturn()
        .getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    // Serializa o DTO esperado para JSON e compara com o body da resposta
    // Correção: write() recebe o objeto; .getJson() é chamado no resultado — estava invertido/errado
    var jsonEsperado = detalhamentoDeConsultaDTOJson.write(detalhamentoDTO).getJson();

    assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
  }
}
