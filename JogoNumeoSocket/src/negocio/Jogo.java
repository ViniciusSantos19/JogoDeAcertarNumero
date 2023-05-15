package negocio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Jogo implements Runnable{

	private int numero;
	private int chances;
	private Socket client;
	
	public Jogo(Socket client) {
		Random r = new Random();
		this.numero = r.nextInt(25) + 1;
		this.chances = 5;
		this.client = client;
	}
	
	public void reiniciar() {
		Random r = new Random();
		this.numero = r.nextInt(25) + 1;
		this.chances = 5;
	}
	
	public String checarNumero(int numero) {
		if(numero < this.numero) {
			this.chances--;
			return"Você errou! " + " o número é maior que esse " + this.chances +
					" chances restantes";
		}else if(numero > this.numero) {
			this.chances--;
			return"Você errou! " + " o número é menor que esse " + this.chances +
					" chances restantes";
		}
		
		//this.reiniciar();
		return "Você venceu! " + "o número era " + " " + this.numero;
		
		
	}
	
	

	@Override
	public void run() {
		try {
			Scanner in = new Scanner(this.client.getInputStream());
			PrintWriter out = new PrintWriter(this.client.getOutputStream(), true);
			while(this.chances > 0) {
				
				if (this.chances == 0) {
			        out.println("Você perdeu! o número era " + this.numero);
			        break;
			    }
				
				int i = in.nextInt();
				out.println(checarNumero(i));
				
				if(i == numero) {
					break;
				}
				 
			}
			
			if(chances == 0) {
				System.out.println("você perdeu");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}

}
