package hard.config;

import java.io.File;

public class ListConnectionDataBase {

    public static void main(String[] args) throws InterruptedException {

        String arq = "C:\\Hardsearch\\config\\map.ini";
        File f = new File(arq);

        if (!f.exists()) {
            System.out.println("não encontrado");

        } else {

            String conteudo = Config.Read(arq);
            String c1 = conteudo.split("    ")[0];

            if (arq.isEmpty()) {

                System.out.println("erro ao ler arquivo");

            } else {
                System.out.println(c1);

            }

        }
    }
}
