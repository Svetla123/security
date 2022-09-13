package com.example.security.service.impl;


import com.example.security.model.UserArrival;
import com.example.security.repository.UserArrivalRepository;
import com.example.security.service.IUserArrivalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserArrivalService implements IUserArrivalService {

    private UserArrivalRepository userArrivals;
    private UserArrivalService(UserArrivalRepository userArrivals) {
        super();
        this.userArrivals = userArrivals;
    }
    @Override
    public UserArrival findUserArrivalById(Long id) {
        try{
            return this.userArrivals.findById(id).get();

        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public List<UserArrival> findAllUserArrivals() {
        return this.userArrivals.findAll();
    }

    @Override
    public boolean deleteUserArrival (long id) {
        UserArrival userArrival = this.userArrivals.findById(id).get();
        try {
            this.userArrivals.delete(userArrival);
        }catch (Exception e) {
            return false;
        } finally {
            return true;
        }
    }

    @Override
    public UserArrival createUserArrival(UserArrival userArrival) {
        return this.userArrivals.save(userArrival);
    }

    @Override
    public UserArrival updateUserArrival(long id, UserArrival userArrival) {
        UserArrival oldUserArrival = this.userArrivals.findById(id).get();

        if (userArrival ==null) {
            return null;
        }else {
            oldUserArrival.setArrivalApproved(userArrival.isArrivalApproved());

            if (userArrival.getMeeting() != null){
                oldUserArrival.setMeeting(userArrival.getMeeting());
            }

            if (userArrival.getUser() != null){
                oldUserArrival.setUser(userArrival.getUser());
            }
        }
        return this.userArrivals.save(oldUserArrival);
    }


}
