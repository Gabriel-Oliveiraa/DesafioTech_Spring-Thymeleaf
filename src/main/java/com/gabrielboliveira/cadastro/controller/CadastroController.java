package com.gabrielboliveira.cadastro.controller;

import com.gabrielboliveira.cadastro.model.DadosPessoaisDTO;
import com.gabrielboliveira.cadastro.model.EnderecoDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cadastro")
public class CadastroController {

    private DadosPessoaisDTO dadosPessoais;
    private EnderecoDTO endereco;

    @GetMapping("/pessoais")
    public String exibirFormularioDadosPessoais(Model model) {
        model.addAttribute("dadosPessoais", new DadosPessoaisDTO());
        return "dadosPessoais";
    }

    @PostMapping("/pessoais")
    public String salvarDadosPessoais(@ModelAttribute @Valid DadosPessoaisDTO dto) {
        this.dadosPessoais = dto;
        return "redirect:/cadastro/endereco";
    }

    @GetMapping("/endereco")
    public String exibirFormularioEndereco(Model model) {
        model.addAttribute("endereco", new EnderecoDTO());
        return "endereco";
    }

    @PostMapping("/endereco")
    public String salvarEndereco(@ModelAttribute @Valid EnderecoDTO dto) {
        this.endereco = dto;
        return "redirect:/cadastro/confirmacao";
    }

    @GetMapping("/confirmacao")
    public String confirmarCadastro(Model model) {
        model.addAttribute("dadosPessoais", dadosPessoais);
        model.addAttribute("endereco", endereco);
        return "confirmacao";
    }
}
