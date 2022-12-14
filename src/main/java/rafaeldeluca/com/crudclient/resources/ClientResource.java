package rafaeldeluca.com.crudclient.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import rafaeldeluca.com.crudclient.dto.ClientDTO;
import rafaeldeluca.com.crudclient.services.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {
	
	@Autowired
	private ClientService service;
	
	//retornando um lista ordenada por nome com os dados mocados
	/*
	@GetMapping
	public ResponseEntity<List<Client>> findAll (Pageable pageable) {
		
		List<Client> listaDeClientes = new ArrayList<Client>();
		listaDeClientes.add(new Client(1L,"Rafael De Luca" ,"72128852072", 10.5000,Instant.parse("1991-08-23T10:30:00Z"),2));
		listaDeClientes.add(new Client(2L,"Claudia De Luca" ,"94812420059", 7.5000,Instant.parse("1985-07-20T12:44:00Z"),1));
		listaDeClientes.add(new Client(3L,"Julia da Silva" ,"74246421030", 3.8000,Instant.parse("2003-12-03T23:15:00Z"),4));
		Collections.sort(listaDeClientes);
		return ResponseEntity.ok().body(listaDeClientes);	
	}		
	
	@GetMapping
	public ResponseEntity<Page<ClientDTO>> findAll (Pageable pageable) {
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name"));
		Page<ClientDTO> page = service.findAllPaged(pageRequest);
		return ResponseEntity.ok().body(page);
	}		
	
	/*
	@GetMapping
	public ResponseEntity<Page <ClientDTO>> findAll (Pageable pageable) {
		
		Page<ClientDTO> paginatedList = service.findAllPaged(pageable);
		return ResponseEntity.ok().body(paginatedList);
		
	}
	*/
	
	@GetMapping
	public ResponseEntity<Page<ClientDTO>> findAll (
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue = "10") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue ="income" ) String orderBy,
			@RequestParam(value="direction", defaultValue = "DESC") String direction
			) {
		
		PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction),orderBy);
		Page<ClientDTO> pagina = service.findAllPaged(pageRequest);
		return ResponseEntity.ok().body(pagina);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> findByid(@PathVariable Long id) {
		ClientDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	// n??o retornar o c??digo 200, retornas o c??digo 201 de recurso criado
	// no cabe??alho colocar o caminho do objeto criado Headers / Location: http://localhost:8080/clients/11 
	
	@PostMapping
	public ResponseEntity<ClientDTO> insert(@RequestBody ClientDTO clientDTO) {
		
		clientDTO = service.insertInto(clientDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(clientDTO.getId()).toUri();	
		
		return ResponseEntity.created(uri).body(clientDTO);
		
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> update(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
		
		clientDTO = service.updateClient(id, clientDTO);		
		return ResponseEntity.ok().body(clientDTO);
	}
	
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> detele (@PathVariable Long id) {
		service.deleteClient(id);
		return ResponseEntity.noContent().build(); //codigo http 204 = exclui com sucesso e avisa a aplica????o que n??o tem corpo na resposta
		
	}
	
	@DeleteMapping()
	public ResponseEntity<Void> deteleAll () {
		service.deleteAll();
		return ResponseEntity.noContent().build(); 
	}
	

}
