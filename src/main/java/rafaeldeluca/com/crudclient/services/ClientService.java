package rafaeldeluca.com.crudclient.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rafaeldeluca.com.crudclient.dto.ClientDTO;
import rafaeldeluca.com.crudclient.entities.Client;
import rafaeldeluca.com.crudclient.repositories.ClientRepository;
import rafaeldeluca.com.crudclient.services.exceptions.ExcecaoRecursoNaoEncontrado;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;	
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		
		Page <Client> paginatedList = repository.findAll(pageRequest);
		return paginatedList.map(c -> new ClientDTO(c));
		
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		//retorno da busca nunca vai ser um objeto nulo
		Optional<Client> object = repository.findById(id);
		//Client entity = object.get();
		Client entity = object.orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Entidade não encontrada com o id de número "
				+ id ));
		ClientDTO dto = new ClientDTO(entity);
		return  dto;
	}
	
	@Transactional
	public ClientDTO insertInto(ClientDTO clientDTO) {
		
		Client entity = new Client();
		entity.setName(clientDTO.getName());
		entity.setCpf(clientDTO.getCpf());
		entity.setIncome(clientDTO.getIncome());
		entity.setBirthDate(clientDTO.getBirthDate());
		entity.setChildren(clientDTO.getChildren());
		entity = repository.save(entity);
		
		ClientDTO dto = new ClientDTO(entity);	
		
		return dto;
		
	}
	
	@Transactional
	public ClientDTO updateClient (Long id, ClientDTO clientDTO) {
		
		try {
			Client entity = repository.getOne(id);
			entity.setName(clientDTO.getName());
			entity.setCpf(clientDTO.getCpf());
			entity.setIncome(clientDTO.getIncome());
			entity.setBirthDate(clientDTO.getBirthDate());
			entity.setChildren(clientDTO.getChildren());
			entity = repository.save(entity);		
			ClientDTO dto = new ClientDTO(entity);
			return dto;
			
		} catch (EntityNotFoundException erro) {
			throw new ExcecaoRecursoNaoEncontrado("Id de número: " + id + " não foi encontrado.\n"
					+ "Dados do cliente não foram alterados.");
		}
		
	}
	

}
