package ru.tsu.hits.webjavabackendhomework1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.webjavabackendhomework1.dto.comments.CommentDto;
import ru.tsu.hits.webjavabackendhomework1.dto.comments.CreateCommentDto;
import ru.tsu.hits.webjavabackendhomework1.dto.comments.PatchCommentDto;
import ru.tsu.hits.webjavabackendhomework1.dto.comments.SearchCommentByTextDto;
import ru.tsu.hits.webjavabackendhomework1.dto.tasks.PatchTaskDto;
import ru.tsu.hits.webjavabackendhomework1.dto.tasks.TaskDto;
import ru.tsu.hits.webjavabackendhomework1.service.CommentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @PostMapping
    public CommentDto createComment(@Validated @RequestBody CreateCommentDto createCommentDto){
        return commentService.createComment(createCommentDto);
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @GetMapping("/{id}")
    public CommentDto getComment(@PathVariable UUID id) {
        return commentService.getComment(id.toString());
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @GetMapping("/template")
    public List<TaskDto> getCommentsByText(@RequestBody SearchCommentByTextDto searchCommentByTextDto){
        return commentService.getCommentsByText(searchCommentByTextDto);
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @PutMapping("/{id}")
    public CommentDto editComment(@RequestBody PatchCommentDto patchCommentDto, @PathVariable UUID id) {
        return commentService.editCommentDto(patchCommentDto, id.toString());
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable UUID id) {
        commentService.deleteCommentDto(id.toString());
    }
}
