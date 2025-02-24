package com.com;

import clases.Person;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dao.PersonDAO;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DbMenu {
    public static void lanzaMenu(){
        String url = "mongodb://root:Sandia4you@localhost:27017";
        try (MongoClient mc = MongoClients.create(url)){
            boolean salir = true;
            while (salir) {
                System.out.println("1. Mostrar todas las personas");
                System.out.println("2. Mostrar personas entre edades");
                System.out.println("3. Mostrar personas con email");
                System.out.println("4. Mostrar personas por código postal");
                System.out.println("5. Eliminar persona");
                System.out.println("6. Actualizar contraseña");
                System.out.println("0. Salir");
                Scanner sc = new Scanner(System.in);
                int opcion = sc.nextInt();
                switch (opcion) {
                    case 1:
                        op1();
                        break;
                    case 2:
                        op2();
                        break;
                    case 3:
                        //TODO
                        break;
                    case 0:
                        salir = false;
                        break;
                }
            }
        }catch(InputMismatchException e){

        }
    }
    public static void op1(){
        ArrayList<Person> personas = (ArrayList<Person>) PersonDAO.readAll();
        for (Person p:personas){
            System.out.println(p.toString());
        }
    }
    public static void op2(){
        System.out.println("Introduce un valor numero minimo para filtrar:");
        Integer min = pedirInt();
        System.out.println("Introduce un valor numero maximo para filtrar:");
        Integer max = pedirInt();
        ArrayList<Person> personas = (ArrayList<Person>) PersonDAO.readBeetween(min,max);
        for (Person p:personas){
            System.out.println(p.toString());
        }
    }

    public static int pedirInt(){
        boolean salir = true;
        int devolver = 0;
        while (salir){
            try {
                Scanner sc = new Scanner(System.in);
                devolver = sc.nextInt();
                salir = false;
            }catch (Exception e){
                System.out.println("Introduzca un valor correcto");
            }
        }
        return devolver;
    }
}
