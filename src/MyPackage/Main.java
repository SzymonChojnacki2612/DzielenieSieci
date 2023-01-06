package MyPackage;

import java.io.IOException;
import java.util.Scanner;

/*
@author Szymon Chojnacki
 */
public class Main {

    public  static void main(String[] args) throws IOException {
        boolean is255;
        int[] adres = new int[4];
        int lastAdress=0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Program dokonuje podzialu sieci o wprowadzonym przez uzytkownika adresie (z maska /24) na mozliwie jak najmniejsze podsieci");
        System.out.println("Podaj adres sieci ktora chcesz podzielic: ");
        for(int i=0;i<4;i++) {
            is255 = false;
            System.out.printf("Podaj " + (i + 1) + " oktet adresu sieci ");
            while (!is255) {
                adres[i] = scanner.nextInt();
                if (adres[i] < 0 || adres[i] > 255) {
                    System.out.printf("Wartosc adresu musi byc wieksza od 0 i mniejsza od 255 ");
                    is255 = false;
                } else is255 = true;
            }
        }
        if(adres[3]!=0) {
            System.out.println("Jesli siec ma maske /24 to adres sieci musi miec postac: x.x.x.0\r\nUstawiono wartosc ostatniego oktetu na 0 ");
            adres[3] = 0;
        }
        System.out.println("Na ile podsieci chcesz podzielic ta siec?");
        int ilosc = scanner.nextInt();
        Siec[] siec = new Siec[ilosc];
        for(int i = 0; i<ilosc;i++){
            siec[i] = new Siec();
            siec[i].siec(adres[0],adres[1],adres[2],adres[3],lastAdress);
            lastAdress = siec[i].lastAdress;
        }
    }

}
