package dio.budgeting.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class FinanceTools {

    public record TransacaoRequest(String categoria, double valor) {}
    public record TransacaoResponse(boolean aprovada, String mensagem, double limiteRestante) {}

    @Bean
    @Description("Valida se uma transação financeira está dentro do limite diário permitido de R$ 500.00")
    public Function<TransacaoRequest, TransacaoResponse> validarLimiteTransacao() {
        return request -> {
            double limiteDiario = 500.00;
            if (request.valor() > limiteDiario) {
                return new TransacaoResponse(
                    false, 
                    "Transação rejeitada: O valor de R$ " + request.valor() + " excede o limite diário de R$ " + limiteDiario,
                    0.0
                );
            }
            double restante = limiteDiario - request.valor();
            return new TransacaoResponse(
                true, 
                "Transação aprovada com sucesso na categoria " + request.categoria(),
                restante
            );
        };
    }
}