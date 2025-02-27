package com.gabrielboliveira.cadastro.controller;

import com.gabrielboliveira.cadastro.model.DadosPessoaisDTO;
import com.gabrielboliveira.cadastro.model.EnderecoDTO;
import com.gabrielboliveira.cadastro.service.ViaCepService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/cadastro")
@SessionAttributes({"dadosPessoais", "endereco"})
public class CadastroController {

    private final ViaCepService viaCepService;

    public CadastroController(ViaCepService viaCepService) {
        this.viaCepService = viaCepService;
    }

    @GetMapping("/pessoais")
    public String exibirFormularioDadosPessoais(Model model) {
        if (!model.containsAttribute("dadosPessoais")) {
            model.addAttribute("dadosPessoais", new DadosPessoaisDTO());
        }
        return "dadosPessoais";
    }

    @PostMapping("/pessoais")
    public String salvarDadosPessoais(@ModelAttribute @Valid DadosPessoaisDTO dto, Model model) {
        model.addAttribute("dadosPessoais", dto);
        return "redirect:/cadastro/endereco";
    }

    @GetMapping("/endereco")
    public String exibirFormularioEndereco(Model model) {
        if (!model.containsAttribute("endereco")) {
            model.addAttribute("endereco", new EnderecoDTO());
        }
        return "endereco";
    }

    @PostMapping("/endereco")
    public String salvarEndereco(@ModelAttribute @Valid EnderecoDTO dto, Model model) {
        // Busca o endereço via API de CEP caso esteja vazio
        if (dto.getCep() != null && (dto.getLogradouro() == null || dto.getLogradouro().isEmpty())) {
            Optional<EnderecoDTO> enderecoApi = viaCepService.buscarEndereco(dto.getCep());
            if (enderecoApi.isPresent()) {
                dto.setLogradouro(enderecoApi.get().getLogradouro());
                dto.setBairro(enderecoApi.get().getBairro());
                dto.setCidade(enderecoApi.get().getCidade());
                dto.setEstado(enderecoApi.get().getEstado());
            }
        }
        model.addAttribute("endereco", dto);
        return "redirect:/cadastro/confirmacao";
    }

    @GetMapping("/confirmacao")
    public String confirmarCadastro(Model model) {
        return "confirmacao";
    }

    @PostMapping("/finalizar")
    public String finalizarCadastro(SessionStatus status) {
        status.setComplete(); // Limpa os dados da sessão
        return "sucesso";
    }
}
