package rafaeldeluca.com.crudclient.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rafaeldeluca.com.crudclient.dto.ClientDTO;
import rafaeldeluca.com.crudclient.entities.Client;
import rafaeldeluca.com.crudclient.repositories.ClientRepository;

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
		Client entity = object.get();
		ClientDTO dto = new ClientDTO(entity);
		return  dto;
	}
	

}
