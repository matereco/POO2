
public class Thread_semaforo implements Runnable {

	private Cor qual_cor;
	private boolean pause;
	private boolean changed_color = false;

	//construtor
	public Thread_semaforo() {
		this.qual_cor = Cor.vermelho;

		new Thread(this).start();
	}

	@Override
	public void run(){
		while (!pause) {
			try {
				Thread.sleep(this.qual_cor.getTempo_de_espera());
				this.mude_Cor();;
			}catch (InterruptedException e){
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	//mudar cor
	private synchronized void mude_Cor() {
		// TODO Auto-generated method stub
		switch (this.qual_cor) {
		case vermelho: 
			this.qual_cor =Cor.verde;
			break;
		case amarelo: 
			this.qual_cor = Cor.vermelho;
			break;
		case verde: 
			this.qual_cor = Cor.amarelo;
			break;

		default:
			break;

		}
		this.changed_color = true;
		notify();

	}
	//esperar mudar
	public synchronized void espere_cor_mudar() {
		while (!this.changed_color) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		this.changed_color = false;
	}
	//desligar
	public synchronized void Turn_Off() {
		this.pause = true;
	}
	//qual cor
	public Cor get_qual_cor() {
		return qual_cor;
	}
	
}

