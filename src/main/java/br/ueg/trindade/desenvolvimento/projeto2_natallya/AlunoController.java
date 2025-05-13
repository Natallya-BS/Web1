package br.ueg.trindade.desenvolvimento.projeto2_natallya;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/alunos")
    public String getAlunos(Model model) {
        model.addAttribute("alunos", alunoRepository.findAll());
        model.addAttribute("mensagem", "Todos os alunos cadastrados");
        return "alunos.html";
    }

    @GetMapping("/alunos/create")
    public String getCreate() {
        return "aluno-create.html";
    }

    @PostMapping("/alunos/create")
    public String postCreate(@RequestParam String nome, @RequestParam String email) {
        Aluno aluno = new Aluno(nome, email);
        alunoRepository.save(aluno);
        return "redirect:/alunos";
    }

    @GetMapping("/alunos/update/{id}")
    public String getUpdate(@PathVariable Integer id, Model model) {
        Optional<Aluno> alunoOpt = alunoRepository.findById(id);
        if (alunoOpt.isPresent()) {
            model.addAttribute("aluno", alunoOpt.get());
            return "aluno-update.html";
        }
        return "redirect:/alunos";
    }

    @PostMapping("/alunos/update")
    public String postUpdate(@RequestParam Integer id, @RequestParam String nome, @RequestParam String email) {
        Optional<Aluno> alunoOpt = alunoRepository.findById(id);
        if (alunoOpt.isPresent()) {
            Aluno aluno = alunoOpt.get();
            aluno.setNome(nome);
            aluno.setEmail(email);
            alunoRepository.save(aluno);
        }
        return "redirect:/alunos";
    }

    @GetMapping("/alunos/delete/{id}")
    public String getDelete(@PathVariable Integer id, Model model) {
        Optional<Aluno> alunoOpt = alunoRepository.findById(id);
        if (alunoOpt.isPresent()) {
            model.addAttribute("aluno", alunoOpt.get());
            return "aluno-delete.html";
        }
        return "redirect:/alunos";
    }

    @PostMapping("/alunos/delete")
    public String postDelete(@RequestParam Integer id) {
        alunoRepository.deleteById(id);
        return "redirect:/alunos";
    }
}
