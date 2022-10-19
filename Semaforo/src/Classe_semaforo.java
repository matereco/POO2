
public class Classe_semaforo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread_semaforo semaforo = new Thread_semaforo();
		
		for (int i = 0; i < 10; i++) {
			System.out.println(semaforo.get_qual_cor());
			semaforo.espere_cor_mudar();;
		}
		
		semaforo.Turn_Off();
	}

}
