package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Halstead {

	private static final List<String> operators = Arrays.asList("+", "-", "/", "*", "double", "dloat", "int", ";", ":", "public", "static", "void", "&&", "||", "<=", ">=", "<", ">");
	private int commentedLines;
	private double halsteadVol;
	private double halsteadLong;
	
	public Halstead(String[] lineas) {
		if(lineas == null)
			throw new IllegalArgumentException("Lineas de codigo nulas");
		halsteadCalculo(lineas);
	}
	
	private void halsteadCalculo(String[] lineasCodigo) {
		
		ArrayList<String> lineasCodigoHalstead = new ArrayList<String>();
		
		int operadoresTotales = 0;
//		List<String> operadoresList = Arrays.asList(operadores);
		
		ArrayList<String> operadoresUnicos = new ArrayList<String>();
		
		// Saco los comentarios
		for (String line : lineasCodigo) {
			if (!line.contains("//")) {
				lineasCodigoHalstead.add(line);
			}
			else
				commentedLines++;
		}
		
		for (String line : lineasCodigo) {
			for (String operador : operators) {
				if (line.contains(operador)) {
			    	if (!operadoresUnicos.contains(operador)) {
			    		operadoresUnicos.add(operador);
			    	}
					Integer last_index = 0;
					while (last_index != -1) {
						last_index = line.indexOf(operador, last_index);
					    if (last_index != -1) {
					    	operadoresTotales += 1;
					    	last_index += operador.length();
					    }
					}
				}
			}
		}
		
		operadoresTotales -= operators.size();
		
		ArrayList<String> operandosUnicos = new ArrayList<String>();
		
		Integer operandosTotales = 0;
		for (String line : lineasCodigoHalstead) {
			// Operandos
			String[] words = line.split(" ");
			for (String word : words) {
				if (operators.indexOf(word) == -1 && operandosUnicos.indexOf(word) == -1) {
					operandosUnicos.add(word);
				}
				if (operators.indexOf(word) == -1) {
					operandosTotales += 1;
				}
			}
		}
		
//		System.out.println(operandosUnicos.size());
		
		halsteadLong = (operadoresUnicos.size() * Math.log(operadoresUnicos.size()) / Math.log(2)) + (operandosUnicos.size() * Math.log(operandosUnicos.size()) / Math.log(2));
		halsteadVol = (operadoresTotales + operandosTotales) * (Math.log((operadoresUnicos.size() + operandosUnicos.size())) / Math.log(2));
//		Double[] halsteadCalculo = {halsteadLongitud, halsteadVolumen};
//		return halsteadCalculo;
	}
	
	public Double halsteadVol() {
		return this.halsteadVol;
	}
	
	public Double halsteadLong() {
		return this.halsteadLong;
	}
	
	public int commentedLines() {
		return this.commentedLines;
	}
}
