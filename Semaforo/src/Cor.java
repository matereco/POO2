
public enum Cor {

	verde(120000), amarelo(30000), vermelho(60000);
	
	 private int tempo_de_espera;

	private Cor(int tempo_de_espera) {
		this.tempo_de_espera = tempo_de_espera;
	}

	public int getTempo_de_espera() {
		return tempo_de_espera;
	}
	 
	
}

 
