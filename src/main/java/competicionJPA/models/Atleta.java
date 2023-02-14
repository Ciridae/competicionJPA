package competicionJPA.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Atletas")
public class Atleta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "Nombre")
	private String nombre;

	@Column(name = "Edad")
	private int edad;

	@Column(name = "Genero")
	private String genero;

	@Column(name = "Press_banca")
	private int pressBanca;

	@Column(name = "Peso_muerto")
	private int pesoMuerto;

	@Column(name = "Sentadilla")
	private int sentadilla;

	@Column(name = "Total")
	private int total;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public int getPressBanca() {
		return pressBanca;
	}

	public void setPressBanca(int pressBanca) {
		this.pressBanca = pressBanca;
	}

	public int getPesoMuerto() {
		return pesoMuerto;
	}

	public void setPesoMuerto(int pesoMuerto) {
		this.pesoMuerto = pesoMuerto;
	}

	public int getSentadilla() {
		return sentadilla;
	}

	public void setSentadilla(int sentadilla) {
		this.sentadilla = sentadilla;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Atleta " + this.id + ": " + this.nombre + ", " + this.edad + " años, " + this.genero + ", Press Banca = "
				+ this.pressBanca + ", Peso Muerto = " + this.pesoMuerto + ", Sentadilla = " + this.sentadilla
				+ ", Total = " + this.total;
	}

}
