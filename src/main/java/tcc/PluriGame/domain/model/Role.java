	package tcc.PluriGame.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "role")
public class Role {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private Long id;
 
	@NaturalId
    @Enumerated(EnumType.STRING)
    @Column(name="name")
    private RoleName name;
 
    public Role() {}
 
    public Role(RoleName name) {
        this.name = name;
    }
 
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public RoleName getName() {
        return name;
    }
 
    public void setName(RoleName name) {
        this.name = name;
    }
}

