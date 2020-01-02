package com.learn.cursomc.utils;

public class Util {
	/**
	 * Verifica se um dado objeto é nulo
	 * 
	 * @param dado
	 *        Dado a ser validado.
	 * @return Retorna <i>true</i> se o objeto é nulo.
	 */
	public static boolean isNull(Object dado) {
		if (dado == null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Verifica se uma string é válida: não nula e diferente de vazio e brancos.
	 * 
	 * @param obj
	 *        Objeto representando a string a ser validada.
	 * @return Retorna <i>true</i> se o objeto é nulo.
	 */
	public static boolean isValidString(Object obj) {
		if (!isNull(obj)) {
			if (obj.toString().trim().length() > 0) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Valida uma string numérica. Se apenas um caracter da string não for um dígito válido, o teste falha.
	 * 
	 * @param obj
	 *        String a ser validada.
	 * @return True se a string tiver apenas dígitos válidos
	 */
	public static boolean isNumerico(Object obj) {
		if (!isValidString(obj)) {
			return false;
		}
		
		String s = obj.toString();
		try {
			if (s.startsWith("-")) {
				s = s.substring(s.indexOf("-") + 1).trim();
			}
			if (s.startsWith("+")) {
				s = s.substring(s.indexOf("+") + 1).trim();
			}
			
			Float.parseFloat(s);
		}
		catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Efetua a consistencia do CPF.
	 * 
	 * @param dado
	 *        String contendo o dado do CPF.
	 * 
	 * @return True caso o CPF esteja valido.
	 */
	public static boolean isValidCPF(String cpf) {
		if (!isValidString(cpf)) {
			return false;
		}
		
		cpf = getDigitos(cpf);
		if (cpf.length() != 11) {
			return false;
		}
		
		if (!cpf.substring(9).equals(calculaDVCPF(cpf.substring(0, 9)))) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Consiste o numero do CNPJ.
	 * 
	 * @param cnpj
	 *        Numero do CNPJ a ser verificado, contendo apenas os digitos.
	 */
	public static boolean isValidCNPJ(String cnpj) {
		if (!isValidString(cnpj)) {
			return false;
		}
		
		cnpj = getDigitos(cnpj);
		if (cnpj.length() != 14) {
			return false;
		}
		
		if (!cnpj.substring(12).equals(calculaDVCNPJ(cnpj.substring(0, 12)))) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Retorna apenas os dígitos existentes em uma string.
	 * 
	 * @param in
	 *        String a ser formatada
	 * @return Retorna apenas os dígitos da String
	 */
	public static String getDigitos(String in) {
		String ret = null;
		if (!isNull(in)) {
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < in.length(); i ++) {
				if (Character.isDigit(in.charAt(i))) {
					sb.append(in.charAt(i));
				}
			}
			ret = sb.toString();
		}
		
		return ret;
	}
	
	/**
	 * Obtem o digito verificador de um determinado CPF.
	 * 
	 * @param cpf
	 *        CPF sem os digitos verificadores (deve ter pelo menos 9 caracteres).
	 * 
	 * @return String contendo os digitos verificador do CPF.
	 */
	public static String calculaDVCPF(String cpf) {
		if (isValidString(cpf)) {
			cpf = getDigitos(cpf);
			// calcula o primeiro digito verificador
			int s1 = 0;
			for(int i = 1; i <= 9; i ++) {
				s1 += Character.digit(cpf.charAt(i - 1), 10) * (11 - i);
			}
			
			int r1 = (s1 % 11);
			int dv1 = (r1 < 2 ? 0 : (11 - r1));
			
			// calcula o segundo digito verificador
			int s2 = (dv1 * 2);
			for(int i = 1; i <= 9; i ++) {
				s2 += Character.digit(cpf.charAt(i - 1), 10) * (12 - i);
			}
			
			int r2 = (s2 % 11);
			int dv2 = (r2 < 2 ? 0 : (11 - r2));
			
			// retorna os digitos transformados em string
			return (Integer.toString(dv1) + Integer.toString(dv2));
		}
		
		return null;
	}
	
	/**
	 * Obtem o digito verificador de um determinado CNPJ
	 * 
	 * @param sCNPJ
	 *        CNPJ sem os digitos verificadores (deve ter pelo menos 12 caracteres)
	 * 
	 * @return String contendo os digitos verificador do CNPJ
	 */
	public static String calculaDVCNPJ(String sCNPJ) {
		if (isValidString(sCNPJ)) {
			sCNPJ = getDigitos(sCNPJ);
			// calcula o primeiro digito verificador
			int s1 = 0;
			
			for(int i = 1; i <= 4; i ++) {
				s1 += Character.digit(sCNPJ.charAt(i - 1), 10) * (6 - i);
			}
			
			for(int i = 5; i <= 12; i ++) {
				s1 += Character.digit(sCNPJ.charAt(i - 1), 10) * (14 - i);
			}
			
			int r1 = (s1 % 11);
			int dv1 = (r1 < 2 ? 0 : (11 - r1));
			
			// calcula o segundo digito verificador
			int s2 = (dv1 * 2);
			for(int i = 1; i <= 5; i ++) {
				s2 += Character.digit(sCNPJ.charAt(i - 1), 10) * (7 - i);
			}
			
			for(int i = 6; i <= 12; i ++) {
				s2 += Character.digit(sCNPJ.charAt(i - 1), 10) * (15 - i);
			}
			
			int r2 = (s2 % 11);
			int dv2 = (r2 < 2 ? 0 : (11 - r2));
			
			// retorna os digitos transformados em string
			return Integer.toString(dv1) + Integer.toString(dv2);
		}
		
		return null;
	}
	
	
}