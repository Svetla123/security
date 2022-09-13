package com.example.security.service;


import com.example.security.model.UserArrival;

import java.util.List;

public interface IUserArrivalService {
    UserArrival findUserArrivalById(Long id);
    List<UserArrival> findAllUserArrivals();

    boolean deleteUserArrival(long id);

    UserArrival createUserArrival (UserArrival userArrival);

    UserArrival updateUserArrival (long id, UserArrival userArrival);
}
