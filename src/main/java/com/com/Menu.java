package com.com;


import clases.Address;
import clases.Person;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dao.PersonDAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        String url = "mongodb://root:Sandia4you@localhost:27017";
        try (MongoClient mc = MongoClients.create(url)){
            boolean salir = true;
            while (salir) {
                System.out.println("1. Registrar a una persona");
                System.out.println("2. Iniciar sesión");
                System.out.println("3. Ver datos");
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
                        DbMenu.lanzaMenu();
                        break;
                    case 0:
                        salir = false;
                        break;
                }
            }
        }catch(InputMismatchException e){

        }
    }

    private static void op1(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca un nombre de usuario");
        String nombre = sc.nextLine();
        System.out.println("Introduzca una contraseña");
        String pass = sc.nextLine();
        byte[] hashPass = hasear(pass);
        Person persona = new Person(nombre,hashPass);
        if(PersonDAO.verifyUserByName(nombre) == false){
            System.out.println("Introduzca nombre (opcional)");
            String name = sc.nextLine();
            if(name != null){
                persona.setName(name);
            }
            System.out.println("Introduzca edad (opcional)");
            String edad = sc.nextLine();
            if(edad != null){
                int ed = Integer.parseInt(edad);
                persona.setAge(ed);
            }
            System.out.println("Introduzca emails (opcional)");
            List<String> emails = new ArrayList<>();
            boolean control = true;
            while(control){
                String email = sc.nextLine();
                if(!email.equals("")){
                    emails.add(email);
                }else{
                    control = false;
                }
            }
            persona.setEmails(emails);
            Address ad = pedirDireccion();
            if(ad != null){
                persona.setAddress(ad);
            }
            PersonDAO.insertUser(persona);
        }else{
            System.out.println("Nombre de usuario ya registrado");
        }
    }

    public static void op2(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca un nombre de usuario para iniciar sesion");
        String nombre = sc.nextLine();
        System.out.println("Introduzca una contraseña para iniciar sesion");
        String pass = sc.nextLine();
        byte[] hashPass = hasear(pass);
        boolean comp = PersonDAO.verifyUserByNamePass(nombre,hashPass);
        if(comp){
            System.out.println("Autentificación corecta");
            //Se abriria el menu de encriptacion(PSP)
        }else{
            System.out.println("Nombre o contraseña incorrecto");
        }
    }

    public static byte[] hasear(String elemento){
        String algoritmo = "MD5";
        byte[] hash = null;
        try {
            MessageDigest md  = MessageDigest.getInstance(algoritmo);
            md.update(elemento.getBytes());
            hash = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

    public static Address pedirDireccion(){
        Address ad = new Address(null,null,null);
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca calle de domicilio (opcional)");
        String calle = sc.nextLine();
        if (!calle.equals("")){
            ad.setStreet(calle);
        }
        System.out.println("Introduzca numero de domicilio (opcional)");
        String numero = sc.nextLine();
        if(numero != null){
            int num = Integer.parseInt(numero);
            ad.setNumber(num);
        }
        System.out.println("Introduzca codigo postal de domicilio (opcional)");
        String cp = sc.nextLine();
        if (cp != null){
            ad.setPostalCode(cp);
        }
        return ad;
    }
}