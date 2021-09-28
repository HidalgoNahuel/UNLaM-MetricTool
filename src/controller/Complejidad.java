package controller;

public class Complejidad {

	private static final String[] decisiones = { "if", "else", "for", "while", "||", "&&" };
	private int complejidad;

	public Complejidad(String [] lineasCodigo) {
		if (lineasCodigo == null)
			throw new IllegalArgumentException("Lineas de codigo vacias");

		for (String line : lineasCodigo) {
			for (String decision : decisiones) {
				if (line.contains(decision)) {
					int last_index = 0;
					while (last_index != -1) {
						last_index = line.indexOf(decision, last_index);
						if (last_index != -1) {
							complejidad += 1;
							last_index += decision.length();
						}
					}
				}
			}
		}
	}
	
	public int complejidad() {
		return this.complejidad+1;
	}
}
