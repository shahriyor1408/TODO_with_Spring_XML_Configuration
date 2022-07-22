package com.company.service;

import com.company.domains.Session;
import com.company.domains.Todo;
import com.company.domains.Users;
import com.company.repository.TodoRepository;
import com.company.repository.UserRepository;
import com.company.ui.Response;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class TodoService {
    private TodoRepository todoRepository;
    private UserRepository userRepository;

    public Response showAllTodos() {
        return new Response<>(todoRepository.getAll());
    }

    public Response showMyAllTodos() {
        return new Response<>(todoRepository.getMyTodos());
    }

    public Response showMyUncompletedTodos() {
        return new Response<>(todoRepository.getUncompletedTodos());
    }

    public Response createTodo(String desc) {
        Optional<Users> optionalUsers = userRepository.getByUsername(Session.sessionUser.getUsername());
        if (optionalUsers.isEmpty()) {
            return new Response<>("User not found!", false);
        }
        Todo todo = Todo.builder()
                .description(desc)
                .completed(false)
                .user(optionalUsers.get())
                .build();
        return new Response<>(todoRepository.save(todo));
    }

    public Response updateTodo(String id, String newDesc) {
        Optional<Todo> optionalTodo = todoRepository.getById(id);
        if (optionalTodo.isEmpty()) {
            return new Response<>("Todo not found!", false);
        }
        todoRepository.update(id, newDesc);
        return new Response<>("Todo is successfully updated!");
    }

    public Response deleteTodo(String id) {
        Optional<Todo> optionalTodo = todoRepository.getById(id);
        if (optionalTodo.isEmpty()) {
            return new Response<>("Todo not found!", false);
        }
        todoRepository.delete(id);
        return new Response<>("Todo successfully deleted!");
    }

    public Response doTodo(String id) {
        Optional<Todo> optionalTodo = todoRepository.getById(id);
        if (optionalTodo.isEmpty()) {
            return new Response<>("Todo not found!", false);
        }
        todoRepository.doTodo(id);
        return new Response<>("Todo successfully completed!");
    }

    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    public TodoService() {
    }
}
