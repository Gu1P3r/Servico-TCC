package tcc.PluriGame.domain.resource;


import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tcc.PluriGame.domain.model.Noticias;
import tcc.PluriGame.domain.repository.NoticiasRepository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;


@RestController
@RequestMapping("/noticias") // rota de acesso
public class NoticiasResource {
	
	@Value("${plurigame.gabaritos.raiz}")
	private String raiz;

	@Value("${plurigame.gabaritos.diretorio}")
	private String diretorioPdf;
	
	private List<String> nomeArquivos = new ArrayList<String>();
	
	public void salvarArquivo(MultipartFile pdf) {
		
		this.salvar(this.diretorioPdf, pdf);
	}
	
	public void salvar(String diretorio, MultipartFile arquivo) {
		Path diretorioPath = Paths.get(this.raiz, diretorio);
		Path arquivoPath = diretorioPath.resolve(arquivo.getOriginalFilename());
		
		try {
			Files.createDirectories(diretorioPath);
			arquivo.transferTo(arquivoPath.toFile());			
		} catch (IOException e) {
			throw new RuntimeException("Problemas na tentativa de salvar arquivo.", e);
		}		
	}
	
	public List<String> listarArquivos() {
		
	    File file = new File("C:/Arquivos/gabaritos");
	    File[] arquivos = file.listFiles();
	    
	    try {
	        for (File arquivo : arquivos) {
	            this.nomeArquivos.add(arquivo.getName());
	        }
	        
	    }catch (Exception e){
	    	throw new RuntimeException("Problemas na tentativa de listar arquivos.", e);
	    }  
	    
	    return nomeArquivos;
	}
	
	public HttpEntity<byte[]> baixar(String nomeArquivo) throws IOException {

        byte[] arquivo = Files.readAllBytes( Paths.get("diretorioPdf"+nomeArquivo+".pdf") );

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Disposition", "attachment;filename=\""+nomeArquivo+".pdf\"");

        HttpEntity<byte[]> entity = new HttpEntity<byte[]>( arquivo, httpHeaders);

        return entity;
    }
	
	@Autowired
	private NoticiasRepository noticiasRepository;
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping(path = "/adicionar")
	public ResponseEntity<Noticias> addNoticias(@Valid @RequestBody Noticias ultimasNoticias) {
		Noticias ultimasNoticiasnew = noticiasRepository.save(ultimasNoticias);
		return ResponseEntity.status(HttpStatus.CREATED).body(ultimasNoticiasnew);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ALUNO', 'ADMIN', 'PROF')")
	@GetMapping(value = "/dados")
	public ResponseEntity<List<Noticias>> getNoticias() {
		List<Noticias> getAllNoticias = noticiasRepository.findAll();
		if (getAllNoticias.isEmpty()) {
			return new ResponseEntity<List<Noticias>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Noticias>>(getAllNoticias, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ALUNO', 'ADMIN', 'PROF')")
	@GetMapping(value = "/dados/{id}")
	public ResponseEntity<Noticias> getNoticia(@PathVariable("id") Long id) {
		Noticias getNoticias = noticiasRepository.findOne(id);
		if (getNoticias == null) {
			System.out.println("Noticia com id " + id + "não encontrado");
			return new ResponseEntity<Noticias>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Noticias>(getNoticias, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<Noticias> updateNoticias(@PathVariable("id") Long id, @Valid @RequestBody Noticias ultimasNoticias) {
		System.out.println("Procurando noticia com id " + id);

		Noticias noticiaAtual = noticiasRepository.findOne(id);

		if (noticiaAtual == null) {
			System.out.println("Noticia com id " + id + " não encontrado");
			return new ResponseEntity<Noticias>(HttpStatus.NOT_FOUND);
		}
		BeanUtils.copyProperties(ultimasNoticias, noticiaAtual, "id"); // observacao
		noticiaAtual.setNoticias(noticiaAtual.getNoticias());
		noticiasRepository.save(ultimasNoticias);
		return new ResponseEntity<Noticias>(ultimasNoticias, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<Noticias> deleteNoticia(@PathVariable("id") long id) {
		System.out.println("Procurando noticia de id " + id);

		Noticias deletarNoticia = noticiasRepository.findOne(id);
		if (deletarNoticia == null) {
			System.out.println("Não foi possivel deletar usuario com id: " + id + ", id não encontrado");
			return new ResponseEntity<Noticias>(HttpStatus.NOT_FOUND);
		}
		noticiasRepository.delete(id);
		return new ResponseEntity<Noticias>(HttpStatus.NO_CONTENT);
	}
	
	//upload de gabaritos
	
	@CrossOrigin(origins = "*")
	// @PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping(value="/upload")
	public void upload(@RequestParam MultipartFile pdf) {
		this.salvarArquivo(pdf);	
	}
	
	@CrossOrigin(origins = "*")
	//@PreAuthorize("hasAnyRole('ALUNO', 'ADMIN', 'PROF')")
	@GetMapping(value = "/gabaritos")
	public ResponseEntity<List<String>> getGabaritos() {
		List<String> getAllGabaritos = this.listarArquivos();
		if (getAllGabaritos.isEmpty()) {
			return new ResponseEntity<List<String>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<String>>(getAllGabaritos, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	//@PreAuthorize("hasAnyRole('ALUNO', 'ADMIN', 'PROF')")
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(String nomeArquivo) throws IOException {
		this.baixar(nomeArquivo);
	}

}
