package com.company.ui;

import com.company.enums.UserRole;
import com.company.service.TodoService;
import com.company.service.UserService;
import lombok.*;
import uz.jl.BaseUtils;
import uz.jl.Colors;

import static com.company.domains.Session.sessionUser;

@Setter
@Getter
public class AppUI {
    private UserService userService;
    private TodoService todoService;

    public void run() {
        if (sessionUser == null) {
            BaseUtils.println("Login    -> 1");
            BaseUtils.println("Register -> 2");
        } else {
            if (sessionUser.getRole().equals(UserRole.ADMIN)) {
                BaseUtils.println("********* Admin Page *******", Colors.YELLOW);
                BaseUtils.println("Show All Users -> 3");
                BaseUtils.println("Show All Todos -> 4");
            } else {
                BaseUtils.println("********* User Page *******", Colors.YELLOW);
            }
            BaseUtils.println("Todo CRUD                 -> 5");
            BaseUtils.println("Show my uncompleted todos -> 6");
            BaseUtils.println("Do todo                   -> 7");
            BaseUtils.println("Logout -> 0");
        }
        BaseUtils.println("Quit   -> q");
        String choice = BaseUtils.readText("?:");
        switch (choice) {
            case "1" -> login();
            case "2" -> register();
            case "3" -> print_response(userService.showAllUsers());
            case "4" -> print_response(todoService.showAllTodos());
            case "5" -> todoCRUD();
            case "6" -> print_response(todoService.showMyUncompletedTodos());
            case "7" -> doTodo();
            case "0" -> print_response(userService.logout());
            case "q" -> {
                return;
            }
            default -> BaseUtils.println("Wrong choice!", Colors.RED);
        }
        run();
    }

    private void doTodo() {
        String id = BaseUtils.readText("Enter todo id: ");
        print_response(todoService.doTodo(id));
    }

    private void todoCRUD() {
        BaseUtils.println("Create todo -> 1");
        BaseUtils.println("Update todo -> 2");
        BaseUtils.println("Delete todo -> 3");
        BaseUtils.println("Show todos  -> 4");
        BaseUtils.println("Back        -> 0");
        BaseUtils.println("Quit -> q");
        String choice = BaseUtils.readText("?:");
        switch (choice) {
            case "1" -> createTodo();
            case "2" -> updateTodo();
            case "3" -> deleteTodo();
            case "4" -> print_response(todoService.showMyAllTodos());
            case "0" -> {
                return;
            }
            default -> BaseUtils.println("Wrong choice!", Colors.RED);
        }
        todoCRUD();
    }

    private void deleteTodo() {
        String id = BaseUtils.readText("Enter todo id: ");
        print_response(todoService.deleteTodo(id));
    }

    private void updateTodo() {
        String id = BaseUtils.readText("Enter todo id: ");
        String newDesc = BaseUtils.readText("Enter new description: ");
        print_response(todoService.updateTodo(id, newDesc));
    }

    private void createTodo() {
        String desc = BaseUtils.readText("Create description: ");
        print_response(todoService.createTodo(desc));
    }

    private void register() {
        String username = BaseUtils.readText("Create username: ");
        String password = BaseUtils.readText("Create password: ");
        print_response(userService.register(username, password));
    }

    private void login() {
        String username = BaseUtils.readText("Username : ");
        String password = BaseUtils.readText("Password : ");
        print_response(userService.login(username, password));
    }

    public void print_response(Response response) {
        String color = !response.isOk() ? Colors.RED : Colors.GREEN;
        BaseUtils.println(BaseUtils.gson.toJson(response), color);
    }

    public AppUI(UserService userService, TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;
    }

    public AppUI() {

    }
}
