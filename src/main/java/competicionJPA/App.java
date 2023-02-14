package competicionJPA;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;

import competicionJPA.models.Atleta;
import competicionJPA.models.Record;
import competicionJPA.utilities.JPAUtil;

public class App {

	public static void main(String[] args) {
		EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();
		Scanner s = new Scanner(System.in);
		boolean salir = false;
		int opcion;
		Atleta atleta;

		while (!salir) {
			mostrarOpcionesMenu();
			try {
				System.out.print("\tEscribe una de las opciones (1-6): ");
				opcion = Integer.parseInt(s.nextLine());

				switch (opcion) {
				case 1:
					insertarAtleta(entity, s);
					break;
				case 2:
					consultarAtletaPorId(entity, s);
					break;
				case 3:
					boolean salirClasificacion = false;
					int opcionClasificacion;
					do {
						mostrarOpcionesMenuClasificacion();
						System.out.print("\tEscribe una de las opciones (1-5): ");
						opcionClasificacion = Integer.parseInt(s.nextLine());

						switch (opcionClasificacion) {
						case 1:
							consultarAtletasPorGeneroYCategoria("Junior", "Hombre", entity);
							break;
						case 2:
							consultarAtletasPorGeneroYCategoria("Master", "Hombre", entity);
							break;
						case 3:
							consultarAtletasPorGeneroYCategoria("Junior", "Mujer", entity);
							break;
						case 4:
							consultarAtletasPorGeneroYCategoria("Master", "Mujer", entity);
							break;
						case 5:
							salirClasificacion = true;
							System.out.println("Has salido de la revisión de clasificación\n");
							break;
						default:
							System.out.println("Solo números entre 1 y 5\n");
							break;
						}
					} while (!salirClasificacion);
					break;
				case 4:
					System.out.print("\tID del atleta a eliminar: ");
					int idEliminar = Integer.parseInt(s.nextLine());

					atleta = entity.find(Atleta.class, idEliminar);
					if (atleta != null) {
						System.out.println(atleta);
						entity.getTransaction().begin();
						entity.remove(atleta);
						entity.getTransaction().commit();
						System.out.println("Atleta eliminado\n");
					} else {
						System.out.println("Atleta no encontrado\n");
					}
					break;
				case 5:
					System.out.print("\tID del atleta a actualizar: ");
					int idActualizar = Integer.parseInt(s.nextLine());

					atleta = entity.find(Atleta.class, idActualizar);
					actualizarAtleta(entity, s, atleta);
					break;
				case 6:
					salir = true;
					System.out.println("HASTA LUEGO!");
					break;
				default:
					System.out.println("Solo números entre 1 y 6\n");
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("Debes insertar un número\n");
			}
		}
		s.close();
		entity.close();
		JPAUtil.shutdown();
	}

	private static void actualizarRecords(EntityManager entity, Atleta atleta) {
		String genero = atleta.getGenero();
		String condicion;
		String categoria;
		if (atleta.getEdad() <= 32) {
			condicion = "<= 32";
			categoria = "Junior";
		} else {
			condicion = "> 32";
			categoria = "Master";
		}

		@SuppressWarnings("unchecked")
		List<Record> recordsDeCategoriaYGenero = entity
				.createQuery("SELECT records FROM Record records WHERE records.genero = '" + genero
						+ "' AND records.categoria = '" + categoria + "'")
				.getResultList();

		if (recordsDeCategoriaYGenero.isEmpty()) {
			int maximoPesoPressBanca = consultarMaximoPeso(entity, "pressBanca", genero, condicion);
			int maximoPesoMuerto = consultarMaximoPeso(entity, "pesoMuerto", genero, condicion);
			int maximoSentadilla = consultarMaximoPeso(entity, "sentadilla", genero, condicion);
			int maximoTotal = consultarMaximoPeso(entity, "total", genero, condicion);
			Record record = new Record();
			record.setGenero(genero);
			record.setCategoria(categoria);
			record.setPressBanca(maximoPesoPressBanca);
			record.setPesoMuerto(maximoPesoMuerto);
			record.setSentadilla(maximoSentadilla);
			record.setTotal(maximoTotal);
			entity.getTransaction().begin();
			entity.persist(record);
			System.out.println("Record insertado\n");
			entity.getTransaction().commit();
		} else {
			Record recordActual = recordsDeCategoriaYGenero.get(0);
			Boolean hayCambios = false;
			if (recordActual.getPressBanca() < atleta.getPressBanca()) {
				recordActual.setPressBanca(atleta.getPressBanca());
				hayCambios = true;
			}
			if (recordActual.getPesoMuerto() < atleta.getPesoMuerto()) {
				recordActual.setPesoMuerto(atleta.getPesoMuerto());
				hayCambios = true;
			}
			if (recordActual.getSentadilla() < atleta.getSentadilla()) {
				recordActual.setSentadilla(atleta.getSentadilla());
				hayCambios = true;
			}
			if (recordActual.getTotal() < atleta.getTotal()) {
				recordActual.setTotal(atleta.getTotal());
				hayCambios = true;
			}
			if (hayCambios) {
				entity.getTransaction().begin();
				entity.merge(recordActual);
				entity.getTransaction().commit();
				System.out.println("Record actualizado\n");
			}
		}
	}

	private static int consultarMaximoPeso(EntityManager entity, String ejercicio, String genero, String condicion) {
		List<?> pesos = entity.createQuery("SELECT atletas." + ejercicio + " FROM Atleta atletas "
				+ "WHERE atletas.genero = '" + genero + "' AND atletas.edad " + condicion + " ORDER BY 1 DESC")
				.getResultList();
		int maximoPeso = 0;

		if (!pesos.isEmpty()) {
			maximoPeso = (Integer) pesos.get(0);
		}

		return maximoPeso;
	}

	private static void mostrarOpcionesMenu() {
		System.out.println("1. Introducir Atleta");
		System.out.println("2. Buscar Atleta por ID");
		System.out.println("3. Revisar Clasificación");
		System.out.println("4. Eliminar Atleta");
		System.out.println("5. Modificar Puntuación");
		System.out.println("6. Salir");
	}

	private static void mostrarOpcionesMenuClasificacion() {
		System.out.println("1. Masculino Junior (<= 32)");
		System.out.println("2. Masculino Master (> 32)");
		System.out.println("3. Femenino Junior (<= 32)");
		System.out.println("4. Femenino Master (> 32)");
		System.out.println("5. Salir");
	}

	@SuppressWarnings("unchecked")
	private static void consultarAtletasPorGeneroYCategoria(String categoria, String genero, EntityManager entity) {
		List<Atleta> atletas = null;
		if (categoria == "Junior") {
			atletas = entity.createQuery(
					"SELECT atletas FROM Atleta atletas WHERE atletas.genero = ?1 AND atletas.edad <= 32 ORDER BY atletas.total DESC")
					.setParameter(1, genero).getResultList();
		} else if (categoria == "Master") {
			atletas = entity.createQuery(
					"SELECT atletas FROM Atleta atletas WHERE atletas.genero = ?1 AND atletas.edad > 32 ORDER BY atletas.total DESC")
					.setParameter(1, genero).getResultList();
		}
		if (atletas != null) {
			for (Atleta atletaEnConsulta : atletas) {
				System.out.println(atletaEnConsulta);
			}
			System.out.println();
		}
	}

	private static void consultarAtletaPorId(EntityManager entity, Scanner s) {
		System.out.print("\tID del atleta: ");
		int idConsultar = Integer.parseInt(s.nextLine());
		Atleta atletaPorId = entity.find(Atleta.class, idConsultar);
		if (atletaPorId != null) {
			System.out.println(atletaPorId + "\n");
		} else {
			System.out.println("Atleta no encontrado\n");
		}
	}

	private static void actualizarAtleta(EntityManager entity, Scanner s, Atleta atleta) {
		if (atleta != null) {
			int pressBancaNueva;
			int pesoMuertoNueva;
			int sentadillaNueva;
			int totalNueva;

			System.out.print("\tPress Banca: ");
			pressBancaNueva = Integer.parseInt(s.nextLine());
			System.out.print("\tPeso Muerto: ");
			pesoMuertoNueva = Integer.parseInt(s.nextLine());
			System.out.print("\tSentadilla: ");
			sentadillaNueva = Integer.parseInt(s.nextLine());
			totalNueva = pressBancaNueva + pesoMuertoNueva + sentadillaNueva;

			atleta.setPressBanca(pressBancaNueva);
			atleta.setPesoMuerto(pesoMuertoNueva);
			atleta.setSentadilla(sentadillaNueva);
			atleta.setTotal(totalNueva);

			entity.getTransaction().begin();
			entity.merge(atleta);
			entity.getTransaction().commit();
			System.out.println("Atleta actualizado\n");
			actualizarRecords(entity, atleta);
		} else {
			System.out.println("Atleta no encontrado\n");
		}
	}

	private static void insertarAtleta(EntityManager entity, Scanner s) {
		Atleta atleta;
		String nombre;
		int edad;
		String genero;
		int pressBanca;
		int pesoMuerto;
		int sentadilla;
		int total;
		atleta = new Atleta();

		System.out.print("\tNombre: ");
		nombre = s.nextLine();
		System.out.print("\tEdad: ");
		edad = Integer.parseInt(s.nextLine());
		System.out.print("\tGenero: ");
		genero = s.nextLine();
		System.out.print("\tPress Banca: ");
		pressBanca = Integer.parseInt(s.nextLine());
		System.out.print("\tPeso Muerto: ");
		pesoMuerto = Integer.parseInt(s.nextLine());
		System.out.print("\tSentadilla: ");
		sentadilla = Integer.parseInt(s.nextLine());
		total = pressBanca + pesoMuerto + sentadilla;

		atleta.setNombre(nombre);
		atleta.setEdad(edad);
		atleta.setGenero(genero);
		atleta.setPressBanca(pressBanca);
		atleta.setPesoMuerto(pesoMuerto);
		atleta.setSentadilla(sentadilla);
		atleta.setTotal(total);

		entity.getTransaction().begin();
		entity.persist(atleta);
		entity.getTransaction().commit();
		System.out.println("Atleta insertado\n");
		actualizarRecords(entity, atleta);
	}

}
