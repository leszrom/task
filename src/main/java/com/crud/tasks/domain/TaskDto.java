package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String content;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskDto)) return false;

        final TaskDto taskDto = (TaskDto) o;

        if (id != null ? !id.equals(taskDto.id) : taskDto.id != null) return false;
        if (title != null ? !title.equals(taskDto.title) : taskDto.title != null) return false;
        return content != null ? content.equals(taskDto.content) : taskDto.content == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
