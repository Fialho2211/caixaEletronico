package com.caixaeletronico.controllers;

import java.util.Arrays;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.caixaeletronico.adapter.RealParaDolarAdapter;
import com.caixaeletronico.chainOfResponsability.IHandler;
import com.caixaeletronico.chainOfResponsability.SenhaHandler;
import com.caixaeletronico.chainOfResponsability.TitularHandler;
import com.caixaeletronico.factories.ContaCorrenteUsuarioBronzeFactory;
import com.caixaeletronico.factories.ContaCorrenteUsuarioOuroFactory;
import com.caixaeletronico.factories.ContaCorrenteUsuarioPrataFactory;
import com.caixaeletronico.factories.ContaPoupancaUsuarioBronzeFactory;
import com.caixaeletronico.factories.ContaPoupancaUsuarioOuroFactory;
import com.caixaeletronico.factories.ContaPoupancaUsuarioPrataFactory;
import com.caixaeletronico.factories.UsuarioContaFactory;
import com.caixaeletronico.models.Conta;
import com.caixaeletronico.proxy.services.ContaServiceProxy;

import jakarta.servlet.http.HttpSession;
@Controller
public class ContaController {
	
	@Autowired   

    private ContaServiceProxy contaService;
	
    @GetMapping("/")
    public String index() {
        return "home";
    }
    
    @GetMapping("/home")
    public String home() {
        return "home";
    }
		
    @GetMapping("/account")
    public String account(@RequestParam Long accountId, @RequestParam String senha, Model model, HttpSession session) {
        Optional<Conta> contaOpt = contaService.getContaById(accountId);
        
        // Verifica se a senha foi passada como parâmetro
        if (senha == null || senha.equals("")) {
            // Se a senha não foi passada, tenta buscar da sessão
            senha = (String) session.getAttribute("senha");
        } else {
            // Se a senha foi passada, atualiza a sessão
            session.setAttribute("senha", senha);
        }
        
        if (contaOpt.isEmpty()) {
            model.addAttribute("error", "Número de conta inválido ou inexistente");
            return "home";  // Retorna à página de login com a mensagem de erro
        }
        
        // Autentica com a senha
        if (senha == null || !contaService.autenticar(accountId, senha)) {
            model.addAttribute("error", "Senha inválida! Tente novamente");
            return "home";  // Retorna à página de login com a mensagem de erro
        }
    
        Conta conta = contaOpt.get();
        model.addAttribute("account", conta);
        model.addAttribute("senha", senha);  // Adiciona a senha ao modelo
        return "account";  // Página onde você exibe as informações da conta
    }
	
	@GetMapping("/new_account")
    public String showNovaContaForm(Model model) {
        model.addAttribute("tiposConta", Arrays.asList("Poupanca", "Corrente"));
        model.addAttribute("tiposUsuario", Arrays.asList("Ouro", "Prata", "Bronze"));
        return "newAccount";
    }
	
	@PostMapping("/new_account")
    public String criarConta(@RequestParam String tipoConta, @RequestParam String tipoUsuario, @RequestParam String titular, @RequestParam String senha, Model model) {

        IHandler titularHandler = new TitularHandler();        
        IHandler senhaHandler =  new SenhaHandler();

        titularHandler.setNext(senhaHandler);

        if(!titularHandler.handle(titular, senha)){
            model.addAttribute("error", "Não foi possível criar a conta! Tente novamente.");
            return "home";
        }

        else{
            UsuarioContaFactory factory = getFactory(tipoConta, tipoUsuario);
            Conta conta = contaService.criarContaComUsuario(factory, senha, titular);
            model.addAttribute("conta", conta);
            return "redirect:/account?accountId=" + conta.getId() + "&senha=" + conta.getSenha();
        }
    }
	
    @GetMapping("/deposit")
    public String showDepositPage(@RequestParam Long accountId, Model model) {
        model.addAttribute("accountId", accountId);
        return "deposit";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam Long accountId, @RequestParam double amount) {  
        Optional <Conta> contaOpt = contaService.getContaById(accountId);

        Conta conta = contaOpt.get();
        
        contaService.depositar(accountId, amount, false);       
        return "redirect:/account?accountId=" + accountId + "&senha=" + conta.getSenha();
    }
	
    @GetMapping("/withdraw")
    public String showWithdrawPage(@RequestParam Long accountId, Model model) {
        model.addAttribute("accountId", accountId);
        return "withdraw";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam Long accountId, @RequestParam double amount) {
        Optional <Conta> contaOpt = contaService.getContaById(accountId);

        Conta conta = contaOpt.get();

        contaService.sacar(accountId, amount);
        return  "redirect:/account?accountId=" + accountId + "&senha=" + conta.getSenha();
    }
		
    @GetMapping("/transfer")
    public String showTransferPage(@RequestParam Long accountId, Model model) {
        model.addAttribute("accountId", accountId);
        return "transfer";
    }

    @PostMapping("/convert")    
    @ResponseBody
    public double converterParaDolar(@RequestParam double amount){
        RealParaDolarAdapter adapter = new RealParaDolarAdapter();
        return adapter.convertToDolar(amount);
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam Long fromAccountId, @RequestParam Long toAccountId, @RequestParam double amount, RedirectAttributes redirectAttributes) {

        Conta conta = contaService.getContaById(fromAccountId).orElseThrow();
    
        if (fromAccountId.equals(toAccountId)) {            
            redirectAttributes.addFlashAttribute("error", "Não é possível enviar transferência para a mesma origem");
            return "redirect:/transfer?accountId=" + fromAccountId;
        }

        else if(contaService.getContaById(toAccountId).isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Não foi possível encontrar a conta destino. Verifique e tente novamente!");
            return "redirect:/transfer?accountId=" + fromAccountId;
        }
    
        else if (conta.getSaldo() == 0 || conta.getSaldo() < amount || amount < 0) {            
            redirectAttributes.addFlashAttribute("error", "Saldo insuficiente para a transferência.");
            return "redirect:/transfer?accountId=" + fromAccountId;
        }
    
        contaService.transferir(fromAccountId, toAccountId, amount);
        return "redirect:/account?accountId=" + fromAccountId + "&senha=" + conta.getSenha();
    }
         
	
    @GetMapping("/statement")
    public String statement(@RequestParam Long accountId, Model model) {    
    
        Optional<Conta> contaOpt = contaService.getContaById(accountId);
        
        if (contaOpt.isEmpty()) {
            model.addAttribute("error", "Número de conta inválido ou inexistente");
            return "home";  // Retorna à página de login ou outra página adequada com a mensagem de erro
        }
        
        Conta conta = contaOpt.get();       
    
        model.addAttribute("account", conta);
        model.addAttribute("transactions", contaService.emitirExtrato(accountId));
        return "statement";
    }
    
	
	private UsuarioContaFactory getFactory(String tipoConta, String tipoUsuario) {
        switch (tipoConta) {
            case "Poupanca":
                switch (tipoUsuario) {
                    case "Ouro": return new ContaPoupancaUsuarioOuroFactory();
                    case "Prata": return new ContaPoupancaUsuarioPrataFactory();
                    case "Bronze": return new ContaPoupancaUsuarioBronzeFactory();
                }
                break;
            case "Corrente":
                switch (tipoUsuario) {
                    case "Ouro": return new ContaCorrenteUsuarioOuroFactory();
                    case "Prata": return new ContaCorrenteUsuarioPrataFactory();
                    case "Bronze": return new ContaCorrenteUsuarioBronzeFactory();
                }
                break;
        }
        throw new IllegalArgumentException("Tipo de conta ou usuário inválido");
    }

}


