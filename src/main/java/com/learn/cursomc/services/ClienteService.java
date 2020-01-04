package com.learn.cursomc.services;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.learn.cursomc.domain.Cidade;
import com.learn.cursomc.domain.Cliente;
import com.learn.cursomc.domain.Endereco;
import com.learn.cursomc.domain.enums.Perfil;
import com.learn.cursomc.domain.enums.TipoCliente;
import com.learn.cursomc.dto.ClienteDTO;
import com.learn.cursomc.dto.ClienteNewDTO;
import com.learn.cursomc.repositories.ClienteRepository;
import com.learn.cursomc.repositories.EnderecoRepository;
import com.learn.cursomc.security.UserSS;
import com.learn.cursomc.services.exceptions.AuthorizationException;
import com.learn.cursomc.services.exceptions.DataIntegrityException;
import com.learn.cursomc.services.exceptions.ObjectNotFoundException;
import com.learn.cursomc.utils.Util;

@Service
public class ClienteService {
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository cli;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix-client-profile}")
	private String prefixClientProfile;
	
	@Value("${img.profile-size}")
	private Integer profileSize;
	
	public Cliente find(Integer id_busca) throws ObjectNotFoundException, AuthorizationException {
		UserSS user = UserService.authenticated();
		if (Util.isNull(user) || (!user.hasRole(Perfil.ADMIN) && !id_busca.equals(user.getId()))) {
			throw new AuthorizationException("Acesso negado. Este cliente não possue autorização para acessar este recurso.");
		}
		
		Optional<Cliente> cat = cli.findById(id_busca);
		// este sem tratamento -> return cat.orElse(null);
		// OR
		return cat.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id: " + id_busca + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = cli.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return cli.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		
		try {
			cli.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			e.printStackTrace();
			throw new DataIntegrityException("Não é possível excluir o cliente porque há pedidos relacionados.");
		}
	}
	
	public List<Cliente> findAll() {
		return cli.findAll();
	}
	
	public Cliente findByEmail(String email) {
		UserSS user = UserService.authenticated();
		if (Util.isNull(user) || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Cliente obj = cli.findByEmail(email);
		if (Util.isNull(obj)) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return cli.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail());
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (Util.isValidString(objDto.getTelefone2())) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (Util.isValidString(objDto.getTelefone3())) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) throws IOException {
		UserSS user = UserService.authenticated();
		if (Util.isNull(user)) {
			throw new AuthorizationException("Acesso negado.");
		}
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, profileSize);
		
		String fileName = prefixClientProfile + user.getId() + ".jpg";
		
		return s3Service.uploadFile(imageService.getImageInputStream(jpgImage, "jpg"), fileName, "image");
	}
}