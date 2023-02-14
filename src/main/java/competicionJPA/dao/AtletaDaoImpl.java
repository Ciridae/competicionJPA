package competicionJPA.dao;

import java.util.List;

import javax.persistence.EntityManager;

import competicionJPA.models.Atleta;

public class AtletaDaoImpl implements AtletaDao{
	
	private static EntityManager entity;

	public List<Atleta> consultarAtletasPorGeneroYCategoria(String condicion, String genero) {
		// TODO Auto-generated method stub
		return null;
	}

	public Atleta consultarAtletaPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void insertarAtleta(Atleta atleta) {
		// TODO Auto-generated method stub
		
	}

	public void actualizarAtleta(Atleta atleta) {
		// TODO Auto-generated method stub
		
	}

	public int consultarMaximoPeso(String ejercicio, String genero, String condicion) {
		// TODO Auto-generated method stub
		return 0;
	}

}
