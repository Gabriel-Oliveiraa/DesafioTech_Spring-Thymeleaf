package com.gabrielboliveira.cadastro.controller;

import com.gabrielboliveira.cadastro.model.DadosPessoaisDTO;
import com.gabrielboliveira.cadastro.model.EnderecoDTO;
import com.gabrielboliveira.cadastro.service.ViaCepService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
        return "cadastro-dados";
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
        return "cadastro-endereco";
    }

    @Autowired
    private HttpSession session;

    @PostMapping("/endereco")
    public String salvarEndereco(@ModelAttribute @Valid EnderecoDTO dto, Model model) {

        if (dto.getCep() != null && (dto.getLogradouro() == null || dto.getLogradouro().isEmpty())) {
            Optional<EnderecoDTO> enderecoApi = viaCepService.buscarEndereco(dto.getCep());
            if (enderecoApi.isPresent()) {
                EnderecoDTO enderecoObtido = enderecoApi.get();
                dto.setLogradouro(enderecoObtido.getLogradouro());
                dto.setBairro(enderecoObtido.getBairro());
                dto.setCidade(enderecoObtido.getCidade());
                dto.setEstado(enderecoObtido.getEstado());
            }
        }

        System.out.println("Dados na sessão: " + session.getAttribute("dadosPessoais"));
        System.out.println("Endereço na sessão: " + session.getAttribute("endereco"));
        model.addAttribute("endereco", dto);
        return "redirect:/cadastro/confirmacao";
    }

    @PostMapping("/confirmacao")
    public String exibirConfirmacao(@ModelAttribute("dadosPessoais") DadosPessoaisDTO dadosPessoais,
                                    @ModelAttribute("endereco") EnderecoDTO endereco,
                                    Model model) {

        model.addAttribute("dadosPessoais", dadosPessoais);
        model.addAttribute("endereco", endereco);

        return "confirmacao";  // Página de confirmação
    }

    @PostMapping("/finalizar")
    public String finalizarCadastro(SessionStatus status) {
        status.setComplete();
        return "sucesso";
    }
}
