package ru.tsu.hits.webjavabackendhomework1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.webjavabackendhomework1.dto.comments.CommentDto;
import ru.tsu.hits.webjavabackendhomework1.dto.comments.CreateCommentDto;
import ru.tsu.hits.webjavabackendhomework1.dto.comments.PatchCommentDto;
import ru.tsu.hits.webjavabackendhomework1.dto.comments.SearchCommentByTextDto;
import ru.tsu.hits.webjavabackendhomework1.dto.tasks.TaskDto;
import ru.tsu.hits.webjavabackendhomework1.entity.CommentEntity;
import ru.tsu.hits.webjavabackendhomework1.entity.TaskEntity;
import ru.tsu.hits.webjavabackendhomework1.exceprion.NotFoundException;
import ru.tsu.hits.webjavabackendhomework1.mappers.CommentMapper;
import ru.tsu.hits.webjavabackendhomework1.mappers.TaskMapper;
import ru.tsu.hits.webjavabackendhomework1.repository.CommentRepository;
import ru.tsu.hits.webjavabackendhomework1.repository.TaskRepository;
import ru.tsu.hits.webjavabackendhomework1.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private final CommentMapper commentMapper;

    @Transactional
    public CommentDto createComment(CreateCommentDto createCommentDto) {
        var entity = commentMapper.CreateDtoToEntity(createCommentDto);
        var savedEntity = commentRepository.save(entity);
        return commentMapper.EntityToDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public CommentDto getComment(String id){
        var entity = commentRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        return commentMapper.EntityToDto(entity);
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getCommentsByText(SearchCommentByTextDto searchCommentByTextDto){
        var comments = commentRepository.findCommentsByTemplate(searchCommentByTextDto.getTemplate());
        var result = new ArrayList<TaskDto>();
        for (var comment : comments){
            var tasks = comment.getTask();
            for (var task : tasks)
                result.add(taskMapper.EntityToDto(task));
        }

        return result;
    }

    private CommentEntity getCommentEntity(String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Комментарий не найден"));
    }

    @Transactional
    public CommentDto editCommentDto(PatchCommentDto patchCommentDto, String id) {
        var comment = getCommentEntity(id);

        comment.setDateOfEdit(new Date());

        comment.setText(patchCommentDto.getText());

        return commentMapper.EntityToDto(comment);
    }

    @Transactional
    public void deleteCommentDto(String id) {
        commentRepository.deleteById(id);
    }
}
