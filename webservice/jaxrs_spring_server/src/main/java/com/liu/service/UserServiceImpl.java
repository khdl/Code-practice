package com.liu.service;

import com.liu.entity.Car;
import com.liu.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: UserServiceImpl
 * @Auther: yu
 * @Date: 2019/2/19 15:51
 * @Description:
 */
public class UserServiceImpl implements IUserService{
    @Override
    public void saveUser(User user) {
        System.out.println("新增用户");
    }

    @Override
    public void updateUser(User user) {
        System.out.println("修改用户信息");
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("xiaoming");
        user1.setCity("chengdu");

        List<Car> cars = new ArrayList<>();
        Car car = new Car();
        car.setId(101);
        car.setCarname("大众");
        car.setPrice(100000d);
        cars.add(car);
        user1.setCar(cars);
        users.add(user1);
        return users;
    }

    @Override
    public User findUserById(Integer id) {
       if(1 == id){
           User user1 = new User();
           user1.setId(1);
           user1.setUsername("xiaoming");
           user1.setCity("chengdu");
           return user1;
       }
       return  null;
    }

    @Override
    public void deleteUser(User user) {
        System.out.println("删除用户信息");
    }
}
