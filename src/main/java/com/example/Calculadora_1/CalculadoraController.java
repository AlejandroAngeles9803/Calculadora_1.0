package com.example.Calculadora_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CalculadoraController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute("user") User user, BindingResult bindingResult) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser == null || !existingUser.getPassword().equals(user.getPassword())) {
            bindingResult.rejectValue("email", "error.user", "Correo Electronico o contraseña erronea");
        }
        if (bindingResult.hasErrors()) {
            return "login";
        }
        return "redirect:/calculadora";
    }
    private List<Double> numeros = new ArrayList<>();
    private String operacion = "";

    @GetMapping("/calculadora")
    public String mostrarCalculadora(Model model) {
        model.addAttribute("numeros", numeros);
        model.addAttribute("operacion", operacion);
        return "index";
    }

    @PostMapping("/calcular")
    public String calcular(@RequestParam("numero") String numero) {
        if (!numero.equals("")) {
            numeros.add(Double.parseDouble(numero));
        }

        if (numeros.size() >= 2 && !operacion.equals("")) {
            double resultado = 0;

            switch (operacion) {
                case "suma":
                    resultado = (numeros.get(0) + numeros.get(1));
                    break;
                case "-":
                    resultado = numeros.get(0) - numeros.get(1);
                    break;
                case "*":
                    resultado = numeros.get(0) * numeros.get(1);
                    break;
                case "/":
                    resultado = numeros.get(0) / numeros.get(1);
                    break;
            }

            numeros.clear();
            numeros.add(resultado);
            operacion = "";
        }

        return "redirect:/calculadora";
    }

    @PostMapping("/operacion")
    public String agregarOperacion(@RequestParam("op") String op) {
        if (numeros.size() > 0) {
            operacion = op;
        }

        return "redirect:/calculadora";
    }

    @PostMapping("/limpiar")
    public String limpiar() {
        numeros.clear();
        operacion = "";
        return "redirect:/calculadora";
    }


}
