package ru.tsu.hits.webjavabackendhomework1.converter;

import ru.tsu.hits.webjavabackendhomework1.dto.comments.CommentDto;
import ru.tsu.hits.webjavabackendhomework1.dto.comments.CreateCommentDto;
import ru.tsu.hits.webjavabackendhomework1.entity.CommentEntity;
import ru.tsu.hits.webjavabackendhomework1.entity.TaskEntity;
import ru.tsu.hits.webjavabackendhomework1.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommentDtoConverter {


    public static CommentDto convertEntityToDto(CommentEntity commentEntity){
        return new CommentDto(
          commentEntity.getId(),
          commentEntity.getDateOfCreation(),
          commentEntity.getDateOfEdit(),
          commentEntity.getAuthor().getName(),
          getTaskOfComment(commentEntity),
          commentEntity.getText()
        );
    }

    private static List<String> getTaskOfComment(CommentEntity commentEntity){
        var tasks = commentEntity.getTask();
        var result = new ArrayList<String>();
        for (var task : tasks){
            result.add(task.getId());
        }

        return result;
    }

    /*
    public static CommentEntity convertCreateDtoToEntity(CreateCommentDto createCommentDto,
                                                         UserEntity author, List<TaskEntity> tasks)
    {
        return new CommentEntity(
                UUID.randomUUID().toString(),
                createCommentDto.getDateOfCreation(),
                createCommentDto.getDateOfEdit(),
                author,
                tasks,
                createCommentDto.getText()
        );
    } */
}
