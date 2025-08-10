package br.restaurante.service;

import br.restaurante.model.Cliente;
import br.restaurante.model.Endereco;
import br.restaurante.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ViaCepService viaCepService;

    public Cliente cadastrarCliente(Cliente cliente) {
        // --- Programação Defensiva: Validação de dados de entrada ---

        // 1. Validar campos obrigatórios
        if (cliente.getNome() == null || cliente.getNome().isBlank() ||
                cliente.getCpf() == null || cliente.getCpf().isBlank() ||
                cliente.getEmail() == null || cliente.getEmail().isBlank() ||
                cliente.getSenha() == null || cliente.getSenha().isBlank()) {
            throw new InputMismatchException("Os campos Nome, CPF, Email e Senha são obrigatórios.");
        }

        // 2. Limpar a formatação do CPF e validar o tamanho
        String cpfLimpo = cliente.getCpf().replaceAll("[^0-9]", "");
        if (cpfLimpo.length() != 11) {
            throw new InputMismatchException("O CPF deve conter 11 dígitos.");
        }
        cliente.setCpf(cpfLimpo);

        // 3. Validar formato do email (opcional, mas recomendado)
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
        if (!emailPattern.matcher(cliente.getEmail()).matches()) {
            throw new InputMismatchException("O formato do email é inválido.");
        }

        // 4. Verificar unicidade do CPF e Email
        Optional<Cliente> clienteExistenteCpf = clienteRepository.findByCpf(cliente.getCpf());
        if (clienteExistenteCpf.isPresent()) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }

        Optional<Cliente> clienteExistenteEmail = clienteRepository.findByEmail(cliente.getEmail());
        if (clienteExistenteEmail.isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }

        // 5. Buscar e preencher o endereço a partir do CEP
        if (cliente.getCep() != null && !cliente.getCep().isBlank()) {
            Endereco endereco = viaCepService.buscaEnderecoPorCep(cliente.getCep());
            cliente.setRua(endereco.rua());
            cliente.setBairro(endereco.bairro());
            cliente.setCidade(endereco.cidade());
            cliente.setEstado(endereco.estado());
            // O número do endereço deve vir do front-end e não é alterado aqui.
        }

        // 6. Criptografar a senha (Atenção: A biblioteca de criptografia deve ser adicionada)
        // ... (código para criptografia de senha, se você for usar) ...

        // 7. Salvar a entidade no banco de dados
        return clienteRepository.save(cliente);
    }

    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> atualizarCliente(Long id, Cliente clienteAtualizado) {
        return clienteRepository.findById(id).map(clienteExistente -> {
            clienteExistente.setNome(clienteAtualizado.getNome());
            clienteExistente.setTelefone(clienteAtualizado.getTelefone());
            clienteExistente.setEmail(clienteAtualizado.getEmail());
            clienteExistente.setRua(clienteAtualizado.getRua());
            clienteExistente.setBairro(clienteAtualizado.getBairro());
            clienteExistente.setCidade(clienteAtualizado.getCidade());
            clienteExistente.setEstado(clienteAtualizado.getEstado());
            clienteExistente.setCep(clienteAtualizado.getCep());
            clienteExistente.setNumero(clienteAtualizado.getNumero());
            clienteExistente.setAceitaProtecaoDados(clienteAtualizado.isAceitaProtecaoDados());
            clienteExistente.setAceitaMarketing(clienteAtualizado.isAceitaMarketing());
            clienteExistente.setAceitaAtendimento(clienteAtualizado.isAceitaAtendimento());
            // A senha não deve ser atualizada diretamente aqui por segurança
            // A lógica de atualização de senha deve ser um endpoint separado
            return clienteRepository.save(clienteExistente);
        });
    }

    public boolean deletarCliente(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}