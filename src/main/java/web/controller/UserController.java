package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;
// Класс контроллера. Внедряется зависимость от UserService. Томкат настроен так, что базовой страницей
// является "/users".
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Метод для отображения списка пользователей
    @GetMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());

        return "index";
    }
    // В index.html кнопка ADD ведет ссылкой на "/new"
    @GetMapping("/new")
    public String getViewForCreateUsers(@ModelAttribute("user") User user) {

        return "new";
    }
    // В new.html кнопка OK ведет на метод (Post /users) В данном методе идет сохранение пользователя в БД
    // Метод возвращает нас на базовую страницу с URL "/users". Срабатывает getAllUsers с @GetMapping("/users")
    // и мы увидим давленного нами пользователя
    @PostMapping()
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);

        return "redirect:/users";
    }
    // Добавленные пользователи на базовой странице отображаются в виде ссылок. Если мы нажмем на нее,
    // то данный  метод по значению id из http запроса добаит в атрибут пользователя с таким id и вернет
    // нас к userInfo.html
    @GetMapping("/id")
    public String userInfo(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", userService.userInfo(id));

        return "userInfo";
    }
    // страничка, которая открывается, после перехода по ссылке с именем пользователя содержит 2 конпки:
    // edit/delete (userInfo.html)
    // Если мы нажмен edit, то сработает данный метод контроллера, который действует аналогично методу выше,
    // но возвращает нас к edit.html
    @GetMapping("/edit")
    public String getViewForEditUser(Model model, @RequestParam("edit") int id) {
        model.addAttribute("user", userService.userInfo(id));

        return "edit";
    }
    // Данный метод срабатывает, когда мы на страничке с URL: users/edit нажимаем  кнопку "edit".
    // В файле edit.html к этой конпке "привязан" соответсвующий  post (patch) метод.
    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") User user ) {
        userService.updateUser(user);
        return "redirect:/users";
    }
    // страничка, которая открывается, после перехода по ссылке с именем пользователя содержит 2 конпки:
    // edit/delete
    // В файле userInfo.html к кнопке delete "привязан" соответсвующий  post (delete) метод.
    @PostMapping("/delete")
    public String deleteUser(@RequestParam("delete") int id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

}
