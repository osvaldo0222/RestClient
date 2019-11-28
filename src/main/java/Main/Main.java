package Main;

import com.google.gson.JsonObject;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String estado = "0";
        do {
            System.out.println("\n<--------REST-CLIENT-------->\n");
            System.out.println("Elija una opcion:");
            System.out.println("1-Listar Estudiantes");
            System.out.println("2-Consultar Estudiante");
            System.out.println("3-Crear Estudiante");
            System.out.println("4-Salir\n");
            System.out.println("Digite una opcion: ");
            estado = scanner.nextLine().trim();

            switch (estado) {
                case "1":
                    listStudents();
                    break;
                case "2":
                    getStudent();
                    break;
                case "3":
                    createStudent();
                    break;
                case "4":
                    System.out.println("Hasta Pronto...!");
                    break;
                default:
                    System.out.println("Opcion invalida!");
                    break;
            }
        } while(!estado.equals("4"));
        scanner.close();
    }

    static void listStudents() {
        HttpResponse<String> response = Unirest.get("http://localhost:4567/rest/estudiantes/").asString();
        System.out.println("Status: " + response.getStatus());
        System.out.println("Respuesta: " + response.getBody());
    }

    static void getStudent() {
        System.out.println("Digite su matricula:");
        String id = scanner.nextLine().trim();
        HttpResponse<String> response = Unirest.get("http://localhost:4567/rest/estudiantes/{matricula}")
                .routeParam("matricula", id)
                .asString();
        System.out.println("Status: " + response.getStatus());
        System.out.println("Respuesta: " + response.getBody());
    }

     static void createStudent() {
         System.out.println("Digite su matricula:");
         String id = scanner.nextLine();
         System.out.println("Digite su nombre:");
         String name = scanner.nextLine();
         System.out.println("Digite su correo:");
         String mail = scanner.nextLine();
         System.out.println("Digite su carrera:");
         String career = scanner.nextLine();
         JsonObject student = new JsonObject();
         student.addProperty("matricula", id);
         student.addProperty("nombre", name);
         student.addProperty("correo", mail);
         student.addProperty("carrera", career);
         HttpResponse<JsonNode> response = Unirest.post("http://localhost:4567/rest/estudiantes/")
                 .header("Content-Type", "application/json")
                 .body(student)
                 .asJson();
         System.out.println("Status: " + response.getStatus());
         System.out.println("Respuesta: " + response.getBody());
    }
}
