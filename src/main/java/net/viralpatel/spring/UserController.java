package net.viralpatel.spring;


import net.viralpatel.spring.model.User;
import net.viralpatel.spring.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public @ResponseBody String hello(){
        return "<h1>WELCOME TO THE REST API SERVICE</h1>";
    }


    /**
     * Получает определенного юзера с БД с помощью id
     * @param id Идентификатор юзера в БД
     * @return User Возвращет User
     */
    @RequestMapping(value = "/users/{id}", method={RequestMethod.GET})
    public ResponseEntity getUserInfo(@PathVariable("id") int id) {
        User user = userRepository.getUserFromDb(id);
        if (user == null) {
            return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(user, HttpStatus.OK);
    }


    /**
     * Добавляет юзера
     * @param User Пользователь
     * @return User Возвращет User
     */
    @RequestMapping(value = "users/create", method=RequestMethod.POST)
    public ResponseEntity<User> setUser(@RequestBody User user){
        User newUser;
        newUser = userRepository.insert(user.getFirstname(), user.getLastname(), user.getBirthdate());
        return new ResponseEntity<User>(newUser, HttpStatus.OK);
    }

    /**
     * Проставляет статус юзера онлайн
     * @param id Идентификатор юзера в БД
     */
    @RequestMapping("setUserOnline/{id}")
    public ResponseEntity setUserOnline(@PathVariable("id") int id){
        userRepository.setUserStatus(id, true);
        return new ResponseEntity(id, HttpStatus.OK);
    }

    /**
     * Проставляет статус юзера оффлайн
     * @param id Идентификатор юзера в БД
     */
    @RequestMapping("setUserOffline/{id}")
    public ResponseEntity setUserOffline(@PathVariable("id") int id){
        userRepository.setUserStatus(id, false);
        return new ResponseEntity(id, HttpStatus.OK);
    }

    /**
     * Получение списка юзера
     * @param status Статус юзера-онлайн или оффлайн
     * @param id Идентификатор юзера
     * @return List<User> Возвращет список юзеров
     */
    @RequestMapping(value = "getServerStats/{status}")
    public ResponseEntity getServerStats(@PathVariable("status") int status, @RequestParam(value = "id", required = false) Integer id) {
        List<User> userList;
        boolean stat_bool = false;
        if (status>0) stat_bool = true;
        userList = userRepository.getUserList(stat_bool, id);
        return new ResponseEntity(userList, HttpStatus.OK);
    }

}
