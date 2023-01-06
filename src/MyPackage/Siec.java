package MyPackage;
import java.io.*;
import java.util.Scanner;

public class Siec {
    int[] adres = new int[4];
    int oktet4= adres[3],mask,lKomp= 0, lRou=0,lMiej,a=adres[3],adresPS,lastAdress, pomocniczaZmienna;
    char pytanie;
    Scanner sc = new Scanner(System.in);
    boolean is255=false,isMaskGood= false, isCountGood= false, isSave = false;


    public void siec() throws IOException {
        //wprowadzanie adresu sieci
        addAdress();
        //wprowadzanie maski
        maska(lRou+lKomp);
        System.out.println("Stworzyles siec o adresie: "+adres[0]+"."+adres[1]+"."+adres[2]+"."+adres[3]+"/"+mask);
        //dodawanie urzadzen sieciowych ( routery/komputery)
        addDevices();
        //przydzielanie adresow urzadzeniom koncowym
        adressGiving();
        while(!isSave) {
            System.out.println("Czy chcesz zapisac ta siec do pliku (plik typu .txt) t/n");
            pytanie = sc.next().charAt(0);
            if (pytanie == 't' || pytanie == 'T') {
                save();
                isSave=true;
            }
            else if (pytanie == 'n' || pytanie == 'N') System.exit(0);
            else {
                System.out.println("Bledna odpowiedz");
            }
        }
    }

    public  void siec(int okt1, int okt2, int okt3, int okt4, int lA) throws IOException {
        this.adres[0]=okt1;
        this.adres[1]=okt2;
        this.adres[2]=okt3;
        this.adres[3]=okt4;
        this.lastAdress = lA;
        //Wprowadzanie urzadzen
        addDevices();
        //ustalanie maski
        maska(lKomp+lRou);
        //addMask();


        //Przypisywanie adresow
        adressGiving();
        while(!isSave) {
            System.out.println("Czy chcesz zapisac ta siec do pliku (plik typu .txt) t/n");
            pytanie = sc.next().charAt(0);
            if (pytanie == 't' || pytanie == 'T') {
                save();
                isSave=true;
            }
            else if (pytanie == 'n' || pytanie == 'N') isSave=true;
            else {
                System.out.println("Bledna odpowiedz");
            }
        }
    }


    void maska(int devices){
        if(devices<2) System.out.println("Nie da sie utworzyc podsieci w ktorej sa mniej niz 2 urzadzenia");
        else if(devices==2){
            mask = 30;
            lMiej = 2;
            oktet4=oktet4+4;
        }
        else if(devices>2&&devices<=6) {
            mask =29;
            lMiej = 6;
            oktet4=oktet4+8;
        }
        else if( devices>6&&devices<=14) {
            mask = 28;
            lMiej = 14;
            oktet4=oktet4+16;
        }
        else if( devices>14&&devices<=30) {
            lMiej=30;
            mask = 27;
            oktet4=oktet4+32;
        }
        else if( devices>30&&devices<=62) {
            lMiej=62;
            mask = 26;
            oktet4=oktet4+64;
        }
        else if( devices>62&&devices<=126) {
            lMiej = 126;
            mask = 25;
            oktet4=oktet4+128;
        }
        else if( devices>126&&devices<=254) {

            lMiej=254;
            mask = 24;
            oktet4=oktet4+256;
        }
        else System.out.printf("Za duzo urzadzen");
    }
    void addAdress(){
        System.out.println("Rozpoczeto proces tworzenia nowej sieci. Podaj jej adres ");
        for(int i=0;i<4;i++) {
            is255 = false;
            System.out.printf("Podaj " + (i+1) +" oktet adresu sieci ");
            while(!is255) {
                adres[i] = sc.nextInt();
                if(adres[i]<0||adres[i]>255) {
                    System.out.printf("Wartosc adresu musi byc wieksza od 0 i mniejsza od 255 ");
                    is255 = false;
                }else is255 = true;
            }
        }
    }

    void addDevices() {
        System.out.println("Ile routerow jest w danej sieci?");
        lRou = sc.nextInt();
        System.out.println("Ile komputerow jest w danej sieci?");
        lKomp = sc.nextInt();
        if((lRou+lKomp)>254){
            System.out.println("Wprowadziles wiecej urzadzen niz jest wolnych miejsc!");
            System.exit(0);
        }else if(lRou<0 || lKomp<0){
            System.out.println("Wprowadziles ujemna liczbe urzadzen!");
            System.exit(0);
        }
    }

    void adressGiving(){
        pomocniczaZmienna = lastAdress;
        adresPS= adres[3]+pomocniczaZmienna;
        if((lMiej)>254-lastAdress) {
            System.out.println("Nie mozna zaadresowac sieci!\r\nLiczba wolnych adresow jest mniejsza niz liczba urzadzen ktore chcesz zaadresowac!");
            System.exit(0);
        }
        System.out.println("Stworzyles podsiec o adresie: "+adres[0]+"."+adres[1]+"."+adres[2]+"."+adresPS+ "/"+mask);
        System.out.println("Adres podsieci" + " "+adres[0]+"."+adres[1]+"."+adres[2]+"."+adresPS);
        for( int i = adresPS+1; i<=(lRou+adresPS);i++){
            System.out.println("Router R"+(i-adresPS) + " "+adres[0]+"."+adres[1]+"."+adres[2]+"."+i);
            a=i+1;
        }
        for( int i=a;i<(a+lKomp);i++){
            System.out.println("Komputer K" +(i-a+1)+ " "+adres[0]+"."+adres[1]+"."+adres[2]+"."+i);
        }

        System.out.println("Adres rozgloszeniowy" + " "+adres[0]+"."+adres[1]+"."+adres[2]+"."+(oktet4+adresPS-1));
        System.out.println();
        pomocniczaZmienna = pomocniczaZmienna+oktet4;
        lastAdress = pomocniczaZmienna;

    }


    void save() throws IOException {
        String nazwa;
        System.out.println("Podaj nazwe pod ktora chcesz zapisac siec ");
        nazwa  = sc.next();
        File plik = new File(nazwa+".txt");
        PrintWriter printer = new PrintWriter(new FileWriter(plik)) ;

        int a=1;
        printer.println("Adres sieci" + " "+adres[0]+"."+adres[1]+"."+adres[2]+"."+adres[3]);
        for( int i = adres[3]+1; i<=(lRou+adres[3]);i++){
            printer.println("Router R"+(i-adres[3]) + " "+adres[0]+"."+adres[1]+"."+adres[2]+"."+i);
            a=i+1;
        }
        for( int i=a;i<(a+lKomp);i++){
            printer.println("Komputer K" +(i-a+1)+ " "+adres[0]+"."+adres[1]+"."+adres[2]+"."+i);

        }

        printer.println("Adres rozgloszeniowy" + " "+adres[0]+"."+adres[1]+"."+adres[2]+"."+oktet4);
        printer.println();
        printer.close();
    }

}


