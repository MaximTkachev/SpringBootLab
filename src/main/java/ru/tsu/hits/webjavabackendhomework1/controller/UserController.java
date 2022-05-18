package ru.tsu.hits.webjavabackendhomework1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.webjavabackendhomework1.dto.users.*;
import ru.tsu.hits.webjavabackendhomework1.entity.Role;
import ru.tsu.hits.webjavabackendhomework1.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @PostMapping
    public UserDto createUser(@RequestBody CreateUserDto createUserDto){
        return userService.createUser(createUserDto);
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable UUID id){
        return userService.getUser(id.toString());
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @PostMapping("/file")
    public void createUsersFromFile() {
        userService.createUsersFromFile();
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @GetMapping("/search")
    public List<UserDto> searchUsersByTemplate(@RequestBody FetchUsersDto searchUserDto){
        return userService.fetchUsers(searchUserDto);
    }

    @PreAuthorize("hasAuthority('baseAdminPrm')")
    @PatchMapping("/{id}/role")
    public UserDto ChangeRole(@PathVariable UUID id, @RequestBody ChangeRoleDto changeRoleDto) {
        return userService.changeRole(id.toString(), changeRoleDto);
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @PutMapping("/{id}")
    public UserDto editUser(@RequestBody PatchUserDto patchUserDto, @PathVariable UUID id) {
        return userService.editUser(patchUserDto, id.toString());
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id){
        userService.deleteUser(id.toString());
    }
}
