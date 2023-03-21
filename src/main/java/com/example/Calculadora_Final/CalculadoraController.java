package com.example.Calculadora_Final;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CalculadoraController {
    private List<Double> numeros = new ArrayList<>();
    private String operacion = "";

    @GetMapping("/")
    public String mostrarCalculadora(Model model) {
        model.addAttribute("numeros", numeros);
        model.addAttribute("operacion", operacion);
        return "index.html";
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

        return "redirect:/";
    }

    @PostMapping("/operacion")
    public String agregarOperacion(@RequestParam("op") String op) {
        if (numeros.size() > 0) {
            operacion = op;
        }

        return "redirect:/";
    }

    @PostMapping("/limpiar")
    public String limpiar() {
        numeros.clear();
        operacion = "";
        return "redirect:/";
    }
}