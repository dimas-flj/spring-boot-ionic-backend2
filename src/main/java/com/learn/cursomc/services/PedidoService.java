package com.learn.cursomc.services;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.learn.cursomc.domain.Cliente;
import com.learn.cursomc.domain.ItemPedido;
import com.learn.cursomc.domain.PagamentoComBoleto;
import com.learn.cursomc.domain.Pedido;
import com.learn.cursomc.domain.enums.EstadoPagamento;
import com.learn.cursomc.repositories.ItemPedidoRepository;
import com.learn.cursomc.repositories.PagamentoRepository;
import com.learn.cursomc.repositories.PedidoRepository;
import com.learn.cursomc.security.UserSS;
import com.learn.cursomc.services.exceptions.AuthorizationException;
import com.learn.cursomc.services.exceptions.ObjectNotFoundException;
import com.learn.cursomc.utils.Util;

@Service
public class PedidoService {
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id_busca) throws ObjectNotFoundException {
		Optional<Pedido> cat = pedidoRepository.findById(id_busca);
//		este sem tratamento -> return cat.orElse(null);
		// OR 
		return cat.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id: " + id_busca + ", Tipo: " + Pedido.class.getName()));
	}
	
	public Pedido insert(Pedido obj) throws IOException {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		obj = pedidoRepository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		
		itemPedidoRepository.saveAll(obj.getItens());
		
		emailService.sendOrderConfirmationHtmlEmail(obj);
		
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (Util.isNull(user)) {
			throw new AuthorizationException("Acesso negado. Este cliente não possue autoização para acessar este recurso.");
		}
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		return pedidoRepository.findByCliente(cliente, pageRequest);
	}
}