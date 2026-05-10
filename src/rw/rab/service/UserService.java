/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rw.rab.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import rw.rab.model.User;

/**
 *
 * @author nsumba
 */
public interface UserService extends Remote{
    public String createUser(User user) throws RemoteException;
    public String updateUser(User user) throws RemoteException;
    public String deleteUser(User user) throws RemoteException;
    public List<User> getAllUsers() throws RemoteException;
    public User getUserById(User user) throws RemoteException;
    public User login(User user) throws RemoteException;
}
