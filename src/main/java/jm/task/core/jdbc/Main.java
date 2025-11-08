package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        // Создание таблицы User(ов)
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        // Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
        addUser(userService,"Айдар", "Мустаев", (byte)35);
        addUser(userService,"Полина", "Стельмах", (byte)24);
        addUser(userService,"Дима", "Мишин", (byte)30);
        addUser(userService,"Никита", "Макаров", (byte)26);
        System.out.println();


        // Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
        userService.getAllUsers().forEach(System.out::println);
        // Очистка таблицы User(ов)
        userService.cleanUsersTable();
        // Удаление таблицы
        userService.dropUsersTable();
    }

    public static void addUser(UserService userService, String name, String lastName, byte age) {
        userService.saveUser(name, lastName, age);
        System.out.println("User с именем – "+ name +" добавлен в базу данных");
    }

}
