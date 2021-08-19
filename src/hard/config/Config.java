package hard.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Config {

    public static String Read(String Caminho) {
        String conteudo = "";

        try {
            FileReader arq = new FileReader(Caminho);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = "";
            try {
                linha = lerArq.readLine();
                while (linha != null) {
                    conteudo = linha;
                    linha = lerArq.readLine();

                }
                arq.close();

            } catch (IOException e) {
                System.out.println("Erro: não é possivel ler arquivo" + e);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro: arquivo não encontrado" + e);
        }

        if (conteudo.contains("Erro")) {
            return "";
        } else {
            return conteudo;
        }
    }

    public static boolean Write(String Caminho, String Texto) {

        try {
            FileWriter arq = new FileWriter(Caminho);
            try (PrintWriter gravarArq = new PrintWriter(arq)) {
                gravarArq.println(Texto);
            }
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
