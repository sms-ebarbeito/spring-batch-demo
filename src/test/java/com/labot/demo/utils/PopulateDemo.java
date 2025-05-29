package com.labot.demo.utils;

import java.util.Random;

public class PopulateDemo {

    public static String generateRandomFullName() {
        String[] firstNames = {"Renzo", "Ana", "Emanuel", "Carolina", "Lucas", "Maria", "Simon", "Laura", "Ezequiel", "Celeste",
                "Nahuel", "Mariana", "Maximiliano", "Adriana", "Agustin", "Antonella", "Gael", "Gabriela", "Matias", "Melina",
                "Santiago", "Victoria", "Valentino", "Natalia", "Fabricio", "Julieta", "Lisandro", "Valeria", "Benicio", "Jazmin",
                "Emilio", "Camila", "Ignacio", "Luciana", "Bautista", "Juliana", "Manuel", "Candelaria", "Joaquin", "Paula",
                "Mauro", "Debora", "Leon", "Rocio", "Joel", "Lorena", "Dario", "Rosa", "Matias", "Ana",
                "Facundo", "Aurora", "Ulises", "Ivonne", "Lionel", "Veronica", "Luciano", "Daniela", "Emiliano", "Florencia",
                "Gaston", "Noelia", "Hugo", "Brenda", "Diego", "Alicia", "Federico", "Magdalena", "Ruben", "Dolores",
                "Roberto", "Marisol", "Raul", "Silvina", "Julio", "Nora", "Nicolas", "Carla", "Carlos", "Elisa",
                "Ramon", "Elena", "Cristian", "Gabriela", "Hernan", "Ines", "Francisco", "Catalina", "Javier", "Marina",
                "Leonardo", "Carmen", "Marcos", "Carolina", "Lucas", "Patricia", "Matias", "Nancy", "Santiago", "Julia",
                "Sebastian", "Luisa", "Lautaro", "Maria", "Franco", "Marta", "Luciano", "Andrea", "Tomas", "Clarisa",
                "German", "Veronica", "Emilio", "Alejandra", "Gonzalo", "Valentina", "Ivan", "Isabel", "Esteban", "Patricia",
                "Mariano", "Agustina", "angel", "Romina", "Cristian", "Laura", "Eduardo", "Natalia", "Maximiliano", "Soledad",
                "Diego", "Maria", "Pablo", "Maria", "Juan", "Alejandra", "Julian", "Guadalupe", "Facundo", "Jimena",
                "Ezequiel", "Cynthia", "Francisco", "Rocio", "Gustavo", "Valeria", "Fernando", "Milagros", "Gabriel", "Vanina",
                "Daniel", "Jorgelina", "Adrian", "Maria", "Luis", "Lucia", "Juan", "Melisa", "Cesar", "Estefania",
                "Martin", "Ailen", "Marcelo", "Micaela", "Alberto", "Veronica", "Guillermo", "Camila", "Carlos", "Ruth",
                "Hector", "Cecilia", "Mario", "Diana", "Nestor", "Andrea", "Oscar", "Gisela", "Marcelo", "Natalia",
                "Nicolas", "Marianela", "Matias", "Carolina", "Juan", "Marina", "Santiago", "Belen", "Ignacio", "Analia",
                "Lautaro", "Antonella", "Alejandro", "Luciana", "Ramiro", "Rosa", "Lucas", "Valeria", "Diego", "Maria",
                "Matias", "Sofia", "Leandro", "Eva", "Martin", "Camila", "Pablo", "Ana", "Tomas", "Julieta",
                "Facundo", "Dolores", "Maximiliano", "Carolina", "Agustin", "Jimena", "Franco", "Natalia", "Luciano", "Florencia",
                "Gaston", "Laura", "Hugo", "Rocio", "Federico", "Marta", "Ruben", "Veronica", "Raul", "Nora",
                "Julio", "Elisa", "Nicolas", "Ines", "Carlos", "Catalina", "Ramon", "Marina", "Cristian", "Luisa",
                "Hernan", "Margarita", "Francisco", "Carla", "Javier", "Carmen", "Leonardo", "Maria", "Marcos", "Carolina",
                "Lucas", "Marcela", "Matias", "Alicia", "Santiago", "Sofia", "Sebastian", "Lorena", "Lautaro", "Aurora",
                "Franco", "Ivonne", "Martin", "Silvina", "Luciano", "Nora", "Facundo", "Brenda", "Gaston", "Magdalena",
                "Federico", "Marisol", "Roberto", "Rosa", "Raul", "Nancy", "Julio", "Veronica", "Nicolas", "Elisa",
                "Carlos", "Ines", "Hernan", "Catalina", "Francisco", "Luisa", "Javier", "Carla", "Leonardo", "Carmen",
                "Marcos", "Marina", "Lucas", "Carolina", "Matias", "Maria", "Santiago", "Alejandra", "Sebastian", "Natalia",
                "Lautaro", "Jimena", "Franco", "Camila", "Martin", "Julieta", "Luciano", "Florencia", "Facundo", "Valentina",
                "Gaston", "Lucia", "Federico", "Mariana", "Roberto", "Celeste", "Raul", "Paula", "Julio", "Maria",
                "Nicolas", "Melina", "Carlos", "Valeria", "Hernan", "Candelaria", "Francisco", "Rocio", "Javier", "Ailin",
                "Leonardo", "Rocio", "Marcos", "Marta", "Lucas", "Nora", "Matias", "Veronica", "Santiago", "Rosa",
                "Sebastian", "Nancy", "Lautaro", "Elisa", "Franco", "Ines", "Martin", "Catalina", "Luciano", "Luisa",
                "Facundo", "Carla", "Gaston", "Carmen", "Federico", "Marina", "Roberto", "Carolina", "Raul", "Ivonne",
                "Julio", "Silvina", "Nicolas", "Margarita", "Carlos", "Camila", "Hernan", "Sofia", "Francisco", "Lorena",
                "Javier", "Aurora", "Leonardo", "Brenda", "Marcos", "Magdalena", "Lucas", "Marisol", "Matias", "Rosa",
                "Santiago", "Nancy", "Sebastian", "Veronica", "Lautaro", "Elisa", "Franco", "Ines", "Martin", "Catalina",
                "Luciano", "Luisa", "Facundo", "Carla", "Gaston", "Carmen", "Federico", "Marina", "Roberto", "Carolina",
                "Raul", "Ivonne", "Julio", "Silvina", "Nicolas", "Margarita", "Carlos", "Camila", "Hernan", "Sofia",
                "Francisco", "Lorena", "Javier", "Aurora", "Leonardo", "Brenda", "Marcos", "Magdalena", "Lucas", "Marisol",
                "Matias", "Rosa", "Santiago", "Nancy", "Sebastian", "Veronica"};

        String[] lastNames = {"alvarez", "Sanchez", "Romero", "Perez", "Gonzalez", "Torres", "Flores", "Diaz", "Hernandez", "Garcia",
                "Martinez", "Lopez", "Martin", "Fernandez", "Rodriguez", "Diaz", "Gomez", "Cabrera", "Reyes", "Figueroa",
                "Leon", "Molina", "Vargas", "Aguirre", "Acosta", "Silva", "Rojas", "Morales", "Navarro", "Vera",
                "Rivas", "Salazar", "Castro", "Muñoz", "Araya", "Moreno", "Peña", "Sandoval", "Sepulveda", "Gutierrez",
                "Valenzuela", "Mendez", "Herrera", "Vidal", "Bravo", "Pizarro", "Campos", "Santos", "Castillo", "Montoya",
                "Soto", "Godoy", "Correa", "Ortega", "Fuentes", "Rivera", "Toro", "Moya", "Rios", "Escobar",
                "Flores", "Gallardo", "Espinosa", "Guzman", "Peralta", "Sanchez", "Aravena", "Contreras", "Saez", "Tapia",
                "Lara", "Gonzales", "Navarro", "Roman", "Zamorano", "Maldonado", "Briones", "Jimenez", "Guzman", "Vergara",
                "Avila", "Gomez", "Torres", "Rojas", "Molina", "Chavez", "Saez", "Vergara", "Caceres", "Arancibia",
                "Silva", "Mendez", "Ramirez", "Aguilar", "Fernandez", "Sanchez", "Molina", "Miranda", "Valle", "Medina",
                "Muñoz", "Cortes", "Peña", "Espinoza", "Contreras", "Ortiz", "Guerrero", "Castillo", "Santana", "Pavez",
                "Carvajal", "Leiva", "Villanueva", "Riquelme", "Villagra", "Chavez", "Aguayo", "Castillo", "Molina", "Arancibia",
                "Hidalgo", "Carrasco", "Mansilla", "Vasquez", "Fuentes", "Moya", "Sepulveda", "Figueroa", "Gallardo", "Valenzuela",
                "Morales", "Espinoza", "Soto", "Leiva", "Torres", "Nuñez", "Suarez", "Valdes", "Fernandez", "Riquelme",
                "San Martin", "Peña", "Fernandez", "Gutierrez", "Muñoz", "Pereira", "Salas", "Muñoz", "Vargas", "Mardones",
                "Bustos", "Santander", "Santos", "Saez", "Campos", "Mansilla", "Aguilera", "Salazar", "Gutierrez", "Pizarro",
                "Gomez", "Castro", "Garcia", "Molina", "Zuñiga", "Orellana", "Catalan", "Zamora", "Perez", "Fernandez",
                "Diaz", "Gonzalez", "Torres", "Lopez", "Vasquez", "Figueroa", "Vega", "Cortes", "Silva", "Paredes",
                "Araya", "Castillo", "Flores", "Gallardo", "Garcia", "Aguilar", "Cortez", "Lopez", "Vargas", "Vargas",
                "Pino", "Montoya", "Campos", "Vargas", "Vera", "Olivares", "Navarro", "Diaz", "Perez", "Muñoz",
                "Vergara", "Herrera", "Vargas", "Figueroa", "Gutierrez", "Gonzalez", "Vega", "Soto", "Araya", "Sanchez",
                "Pizarro", "Gomez", "Molina", "Ortiz", "Muñoz", "Diaz", "Fernandez", "Jimenez", "Perez", "Morales",
                "Vargas", "Salazar", "Fuentes", "Vasquez", "Orellana", "Valenzuela", "Gallardo", "Gonzalez", "Vega", "Arancibia",
                "Caceres", "Garcia", "Muñoz", "Soto", "Torres", "Navarro", "Mardones", "Vargas", "Araya", "Fernandez",
                "Cortes", "Vera", "Gutierrez", "Gonzalez", "Figueroa", "Sanchez", "Navarro", "Molina", "Gonzalez", "Silva",
                "Riquelme", "Cortez", "Vega", "Gonzalez", "Paredes", "Garcia", "Soto", "Molina", "Diaz", "Olivares",
                "Fuentes", "Gutierrez", "Muñoz", "Vasquez", "Gallardo", "Vera", "Cortes", "Silva", "Paredes", "Garcia",
                "Araya", "Vargas", "Muñoz", "Pizarro", "Sanchez", "Perez", "Caceres", "Arancibia", "Orellana", "Gonzalez"};

        Random random = new Random();
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        return firstName + " " + lastName;
    }

    public static String generateRandomPhoneNumber() {
        Random random = new Random();
        StringBuilder phoneNumber = new StringBuilder("+54011");
        for (int i = 0; i < 8; i++) {
            phoneNumber.append(random.nextInt(10));
        }
        return phoneNumber.toString();
    }

    public static Integer generateRandomAge() {
        return new Random().nextInt(100);
    }

    private static Integer num = 1;

    public static String generateRandomNumber() {
        Random random = new Random();
        int number = random.nextInt(1000);
        num++;
        return String.format("%03d", number) + num;
    }

    public static String generateRandomUsername(String fullName) {
        return fullName.replace(" ", ".");
    }

    public static String generateRandomEmail(String username) {
        return (username + "@local.com").toLowerCase();
    }
}
