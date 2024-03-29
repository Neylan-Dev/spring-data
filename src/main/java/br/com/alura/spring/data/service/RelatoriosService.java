package br.com.alura.spring.data.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatoriosService {

	private Boolean system = true;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private final FuncionarioRepository funcionarioRepository;
	
	public RelatoriosService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}
	
	
	public void inicial(Scanner scanner) {
		while(system) {
			System.out.println("Qual acao de cargo deseja executar");
			System.out.println("0 - Sair");
			System.out.println("1 - Buscar funcionario por nome");
			System.out.println("2 - Buscar funcionario por nome, data da contratação e salario");
			System.out.println("3 - Buscar funcionario por data da contratação");
			System.out.println("4 - Listar os salarios de todos os funcionarios");
			
			int action = scanner.nextInt();
			if(action == 1) {
				buscarFuncionarioNome(scanner);
			}
			else if(action == 2) {
				buscaFuncionarioNomeSalarioMaiorData(scanner);
			}
			else if(action == 3) {
				buscaFuncionarioDataContratacao(scanner);
			}
			else if(action == 4) {
				pesquisaFuncionarioSalario();
			}
			else {
				system = false;
			}			
		}
	}

	private void pesquisaFuncionarioSalario() {
		List<FuncionarioProjecao> list = this.funcionarioRepository.findFuncionarioSalario();
		list.forEach(f->System.out.println("Funcionario: Id: "+f.getId()+" | Nome: "+f.getNome()+" | Salario: "+f.getSalario()));
	}


	private void buscaFuncionarioDataContratacao(Scanner scanner) {
		System.out.println("Qual data da contratação deseja pesquisar");
		String dataContratacao = scanner.next();
		LocalDate localDate = LocalDate.parse(dataContratacao, formatter);
		List<Funcionario> list = funcionarioRepository.findDataContratacaoMaior(localDate);
		list.forEach(System.out::println);
	}


	private void buscarFuncionarioNome(Scanner scanner) {
		System.out.println("Qual nome deseja pesquisar");
		String nome = scanner.next();
		List<Funcionario> list = this.funcionarioRepository.findByNome(nome);
		list.forEach(System.out::println);
	}
	
	private void buscaFuncionarioNomeSalarioMaiorData(Scanner scanner) {
		System.out.println("Qual nome deseja pesquisar");
		String nome = scanner.next();
		System.out.println("Qual data da contratação deseja pesquisar");
		String dataContratacao = scanner.next();
		LocalDate localDate = LocalDate.parse(dataContratacao, formatter);
		System.out.println("Qual salario deseja pesquisar");
		Double salario = scanner.nextDouble();
		List<Funcionario> list = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome, salario, localDate);
		list.forEach(System.out::println);
	}
	
}
