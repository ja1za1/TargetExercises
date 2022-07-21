package exercicios.resolucao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;


public class ExerciciosTarget {
	
	//CONSTANTES PARA EXERCICIOFATURAMENTO2().
	private final static int SP = 0,
							 RJ = 1,
							 MG = 2,
							 ES = 3,
							 OUTROS = 4;
							 

	public static void main(String[] args) {
		ExercicioFibonacci();
		
		//Para obter os dados do arquivo JSON, estou utilizando a API JSON.simple.
		
		ExercicioFaturamento1();
		
		ExercicioFaturamento2();
		
		InverteCaracterString();
	}

	private static void InverteCaracterString() {
		String bomDia = "bom Dia";
		String stringInvertida = "";
		for(int i = 0; i < bomDia.length(); i++) {
			stringInvertida += bomDia.charAt((bomDia.length()-1)-i);
		}
		System.out.println("\n\nString "+bomDia+" invertida:\n\n"+stringInvertida);
		
	}

	private static void ExercicioFaturamento2() {
		
		Double[] faturamentoEstados = {67836.43, 36678.66, 29229.88, 27165.48, 19849.53};
		Double totalMes = calcularFaturamentoTotalMes(faturamentoEstados);
		calcularPorcentagemEstado(faturamentoEstados, totalMes);
		
	}

	private static void calcularPorcentagemEstado(Double[] faturamentoEstados, Double totalMes) {
		Double[] porcentagemPorEstado = new Double[faturamentoEstados.length];
		for(int i = 0; i < faturamentoEstados.length; i++) {
			//Multiplica o valor do faturamento por 100 e divide pelo total faturado no mês para obter-se a %
			porcentagemPorEstado[i] = faturamentoEstados[i] * 100 / totalMes;
		}
		System.out.printf("Porcentagem do estado de São Paulo = %.2f%%"
						+"\nPorcentagem do estado do Rio de Janeiro = %.2f%%"
						+"\nPorcentagem do estado de Minas Gerais = %.2f%%"
						+"\nPorcentagem do estado do Espirito Santo = %.2f%%"
						+"\nPorcentagem dos outros estados = %.2f%%",porcentagemPorEstado[SP],porcentagemPorEstado[RJ],
						porcentagemPorEstado[MG],porcentagemPorEstado[ES],porcentagemPorEstado[OUTROS]);
	}
	

	private static Double calcularFaturamentoTotalMes(Double[] faturamentoEstados) {
		Double valorTotal = 0D;
		for(Double valorFaturamento : faturamentoEstados) {
			valorTotal += valorFaturamento;
		}
		return valorTotal;
	}

	private static void ExercicioFaturamento1() {
		//Bloco try-catch para tratar as exceções geradas pelo parse().
		try {
			//Fazendo o parser do arquivo json para variavel objetoJson e convertendo essa variavel em um arrayJson
			
			Object objetoJson = new JSONParser().parse(new FileReader("resources/dados.json"));
			JSONArray arrayJson = (JSONArray) objetoJson;
			
			//Pegando todos os dados do arrayJson e convertendo para String e formatando os dados para que seja possível pegar os campos
			//de valor.
			
			String dadosJson = arrayJson.toString();
			String[] dadosJsonFormatados = dadosJson.split("}");
			
			//Inicializando o valores de faturamento com a quantidade de posicoes que o 
			//arrayJson possui (no caso, 30 posições, uma para cada dia).
			
			Double[] valoresFaturamento = new Double[arrayJson.size()];
			
			//Dividindo cada string formatada até conseguir pegar SOMENTE o valor que refere-se ao valor de faturamento do dia
			
			for(int i = 0; i < arrayJson.size(); i++) {
				valoresFaturamento[i] = Double.parseDouble(dadosJsonFormatados[i].split(":")[1].split(",")[0]);
			}
			System.out.printf("\n\nO valor de menor faturamento no mes foi: R$ %.2f",menorFaturamento(valoresFaturamento));
			System.out.printf("\n\nO valor de maior faturamento no mes foi: R$ %.2f",maiorFaturamento(valoresFaturamento));
			System.out.printf("\n\nQuantidade de dias em que o valor de faturamento foi maior que a media do mes: %02d dias",
							 numDiasFaturamentoMaiorMedia(valoresFaturamento));
			
			
		} catch (FileNotFoundException e) {
			System.out.println("O arquivo nao foi encontrado!!!");
		} catch (IOException e) {
			System.out.println("Houve um erro na entrada/saida de dados!!!");
		} catch (ParseException e) {
			System.out.println("Houve um erro ao fazer o parser do arquivo!!!");
		}
	}

	
	//Função que retorna a quantidade de dias do mês em que o faturamento foi inferior a média mensal.
	
	private static int numDiasFaturamentoMaiorMedia(Double[] valores) {
		Double mediaFaturamentoMes = calcularMediaMes(valores);
		int numeroDiasFaturamentoMaiorMedia = 0;
		for(Double valor : valores) {
			if(valor > mediaFaturamentoMes) {
				numeroDiasFaturamentoMaiorMedia++;
			}
		}
		return numeroDiasFaturamentoMaiorMedia;
	}
	
	//Função que calcula a media por mes a partir do vetor de valores de faturamento mensal, nao levando em conta os dias que
	//o faturamento foi igual a 0.

	private static Double calcularMediaMes(Double[] valores) {
		Double somaTotalValor = 0D;
		int totalZero = 0;
		for(Double valor : valores) {
			if(valor > 0D)
				somaTotalValor += valor;
			else {
				totalZero++;
			}
		}
		return somaTotalValor / (valores.length - totalZero);
	}

	private static Double maiorFaturamento(Double[] valores) {
		Double maiorValor = valores[0];
		for(Double valor : valores) {
			if(valor > maiorValor) {
				maiorValor = valor;
			}
		}
		return maiorValor;
	}

	private static Double menorFaturamento(Double[] valores) {
		Double menorValor = valores[0];
		for (Double valor : valores){
			if(valor < menorValor) {
				menorValor = valor;
			}
		}
		return menorValor;
	}

	private static void ExercicioFibonacci() {
		//Adicionando bloco try catch para caso o usuário insira um número diferente de int
		
		try(Scanner entradaNumero = new Scanner(System.in)){
			System.out.printf("Digite um numero inteiro: ");
			int numeroDigitado = entradaNumero.nextInt();
			boolean pertence = false;
			long numeroFibonacci = 0;
			//enquanto o numero de fibonacci for menor que o numero que o usuario digitou, eu verifico se o numero de fibonacci corresponde
			//ao valor especificado pelo usuario.
			
			for(int i = 0; numeroFibonacci < numeroDigitado; i++) {
				numeroFibonacci = fibo(i);
				if(numeroFibonacci == numeroDigitado) {
					pertence = true;					
					break;
				}
			}
			if(pertence) {
				System.out.println("O numero digitado pertence a sequencia de Fibonacci!!!");
			}
			else {
				System.out.println("O numero digitado nao pertence a sequencia de Fibonacci");
			}
			return;
			
		} catch (Exception e) {
			System.out.println("O valor digitado nao e um numero inteiro!!!");
		}
		
	}
	
	//função recursiva simples que retorna a soma dos n numeros da sequencia de fibonacci
	
	private static long fibo(int n) {
        if (n < 2) {
            return n;
        }
        else {
            return fibo(n - 1) + fibo(n - 2);
        }
    }

}//class ExerciciosTarget
