import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Compilador_java {
    public static void main(String[] args) {
        try {
            BufferedReader leitor = new BufferedReader(new FileReader("operacao.txt"));
            String line;
            Map<String, String> variables = new HashMap<>();

            while ((line = leitor.readLine()) != null) {
                String[] parts = line.split(" = ");
                if (parts.length == 2) {
                    variables.put(parts[0], parts[1]);
                } else {
                    System.out.println("Linha inválida: " + line);
                }
            }

            leitor.close();

            System.out.println("public static void main(String[] args) {");
            for (Map.Entry<String, String> entry : variables.entrySet()) {
                System.out.println("    double " + entry.getKey() + " = " + entry.getValue() + ";");
            }
            System.out.println("    System.out.println(\"O resultado de c é: \" + c);");
            System.out.println("}");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo.");
            e.printStackTrace();
        }
    }
}


