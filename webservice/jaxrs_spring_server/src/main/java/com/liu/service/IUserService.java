package com.liu.service;

import com.liu.entity.User;

import javax.ws.rs.*;
import java.util.List;

/**
 * @ClassName: IUserService
 * @Auther: yu
 * @Date: 2019/2/19 15:42
 * @Description:
 */
@Path("/userService")
@Produces("*/*")
public interface IUserService {

    @POST
    @Path("/user")
    @Consumes({"application/xml","application/json"})
    public void saveUser(User user);

    @PUT
    @Path("/user")
    @Consumes({"application/xml","application/json"})
    public void updateUser(User user);

    @GET
    @Path("/user")
    @Produces({"application/xml","application/json"})
    public List<User> findAllUsers();

    @GET
    @Path("/user/{id}")
    @Consumes("application/xml")
    @Produces({"application/xml","application/json"})
    public User findUserById(@PathParam("id") Integer id);

    @DELETE
    @Path("/user/{id}")
    @Consumes({"application/xml","application/json"})
    public void deleteUser(User user);
}
