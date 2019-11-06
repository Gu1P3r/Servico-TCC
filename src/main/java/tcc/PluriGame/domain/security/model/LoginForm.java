package tcc.PluriGame.domain.security.model;

 
public class LoginForm {
    
    private String email;
    
    private String senha;
    
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getSenha() {
        return senha;
    }
 
    public void setSenha(String password) {
        this.senha = password;
    }
}