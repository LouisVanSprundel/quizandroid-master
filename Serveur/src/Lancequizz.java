
public class Lancequizz implements Runnable {

	public static int lq = 0;

	public void run() {
		Quizz.textArea_1.append("POUR DEMARRER LE QUIZZ APPUYER SUR LE BOUTON LANCER QUIZZ\n");
		Quizz.btnLancerQuizz.setEnabled(true); // débloque le bouton lancer quizz

	}
}
