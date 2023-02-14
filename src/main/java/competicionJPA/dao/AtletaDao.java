package competicionJPA.dao;

import java.util.List;

import competicionJPA.models.Atleta;

public interface AtletaDao {

	public List<Atleta> consultarAtletasPorGeneroYCategoria(String condicion, String genero);

	public Atleta consultarAtletaPorId(int id);

	public void insertarAtleta(Atleta atleta);

	public void actualizarAtleta(Atleta atleta);
	
	public int consultarMaximoPeso(String ejercicio, String genero, String condicion);

}
