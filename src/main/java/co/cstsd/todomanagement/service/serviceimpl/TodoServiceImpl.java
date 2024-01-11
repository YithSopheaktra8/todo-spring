package co.cstsd.todomanagement.service.serviceimpl;

import co.cstsd.todomanagement.dto.TodoDto;
import co.cstsd.todomanagement.entity.Todo;
import co.cstsd.todomanagement.exception.ResourceNotFoundException;
import co.cstsd.todomanagement.repository.TodoRepository;
import co.cstsd.todomanagement.service.TodoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    private ModelMapper modelMapper;

    @Override
    public TodoDto addTodo(TodoDto todoDto) {

//      convert todoDTO to TodoJpa entity
        Todo todo = modelMapper.map(todoDto,Todo.class);

//      save todoJpa to database
        Todo savedTodo = todoRepository.save(todo);

//      convert savedJpa to todoDto object
        TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class);

        return savedTodoDto;
    }

    @Override
    public TodoDto getTodo(Long id) {
//        get data from database
        Todo todo = todoRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Todo not found with id : "+id));

//        return and map todoJpa to todoDto
        return modelMapper.map(todo,TodoDto.class);
    }

    @Override
    public List<TodoDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();

        return todos.stream()
                .map(todo -> modelMapper.map(todo,TodoDto.class)).toList();
    }

    @Override
    public TodoDto updateTodo(TodoDto todoDto, Long id) {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Todo not found with id : "+id));

        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.getCompleted());

        Todo updatedTodo = todoRepository.save(todo);

        return modelMapper.map(updatedTodo,TodoDto.class);
    }


    @Override
    public void deleteTodo( Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Todo not found id : "+id));
        todoRepository.delete(todo);
    }

    @Override
    public TodoDto completeTodo(Long id) {

        Todo todo = todoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Todo not found id : "+ id));

        todo.setCompleted(Boolean.TRUE);

        Todo updated = todoRepository.save(todo);

        return modelMapper.map(updated,TodoDto.class);
    }

    @Override
    public TodoDto inCompleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Todo not found id : "+ id));
        todo.setCompleted(Boolean.FALSE);
        Todo updated = todoRepository.save(todo);

        return modelMapper.map(updated,TodoDto.class);
    }
}
