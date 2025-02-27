package com.gabrielboliveira.cadastro.service;

import com.gabrielboliveira.cadastro.model.EnderecoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class ViaCepService {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    private final RestTemplate restTemplate;

    // Injeção de dependência do RestTemplate
    @Autowired
    public ViaCepService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*
     * Buscar o endereço pelo CEP utilizando a API do ViaCEP.
     * @param cep o CEP a ser consultado.
     * @return um Optional contendo o EndereçoDTO com os dados do endereço, ou um Optional vazio se não encontrado.
     */
    public Optional<EnderecoDTO> buscarEndereco(String cep) {
        String url = UriComponentsBuilder.fromUriString(VIA_CEP_URL)
                .buildAndExpand(cep)
                .toUriString();

        try {
            ResponseEntity<ViaCepResponse> responseEntity = restTemplate.getForEntity(url, ViaCepResponse.class);

            // Verifica se o código de status HTTP é 200 OK
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                ViaCepResponse response = responseEntity.getBody();
                if (response != null && response.getLogradouro() != null) {
                    EnderecoDTO enderecoDTO = new EnderecoDTO(
                            cep,
                            response.getLogradouro(),
                            "",  // O número será preenchido pelo usuário
                            "",  // Complemento será preenchido pelo usuário
                            response.getBairro(),
                            response.getLocalidade(),
                            response.getUf()
                    );
                    return Optional.of(enderecoDTO);
                } else {
                    System.err.println("Erro: Não foi possível recuperar os dados do endereço.");
                    return Optional.empty();
                }
            } else {
                System.err.println("Erro: Resposta da API ViaCEP não foi bem-sucedida. Status: "
                        + responseEntity.getStatusCode());
                return Optional.empty();
            }
        } catch (Exception e) {
            System.err.println("Erro ao consultar ViaCEP: " + e.getMessage());
            return Optional.empty();
        }
    }

    //Classe interna para mapear a resposta da API ViaCEP.

    private static class ViaCepResponse {
        private String logradouro;
        private String bairro;
        private String localidade;
        private String uf;

        public String getLogradouro() {
            return logradouro;
        }

        public String getBairro() {
            return bairro;
        }

        public String getLocalidade() {
            return localidade;
        }

        public String getUf() {
            return uf;
        }
    }
}
