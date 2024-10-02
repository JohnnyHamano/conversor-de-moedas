import java.util.Scanner;

public class App
{
	static final String MENU  = """
								\n
								   CONVERSOR DE MOEDA

								1) Converter moeda
								0) Sair
								""";

	public static void main(String[] args) throws Exception
	{
		Scanner scanner = new Scanner(System.in);

		System.out.println(MENU);

		int option = scanner.nextInt();
		while (option < 0 || option > 1) {
			System.out.println(MENU);
			option = scanner.nextInt();
		}

		switch(option)
		{
			case 1: // Converter moedas
				exchangeCurrency();
				break;
			case 0:
				System.out.println("Saindo do programa...");
				scanner.close();
			default: return;
		}

		scanner.close();
	}

	static void exchangeCurrency()
	{
		Scanner scanner = new Scanner(System.in);
		final String REGEX = "^[A-Z]*$";

		System.out.println("Digite o código da moeda que deseja converter: ");
		String base_code = scanner.nextLine().toUpperCase();
		while (base_code.length() != 3 || !base_code.matches(REGEX)) {
			System.out.println("Digite o código da moeda que deseja converter (Exemplo: USD ou BRL): ");
			base_code = scanner.nextLine();
		}

		System.out.println("Aguarde...");
		Exchanger exchanger = new Exchanger(base_code);
	  
		System.out.println("Digite o código da moeda para a qual deseja converter: ");
		String target_code = scanner.nextLine().toUpperCase();
		while (target_code.length() != 3 || !target_code.matches(REGEX)) {
			System.out.println("Digite o código da moeda para a qual deseja converter (Exemplo: USD ou BRL): ");
			target_code = scanner.nextLine();
		}

		scanner.close();

		double converted = exchanger.convertTo(target_code);
		System.out.printf("1 " + base_code.toUpperCase() + " = %.2f " + target_code, converted);
	}
}