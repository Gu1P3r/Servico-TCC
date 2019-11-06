package tcc.PluriGame.domain.resource;


import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;


@RestController
@RequestMapping("/provasanteriores") // rota de acesso	
public class ProvasAnterioresResource {

	
	@Value("${plurigame.provas.raiz}")
	private String raiz;

	@Value("${plurigame.provas.diretorio}")
	private String diretorioPdf;
	
	
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

	
    public HttpEntity<byte[]> baixar(String nomeArquivo) throws IOException {

        byte[] arquivo = Files.readAllBytes( Paths.get("diretorioPdf"+nomeArquivo+".pdf") );

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Disposition", "attachment;filename=\""+nomeArquivo+".pdf\"");

        HttpEntity<byte[]> entity = new HttpEntity<byte[]>( arquivo, httpHeaders);

        return entity;
    }
	
	
	@CrossOrigin(origins = "*")
	// @PreAuthorize("hasAnyRole('ADMIN', 'PROF')")
	@RequestMapping(value="/upload", method= RequestMethod.POST)
	public void upload(@RequestParam MultipartFile pdf) {
		this.salvarArquivo(pdf);
		System.out.println("Chegando aqui");
	}
	
	@CrossOrigin(origins = "*")
	// @PreAuthorize("hasAnyRole('ALUNO', 'ADMIN', 'PROF')")
	@GetMapping(value = "/dados")
	public List<String> getProvas() {
		
		List<String> lstProvas = new ArrayList<String>();
		
	    File file = new File("C:/Arquivos/provasanteriores");
	    File[] arquivos = file.listFiles();
	  
	        for (File arquivo : arquivos) {
	        	
	        	if(!lstProvas.contains(arquivo.getName())) {
	        		
	        		lstProvas.add(arquivo.getName());
	        	}
	        }
	        
	        System.out.println(lstProvas);
			return lstProvas;
	}
	
	
	@CrossOrigin(origins = "*")
	// @PreAuthorize("hasAnyRole('ALUNO', 'ADMIN', 'PROF')")
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(String nomeArquivo) throws IOException {
		this.baixar(nomeArquivo);
	}
}
