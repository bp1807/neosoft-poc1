package com.neosoft.poc1.service;

import com.neosoft.poc1.exception.UserNotFoundException;
import com.neosoft.poc1.model.User;
import com.neosoft.poc1.repo.UserRepo;
import com.neosoft.poc1.utils.PagingHeaders;
import com.neosoft.poc1.utils.PagingResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Transactional
    public User addUser(User user){
        return userRepo.save(user);
    }

    public User getUser(Integer id) {
        return userRepo.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " does not exist"));
    }

    @Transactional
    public User editUser(Integer id, User updatedUser) {

        User originalUser = userRepo.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " does not exist"));

        updatedUser.setId(originalUser.getId());
        if(updatedUser.getName() == null) updatedUser.setName(originalUser.getName());
        if(updatedUser.getSurname() == null) updatedUser.setSurname(originalUser.getSurname());
        if(updatedUser.getPincode() == null) updatedUser.setPincode(originalUser.getPincode());
        if(updatedUser.getDateOfBirth() == null) updatedUser.setDateOfBirth(originalUser.getDateOfBirth());
        if(updatedUser.getDateOfJoining() == null) updatedUser.setDateOfJoining(originalUser.getDateOfJoining());

        return userRepo.save(updatedUser);
    }

    @Transactional
    public Boolean deleteUser(Integer id) {
        if(userRepo.existsById(id)){
            userRepo.deleteById(id);
            return true;
        } else
            throw new UserNotFoundException("User with id " + id + " does not exist");
    }

    @Transactional
    public void purgeUser(Integer id) {
             userRepo.purgeUserById(id);
    }

    public PagingResponse get(Specification<User> spec, HttpHeaders headers, Sort sort) {
        if (isRequestPaged(headers)) {
            return get(spec, buildPageRequest(headers, sort));
        } else {
            final List<User> entities = get(spec, sort);
            return new PagingResponse((long) entities.size(), 0L, 0L, 0L, 0L, entities);
        }
    }

    public List<User> get(Specification<User> spec, Sort sort) {
        return userRepo.findAll(spec, sort);
    }

    private Pageable buildPageRequest(HttpHeaders headers, Sort sort) {
        int page = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_NUMBER.getName())).get(0));
        int size = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_SIZE.getName())).get(0));
        return PageRequest.of(page, size, sort);
    }

    public PagingResponse get(Specification<User> spec, Pageable pageable) {
        Page<User> page = userRepo.findAll(spec, pageable);
        List<User> content = page.getContent();
        return new PagingResponse(page.getTotalElements(), (long) page.getNumber(), (long) page.getNumberOfElements(), pageable.getOffset(), (long) page.getTotalPages(), content);
    }

    private boolean isRequestPaged(HttpHeaders headers) {
        return headers.containsKey(PagingHeaders.PAGE_NUMBER.getName()) && headers.containsKey(PagingHeaders.PAGE_SIZE.getName());
    }

}
