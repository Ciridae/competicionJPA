package competicionJPA.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Records")
public class Record {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id_record")
	private int idRecord;

	@Column(name = "Genero")
	private String genero;

	@Column(name = "Categoria")
	private String categoria;

	@Column(name = "Press_banca")
	private int pressBanca;

	@Column(name = "Peso_muerto")
	private int pesoMuerto;

	@Column(name = "Sentadilla")
	private int sentadilla;

	@Column(name = "Total")
	private int total;

	public int getIdRecord() {
		return idRecord;
	}

	public void setIdRecord(int idRecord) {
		this.idRecord = idRecord;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
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

}
