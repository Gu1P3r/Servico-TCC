package tcc.PluriGame.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuario_roles")
public class UsuarioRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userrolesid")
	private long id;

	@ManyToOne
	@JoinColumn(name = "usuario")
	private Usuarios usuario;

	@ManyToOne
	@JoinColumn(name = "role")
	private Role role;
	
	
	public UsuarioRole() {
		
	}
	
	public UsuarioRole(Usuarios usuario, Role role) {
		this.usuario = usuario;
		this.role = role;
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}
